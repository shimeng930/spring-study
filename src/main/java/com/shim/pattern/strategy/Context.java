package com.shim.pattern.strategy;

/**
 * 上下文 有一个策略接口，并且可以选择某个策略实现
 * Created by xn064961 on 2017/2/15.
 */
public class Context {

    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void method(){
        strategy.algorithm();
    }

}
