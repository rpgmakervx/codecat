package org.code4j.codecat.api.response;/**
 * Description : 
 * Created by YangZH on 16-6-17
 *  上午1:08
 */

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import org.code4j.codecat.api.http.Method;

/**
 * Description :
 * Created by YangZH on 16-6-17
 * 上午1:08
 */

public class HttpResponse {
    private DefaultFullHttpResponse response;
    private int statusCode;

    private Method method;

    public void addHeader(String key,String value){
        response.headers().add(key,value);
    }

    public void setContentType(String contentType){
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,contentType);
    }

    public void sendError(int statusCode,String msg){
//        response.setStatus(new HttpResponseStatus(statusCode,));
    }
}
