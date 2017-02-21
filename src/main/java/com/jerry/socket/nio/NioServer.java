package com.jerry.socket.nio;

/**
 * NIO Server
 * Created by jerrychien on 2017-02-16 23:43.
 */
public class NioServer {

    private static int PORT = 8765;

    private static NioServerHandle nioServerHandle;

    public static void start() {
        start(PORT);
    }

    public static synchronized void start(int port) {
        if (nioServerHandle == null) {
            nioServerHandle = new NioServerHandle(port);
            new Thread(nioServerHandle, "Server").start();
        }
    }

    public static void main(String[] args) {
        start();
    }
}
