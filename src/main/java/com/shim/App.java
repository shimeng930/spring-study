package com.shim;

import java.math.BigDecimal;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( value2String("0.00") );
    }

    protected static Long value2String(String num) {
        Double d = Double.valueOf(num) * 100;
        return (long) (Double.valueOf(num) * 100);
    }

    public static void tree(){
        // test
    }
}
