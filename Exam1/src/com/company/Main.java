package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        String text = getStringFromFile("input.txt");
        FileWriter output1 = new FileWriter("output1.txt");
        FileWriter output2 = new FileWriter("output2.txt");
        FileWriter output3 = new FileWriter("output3.txt");

        text = text.toLowerCase();

        List<String> words = Arrays.asList(text.split("[,.?:;\\-! \\r\\n\\t]"));
        List<String> words1 = new ArrayList<>(words);

        List<Pair> wordsWithCount = new ArrayList<>();

        for (String word : words1) {
            if (!word.equals("")) {
                wordsWithCount.add(new Pair(words1.stream().filter(word::equals).count(), word));

                for (int i = words1.indexOf(word) + 1; i < words1.size(); i++) {
                    if (words1.get(i).equals(word)) {
                        words1.set(i, "");
                    }
                }
            }
        }

        wordsWithCount.sort(Pair::compareTo);

        for (Pair pair : wordsWithCount) {
            output1.write(pair.getSecond() + "(" + pair.getFirst() + ") ");
        }

        char[] separators = {',', '.', '?', ':', ';', '-', '!', ' ', '\r', '\n', '\t'};

        for (int i = 0; i < separators.length; i++) {
            int finalI = i;
            if (text.chars().filter(ch -> separators[finalI] == ch).count() == 0) {
                separators[i] = 'a';
            }
        }

        List<Character> separatorsInText = new ArrayList<>();

        for (char separator : separators) {
            if (separator != 'a') {
                separatorsInText.add(separator);
            }
        }

        separatorsInText.sort(Character::compareTo);

        for (char separator : separatorsInText) {
            output2.write(separator);
        }

        StringBuilder chars = new StringBuilder();

        text.chars().distinct().boxed().sorted((a, b) -> (b - a)).forEach((s) -> chars.append((char)(int)s));

        output3.write(chars.toString());

        output3.close();
        output2.close();
        output1.close();
    }
}
