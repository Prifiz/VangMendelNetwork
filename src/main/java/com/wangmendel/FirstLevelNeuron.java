package com.wangmendel;

public class FirstLevelNeuron {
    private float c;
    private double sigma;
    private int x_idx;
    private int implicationIdx;
    private double weight;

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public FirstLevelNeuron(float c, double sigma, int x_idx, int implicationIdx) {
        this.c = c;
        this.sigma = sigma;
        this.x_idx = x_idx;
        this.implicationIdx = implicationIdx;
    }

    public void execute(float xValue) {
        this.weight = Fuzzificator.getGaussian(xValue, c, 1.0f, sigma);
    }

    public double getWeight() {
        return weight;
    }

    public float getC() {
        return c;
    }

    public double getSigma() {
        return sigma;
    }

    public int getX_idx() {
        return x_idx;
    }

    public int getImplicationIdx() {
        return implicationIdx;
    }

}
