package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Matrix {
    private List<List<Integer>> matrix;
    private int dim;

    public Matrix(int dim) {
        this.dim = dim;
        matrix = new ArrayList<>();

        for (int i = 0; i < this.dim; i++) {
            List<Integer> line = new ArrayList<>(Collections.nCopies(this.dim, 0));

            matrix.add(line);
        }
    }

    public void set(int first, int second, int num) {
        matrix.get(first).set(second, num);
        matrix.get(second).set(first, num);
    }

    public List<Integer> getLine(int index) {
        return matrix.get(index);
    }

    public int getDim() {
        return dim;
    }

    public void deleteColumn(int numColumn) {
        for (int i = 0; i < dim; i++) {
            matrix.get(i).set(numColumn, 0);
        }
    }
}
