package com.wangmendel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class Teacher {

    private  Map<String, Integer> namesMapping = new LinkedHashMap<String, Integer>();
    private Map<List<Float>, Integer> learningValues = new LinkedHashMap<List<Float>, Integer>();
    private List<Cluster> clusters = new ArrayList<Cluster>();

    public Teacher(String filePath) {
        File file = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                List<Float> vectorElements = new ArrayList<Float>();
                String description = "NO_DESCRIPTION";
                for(String elem : elements) {
                    if(Pattern.compile("[0-9].+").matcher(elem).matches()) {
                        vectorElements.add(Float.parseFloat(elem));
                    } else {
                        description = elem;
                    }
                }
                int maxIdx;
                if(namesMapping.values().isEmpty()) {
                    maxIdx = 0;
                } else {
                    maxIdx = Collections.max(namesMapping.values());
                }
                if(!namesMapping.containsKey(description)) {
                    namesMapping.put(description, maxIdx + 1);
                }
                learningValues.put(vectorElements, namesMapping.get(description));
            }
            System.out.println("Complete");
        } catch (IOException ex) {
            System.out.println("FAIL reading file");
        }
    }

    public void performLearning() throws IOException {
        final double limitDistance = 0.5; // задать какое-то своё

        for(Map.Entry<List<Float>, Integer> learningSetEntry : learningValues.entrySet()) {
            if(clusters.size() == 0) {
                clusters.add(new Cluster(
                        learningSetEntry.getKey(), 1, learningSetEntry.getValue()));
            } else {
                Cluster nearest = null;
                double minDist = 99999;
                for(Cluster currentCluster : clusters) {
                    double dist = getVectorsDistance(currentCluster.getC(), learningSetEntry.getKey());
                    if(dist < minDist) {
                        minDist = dist;
                        nearest = currentCluster;
                    }
                }
                if(minDist > limitDistance) {
                    clusters.add(new Cluster(
                            learningSetEntry.getKey(), 1, learningSetEntry.getValue()));
                } else {
                    nearest.setW(nearest.getW() + learningSetEntry.getValue());
                    int Lprev = nearest.getL();
                    nearest.setL(Lprev + 1);
                    nearest.setC(
                            getDivToScalar(
                                    getSum(
                                            getMultWithScalar(nearest.getC(), Lprev),
                                            learningSetEntry.getKey()),
                                    nearest.getL()));
                }
            }
        }
    }

    private List<Float> getDivToScalar(List<Float> vec, int scalar) throws IOException {
        List<Float> result = new ArrayList<Float>();
        for(float element : vec) {
            if(scalar == 0) {
                throw new IOException("Divide by zero!");
            } else {
                result.add(element / scalar);
            }
        }
        return result;
    }

    private List<Float> getMultWithScalar(List<Float> vec, int scalar) {
        List<Float> result = new ArrayList<Float>();
        for(float element : vec) {
            result.add(element * scalar);
        }
        return result;
    }

    private List<Float> getSum(List<Float> vec1, List<Float> vec2) throws IOException {
        List<Float> result = new ArrayList<Float>();
        if(vec1 != null && vec2 != null) {
            if(vec1.size() == vec2.size()) {
                for(int i = 0; i < vec1.size(); i++) {
                    result.add(vec1.get(i) + vec2.get(i));
                }
            } else {
                throw new IOException("Arrays sizes NOT equal");
            }
        } else {
            throw new IOException("NULL arrays found");
        }
        return result;
    }

    private double getVectorsDistance(List<Float> vec1, List<Float> vec2) throws IOException {
        if(vec1 != null && vec2 != null) {
            if(vec1.size() == vec2.size()) {
                float sum = 0;
                for(int i = 0; i < vec1.size(); i++) {
                    sum += (vec1.get(i)-vec2.get(i))*(vec1.get(i)-vec2.get(i));
                }
                return Math.sqrt(sum);
            } else {
                throw new IOException("Arrays sizes NOT equal");
            }
        } else {
            throw new IOException("NULL arrays found");
        }
    }

    public Map<String, Integer> getNamesMapping() {
        return namesMapping;
    }

    public Map<List<Float>, Integer> getLearningValues() {
        return learningValues;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }
}
