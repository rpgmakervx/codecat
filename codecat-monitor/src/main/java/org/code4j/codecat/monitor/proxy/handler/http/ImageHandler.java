package org.code4j.codecat.monitor.proxy.handler.http;/**
 * Description : SocksServerHandler
 * Created by YangZH on 16-5-25
 *  上午9:18
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;
import org.code4j.codecat.monitor.listener.PortCounter;
import org.code4j.codecat.monitor.proxy.client.MonitorClient;
import org.code4j.codecat.monitor.proxy.util.WebUtil;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import static org.code4j.codecat.commons.constants.Const.LOCALHOST;

/**
 * Description : SocksServerHandler
 * Created by YangZH on 16-5-25
 * 上午9:18
 */

public class ImageHandler extends ChannelInboundHandlerAdapter{
    private Logger logger  = Logger.getLogger(ImageHandler.class);
    private InetSocketAddress address;
    private ExecutorService threadPool = Executors.newCachedThreadPool();

    public ImageHandler() {
        this.address = new InetSocketAddress(LOCALHOST, PortCounter.getPort());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        messageReceived(ctx,msg);
    }

    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        threadPool.submit(new Task(ctx,msg));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void response(ChannelHandlerContext ctx,byte[] contents,Header[] headers) throws UnsupportedEncodingException {
        ByteBuf byteBuf = Unpooled.wrappedBuffer(contents, 0, contents.length);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,byteBuf);
        logger.info("response header ---------------");
        for (Header header:headers){
            response.headers().set(header.getName(),header.getValue());
            logger.info(header.getName()+"::"+header.getValue());
        }
        logger.info("end header ---------------");
        ctx.channel().writeAndFlush(response);
        ctx.close();
    }

    class Task implements Runnable{

        Object msg;
        ChannelHandlerContext ctx;

        public Task( ChannelHandlerContext ctx,Object msg) {
            this.msg = msg;
            this.ctx = ctx;
        }
        @Override
        public void run() {
            HttpRequest request = (HttpRequest) msg;
            try {
                if (request.method().equals(HttpMethod.GET)){
                    Pattern pattern = Pattern.compile(".+\\.("+ WebUtil.IMAGE+").*");
                    String context = "";
                    byte[] bytes = null;
                    CloseableHttpResponse response = null;
                    //读取图片
                    if (pattern.matcher(request.uri()).matches()){
                        MonitorClient client = new MonitorClient(address,WebUtil.ROOT.equals(request.uri())?"":request.uri());
                        //在这里强转类型，如果使用了聚合器，就会被阻塞
                        logger.info("读取到图片 " + request.uri());
                        response = client.fetchImage();
                        bytes = client.getResponseBytes(response);
                        response(ctx, bytes, response.getAllHeaders());
                    }else{
                        ctx.fireChannelRead(request);
                    }
                }else{
                    ctx.fireChannelRead(request);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
