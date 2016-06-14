package org.code4j.codecat.realserver.server;/**
 * Description : 
 * Created by YangZH on 16-6-14
 *  下午2:32
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;
import org.code4j.codecat.realserver.handler.RealServerChildHandler;


/**
 * Description :
 * Created by YangZH on 16-6-14
 * 下午2:32
 */

public class RealServer {

    private Logger logger = Logger.getLogger(RealServer.class);
    public void startup(int port){
        launch(port);
    }

    private void launch(int port){
        logger.info("launching real server...");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        ChannelFuture f = null;
        try {
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new RealServerChildHandler())
                    .option(ChannelOption.SO_BACKLOG, 256).childOption(ChannelOption.SO_KEEPALIVE, true);
            f = b.bind(port).sync();
            logger.info("server started completely");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
