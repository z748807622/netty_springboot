package com.zjy.netty_springboot.netty.TCPStreamServer;

import com.zjy.netty_springboot.Config;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class TCPStreamServer {

    @Bean(name = "getTCPStreamBossGroup")
    public NioEventLoopGroup bossGroup(){
        return new NioEventLoopGroup(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "BOSS_" + index.incrementAndGet());
            }
        });
    }

    @Bean(name = "getTCPStreamWorkGroup")
    public NioEventLoopGroup workGroup(){
        return new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 10, new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "WORK_" + index.incrementAndGet());
            }
        });
    }

    @Bean(name = "TCPStreamBootstrap")
    public ServerBootstrap bootstrap(){
        //创建ServerBootstrap实例
        ServerBootstrap serverBootstrap=new ServerBootstrap();
        //初始化ServerBootstrap的线程组
        serverBootstrap.group(bossGroup(),workGroup());//
        //设置将要被实例化的ServerChannel类
        serverBootstrap.channel(NioServerSocketChannel.class);//
        //在ServerChannelInitializer中初始化ChannelPipeline责任链，并添加到serverBootstrap中
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("idleStateHandler",
                        new IdleStateHandler(15, 0, 0, TimeUnit.MINUTES));
                //自定义Hadler
                pipeline.addLast("handler11",new TCPServerHandler());
                //自定义Hander,可用于处理耗时操作，不阻塞IO处理线程
                //pipeline.addLast(group,"BussinessHandler",new BussinessHandler());
            }
        });
        //标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        // 是否启用心跳保活机机制
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        return serverBootstrap;
    }

}
