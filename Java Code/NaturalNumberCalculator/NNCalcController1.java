import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author Ryan McKinley
 */
public final class NNCalcController1 implements NNCalcController {

    /**
     * Model object.
     */
    private final NNCalcModel model;

    /**
     * View object.
     */
    private final NNCalcView view;

    /**
     * Useful constants.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2),
            INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE);

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToMatchModel(NNCalcModel model,
            NNCalcView view) {

        NaturalNumber high = model.top();
        NaturalNumber low = model.bottom();

        view.updateTopDisplay(high);
        view.updateBottomDisplay(low);
        view.updateDivideAllowed(!low.isZero());
        view.updatePowerAllowed(low.compareTo(INT_LIMIT) <= 0);
        view.updateSubtractAllowed(low.compareTo(high) <= 0);
        view.updateRootAllowed(
                low.compareTo(INT_LIMIT) <= 0 && low.compareTo(TWO) >= 0);
    }

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public NNCalcController1(NNCalcModel model, NNCalcView view) {
        this.model = model;
        this.view = view;
        updateViewToMatchModel(model, view);
    }

    @Override
    public void processClearEvent() {
        /*
         * Get alias to low from model
         */
        NaturalNumber low = this.model.bottom();
        /*
         * Update model in response to this event
         */
        low.clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSwapEvent() {
        /*
         * Get aliases to high and low from model
         */
        NaturalNumber high = this.model.top();
        NaturalNumber low = this.model.bottom();
        /*
         * Update model in response to this event
         */
        NaturalNumber holder = high.newInstance();
        holder.transferFrom(high);
        high.transferFrom(low);
        low.transferFrom(holder);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processEnterEvent() {
        NaturalNumber high = this.model.top();
        NaturalNumber low = this.model.bottom();
        high.clear();
        high.add(low);
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processAddEvent() {
        NaturalNumber high = this.model.top();
        NaturalNumber low = this.model.bottom();
        NaturalNumber holder = high.newInstance();
        holder.transferFrom(high);
        low.add(holder);
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSubtractEvent() {
        NaturalNumber high = this.model.top();
        NaturalNumber low = this.model.bottom();
        NaturalNumber holder = high.newInstance();
        holder.transferFrom(high);
        holder.subtract(low);
        low.copyFrom(holder);
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processMultiplyEvent() {
        NaturalNumber high = this.model.top();
        NaturalNumber low = this.model.bottom();
        NaturalNumber holder = high.newInstance();
        holder.transferFrom(high);
        low.multiply(holder);
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processDivideEvent() {
        NaturalNumber high = this.model.top();
        NaturalNumber low = this.model.bottom();
        NaturalNumber holder = high.newInstance();
        holder.transferFrom(high);
        NaturalNumber r = holder.divide(low);
        low.copyFrom(holder);
        high.copyFrom(r);
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processPowerEvent() {
        NaturalNumber high = this.model.top();
        NaturalNumber low = this.model.bottom();
        high.power(low.toInt());
        low.transferFrom(high);
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processRootEvent() {
        NaturalNumber high = this.model.top();
        NaturalNumber low = this.model.bottom();
        high.root(low.toInt());
        low.transferFrom(high);
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processAddNewDigitEvent(int digit) {
        NaturalNumber low = this.model.bottom();
        low.multiplyBy10(digit);
        updateViewToMatchModel(this.model, this.view);

    }

}
