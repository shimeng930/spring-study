package com.shim.multiThread;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @类描述：
 * @创建人：xn064961
 * @创建时间：2017/11/22 14:16
 * @版权：Copyright (c) 深圳市牛鼎丰科技有限公司-版权所有.
 */
public class TaskTest {

    public static void runTask(Class<? extends Runnable> clz) throws InterruptedException,
            InstantiationException,
            IllegalAccessException {
        ExecutorService es = Executors.newCachedThreadPool();
        System.out.println("*** 开始执行 " + clz.getSimpleName() + " 任务****");
        for (int i = 0; i < 3; i++) {
            es.submit(clz.newInstance());
        }
        TimeUnit.SECONDS.sleep(10);
        System.out.println("---------" + clz.getSimpleName() + "-----------任务执行完毕！\n\n");
        es.shutdown();
    }

    /**
     *
     * @param args
     * @throws Exception
     * @throws
     * @throws
     */
    public static void main(String[] args) throws Exception {
        runTask(TaskWithLock.class);

        runTask(TaskWithSync.class);
    }
}


class TaskWithSync extends Task1 implements Runnable {

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        synchronized ("A") {
            doSomething();
        }
    }

}

class TaskWithLock extends Task1 implements Runnable {

    Lock lock = new ReentrantLock();

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            lock.lock();
            doSomething();
        }
        finally {
            lock.unlock();
        }
    }
}

class Task1 {

    public void doSomething() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {

        }
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("线程名称：" + Thread.currentThread().getName());
        strBuffer.append(", 执行时间 ：" + Calendar.getInstance().get(13) + "s");
        System.out.println(strBuffer.toString());
    }

}
