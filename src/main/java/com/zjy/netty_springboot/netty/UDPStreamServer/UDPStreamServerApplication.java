package com.zjy.netty_springboot.netty.UDPStreamServer;

import com.zjy.netty_springboot.netty.transpondStreamServer.TranspondStreamServerApplication;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.DefaultEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@Component
public class UDPStreamServerApplication {

    @Autowired
    @Qualifier("bootstrapUDP")
    private Bootstrap bootstrap;

    private Channel channel;
    private static Logger logger = LoggerFactory.getLogger(TranspondStreamServerApplication.class);

    public void start(String host, int port) throws Exception{
        try {
            channel = bootstrap.bind(port).sync().channel();
            System.out.println("UDPStreamServer start at " + port);
            channel.closeFuture().await();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stop(){
        logger.info("UDPStreamServer 关闭");
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        DefaultEventLoopGroup group1 = (DefaultEventLoopGroup)wac.getBean("getWorkGroup2");
        group1.shutdownGracefully();
        channel.close();
        channel.parent().close();
    }

}
