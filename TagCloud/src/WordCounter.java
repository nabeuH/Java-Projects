import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

/**
 * Takes a .txt file and makes an html page with a table of every word in the
 * file and the number of occurrences.
 *
 * @author Nabeu Habetaslassa
 * @author Jay Jones
 *
 */
public final class WordCounter {

    /**
     * Constant for the max font.
     */
    private static final int MAX_FONT = 48;

    /**
     * Constant for the min font.
     */
    private static final int MIN_FONT = 11;

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private WordCounter() {
    }

    /**
     * . Creates comparator class to put term titles in alphabetical order
     */
    private static class Alphabetical
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> pair1,
                Map.Pair<String, Integer> pair2) {
            return pair1.key().toLowerCase()
                    .compareTo(pair2.key().toLowerCase());
        }
    }

    /**
     * . Creates comparator class to put term titles in alphabetical order
     */
    private static class Occurances
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> pair1,
                Map.Pair<String, Integer> pair2) {
            return pair2.value().compareTo(pair1.value());
        }
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    private static void generateElements(String str, Set<Character> charSet) {
        assert str != null : "Violation of: str is not null";
        assert charSet != null : "Violation of: charSet is not null";

        charSet.clear();
        for (int i = 0; i < str.length(); i++) {
            if (!charSet.contains(str.charAt(i))) {
                charSet.add(str.charAt(i));
            }
        }

    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        int i = position;
        int n = text.length();

        // Find the end of the word or separator
        if (!separators.contains(text.charAt(i))) {
            while (i < n && !separators.contains(text.charAt(i))) {
                i++;
            }
        } else {
            while (i < n && separators.contains(text.charAt(i))) {
                i++;
            }
        }

        return text.substring(position, i);

    }

    /**
     * Updates the map and returns a queue of all words in alphabetical order.
     *
     * @param inFile
     *            name of input file
     * @param outFile
     *            name of output file
     * @param table
     *            Map of words and number of occurrences
     *
     * @return a queue of all words in alphabetical order
     *
     */
    private static SortingMachine<Map.Pair<String, Integer>> createMap(
            String inFile, String outFile, Map<String, Integer> table) {

        //creates simple reader for file, queue for words, and comparator for order
        SimpleReader fileIn = new SimpleReader1L(inFile);
        Comparator<Map.Pair<String, Integer>> size = new Occurances();
        SortingMachine<Map.Pair<String, Integer>> sm1 = new SortingMachine1L<Map.Pair<String, Integer>>(
                size);

        //creates set of separators
        Set<Character> separators = new Set1L<Character>();
        String separatorsString = " :;!@#$%^&*()_+-=[]|{};:'<>,.?/`~";
        generateElements(separatorsString, separators);

        //reads in one line at a time
        while (!fileIn.atEOS()) {
            int indx = 0;
            String line = fileIn.nextLine();

            //fills map with every word and the number of occurrences
            while (indx < line.length()) {
                String word = nextWordOrSeparator(line, indx, separators)
                        .toLowerCase();
                indx += word.length();
                int count = 1;
                if (table.hasKey(word)
                        && !separators.contains(word.charAt(0))) {
                    Map.Pair<String, Integer> current = table.remove(word);
                    count = current.value();
                    count++;
                    table.add(word, count);

                } else if (!table.hasKey(word)
                        && !separators.contains(word.charAt(0))) {

                    //fills a queue with every word
                    table.add(word, count);
                }
            }
        }

        for (Map.Pair<String, Integer> pair : table) {
            sm1.add(pair);
        }
        sm1.changeToExtractionMode();
        fileIn.close();
        return sm1;
    }

    /**
     * Sets up html file.
     *
     * @param inFile
     *            name of input file
     * @param outFile
     *            name of output file
     * @param sm1
     *            Sorting machine ordered by occurance
     * @param number
     *            The number of words to use
     *
     */
    private static void htmlPage(String inFile, String outFile,
            SortingMachine<Map.Pair<String, Integer>> sm1, int number) {

        Comparator<Map.Pair<String, Integer>> alph = new Alphabetical();
        SortingMachine<Map.Pair<String, Integer>> sm2 = new SortingMachine1L<Map.Pair<String, Integer>>(
                alph);

        int minCount = Integer.MAX_VALUE;
        int maxCount = 0;
        for (int indx = 0; indx < number; indx++) {
            Map.Pair<String, Integer> current = sm1.removeFirst();
            if (current.value() < minCount) {
                minCount = current.value();
            }
            if (current.value() > maxCount) {
                maxCount = current.value();
            }

            sm2.add(current);
        }
        sm2.changeToExtractionMode();

        //prints the html text to a file
        SimpleWriter fileOut = new SimpleWriter1L(outFile);
        fileOut.println("<html>");
        fileOut.println("<head>");
        fileOut.println("<title>" + "Top 100 words in " + inFile + "</title>");
        fileOut.println(
                "<link href=\"http://web.cse.ohio-state.edu/software/2231/web-sw2/assignments/projects/tag-cloud-generator/data/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        fileOut.println(
                "<link href=\"tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        fileOut.println("</head>");
        fileOut.println("<body>");
        fileOut.println("<h2>" + "Top 100 words in " + inFile + "</h2>");
        fileOut.println("<hr>");
        fileOut.println("<div class=\"cdiv\">");
        fileOut.println("<p class=\"cbox\">");

        while (sm2.size() > 0) {
            Map.Pair<String, Integer> current = sm2.removeFirst();
            int fontSize = ((MAX_FONT - MIN_FONT) * (current.value() - minCount)
                    / (maxCount - minCount) + MIN_FONT);

            fileOut.println("<span style=\"cursor:default\" class=\"f"
                    + fontSize + "\" title=\"count: " + current.value() + "\">"
                    + current.key() + "</span>");

        }
        fileOut.println("</p>");
        fileOut.println("</div>");
        fileOut.println("</body>");
        fileOut.println("</html>");
        fileOut.close();
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();

        out.print("Enter the name of your input file: ");
        String inFile = in.nextLine();
        out.print("Enter the name of your Output file: ");
        String outFile = in.nextLine();
        out.print("Enter the number of words: ");
        int num = in.nextInteger();

        Map<String, Integer> table = new Map1L<String, Integer>();
        SortingMachine<Map.Pair<String, Integer>> sm = createMap(inFile,
                outFile, table);
        htmlPage(inFile, outFile, sm, num);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
