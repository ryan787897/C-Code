import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class ABCDGuesser1 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ABCDGuesser1() {
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
        boolean statement = false;
        double n = -4;
        while (n <= 0) {
            out.println("Enter a positive number: ");
            String input = in.nextLine();
            while (statement == false) {
                if (FormatChecker.canParseDouble(input)) {
                    statement = true;
                } else {
                    out.println("Enter a positive number: ");
                    input = in.nextLine();
                }
            }
            n = Double.parseDouble(input);
        }
        return n;
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
        boolean statement = false;
        double n = -4;
        while (n <= 0 && n != 1) {
            out.println("Enter a positive number (Not one): ");
            String input = in.nextLine();
            while (statement == false) {
                if (FormatChecker.canParseDouble(input)) {
                    statement = true;
                } else {
                    out.println("Enter a positive number (Not one): ");
                    input = in.nextLine();
                }
            }
            n = Double.parseDouble(input);
        }
        return n;
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
        /*
         * Put your main program code here; it may call myMethod as shown
         */
        double Mu = getPositiveDouble(in, out);
        double[] personal = new double[4];
        int i = 0;
        while (i <= 3) {
            personal[i] = getPositiveDoubleNotOne(in, out);
            i++;
        }
        double[] Exponents = { -5.0, -4.0, -3.0, -2.0, -1.0, -1.0 / 2, -1.0 / 3,
                -1.0 / 4, 0.0, 1.0 / 4, 1.0 / 3, 1.0 / 2, 1.0, 2.0, 3.0, 4.0,
                5.0 };
        double E1 = 0, E2 = 0, E3 = 0, E4 = 0;
        int w = 0, x = 0, y = 0, z = 0;
        double diff = Math.pow(personal[0], Exponents[0])
                * Math.pow(personal[1], Exponents[0])
                * Math.pow(personal[2], Exponents[0])
                * Math.pow(personal[3], Exponents[0]) - Mu;
        while (z <= 16) {
            while (y <= 16) {
                while (x <= 16) {
                    while (w <= 16) {
                        double est = Math.pow(personal[0], Exponents[w])
                                * Math.pow(personal[1], Exponents[x])
                                * Math.pow(personal[2], Exponents[y])
                                * Math.pow(personal[3], Exponents[z]) - Mu;
                        if (Math.abs(est) < Math.abs(diff)) {
                            diff = est;
                            E1 = Exponents[w];
                            E2 = Exponents[x];
                            E3 = Exponents[y];
                            E4 = Exponents[z];
                        }
                        w++;
                    }
                    x++;
                    w = 0;
                }
                y++;
                x = 0;
            }
            z++;
            y = 0;
        }
        double error = (diff / Mu) * 100;
        double s = Math.pow(10, 2);
        double PeError = Math.round(error * s) / s;
        out.println("Estiment " + (Mu + diff));
        out.println("Percent Error: " + PeError);
        out.print(" exponent 1: " + E1);
        out.print(" exponent 2: " + E2);
        out.print(" exponent 3: " + E3);
        out.print(" exponent 4: " + E4);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
