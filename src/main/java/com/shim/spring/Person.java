package com.shim.spring;

/**
 * Created by xn064961 on 2017/1/17.
 */
public class Person {

    private int age;
    private String name;
    private String sex;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    private KungFu kungFu;

    public Person(KungFu kungfu) {
        System.out.println("autowiring by constructor");
        this.kungFu = kungfu;
    }

    public KungFu getKungfu() {
        return kungFu;
    }

    public void setKungfu(KungFu kungfu) {
        System.out.println("autowiring by type");
        this.kungFu = kungfu;
    }

    private static class InnerClass{
        static Person pInstance = new Person();
    }
    private Person(){}

    public static Person getInstance (){
        return InnerClass.pInstance;
    }

    public void action(){
        System.out.println("action~~~~");
    }
}
