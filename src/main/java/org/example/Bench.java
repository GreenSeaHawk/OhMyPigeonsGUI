package org.example;

public class Bench {
    private int numPigeons = 3;

    public int getNumPigeons() {
        return numPigeons;
    }

    public void addPigeons(int num) {
        numPigeons += num;
    }

    public void subtractPigeons(int num) {
        numPigeons = Math.max(0, numPigeons - num);
    }

    public void setNumberPigeons(int num) {
        numPigeons = num;
    }
}
