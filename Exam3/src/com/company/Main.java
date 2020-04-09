package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    private static String getStringFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        return new String(data, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(new FileReader("input2.in"));
        FileWriter output1 = new FileWriter("output1.txt");
        FileWriter output2 = new FileWriter("output2.txt");
        FileWriter output3 = new FileWriter("output3.txt");

	    String text = getStringFromFile("input1.html").toLowerCase();
        String textWithoutTeg = text.replaceAll("<.+?>", "");
	    List<String> linesOfText = Arrays.asList(textWithoutTeg.split("\r\n"));
        List<String> rightLines = Arrays.asList(scanner.nextLine().toLowerCase().split(";"));

        Pattern pattern = Pattern.compile("<[^/].*?>");
        Matcher matcher = pattern.matcher(text);

        List<String> stringTegs = new ArrayList<>();

        while (matcher.find()) {
            stringTegs.add(text.substring(matcher.start(), matcher.end()));
        }

        stringTegs = stringTegs.stream().distinct().collect(Collectors.toList());

        List<Teg> tegs = new ArrayList<>();

        for (String tegName : stringTegs) {
            tegs.add(new Teg(tegName));
        }

        MyRunnable myRunnable = new MyRunnable(tegs);
        Thread myThread = new Thread(myRunnable);
        myThread.start();

        for (String rightLine : rightLines) {
            output2.write(rightLine + ": ");
            int i;

            for (i = 0; i < linesOfText.size(); i++) {
                if (linesOfText.get(i).contains(rightLine)) {
                    output2.write(i + "\n");

                    break;
                }
            }

            if (i == linesOfText.size()) {
                output2.write("-1");
                output3.write(rightLine + "\n");
            }
        }

        myThread.join();

        for (Teg teg : tegs) {
            output1.write(teg.toString() + " ");
        }

        output1.close();
        output2.close();
        output3.close();
    }
}
