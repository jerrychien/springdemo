package com.jerry.socket.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Created by jerrychien on 2017-02-21 12:07.
 * CAS的ABA问题 使用版本标记stamp  AtomicStampedReference类实现
 */
public class CASAba {

    private static AtomicInteger atomicInt = new AtomicInteger(100);

    private static AtomicStampedReference atomicStampedRef = new AtomicStampedReference(100, 0);

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("A1(A):" + atomicInt.compareAndSet(100, 101));//A
                System.out.println("A2(B):" + atomicInt.compareAndSet(101, 100));//B
            }
        }, "T1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A3(A):" + atomicInt.compareAndSet(100, 101));//A
            }
        }, "T2");
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread asrT1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean asrResultA = atomicStampedRef.compareAndSet(100, 101, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
                System.out.println("asrT1ResultA:" + asrResultA + ", stamp:" + atomicStampedRef.getStamp());
                boolean asrResultB = atomicStampedRef.compareAndSet(101, 101, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
                System.out.println("asrT1ResultB:" + asrResultB + ", stamp:" + atomicStampedRef.getStamp());
            }
        }, "asrT1");

        Thread asrT2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int stamp = atomicStampedRef.getStamp();
                System.out.println("stamp:" + stamp);
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean c3 = atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1);
                System.out.println("asrT2ResultA:" + c3); // false
            }
        }, "asrT2");
        asrT1.start();
        asrT2.start();
    }
}
