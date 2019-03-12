package com.zjy.netty_springboot.netty.webSocketChatServer;

import com.zjy.netty_springboot.Config;
import com.zjy.netty_springboot.netty.uploadVideoServer.UploadVideoServerApplication;
import com.zjy.netty_springboot.netty.webSocketChatServer.handler.UserInfoManager;
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
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ChatServerApplication {

    @Autowired
    @Qualifier("ChatBootstrap")
    private ServerBootstrap bootstrap;

    private ScheduledExecutorService executorService ;

    private Channel channel;
    private static Logger logger = LoggerFactory.getLogger(ChatServerApplication.class);

    public void start() throws InterruptedException, IOException {
        logger.info("netty websocket 聊天室服务 启动");
        executorService = Executors.newScheduledThreadPool(2);

        // 定时扫描所有的Channel，关闭失效的Channel
        executorService.scheduleAtFixedRate(()->{
            logger.info("scanNotActiveChannel --------");
            UserInfoManager.scanNotActiveChannel();
        },3,60, TimeUnit.SECONDS);

        // 定时向所有客户端发送Ping消息
        executorService.scheduleAtFixedRate(()->{
            logger.info("定时向所有客户端发送Ping消息 --------");
            UserInfoManager.broadCastPing();
        },3,5, TimeUnit.SECONDS);

        channel = bootstrap.bind().sync().channel().closeFuture().sync().channel();
        close();
    }

    public void close() throws IOException {
        logger.info("websocket 聊天室服务 关闭");
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        DefaultEventLoopGroup group1 = (DefaultEventLoopGroup)wac.getBean("getChatDefLoopGroup");
        DefaultEventLoopGroup group2 = (DefaultEventLoopGroup)wac.getBean("getChatBossGroup");
        DefaultEventLoopGroup group3 = (DefaultEventLoopGroup)wac.getBean("getChatWorkGroup");
        if (group1 != null)
            group1.shutdownGracefully();

        if (group2 != null)
            group2.shutdownGracefully();

        if (group3 != null)
            group3.shutdownGracefully();

        if(executorService != null)
            executorService.shutdown();

        if (channel != null) {
            channel.close();
            channel.parent().close();
        }
    }

}
