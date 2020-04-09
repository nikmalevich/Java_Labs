package com.company;

public class Teg implements Comparable<Teg>{
    private String name;

    public Teg(String name) {
        this.name = String.valueOf(name);
    }

    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Teg o) {
        return (this.name.length() - o.name.length());
    }
}
