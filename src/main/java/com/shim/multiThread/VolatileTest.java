package com.shim.multiThread;

/**
 * @类描述：
 * @创建人：xn064961
 * @创建时间：2017/5/5 17:23
 * @版权：Copyright (c) 深圳市牛鼎丰科技有限公司-版权所有.
 */
public class VolatileTest {

    public static void main(String[] args) throws InterruptedException {
        /*int value = 1000;
        Thread.currentThread().getThreadGroup();

        Task task = new Task();
        for (int i = 0; i < value; i++) {
            new Thread(task).start();
        }*/


        Task2 task = new Task2();
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();t2.start();
        t1.join(); t2.join();
        System.out.println(task.i);

    }
}


class Task implements Runnable{

    private volatile int count = 0;

    @Override
    public void run() {
        System.out.print(Thread.currentThread().getName() + ">>>" + count + "=========");
        for (int i = 0; i < 100; i++) {
            // 耗时运算
            Math.hypot(Math.pow(92456789, i), Math.cos(i));
        }
        // 输出自增结果
        System.out.println(Thread.currentThread().getName() + ">>>" + count++);
    }
}

class Task2 implements Runnable{

    public volatile int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 10000; j++) {
           i++;
        }
    }
}