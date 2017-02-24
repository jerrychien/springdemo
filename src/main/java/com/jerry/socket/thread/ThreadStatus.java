package com.jerry.socket.thread;

/**
 * Created by jerrychien on 2017-02-24 15:15.
 * 详细的jstack日志见ThreadStatusJstack.txt文件
 */
public class ThreadStatus {
    public static void main(String[] args) {
        new Thread(new TimeWating(), "TimeWatting-thread").start();
        new Thread(new Watting(), "Watting-thread").start();
        new Thread(new Blocking(), "Blocking-thread1").start();
        new Thread(new Blocking(), "Blocking-thread2").start();
    }

    /**
     * 等待超时
     */
    static class TimeWating implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 无限等待,直到唤醒
     */
    static class Watting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Watting.class) {
                    try {
                        Watting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 阻塞和等待超时
     */
    static class Blocking implements Runnable {
        @Override
        public void run() {
            synchronized (Blocking.class) {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
