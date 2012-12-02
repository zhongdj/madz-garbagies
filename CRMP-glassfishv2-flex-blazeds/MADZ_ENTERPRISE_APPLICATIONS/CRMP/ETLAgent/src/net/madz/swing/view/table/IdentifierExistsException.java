package net.madz.swing.view.table;

public class IdentifierExistsException extends Exception {
	/** Identifier */
	private Object _identifier = null;

	/** Message of exception */
	private String _message = null;

	public IdentifierExistsException(Object identifier) {
		super();

		_identifier = identifier;

		_message = "Idnetifier (" + _identifier + ") already exists.";

	}

	public Object getIdentifer() {
		return _identifier;
	}

	/**
	 * Returns the error message string of this throwable object.
	 * 
	 * @return the error message string of this Throwable object
	 */
	public String getMessage() {
		return _message;
	}
}
