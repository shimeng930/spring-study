package com.shim.pattern.strategy;

/**
 * Created by xn064961 on 2017/2/15.
 */
public interface CalPrice {

    Double calPrice(Double originalPrice);
}

@TotalCostRegion(max = 1000)
class Common implements CalPrice{

    public Double calPrice(Double originalPrice) {
        return originalPrice;
    }

}

@TotalCostRegion(min = 1000, max = 2000)
class Vip implements CalPrice{

    public Double calPrice(Double originalPrice) {
        return originalPrice * 0.8;
    }

}

@TotalCostRegion(min = 2000, max = 3000)
class SuperVip implements CalPrice{

    public Double calPrice(Double originalPrice) {
        return originalPrice * 0.7;
    }

}

@TotalCostRegion(min = 3000)
class GoldVip implements CalPrice{

    public Double calPrice(Double originalPrice) {
        return originalPrice * 0.5;
    }

}
