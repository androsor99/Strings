package com.androsor.string;

import java.util.Scanner;

public class IOHelper {

    public static void printOptions() {
        System.out.println(" Введите \"0\" чтобы увидеть список команд");
        System.out.println(" Введите \"1\" чтобы напечатать исходный текст");
        System.out.println(" Введите \"2\" чтобы отсортировать абзацы по количеству предложений");
        System.out.println(" Введите \"3\" чтобы в каждом предложении отсортировать слова по длине");
        System.out.println(" Введите \"4\" чтобы отсортировать лексемы в предложении по убыванию количества вхождений заданного символа, а в случае равенства – по алфавиту");
        System.out.println(" Введите \"9\" чтобы завершить программу");
        System.out.println("---------------------------");
    }

    public static String enterStringFromConsole() {
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите значение");
        return sc.nextLine();
    }
}
