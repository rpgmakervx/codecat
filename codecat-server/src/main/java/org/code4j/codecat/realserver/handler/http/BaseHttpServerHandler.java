package org.code4j.codecat.realserver.handler.http;/**
 * Description : 
 * Created by YangZH on 16-6-14
 *  下午3:34
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.log4j.Logger;
import org.code4j.codecat.api.response.factory.HttpResponseFactory;
import org.code4j.codecat.api.service.BasicHttpHandler;
import org.code4j.codecat.api.service.Path;

/**
 * Description :
 * Created by YangZH on 16-6-14
 * 下午3:34
 */
@Path("/")
public class BaseHttpServerHandler extends BasicHttpHandler {
    private Logger logger  = Logger.getLogger(BaseHttpServerHandler.class);
    private int count = 0;
    private FullHttpRequest request;

    @Override
    public Object service(Object msg) {
        return "<h1>Hello World  "+msg+"</h1>\n";
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.writeAndFlush(HttpResponseFactory.getResponse(
                cause.getMessage(), HttpResponseStatus.INTERNAL_SERVER_ERROR));

        ctx.close();
        cause.printStackTrace();
    }

}
