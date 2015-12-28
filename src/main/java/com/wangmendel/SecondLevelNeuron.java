package com.wangmendel;

import java.util.List;

public class SecondLevelNeuron {
    private int implicationIdx;
    private double weight;

    public SecondLevelNeuron(int implicationIdx) {
        this.implicationIdx = implicationIdx;
    }

    public void calculateWeight(List<FirstLevelNeuron> firstLevel) {
        double result = 1;
        for (FirstLevelNeuron firstLevelNeuron : firstLevel) {
            if (firstLevelNeuron.getImplicationIdx() == implicationIdx) {
                result *= firstLevelNeuron.getWeight();
            }
        }
        this.weight = result;
    }

    public double getWeight() {
        return weight;
    }
}
