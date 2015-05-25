package uk.co.jmr.webforms.db;

public class FieldAttributes {
	public static final short DISABLED = 0x0001;
	public static final short HIDDEN = 0x0002;
	public static final short RO = 0x0004;
	public static final short AJAX = 0x0008;

	public static final short CB_SELECTED = 0x0100;
	public static final short CB_HASCHILD = 0x0200;

	public static final short LB_MULTISELECT = 0x0100;
	public static final short LB_DROPDOWN = 0x0200;
}
