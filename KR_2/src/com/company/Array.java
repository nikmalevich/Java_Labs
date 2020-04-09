package com.company;

public class Array implements Comparable<Array> {
    private String name;
    private int size;

    public Array(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public int compareTo(Array o) {
        return o.size - this.size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
}
