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
 * @author Put your name here
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

        NaturalNumber zero = new NaturalNumber2(0);
        NaturalNumber answer = new NaturalNumber2();

        if (!exp.label().equals("number")) {
            answer.transferFrom(evaluate(exp.child(0)));
            if (exp.label().equals("plus")) {
                answer.add(evaluate(exp.child(1)));
            } else if (exp.label().equals("minus")) {
                if (evaluate(exp.child(1)).compareTo(answer) > 0) {
                    Reporter.fatalErrorToConsole("ERROR NEGATIVE");
                } else {
                    answer.subtract(evaluate(exp.child(1)));
                }
            } else if (exp.label().equals("times")) {
                answer.multiply(evaluate(exp.child(1)));
            } else if (exp.label().equals("divide")) {
                if (evaluate(exp.child(1)).equals(zero)) {
                    Reporter.fatalErrorToConsole("ERROR OF DIVIDE BY ZERO");
                } else {
                    answer.divide(evaluate(exp.child(1)));
                }
            }
        } else {
            answer = new NaturalNumber2(exp.attributeValue("value"));
        }
        return answer;
        // TODO - fill in body

        /*
         * This line added just to make the program compilable. Should be
         * replaced with appropriate return statement.
         */
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
