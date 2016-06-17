package org.code4j.codecat.monitor.proxy.handler.http;/**
 * Description : CSSFilterHandler
 * Created by YangZH on 16-5-26
 *  下午5:01
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;
import org.code4j.codecat.commons.dao.RequestDataDao;
import org.code4j.codecat.monitor.listener.PortCounter;
import org.code4j.codecat.monitor.proxy.client.MonitorClient;
import org.code4j.codecat.monitor.proxy.util.WebUtil;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.code4j.codecat.commons.constants.Const.LOCALHOST;

/**
 * Description : CSSFilterHandler
 * Created by YangZH on 16-5-26
 * 下午5:01
 */

public class GetRequestHandler extends ChannelInboundHandlerAdapter {
    private Logger logger  = Logger.getLogger(GetRequestHandler.class);
    private InetSocketAddress address;
    private RequestDataDao dao = new RequestDataDao();
    private ExecutorService threadPool = Executors.newCachedThreadPool();

    public GetRequestHandler() {
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
        ctx.channel().flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.debug("CSS 解析异常");
        cause.printStackTrace();
    }

    private void response(ChannelHandlerContext ctx,byte[] contents,Header[] headers) throws UnsupportedEncodingException {
        ByteBuf byteBuf = Unpooled.wrappedBuffer(contents, 0, contents.length);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,byteBuf);
        logger.debug("response header ---------------");
        for (Header header:headers){
            response.headers().set(header.getName(),header.getValue());
            logger.debug(header.getName() + "::" + header.getValue());
        }
        logger.debug("end header ---------------");
        ctx.channel().writeAndFlush(response);
        ctx.close();
    }
    private void response(ChannelHandlerContext ctx,byte[] contents) throws UnsupportedEncodingException {
        ByteBuf byteBuf = Unpooled.wrappedBuffer(contents, 0, contents.length);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,byteBuf);
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
            String context = "";
            byte[] bytes = null;
            CloseableHttpResponse response = null;
            HttpRequest request = (HttpRequest) msg;
            boolean isGet = request.method().equals(HttpMethod.GET);
            boolean isJSON = "application/json".equals(request.headers().get("Content-Type"));
            try {
                if (isGet){
                    MonitorClient client = new MonitorClient(address, WebUtil.ROOT.equals(request.uri())?"":request.uri());
                    if (isJSON){
                        logger.debug("GET 业务请求");
                        //redis缓存
                        String cache = dao.get(request.uri(),"");
                        if (cache == null ||cache.isEmpty()){
                            logger.debug("cache没有命中");
                            response = client.fetchText(request.headers());
                            context = client.getResponse(response);
                            dao.save(request.uri(),"",context);
                            bytes = context.getBytes();
                            response(ctx, bytes, response.getAllHeaders());
                        }else{
                            logger.debug("cache命中！ " + cache);
                            response(ctx, cache.getBytes());
                        }
                    }else{
                        logger.debug("GET 页面请求");
                        response = client.fetchText(request.headers());
                        context = client.getResponse(response);
                        //CDN缓存
                        bytes = context.getBytes();
                        response(ctx, bytes, response.getAllHeaders());
                    }
                }else{
                    logger.debug("非GET请求或JSON类型  " + request.uri());
                    ctx.fireChannelRead(request);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
