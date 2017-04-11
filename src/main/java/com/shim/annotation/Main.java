package com.shim.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xn064961 on 2017/1/16.
 */
public class Main {

    public static void main (String[] s) {
        List<Integer> useCases = new ArrayList<Integer>();
        Collections.addAll(useCases, 24, 48, 49, 50);
        trackUserCases(useCases, PwdUtils.class);
    }

    public static void trackUserCases (List<Integer> userCases, Class<?> cls) {
        for (Method m : cls.getDeclaredMethods()) {
            UserCase uc = m.getAnnotation(UserCase.class);
            if (uc != null) {
                System.out.println("userCase: " + uc.id() + "," + uc.desc());
                userCases.remove(new Integer(uc.id()));
            }
        }

        for (int i : userCases) {
            System.out.println("miss userCase-" + i);
        }
    }

}
