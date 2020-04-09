package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static String getStringFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        return new String(data, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws IOException {
        String file = getStringFromFile("input.txt");

        file = file.replaceAll("[\n\r]", "");

        String[] stringArrays = file.split(";");
        List<Array> arrays = new ArrayList<>();

        for (String array : stringArrays) {
            String[] words = array.split(" ");
            String name = words[1].replaceAll("\\[\\]", "");
            int size = 1;

            if (array.contains("{")) {
                Pattern pattern = Pattern.compile("\\{.*?\\}");
                Matcher matcher = pattern.matcher(array);

                String stringElements = "";

                while (matcher.find()) {
                    stringElements = array.substring(matcher.start(), matcher.end());
                }

                stringElements = stringElements.replaceAll(" ", "");

                if (stringElements.equals("{}")) {
                    size = 0;
                }
                else {
                    size = stringElements.split(",").length;
                }
            }
            else {
                Pattern pattern = Pattern.compile("\\[[0-9]+?\\]");
                Matcher matcher = pattern.matcher(words[4]);

                while (matcher.find()) {
                    size *= Integer.parseInt(words[4].substring(matcher.start() + 1, matcher.end() - 1));
                }
            }

            arrays.add(new Array(name, size));
        }

        arrays.sort(Array::compareTo);

        FileWriter output = new FileWriter(new File("output.txt"));

        for (Array array : arrays) {
            output.write(array.getName() + "-" + array.getSize() + "\n");
        }

        output.close();
    }
}
