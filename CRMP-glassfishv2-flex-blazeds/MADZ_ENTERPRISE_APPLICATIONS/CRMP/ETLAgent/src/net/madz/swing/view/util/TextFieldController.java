package net.madz.swing.view.util;

public interface TextFieldController {

	public static final int ACCEPT = 1;
	public static final int REFUSE = 2;
	public static final int WARN = 3;


	public int verifyTextChange(String fOldString, String fNewString);
}
