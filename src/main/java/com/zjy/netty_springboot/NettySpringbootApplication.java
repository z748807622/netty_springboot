package com.zjy.netty_springboot;

import com.zjy.netty_springboot.netty.UDPStreamServer.UDPStreamServerApplication;
import com.zjy.netty_springboot.netty.chat.ChatServerApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class NettySpringbootApplication {

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context = SpringApplication.run(NettySpringbootApplication.class, args);

        new Thread(()->{
            ChatServerApplication chatServerApplication = context.getBean(ChatServerApplication.class);
            try {
                chatServerApplication.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            UDPStreamServerApplication udpStreamServerApplication = context.getBean(UDPStreamServerApplication.class);
            try {
                udpStreamServerApplication.start("127.0.0.1",4444);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        //SpringApplication.run(NettySpringbootApplication.class, args);
    }

}

