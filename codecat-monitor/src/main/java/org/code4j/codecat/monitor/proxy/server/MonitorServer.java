package org.code4j.codecat.monitor.proxy.server;/**
 * Description : realserver
 * Created by YangZH on 16-5-25
 *  上午8:14
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;
import org.code4j.codecat.monitor.listener.PortCounter;
import org.code4j.codecat.monitor.proxy.handler.MonitorServerChildHandler;
import org.code4j.codecat.monitor.proxy.invoker.ShellInvoker;

/**
 * Description : realserver
 * Created by YangZH on 16-5-25
 * 上午8:14
 */

public class MonitorServer {

    private Logger logger  = Logger.getLogger(MonitorServer.class);

    /**
     * 这个启动方法是为了封装成jar包后提供给用户来方便他们调用时设置jproxy端口的
     * @param port
     */
    public void startup(int port){
        launch(port);
    }

    private void launch(int port){
        logger.debug("proxy server is starting...");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        ChannelFuture f = null;
        try {
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MonitorServerChildHandler())
                    .option(ChannelOption.SO_BACKLOG, 256).childOption(ChannelOption.SO_KEEPALIVE, true);
            f = b.bind(port).sync();
            logger.info("proxy server has started");
            ShellInvoker.execute(ShellInvoker.STARTUP_SERVER
                    ,String.valueOf(PortCounter.getPort()));
            logger.info("real server is drived by proxy server");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
