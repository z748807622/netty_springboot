package com.zjy.netty_springboot.netty.FFMPGETools;

import io.netty.channel.group.ChannelGroup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class CallCmdClass {

    public Process p;

    /**
     * 发送cmd的错误信息
     * @param channels
     * @param error
     */
    public abstract void sendErrorInfo(ChannelGroup channels, String error);

    /**
     * 发送cmd的日志信息
     * @param channels
     * @param log
     */
    public abstract void sendLogInfo(ChannelGroup channels, String log);

    /**
     * 关闭cmd
     */
    public void close(){
        if(p != null){
            p.destroy();
        }
    }

    /**
     * 调用cmd命令
     * @param channels
     * @param cmd
     */
    public void callCmd(ChannelGroup channels, String cmd){
        try {
            //Process p = Runtime.getRuntime().exec("ping 172.247.34.70");
            //String cmd = "ffmpeg -stream_loop -1 -re -i D:/video/哈利波特第8部哈利波特与死亡圣器(下).mp4 -f mpegts -codec:v mpeg1video -s 864*486 udp://127.0.0.1:4444";
            //String cmd2 = "ffmpeg";
            String cmd3 = "ping 172.247.34.70";
            p = Runtime.getRuntime().exec(cmd);

            new Thread(()->{
                try {
                    InputStream is = p.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "GBK"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        //System.out.println("info--"+line);
                        sendLogInfo(channels,line);
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
                        //System.out.println("error -- "+line);
                        sendErrorInfo(channels, line);
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
