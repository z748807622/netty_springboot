package com.zjy.netty_springboot.netty.transpondStreamServer;

import com.zjy.netty_springboot.Config;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.DefaultEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@Component
public class TranspondStreamServerApplication {

    @Autowired
    @Qualifier("bootstrap")
    private ServerBootstrap serverBootstrap;

    private Channel channel;
    private static Logger logger = LoggerFactory.getLogger(TranspondStreamServerApplication.class);

    public void start() throws InterruptedException, IOException {
        System.out.println("netty websocket 推流 启动");
        logger.info("etty websocket 推流 启动");
        channel = serverBootstrap.bind(Config.TRANSPOND_STREAM_SERVER_PORT).sync().channel().closeFuture().sync().channel();
        close();
    }

    public void close() throws IOException {
        logger.info("websocket 推流 关闭");
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        DefaultEventLoopGroup group1 = (DefaultEventLoopGroup)wac.getBean("getDefLoopGroup");
        DefaultEventLoopGroup group2 = (DefaultEventLoopGroup)wac.getBean("getBossGroup");
        DefaultEventLoopGroup group3 = (DefaultEventLoopGroup)wac.getBean("getWorkGroup");
        group1.shutdownGracefully();
        group2.shutdownGracefully();
        group3.shutdownGracefully();
        channel.close();
        channel.parent().close();
    }

}
