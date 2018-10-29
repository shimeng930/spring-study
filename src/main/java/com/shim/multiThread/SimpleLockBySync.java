package com.shim.multiThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @类描述：
 * @创建人：xn064961
 * @创建时间：2018/10/17 16:20
 * @版权：Copyright (c) 深圳市牛鼎丰科技有限公司-版权所有.
 */
public class SimpleLockBySync {

    private long ownerThreadId = -1;

    public synchronized void lock() {
        long threadId = Thread.currentThread().getId();
        //todo 可改为：支持可重入（增加state字段记录重入次数并且释放锁时也要做减次数操作）
        if (threadId == ownerThreadId) {
            throw new IllegalStateException("lock has been acquired by current thread");
        }

        while (this.ownerThreadId != -1) {
            System.out.println(String.format("thread %s is waiting lock", threadId));
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ownerThreadId = threadId;
        System.out.println(String.format("lock is acquired by thread %s", threadId));
    }


    public synchronized void unlock() {
        long threadId = Thread.currentThread().getId();
        if (threadId != ownerThreadId) {
            throw new IllegalStateException("only lock owner can unlock the lock");
        }

        System.out.println(String.format("thread %s is unlocking", threadId));
        ownerThreadId = -1;
        notify();
    }

    public static void main(String[] s) {
        final SimpleLockBySync lock = new SimpleLockBySync();
        int num = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(num);

        for (int i = 0; i < num; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    System.out.println(String.format("thread %s is running...", Thread.currentThread().getId()));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.unlock();
                }
            });
        }
    }
}
