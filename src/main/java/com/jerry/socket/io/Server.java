package com.jerry.socket.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server-普通的socket
 *
 * @author jerrychien
 * @create 2016-06-30 10:40
 */
public class Server {

    private static final int PORT = 9999;
    private List<Socket> mList = new ArrayList<Socket>();
    private ServerSocket server = null;
    private ExecutorService mExecutorService = null;//線程池

    public Server() {
        try {
            server = new ServerSocket(PORT);
            System.out.println("[server started]");
            mExecutorService = Executors.newCachedThreadPool();
            //心跳检测
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("--检测状态---");
                        for (int i = 0; i < mList.size(); i++) {
                            Socket socket = mList.get(i);
                            try {
                                socket.sendUrgentData(0xFF);
                            } catch (IOException e) {
                                mList.remove(socket);
                                try {
                                    socket.close();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                System.out.println("客户端掉线, 剩余客户端数:" + mList.size());
                            }
                        }
                    }
                }
            }).start();
            Socket client = null;
            while (true) {
                client = server.accept();
                mList.add(client);
                System.out.println("[one client connected][" + client.hashCode() + "][current count:" + mList.size() + "]");
                mExecutorService.execute(new ChatService(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ChatService implements Runnable {

        private Socket socket;

        private BufferedReader bufferedReader = null;

        private PrintWriter printWriter;

        private String msg;

        public ChatService(Socket socket) {
            this.socket = socket;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printWriter = new PrintWriter(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            this.sendMsg("welcome client" + socket.getPort() + "!!!, current count is :" + mList.size(), true);
            while (true) {
                try {
                    String content = "";
                    if ((content = bufferedReader.readLine()) != null) {
                        System.out.println("收到消息");
                        //如果收到信息為"exit",則向客戶端返回ok,然后關掉socket,
                        if (content.equalsIgnoreCase("exit")) {
                            System.out.println("socket closed, quit");
                            msg = "用戶IP:" + socket.getInetAddress() + ",PORT: " + socket.getPort() + "退出, 退出后在線用戶數量：" + (mList.size() - 1);
                            mList.remove(socket);
                            System.out.println(msg);
                            this.sendMsg(msg, true);
                            this.sendMsg("exit", false);
                            this.closeSocket();
                            break;
                        } else {
                            //打印客戶提交過來的其它信息
                            msg = "用户IP:" + socket.getInetAddress() + ",PORT: " + socket.getPort() + "[" + socket.hashCode() + "]" + "说: \"" + content + "\", 当前用户数量:" + mList.size();
                            this.sendMsg(msg, true);
                        }
                    }
                    if (socket.isClosed()) {
                        this.closeSocket();
                        break;
                    }
                } catch (IOException e) {
                    System.out.println("error---------");
                    e.printStackTrace();
                }
            }
            System.out.println("thread over");
        }

        private void closeSocket() {
            System.out.println("客户端关闭,循环结束");
            try {
                bufferedReader.close();
                printWriter.flush();
                printWriter.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendMsg(String msg, boolean toAll) {
            System.out.println("server send :[" + msg + "]");
            if (toAll) {
                for (Socket tempSocket : mList) {
                    try {
                        if (tempSocket == socket) {
                            msg = "[自己说]" + msg;
                        }
                        PrintWriter pw = new PrintWriter(tempSocket.getOutputStream());
                        pw.println(msg);
                        pw.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                printWriter.println(msg);
                printWriter.flush();
                printWriter.close();
            }
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
