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
        String fileName1 = "自然发现鸟类的生活——成长.mp4";
        String fileName2 = "有头脑的祖先.mp4";
        String fileName3 = "output.avi";
        try {
            //Process p = Runtime.getRuntime().exec("ping 172.247.34.70"); 1152*640  1152*640
            String cmd = "ffmpeg -stream_loop -1 -re -i D:/video/" + fileName2 + " -f mpegts -codec:v mpeg1video -s 1037*576 udp://127.0.0.1:4444";
            //String cmd2 = "ffmpeg";
            String cmd3 = "ping 172.247.34.70";
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(cmd);

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
