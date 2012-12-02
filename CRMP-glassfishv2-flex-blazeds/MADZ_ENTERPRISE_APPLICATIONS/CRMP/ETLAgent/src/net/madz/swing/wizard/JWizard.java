package net.madz.swing.wizard;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import net.madz.swing.resources.ResourceFactory;
import net.madz.swing.view.util.GraphicFactory;
import net.madz.swing.view.util.TitleComponent;

public abstract class JWizard extends JDialog {
	private static final long serialVersionUID = -6121796233019747771L;
	private static final int BACK_BUTTON = 0;
	private static final int NEXT_BUTTON = 1;
	private static final int FINISH_BUTTON = 2;
	private static final int CANCEL_BUTTON = 3;
	private static final int HELP_BUTTON = 4;
	private String[] stepInfos;
	// The label which holds the image to display on the left side of the
	// wizard.
	private JPanel logoPanel;
	private JLabel captionLabel;
	private JLabel stepsLabel;

	// The panel to which JWizardPage's are added.
	private JPanel workArea;

	// The layout for the work area.
	private CardLayout cardLayout;

	// The current JWizardPage
	private JWizardPage currentWizard = null;
	private int currentStep = -1;
	private int lastStep = -1;
	private int panelCount = 0;

	// The button panel and buttons
	private JPanel buttonPanel;
	private JPanel buttons;
	private JButton buttonBack;
	private JButton buttonNext;
	private JButton buttonFinish;
	private JButton buttonCancel;
	private JButton buttonHelp;

	// True if the finish button should be enabled all the time
	private boolean enableEarlyFinish = false;

	// True if the cancel button is enabled on the final step
	private boolean enableCancelAtEnd = true;

	// True if the dialog should be centered on display
	private boolean isCentered = true;

	// True if the wizard finished
	private boolean isFinished = false;

	// The resource bundle

	private ImageIcon logoImage;

	protected Object context;

	// **********************************************************************
	// Constructors
	// **********************************************************************
	/**
	 * Creates a non-modal JWizard without a title and without a specified Frame owner. A shared, hidden frame will be set as the owner of
	 * the dialog.
	 * <p>
	 * This constructor sets the component's locale property to the value returned by JComponent.getDefaultLocale().
	 * 
	 * @throws HeadlessException
	 *         If GraphicsEnvironment.isHeadless() returns true.
	 */
	public JWizard() throws HeadlessException {
		super();
		// init();
	}

	/**
	 * Creates a non-modal JWizard without a title with the specified Frame as its owner. If owner is null, a shared, hidden frame will be
	 * set as the owner of the dialog.
	 * <p>
	 * This constructor sets the component's locale property to the value returned by JComponent.getDefaultLocale().
	 * 
	 * @param owner
	 *        The Frame owning the dialog.
	 * @throws HeadlessException
	 *         If GraphicsEnvironment.isHeadless() returns true.
	 */
	public JWizard(Frame owner) throws HeadlessException {
		super(owner);
		init();
	}

	/**
	 * Creates a modal or non-modal JWizard without a title and with the specified owner Frame. If owner is null, a shared, hidden frame
	 * will be set as the owner of the dialog.
	 * 
	 * This constructor sets the component's locale property to the value returned by JComponent.getDefaultLocale().
	 * 
	 * @param owner
	 *        The Frame owning the dialog.
	 * @param modal
	 *        True for a modal dialog, false for one that allows others windows to be active at the same time.
	 * @throws HeadlessException
	 *         If GraphicsEnvironment.isHeadless() returns true.
	 */
	public JWizard(Frame owner, boolean modal) throws HeadlessException {
		super(owner, modal);
		init();
	}

	/**
	 * Creates a non-modal JWizard with the specified title and with the specified owner frame. If owner is null, a shared, hidden frame
	 * will be set as the owner of the dialog.
	 * <p>
	 * This constructor sets the component's locale property to the value returned by JComponent.getDefaultLocale().
	 * 
	 * @param owner
	 *        The Frame owning the dialog.
	 * @param title
	 *        The String to display in the dialog's title bar.
	 * @throws HeadlessException
	 *         If GraphicsEnvironment.isHeadless() returns true.
	 */
	public JWizard(Frame owner, String title) throws HeadlessException {
		super(owner, title);
		init();
	}

	/**
	 * Creates a modal or non-modal JWizard with the specified title and the specified owner Frame. If owner is null, a shared, hidden frame
	 * will be set as the owner of this dialog. All constructors defer to this one.
	 * <p>
	 * NOTE: Any popup components (JComboBox, JPopupMenu, JMenuBar) created within a modal dialog will be forced to be lightweight.
	 * <p>
	 * This constructor sets the component's locale property to the value returned by JComponent.getDefaultLocale().
	 * 
	 * @param owner
	 *        The Frame owning the dialog.
	 * @param title
	 *        The String to display in the dialog's title bar.
	 * @param modal
	 *        True for a modal dialog, false for one that allows others windows to be active at the same time.
	 * @throws HeadlessException
	 *         If GraphicsEnvironment.isHeadless() returns true.
	 */
	public JWizard(Frame owner, String title, boolean modal) throws HeadlessException {
		super(owner, title, modal);
		init();
	}

	/**
	 * Creates a modal or non-modal JWizard with the specified title, owner Frame, and GraphicsConfiguration.
	 * <p>
	 * NOTE: Any popup components (JComboBox, JPopupMenu, JMenuBar) created within a modal dialog will be forced to be lightweight.
	 * <p>
	 * This constructor sets the component's locale property to the value returned by JComponent.getDefaultLocale.
	 * 
	 * @param owner
	 *        The Frame owning the dialog.
	 * @param title
	 *        The String to display in the dialog's title bar.
	 * @param modal
	 *        True for a modal dialog, false for one that allows others windows to be active at the same time.
	 * @param gc
	 *        The GraphicsConfiguration of the target screen device. If gc is null, the same GraphicsConfiguration as the owning Frame is
	 *        used.
	 * @throws HeadlessException
	 *         If GraphicsEnvironment.isHeadless() returns true.
	 */
	public JWizard(Frame owner, String title, boolean modal, GraphicsConfiguration gc) throws HeadlessException {
		super(owner, title, modal, gc);
		init();
	}

	/**
	 * Creates a non-modal JWizard without a title with the specified Dialog as its owner. If owner is null, a shared, hidden frame will be
	 * set as the owner of the dialog.
	 * <p>
	 * This constructor sets the component's locale property to the value returned by JComponent.getDefaultLocale().
	 * 
	 * @param owner
	 *        The Dialog owning the dialog.
	 * @throws HeadlessException
	 *         If GraphicsEnvironment.isHeadless() returns true.
	 */
	public JWizard(Dialog owner) throws HeadlessException {
		super(owner);
		init();
	}

	/**
	 * Creates a modal or non-modal JWizard without a title and with the specified owner Dialog. If owner is null, a shared, hidden frame
	 * will be set as the owner of the dialog.
	 * 
	 * This constructor sets the component's locale property to the value returned by JComponent.getDefaultLocale().
	 * 
	 * @param owner
	 *        The Dialog owning the dialog.
	 * @param modal
	 *        True for a modal dialog, false for one that allows others windows to be active at the same time.
	 * @throws HeadlessException
	 *         If GraphicsEnvironment.isHeadless() returns true.
	 */
	public JWizard(Dialog owner, boolean modal) throws HeadlessException {
		super(owner, modal);
		init();
	}

	/**
	 * Creates a non-modal JWizard with the specified title and with the specified owner frame. If owner is null, a shared, hidden frame
	 * will be set as the owner of the dialog.
	 * <p>
	 * This constructor sets the component's locale property to the value returned by JComponent.getDefaultLocale().
	 * 
	 * @param owner
	 *        The Dialog owning the dialog.
	 * @param title
	 *        The String to display in the dialog's title bar.
	 * @throws HeadlessException
	 *         If GraphicsEnvironment.isHeadless() returns true.
	 */
	public JWizard(Dialog owner, String title) throws HeadlessException {
		super(owner, title);
		init();
	}

	/**
	 * Creates a modal or non-modal JWizard with the specified title and the specified owner Dialog. If owner is null, a shared, hidden
	 * frame will be set as the owner of this dialog. All constructors defer to this one.
	 * <p>
	 * NOTE: Any popup components (JComboBox, JPopupMenu, JMenuBar) created within a modal dialog will be forced to be lightweight.
	 * <p>
	 * This constructor sets the component's locale property to the value returned by JComponent.getDefaultLocale().
	 * 
	 * @param owner
	 *        The Dialog owning the dialog.
	 * @param title
	 *        The String to display in the dialog's title bar.
	 * @param modal
	 *        True for a modal dialog, false for one that allows others windows to be active at the same time.
	 * @throws HeadlessException
	 *         If GraphicsEnvironment.isHeadless() returns true.
	 */
	public JWizard(Dialog owner, String title, boolean modal) throws HeadlessException {
		super(owner, title, modal);
		init();
	}

	/**
	 * Creates a modal or non-modal JWizard with the specified title, owner Dialog, and GraphicsConfiguration.
	 * <p>
	 * NOTE: Any popup components (JComboBox, JPopupMenu, JMenuBar) created within a modal dialog will be forced to be lightweight.
	 * <p>
	 * This constructor sets the component's locale property to the value returned by JComponent.getDefaultLocale.
	 * 
	 * @param owner
	 *        The Dialog owning the dialog.
	 * @param title
	 *        The String to display in the dialog's title bar.
	 * @param modal
	 *        True for a modal dialog, false for one that allows others windows to be active at the same time.
	 * @param gc
	 *        The GraphicsConfiguration of the target screen device. If gc is null, the same GraphicsConfiguration as the owning Dialog is
	 *        used.
	 * @throws HeadlessException
	 *         If GraphicsEnvironment.isHeadless() returns true.
	 */
	public JWizard(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) throws HeadlessException {
		super(owner, title, modal, gc);
		init();
	}

	/**
	 * Add an image which displays on the left side of the wizard. By default, no image is displayed. This must be set before the dialog is
	 * made visible.
	 * 
	 * @param icon
	 *        The icon representing the image to display. If null, no image is displayed.
	 */
	public void setWizardIcon(ImageIcon icon) {
		// If null, remove any existing logo panel
		this.logoImage = icon;
		if (icon == null) {
			if (logoPanel != null) {
				remove(logoPanel);
				logoPanel = null;
			}
		} // If not null, add it or replace an existing label
		else {
			if (logoPanel != null) {
				remove(logoPanel);
			}
		}
	}

	// display all step informations on the left side
	public void setWizardSetpsInfo(String[] infos) {
		if (infos == null || infos.length < 0) {
			return;
		}
		stepInfos = infos;
		String displaySteps = "<html>";
		for (int i = 0; i < infos.length; i++) {
			if (i == currentStep) {
				displaySteps += "<b>" + (i + 1) + ": " + infos[i] + "</b> <br>";
			} else {
				displaySteps += "" + (i + 1) + ": " + infos[i] + "<br>";
			}
		}
		displaySteps += "</html>";

		BorderLayout logoLayout = new BorderLayout(0, 4);
		BorderLayout stepsLayout = new BorderLayout(0, 3);
		BorderLayout layout4 = new BorderLayout(0, 3);
		BorderLayout layout5 = new BorderLayout(0, 3);
		BorderLayout stepsContainerLayout = new BorderLayout();
		if (logoPanel == null) {
			logoPanel = new JPanel(logoLayout);
			logoPanel.setBackground(Color.WHITE);
			getContentPane().add(logoPanel, BorderLayout.WEST);
		} else {
			logoPanel.removeAll();
			logoPanel.setLayout(logoLayout);
		}
		// JPanel captionPanel = new JPanel(captionLayout);
		JPanel stepsPanel = new JPanel(stepsLayout);
		JPanel stepsContainerPanel = new JPanel(stepsContainerLayout);
		JPanel imageConainerPanel = new JPanel(layout5);
		JPanel imagePanel = new LogoPanel(layout4, logoImage);

		// captionPanel.setBackground(Color.WHITE);
		stepsPanel.setBackground(Color.WHITE);
		stepsContainerPanel.setBackground(Color.WHITE);
		imageConainerPanel.setBackground(Color.WHITE);
		imagePanel.setBackground(Color.WHITE);

		imagePanel.setPreferredSize(new Dimension(logoImage.getIconWidth() * 3 / 4, logoImage.getIconHeight()));

		captionLabel = GraphicFactory.createLabel("Steps:");
		// Font font = new
		// Font(WizardStyle.WIZARD_TITLE_FONT_NAME,Font.PLAIN,WizardStyle.WIZARD_TITLE_FONT_SIZE);
		captionLabel.setFont(captionLabel.getFont().deriveFont(Font.BOLD));

		JPanel captionPanel = TitleComponent.getTitleComponent(captionLabel, Color.white);

		stepsLabel = GraphicFactory.createStepLabel(displaySteps, stepInfos.length);
		//
		// stepsLabel.setFont(new Font(WizardStyle.WIZARD_STEP_FONT_NAME,
		// Font.PLAIN, WizardStyle.WIZARD_STEP_FONT_SIZE));
		stepsContainerPanel.add(stepsLabel, BorderLayout.CENTER);
		JPanel leftEmptyPanel = new JPanel();
		leftEmptyPanel.setBackground(Color.WHITE);
		JPanel rightEmptyPanel = new JPanel();
		rightEmptyPanel.setBackground(Color.WHITE);
		stepsContainerPanel.add(leftEmptyPanel, BorderLayout.WEST);
		stepsContainerPanel.add(rightEmptyPanel, BorderLayout.EAST);

		logoPanel.add(captionPanel, BorderLayout.NORTH);
		logoPanel.add(stepsPanel);
		stepsPanel.add(stepsContainerPanel, BorderLayout.NORTH);
		stepsPanel.add(imageConainerPanel, BorderLayout.SOUTH);
		imageConainerPanel.add(imagePanel, BorderLayout.EAST);

		logoPanel.invalidate();
		logoPanel.validate();
	}

	/**
	 * Add a panel representing a step in the wizard. Since removing a panel would force a renumbering of the remaining panels and since you
	 * have flexible sequencing control, there is no matching removeWizardPanel() method.
	 * 
	 * @param panel
	 *        The JWizardPage to add
	 */
	public void addWizardPage(JWizardPage panel) {
		if (currentWizard == null) {
			currentWizard = panel;
			currentStep = 0;
		}
		workArea.add(panel, Integer.toString(panelCount++));
		panel.setWizardParent(this);
	}

	/**
	 * This adds a help button to the wizard. When the button is pressed, the help() method is called.
	 * 
	 * @see #help()
	 */
	public void addHelpButton() {
		if (buttonHelp == null) {
			buttonHelp = new JButton(new ButtonAction("HelpButton", "HelpButtonMnemonic", "HelpButtonAccelerator", "images/Help16.gif",
					"HelpButtonShort", "HelpButtonLong", HELP_BUTTON));
			buttons.add(buttonHelp);
		}
	}

	/**
	 * If this method is called, the Finish button is enabled immediately. By default, it is enabled only on the last step (any step where
	 * the next JWizardPage step is -1).
	 */
	public void setEarlyFinish() {
		enableEarlyFinish = true;
	}

	/**
	 * Returns true if the wizard finished (the user pressed the Finish) button). Returns false otherwise (either the wizard hasn't finished
	 * or the user pressed Cancel to exit).
	 * 
	 * @return True if the wizard finished.
	 */
	public boolean isFinished() {
		return isFinished;
	}

	/**
	 * If this method is called, the Cancel button is disabled when on the last step. If setEarlyFinish() is called, it is still disabled
	 * only on the last step.
	 */
	public void disableCancelAtEnd() {
		enableCancelAtEnd = false;
	}

	/**
	 * Don't center the dialog. This method must be called before the dialog is made visible. The default behavior is to center the dialog
	 * over its parent, or on the screen if no parent was given.
	 */
	public void disableCentering() {
		isCentered = false;
	}

	/**
	 * Returns the current step being displayed by the wizard. Steps start at 0. If no step is yet displayed, a -1 is returned.
	 * 
	 * @return The current step being displayed by the wizard.
	 */
	public int getCurrentStep() {
		return currentStep;
	}

	/**
	 * Returns the last step displayed by the wizard. Steps start at 0. If there is no previous step yet, -1 is returned.
	 * 
	 * @return The last step being displayed by the wizard.
	 */
	public int getLastStep() {
		return lastStep;
	}

	/**
	 * @deprecated As of JDK version 1.1, replaced by setVisible(boolean).
	 */
	public void show() {
		goTo(0);

		if (isCentered) {
			Dimension screenSize = getToolkit().getScreenSize();
			Dimension parentSize = getParent().getSize();
			Point parentLocation = getParent().getLocation();

			// If the parent Frame is invisible, we center the dialog on
			// the screen

			if (!getParent().isVisible()) {
				parentSize = getToolkit().getScreenSize();
				parentLocation.setLocation(0, 0);
			}

			Dimension size = getSize();

			int x = parentLocation.x + (parentSize.width - size.width) / 2;
			int y = parentLocation.y + (parentSize.height - size.height) / 2;

			// Make sure the dialog is placed completely on the screen (as
			// long as it is smaller than the screen size)

			if (size.width < screenSize.width && x + size.width > screenSize.width) {
				x = screenSize.width - size.width;
			}
			if (size.height < screenSize.height && y + size.height > screenSize.height) {
				y = screenSize.height - size.height;
			}
			if (x < 0) {
				x = 0;
			}
			if (y < 0) {
				y = 0;
			}

			setBounds(x, y, size.width, size.height);
		}

		super.show();
	}

	/**
	 * Set the sensitivity of each button based on the back and next step values. This should be called when changing steps or when the back
	 * or next button values are changed.
	 */
	void setButtonStates() {
		int backStep = currentWizard.getBackStep();
		int nextStep = currentWizard.getNextStep();

		boolean atBegin = backStep < 0 || backStep >= panelCount;

		boolean atEnd = nextStep < 0 || nextStep >= panelCount;

		buttonBack.setEnabled(!atBegin);
		buttonNext.setEnabled(!atEnd);
		buttonFinish.setEnabled(enableEarlyFinish || atEnd);
		buttonCancel.setEnabled(!atEnd || enableCancelAtEnd);

		// Set the default button

		if (buttonNext.isEnabled()) {
			getRootPane().setDefaultButton(buttonNext);
		} else if (buttonFinish.isEnabled()) {
			getRootPane().setDefaultButton(buttonFinish);
		} else if (buttonBack.isEnabled()) {
			getRootPane().setDefaultButton(buttonBack);
		} else {
			getRootPane().setDefaultButton(null);
		}
	}

	/**
	 * Display the JWizardPage with the given step number. This method is package public so that JWizardPage can call it. The switchToStep()
	 * method may override the step choice. </code></pre>
	 * 
	 * @param step
	 *        The step number of the JWizardPage to display.
	 * @see #switchToStep(int,int)
	 */
	void goTo(int step) {
		// Give the user a last chance to change things

		step = switchToStep(currentStep, step);

		// We can't do anything if we're outside the valid range

		if (step < 0 || step >= panelCount) {
			return;
		}

		// Save the current step as the previous step

		lastStep = currentStep;

		currentWizard = (JWizardPage) workArea.getComponent(step);
		currentStep = step;
		currentWizard.doMakingVisible();
		cardLayout.show(workArea, Integer.toString(step));

		// The panel may have just been created or modified in the
		// doMakingVisible() method. If so, the CardLayout's original
		// guess at the window size may be too small. We increase the size
		// if we have to -- but we never decrease the size

		Dimension prefSize = getPreferredSize();
		Dimension curSize = getSize();
		if (prefSize.width > curSize.width || prefSize.height > curSize.height) {
			Dimension newSize = new Dimension(Math.max(prefSize.width, curSize.width), Math.max(prefSize.height, curSize.height));
			setSize(newSize);
			invalidate();
			validate();
		}
		if (stepInfos != null && stepInfos.length > 0) {
			setWizardSetpsInfo(stepInfos);
		}
		// Set the button states

		setButtonStates();
	}

	/**
	 * Called when the Back button is pressed. This calls the back() method in the current JWizardPage.
	 * 
	 * @see JWizardPage#back()
	 */
	protected void back() {
		if (currentWizard != null) {
			currentWizard.doBack();

		}
	}

	/**
	 * Called when the Next button is pressed. This calls the next() method in the current JWizardPage.
	 * 
	 * @see JWizardPage#next()
	 */
	protected void next() {
		if (currentWizard != null) {
			currentWizard.doNext();
		}
	}

	/**
	 * Called when the Finish button is pressed. This calls dispose(). You will probably want to override this.
	 */
	protected void finish() {
		isFinished = true;
		dispose();
	}

	/**
	 * Called when the Cancel button is pressed. This calls dispose().
	 */
	protected void cancel() {
		dispose();
	}

	/**
	 * Called when the Help button is pressed. This calls the help() method in the current JWizardPage. If the help text is the same for all
	 * panels, you will want to override this.
	 */
	protected void help() {
		if (currentWizard != null) {
			currentWizard.doHelp();
		}
	}

	/**
	 * This method is called just prior to switching from one step to another (after any next() or back() method is called). It receives the
	 * current and new indices. By default, it returns the new index. You can override the method if you need to control sequencing from
	 * this JWizard class (normally, each step decides what the back and next steps should be).
	 * 
	 * @param currentIndex
	 *        The index of the current JWizardPage.
	 * @param newIndex
	 *        The index of the JWizardPage we are about to display.
	 * @return The index of the JWizardPage to display.
	 */
	protected int switchToStep(int currentIndex, int newIndex) {
		return newIndex;
	}

	/**
	 * Initialize the JWizard.
	 */
	private void init() {
		this.setModal(true);
		getContentPane().setLayout(new BorderLayout(5, 2));

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				if (buttonCancel.isEnabled()) {
					cancel();
				}
			}
		});

		createButtons();

		createContentContainer();

		createWizardPages();

		createLogo();

	}

	public void initializeContext(Object context) {
		this.context = context;
		init();
	}

	public void initializeContext() {
		init();
	}

	private void createLogo() {
		setWizardIcon(ResourceFactory.getInstance().createImageIcon("bitmaps/WizardIcon.PNG"));
		Component[] pages = workArea.getComponents();

		if (pages != null) {
			String[] infos = new String[pages.length];
			for (int i = 0; i < pages.length; i++) {
				infos[i] = ((JWizardPage) pages[i]).getStepTitle();
			}
			setWizardSetpsInfo(infos);
		}
		// logoPanel.setPreferredSize(new Dimension(logoImage.getIconWidth(),
		// 0));
	}

	private void createContentContainer() {
		workArea = new JPanel();
		cardLayout = new CardLayout();
		workArea.setLayout(cardLayout);
		getContentPane().add(workArea);
	}

	private void createButtons() {
		// Buttons

		buttonBack = GraphicFactory.createActionButton(new ButtonAction("BackButton", "BackButtonMnemonic", "BackButtonAccelerator",
				"images/Back16.gif", "BackButtonShort", "BackButtonLong", BACK_BUTTON));

		buttonNext = GraphicFactory.createActionButton(new ButtonAction("NextButton", "NextButtonMnemonic", "NextButtonAccelerator",
				"images/Forward16.gif", "NextButtonShort", "NextButtonLong", NEXT_BUTTON));
		buttonFinish = GraphicFactory.createActionButton(new ButtonAction("FinishButton", "FinishButtonMnemonic",
				"FinishButtonAccelerator", null, "FinishButtonShort", "FinishButtonLong", FINISH_BUTTON));
		buttonCancel = GraphicFactory.createActionButton(new ButtonAction("CancelButton", "CancelButtonMnemonic",
				"CancelButtonAccelerator", null, "CancelButtonShort", "CancelButtonLong", CANCEL_BUTTON));

		// buttonBack = new JButton(new ButtonAction("BackButton",
		// "BackButtonMnemonic", "BackButtonAccelerator", "images/Back16.gif",
		// "BackButtonShort", "BackButtonLong", BACK_BUTTON));
		// buttonNext = new JButton(new ButtonAction("NextButton",
		// "NextButtonMnemonic", "NextButtonAccelerator",
		// "images/Forward16.gif",
		// "NextButtonShort", "NextButtonLong", NEXT_BUTTON));
		// buttonFinish = new JButton(new ButtonAction("FinishButton",
		// "FinishButtonMnemonic", "FinishButtonAccelerator", null,
		// "FinishButtonShort",
		// "FinishButtonLong", FINISH_BUTTON));
		// buttonCancel = new JButton(new ButtonAction("CancelButton",
		// "CancelButtonMnemonic", "CancelButtonAccelerator", null,
		// "CancelButtonShort",
		// "CancelButtonLong", CANCEL_BUTTON));

		buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		buttons.add(buttonBack);
		buttons.add(buttonNext);
		buttons.add(buttonFinish);
		buttons.add(buttonCancel);

		buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(new JSeparator(), BorderLayout.NORTH);
		buttonPanel.add(buttons);

		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	protected abstract void createWizardPages();

	// ButtonAction
	private class ButtonAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		ButtonAction(String name, String mnemonic, String accelerator, String imageName, String shortDescription, String longDescription,
				int actionId) {
			if (name != null) {
				putValue(Action.NAME, ResourceFactory.getInstance().getString("WizardBundle", name));
			}

			if (mnemonic != null) {
				putValue(Action.MNEMONIC_KEY, Integer.valueOf(ResourceFactory.getInstance().getString("WizardBundle", mnemonic).charAt(0)));
			}

			if (accelerator != null) {
				putValue(Action.ACCELERATOR_KEY,
						KeyStroke.getKeyStroke(ResourceFactory.getInstance().getString("WizardBundle", accelerator)));
			}

			URL url = null;
			if (imageName != null) {
				url = this.getClass().getResource(imageName);
				if (url != null) {
					putValue(Action.SMALL_ICON, new ImageIcon(url));
				}
			}

			if (shortDescription != null) {
				putValue(Action.SHORT_DESCRIPTION, ResourceFactory.getInstance().getString("WizardBundle", shortDescription));
			}

			if (longDescription != null) {
				putValue(Action.LONG_DESCRIPTION, ResourceFactory.getInstance().getString("WizardBundle", longDescription));
			}
			putValue("buttonAction", Integer.valueOf(actionId));
		}

		public void actionPerformed(ActionEvent e) {
			Integer value = (Integer) getValue("buttonAction");
			switch (value.intValue()) {
			case BACK_BUTTON:
				back();
				break;
			case NEXT_BUTTON:
				next();
				break;
			case FINISH_BUTTON:
				finish();
				break;
			case CANCEL_BUTTON:
				cancel();
				break;
			case HELP_BUTTON:
				help();
				break;
			}
		}
	}

	public void open() {
		setModal(true);
		pack();
		setVisible(true);
	}

	private boolean errorOccured;
	private boolean oldNextState;
	private boolean oldFinishState;

	public void setNextButtonStatus(boolean enable) {
		buttonNext.setEnabled(enable);
	}

	public void errorCleared() {
		if (errorOccured) {
			buttonFinish.setEnabled(oldFinishState);
			buttonNext.setEnabled(oldNextState);
			errorOccured = false;
		}
	}

	public void errorOccured() {
		errorOccured = true;
		oldFinishState |= buttonFinish.isEnabled();
		oldNextState |= buttonNext.isEnabled();
		if (oldFinishState) {
			buttonFinish.setEnabled(false);
		}

		if (oldNextState) {
			buttonNext.setEnabled(false);
		}
	}
	
	public void disableFinishButton() {
		buttonFinish.setEnabled(false);
	}
	
	public void enableFinishButton() {
		buttonFinish.setEnabled(true);
	}

}
