package com.shim.multiThread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @类描述：
 * @创建人：xn064961
 * @创建时间：2018/8/31 15:02
 * @版权：Copyright (c) 深圳市牛鼎丰科技有限公司-版权所有.
 */
public class CountdownTest {

//    private Lock lock = new ReentrantLock();

    public static void main(String[] s) {

        final CountDownLatch latch = new CountDownLatch(2);

        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
                    Thread.sleep(3000);
                    System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();

        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
                    Thread.sleep(3000);
                    System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();

        System.out.println("等待2个子线程执行完毕...");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("2个子线程已经执行完毕");
        System.out.println("继续执行主线程");
    }

    public void insert(Thread thread) {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            System.out.println(thread.getName() + "获得了锁");
            Thread.sleep(500);
        } catch (Exception e) {

        }finally {
            System.out.println(thread.getName() + "实放了锁");
            lock.unlock();
        }
    }
}
