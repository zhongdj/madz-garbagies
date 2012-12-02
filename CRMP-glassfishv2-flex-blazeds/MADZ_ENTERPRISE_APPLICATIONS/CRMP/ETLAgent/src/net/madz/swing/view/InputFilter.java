package net.madz.swing.view;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class InputFilter extends PlainDocument {
	private char c;

	public InputFilter(char c) {
		this.c = c;
	}

	public void insertString(int offset, String str, AttributeSet a)
			throws BadLocationException {

		char[] testString = str.toCharArray();

		int cptAvailable = 0;
		for (int i = 0; i < testString.length; i++) {

			if ((testString[i] >= '0' && testString[i] <= '9')
					|| testString[i] == c) {

				cptAvailable++;
			}
		}
		if (cptAvailable == testString.length) {
			super.insertString(offset, str, a);
		}
	}
}
