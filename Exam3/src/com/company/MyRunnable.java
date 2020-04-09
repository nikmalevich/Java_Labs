package com.company;

import java.util.List;

public class MyRunnable implements Runnable {
    private List<Teg> array;

    public MyRunnable(List<Teg> array){
        this.array = array;
    }

    @Override
    public void run() {
        array.sort(Teg::compareTo);
    }
}
