package net.madz.swing.view.util;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class ControlledTextField extends JTextField implements KeyListener {

	private TextFieldController _controller;

	int _lastWarningLevel;

	public ControlledTextField() {
		super();
		_init(null);
	}

	public ControlledTextField(TextFieldController fController) {
		super();
		_init(fController);
	}

	private void _init(TextFieldController fController) {
		_controller = fController;

		_lastWarningLevel = TextFieldController.ACCEPT;
		addKeyListener(this);
	}

	public void setText(String fText) {
		if (_controller != null) {
			int lReact = _controller.verifyTextChange(getText(), fText);
			boolean lWillWarn = lReact >= TextFieldController.WARN
					&& _lastWarningLevel != lReact;

			if (lWillWarn) {
				warn(lReact, fText);
			}

			if (lReact != TextFieldController.REFUSE)
				super.setText(fText);
		} else {
			super.setText(fText);
		}
	}

	public void warn(int fWarningLevel, String fAbout) {
		if (fWarningLevel >= TextFieldController.WARN)
			setBackground(Color.red);
		else
			setBackground(Color.white);
		repaint();
	}

	public void setController(TextFieldController fController) {
		int lReact = TextFieldController.ACCEPT;

		if (fController != null)
			lReact = fController.verifyTextChange("", getText());

		boolean lWillWarn = lReact >= TextFieldController.WARN
				&& _lastWarningLevel != lReact;

		if (lWillWarn) {
			warn(lReact, getText());
		}

		_controller = fController;

		if (lReact == TextFieldController.REFUSE) {
			super.setText("");
		}
	}

	public TextFieldController getController() {
		return _controller;
	}

	public void keyPressed(KeyEvent e) {
		if (_controller != null) {
			if (e.getKeyCode() == KeyEvent.VK_DELETE) {
				String lNewString = "";
				String lOldString = getText();
				if (getSelectionStart() != getSelectionEnd()) {
					lNewString = lOldString.substring(0, getSelectionStart())
							+ lOldString.substring(getSelectionEnd(),
									lOldString.length());
				} else if (getSelectionEnd() < lOldString.length()) {
					lNewString = lOldString.substring(0, getSelectionStart())
							+ lOldString.substring(getSelectionEnd() + 1,
									lOldString.length());
				}
				int lReact = _controller.verifyTextChange(lOldString,
						lNewString);

				if (lReact >= TextFieldController.WARN) {
					// don't know what else to do.
					warn(lReact, lNewString);
				} else if (lReact != _lastWarningLevel) {
					warn(lReact, null);
				}

				if (lReact == TextFieldController.REFUSE) {
					e.consume();
				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {

		if (_controller != null && e.getKeyChar() != KeyEvent.VK_DELETE
				&& e.getKeyCode() != KeyEvent.VK_ENTER && e.getKeyChar() != 10) {
			String lOldString = getText();
			String lNewString = "";

			int lBeg = getSelectionStart();
			int lInt = getSelectionEnd();
			int lEnd = lOldString.length();
			String lCharString = "";

			if ((int) e.getKeyChar() != 8) {
				lCharString += e.getKeyChar();
			} else if (getSelectionStart() != 0) {
				lBeg--;
			}
			lNewString = lOldString.substring(0, lBeg);
			lNewString += lCharString;
			lNewString += lOldString.substring(lInt, lEnd);

			int lReact = _controller.verifyTextChange(lOldString, lNewString);
			if (lReact >= TextFieldController.WARN) {

				warn(lReact, lOldString);
			} else if (lReact != _lastWarningLevel) {
				warn(lReact, null);
			}

			if (lReact == TextFieldController.REFUSE) {
				e.consume();
			}
		}
	}

}
