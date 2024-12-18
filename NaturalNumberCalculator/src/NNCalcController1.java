
import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author Nabeu Habetaslassa
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

        /*
         * Update view of top and bottom display
         */
        NaturalNumber top = model.top();
        NaturalNumber bottom = model.bottom();
        boolean root = false;
        boolean power = false;
        boolean divide = false;
        boolean subtract = false;
        if (!bottom.isZero()) {
            divide = true;
        }
        if (bottom.compareTo(top) <= 0) {
            subtract = true;
        }
        if (bottom.compareTo(INT_LIMIT) <= 0) {
            power = true;
        }
        if (bottom.compareTo(TWO) >= 0 && bottom.compareTo(INT_LIMIT) <= 0) {
            root = true;
        }
        view.updatePowerAllowed(power);
        view.updateRootAllowed(root);
        view.updateDivideAllowed(divide);
        view.updateSubtractAllowed(subtract);
        view.updateBottomDisplay(bottom);
        view.updateTopDisplay(top);
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
         * Get bottom
         */
        NaturalNumber bottom = this.model.bottom();
        /*
         * clear
         */
        bottom.clear();
        /*
         * Update view
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSwapEvent() {
        /*
         * Get top and bottom
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * swap
         */
        NaturalNumber temp = top.newInstance();
        temp.transferFrom(top);
        top.transferFrom(bottom);
        bottom.transferFrom(temp);
        /*
         * Update view
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processEnterEvent() {

        /*
         * Get top and bottom
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        top.copyFrom(bottom);

        /*
         * Update view
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddEvent() {

        /*
         * Get top and bottom
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * add
         */
        bottom.add(top);
        top.clear();
        /*
         * Update view
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processSubtractEvent() {

        /*
         * Get top and bottom
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * subtract
         */
        top.subtract(bottom);
        bottom.transferFrom(top);
        /*
         * Update view
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processMultiplyEvent() {

        /*
         * Get top and bottom
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * multiply
         */
        bottom.multiply(top);
        top.clear();
        /*
         * Update view
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processDivideEvent() {

        /*
         * Get top and bottom
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * divide and get remainder
         */
        NaturalNumber remain = top.divide(bottom);
        bottom.transferFrom(top);
        top.transferFrom(remain);
        /*
         * Update view
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processPowerEvent() {

        /*
         * Get top and bottom
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        int pow = bottom.toInt();
        /*
         * power
         */
        top.power(pow);
        bottom.transferFrom(top);
        /*
         * Update view
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processRootEvent() {

        /*
         * Get top and bottom
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        /*
         * Get root
         */
        int root = bottom.toInt();

        /*
         * root
         */
        top.root(root);
        bottom.transferFrom(top);
        /*
         * Update view
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddNewDigitEvent(int digit) {

        /*
         * Get top and bottom
         */
        NaturalNumber bottom = this.model.bottom();
        /*
         * If 0 is inputed, add new value
         */
        if (bottom.toInt() == 0) {
            NaturalNumber num = new NaturalNumber2(digit);
            bottom.transferFrom(num);
        } else {
            String current = bottom.toString();
            current += digit;
            NaturalNumber num = new NaturalNumber2(current);
            bottom.transferFrom(num);
        }
        /*
         * Update view
         */
        updateViewToMatchModel(this.model, this.view);

    }

}
