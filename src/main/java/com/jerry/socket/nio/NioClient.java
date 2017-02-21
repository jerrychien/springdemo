package com.jerry.socket.nio;

import java.util.Scanner;

/**
 * Created by jerrychien on 2017-02-20 10:07.
 */
public class NioClient {

    private static final String DEAFULT_HOST = "127.0.0.1";

    private static final int DEFAULT_PORT = 8765;

    private static NioClientHandle nioClientHandle;

    public static void start() {
        start(DEAFULT_HOST, DEFAULT_PORT);
    }

    public static synchronized void start(String host, int port) {
        if (nioClientHandle == null) {
            nioClientHandle = new NioClientHandle(host, port);
            new Thread(nioClientHandle, "Client").start();
        }
    }

    public static boolean sendMsg(String msg) {
        try {
            if (msg.equalsIgnoreCase("bye")) {
                return false;
            }
            nioClientHandle.sendMsg(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        NioClient.start();
        while (NioClient.sendMsg(new Scanner(System.in).nextLine())) ;
        System.out.println("Client quit");
    }
}
