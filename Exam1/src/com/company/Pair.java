package com.company;

public class Pair implements Comparable<Pair> {
    private long first;
    private String second;

    Pair (long first, String second) {
        this.first = first;
        this.second = String.valueOf(second);
    }

    public long getFirst() {
        return first;
    }

    public String getSecond() {
        return String.valueOf(second);
    }

    @Override
    public int compareTo(Pair o) {
        return (int) (o.first - this.first);
    }
}
