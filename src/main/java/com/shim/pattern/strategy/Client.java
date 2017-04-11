package com.shim.pattern.strategy;

/**
 * Created by xn064961 on 2017/2/15.
 */
public class Client {

    public static void main(String[] s) {
        /*Context ctx = new Context();
        ctx.setStrategy(new StrategyA());
        ctx.method();

        ctx.setStrategy(new StrategyB());
        ctx.method();

        ctx.setStrategy(new StrategyC());
        ctx.method();*/

        // 购物client
        Customer customer = new Customer();
        customer.buy(500D);
        System.out.println("客户需要付钱：" + customer.calLastCost());
        customer.buy(1000D);
        System.out.println("客户需要付钱：" + customer.calLastCost());
        customer.buy(1000D);
        System.out.println("客户需要付钱：" + customer.calLastCost());
        customer.buy(1000D);
        System.out.println("客户需要付钱：" + customer.calLastCost());
    }
}
