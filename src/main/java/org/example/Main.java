package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите алгебраическое выражение, содержащее скобки, операции сложения, вычитания, умножения и деления.");
        System.out.println("Вы можете ввести римские или арабские числа. Каждый символ должен отделяться пробелом");
        Scanner scanner = new Scanner(System.in);
        String expression  = scanner.nextLine();
        Calculator.calc(expression);
    }
}