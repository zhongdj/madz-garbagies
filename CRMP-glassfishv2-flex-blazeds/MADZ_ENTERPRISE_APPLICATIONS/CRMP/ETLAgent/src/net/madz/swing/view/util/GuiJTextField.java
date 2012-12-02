package net.madz.swing.view.util;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class GuiJTextField extends JTextField {

	public GuiJTextField() {
		super();
	};

	public GuiJTextField(Document doc, String text, int columns) {
		super(doc, text, columns);
	};

	public GuiJTextField(int columns) {
		super(columns);
	};

	public GuiJTextField(String text) {
		super(text);
	};

	public GuiJTextField(String text, int columns) {
		super(text, columns);
	};

}
