package com.shim.pattern.strategy;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xn064961 on 2017/2/15.
 */
public class CalPriceFactory {

    // 扫描策略的包名路径
    private static final String CAL_PRICE_PACKAGE = "com.shim.pattern.strategy";

    // 加载策略时的类加载器
    private ClassLoader classLoader = getClass().getClassLoader();

    // 策略列表
    private List<Class<? extends CalPrice>> calPriceList;

    /*public static CalPrice createCalPrice(Customer customer) {
        if (customer.getTotalCost() > 3000) {
            return new GoldVip();
        }else if (customer.getTotalCost() > 2000) {
            return new SuperVip();
        }else if (customer.getTotalCost() > 1000) {
            return new Vip();
        }else {
            return new Common();
        }
    }*/

    private CalPriceFactory () {
        init();
    }

    public static CalPriceFactory getInstance () {
        return CalPriceFactoryInstance.instance;
    }

    private static class CalPriceFactoryInstance{
        private static CalPriceFactory instance = new CalPriceFactory();
    }

    // 获取包下面所有的class文件
    private File[] getResources() {
        try {
            File file = new File(classLoader.getResource(CAL_PRICE_PACKAGE.replace(".", "/")).toURI());
            return file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.getName().endsWith(".class")) {
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("未找到策略资源");
        }
    }

    //工厂初始化时，初始化策略列表
    private void init () {
        calPriceList = new ArrayList<Class<? extends CalPrice>>();
        File[] resources = getResources();
        Class<CalPrice> calPriceClass = null;

        try {
            calPriceClass = (Class<CalPrice>) classLoader.loadClass(CalPrice.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("未找到策略接口");
        }

        for (int i = 0; i < resources.length; i++) {
            try {
                //载入包下的类
                Class<?> clazz = classLoader.loadClass(CAL_PRICE_PACKAGE + "."+resources[i].getName().replace(".class", ""));
                //判断是否是CalPrice的实现类并且不是CalPrice它本身，满足的话加入到策略列表
                if (CalPrice.class.isAssignableFrom(clazz) && clazz != calPriceClass) {
                    calPriceList.add((Class<? extends CalPrice>) clazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //处理注解（传入一个策略类，返回其注解）
    private TotalCostRegion handleAnnotation (Class<? extends CalPrice> clazz) {
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        if (annotations == null || annotations.length == 0) {
            return null;
        }

        for (int i = 0; i < annotations.length; i++) {
            if (annotations[i] instanceof TotalCostRegion) {
                return (TotalCostRegion) annotations[i];
            }
        }
        return null;
    }

    // 根据客户的总额产生相应的策略
    public CalPrice createCalPrice (Customer customer) {
        for (Class<? extends CalPrice> clz : calPriceList) {
            TotalCostRegion region = handleAnnotation(clz);
            Double totalCost = customer.getTotalCost();
            if (totalCost > region.min() && totalCost < region.max()) {
                try {
                    return clz.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("策略获得失败");
                }
            }
        }

        throw new RuntimeException("策略获得失败");
    }


}
