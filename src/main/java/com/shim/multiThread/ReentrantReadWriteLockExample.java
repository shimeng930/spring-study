package com.shim.multiThread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @类描述：
 * @创建人：xn064961
 * @创建时间：2018/10/16 9:37
 * @版权：Copyright (c) 深圳市牛鼎丰科技有限公司-版权所有.
 */
public class ReentrantReadWriteLockExample {

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public void get(String key) {
        readLock.lock();
        System.out.print(key + " get lock>>>");
        try {
            System.out.println(key + " is reading...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            readLock.unlock();
        }
    }

    public void put(String key, String value) {
        writeLock.lock();
        System.out.print(key + " get lock>>>");
        try {
            System.out.println(key + " is writing...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] s) {
        final ReentrantReadWriteLockExample example = new ReentrantReadWriteLockExample();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    example.get(Thread.currentThread().getName());
                }
            }).start();
        }

        /*for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    example.put(Thread.currentThread().getName(), "");
                }
            }).start();
        }*/

    }
}
