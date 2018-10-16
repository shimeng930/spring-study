package com.shim.pattern.singlton;

/**
 * @类描述：
 * @创建人：xn064961
 * @创建时间：2018/3/7 16:34
 * @版权：Copyright (c) 深圳市牛鼎丰科技有限公司-版权所有.
 */
public class SingltonDemo {

    private static volatile SingltonDemo instance;

    // 单例 设置构造函数为私有化，则外界不能直接调用构造函数来破坏单例原则
    private SingltonDemo(){};

    public static SingltonDemo getInstance() {
        if (instance == null) {
            synchronized (SingltonDemo.class) {
                if (instance == null) {
                    instance = new SingltonDemo();
                }
            }
        }
        return instance;
    }

}
