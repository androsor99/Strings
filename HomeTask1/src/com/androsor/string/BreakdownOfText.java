package com.androsor.string;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create an application that parses the text (the text is stored in a line)
 * and allows you to perform three different operations with the text: sort paragraphs by the number of sentences;
 * sort words by length in each sentence; sort lexemes in a sentence in descending order of the number of occurrences
 * of a given character, and in case of equality - alphabetically.
 */
public class BreakdownOfText {

    private static final String TEXT = """
            \tПервыйц абзац? Три предложения. Три!
            \tВторой абзац. Пять предложений. Это третье предложение. Это четвертое. И это пятое...
            \tТретий абзац и всего одно предложение.!
            \tЧетвертый абзац и семь предложений. Второе. Третье. Четвертое. Пятое. Шестое? Седьмое!
            \tПятый абзац, и снова одно предложение.
            \tШестой абзац. Два предложения и всякая фигня непотребная.
            \tццццц цц ц ццц.
            """;
    private static final Pattern PARAGRAPHS_PATTERN = Pattern.compile("(\\t*.+\\n+)");
    private static final Pattern SENTENCES_PATTERN = Pattern.compile("([^.!?]+[.!?])");
    private static final Pattern WORD_PATTERN = Pattern.compile("([0-9А-Яа-я-]+)");
    private static final Pattern FINISH_WORD_PATTERN = Pattern.compile("([.!?])");

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
                case "4" -> System.out.println(sortWordsOfTextByCountSymbolsInWord(TEXT, 'ц'));
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
        List<String> paragraphs = getListOfTextItems(string, PARAGRAPHS_PATTERN);
        paragraphs.sort(new Comparator<>() {
            @Override
            public int compare(String o1, String o2) {
                return getListOfTextItems(o1, SENTENCES_PATTERN).size() - getListOfTextItems(o2, SENTENCES_PATTERN).size();
            }
        });
        StringBuilder stringBuilder = new StringBuilder();
        for (String paragraph : paragraphs) {
            stringBuilder.append(paragraph);
        }
        return stringBuilder.toString();
    }

    private static List<String> getListOfTextItems(String string, Pattern pattern) {
        List<String> items = new ArrayList<>();
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            items.add(matcher.group());
        }
        return items;
    }

    private static String sortWordsOfTextByLength(String string) {
        List<String> paragraphs = getListOfTextItems(string, PARAGRAPHS_PATTERN);
        StringBuilder stringBuilder = new StringBuilder();
        for (String paragraph : paragraphs) {
            List<String> sentences = getListOfTextItems(paragraph, SENTENCES_PATTERN);
            stringBuilder.append('\t');
            for (String sentence : sentences) {
                stringBuilder.append(sortWordInSentenceByLength(sentence));
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    private static String sortWordInSentenceByLength(String string) {
        List<String> words = getListOfTextItems(string, WORD_PATTERN);
        words.sort(new Comparator<>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        return concatenateWord(words) + getFinishSymbol(string);
    }

    private static String concatenateWord(List<String> words) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            stringBuilder.append(" ");
            if (i == 0) {
                stringBuilder.append(replaceFirstUpperCase(words.get(i)));
            } else {
                stringBuilder.append(words.get(i).toLowerCase());
            }
        }
        return stringBuilder.toString();
    }

    private static String replaceFirstUpperCase(String word){
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    private static String getFinishSymbol(String string){
        Matcher matcher = FINISH_WORD_PATTERN.matcher(string);
        String punctuation = "";
        while (matcher.find()) {
            punctuation = matcher.group();
        }
        return punctuation;
    }

    private static String sortWordsOfTextByCountSymbolsInWord(String string, char symbol) {
        List<String> paragraphs = getListOfTextItems(string, PARAGRAPHS_PATTERN);
        StringBuilder stringBuilder = new StringBuilder();
        for (String paragraph : paragraphs) {
            List<String> sentences = getListOfTextItems(paragraph, SENTENCES_PATTERN);
            stringBuilder.append('\t');
            for (String sentence : sentences) {
                    stringBuilder.append(sortWordInSentenceByCountSymbols(sentence, symbol));
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    public static String sortWordInSentenceByCountSymbols(String string, char symbol){
        List<String> words = getListOfTextItems(string, WORD_PATTERN);
        words.sort(new Comparator<>() {
            @Override
            public int compare(String o1, String o2) {
                if (getNumberOccurrencesOfCharacter(o1, symbol) < getNumberOccurrencesOfCharacter(o2, symbol)) return 1;
                if (getNumberOccurrencesOfCharacter(o1, symbol) == getNumberOccurrencesOfCharacter(o2, symbol)) return o1.compareToIgnoreCase(o2);
                return -1;
            }
        });
        return concatenateWord(words) + getFinishSymbol(string);
    }

    private static long getNumberOccurrencesOfCharacter(String string, char symbol) {
        return string.chars().filter(ch -> ch == symbol).count();
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
