package com.company;

public class Vertex {
    private int num;
    private int cost;
    private int previous;
    private boolean check;

    public Vertex(int num) {
        this.num = num;
        this.cost = Integer.MAX_VALUE;
        this.previous = 0;
        this.check = false;
    }

    public void check() {
        check = true;
    }

    public void change(int cost, int previous) {
        this.cost = cost;
        this.previous = previous;
    }

    public int getCost() {
        return cost;
    }

    public int getNum() {
        return num;
    }

    public boolean getCheck() {
        return check;
    }

    public int getPrevious() {
        return previous;
    }
}