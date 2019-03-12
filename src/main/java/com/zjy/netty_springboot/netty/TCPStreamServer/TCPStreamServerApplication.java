package com.zjy.netty_springboot.netty.TCPStreamServer;

import com.zjy.netty_springboot.Config;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TCPStreamServerApplication {
    @Autowired
    @Qualifier("TCPStreamBootstrap")
    private ServerBootstrap bootstrap;

    private Channel channel;
    private static Logger logger = LoggerFactory.getLogger(TCPStreamServerApplication.class);

    public void start() throws InterruptedException, IOException {
        System.out.println("TCPStreamServer 启动...");
        logger.info("TCPStreamServer 启动...");
        channel = bootstrap.bind(Config.ADDRESS_URL,Config.TCP_STREAM_PORT).sync().channel().closeFuture().sync().channel();
    }
}
