import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Creates a glossary from a text file.
 *
 * @author Nabeu Habetaslassa
 */
public final class Glossary {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Glossary() {
        // no code needed here
    }

    /**
     * . Creates comparator class to put term titles in alphabetical order
     */
    private static class Sort implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     * . Creates the html file for the glossary
     *
     * @param fileIn
     *            input stream for .txt file
     * @param termsDefMap
     *            Map filled with term names and their respective descriptions
     * @param fileOut
     *            output stream for the glossary .html file
     * @param titlesQueue
     *            a queue of strings that holds all the names of terms
     */
    private static void indexPage(SimpleReader fileIn,
            Map<String, String> termsDefMap, SimpleWriter fileOut,
            Queue<String> titlesQueue) {

        fileOut.println("<html>");

        fileOut.print("<title>");
        fileOut.print("Glossary");
        fileOut.println("</title>");
        fileOut.println("<body>");
        fileOut.println("<h2>" + "Glossary" + "</h2>");
        fileOut.println("<hr />");
        fileOut.println("<h3>Index</h3>");
        fileOut.println("<ul>");

        for (String s : titlesQueue) {
            fileOut.print("<li>");
            fileOut.print("<a href=\"" + s + ".html\">" + s + "</a>");
            fileOut.println("</li>");
        }
        fileOut.println("</ul>");

        fileOut.println("</body>");
        fileOut.println("</html>");

    }

    /**
     * . Creates html files for each term
     *
     * @param termsDefMap
     *            Map filled with term names and their respective descriptions
     * @param titlesQueue
     *            a queue of strings that holds all the names of terms
     * @param folderName
     *            name of file holding the glossary
     */
    private static void outputFiles(Map<String, String> termsDefMap,
            Queue<String> titlesQueue, String folderName) {

        for (int i = titlesQueue.length(); i != 0; i--) {

            Map.Pair<String, String> current = termsDefMap
                    .remove(titlesQueue.front());
            titlesQueue.rotate(1);

            SimpleWriter fileOut = new SimpleWriter1L(
                    folderName + "/" + current.key() + ".html");

            String title = current.key();
            String description = current.value();

            fileOut.println("<html>");
            fileOut.print("<title>");
            fileOut.print(title);
            fileOut.println("</title>");

            fileOut.println(
                    "<h2 style=\"color:red;\"><i>" + title + "</i></h2>");

            fileOut.println("<body>");
            printDescription(description, titlesQueue, fileOut);

            fileOut.println("<hr>");

            fileOut.println(
                    "<p>Return to <a href=\"index.html\">index</a></p>");

            fileOut.println("</body>");

            fileOut.println("</html>");
            fileOut.close();
        }
    }

    /**
     * . Fills map with term names and their respective descriptions and creates
     * a queue of all term names in alphabetical order
     *
     * @param fileIn
     *            Input stream for the .txt file
     *
     * @param termsDefMap
     *            empty map
     *
     * @return titlesQueue a queue of strings that holds all the names of terms
     *         in alphabetical order
     */
    private static Queue<String> getMapQueue(SimpleReader fileIn,
            Map<String, String> termsDefMap) {
        Queue<String> titlesQueue = new Queue1L<>();
        Comparator<String> x = new Sort();

        while (!fileIn.atEOS()) {
            String title = fileIn.nextLine();
            String description = fileIn.nextLine();
            String line = "";
            if (!fileIn.atEOS()) {
                line = fileIn.nextLine();
            }
            while (!line.equals("")) {
                description = description + line;
                line = fileIn.nextLine();
            }

            termsDefMap.add(title, description);
            titlesQueue.enqueue(title);
        }
        titlesQueue.sort(x);

        return titlesQueue;
    }

    /**
     * . Prints the description code in html and adds hypertext for terms
     *
     * @param description
     *            the string for the description of a term
     * @param titlesQueue
     *            a queue of strings that holds all the names of terms
     * @param fileOut
     *            the output stream to the .txt file
     *
     */
    private static void printDescription(String description,
            Queue<String> titlesQueue, SimpleWriter fileOut) {

        Set<Character> separators = new Set1L<Character>();
        separators.add(' ');

        fileOut.print("<p>");
        int indx = 0;
        while (indx < description.length()) {
            String current = nextWordOrSeparator(description, indx, separators);
            boolean contains = false;
            for (String s : titlesQueue) {
                if (s.equals(current)) {
                    contains = true;
                    fileOut.print("<a href=\"" + current + ".html\">" + current
                            + " " + "</a>");
                }
            }
            if (!contains) {
                fileOut.print(current + " ");
            }
            indx = indx + current.length() + 1;

        }
        fileOut.println("</p>");
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
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.println(
                "Enter an input txt file containing the terms and descriptions. ");
        SimpleReader fileIn = new SimpleReader1L(in.nextLine());

        out.println("Enter the name for your folder for the .html files ");
        String folderName = in.nextLine();
        SimpleWriter fileOut = new SimpleWriter1L(folderName + "/index.html");

        Map<String, String> wordMap = new Map1L<>();

        Queue<String> titlesQueue = getMapQueue(fileIn, wordMap);

        outputFiles(wordMap, titlesQueue, folderName);

        indexPage(fileIn, wordMap, fileOut, titlesQueue);

        in.close();
        out.close();
        fileIn.close();
        fileOut.close();
    }

}
