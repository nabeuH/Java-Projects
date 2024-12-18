import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Jay Jones
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    /*
     * Constructor tests
     */
    //no arg constructor test
    @Test
    public final void testConstructor() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();
        assertEquals(nExpected, n);
    }

    //int constructor tests
    @Test
    public final void testIntConstructorZero() {
        final int i = 0;
        NaturalNumber n = this.constructorTest(i);
        NaturalNumber nExpected = this.constructorRef(i);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIntConstructorSeven() {
        final int i = 7;
        NaturalNumber n = this.constructorTest(i);
        NaturalNumber nExpected = this.constructorRef(i);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIntConstructor12() {
        final int i = 12;
        NaturalNumber n = this.constructorTest(i);
        NaturalNumber nExpected = this.constructorRef(i);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIntConstructor2134() {
        final int i = 2134;
        NaturalNumber n = this.constructorTest(i);
        NaturalNumber nExpected = this.constructorRef(i);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIntConstructorMAX() {
        final int i = Integer.MAX_VALUE;
        NaturalNumber n = this.constructorTest(i);
        NaturalNumber nExpected = this.constructorRef(i);
        assertEquals(nExpected, n);
    }

    //String constructor
    @Test
    public final void testStringConstructorZero() {
        String s = "0";
        NaturalNumber n = this.constructorTest(s);
        NaturalNumber nExpected = this.constructorRef(s);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testStringConstructorNine() {
        String s = "9";
        NaturalNumber n = this.constructorTest(s);
        NaturalNumber nExpected = this.constructorRef(s);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testStringConstructor65() {
        String s = "65";
        NaturalNumber n = this.constructorTest(s);
        NaturalNumber nExpected = this.constructorRef(s);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testStringConstructor2543() {
        String s = "2543";
        NaturalNumber n = this.constructorTest(s);
        NaturalNumber nExpected = this.constructorRef(s);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testStringConstructor97257042() {
        String s = "97257042";
        NaturalNumber n = this.constructorTest(s);
        NaturalNumber nExpected = this.constructorRef(s);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testStringConstructorMAX() {
        String s = "2147483647";
        NaturalNumber n = this.constructorTest(s);
        NaturalNumber nExpected = this.constructorRef(s);
        assertEquals(nExpected, n);
    }

    //NaturalNumber constructor tests
    @Test
    public final void testNNConstructorZero() {
        final int forTest = 0;
        NaturalNumber test = this.constructorTest(forTest);
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(test);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testNNConstructorEight() {
        final int forTest = 8;
        NaturalNumber test = this.constructorTest(forTest);
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(test);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testNNConstructor54() {
        final int forTest = 54;
        NaturalNumber test = this.constructorTest(forTest);
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(test);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testNNConstructor5463() {
        final int forTest = 5463;
        NaturalNumber test = this.constructorTest(forTest);
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(test);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testNNConstructor79468986() {
        final int forTest = 79468986;
        NaturalNumber test = this.constructorTest(forTest);
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(test);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testNNConstructorMAX() {
        final int forTest = Integer.MAX_VALUE;
        NaturalNumber test = this.constructorTest(forTest);
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(test);
        assertEquals(nExpected, n);
    }

    /*
     * Multiply by 10 tests
     */
    @Test
    public final void testMultiplyBy10NoArgZero() {
        //Set-up
        final int k = 0;
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();
        //Call method
        n.multiplyBy10(k);
        //Evaluation
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10IntZeroAddZero() {
        //Set-up
        final int test = 0;
        final int expected = 0;
        final int k = 0;
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(expected);
        //Call method
        n.multiplyBy10(k);
        //Evaluation
        assertEquals(nExpected, n);
    }

    //New for re-submission
    @Test
    public final void testMultiplyBy10IntZeroAddNonZero() {
        //Set-up
        final int test = 0;
        final int expected = 7;
        final int k = 7;
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(expected);
        //Call method
        n.multiplyBy10(k);
        //Evaluation
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10IntNineAddZero() {
        //Set-up
        final int test = 9;
        final int expected = 90;
        final int k = 0;
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(expected);
        //Call method
        n.multiplyBy10(k);
        //Evaluation
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10StringSevenAddZero() {
        //Set-up
        String test = "7";
        String expected = "70";
        final int k = 0;
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(expected);
        //Call method
        n.multiplyBy10(k);
        //Evaluation
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10String65646565AddTwo() {
        //Set-up
        String test = "65646565";
        String expected = "656465652";
        final int k = 2;
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(expected);
        //Call method
        n.multiplyBy10(k);
        //Evaluation
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10NN7815AddZero() {
        //Set-up
        final int forTest = 7815;
        NaturalNumber test = this.constructorTest(forTest);
        final int forExpected = 78150;
        NaturalNumber expected = this.constructorTest(forExpected);
        final int k = 0;
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(expected);
        //Call method
        n.multiplyBy10(k);
        //Evaluation
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10NN65435AddFive() {
        //Set-up
        final int forTest = 65435;
        NaturalNumber test = this.constructorTest(forTest);
        final int forExpected = 654355;
        NaturalNumber expected = this.constructorTest(forExpected);
        final int k = 5;
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(expected);
        //Call method
        n.multiplyBy10(k);
        //Evaluation
        assertEquals(nExpected, n);
    }

    /*
     * divideBy10 tests
     */

    //New for re-submission
    @Test
    public final void testDividedBy10Zero() {
        //Set-up
        final int test = 0;
        NaturalNumber n = this.constructorTest(test);
        final int expected = 0;
        NaturalNumber nExpected = this.constructorTest(expected);
        final int rExpected = 0;

        //Call method
        int r = n.divideBy10();

        //Evaluation
        assertEquals(nExpected, n);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testDividedBy10Int80() {
        //Set-up
        final int test = 80;
        NaturalNumber n = this.constructorTest(test);
        final int expected = 8;
        NaturalNumber nExpected = this.constructorTest(expected);
        final int rExpected = 0;

        //Call method
        int r = n.divideBy10();

        //Evaluation
        assertEquals(nExpected, n);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testDivideBy10IntNine() {
        //Set-up
        final int test = 9;
        NaturalNumber n = this.constructorTest(test);
        final int expected = 0;
        NaturalNumber nExpected = this.constructorTest(expected);
        final int rExpected = 9;

        //Call method
        int r = n.divideBy10();

        //Evaluation
        assertEquals(nExpected, n);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testDivideBy10String365() {
        //Set-up
        String test = "365";
        NaturalNumber n = this.constructorTest(test);
        String expected = "36";
        NaturalNumber nExpected = this.constructorTest(expected);
        final int rExpected = 5;

        //Call method
        int r = n.divideBy10();

        //Evaluation
        assertEquals(nExpected, n);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testDivideBy10String98453147() {
        //Set-up
        String test = "98453147";
        NaturalNumber n = this.constructorTest(test);
        String expected = "9845314";
        NaturalNumber nExpected = this.constructorTest(expected);
        final int rExpected = 7;

        //Call method
        int r = n.divideBy10();

        //Evaluation
        assertEquals(nExpected, n);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testDivideBy10NN7815564() {
        //Set-up
        final int forTest = 7815564;
        NaturalNumber test = this.constructorTest(forTest);
        final int forExpected = 781556;
        NaturalNumber expected = this.constructorTest(forExpected);

        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(expected);
        final int rExpected = 4;

        //Call method
        int r = n.divideBy10();

        //Evaluation
        assertEquals(nExpected, n);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testDivideBy10NN742() {
        //Set-up
        final int forTest = 742;
        NaturalNumber test = this.constructorTest(forTest);
        final int forExpected = 74;
        NaturalNumber expected = this.constructorTest(forExpected);

        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(expected);
        final int rExpected = 2;

        //Call method
        int r = n.divideBy10();

        //Evaluation
        assertEquals(nExpected, n);
        assertEquals(rExpected, r);
    }

    /*
     * isZero tests
     */
    @Test
    public final void testIsZeroNoArg() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();
        assertEquals(nExpected.isZero(), n.isZero());
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIsZeroIntZero() {
        final int test = 0;
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(test);
        assertEquals(nExpected.isZero(), n.isZero());
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIsZeroIntNonZero() {
        final int test = 234324;
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(test);
        assertEquals(nExpected.isZero(), n.isZero());
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIsZeroStringZero() {
        String test = "0";
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(test);
        assertEquals(nExpected.isZero(), n.isZero());
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIsZeroStringNonZero() {
        String test = "956785314";
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(test);
        assertEquals(nExpected.isZero(), n.isZero());
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIsZeroNNZero() {
        final int forTest = 0;
        NaturalNumber test = this.constructorRef(forTest);
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(test);
        assertEquals(nExpected.isZero(), n.isZero());
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIsZeroNNNonZero() {
        final int forTest = 7614581;
        NaturalNumber test = this.constructorRef(forTest);
        NaturalNumber n = this.constructorTest(test);
        NaturalNumber nExpected = this.constructorRef(test);
        assertEquals(nExpected.isZero(), n.isZero());
        assertEquals(nExpected, n);
    }
}