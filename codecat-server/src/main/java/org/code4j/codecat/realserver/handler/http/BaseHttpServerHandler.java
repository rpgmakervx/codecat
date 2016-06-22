package org.code4j.codecat.realserver.handler.http;/**
 * Description : 
 * Created by YangZH on 16-6-14
 *  下午3:34
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.code4j.codecat.api.response.factory.HttpResponseFactory;
import org.code4j.codecat.api.container.HttpContainer;
import org.code4j.codecat.api.container.Path;

/**
 * Description :
 * Created by YangZH on 16-6-14
 * 下午3:34
 */
@Path("/")
public class BaseHttpServerHandler extends HttpContainer {

    @Override
    public Object service(Object msg) {
        return "<h1 align='center'>Welcome to CodeCat home!!!</h1>\n";
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.writeAndFlush(HttpResponseFactory.getFullResponse(
                cause.getMessage(), HttpResponseStatus.INTERNAL_SERVER_ERROR));

        ctx.close();
        cause.printStackTrace();
    }

}
