package net.madz.infra.biz.service.core;

/**
 * 
 * @author dezhong
 */
public class ApplicationServiceException extends Exception {

	private static final long serialVersionUID = 571041044107422402L;

	public ApplicationServiceException(Exception ex) {
		super(ex);
	}

	public ApplicationServiceException(String message) {
		super(message);
	}

	public ApplicationServiceException(String message, Exception ex) {
		super(message, ex);
	}
}
