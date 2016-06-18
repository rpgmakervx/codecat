package org.code4j.codecat.realserver.server;/**
 * Description : 
 * Created by YangZH on 16-6-14
 *  下午2:32
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.apache.log4j.Logger;
import org.code4j.codecat.realserver.handler.RealServerChildHandler;
import org.code4j.codecat.realserver.handler.http.BaseHttpServerHandler;


/**
 * Description :
 * Created by YangZH on 16-6-14
 * 下午2:32
 *
 * 将 server的启动分成两个过程：
 * 1.设置启动参数和业务
 * 2.绑定端口并开启服务
 * 这样做主要是方便动态代理server端的启动参数设置，
 * 可以在server函数本身设置好后代理再进行增强，
 * 如果写一起，先launch再增强功能是没用的，因为服务已经启动
 * 先增强功能再launch会被后面launch里设置参数部分覆盖。
 */

public class RealServer implements IRealServer{

    private Logger logger = Logger.getLogger(RealServer.class);
    private EventLoopGroup bossGroup ;
    private EventLoopGroup workerGroup;
    private ServerBootstrap bootstrap;
    private ChannelFuture future ;
    private RealServerChildHandler childHandler;

    public void startup(int port){
        setup();
        launch(port);
    }

    public RealServer() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        childHandler = new RealServerChildHandler();
        initHandler();
    }

    public void setup(){
        logger.info("launching real server...");
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(childHandler).option(ChannelOption.SO_BACKLOG, 256)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    public void launch(int port){
        try {
            future = bootstrap.bind(port).sync();
            logger.info("server started completely port is : "+port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public void initHandler(){
        childHandler.addHandler(HttpRequestDecoder.class);
        childHandler.addHandler(HttpResponseEncoder.class);
        childHandler.addHandler(HttpObjectAggregator.class);
        childHandler.addHandler(BaseHttpServerHandler.class);
    }

    public void addHandler(Class<? extends ChannelHandler> handler){
        childHandler.addHandler(handler);
    }

}
