package net.madz.swing.view.util;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;

public class WizardBar extends JPanel {
	GraphicFactory _toolsGraphic = new GraphicFactory();

	JButton _nextButton;
	JButton _cancelButton;
	JButton _helpButton;
	JButton _backButton;
	JButton _okButton;

	public final static int NEXT = 0;
	public final static int BACK = 1;
	public final static int OK = 2;
	public final static int HELP = 3;
	public final static int CANCEL = 4;

	Vector _listenerList = new Vector();

	JPanel HeaderPanel = new JPanel();
	JPanel MiddlePanel = new JPanel();

	private GridBagLayout gridbagGlobal = new GridBagLayout();
	private GridBagConstraints cGlobal = new GridBagConstraints();
	private GraphicFactory toolsGraphic = new GraphicFactory();

	public WizardBar() {
		this.setLayout(gridbagGlobal);

		cGlobal.gridx = 0;
		cGlobal.gridy = 0;
		cGlobal.weightx = 1.0;
		cGlobal.weighty = 0.0;
		cGlobal.fill = GridBagConstraints.HORIZONTAL;
		cGlobal.anchor = GridBagConstraints.NORTH;
		cGlobal.insets = new Insets(0, 10, 0, 10);
		this.add(toolsGraphic.createSectionHeader(""), cGlobal);

		cGlobal.gridx = 0;
		cGlobal.gridy = 1;
		cGlobal.weightx = 0.0;
		cGlobal.weighty = 0.0;
		cGlobal.fill = GridBagConstraints.NONE;
		cGlobal.anchor = GridBagConstraints.EAST;
		cGlobal.insets = new Insets(0, 10, 0, 10);
		JPanel lPanel = _createwizardBar();
		this.add(lPanel, cGlobal);
	}

	private JPanel _createwizardBar() {
		JPanel buttonPanel = new JPanel();

		buttonPanel.setLayout(new FlowLayout2(FlowLayout2.EAST, 0, true, false));

		_nextButton = _toolsGraphic.createTextButton("Next");
		_nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireWizardBarNext();
			}
		});

		_backButton = _toolsGraphic.createTextButton("Back");
		_backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireWizardBarBack();
			}
		});

		_cancelButton = _toolsGraphic.createTextButton("Cancel");
		_cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireWizardBarCancel();
			}
		});

		if (buttonPanel.add(_cancelButton) == null) {
		}
		buttonPanel.add(_nextButton, new Integer(10));
		if (buttonPanel.add(_backButton) == null) {
		}

		return buttonPanel;
	}

	public void addwizardBarListener(Object listener) {
		_listenerList.addElement(listener);
	}

	public void fireWizardBarNext() {
		for (int i = 0; i < _listenerList.size(); i++) {
			WizardBarListener listener = (WizardBarListener) _listenerList.elementAt(i);
			listener.selectedNext();
		}
	}

	public void fireWizardBarBack() {
		for (int i = 0; i < _listenerList.size(); i++) {
			WizardBarListener listener = (WizardBarListener) _listenerList.elementAt(i);
			listener.selectedBack();
		}
	}

	public void fireWizardBarCancel() {
		for (int i = 0; i < _listenerList.size(); i++) {
			WizardBarListener listener = (WizardBarListener) _listenerList.elementAt(i);
			listener.selectedCancel();
		}
	}

	public void setEnablewizard(boolean bButtonBack, boolean bButtonNext, boolean bButtonCancel) {
		_backButton.setEnabled(bButtonBack);
		_nextButton.setEnabled(bButtonNext);
		_cancelButton.setEnabled(bButtonCancel);
	}

	public void setEnableWizardButton(int iButton, boolean bEnable) {
		switch (iButton) {
		case NEXT:
			_nextButton.setEnabled(bEnable);
			break;
		case BACK:
			_backButton.setEnabled(bEnable);
			break;
		case CANCEL:
			_cancelButton.setEnabled(bEnable);
			break;
		default:
			break;
		}
	}

	public void setWizardButtonText(int iButton, String strTitle) {
		switch (iButton) {
		case NEXT:
			_nextButton.setText(strTitle);
			break;
		case BACK:
			_backButton.setText(strTitle);
			break;
		case CANCEL:
			_cancelButton.setText(strTitle);
			break;
		default:
			break;
		}
	}
}
