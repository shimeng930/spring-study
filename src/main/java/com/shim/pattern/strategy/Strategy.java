package com.shim.pattern.strategy;

/**
 * 一个策略接口 & 多个接口实现
 * Created by xn064961 on 2017/2/15.
 */
public interface Strategy {

    void algorithm();
}

class StrategyA implements Strategy {

    @Override
    public void algorithm() {
        System.out.println("策略A");
    }
}

class StrategyB implements Strategy {

    @Override
    public void algorithm() {
        System.out.println("策略B");
    }
}

class StrategyC implements Strategy {

    @Override
    public void algorithm() {
        System.out.println("策略C");
    }
}
