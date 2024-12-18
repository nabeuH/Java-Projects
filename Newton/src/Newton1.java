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
public final class Newton1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton1() {
    }

    /**
     * Relative error margine.
     */
    private static final double RELATIVE_ERROR = .0001;

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {
        double guess = x;

        //loops until it gets a root within the relative error
        while (((Math.abs((guess * guess - x) / x)) > RELATIVE_ERROR
                * RELATIVE_ERROR)) {
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
        String responce;

        do {
            out.print("Do you wish to compute a root? ");
            responce = in.nextLine();
            if (responce.equals("y")) {
                out.print("Enter a positive number: ");
                number = in.nextLine();
                numberDouble = Double.parseDouble(number);
                out.println("The square root of " + number + " is "
                        + sqrt(numberDouble) + ".");
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
