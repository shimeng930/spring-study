package com.shim.pattern.template;

/**
 * Created by xn064961 on 2017/2/15.
 */
public class MyPageBuilder extends AbstractPageBuilder {

    public static void main(String[] s){
        MyPageBuilder mp = new MyPageBuilder();
        System.out.println(mp.buildHtml());
    }

    @Override
    protected void appendHead(StringBuffer sb) {
        sb.append("<head><title>你好</title></head>");
    }

    @Override
    protected void appendBody(StringBuffer sb) {
        sb.append("<body><h1>你好,世界！</h1></body>");
    }
}
