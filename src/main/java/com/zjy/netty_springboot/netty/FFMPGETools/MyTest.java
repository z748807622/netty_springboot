package com.zjy.netty_springboot.netty.FFMPGETools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyTest {
    /*public static void main(String[] args) throws IOException, InterruptedException {
        *//*ProcessBuilder builder = new ProcessBuilder();
        builder.command("ping 127.0.0.1");
        builder.redirectErrorStream();
        Process proc = builder.start();
        BufferedReader stdout = new BufferedReader(new InputStreamReader(proc.getInputStream(),"GBK"));
        BufferedReader errout = new BufferedReader(new InputStreamReader(proc.getErrorStream(),"GBK"));

        String line;
        while ((line = stdout.readLine()) != null){
            System.out.println(line);
        }
        proc.waitFor();
        stdout.close();*//*
        new MyTest().execCmd();
    }

    public void execCmd(){
        try {
            //Process p = Runtime.getRuntime().exec("ping 172.247.34.70");
            //String cmd = "ffmpeg -stream_loop -1 -re -i C:/Users/74880/Desktop/人与自然/《人与自然》自然传奇：与远古人同行h2哈利波特第8部哈利波 特与死亡圣器(下).mp4 -f mpegts -codec:v mpeg1video -s 864*486 udp://127.0.0.1:4444";
            //String cmd2 = "ffmpeg";
            String cmd3 = "ping 172.247.34.70";
            Process p = Runtime.getRuntime().exec(cmd3);
            InputStream is = p.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "GBK"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.flush();
                System.out.println(line);
            }

            p.waitFor();
            is.close();
            reader.close();
            p.destroy();
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
}
