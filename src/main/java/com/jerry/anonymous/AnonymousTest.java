package com.jerry.anonymous;

/**
 * Created by jerrychien on 06/01/2017.
 */
public class AnonymousTest {
    public static void main(String[] args) {
        new Life() {
            @Override
            public void sleep() {
                System.out.println("Anonymous sleep for Life interface!");
            }
        }.sleep();

        new AbstractLife() {
            @Override
            void sleep() {
                System.out.println("Anonymous sleep for AbstractLife!");
            }
        }.sleep();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("runing");
            }
        }).start();

        new Runnable() {

            /**
             * When an object implementing interface <code>Runnable</code> is used
             * to create a thread, starting the thread causes the object's
             * <code>run</code> method to be called in that separately executing
             * thread.
             * <p>
             * The general contract of the method <code>run</code> is that it may
             * take any action whatsoever.
             *
             * @see Thread#run()
             */
            @Override
            public void run() {
                System.out.println("Runnable runing");
            }
        }.run();
    }
}
