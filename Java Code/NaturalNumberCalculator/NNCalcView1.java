import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import components.naturalnumber.NaturalNumber;

/**
 * View class.
 *
 * @author Ryan McKinley
 */
public final class NNCalcView1 extends JFrame implements NNCalcView {

    /**
     * Controller object registered with this view to observe user-interaction
     * events.
     */
    private NNCalcController controller;

    /**
     * State of user interaction: last event "seen".
     */
    private enum State {
        /**
         * Last event was clear, enter, another operator, or digit entry, resp.
         */
        SAW_CLEAR, SAW_ENTER_OR_SWAP, SAW_OTHER_OP, SAW_DIGIT
    }

    /**
     * State variable to keep track of which event happened last; needed to
     * prepare for digit to be added to bottom operand.
     */
    private State currentState;

    /**
     * Text areas.
     */
    private final JTextArea high, low;

    /**
     * Operator and related buttons.
     */
    private final JButton clear, swap, enter, add, subt, mult, divide, power,
            root;

    /**
     * Digit entry buttons.
     */
    private final JButton[] digit;

    /**
     * Useful constants.
     */
    private static final int TEXT_AREA_HEIGHT = 5, TEXT_AREA_WIDTH = 20,
            DIGIT_BUTTONS = 10, MAIN_BUTTON_PANEL_GRID_ROWS = 4,
            MAIN_BUTTON_PANEL_GRID_COLUMNS = 4, SIDE_BUTTON_PANEL_GRID_ROWS = 3,
            SIDE_BUTTON_PANEL_GRID_COLUMNS = 1, CALC_GRID_ROWS = 3,
            CALC_GRID_COLUMNS = 1;

    /**
     * Default constructor.
     */
    public NNCalcView1() {
        // Create the JFrame being extended

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Natural Number Calculator");

        // Set up the GUI widgets --------------------------------------------

        /*
         * Set up initial state of GUI to behave like last event was "Clear";
         * currentState is not a GUI widget per se, but is needed to process
         * digit button events appropriately
         */
        this.currentState = State.SAW_CLEAR;

        this.high = new JTextArea("", TEXT_AREA_HEIGHT, TEXT_AREA_WIDTH);
        this.low = new JTextArea("", TEXT_AREA_HEIGHT, TEXT_AREA_WIDTH);
        this.clear = new JButton("Clear");
        this.swap = new JButton("Swap");
        this.enter = new JButton("Enter");
        this.add = new JButton("+");
        this.subt = new JButton("-");
        this.mult = new JButton("*");
        this.divide = new JButton("/");
        this.power = new JButton(" Power");
        this.root = new JButton(" Root");
        this.digit = new JButton[DIGIT_BUTTONS];
        for (int i = 0; i < DIGIT_BUTTONS; i++) {
            this.digit[i] = new JButton("" + i + "");
        }
        this.high.setEditable(false);
        this.high.setLineWrap(true);
        this.high.setWrapStyleWord(true);
        this.low.setEditable(false);
        this.low.setLineWrap(true);
        this.low.setWrapStyleWord(true);

        JScrollPane highPane = new JScrollPane(this.high);
        JScrollPane lowPane = new JScrollPane(this.low);

        JPanel buttons = new JPanel(new GridLayout(MAIN_BUTTON_PANEL_GRID_ROWS,
                MAIN_BUTTON_PANEL_GRID_COLUMNS));

        buttons.add(this.digit[7]);
        buttons.add(this.digit[8]);
        buttons.add(this.digit[9]);
        buttons.add(this.add);
        buttons.add(this.digit[4]);
        buttons.add(this.digit[5]);
        buttons.add(this.digit[6]);
        buttons.add(this.subt);
        buttons.add(this.digit[1]);
        buttons.add(this.digit[2]);
        buttons.add(this.digit[3]);
        buttons.add(this.mult);
        buttons.add(this.digit[0]);
        buttons.add(this.power);
        buttons.add(this.root);
        buttons.add(this.divide);

        JPanel altPanel = new JPanel(new GridLayout(SIDE_BUTTON_PANEL_GRID_ROWS,
                SIDE_BUTTON_PANEL_GRID_COLUMNS));

        altPanel.add(this.clear);
        altPanel.add(this.swap);
        altPanel.add(this.enter);

        JPanel mainPanel = new JPanel(new FlowLayout());

        mainPanel.add(buttons);
        mainPanel.add(altPanel);

        this.setLayout(new GridLayout(CALC_GRID_ROWS, CALC_GRID_COLUMNS));

        this.add(highPane);
        this.add(lowPane);
        this.add(mainPanel);

        this.clear.addActionListener(this);
        this.swap.addActionListener(this);
        this.enter.addActionListener(this);
        this.add.addActionListener(this);
        this.subt.addActionListener(this);
        this.mult.addActionListener(this);
        this.divide.addActionListener(this);
        this.power.addActionListener(this);
        this.root.addActionListener(this);
        for (int i = 0; i < DIGIT_BUTTONS; i++) {
            this.digit[i].addActionListener(this);

        }

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void registerObserver(NNCalcController controller) {

        this.controller = controller;

    }

    @Override
    public void updateTopDisplay(NaturalNumber n) {

        this.high.setText(n.toString());

    }

    @Override
    public void updateBottomDisplay(NaturalNumber n) {

        this.low.setText(n.toString());

    }

    @Override
    public void updateSubtractAllowed(boolean allowed) {

        this.subt.setEnabled(allowed);

    }

    @Override
    public void updateDivideAllowed(boolean allowed) {

        this.divide.setEnabled(allowed);

    }

    @Override
    public void updatePowerAllowed(boolean allowed) {

        this.power.setEnabled(allowed);

    }

    @Override
    public void updateRootAllowed(boolean allowed) {

        this.root.setEnabled(allowed);

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        /*
         * Set cursor to indicate computation on-going; this matters only if
         * processing the event might take a noticeable amount of time as seen
         * by the user
         */
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        /*
         * Determine which event has occurred that we are being notified of by
         * this callback; in this case, the source of the event (i.e, the widget
         * calling actionPerformed) is all we need because only buttons are
         * involved here, so the event must be a button press; in each case,
         * tell the controller to do whatever is needed to update the model and
         * to refresh the view
         */
        Object source = event.getSource();
        if (source == this.clear) {
            this.controller.processClearEvent();
            this.currentState = State.SAW_CLEAR;
        } else if (source == this.swap) {
            this.controller.processSwapEvent();
            this.currentState = State.SAW_ENTER_OR_SWAP;
        } else if (source == this.enter) {
            this.controller.processEnterEvent();
            this.currentState = State.SAW_ENTER_OR_SWAP;
        } else if (source == this.add) {
            this.controller.processAddEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (source == this.subt) {
            this.controller.processSubtractEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (source == this.mult) {
            this.controller.processMultiplyEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (source == this.divide) {
            this.controller.processDivideEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (source == this.power) {
            this.controller.processPowerEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (source == this.root) {
            this.controller.processRootEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else {
            for (int i = 0; i < DIGIT_BUTTONS; i++) {
                if (source == this.digit[i]) {
                    switch (this.currentState) {
                        case SAW_ENTER_OR_SWAP:
                            this.controller.processClearEvent();
                            break;
                        case SAW_OTHER_OP:
                            this.controller.processEnterEvent();
                            this.controller.processClearEvent();
                            break;
                        default:
                            break;
                    }
                    this.controller.processAddNewDigitEvent(i);
                    this.currentState = State.SAW_DIGIT;
                    break;
                }
            }
        }
        /*
         * Set the cursor back to normal (because we changed it at the beginning
         * of the method body)
         */
        this.setCursor(Cursor.getDefaultCursor());
    }

}
