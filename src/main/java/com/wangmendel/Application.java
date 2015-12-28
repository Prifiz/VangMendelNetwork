package com.wangmendel;

import java.io.IOException;
import java.util.*;

public class Application {
    public static void main(String[] args) throws IOException {

        List<Float> inputVector = new ArrayList<Float>();
        inputVector.add(5.00f);
        inputVector.add(3.50f);
        inputVector.add(1.30f);
        inputVector.add(0.21f);

        Teacher teacher = new Teacher("teachingSet.txt");
        System.out.println("Naming mapping:");
        for(Map.Entry<String, Integer> entry : teacher.getNamesMapping().entrySet()) {
            System.out.println(entry.getKey() + "\t->\t" + entry.getValue());
        }
        teacher.performLearning();
        List<Cluster> clusters = teacher.getClusters();
        int M = clusters.size();
        Network network = new Network(M);
        network.setWeights(getStartWeights(inputVector, M));
        //network.setcValues(getInitCValues(inputVector, M));
        network.setcValues();
        network.init(inputVector);

        double result = network.getResult(inputVector);
        System.out.println("Result = " + result);
    }

    private static List<Float> getClustersCValues(List<Cluster> clusters) {
        List<Float> result = new ArrayList<Float>();
        for(Cluster cluster : clusters) {
            result.add(cluster.)
        }
    }

//    private static List<Float> getClustersWeights(List<Cluster> clusters) {
//        List<Float> result = new ArrayList<Float>();
//        for(Cluster cluster : clusters) {
//            result.add(cluster.getC());
//        }
//        return result;
//    }


    public static List<Float> getStartWeights(List<Float> inputVector, int implicationsCount) {
        List<Float> result = new ArrayList<Float>();
        float avgX = getAverageX(inputVector);
        Random random = new Random();
        float minOffset = 0.001f;
        float maxOffset = 0.009f;
        for(int i = 0; i < implicationsCount; i++) {
            float offsetValue = minOffset + (maxOffset - minOffset) * random.nextFloat();
            result.add(avgX + offsetValue);
        }
        return result;
    }

    public static float getInitC(List<Float> inputVector) {
        return getAverageX(inputVector);
    }

    public static float getAverageX(List<Float> inputVector) {
        float sum = 0;
        for(float value : inputVector) {
            sum += value;
        }
        return sum / inputVector.size();
    }

    public static List<Float> getInitCValues(List<Float> inputVector, int implicationsCount) {
        List<Float> result = new ArrayList<Float>();
        float initialC = getInitC(inputVector);
        for(int i = 0; i < inputVector.size()*implicationsCount; i++) {
            result.add(initialC);
        }
        return result;
    }
}
