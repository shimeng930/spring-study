package com.shim.java;

import com.shim.spring.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：isAssignableFrom方法测试
 * @创建人：xn064961
 * @创建时间：2017/4/24 20:45
 * @版权：Copyright (c) 深圳市牛鼎丰科技有限公司-版权所有.
 */
public class AssignableTest {

    /**
     * isAssignableFrom() 和 instanceof
     * Class.isAssignableFrom()是用来判断一个类Class1和另一个类Class2是否相同或是另一个类的子类或接口。
     *      格式为： Class1.isAssignableFrom(Class2)
     *      调用者和参数都是java.lang.Class类型。
     * 而instanceof是用来判断一个对象实例是否是一个类或接口的或其子类子接口的实例。
     *      格式是：o instanceof TypeName
     *      第一个参数是对象实例名，第二个参数是具体的类名或接口名，例如   String,InputStream
     */

    public AssignableTest(String name) {
    }

    /**
     * 判断一个类是否是另一个类的父类
     * 是打印true
     * 否打印false
     */
    public static void testIsAssignedFrom1() {
        System.out.println("String是Object的父类:"+String.class.isAssignableFrom(Object.class));
    }

    /**
     * 判断一个类是否是另一个类的父类
     * 是打印true
     * 否打印false
     */
    public static void testIsAssignedFrom2() {
        System.out.println("Object是String的父类:"+Object.class.isAssignableFrom(String.class));
    }
    /**
     * 判断一个类是否和另一个类相同
     * 是打印true
     * 否打印false
     */
    public static void testIsAssignedFrom3() {
        System.out.println("Object和Object相同:"+Object.class.isAssignableFrom(Object.class));
    }

    /**
     * 判断str是否是Object类的实例
     * 是打印true
     * 否打印false
     */
    public static void testInstanceOf1() {
        String str = new String();
        System.out.print("str是Object的实例:");
        System.out.println(str instanceof Object);
    }
    /**
     * 判断o是否是Object类的实例
     * 是打印true
     * 否打印false
     */
    public static void testInstanceOf2() {
        List<?> o = new ArrayList<>();
        System.out.print("o是Object的实例:");
        System.out.println(o instanceof List<?>);
    }

    public static void main(String[] args) {
        testIsAssignedFrom1();
        testIsAssignedFrom2();
        testIsAssignedFrom3();
        testInstanceOf1();
        testInstanceOf2();
    }
}
