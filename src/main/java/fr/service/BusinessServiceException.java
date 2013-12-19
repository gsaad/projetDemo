package fr.service;


public class BusinessServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6786448232616440356L;

	public BusinessServiceException(String message) {
		super(message);
	}

	public BusinessServiceException(Throwable cause) {
		super(cause);
	}

	public BusinessServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
}