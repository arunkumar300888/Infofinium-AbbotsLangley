package uk.co.jmr.sdp.web.revision.exception;

public class RevisioningException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RevisioningException(String message, Throwable cause) {

		super(message, cause);

	}

	@Override
	public synchronized Throwable getCause() {

		// TODO Auto-generated method stub
		return super.getCause();
	}

	@Override
	public String getMessage() {

		return super.getMessage();
	}

	@Override
	public String toString() {

		return super.getMessage();
	}

}
