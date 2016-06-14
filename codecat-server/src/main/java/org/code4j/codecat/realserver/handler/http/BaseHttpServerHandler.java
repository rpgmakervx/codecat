package org.code4j.codecat.realserver.handler.http;/**
 * Description : 
 * Created by YangZH on 16-6-14
 *  下午3:34
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.Date;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpHeaderValues.CHUNKED;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Description :
 * Created by YangZH on 16-6-14
 * 下午3:34
 */

public class BaseHttpServerHandler extends ChannelInboundHandlerAdapter {
    private Logger logger  = Logger.getLogger(BaseHttpServerHandler.class);

    private FullHttpRequest request;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        request = (FullHttpRequest) msg;
        String uri = request.uri();
        logger.info("uri:" + uri);
        ByteBuf buf = request.content();
        logger.info(buf.toString(CharsetUtil.UTF_8));
        buf.release();
        InetSocketAddress address = (InetSocketAddress)ctx.channel().localAddress();
        String response = "<h1>"+address.getHostName()+":"+address.getPort()+"</h1>";
        ctx.writeAndFlush(makeResponseByRequest(response,HttpResponseStatus.OK));
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("BaseHttpServerHandler.channelReadComplete");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.writeAndFlush(makeResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR));
        ctx.close();
    }

    private FullHttpResponse makeResponseByRequest(String content,HttpResponseStatus status)throws Exception{
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                OK, Unpooled.wrappedBuffer(content.getBytes("UTF-8")));
        response.setStatus(status);
        return originResponse(response);
    }

    private FullHttpResponse makeResponse(HttpResponseStatus status) throws Exception {
        String res = "<h1>I am OK</h1>";
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                OK, Unpooled.wrappedBuffer(res.getBytes("UTF-8")));
        response.setStatus(status);
        return originResponse(response);
    }

    public FullHttpResponse originResponse(FullHttpResponse response){

        response.headers().set(CONTENT_TYPE, "text/html");
        response.headers().set(TRANSFER_ENCODING, CHUNKED);
        response.headers().set(DATE,new Date());
        response.headers().set(CONTENT_LENGTH,
                response.content().readableBytes());
        response.headers().set(CONNECTION,  HttpHeaderValues.KEEP_ALIVE);
        return response;
    }
}
