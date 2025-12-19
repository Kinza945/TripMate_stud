package com.kynzai.tripmate_stud.domain.model;

public class CurrencyInfo {
    private final String base;
    private final String target;
    private final double rate;

    public CurrencyInfo(String base, String target, double rate) {
        this.base = base;
        this.target = target;
        this.rate = rate;
    }

    public String getBase() {
        return base;
    }

    public String getTarget() {
        return target;
    }

    public double getRate() {
        return rate;
    }
}
