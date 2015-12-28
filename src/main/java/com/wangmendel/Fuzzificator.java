package com.wangmendel;

public class Fuzzificator {

    public static double getGaussian(float x, float c, float b, double sigma) {
        return Math.exp(-Math.pow((x - c), 2 * b) / sigma);
    }
}
