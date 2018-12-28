package com.zjy.netty_springboot.netty.uploadVideoServer;

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
public class UploadVideoServerApplication {

    @Autowired
    @Qualifier("UploadBootstrap")
    private ServerBootstrap bootstrap;

    private Channel channel;
    private static Logger logger = LoggerFactory.getLogger(UploadVideoServerApplication.class);

    public void start() throws InterruptedException, IOException {
        System.out.println("netty websocket 上传文件服务 启动");
        logger.info("etty websocket 上传文件服务 启动");
        channel = bootstrap.bind(Config.UPLOAD_VIDEO_SERVER_PORT).sync().channel().closeFuture().sync().channel();
        close();
    }

    public void close() throws IOException {
        logger.info("关闭");
        channel.close();
        channel.parent().close();
    }

}
