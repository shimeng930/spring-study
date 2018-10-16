package com.shim.multiThread;

/**
 * @类描述：
 * @创建人：xn064961
 * @创建时间：2018/3/6 14:47
 * @版权：Copyright (c) 深圳市牛鼎丰科技有限公司-版权所有.
 */
public class ThreadLocalDemo {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    private static int value = 0;

    public static class ThreadLocalThread implements Runnable {

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
            threadLocal.set((int) (Math.random() * 100));
            value = (int) (Math.random() * 100);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf(Thread.currentThread().getName() + ": threadLocal=%d, value=%d\n", threadLocal.get(), value);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new ThreadLocalThread());
        Thread t1 = new Thread(new ThreadLocalThread());
        t.start();
        t1.start();
//        t.join();
//        t1.join();
    }
}
