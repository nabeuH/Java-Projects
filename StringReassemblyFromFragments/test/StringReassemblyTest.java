import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * . Test Fixture for StringReassembly Methods
 *
 * @author habetaslassa.1
 *
 */
public class StringReassemblyTest {

    /**
     * . test combine method
     */
    @Test
    public void testCombineStrings() {
        String str1 = "hello world";
        String str2 = "world domination";
        final int overlap = 5;
        String expected = "hello world domination";
        String result = StringReassembly.combination(str1, str2, overlap);
        assertEquals(expected, result);
    }

    /**
     * . test combine method with empty strings
     */
    @Test
    public void testCombineStringsWithEmptyStrings() {
        String str1 = "";
        String str2 = "";
        int overlap = 0;
        String expected = "";
        String result = StringReassembly.combination(str1, str2, overlap);
        assertEquals(expected, result);
    }

    /**
     * . test lines from input
     */
    @Test
    public void testlinesFromInput() {

        SimpleReader in = new SimpleReader1L("data/test3.txt");
        Set<String> result = StringReassembly.linesFromInput(in);
        Set<String> expectedSet = new Set2<String>();
        expectedSet.add("hello");
        expectedSet.add("world");
        expectedSet.add("how are you");
        expectedSet.add("wsg");
        assertEquals(expectedSet, result);
    }

    /**
     * . test addToSetAvoidingSubstrings with no overlap
     */
    @Test
    public void testAddToSetAvoidingSubstringsNoOverlap() {
        Set<String> strSet = new Set2<>();
        strSet.add("hello");
        String str = "world";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        Set<String> expected = new Set2<>();
        expected.add("hello");
        expected.add("world");
        assertEquals(expected, strSet);
    }

    /**
     * . test addToSetAvoidingSubstrings with overlap
     */
    @Test
    public void testAddToSetAvoidingSubstringsOverlap() {
        Set<String> strSet = new Set2<>();
        strSet.add("hello");
        String str = "hell";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        Set<String> expected = new Set2<>();
        expected.add("hello");
        assertEquals(expected, strSet);
    }

    /**
     * . test printWithLineSeparators
     */
    @Test
    public void testPrintWithLineSeparators() {
        SimpleWriter out = new SimpleWriter1L("file.txt");
        SimpleReader in = new SimpleReader1L("file.txt");
        StringReassembly.printWithLineSeparators("This~Test~Works!", out);

        String line1 = in.nextLine();
        String line2 = in.nextLine();
        String line3 = in.nextLine();

        String expectedLine1 = "This";
        String expectedLine2 = "Test";
        String expectedLine3 = "Works!";

        assertEquals(expectedLine1, line1);
        assertEquals(expectedLine2, line2);
        assertEquals(expectedLine3, line3);
        in.close();
    }

}
