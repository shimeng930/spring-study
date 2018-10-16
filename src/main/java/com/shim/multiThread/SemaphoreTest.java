package com.shim.multiThread;

import java.util.concurrent.Semaphore;

/**
 * @类描述：
 * @创建人：xn064961
 * @创建时间：2018/9/3 20:37
 * @版权：Copyright (c) 深圳市牛鼎丰科技有限公司-版权所有.
 */
public class SemaphoreTest implements Runnable {

    private int workers;
    private Semaphore semaphore;

    public SemaphoreTest(int workers, Semaphore semaphore) {
        this.workers = workers;
        this.semaphore = semaphore;
    }

    public static void main(String[] s) {

        /*final Semaphore semaphore = new Semaphore(1);
        for (int i = 0; i < 8; i++) {
            new Thread(new SemaphoreTest(i, semaphore)).start();
        }*/

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            builder.append(",").append(i);
        }
        System.out.println(builder.toString());
        System.out.println(builder.toString().substring(1));
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println("worker " + workers + " use machine working...");
            Thread.sleep(1000);
            semaphore.release();
            System.out.println("worker " + workers + " release machine.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
