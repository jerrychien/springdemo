package com.jerry.socket.thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by jerrychien on 2017-02-27 10:43.
 * 采用中断的方式终止某个线程
 */
public class ThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {
        CancelThread r1 = new CancelThread();
        Thread t1 = new Thread(r1, "T1");
        Thread t2 = new Thread(new CancelThread(), "T2");
        t1.start();
        t2.start();
        TimeUnit.SECONDS.sleep(10);
        r1.cancel();
        t2.interrupt();
    }

    private static class CancelThread implements Runnable {

        private volatile boolean start = true;

        private int i;

        @Override
        public void run() {
            while (start && !Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println("Thread:" + Thread.currentThread().getName() + ", stoped! i+" + i);
        }

        public void cancel() {
            start = false;
        }
    }
}
