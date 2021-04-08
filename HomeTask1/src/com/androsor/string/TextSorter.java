package com.androsor.string;

import java.util.Comparator;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextSorter {

    private static final Pattern PARAGRAPHS_PATTERN = Pattern.compile("\\t*.+\\n+");
    private static final Pattern SENTENCES_PATTERN = Pattern.compile(".+?[.!?:;]+\\s");
    private static final Pattern WORD_PATTERN = Pattern.compile("([^\\s]*[А-Яа-я\\d,])");
    private static final Pattern FINISH_WORD_PATTERN = Pattern.compile("([:;.!?]+)");

    public String sortTextBySizeParagraphs(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> paragraphs = getListOfTextItems(text, PARAGRAPHS_PATTERN);
        paragraphs.sort(Comparator.comparingInt(o -> getListOfTextItems(o, SENTENCES_PATTERN).size()));
        paragraphs.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

    private List<String> getListOfTextItems(String text, Pattern pattern) {
       return pattern.matcher(text).results()
                .map(MatchResult::group)
                .collect(Collectors.toList());
    }

    public String sortWordsOfTextByLength(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> paragraphs = getListOfTextItems(text, PARAGRAPHS_PATTERN);
        for (String paragraph : paragraphs) {
            List<String> sentences = getListOfTextItems(paragraph, SENTENCES_PATTERN);
            stringBuilder.append("\t");
            sentences.forEach(sentence -> stringBuilder.append(sortWordInSentenceByLength(sentence)).append(" "));
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
        StringBuilder stringBuilder = new StringBuilder();
        List<String> paragraphs = getListOfTextItems(text, PARAGRAPHS_PATTERN);
        for (String paragraph : paragraphs) {
            List<String> sentences = getListOfTextItems(paragraph, SENTENCES_PATTERN);
            stringBuilder.append('\t');
            sentences.forEach(sentence -> stringBuilder.append(sortWordInSentenceByCountSymbols(sentence, symbol)).append(" "));
            stringBuilder.append("\b\n");
        }
        return stringBuilder.toString();
    }

    private String sortWordInSentenceByCountSymbols(String sentence, char symbol){
        List<String> words = getListOfTextItems(sentence, WORD_PATTERN);
        words.sort((o1, o2) -> {
            long count1 = getNumberOccurrencesOfCharacter(o1, symbol);
            long count2 =  getNumberOccurrencesOfCharacter(o2, symbol);
            if (count1 < count2) {
                return 1;
            }
            else if (count1 == count2) {
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
