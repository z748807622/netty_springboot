package com.zjy.netty_springboot.netty.UDPStreamServer;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UDPStreamServer {

    @Bean(name = "getWorkGroup2")
    public EventLoopGroup getWorkGroup(){
        return new NioEventLoopGroup();
    };

    @Bean(name = "bootstrapUDP")
    public Bootstrap bootstrap(){
        Bootstrap b = new Bootstrap();
        b.group(getWorkGroup()).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new UdpServerHandler());
        return b;
    }


}
