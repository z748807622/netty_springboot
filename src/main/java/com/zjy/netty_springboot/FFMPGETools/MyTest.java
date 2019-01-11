package com.zjy.netty_springboot.FFMPGETools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        /*ProcessBuilder builder = new ProcessBuilder();
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
        stdout.close();*/
        new MyTest().execCmd();
    }

    public void execCmd(){
        try {
            //Process p = Runtime.getRuntime().exec("ping 172.247.34.70"); 1152*640
            String cmd = "ffmpeg -stream_loop -1 -re -i D:/video/3有头脑的祖先.mp4 -f mpegts -codec:v mpeg1video -s 1152*640 udp://127.0.0.1:4444";
            //String cmd2 = "ffmpeg";
            String cmd3 = "ping 172.247.34.70";
            Process p = Runtime.getRuntime().exec(cmd);

            new Thread(()->{
                try {
                    InputStream is = p.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "GBK"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("info--"+line);
                    }
                    is.close();
                    reader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }).start();

            new Thread(()->{
                try {
                    InputStream is = p.getErrorStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "GBK"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("error -- "+line);
                    }
                    is.close();
                    reader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }).start();
            p.waitFor();
            p.destroy();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
