package com.wangmendel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {

    public static List<Float> getDistances(float c, List<Float> c_values) {
        List<Float> result = new ArrayList<Float>();
        for(float currentC : c_values) {
            float dist = Math.abs(c - currentC);
            result.add(dist);
        }
        return result;
    }

    public static double getSigma(float c, List<Float> c_values) {
        final int R = 4; // R обычно берут принадлежащим [3; 5]
        float sum = 0;
        List<Float> distances = getDistances(c, c_values);
        Collections.sort(distances);
        // TODO maybe shit
        for(float d : distances) {
            if(d == 0) {
                continue;
            }
            for (int i = 0; i < R; i++) {
                float dist = distances.get(i);
                sum += dist * dist;
            }
        }
        return Math.sqrt(sum / R);
    }
}
