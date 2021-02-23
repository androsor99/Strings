package com.androsor.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Write an analyzer that allows you to sequentially return the contents of the nodes of an xml document and its type
 * (opening tag, closing tag, tag content, tag without body). You cannot use ready-made XML parsers to solve this problem.
 */
public class XMLAnalyzer {

    private static final String XML = """
                <notes>
                    <note id = "1">
                        <to>Вася</to>
                        <from>Света</from>
                        <heading>Напоминание</heading>
                        <body>Позвони мне завтра!</body>
                    </note>
                    <note id = "2">
                        <to>Петя</to>
                        <from>Маша</from>
                        <heading>Важное напоминание</heading>
                        <body/>
                    </note>
                 </notes>
                """;

    private static final Pattern OPENING_TAG = Pattern.compile("<\\w.+?>");
    private static final Pattern CLOSING_TAG = Pattern.compile("</\\w+>");
    private static final Pattern BODY_TAG = Pattern.compile(">.+?<");
    private static final Pattern NO_BODY_TAG = Pattern.compile("<\\w.+?/>");

    public static void main(String[] args) {

        System.out.println(analyzerXML(XML));
    }

    public static String analyzerXML(String xml) {
        StringBuilder strBuilder = new StringBuilder();
        String[] lines = xml.split("\n\\s*");
        for (String line : lines) {
            Matcher openingTag = OPENING_TAG.matcher(line);
            Matcher closingTag = CLOSING_TAG.matcher(line);
            Matcher bodyTag = BODY_TAG.matcher(line);
            Matcher noBodyTag = NO_BODY_TAG.matcher(line);
            if (noBodyTag.find()) {
                strBuilder.append(noBodyTag.group());
                strBuilder.append(" - тег без тела\n");
            }
            if (openingTag.find()) {
                strBuilder.append(openingTag.group());
                strBuilder.append(" - открывающий тег\n");
            }
            if (bodyTag.find()) {
                strBuilder.append(bodyTag.group().substring(1));
                strBuilder.append("\b - содержимое тега\n");
            }
            if (closingTag.find()) {
                strBuilder.append(closingTag.group());
                strBuilder.append(" - закрывающий тег\n");
            }
        }
        return strBuilder.toString();
    }
}
