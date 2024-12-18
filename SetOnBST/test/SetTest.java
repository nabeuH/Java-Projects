import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Nabeu Habetalsassa
 * @author Jay Jones
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size

    /*
     * Constructor tests
     */
    @Test
    public final void testConstructor() {
        Set<String> s = this.constructorTest();
        assertEquals(s.size(), 0);
        assertEquals("{}", s.toString());
    }

    /*
     * Add tests
     */
    @Test
    public final void testAddEmpty() {
        Set<String> s = this.constructorTest();
        s.add("a");
        assertEquals("{a}", s.toString());

    }

    @Test
    public final void testAddSingle() {
        Set<String> s = this.createFromArgsTest("a");
        s.add("b");
        assertEquals("{a,b}", s.toString());
    }

    @Test
    public final void testAddMany() {
        Set<String> s = this.createFromArgsTest("a", "b", "c");
        s.add("d");
        assertEquals("{a,b,c,d}", s.toString());
    }

    /*
     * Remove tests
     */
    @Test
    public final void testRemoveOnly() {
        Set<String> s = this.createFromArgsTest("a");
        String rem = s.remove("a");
        assertEquals("a", rem);
        assertEquals("{}", s.toString());
    }

    @Test
    public final void testRemoveFront() {
        Set<String> s = this.createFromArgsTest("a", "b");
        String rem = s.remove("a");
        assertEquals("a", rem);
        assertEquals("{b}", s.toString());
    }

    @Test
    public final void testRemoveEnd() {
        Set<String> s = this.createFromArgsTest("a", "b");
        String rem = s.remove("b");
        assertEquals("b", rem);
        assertEquals("{a}", s.toString());
    }

    @Test
    public final void testRemoveFrontMany() {
        Set<String> s = this.createFromArgsTest("a", "b", "c");
        String rem = s.remove("a");
        assertEquals("a", rem);
        assertEquals("{b,c}", s.toString());
    }

    @Test
    public final void testRemoveEndMany() {
        Set<String> s = this.createFromArgsTest("a", "b", "c");
        String rem = s.remove("c");
        assertEquals("c", rem);
        assertEquals("{a,b}", s.toString());
    }

    @Test
    public final void testRemoveMiddleMany() {
        Set<String> s = this.createFromArgsTest("a", "b", "c");
        String rem = s.remove("b");
        Set<String> sExpected = this.createFromArgsRef("a", "c");
        assertEquals("b", rem);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveMiddleManyV2() {
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j");
        Set<String> sExpected = this.createFromArgsRef("a", "b", "c", "d", "f",
                "g", "h", "i", "j");
        String rem = s.remove("e");
        assertEquals("e", rem);
        assertEquals(sExpected, s);
    }

    /*
     * Remove Any Tests
     */
    @Test
    public final void testRemoveAnyOnly() {
        //Setup
        Set<String> s = this.createFromArgsTest("a");
        Set<String> sExpected = this.createFromArgsTest("a");
        //Call
        String rem = s.removeAny();
        //Evaluation
        assertEquals(true, sExpected.contains(rem));
        sExpected.remove(rem);
        assertEquals(s, sExpected);
    }

    @Test
    public final void testRemoveAnySingle() {
        //Setup
        Set<String> s = this.createFromArgsTest("a", "b");
        Set<String> sExpected = this.createFromArgsTest("a", "b");
        //Call
        String rem = s.removeAny();
        //Evaluation
        assertEquals(true, sExpected.contains(rem));
        sExpected.remove(rem);
        assertEquals(s, sExpected);
    }

    @Test
    public final void testRemoveAnyMany() {
        //Setup
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j");
        Set<String> sExpected = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f", "g", "h", "i", "j");
        //Call
        String rem = s.removeAny();

        //Evaluation
        assertEquals(true, sExpected.contains(rem));
        sExpected.remove(rem);
        assertEquals(s, sExpected);
    }

    /*
     * Contains tests
     */
    @Test
    public final void testContainsEmpty() {
        Set<String> s = this.createFromArgsTest();
        assertTrue(!s.contains("a"));
    }

    @Test
    public final void testContainsOnly() {
        Set<String> s = this.createFromArgsTest("a");
        assertTrue(s.contains("a"));
    }

    @Test
    public final void testContainsFront() {
        Set<String> s = this.createFromArgsTest("a", "b");
        assertTrue(s.contains("a"));
    }

    @Test
    public final void testContainsEnd() {
        Set<String> s = this.createFromArgsTest("a", "b");
        assertTrue(s.contains("b"));
    }

    @Test
    public final void testContainsFrontMany() {
        Set<String> s = this.createFromArgsTest("a", "b", "c");
        assertTrue(s.contains("a"));
    }

    @Test
    public final void testContainsEndMany() {
        Set<String> s = this.createFromArgsTest("a", "b", "c");
        assertTrue(s.contains("c"));
    }

    @Test
    public final void testContainsMiddleMany() {
        Set<String> s = this.createFromArgsTest("a", "b", "c");
        assertTrue(s.contains("b"));
    }

    @Test
    public final void testContainsMiddleManyV2() {
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j");
        assertTrue(s.contains("e"));
    }

    /*
     * Size tests
     */
    @Test
    public final void testSizeZero() {
        Set<String> s = this.constructorTest();
        int size = s.size();
        assertEquals("{}", s.toString());
        assertEquals(0, size);
    }

    @Test
    public final void testSizeOne() {
        Set<String> s = this.createFromArgsTest("a");
        int size = s.size();
        assertEquals("{a}", s.toString());
        assertEquals(1, size);
    }

    @Test
    public final void testSizeManyThree() {
        Set<String> s = this.createFromArgsTest("a", "b", "c");
        int size = s.size();
        assertEquals("{a,b,c}", s.toString());
        assertEquals(3, size);
    }

    @Test
    public final void testSizeOneManyTen() {
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j");
        int size = s.size();
        assertEquals("{a,b,c,d,e,f,g,h,i,j}", s.toString());
        assertEquals(10, size);
    }

}
