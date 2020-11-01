package com.androsor.string;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Create an application that parses the text (the text is stored in a line)
 * and allows you to perform three different operations with the text: sort paragraphs by the number of sentences;
 * sort words by length in each sentence; sort lexemes in a sentence in descending order of the number of occurrences
 * of a given character, and in case of equality - alphabetically.
 */

public class BreakdownOfText {

    public static void main(String[] args) {

        String command;

        do {

            System.out.println("Введите \"1\" чтобы отсортировать  абзацы  по  количеству  предложений");
            System.out.println("Введите \"2\" чтобы в  каждом  предложении  отсортировать  слова  по  длине");
            System.out.println("Введите \"3\" чтобы отсортировать лексемы в предложении по убыванию количества вхождений заданного символа, а в случае равенства – по алфавиту");
            System.out.println("Введите \"0\" чтобы напечатать исходный текст");
            System.out.println("Введите \"9\" чтобы завершить программу");

            command = getStrFromCons();
            System.out.println("---------------------------");

            switch (command) {

                case "1" ->
                    sortParagraphs(text);


                case "2" ->

                    sortSentence(text);


                case "3" ->

                    sortWord(text);


                case "0" ->
                    System.out.println(text);


            }
            System.out.println("---------------------------");

        } while (!command.equals("9"));
    }


    public static String getStrFromCons() {

        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNextLine()) {
            System.out.println("---------------------------");
            System.out.println("Введите значение");
        }

        return sc.nextLine();
    }

    public static void sortParagraphs(String text) {

        String [] paragraphs;
        paragraphs = text.split("\n");

        // массив с количеством предложений.
        int[] counterSentences;
        counterSentences = new int[paragraphs.length];
        int maxLengthParagraph = 0;

        for (int i = 0; i < paragraphs.length; i++) {

            String [] sentences;
            sentences = splitSentences(paragraphs[i]);
            counterSentences[i] = sentences.length;

            if (maxLengthParagraph < sentences.length) {
                maxLengthParagraph = sentences.length;
            }
        }

        //вывод от самого короткого абазаца до самого длинного
        StringBuilder sb;
        sb = new StringBuilder();

        for (int i = 1; i <= maxLengthParagraph; i++) {

            for (int j = 0; j < counterSentences.length; j++) {

                if (i == counterSentences[j]) {

                    sb.append("\t");
                    sb.append(paragraphs[j].trim());
                    sb.append("\n");
                }
            }
        }

        System.out.println(sb.toString());
    }

    // разбивка на предложения.
    public static String [] splitSentences(String text) {

        Pattern pattern;
        pattern = Pattern.compile("\\.*[.!?]\\s*");

        return pattern.split(text);
    }

    public static void sortSentence(String text) {

        String [] paragraphs; // масив с обзацами.
        paragraphs = text.split("\n");

        for (String paragraph : paragraphs) {

            String [] sentences;// массив спредложениями.
            sentences = splitSentences(paragraph);

            for (String sentence : sentences) {

                String [] words; // массив со словами.
                words = splitWords(sentence);

                //сортировка слов
                for (int k = words.length - 1; k >= 0; k--) {
                    for (int m = 0; m < k; m++) {
                        if (words[m].length() > words[m + 1].length()) {
                            String tmp = words[m];
                            words[m] = words[m + 1];
                            words[m + 1] = tmp;
                        }
                    }
                }

                //вывод слов
                for (String  word : words) {
                    System.out.print(word + " ");
                }

                System.out.print("\b. ");
            }

            System.out.println();
        }

        System.out.println();
    }

    // разбивка на слова.
    public static String [] splitWords(String sentence) {
        Pattern pattern;
        pattern = Pattern.compile("\\s*(\\s|,|;|:)\\s*");
        return pattern.split(sentence);
    }

    public static void sortWord(String text) {
        System.out.println("Введите символ:");
        String strIn = getStrFromCons();

        String [] paragraphs = text.split("\n");
        for (String paragraph : paragraphs) {
            String[] sentences = splitSentences(paragraph);
            for (String sentence : sentences) {
                String[] words = splitWords(sentence);

                //сортировка лексем
                for (int k = words.length - 1; k >= 0; k--) {
                    for (int m = 0; m < k; m++) {
                        int countRight = 0;
                        int countLeft = 0;
                        for (int n = 0; n < words[m].length(); n++) { //считаем количество вхождений
                            if (String.valueOf(words[m].charAt(n)).compareToIgnoreCase(strIn) == 0) {
                                countLeft++;
                            }
                        }
                        for (int n = 0; n < words[m + 1].length(); n++) {   //считаем количество вхождений следующего символа
                            if (String.valueOf(words[m + 1].charAt(n)).compareToIgnoreCase(strIn) == 0) {
                                countRight++;
                            }
                        }
                        if (countLeft < countRight) {   //сравниваем количесво вхождений
                            String tmp = words[m];
                            words[m] = words[m + 1];
                            words[m + 1] = tmp;
                        } else if (countLeft == countRight) { //если количество вхождений равно, сортировка по алфавиту
                            String [] forCompare = {words[m], words[m + 1]};
                            Arrays.sort(forCompare);
                            words[m] = forCompare[0];
                            words[m + 1] = forCompare[1];
                        }
                    }
                }

                //вывод слов
                for (String  word : words) {
                    System.out.print(word + " ");
                }
                System.out.print("\b. ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static final String text = "Первый абзац. Три предложения. Три!\n" +
            "Второй абзац. Пять предложений. Это третье предложение. Это четвертое. И это пятое...\n" +
            "Третий абзац и всего одно предложение.\n" +
            "Четвертый абзац и семь предложений. Второе. Третье. Четвертое. Пятое. Шестое? Седьмое.\n" +
            "Пятый абзац и снова одно предложение.\n" +
            "Шестой абзац. Два предложения и всякая фигня непотреьная.";
}
