import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Model class.
 *
 * @author Ryan McKinley
 */
public final class NNCalcModel1 implements NNCalcModel {

    /**
     * Model variables.
     */
    private final NaturalNumber high, low;

    /**
     * Default constructor.
     */
    public NNCalcModel1() {
        this.high = new NaturalNumber2();
        this.low = new NaturalNumber2();
    }

    @Override
    public NaturalNumber top() {

        return this.high;

        /*
         * This line added just to make the program compilable. Should be
         * replaced with appropriate return statement.
         */
    }

    @Override
    public NaturalNumber bottom() {

        return this.low;

        /*
         * This line added just to make the program compilable. Should be
         * replaced with appropriate return statement.
         */
    }

}
