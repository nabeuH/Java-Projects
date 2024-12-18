import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program that computes roots using newton's iteration.
 *
 * @author Nabeu Habetaslassa
 *
 */
public final class Newton3 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton3() {
    }

    /**
     * Relative error margine for aproximation of zero.
     */
    private static final double RELATIVE_ERROR_ZERO = .0001;

    /**
     * Aproximate double value of zero .
     */
    private static final double ZERO_DOUBLE = .0000001;

    /**
     * Computes estimate of square root of x to within a relative error.
     *
     * @param x
     *            positive number (or 0) to compute square root of
     * @param relativeError
     *            Error margin.
     * @return estimate of square root
     */
    private static double sqrt(double x, double relativeError) {

        double guess = x;
        double error = relativeError;
        boolean isZero;

        //checks if guess is zero and accounts for that case
        if ((guess - ZERO_DOUBLE) / ZERO_DOUBLE > RELATIVE_ERROR_ZERO) {
            isZero = false;
        } else {
            isZero = true;
        }

        if (isZero) {
            guess = 0.0;
        }

        //checks if error is zero for if guess is a decimal and error is 0)
        if ((error - ZERO_DOUBLE) / ZERO_DOUBLE < RELATIVE_ERROR_ZERO) {
            error = ZERO_DOUBLE;
        }

        //loops until it gets a root within the relative error
        while (((Math.abs((guess * guess - x) / x)) > error * error)
                && !isZero) {
            guess = (guess + (x / guess)) / 2;
        }

        return guess;

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

        String number;
        double numberDouble;

        String errorString;
        double relativeError;

        String responce;

        do {
            out.print("Do you wish to compute a root? ");
            responce = in.nextLine();
            if (responce.equals("y")) {
                out.print("Enter a postive number (includes 0): ");
                number = in.nextLine();
                numberDouble = Double.parseDouble(number);

                out.print("Enter the margine of error: ");
                errorString = in.nextLine();
                relativeError = Double.parseDouble(errorString);

                out.println("The square root of " + number + " is "
                        + sqrt(numberDouble, relativeError) + ".");
                out.println();
            }
        } while (responce.equals("y"));

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
