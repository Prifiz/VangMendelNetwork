package com.wangmendel;

import java.util.ArrayList;
import java.util.List;

public class Network {

    private List<FirstLevelNeuron> firstLevelNeurons;
    private List<SecondLevelNeuron> secondLevelNeurons;
    private List<ThirdLevelNeuron> thirdLevelNeurons;
    private List<FourthLevelNeuron> fourthLevelNeurons;

    private List<Float> weights;
    private List<Float> cValues;
    int implicationsCount;

    public void setcValues(List<Float> cValues) {
        this.cValues = cValues;
    }

    public void setWeights(List<Float> weights) {
        this.weights = weights;
    }

    public Network(int implicationsCount) {
        firstLevelNeurons = new ArrayList<FirstLevelNeuron>();
        secondLevelNeurons = new ArrayList<SecondLevelNeuron>();
        thirdLevelNeurons = new ArrayList<ThirdLevelNeuron>();
        fourthLevelNeurons = new ArrayList<FourthLevelNeuron>();
        weights = new ArrayList<Float>();
        cValues = new ArrayList<Float>();
        this.implicationsCount = implicationsCount;
    }

    public void init(List<Float> inputVector) {
        initFirstLevel(inputVector, cValues);
        initSecondLevel();
        initThirdLevel();
        initFourthLevel();
    }

    public double getResult(List<Float> inputVector) {
        float inputValue = 0;
        int inputIdx = 0;
        for(int i = 0; i < firstLevelNeurons.size(); i++) {
            FirstLevelNeuron neuron = firstLevelNeurons.get(i);
            if(i % (firstLevelNeurons.size()/inputVector.size()) == 0) {
                inputValue = inputVector.get(inputIdx);
                inputIdx++;
            }
            neuron.execute(inputValue);
        }
        for(SecondLevelNeuron neuron : secondLevelNeurons) {
            neuron.calculateWeight(firstLevelNeurons);
        }

        Aggregator aggregator = (Aggregator) thirdLevelNeurons.get(0);
        Normalizer normalizer = (Normalizer) thirdLevelNeurons.get(1);
        aggregator.aggregate(secondLevelNeurons);
        normalizer.normalize(secondLevelNeurons, weights);
        FourthLevelNeuron fourthLevelNeuron = fourthLevelNeurons.get(0);
        fourthLevelNeuron.calculateResult(aggregator, normalizer);
        return fourthLevelNeuron.getResult();
    }

    // inputVectorSize is N
    // implicationsCount is M
    private void initFirstLevel(List<Float> inputVector, List<Float> c_values) {
        for(int idx = 0; idx < inputVector.size(); idx++) {
            for(int i = 0; i < implicationsCount; i++) {
                int absIdx = (idx * implicationsCount) + i;
                float c = c_values.get(absIdx);
                double sigma = Utils.getSigma(c, c_values);
                FirstLevelNeuron neuron = new FirstLevelNeuron(c, sigma, idx, i);
                firstLevelNeurons.add(neuron);
            }
        }
    }

    private void initSecondLevel() {
        for(int i = 0; i < implicationsCount; i++) {
            SecondLevelNeuron neuron = new SecondLevelNeuron(i);
            secondLevelNeurons.add(neuron);
        }
    }

    private void initThirdLevel() {
        thirdLevelNeurons.add(new Aggregator());
        thirdLevelNeurons.add(new Normalizer());
    }

    private void initFourthLevel() {
        fourthLevelNeurons.add(new FourthLevelNeuron());
    }
}
