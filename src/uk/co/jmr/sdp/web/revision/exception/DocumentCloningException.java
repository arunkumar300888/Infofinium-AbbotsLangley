package uk.co.jmr.sdp.web.revision.exception;

public class DocumentCloningException extends RevisioningException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DocumentCloningException(String message, Throwable cause) {

		super(message, cause);

	}

	@Override
	public synchronized Throwable getCause() {

		return super.getCause();
	}

	@Override
	public String getMessage() {

		return super.getMessage();
	}

}
