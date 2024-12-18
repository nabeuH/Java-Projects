import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Nabeu Habetalsassa
 * @author Jay Jones
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a",
                "c");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "a", "b", "c");
        m.add("b");

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a",
                "b", "c");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "b", "c");
        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();
        String removed = m.removeFirst();
        assertEquals("a", removed);
        assertEquals(mExpected, m);
    }
    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size
    /*
     * Add tests
     */

    /*
     * Add tests
     */

    @Test
    public final void testAddSingleNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "blue");
        m.add("green");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddMultiEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue", "red", "white");
        m.add("red");
        m.add("blue");
        m.add("white");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddMultiNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "violet");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue", "red", "violet", "white");
        m.add("red");
        m.add("blue");
        m.add("white");
        assertEquals(mExpected, m);
    }

    /*
     * changeToExtractionMode tests
     */

    @Test
    public final void testChangeToExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeNonEmpty() {
        SortingMachine<String> m = this.createFromArgsRef(ORDER, true, "blue",
                "red", "white");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue", "red", "white");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    /*
     * removeFirst tests
     */

    @Test
    public final void testRemoveFirstSingleEntry() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue");
        String check = m.removeFirst();
        String expected = mExpected.removeFirst();
        assertEquals(mExpected, m);
        assertEquals(check, "blue");
    }

    @Test
    public final void testRemoveFirstMultiEntries() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "white", "red", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "white", "red", "blue");
        String check = m.removeFirst();
        String expected = mExpected.removeFirst();
        assertEquals(mExpected, m);
        assertEquals(check, expected);
    }

    /*
     * isInInsertionMode tests
     */

    @Test
    public final void testIsInInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        assertEquals(m.isInInsertionMode(), true);
    }

    @Test
    public final void testIsInInsertionModeFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        m.changeToExtractionMode();
        assertEquals(m.isInInsertionMode(), false);
    }

    /*
     * Order test
     */

    @Test
    public final void testOrder() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        assertEquals(m.order(), ORDER);
    }

    @Test
    public final void testOrderExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        m.changeToExtractionMode();
        assertEquals(m.order(), ORDER);
    }

    /*
     * Size tests
     */

    @Test
    public final void testSizeZero() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        assertEquals(m.size(), 0);
    }

    @Test
    public final void testSizeOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue");
        assertEquals(m.size(), 1);
    }

    @Test
    public final void testSizeSix() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "white", "red", "green", "violet", "teal");
        assertEquals(m.size(), 6);
    }

    @Test
    public final void testSizeTwelve() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "white", "red", "green", "violet", "teal", "cream", "yellow",
                "pink", "mint", "orange", "brown");
        assertEquals(m.size(), 12);
    }

    @Test
    public final void testSizeZeroExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        m.changeToExtractionMode();
        assertEquals(m.size(), 0);
    }

    @Test
    public final void testSizeOneExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue");
        m.changeToExtractionMode();
        assertEquals(m.size(), 1);
    }

    @Test
    public final void testSizeSixExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "white", "red", "green", "violet", "teal");
        m.changeToExtractionMode();
        assertEquals(m.size(), 6);
    }

    @Test
    public final void testSizeTwelveExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "white", "red", "green", "violet", "teal", "cream", "yellow",
                "pink", "mint", "orange", "brown");
        m.changeToExtractionMode();
        assertEquals(m.size(), 12);
    }
}
