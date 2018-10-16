package com.shim.multiThread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @类描述：
 * @创建人：xn064961
 * @创建时间：2018/8/31 15:02
 * @版权：Copyright (c) 深圳市牛鼎丰科技有限公司-版权所有.
 */
public class LockTest {

//    private Lock lock = new ReentrantLock();

    public static void main(String[] s) {
        final LockTest test = new LockTest();
        new Thread() {
            @Override
            public void run(){
                test.insert(Thread.currentThread());
            }
        }.start();
        new Thread() {
            @Override
            public void run(){
                test.insert(Thread.currentThread());
            }
        }.start();

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
