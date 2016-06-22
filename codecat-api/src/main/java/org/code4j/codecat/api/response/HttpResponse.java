package org.code4j.codecat.api.response;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.code4j.codecat.commons.util.JSONUtil;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Description :
 * Created by YangZH on 16-6-17
 * 上午1:08
 */

public class HttpResponse implements Response{
    private DefaultFullHttpResponse response;
    private ChannelHandlerContext ctx;
    private int statusCode;

    private HttpHeaders headers;

    private Object msg;

    private Charset charset;

    private HttpResponseStatus status;


    public HttpResponse(ChannelHandlerContext ctx){
        this.ctx = ctx;
        String content = null;
        try {
            if (msg instanceof String){
                content = (String) msg;
            }else if (msg instanceof Map){
                content = JSONUtil.mapToStr((Map<String, Object>) msg);
            }else if (msg instanceof Object){
                content = JSONUtil.objectToStr(msg);
            }
            this.response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, Unpooled.wrappedBuffer(content.getBytes(charset)));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void addHeader(String key,String value){
        headers.add(key, value);
    }

    @Override
    public void setContentType(String contentType){
        headers.set(HttpHeaderNames.CONTENT_TYPE, contentType);
    }

    @Override
    public void setCharacterEncoding(String encoding){
        charset = Charset.forName(encoding);
    }

    @Override
    public void setStatusCode(HttpResponseStatus status){
        this.status = status;
    }

    @Override
    public void write(Object msg){
        ctx.writeAndFlush(msg);
    }

    @Override
    public void writeAndClose(Object msg) {
        ctx.writeAndFlush(msg);
        close();
    }

    @Override
    public void close(){
        ctx.close();
    }
}
