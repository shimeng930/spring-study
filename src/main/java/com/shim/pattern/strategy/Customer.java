package com.shim.pattern.strategy;

/**
 * Created by xn064961 on 2017/2/15.
 */
public class Customer {

    private Double totalCost = 0d;
    // 单次消费金额
    private Double cost = 0d;
    private CalPrice calPrice = new Common();

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void buy(Double cost){
        this.cost = cost;
        totalCost += cost;
        calPrice = CalPriceFactory.getInstance().createCalPrice(this);
    }

    public Double calLastCost () {
        return calPrice.calPrice(totalCost);
    }
}
