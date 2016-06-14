package org.code4j.codecat.realserver.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.code4j.codecat.realserver.handler.http.BaseHttpServerHandler;

/**
 * Description :
 * Created by YangZH on 16-6-14
 * 上午11:04
 */

public class RealServerChildHandler extends ChannelInitializer<SocketChannel> {
    private ChannelPipeline pipeline;
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        this.pipeline = socketChannel.pipeline();
        pipeline.addLast("request_decoder",new HttpRequestDecoder());
        pipeline.addLast("response_encoder",new HttpResponseEncoder());
        pipeline.addLast("aggregator",new HttpObjectAggregator(1024000));
        pipeline.addLast(new BaseHttpServerHandler());
    }
}
