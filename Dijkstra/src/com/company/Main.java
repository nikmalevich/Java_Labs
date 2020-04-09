package com.company;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(new FileReader(new File("input.txt")));

        int numVertex = input.nextInt();
        int numEdge = input.nextInt();
        int startVertex = input.nextInt();
        int endVertex = input.nextInt();

        Matrix matrix = new Matrix(numVertex);

        for (int i = 0; i < numEdge; i++) {
            matrix.set(input.nextInt() - 1, input.nextInt() - 1, input.nextInt());
        }

        List<Vertex> vertices = new ArrayList<>();

        for (int i = 1; i < numVertex + 1; i++) {
            vertices.add(new Vertex(i));
        }

        vertices.get(startVertex - 1).change(0, startVertex);

        for (int i = 0; i < numVertex; i++) {
            int indexMinVertex = -1;
            int costMinVertex = Integer.MAX_VALUE;

            for (Vertex vertex : vertices) {
                if ((vertex.getCost() < costMinVertex) && !vertex.getCheck()) {
                    indexMinVertex = vertex.getNum() - 1;
                    costMinVertex = vertex.getCost();
                }
            }

            if (indexMinVertex == -1) {
                break;
            }

            for (int j = 0; j < matrix.getDim(); j++) {
                if (matrix.getLine(indexMinVertex).get(j) != 0) {
                    int cost = costMinVertex + matrix.getLine(indexMinVertex).get(j);

                    if (cost < vertices.get(j).getCost()) {
                        vertices.get(j).change(cost, indexMinVertex + 1);
                    }
                }
            }

            vertices.get(indexMinVertex).check();

            matrix.deleteColumn(indexMinVertex);
        }

        try {
            int indexVertex = endVertex - 1;

            StringBuilder way = new StringBuilder();

            while (indexVertex != startVertex - 1) {
                way.append((indexVertex + 1)).append(" ");

                if (vertices.get(indexVertex).getPrevious() == 0) {
                    throw new Exception("There is no way!");
                }

                indexVertex = vertices.get(indexVertex).getPrevious() - 1;
            }

            way.append(startVertex);

            System.out.println(vertices.get(endVertex - 1).getCost());
            System.out.println(way.reverse());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        input.close();
    }
}
