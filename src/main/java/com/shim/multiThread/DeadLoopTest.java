package com.shim.multiThread;

/**
 * @类描述：
 * @创建人：xn064961
 * @创建时间：2018/3/7 15:43
 * @版权：Copyright (c) 深圳市牛鼎丰科技有限公司-版权所有.
 */
public class DeadLoopTest implements Runnable {
    volatile  boolean running = true;
    int i = 0;
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
        while (running) {
            i++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DeadLoopTest task = new DeadLoopTest();
        Thread thread = new Thread(task);
        thread.start();
        Thread.sleep(100);
        task.running = false;
        Thread.sleep(100);
        System.out.println(task.i);
        System.out.println("program finish");
    }
}
