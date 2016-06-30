package com.jerry.socket.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client--普通的socket
 *
 * @author jerrychien
 * @create 2016-06-30 10:40
 */
public class Client {

    private int port = 9999;

    private String host = "127.0.0.1";

    private Socket socket;

    private BufferedReader bufferedReader;

    private PrintWriter printWriter;

    private volatile boolean quit = false;

    public Client() {
        try {
            socket = new Socket(host, port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream());
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    Scanner scanner = new Scanner(System.in);
                    while (true) {
                        String content = scanner.nextLine();
                        printWriter.println(content);
                        printWriter.flush();
                        if (content.equalsIgnoreCase("exit")) {
                            close();
                            break;
                        }
                    }
                    scanner.close();
                    System.out.println("scanner stopped");
                }
            });
            t1.start();
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String line = null;
                        try {
                            if ((line = bufferedReader.readLine()) != null) {
                                System.out.println("[received msg from server:]" + line);
                                if (line.equalsIgnoreCase("exit")) {
                                    System.out.println("[client will quit]");
                                    close();
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("reader stopped");
                }
            });
            t2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            bufferedReader.close();
            printWriter.close();
            socket.close();
            quit = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
