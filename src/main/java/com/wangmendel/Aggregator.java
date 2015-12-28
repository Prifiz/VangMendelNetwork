package com.wangmendel;

import java.util.List;

public class Aggregator extends ThirdLevelNeuron {

    public void aggregate(List<SecondLevelNeuron> secondLevel) {
        double result = 0;
        for(SecondLevelNeuron neuron : secondLevel) {
            result += neuron.getWeight();
        }
        value = result;
    }
}
