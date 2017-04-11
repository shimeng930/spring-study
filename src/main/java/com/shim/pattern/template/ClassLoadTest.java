package com.shim.pattern.template;

/**
 * Created by xn064961 on 2017/2/15.
 */
public class ClassLoadTest {

    public static void main(String[] args) throws Exception {
        Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass("com.shim.pattern.template.ClassLoadTest");
        Object entity = clazz.newInstance();
        System.out.println(entity instanceof ClassLoadTest);
    }

}

class MyClassLoad extends ClassLoader {

}
