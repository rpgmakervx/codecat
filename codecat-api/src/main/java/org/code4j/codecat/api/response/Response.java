package org.code4j.codecat.api.response;/**
 * Description : 
 * Created by YangZH on 16-6-21
 *  上午12:59
 */

import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * Description :
 * Created by YangZH on 16-6-21
 * 上午12:59
 */

public interface Response {

    public void addHeader(String key,String value);

    public void setContentType(String contentType);

    public void write(Object msg);
    public void setCharacterEncoding(String encoding);
    public void writeAndClose(Object msg);
    public void setStatusCode(HttpResponseStatus status);
    public void close();


}
