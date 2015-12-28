package com.wangmendel;

public class FourthLevelNeuron {

    public double getResult() {
        return result;
    }

    private double result;

    public void calculateResult(ThirdLevelNeuron aggregator, ThirdLevelNeuron normalizer) {
        result = normalizer.getValue() / aggregator.getValue();
    }
}
