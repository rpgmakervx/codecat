package org.code4j.codecat.api.request;/**
 * Description : 
 * Created by YangZH on 16-6-21
 *  下午4:03
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Description :
 * Created by YangZH on 16-6-21
 * 下午4:03
 */

public class HttpRequest implements Request{

    private DefaultFullHttpRequest request;

    private ChannelHandlerContext ctx;

    private int remoteIp;

    private String remoteAddr;

    private String queryString;

    private HttpHeaders headers;

    private Charset charset;

    private int contentLength;

    HttpPostRequestDecoder decoder;

    public HttpRequest(DefaultFullHttpRequest request){
        this.request = request;
        this.decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), request);
        this.contentLength = this.request.content().toString().length();
    }

    @Override
    public void install() {

    }

    @Override
    public String getParam(String key) {
        InterfaceHttpData data = decoder.getBodyHttpData(key);
        String value = "";
        if (InterfaceHttpData.HttpDataType.Attribute == data.getHttpDataType()) {
            MemoryAttribute attribute = (MemoryAttribute) data;
            attribute.setCharset(CharsetUtil.UTF_8);
            value = attribute.getValue();
        }
        return value;
    }

    @Override
    public int getContentLength() {
        return this.contentLength;
    }

    @Override
    public String getContentType() {
        return headers.get(HttpHeaderNames.CONTENT_TYPE);
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String encoding) {
        charset = Charset.forName(encoding);
    }
}
