package uk.co.jmr.sdp.email;

import org.springframework.core.io.InputStreamSource;

public class Attachment {
	private String mimeType;
	private String name;
	private InputStreamSource ins;
	private boolean inline = true;

	public Attachment() {

	}

	public Attachment(String name, String mimeType, InputStreamSource ins) {

		this.name = name;
		this.mimeType = mimeType;
		this.ins = ins;
	}

	/**
	 * @return the mimeType
	 */
	public String getMimeType() {

		return mimeType;
	}

	/**
	 * @param mimeType
	 *            the mimeType to set
	 */
	public void setMimeType(String mimeType) {

		this.mimeType = mimeType;
	}

	/**
	 * @return the name
	 */
	public String getName() {

		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * @return the ins
	 */
	public InputStreamSource getIns() {

		return ins;
	}

	/**
	 * @param ins
	 *            the ins to set
	 */
	public void setIns(InputStreamSource ins) {

		this.ins = ins;
	}

	/**
	 * @return the inline
	 */
	public boolean isInline() {

		return inline;
	}

	/**
	 * @param inline
	 *            the inline to set
	 */
	public void setInline(boolean inline) {

		this.inline = inline;
	}
}
