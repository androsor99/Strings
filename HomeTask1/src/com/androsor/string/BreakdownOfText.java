package com.androsor.string;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create an application that parses the text (the text is stored in a line)
 * and allows you to perform three different operations with the text: sort paragraphs by the number of sentences;
 * sort words by length in each sentence; sort lexemes in a sentence in descending order of the number of occurrences
 * of a given character, and in case of equality - alphabetically.
 */

public class BreakdownOfText {

    private static final String TEXT = "\tПервый абзац. Три предложения. Три!\n" +
            "\tВторой абзац. Пять предложений. Это третье предложение. Это четвертое. И это пятое...\n" +
            "\tТретий абзац и всего одно предложение.\n" +
            "\tЧетвертый абзац и семь предложений. Второе. Третье. Четвертое. Пятое. Шестое? Седьмое.\n" +
            "\tПятый абзац и снова одно предложение.\n" +
            "\tШестой абзац. Два предложения и всякая фигня непотребная.\n";
    private static final Pattern PARAGRAPHS_PATTERN = Pattern.compile("(\\t*.+\\n+)");
    private static final Pattern SENTENCES_PATTERN = Pattern.compile("([^.!?]+[.!?])");
    private static final Pattern WORD_PATTERN = Pattern.compile("([А-Яа-я]+'*[А-Яа-я]+)");
    private static final Pattern FINISH_WORD_PATTERN = Pattern.compile("([.!?]+\\n*)");

    public static void main(String[] args) {

        printOptions();
        run();
    }

    private static void run() {
        while (true) {
            String command = enterStringFromConsole();
            switch (command) {
                case "0" -> printOptions();
                case "1" -> System.out.println(TEXT);
                case "2" -> System.out.println(sortStringBySizeParagraphs(TEXT));
                case "3" -> System.out.println(sortWordsOfTextByLength(TEXT));
                case "4" -> sortWord(TEXT);
                case "9" -> System.exit(9);
                default -> System.out.println(" Неверная команда");
            }
            System.out.println("---------------------------");
        }
    }

    public static String enterStringFromConsole() {
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите значение");
        return sc.nextLine();
    }

    private static String sortStringBySizeParagraphs(String string) {
        List<String> paragraphs = getParagraphs(string);
        paragraphs.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return getSentencesInParagraphs(o1).size() - getSentencesInParagraphs(o2).size();
            }
        });
        StringBuilder stringBuilder = new StringBuilder();
        for (String paragraph : paragraphs) {
            stringBuilder.append(paragraph);
        }
        return stringBuilder.toString();
    }

    private static List<String> getParagraphs(String string) {
        List<String> result = new ArrayList<>();
        Matcher matcher = PARAGRAPHS_PATTERN.matcher(string);
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    private static List<String> getSentencesInParagraphs(String string) {
        List<String> result = new ArrayList<>();
        Matcher matcher = SENTENCES_PATTERN.matcher(string);
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    private static String sortWordsOfTextByLength(String string) {
        List<String> paragraphs = getParagraphs(string);
        StringBuilder stringBuilder = new StringBuilder();
        for (String paragraph : paragraphs) {
            List<String> sentences = getSentencesInParagraphs(paragraph);
            for (String sentence : sentences){
                stringBuilder.append(sortWordInSentenceByLength(sentence));
            }
        }
        return stringBuilder.toString();
    }

    private static String sortWordInSentenceByLength(String string) {
        List<String> words = new ArrayList<>();
        Matcher matcher = WORD_PATTERN.matcher(string);
        while (matcher.find()) {
            words.add(matcher.group());
        }
        words.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            stringBuilder.append(" ");
            if (i == 0) {
                stringBuilder.append(firstUpperCase(words.get(i)));
            } else {
                stringBuilder.append(words.get(i).toLowerCase());
            }
        }
        return stringBuilder.toString() + getFinishSymbol(string);
    }

    private static String getFinishSymbol(String string){
        Matcher matcher = FINISH_WORD_PATTERN.matcher(string);
        matcher.find();
        return matcher.group();
    }

    public static String firstUpperCase(String word){
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public static void sortByParagraphs(String text) {
        String[] paragraphs = text.split("\n");

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
        String strIn = enterStringFromConsole();

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

    private static void printOptions() {
        System.out.println("Введите \"0\" чтобы увидеть список команд");
        System.out.println("Введите \"1\" чтобы напечатать исходный текст");
        System.out.println("Введите \"2\" чтобы отсортировать  абзацы  по  количеству  предложений");
        System.out.println("Введите \"3\" чтобы в  каждом  предложении  отсортировать  слова  по  длине");
        System.out.println("Введите \"4\" чтобы отсортировать лексемы в предложении по убыванию количества вхождений заданного символа, а в случае равенства – по алфавиту");
        System.out.println("Введите \"9\" чтобы завершить программу");
        System.out.println("---------------------------");
    }


}
