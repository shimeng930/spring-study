package com.shim.spring;

/**
 * Created by xn064961 on 2017/2/28.
 */
public class Book {
    public static class Load {
        public void loading() {
            System.out.println("loading......");
        }
        public Load(Book b){}
        private int length;

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
    }

    private String name;
    private Load load;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Load getLoad() {
        return load;
    }

    public void setLoad(Load load) {
        this.load = load;
    }


    public void init() {
        System.out.println("call init function...");
    }

    public void destroy() {
        System.out.println("call destroy function...");
    }

    public void doing() {
        System.out.println("call doing function...");
    }
}
