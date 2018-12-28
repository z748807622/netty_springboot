package com.zjy.netty_springboot.netty.UDPStreamServer;

import com.zjy.netty_springboot.netty.transpondStreamServer.TranspondStreamServerApplication;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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

}
