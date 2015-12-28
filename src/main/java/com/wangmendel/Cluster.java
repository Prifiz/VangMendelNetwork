package com.wangmendel;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
    private List<Float> c = new ArrayList<Float>();
    private int L;
    private int w;

    public Cluster(List<Float> c, int l, int w) {
        this.c = c;
        L = l;
        this.w = w;
    }

    public List<Float> getC() {
        return c;
    }

    public void setC(List<Float> c) {
        this.c = c;
    }

    public int getL() {
        return L;
    }

    public void setL(int l) {
        L = l;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }
}
