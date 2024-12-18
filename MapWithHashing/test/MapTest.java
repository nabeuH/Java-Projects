import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Nabeu Habetaslassa
 * @author Jay Jones
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void constructorsTest() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> expected = this.constructorRef();
        Map<String, String> test = this.constructorTest();
        assertEquals(test, expected);
    }

    @Test
    public void constructorFromArgsTest() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> expected = this.createFromArgsRef("A", "1", "B",
                "2");
        Map<String, String> test = this.createFromArgsTest("A", "1", "B", "2");
        assertEquals(test, expected);
    }

    /*
     * Add tests
     */

    @Test
    public void emptyAddTest() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> expected = this.createFromArgsRef("A", "1");
        Map<String, String> test = this.constructorTest();
        test.add("A", "1");
        assertEquals(test, expected);
    }

    @Test
    public void routineAddTest() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> expected = this.createFromArgsRef("A", "1", "B",
                "2", "C", "3");
        Map<String, String> test = this.createFromArgsTest("A", "1", "B", "2");
        test.add("C", "3");
        assertEquals(test, expected);
    }

    @Test
    public final void testAddMany() {
        Map<String, String> s = this.createFromArgsTest("testing", "123",
                "function", "456", "units", "111");
        Map<String, String> sExpected = this.createFromArgsRef("testing", "123",
                "function", "456", "units", "111", "gears", "234");

        s.add("gears", "234");

        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddManyDuplicates() {
        Map<String, String> s = this.createFromArgsTest("testing", "123",
                "function", "456", "units", "111");
        Map<String, String> sExpected = this.createFromArgsRef("testing", "123",
                "function", "456", "units", "111", "gears", "111");

        s.add("gears", "111");

        assertEquals(sExpected, s);
    }

    @Test
    public void addBoundaryManyMore() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> expected = this.createFromArgsRef("A", "1", "B",
                "2", "C", "3", "D", "4", "E", "5", "F", "6", "G", "7", "H", "8",
                "I", "9", "J", "10");
        Map<String, String> test = this.createFromArgsTest("A", "1", "B", "2",
                "C", "3", "D", "4", "E", "5", "F", "6", "G", "7", "H", "8", "I",
                "9");
        test.add("J", "10");
        assertEquals(test, expected);
    }

    /*
     * Remove tests
     */

    @Test
    public void emptyRemoveTest() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> expected = this.createFromArgsRef();
        Map<String, String> test = this.createFromArgsTest("A", "1");
        test.remove("A");
        assertEquals(test, expected);
    }

    @Test
    public void standardRemoveTest() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> expected = this.createFromArgsRef("B", "2", "C",
                "3");
        Map<String, String> test = this.createFromArgsTest("A", "1", "B", "2",
                "C", "3");
        test.remove("A");
        assertEquals(test, expected);
    }

    @Test
    public void removeBoundaryTest() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> expected = this.createFromArgsRef("A", "1", "B",
                "2", "C", "3", "E", "5", "F", "6", "G", "7", "H", "8", "I", "9",
                "J", "10");
        Map<String, String> test = this.createFromArgsTest("A", "1", "B", "2",
                "C", "3", "D", "4", "E", "5", "F", "6", "G", "7", "H", "8", "I",
                "9", "J", "10");
        test.remove("D");
        assertEquals(test, expected);
    }

    /*
     * RemoveAny tests
     */

    @Test
    public void removeToEmptyTest() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> expected = this.createFromArgsRef("A", "1");
        Map<String, String> test = this.createFromArgsTest("A", "1");
        assertEquals(test, expected);

        Map.Pair<String, String> current = test.removeAny();
        assertEquals(expected.hasKey(current.key()), true);
        expected.remove(current.key());
        assertEquals(test, expected);
    }

    @Test
    public void routineRemoveAnyTest() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> expected = this.createFromArgsRef("A", "1", "B",
                "2");
        Map<String, String> test = this.createFromArgsTest("A", "1", "B", "2");
        assertEquals(test, expected);

        Map.Pair<String, String> current = test.removeAny();
        assertEquals(expected.hasKey(current.key()), true);
        expected.remove(current.key());
        assertEquals(test, expected);
    }

    @Test
    public void removeAnyTestMany() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> expected = this.createFromArgsRef("A", "1", "B",
                "2", "C", "3", "E", "5", "F", "6", "G", "7", "H", "8", "I", "9",
                "J", "10");
        Map<String, String> test = this.createFromArgsTest("A", "1", "B", "2",
                "C", "3", "E", "5", "F", "6", "G", "7", "H", "8", "I", "9", "J",
                "10");
        assertEquals(test, expected);

        Map.Pair<String, String> current = test.removeAny();
        assertEquals(expected.hasKey(current.key()), true);
        expected.remove(current.key());
        assertEquals(test, expected);
    }

    /*
     * Value tests
     */

    @Test
    public final void testValueEmpty() {
        //Set-Up
        Map<String, String> s = this.createFromArgsTest("testing", "");
        Map<String, String> sExpected = this.createFromArgsRef("testing", "");

        //Call
        String v = s.value("testing");
        String vExpected = sExpected.value("testing");

        //Evaluation
        assertEquals(sExpected, s);
        assertEquals(vExpected, v);
    }

    @Test
    public final void testValueOne() {
        //Set-Up
        Map<String, String> s = this.createFromArgsTest("testing", "123");
        Map<String, String> sExpected = this.createFromArgsRef("testing",
                "123");

        //Call
        String v = s.value("testing");
        String vExpected = sExpected.value("testing");

        //Evaluation
        assertEquals(sExpected, s);
        assertEquals(vExpected, v);
    }

    @Test
    public final void testValueMany() {
        //Set-Up
        Map<String, String> s = this.createFromArgsTest("testing", "123",
                "function", "456", "units", "111", "gears", "256");
        Map<String, String> sExpected = this.createFromArgsRef("testing", "123",
                "function", "456", "units", "111", "gears", "256");

        //Call
        String v = s.value("gears");
        String vExpected = sExpected.value("gears");

        //Evaluation
        assertEquals(sExpected, s);
        assertEquals(vExpected, v);
    }

    /*
     * hasKey tests
     */

    @Test
    public void hasKeyTestOne() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> expected = this.createFromArgsRef("A", "1");
        Map<String, String> test = this.createFromArgsTest("A", "1");
        assertEquals(test.hasKey("A"), expected.hasKey("A"));
    }

    public void hasKeyTest() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> expected = this.createFromArgsRef("A", "1", "B",
                "2");
        Map<String, String> test = this.createFromArgsTest("A", "1", "B", "2");
        assertEquals(test.hasKey("A"), expected.hasKey("A"));
    }

    public void hasKeyTestEmpty() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> expected = this.createFromArgsRef();
        Map<String, String> test = this.createFromArgsTest();
        assertEquals(test.hasKey("A"), expected.hasKey("A"));
    }

    public void hasKeyTestNoKey() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> expected = this.createFromArgsRef("A", "1", "B",
                "2");
        Map<String, String> test = this.createFromArgsTest("A", "1", "B", "2");
        assertEquals(test.hasKey("C"), expected.hasKey("C"));
    }

    @Test
    public void hasKeyTestMany() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> expected = this.createFromArgsRef("A", "1", "B",
                "2", "C", "3", "E", "5", "F", "6", "G", "7", "H", "8", "I", "9",
                "J", "10");
        Map<String, String> test = this.createFromArgsTest("A", "1", "B", "2",
                "C", "3", "E", "5", "F", "6", "G", "7", "H", "8", "I", "9", "J",
                "10");
        assertEquals(test.hasKey("C"), expected.hasKey("C"));
    }

    /*
     * Size tests
     */

    @Test
    public final void testSizeZero() {
        Map<String, String> s = this.constructorTest();
        Map<String, String> sExpected = this.constructorRef();

        //Evaluation - size and object
        assertEquals(sExpected.size(), s.size());
        assertEquals(sExpected, s);
    }

    @Test
    public final void testSizeOne() {
        Map<String, String> s = this.createFromArgsTest("testing", "123");
        Map<String, String> sExpected = this.createFromArgsRef("testing",
                "123");

        //Evaluation - size and object
        assertEquals(sExpected.size(), s.size());
        assertEquals(sExpected, s);
    }

    @Test
    public final void testSizeMany4() {
        Map<String, String> s = this.createFromArgsTest("testing", "123",
                "function", "456", "units", "111", "gears", "256");
        Map<String, String> sExpected = this.createFromArgsRef("testing", "123",
                "function", "456", "units", "111", "gears", "256");

        //Evaluation - size and object
        assertEquals(sExpected.size(), s.size());
        assertEquals(sExpected, s);
    }

    @Test
    public final void testSizeMany7() {
        Map<String, String> s = this.createFromArgsTest("testing", "123",
                "function", "456", "units", "111", "gears", "256", "wheel",
                "874", "mower", "5698", "gloves", "2145");
        Map<String, String> sExpected = this.createFromArgsRef("testing", "123",
                "function", "456", "units", "111", "gears", "256", "wheel",
                "874", "mower", "5698", "gloves", "2145");

        //Evaluation - size and object
        assertEquals(sExpected.size(), s.size());
        assertEquals(sExpected, s);
    }

}
