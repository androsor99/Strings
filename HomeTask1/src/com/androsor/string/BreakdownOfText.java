package com.androsor.string;

import static com.androsor.string.IOHelper.enterStringFromConsole;
import static com.androsor.string.IOHelper.printOptions;

/**
 * Create an application that parses the text (the text is stored in a line)
 * and allows you to perform three different operations with the text: sort paragraphs by the number of sentences;
 * sort words by length in each sentence; sort lexemes in a sentence in descending order of the number of occurrences
 * of a given character, and in case of equality - alphabetically.
 */
public class BreakdownOfText {

    private static final String TEXT = """
            \t.Пер.вый абзац? Три, предложения!!!!!. Три!
            \tВторой, абзац. Пять предложений. Это третье предложение. Это четвертое. Итрооооо это пятое...
            \tТретий абзац, и всего одно предложение:!
            \tЧетвертый абзац и семьсемьсемь предл.ожений!!! Второе. Третье. Четвертое. Пятое. Шестое? Седьмое!
            \tПятый абзац, и снова одно предложение.
            \tШестой абзац. Два предложения и всякая фигня непотребная.
            \tцццацц цец ц ццц.
            """;
    private static final String INVALID_COMMAND = "Неверная команда";
    private static final char SYMBOL = 'е';
    private static final TextSorter TEXT_SORTER = new TextSorter();

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
                case "2" -> System.out.println(TEXT_SORTER.sortTextBySizeParagraphs(TEXT));
                case "3" -> System.out.println(TEXT_SORTER.sortWordsOfTextByLength(TEXT));
                case "4" -> System.out.println(TEXT_SORTER.sortWordsOfTextByCountSymbolsInWord(TEXT, SYMBOL));
                case "9" -> System.exit(9);
                default -> System.out.println(INVALID_COMMAND);
            }
            System.out.println("---------------------------");
        }
    }
}
