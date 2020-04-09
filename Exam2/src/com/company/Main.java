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

    private static String shiftWord(String word, int numShifts) {
        StringBuilder result  = new StringBuilder();

        for (int i = 0; i < word.length(); i++) {
            if ((word.charAt(i) - numShifts) < 97) {
                result.append((char) (word.charAt(i) - numShifts + 26));
            }
            else {
                result.append((char) (word.charAt(i) - numShifts));
            }
        }

        return result.toString();
    }

    private static List<String> shiftWords(List<String> words, int numShifts) {
        List<String> result = new ArrayList<>();

        for (String word : words) {
            result.add(shiftWord(word, numShifts));
        }

        return result;
    }

    private static String shiftText(String text, int numShifts) {
        StringBuilder result = new StringBuilder();
        String separators = ",.?:;-! \r\n\t";

        for (int i = 0; i < text.length(); i++) {
            if (separators.indexOf(text.charAt(i)) == -1) {
                if ((text.charAt(i) - numShifts) < 97) {
                    result.append((char) (text.charAt(i) - numShifts + 26));
                }
                else {
                    result.append((char) (text.charAt(i) - numShifts));
                }
            }
            else {
                result.append(text.charAt(i));
            }
        }

        return result.toString();
    }

    public static void main(String[] args) throws IOException {
        FileWriter output = new FileWriter("output.txt");

        String encryptedText = getStringFromFile("input1.txt").toLowerCase();

        List<String> encryptedWords = Arrays.asList(encryptedText.split("[,.?:;\\-! \\r\\n\\t]"));
	    List<String> sourceWords = Arrays.asList(getStringFromFile("input2.txt").toLowerCase().split("[,.?:;\\-! \\r\\n\\t]"));

        for (String sourceWord : sourceWords) {
            if (!sourceWord.equals("")) {
                char sourceChar = sourceWord.charAt(0);

                for (String encryptedWord : encryptedWords) {
                    if (!encryptedWord.equals("")) {
                        int numShifts = encryptedWord.charAt(0) - sourceChar;

                        if (numShifts < 0) {
                            numShifts += 27;
                        }

                        if (shiftWord(encryptedWord, numShifts).equals(sourceWord)) {
                            List<String> shiftText = shiftWords(encryptedWords, numShifts);

                            int numCoincidences = 0;

                            for (String sourceWord1 : sourceWords) {
                                if (!sourceWord1.equals("") && shiftText.contains(sourceWord1)) {
                                    numCoincidences++;
                                }
                            }

                            if (numCoincidences == sourceWords.size()) {
                                output.write(shiftText(encryptedText, numShifts));

                                output.close();

                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
