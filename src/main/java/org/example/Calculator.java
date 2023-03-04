package org.example;

import java.util.*;

public class Calculator {
    public static void calc(String expression) {
        try {
            switch (numberValidation(expression)) {
                case (0):
                    System.out.println("Неверный формат записи");
                    break;
                case (1):
                    System.out.println(doCalcFromPolish(toPolish(splitString(expression))));
                    break;
                case (2):
                    System.out.println(intToRoman(doCalcFromPolish(toPolish(toArabic(splitString(expression))))));
            }
        }
        catch (Exception e){
            System.out.println("Неверный ввод, поверьте пробелы и скобки");
        }

    }

    private static int numberValidation(String s) {
        String operations = "+-*/()";
        boolean roman = false;
        boolean arab = false;
        String[] arr = splitString(s);
        for (String str : arr) {
            if (!operations.contains(str)) {
                try {
                    Integer.parseInt(str);
                    arab = true;
                } catch (NumberFormatException e) {
                    roman = true;
                }
            }
        }
        if (roman && arab) {
            return 0;
        }
        if (arab) {
            return 1;
        }
        if (roman) {
            return 2;
        }
        return 0;
    }

    private static String[] splitString(String s){
        return s.trim().split(" ");
    }

    private static String[] toArabic(String[] inputArr) {
        LinkedHashMap<Character, Integer> numMap = new LinkedHashMap<>();
        numMap.put('M', 1000);
        numMap.put('D', 500);
        numMap.put('C', 100);
        numMap.put('L', 50);
        numMap.put('X', 10);
        numMap.put('V', 5);
        numMap.put('I', 1);

        String operations = "+-*/()";

        String[] resultArr = new String[inputArr.length];

        for (int i = 0; i < inputArr.length; i++) {
            if (operations.contains(inputArr[i])) {
                resultArr[i] = inputArr[i];
            } else {
                int res = 0;
                char[] number = inputArr[i].toCharArray();
                for (int j = 0; j < number.length; j++) {
                    if (j != (number.length - 1) && numMap.get(number[j]) < numMap.get(number[j + 1])) {
                        res = res + numMap.get(number[j + 1]) - numMap.get(number[j]);
                        j++;
                    } else {
                        res = res + numMap.get(number[j]);
                    }
                }
                resultArr[i] = Integer.toString(res);
            }
        }
        return resultArr;
    }


    private static ArrayList<String> toPolish(String[] arr) {
        String operations = "+-*/()";

        ArrayList<String> polishResult = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        for (String s : arr) {
            if (!operations.contains(s)) {
                polishResult.add(s);
                continue;
            }
            if (s.equals("(")) {
                stack.push(s);
                continue;
            }
            if (s.equals(")")) {
                while (!stack.peek().equals("(")) {
                    polishResult.add(stack.pop());
                }
                stack.pop();
                continue;
            }
            if (s.equals("*") || s.equals("/")) {
                while (!stack.empty() && (!stack.peek().equals("+") && !stack.peek().equals("-") && !stack.peek().equals("("))) {
                    polishResult.add(stack.pop());
                }
                stack.push(s);
                continue;
            }
            if (s.equals("+") || s.equals("-")) {
                while (!stack.empty() && !stack.peek().equals("(")) {
                    polishResult.add(stack.pop());
                }
                stack.push(s);
            }
        }
        while (!stack.empty()) {
            polishResult.add(stack.pop());
        }
        return polishResult;
    }

    private static int doCalcFromPolish(ArrayList<String> polishResult) {
        String operations = "+-*/";
        Stack<Integer> stack = new Stack<>();
        for (String s : polishResult) {
            if (!operations.contains(s)) {
                stack.push(Integer.parseInt(s));
                continue;
            }
            switch (s) {
                case ("+"):
                    int a = stack.pop();
                    int b = stack.pop();
                    stack.push(b + a);
                    break;
                case ("-"):
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(b - a);
                    break;
                case ("*"):
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(b * a);
                    break;
                case ("/"):
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(b / a);
                    break;
            }
        }
        return stack.pop();
    }

    public static String intToRoman(int num) {
        if(num>=0){
            StringBuilder result = new StringBuilder();
            LinkedHashMap<String, Integer> numMap = new LinkedHashMap<>();
            numMap.put("M",1000);
            numMap.put("CM",900);
            numMap.put("D",500);
            numMap.put("CD",400);
            numMap.put("C",100);
            numMap.put("XC",90);
            numMap.put("L",50);
            numMap.put("XL",40);
            numMap.put("X",10);
            numMap.put("IX",9);
            numMap.put("V",5);
            numMap.put("IV",4);
            numMap.put("I",1);

            for(Map.Entry<String, Integer> entry: numMap.entrySet()) {
                while (num >= entry.getValue()) {
                    result.append(entry.getKey());
                    num = num - entry.getValue();
                }
            }
            return result.toString();
        }
        else {
            return "Отрицательных чисел в римской системе не бывает";
        }
    }
}
