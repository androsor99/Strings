package com.androsor.string;

/**
 * Write an analyzer that allows you to sequentially return the contents of the nodes of an xml document and its type
 * (opening tag, closing tag, tag content, tag without body). You cannot use ready-made XML parsers to solve this problem.
 */
public class Test {

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
    private static final AnalyzerXML ANALYSER = new AnalyzerXML();

    public static void main(String[] args) {
        System.out.println(ANALYSER.analyzeXML(XML));
    }
}
