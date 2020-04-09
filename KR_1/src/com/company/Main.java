package com.company;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new FileReader(new File("input2.txt")));
        HashMap<String, Boolean> variables = new HashMap<>();

        while (scanner.hasNext()) {
            String nameVar = scanner.next().toLowerCase();
            scanner.next();
            boolean valueVar = scanner.next().toLowerCase().equals("true");

            variables.put(nameVar, valueVar);
        }

        scanner = new Scanner(new FileReader(new File("input1.txt")));

        List<List<String>> expressions = new ArrayList<>();

        while (scanner.hasNext()) {
            String line = scanner.nextLine();

            expressions.add(Arrays.asList(line.split(";")));
        }

        FileWriter output = new FileWriter(new File("output.txt"));

        for (int i = 0; i < expressions.size(); i++) {
            for (String expression : expressions.get(i)) {
                List<String> wordArray = Arrays.asList(expression.split(" "));

                Stack<String> charStack = new Stack<>();
                StringBuilder polishResult = new StringBuilder();

                for (String word : wordArray) {
                    if (word.equals("")) {
                        continue;
                    }

                    word = word.toLowerCase();

                    if (word.matches("[a-z]|[A-Z]")) {
                        polishResult.append(word);
                    }
                    else if (word.equals("true")) {
                        polishResult.append(1);
                    }
                    else if (word.equals("false")) {
                        polishResult.append(0);
                    }
                    else {
                        if (charStack.empty()) {
                            charStack.push(word);
                        }
                        else {
                            String act = charStack.peek();

                            while (getPriority(word) <= getPriority(act)) {
                                polishResult.append(getLogicSymbol(charStack.pop()));

                                if (charStack.empty()) {
                                    break;
                                }

                                act = charStack.peek();
                            }

                            charStack.push(word);
                        }
                    }
                }

                while (!charStack.empty()) {
                    polishResult.append(getLogicSymbol(charStack.pop()));
                }

                Stack<Boolean> booleanStack = new Stack<>();

                for (int k = 0; k < polishResult.length(); k++) {
                    String symbol = polishResult.substring(k, k + 1);

                    if (symbol.matches("[a-z]|[A-Z]")) {
                        booleanStack.push(variables.get(symbol));
                    }
                    else if (symbol.equals("1")) {
                        booleanStack.push(true);
                    }
                    else if (symbol.equals("0")) {
                        booleanStack.push(false);
                    }
                    else {
                        boolean result;

                        if (symbol.equals("!")) {
                            result = !booleanStack.pop();
                        }
                        else {
                            boolean var2 = booleanStack.pop();
                            boolean var1 = booleanStack.pop();

                            if (symbol.equals("&")) {
                                result = var1 & var2;
                            }
                            else {
                                result = var1 | var2;
                            }
                        }

                        booleanStack.push(result);
                    }
                }

                output.write(expression + "=" + booleanStack.pop().toString() + "\n");
            }
        }

        output.flush();
    }

    public static int getPriority(String act) {
        if (act.equals("not")) {
            return 3;
        }
        else if (act.equals("and")) {
            return 2;
        }
        else {
            return 1;
        }
    }

    public static String getLogicSymbol(String word) {
        if (word.equals("not")) {
            return "!";
        }
        else if (word.equals("and")) {
            return "&";
        }
        else {
            return "|";
        }
    }
}
