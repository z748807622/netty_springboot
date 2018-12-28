package com.zjy.netty_springboot;

import com.zjy.netty_springboot.netty.UDPStreamServer.UDPStreamServerApplication;
import com.zjy.netty_springboot.netty.transpondStreamServer.TranspondStreamServerApplication;
import com.zjy.netty_springboot.netty.uploadVideoServer.UploadVideoServerApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NettySpringbootApplication {

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context = SpringApplication.run(NettySpringbootApplication.class, args);

        new Thread(()->{
            TranspondStreamServerApplication transpondStreamServerApplication = context.getBean(TranspondStreamServerApplication.class);
            try {
                transpondStreamServerApplication.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            UDPStreamServerApplication udpStreamServerApplication = context.getBean(UDPStreamServerApplication.class);
            try {
                udpStreamServerApplication.start(Config.ADDRESS_URL,Config.UDP_STREAM_SERVER_PORT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            UploadVideoServerApplication uploadVideoServerApplication = context.getBean(UploadVideoServerApplication.class);
            try {
                uploadVideoServerApplication.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        //SpringApplication.run(NettySpringbootApplication.class, args);
    }

}

