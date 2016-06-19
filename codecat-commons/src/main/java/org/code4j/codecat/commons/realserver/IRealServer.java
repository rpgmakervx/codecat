package org.code4j.codecat.commons.realserver;/**
 * Description : 
 * Created by YangZH on 16-6-17
 *  上午2:00
 */

import io.netty.channel.ChannelHandler;

/**
 * Description :
 * Created by YangZH on 16-6-17
 * 上午2:00
 */

public interface IRealServer {

    public void startup(int port);

    public abstract void setup();
    public abstract void initHandler();
    public abstract void addHandler(Class<? extends ChannelHandler> handler);
    public abstract void launch(int port);

    public abstract void close();
}
