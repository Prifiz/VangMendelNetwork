package com.wangmendel;

import java.util.List;

public class Normalizer extends ThirdLevelNeuron {

    public void normalize(List<SecondLevelNeuron> secondLevel, List<Float> weights) {
        double result = 0;
        for(int i = 0; i < secondLevel.size(); i++) {
            SecondLevelNeuron neuron = secondLevel.get(i);
            float netWeight = weights.get(i);
            result += netWeight * neuron.getWeight();
        }
        value = result;
    }
}
