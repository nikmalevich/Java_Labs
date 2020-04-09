package com.company;

import java.util.Stack;

public class Main {

    public static void main(String[] args) {
	    String input = "342*15-2^/+";

        Stack<Double> intStack = new Stack<>();

        String regExNumeral = "[0-9]";

        for (int i = 0; i < input.length(); i++) {
            String symbol = input.substring(i, i + 1);

            if (symbol.matches(regExNumeral)) {
                intStack.push((double) Integer.parseInt(symbol));
            }
            else {
                double result;
                double num2 = intStack.pop();
                double num1 = intStack.pop();

                if (symbol.equals("+")) {
                    result = num1 + num2;
                }
                else if (symbol.equals("*")) {
                    result = num1 * num2;
                }
                else if (symbol.equals("^")) {
                    result = (int) Math.pow(num1, num2);
                }
                else if (symbol.equals("-")) {
                    result = num1 - num2;
                }
                else {
                    result = num1 / num2;
                }

                intStack.push(result);
            }
        }

        System.out.println(intStack.pop());

        input = "3+4*2/(1-5)^2";

        Stack<Character> charStack = new Stack<>();

        StringBuilder polishResult = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            String symbol = input.substring(i, i + 1);

            if (symbol.matches(regExNumeral)) {
                polishResult.append(symbol);
            }
            else {
                if (charStack.empty()) {
                    charStack.push(symbol.charAt(0));
                }
                else if (symbol.equals(")")) {
                    while (charStack.peek() != '(') {
                        polishResult.append(charStack.pop());
                    }

                    charStack.pop();
                }
                else {
                    Character act = charStack.peek();

                    while (getPriority(symbol) <= getPriority(act.toString()) && !symbol.equals("(")) {
                        polishResult.append(charStack.pop());

                        if (charStack.empty()) {
                            break;
                        }

                        act = charStack.peek();
                    }

                    charStack.push(symbol.charAt(0));
                }
            }
        }

        while (!charStack.empty()) {
            polishResult.append(charStack.pop());
        }

        System.out.println(polishResult);
    }

    public static int getPriority(String act) {
        if (act.equals("(")) {
            return 0;
        }
        else if (act.matches("[+\\-]")) {
            return 1;
        }
        else if (act.matches("[*/]")) {
            return 2;
        }
        else if (act.equals("^")) {
            return 3;
        }
        else {
            return -1;
        }
    }
}
