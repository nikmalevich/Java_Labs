package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
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

    private static String getGoodString(String badString) {
        Pattern pattern = Pattern.compile("(\".*?\")|(\'.*?\')|(/\\*(.|\\s)*?\\*/)|(//.*?[\n\r])");
        Matcher matcher = pattern.matcher(badString);

        StringBuilder result = new StringBuilder();
        int start = 0;


        while (matcher.find()) {
            if (matcher.group(3) != null || matcher.group(5) != null) {
                result.append(badString, start, matcher.start());

                start = matcher.end();
            }
            if (matcher.group(5) != null) {
                result.append(badString, start, matcher.start());

                start = matcher.end();
            }
        }

        result.append(badString.substring(start));

        return result.toString();
    }

    public static void main(String[] args) throws IOException {
        String badString = Main.getStringFromFile("input.txt");

        String goodString = Main.getGoodString(badString);

        System.out.println(goodString);
    }
}