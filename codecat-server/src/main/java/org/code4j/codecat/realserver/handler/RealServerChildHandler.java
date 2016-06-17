package org.code4j.codecat.realserver.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Created by YangZH on 16-6-14
 * 上午11:04
 */

public class RealServerChildHandler extends ChannelInitializer<SocketChannel> {
    private List<Class<? extends ChannelHandler>> handlers;

    /**
     * 动态添加handler的时候，要去重复，但是不能用hashset,
     * 这是个无序的集合，会导致你的编解码器顺序错乱，导致有的handler会得不到正确的请求数据而失效
     * 所以必须要按照顺序添加。用arraylist自己做一步去重
     * （因为用的是class，反射调用，所以用contains就够了，肯定不会有重复类型）
     * @param handler
     */
    public void addHandler(Class<? extends ChannelHandler> handler){
        if (!handlers.contains(handler))
            handlers.add(handler);
    }
    public RealServerChildHandler(){
        handlers = new ArrayList<>();
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        for (Class<? extends ChannelHandler> clazz:handlers){
            if (clazz == HttpObjectAggregator.class){
                Constructor<? extends ChannelHandler> constructor = clazz.getConstructor(int.class);
                pipeline.addLast(constructor.newInstance(1024000));
            }else{
                pipeline.addLast(clazz.newInstance());
            }
        }
        System.out.println("handler size : "+handlers.size()+"  执行一次 initChannel "+pipeline.toMap().size());
    }
}
