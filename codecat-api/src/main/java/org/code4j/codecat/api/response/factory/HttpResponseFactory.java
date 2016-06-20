package org.code4j.codecat.api.response.factory;/**
 * Description : 
 * Created by YangZH on 16-6-17
 *  上午1:10
 */

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.Date;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpHeaderValues.CHUNKED;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Description :
 * Created by YangZH on 16-6-17
 * 上午1:10
 */

public class HttpResponseFactory {

    public static FullHttpResponse getResponse(String content,HttpResponseStatus status){
        FullHttpResponse response = null;
        try {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, Unpooled.wrappedBuffer(content.getBytes("UTF-8")));
            response.setStatus(status);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html");
            response.headers().set(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
            response.headers().set(HttpHeaderNames.DATE,new Date());
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,
                    response.content().readableBytes());
            response.headers().set(HttpHeaderNames.CONNECTION,  HttpHeaderValues.KEEP_ALIVE);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
