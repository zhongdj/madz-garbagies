package net.madz.swing.view.util;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.ComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.SizeRequirements;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import net.madz.swing.view.table.ChooseList;
import net.madz.swing.view.table.TableSorter;

public class GraphicFactory {
	public static final boolean NOT_FOCUSABLE = false;
	public static final boolean FOCUSABLE = true;
	static final Color _SUBSECTION_LINE_COLOR = new Color(123, 123, 123);
	public static final int HORIZONTALSPACEWITHBORDER = 10;
	public static final int HUGEHORIZONTALSPACEWITHBORDER = 12;
	public static final int VERTICALSPACEWITHBORDER = 10;
	public static final int HUGEVERTICALSPACEWITHBORDER = 14;
	public static final int SMALLVERTICALSPACEWITHBORDER = 3;
	public static final int SMALLVERTICALSPACEBETWEEN = 5;

	static String MotifLookAndFeelName = "CDE/Motif";

	static SizeRequirements _defaultHInterval = new SizeRequirements(2, 5,
			3333, 0.5f);
	static SizeRequirements _defaultVInterval = new SizeRequirements(2, 6,
			3333, 0.5f);
	static SizeRequirements _default2Border = new SizeRequirements(2, 10, 3333,
			0.5f);

	static SizeRequirements _defaultHIntervalMotif = new SizeRequirements(1, 2,
			3333, 0.5f);
	static SizeRequirements _defaultVIntervalMotif = new SizeRequirements(1, 2,
			3333, 0.5f);
	static SizeRequirements _default2BorderMotif = new SizeRequirements(1, 5,
			3333, 0.5f);

	public static void initForLookAndFeel() {
		String lookName = UIManager.getLookAndFeel().getName();
		if (lookName.equals(MotifLookAndFeelName)) {
			_defaultHInterval = _defaultHIntervalMotif;
			_defaultVInterval = _defaultVIntervalMotif;
			_default2Border = _default2BorderMotif;
		}
	}

	public static JButton createTextButton(String fTitle) {
		JButton lRes = new JButton(fTitle);

		Dimension lPref = lRes.getPreferredSize();
		int width = lPref.width;
		int height = lPref.height;
		height -= 5;
		if (width <= 90)
			width = 90;

		lRes.setPreferredSize(new Dimension(width, height));
		lRes.setMaximumSize(new Dimension(3000, height));
		return lRes;
	}

	public static JButton createIconButton(Icon fIcon) {
		JButton lRes = new JButton(fIcon);

		Border lBorder = lRes.getBorder();
		if (lBorder instanceof CompoundBorder) {
			CompoundBorder lCompBorder = (CompoundBorder) lBorder;

			lRes.setBorder(lCompBorder.getOutsideBorder());
		}

		return lRes;
	}

	public static JButton createTextIconButton(String fTitle, Icon fIcon) {
		JButton lRes = new JButton(fTitle, fIcon);
		// see createTextButton
		Dimension lPref = lRes.getPreferredSize();
		int width = lPref.width;
		int height = lPref.height;
		height -= 5;

		if (height < 22)
			height = 22;
		if (width <= 90)
			width = 90;
		lRes.setPreferredSize(new Dimension(width, height));
		lRes.setMaximumSize(new Dimension(3000, height));
		return lRes;
	}

	public static JButton createActionButton(Action fAction) {
		JButton lRes = new JButton();
		lRes.setText((String) fAction.getValue(Action.NAME));
		lRes.addActionListener(fAction);
		Dimension lPref = lRes.getPreferredSize();
		lRes.setPreferredSize(new Dimension(90, lPref.height - 5));
		return lRes;
	}

	public static JButton createActionButton(Action fAction, String caption) {
		JButton lRes = new JButton(caption);
		lRes.addActionListener(fAction);
		Dimension lPref = lRes.getPreferredSize();
		lRes.setPreferredSize(new Dimension(90, lPref.height - 5));
		return lRes;
	}

	public static JLabel createIconLabel(Icon fIcon) {
		JLabel lRes = new JLabel(fIcon);
		lRes.setPreferredSize(new Dimension(22, 22));
		return lRes;
	}

	public static int getTextWidgetHeight() {
		return 21;
	}

	public static SizeRequirements getHIntervalBetween(JComponent fLeft,
			JComponent fRight) {
		// moved the test in initForLookAndFeel, above.
		SizeRequirements result = _defaultHInterval;

		return result;
	}

	public static SizeRequirements getVIntervalBetween(JComponent fTop,
			JComponent fBottom) {
		// moved the test in initForLookAndFeel, above.
		SizeRequirements result = _defaultVInterval;

		return result;
	}

	public static SizeRequirements getHIntervalToBorder(JComponent fOne) {

		SizeRequirements result = _default2Border;

		return result;
	}

	public static SizeRequirements getVIntervalToBorder(JComponent fOne) {

		SizeRequirements result = _default2Border;

		return result;
	}

	public static void resizeWidth(JComponent fComp, int fWidth) {
		fComp.setPreferredSize(new Dimension(fWidth,
				fComp.getPreferredSize().height));
	}

	public static void resizeHeight(JComponent fComp, int fHeight) {
		fComp.setPreferredSize(new Dimension(fComp.getPreferredSize().width,
				fHeight));
	}

	public static void setButtonHeight(JButton button) {
		if (System.getProperty("os.name").toString().toLowerCase().indexOf(
				"windows") != -1)
			GraphicFactory.resizeHeight(button, 22);
		else
			GraphicFactory.resizeHeight(button, 36);
	}

	public static GuiJComboBox createComboBox(ComboBoxModel fModel) {
		GuiJComboBox lRes = new GuiJComboBox(fModel);
		resizeHeight(lRes, getTextWidgetHeight());

		return lRes;
	}

	public static GuiJComboBox createComboBox(Vector fVector) {
		GuiJComboBox lRes = new GuiJComboBox(fVector);
		resizeHeight(lRes, getTextWidgetHeight());

		return lRes;
	}

	public static JLabel createLabel(String fText) {
		JLabel lRes = new JLabel(fText);
		resizeHeight(lRes, getTextWidgetHeight());

		return lRes;
	}

	public static JList createList(ListModel fModel) {
		JList lRes = new JList(fModel);

		return lRes;
	}

	public static JList createList() {
		JList lRes = new JList();

		return lRes;
	}

	public static GuiJScrolledList createScrolledList() {
		GuiJScrolledList lRes = new GuiJScrolledList();
		return lRes;
	}

	public static GuiJScrolledList createScrolledList(ListModel dataModel) {
		GuiJScrolledList lRes = new GuiJScrolledList(dataModel);
		return lRes;
	}

	public static JTree createTree(TreeModel fModel) {
		JTree lRes = new JTree(fModel);

		return lRes;
	}

	public static JTree createTree(TreeNode tn) {
		JTree lRes = new JTree(tn);
		return lRes;
	}

	public static JTable createTable(TableModel fModel) {
		JTable lRes = new JTable(fModel);

		return lRes;
	}

	public static TableSorter createTableSorter(TableModel fModel) {
		TableSorter lRes = new TableSorter(fModel);

		return lRes;
	}

	public static JTable createTable(TableSorter sorter) {
		JTable lRes = new JTable(sorter);
		sorter.addMouseListenerToHeaderInTable(lRes);
		return lRes;
	}

	public static JTextArea createTextArea(boolean fbFocusable) {
		JTextArea lRes;
		if (fbFocusable)
			lRes = new JTextArea();
		else
			lRes = new JTextArea() {
				public boolean isFocusable() {
					return false;
				}
			};

		return lRes;
	}

	public static GuiJTextField createTextField(boolean fbFocusable) {
		GuiJTextField lRes;
		if (fbFocusable)
			lRes = new GuiJTextField();
		else
			lRes = new GuiJTextField() {
				public boolean isFocusable() {
					return false;
				}
			};
		Dimension dim = lRes.getPreferredSize();
		lRes.setPreferredSize(new Dimension(dim.width, getTextWidgetHeight()));
		return lRes;
	}

	public static ControlledTextField createControlledTextField() {
		ControlledTextField lRes;
		lRes = new ControlledTextField();

		Dimension dim = lRes.getPreferredSize();
		lRes.setPreferredSize(new Dimension(dim.width, getTextWidgetHeight()));

		return lRes;
	}

	public static JComponent createSectionHeader(String fTitle) {
		// SectionHeaderC lRes = new SectionHeaderC(fTitle);
		JComponent lRes = new SectioningHeader(fTitle);

		return lRes;
	}

	public static JComponent createSectionHeader(Vector fTitle) {
		return (new SectioningHeader(fTitle));
	}

	public static JComponent createSubSectionHeader(String fTitle) {
		JComponent lRes = new SectioningHeader(fTitle, BorderFactory
				.createLineBorder(_SUBSECTION_LINE_COLOR, 1));

		return lRes;
	}

	public static SizeToggler createSizeToggler(String fTitle) {
		SizeToggler lRes = new SizeToggler(fTitle);

		return lRes;
	}

	public static JCheckBox createCheckBox(String fText) {
		JCheckBox lRes = new JCheckBox(fText);
		resizeHeight(lRes, getTextWidgetHeight());

		return lRes;
	}

	public static JRadioButton createRadioButton(String fText) {
		JRadioButton lRes = new JRadioButton(fText);
		resizeHeight(lRes, getTextWidgetHeight());

		return lRes;
	}

	public static JProgressBar createProgressBar(BoundedRangeModel fModel) {
		JProgressBar lRes = new ProgressBar(fModel);

		return lRes;
	}

	public static JMenuBar createMenuBar() {
		JMenuBar lRes = new JMenuBar();

		return lRes;
	}

	public static JMenu createMenu(String fText) {
		JMenu lRes = new JMenu(fText);

		return lRes;
	}

	public static JMenuItem createMenuItem(String fCommand, String fText,
			Icon fIcon) {
		JMenuItem lRes = new JMenuItem(fText, fIcon);
		if (fCommand != null)
			lRes.setActionCommand(fCommand);

		return lRes;
	}

	public static JMenuItem createMenuItem(Action fAction, String fText) {
		JMenuItem lRes;
		if (fAction == null) {
			lRes = new JMenuItem(fText);
		} else {
			lRes = new JMenuItem(fText, (Icon) fAction
					.getValue(Action.SMALL_ICON));

			String lCommand = (String) fAction.getValue(Action.NAME);
			if (lCommand != null) {
				lRes.setActionCommand(lCommand);
				lRes.addActionListener(fAction);
			}
		}

		return lRes;
	}

	public static Border createEtchedBorder() {
		return BorderFactory.createEtchedBorder();
	}

	public static Border createLoweredBevelBorder() {
		return BorderFactory.createLoweredBevelBorder();
	}

	public static JTabbedPane createTabbedPane() {
		return new JTabbedPane();
	}

	public static ChooseList createChooseList() {
		return new ChooseList();
	}

	public static WizardBar createWizardBar() {
		return new WizardBar();
	}

	public static JSplitPane createJSplitPaneWithHiddenDivider(int dividerSize) {
		JSplitPane sp = new JSplitPane();
		sp.setUI(new SplitPaneUIWithHiddenDivider(dividerSize));
		return sp;
	}

    public static JLabel createStepLabel(String fText, int rows) {
        JLabel lRes = new JLabel(fText);
        resizeHeight(lRes, getTextWidgetHeight() * rows);

        return lRes;
    }
}
