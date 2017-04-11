package com.shim.alg;

/**
 * Created by xn064961 on 2017/3/21.
 */
public class SqrtTest {

    public static void main(String[] s){
        System.out.println(judge(10, 0.01d));
    }

    private static boolean judge(int v, double t){
        double r = sqrt(v, t);
        System.out.println("true sqrt v is: " + Math.sqrt(v));
        System.out.println("sqrt v : " + r);
        if (Math.abs(r-Math.sqrt(v)) <= t)
            return true;
        else
            return false;
    }

    private static double sqrt(int v, double t){
        double r = 0.0d;
        do {
            r++;
        } while (r*r < v);
        r--;

        do {
            r += t;
            System.out.print(r + ",");
        } while (r*r < v);
        r -= t;
        System.out.println();

        return r;
    }

    private static double divideSqrt(int v, double t){
        return 0;
    }
}
