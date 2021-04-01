package com.androsor.string;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextSorter {

    private static final Pattern PARAGRAPHS_PATTERN = Pattern.compile("(\\t*.+\\n+)");
    private static final Pattern SENTENCES_PATTERN = Pattern.compile("([^.!?]+[.!?])");
    private static final Pattern WORD_PATTERN = Pattern.compile("([0-9А-Яа-я-]+)");
    private static final Pattern FINISH_WORD_PATTERN = Pattern.compile("([.!?]+)");

    public String sortTextBySizeParagraphs(String text) {
        List<String> paragraphs = getListOfTextItems(text, PARAGRAPHS_PATTERN);
        paragraphs.sort(Comparator.comparingInt(o -> getListOfTextItems(o, SENTENCES_PATTERN).size()));
        StringBuilder stringBuilder = new StringBuilder();
        paragraphs.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

    private List<String> getListOfTextItems(String text, Pattern pattern) {
        List<String> items = new ArrayList<>();
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            items.add(matcher.group());
        }
        return items;
    }

    public String sortWordsOfTextByLength(String text) {
        List<String> paragraphs = getListOfTextItems(text, PARAGRAPHS_PATTERN);
        StringBuilder stringBuilder = new StringBuilder();
        for (String paragraph : paragraphs) {
            List<String> sentences = getListOfTextItems(paragraph, SENTENCES_PATTERN);
            stringBuilder.append('\t');
            for (String sentence : sentences) {
                stringBuilder.append(sortWordInSentenceByLength(sentence)).append(" ");
            }
            stringBuilder.append("\b\n");
        }
        return stringBuilder.toString();
    }

    private String sortWordInSentenceByLength(String text) {
        List<String> words = getListOfTextItems(text, WORD_PATTERN);
        words.sort(Comparator.comparingInt(String::length));
        return concatenateWord(words) + getFinishSymbol(text);
    }

    private String concatenateWord(List<String> words) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(replaceFirstUpperCase(words.get(0)));
        for (int i = 1; i < words.size(); i++) {
            stringBuilder.append(" ");
            stringBuilder.append(words.get(i).toLowerCase());
        }
        return stringBuilder.toString();
    }

    private String replaceFirstUpperCase(String word){
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    private String getFinishSymbol(String string){
        Matcher matcher = FINISH_WORD_PATTERN.matcher(string);
        String punctuation = "";
        while (matcher.find()) {
            punctuation = matcher.group();
        }
        return punctuation;
    }

    public String sortWordsOfTextByCountSymbolsInWord(String text, char symbol) {
        List<String> paragraphs = getListOfTextItems(text, PARAGRAPHS_PATTERN);
        StringBuilder stringBuilder = new StringBuilder();
        for (String paragraph : paragraphs) {
            List<String> sentences = getListOfTextItems(paragraph, SENTENCES_PATTERN);
            stringBuilder.append('\t');
            for (String sentence : sentences) {
                stringBuilder.append(sortWordInSentenceByCountSymbols(sentence, symbol)).append(" ");
            }
            stringBuilder.append("\b\n");
        }
        return stringBuilder.toString();
    }

    private String sortWordInSentenceByCountSymbols(String sentence, char symbol){
        List<String> words = getListOfTextItems(sentence, WORD_PATTERN);
        words.sort((o1, o2) -> {
            if (getNumberOccurrencesOfCharacter(o1, symbol) < getNumberOccurrencesOfCharacter(o2, symbol)) {
                return 1;
            }
            else if (getNumberOccurrencesOfCharacter(o1, symbol) == getNumberOccurrencesOfCharacter(o2, symbol)) {
                return o1.compareToIgnoreCase(o2);
            }
            else {
                return -1;
            }
        });
        return concatenateWord(words) + getFinishSymbol(sentence);
    }

    private long getNumberOccurrencesOfCharacter(String word, char symbol) {
        return word.chars().filter(ch -> ch == symbol).count();
    }
}
