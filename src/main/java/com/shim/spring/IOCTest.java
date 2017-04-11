package com.shim.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xn064961 on 2017/1/17.
 */
public class IOCTest {

    public static void main (String[] a) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        Person p = (Person) ctx.getBean("person");
        System.out.println(p.getKungfu().getName());

        Person p02 = (Person) ctx.getBean("person02");
        p02.action();

        /**
         * spring注入内部类
         * 1、内部类加static修饰
         * 2、在配置文件中添加<constructor-arg ref="book" />标签，因为非静态内部类默认的构造方法中有
         *  一个参数，是其外部类的实例
         */
        Book book = (Book) ctx.getBean("book");
        book.getLoad().loading();

        Book book01 = (Book) ctx.getBean("book01");
        ((ClassPathXmlApplicationContext)ctx).close();

    }
}
