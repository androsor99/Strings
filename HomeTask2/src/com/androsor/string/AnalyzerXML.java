package com.androsor.string;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.stream;

public class AnalyzerXML {

    private static final Pattern OPENING_TAG = Pattern.compile("(<\\w.+?>)");
    private static final Pattern CLOSING_TAG = Pattern.compile("(</\\w+>)");
    private static final Pattern BODY_TAG = Pattern.compile("[>]*>(.*?)<");
    private static final Pattern NO_BODY_TAG = Pattern.compile("(<\\w.+?/>)");
    private final Map<Pattern, String> matchers;

    public AnalyzerXML() {
        this.matchers = new HashMap<>();
        this.matchers.put(OPENING_TAG, " - открывающий тег\n");
        this.matchers.put(BODY_TAG, " - содержимое тега\n");
        this.matchers.put(NO_BODY_TAG, " - тег без тела\n");
        this.matchers.put(CLOSING_TAG, " - закрывающий тег\n");
    }

    public String analyzeXML(String xml) {
        StringBuilder strBuilder = new StringBuilder();
        String[] lines = xml.split("\n\\s*");
        stream(lines)
                .map(this::getContentsOfNodes)
                .forEach(strBuilder::append);
        return strBuilder.toString();
    }

    private String getContentsOfNodes(String line) {
        StringBuilder strBuilder = new StringBuilder();
        for (Pattern pattern : matchers.keySet()) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                strBuilder.append(matcher.group(1))
                        .append(matchers.get(pattern));
            }
        }
        return strBuilder.toString();
    }
}
