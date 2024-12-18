import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Computes an estimate of a number input using the deJager formula.
 *
 * @author Nabeu Habetaslassa
 *
 */
public final class ABCDGuesser1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser1() {
    }

    /**
     * De Jager 17 numbers.
     */
    private static final double[] JAGER_NUMBERS = { -5.0, -4.0, -3.0, -2.0,
            -1.0, -1.0 / 2.0, -1.0 / 3.0, -1.0 / 4.0, 0.0, 1.0 / 4.0, 1.0 / 3.0,
            1.0 / 2.0, 1.0, 2.0, 3.0, 4.0, 5.0 };

    /**
     * Calculates the deJager formula for given imputs.
     *
     * @param w
     *            variable in formula
     * @param aIndx
     *            variable in formula
     * @param x
     *            variable in formula
     * @param bIndx
     *            variable in formula
     * @param y
     *            variable in formula
     * @param cIndx
     *            variable in formula
     * @param z
     *            variable in formula
     * @param dIndx
     *            variable in formula
     * @return an estimate calculated by the deJager formula
     */

    //I need all 8 parameters to calculate the estimate. Please ignore checkstyle error

    private static double calculateDeJager(double w, int aIndx, double x,
            int bIndx, double y, int cIndx, double z, int dIndx) {

        double estimate = Math.pow(w, JAGER_NUMBERS[aIndx])
                * Math.pow(x, JAGER_NUMBERS[bIndx])
                * Math.pow(y, JAGER_NUMBERS[cIndx])
                * Math.pow(z, JAGER_NUMBERS[dIndx]);

        return estimate;
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        double num = 0.0;
        boolean run = true;
        while (run) {
            out.print("Enter a postive double: ");
            String input = in.nextLine();

            if (FormatChecker.canParseDouble(input)) {
                num = Double.parseDouble(input);
                if (num > 0) {
                    run = false;
                }
            }
            if (run) {
                out.println("Input does not pass all requierments.");
            }
        }

        return num;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        double num = 0.0;
        boolean run = true;
        while (run) {
            out.print("Enter a postive double: ");
            String input = in.nextLine();

            if (FormatChecker.canParseDouble(input)) {
                num = Double.parseDouble(input);
                if (num > 0 && Math.abs(num - 1.0) > 0.00001) {
                    run = false;

                }
            }

            if (run) {
                out.println("Input does not pass all requierments.");

            }
        }

        return num;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //Get number to estimate
        out.print("Enter postive number to aproximate");
        double numToEstimate = getPositiveDouble(in, out);
        out.println();

        //Get values for formula
        out.println("Enter four postive numbers not equal to one");
        Double w = getPositiveDoubleNotOne(in, out);
        Double x = getPositiveDoubleNotOne(in, out);
        Double y = getPositiveDoubleNotOne(in, out);
        Double z = getPositiveDoubleNotOne(in, out);
        out.println();

        // Declare and assign values for all variables in nested loop
        int aIndx = 0;
        int bIndx = 0;
        int cIndx = 0;
        int dIndx = 0;

        double bestA = 0;
        double bestB = 0;
        double bestC = 0;
        double bestD = 0;

        double estimate = 0;
        double bestEstimate = 0;
        double relativeError;
        double bestRelativeError = 1;

        //loops through every possible combination of a, b, c, and d
        while (aIndx < JAGER_NUMBERS.length) {

            if (bIndx == JAGER_NUMBERS.length) {
                bIndx = 0;
            }

            while (bIndx < JAGER_NUMBERS.length) {

                if (cIndx == JAGER_NUMBERS.length) {
                    cIndx = 0;
                }

                while (cIndx < JAGER_NUMBERS.length) {

                    if (dIndx == JAGER_NUMBERS.length) {
                        dIndx = 0;
                    }

                    while (dIndx < JAGER_NUMBERS.length) {

                        //calculates the estimate using calculateDeJager
                        estimate = calculateDeJager(w, aIndx, x, bIndx, y,
                                cIndx, z, dIndx);

                        //calculates the relative error
                        relativeError = (Math.abs(numToEstimate - estimate))
                                / numToEstimate;

                        //if error < best error replace best error, a, b, c, and d
                        if (relativeError < bestRelativeError) {
                            bestRelativeError = relativeError;
                            bestA = JAGER_NUMBERS[aIndx];
                            bestB = JAGER_NUMBERS[bIndx];
                            bestC = JAGER_NUMBERS[cIndx];
                            bestD = JAGER_NUMBERS[dIndx];
                            bestEstimate = estimate;
                        }

                        dIndx++;
                    }
                    cIndx++;

                }
                bIndx++;

            }
            aIndx++;

        }

        //output statments
        out.print("The best estimate was: ");
        out.println(bestEstimate, 2, false);

        out.print("Relative error: ");
        out.print(bestRelativeError * 100, 2, false);
        out.println("%");
        out.println("The best a, b, c, and d: " + bestA + ", " + bestB + ", "
                + bestC + ",  " + bestD);

        /*
         * Close input and output streams
         */
        in.close();

        out.close();
    }

}
