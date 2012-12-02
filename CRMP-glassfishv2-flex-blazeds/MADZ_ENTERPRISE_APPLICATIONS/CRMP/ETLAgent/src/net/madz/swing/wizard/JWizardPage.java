package net.madz.swing.wizard;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.LayoutManager;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import net.madz.swing.resources.ResourceFactory;
import net.madz.swing.view.util.GraphicFactory;
import net.madz.swing.view.util.TitleComponent;

public abstract class JWizardPage extends JPanel {
    private static final long serialVersionUID = 5897235812435777792L;
    public static final int MAX_INPUT_LENGTH = 128;
    
    public static String selectPath = null;

    public static void setSelectPath(String selectPath) {
        JWizardPage.selectPath = selectPath;
    }

    private static Icon errorIcon = net.madz.swing.resources.ResourceFactory.getInstance().createImageIcon(

    "bitmaps/errorIcon.png");

    private static Icon waringIcon = ResourceFactory.getInstance().createImageIcon(

    "bitmaps/warningIcon.png");

    // The JWizard parent
    protected JWizard dialogParent;

    // The step title
    JPanel titlePanel;
    String stepTitle;
    JLabel stepTitleLabel;

    // The content pane
    JPanel contentPane;

    // The back and next steps
    int backStep = -1;
    int nextStep = -1;

    // Flags the first time the component is added to a window
    boolean firstNotify = true;

    private JPanel infoCotainerPane;

    private JLabel infoLabel;

    private JPanel contentContainerPane;

    protected String lDomain = "WizardResource";

    /**
     * Creates a new JWizardPage with a double buffer and a flow layout. The flow layout is assigned to the panel
     * accessed through getContentPane().
     */
    public JWizardPage() {
        super();
        init(new FlowLayout());
    }

    /**
     * Create a new buffered JWizardPage with the specified layout manager. The layout is assigned to the panel accessed
     * through getContentPane().
     * 
     * @param layout The LayoutManager for the content pane.
     */
    public JWizardPage(LayoutManager layout) {
        super();
        init(layout);
    }

    /**
     * Creates a new JWizardPage a flow layout and the specified buffering strategy. If isDoubleBuffered is true, the
     * JWizardPage will use a double buffer. The layout is assigned to the panel accessed through getContentPane().
     * 
     * @param isDoubleBuffered True for double-buffering, which uses additional memory space to achieve fast,
     * flicker-free updates.
     */
    public JWizardPage(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        init(new FlowLayout());
    }

    /**
     * Creates a new JWizardPage with the specified layout manager and buffering strategy. The layout is assigned to the
     * panel accessed through getContentPane().
     * 
     * @param layout The LayoutManager for the content pane.
     * @param isDoubleBuffered True for double-buffering, which uses additional memory space to achieve fast,
     * flicker-free updates.
     */
    public JWizardPage(LayoutManager layout, boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        init(layout);
    }

    public void setNextButtonStatus(boolean enable) {
        dialogParent.setNextButtonStatus(enable);
    }

    public void setErrorInfo(String errorInfo) {

        if (errorInfo == null || errorInfo.trim().length() == 0) {
            if (infoLabel != null) {
                infoLabel.setIcon(null);
                infoLabel.setText("");
            }
            dialogParent.errorCleared();
        } else if (infoLabel != null && errorInfo.trim().length() > 0) {
            if (infoLabel != null) {
                infoLabel.setIcon(errorIcon);
                infoLabel.setText(errorInfo);
            }
            dialogParent.errorOccured();
        }

        // contentPane.invalidate();
        // contentPane.validate();
    }

    public void setWarningInfo(String waringInfo) {

        if (waringInfo == null || waringInfo.trim().length() == 0) {
            infoLabel.setIcon(null);
            infoLabel.setText("");
        } else if (infoLabel != null && waringInfo.trim().length() > 0) {
            infoLabel.setIcon(waringIcon);
            infoLabel.setText(waringInfo);
        }
        dialogParent.errorCleared();

        infoLabel.invalidate();
        infoLabel.validate();

        contentPane.invalidate();
        contentPane.validate();
    }

    // **********************************************************************
    // Public
    // **********************************************************************
    /**
     * Set the title to use for this step. Normally this title would be unique for each wizards step.
     * 
     * @param stepTitle The title to use for this step.
     */
    public void setStepTitle(String stepTitle) {
        this.stepTitle = stepTitle;
        stepTitleLabel.setText(stepTitle + ":");

        stepTitleLabel.invalidate();
        validate();
    }

    /**
     * Get the step title to use for this step.
     * 
     * @return The step title to use for this step.
     */
    public String getStepTitle() {
        return stepTitle;
    }

    /**
     * Get a JPanel to use for adding your own components to this WizardPanel. Do not add components directly to the
     * JWizardPage. The JPanel uses the layout given in the JWizardPage constructor.
     * 
     * @return The JPanel to use for adding components for this wizard step.
     */
    public JPanel getContentPane() {
        return contentPane;
    }

    /**
     * Get the wizard step to go to when the Back button is pressed.
     * 
     * @return The wizard step to go to when the Back button is pressed.
     */
    public int getBackStep() {
        return backStep;
    }

    /**
     * Set the wizard step to go to when the Back button is pressed. This should be set in the constructor of the
     * JWizardPage subclass since it determines whether the Back button is enabled or not.
     * 
     * @param backStep The wizard step to go to when the Back button is pressed.
     */
    public void setBackStep(int backStep) {
        this.backStep = backStep;
        JWizard dialog = getWizardParent();
        if (dialog != null) {
            dialog.setButtonStates();
        }
    }

    /**
     * Get the wizard step to go to when the Next button is pressed.
     * 
     * @return The wizard step to go to when the Next button is pressed.
     */
    public int getNextStep() {
        return nextStep;
    }

    /**
     * Set the wizard step to go to when the Next button is pressed. This should be set in the constructor of the
     * JWizardPage subclass since it determines whether the Next and Finish buttons are enabled or not.
     * 
     * @param nextStep The wizard step to go to when the Next button is pressed.
     */
    public void setNextStep(int nextStep) {
        this.nextStep = nextStep;
        JWizard dialog = getWizardParent();
        if (dialog != null) {
            dialog.setButtonStates();
        }
    }

    /**
     * Returns the JWizard in which this JWizardPage resides. This is valid only after the panel has been added to the
     * dialog.
     * 
     * @return The JWizard in which this JWizardPage resides.
     */
    public JWizard getWizardParent() {
        return dialogParent;
    }

    /**
     * Do not call directly.
     */
    public void addNotify() {
        if (firstNotify) {
            // Font font = new Font(WizardStyle.WIZARD_TITLE_FONT_NAME,
            // Font.PLAIN, WizardStyle.WIZARD_TITLE_FONT_SIZE);
            // font = font.deriveFont(Font.BOLD, font.getSize() * 14 / 10);
            stepTitleLabel.setFont(stepTitleLabel.getFont().deriveFont(Font.BOLD));
            firstNotify = false;
        }
        super.addNotify();
    }

    // WizardPanels are equal if they are the same object, so the default
    // equals() and hashCode() methods are acceptable. I'm not using the
    // paramString() method for debugging, so the default is OK.

    // **********************************************************************
    // Package Public
    // **********************************************************************
    /**
     * Set the JWizard parent for this JWizardPage.
     * 
     * @param dialogParent The JWizardPage parent for this JWizardPage.
     */
    protected void setWizardParent(JWizard dialogParent) {
        this.dialogParent = dialogParent;
    }

    /**
     * Calls makingVisible(). This allows the JWizard to call the protected method makingVisible().
     * 
     * @see #makingVisible()
     */
    void doMakingVisible() {
        makingVisible();
    }

    /**
     * Calls back(). This allows the JWizard to call the protected method back().
     * 
     * @see #back()
     */
    void doBack() {
        back();
    }

    /**
     * Calls next(). This allows the JWizard to call the protected method next().
     * 
     * @see #next()
     */
    void doNext() {
        next();
    }

    /**
     * Calls help(). This allows the JWizard to call the protected method help().
     * 
     * @see #help()
     */
    void doHelp() {
        help();
    }

    // **********************************************************************
    // Protected
    // **********************************************************************
    /**
     * Called just prior to making this panel visible. This is a hook in case information in the panel needs to be
     * created dynamically based on previous steps.
     */
    protected void makingVisible() {
    }

    /**
     * Called when the Back button is pressed. By default this displays the wizard step set by setBackStep().
     * 
     * @see #setBackStep(int)
     */
    protected void back() {
        dialogParent.goTo(getBackStep());
    }

    /**
     * Called when the Next button is pressed. By default this displays the wizard step set by setNextStep().
     * 
     * @see #setNextStep(int)
     */
    protected void next() {
        dialogParent.goTo(getNextStep());
    }

    /**
     * Called when the Help button is pressed. By default this does nothing.
     */
    protected void help() {
    }

    // **********************************************************************
    // Private
    // **********************************************************************
    /**
     * Initialize the JWizardPage.
     * 
     * @param layout The layout to use.
     */
    private void init(LayoutManager layout) {
        // Set the layout for the JWizardPage

        setLayout(new BorderLayout(0, 5));

        // Step title

        titlePanel = new JPanel(new BorderLayout(0, 5));
        titlePanel.add(new JSeparator(), BorderLayout.SOUTH);
        stepTitleLabel = GraphicFactory.createLabel(" ");
        titlePanel.add(stepTitleLabel);

        titlePanel = TitleComponent.getTitleComponent(stepTitleLabel, null);

        add(titlePanel, BorderLayout.NORTH);

        // Set the layout for the content area

        contentContainerPane = new JPanel(new BorderLayout());
        contentPane = new JPanel(layout);
        infoCotainerPane = new JPanel(new BorderLayout());

        contentContainerPane.add(contentPane, BorderLayout.CENTER);
        contentContainerPane.add(infoCotainerPane, BorderLayout.SOUTH);

        // Set the info panel
        infoLabel = GraphicFactory.createLabel(" ");

        infoLabel.setVerticalAlignment(SwingConstants.CENTER);
        infoLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.add(infoLabel, BorderLayout.WEST);
        infoCotainerPane.add(infoPanel, BorderLayout.SOUTH);

        add(contentContainerPane);

        createPartControl();

        contentPane.invalidate();
        contentPane.validate();

        final JPanel contentPane2 = this.getContentPane();
        Runnable doJob = new Runnable(){
            public void run() {
                // TODO Auto-generated method stub
                int width = (int) contentPane2.getSize().getWidth();
                Dimension dim = new Dimension(width, 80);
                infoLabel.setPreferredSize(dim);
                infoLabel.setMaximumSize(dim);
                infoLabel.setMinimumSize(dim);
            }
        };
        SwingUtilities.invokeLater(doJob);

        Runnable validateJob = new Runnable(){
            public void run() {
                validatePage();
            }
        };
        SwingUtilities.invokeLater(validateJob);

    }

    protected abstract void validatePage();

    protected void addComp(Component c, Container container, GridBagConstraints gbConstraints, int row, int column,
                           int numberOfRows, int numberOfColumn) {
        gbConstraints.gridx = column;
        gbConstraints.gridy = row;
        gbConstraints.gridwidth = numberOfColumn;
        gbConstraints.gridheight = numberOfRows;
        container.add(c, gbConstraints);
    }

    public abstract void createPartControl();
}
