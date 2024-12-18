import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Nabeu Habetaslassa
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";
        NaturalNumber num = new NaturalNumber2();

        //Base Case.
        if (exp.label().equals("number")) {
            num = new NaturalNumber2(
                    Integer.parseInt(exp.attributeValue("value")));

            //Recursive Cases
        } else if (exp.label().equals("plus")) {
            NaturalNumber child1 = new NaturalNumber2(evaluate(exp.child(0)));
            NaturalNumber child2 = new NaturalNumber2(evaluate(exp.child(1)));
            child1.add(child2);
            num.copyFrom(child1);

        } else if (exp.label().equals("minus")) {
            NaturalNumber child1 = new NaturalNumber2(evaluate(exp.child(0)));
            NaturalNumber child2 = new NaturalNumber2(evaluate(exp.child(1)));

            if (child1.compareTo(child2) < 0) {
                Reporter.fatalErrorToConsole("Negative natural number error.");
            }

            child1.subtract(child2);
            num.copyFrom(child1);

        } else if (exp.label().equals("divide")) {
            if (exp.child(1).attributeValue("value").equals("0")) {
                Reporter.fatalErrorToConsole("Divide by 0 error.");
            }
            NaturalNumber child1 = new NaturalNumber2(evaluate(exp.child(0)));
            NaturalNumber child2 = new NaturalNumber2(evaluate(exp.child(1)));
            child1.divide(child2);
            num.copyFrom(child1);

        } else if (exp.label().equals("times")) {
            NaturalNumber child1 = new NaturalNumber2(evaluate(exp.child(0)));
            NaturalNumber child2 = new NaturalNumber2(evaluate(exp.child(1)));
            child1.multiply(child2);
            num.copyFrom(child1);
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

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}
