package uk.co.jmr.sdp.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static final String DATE_FORMAT_STR = "dd/MM/yyyy";
	public static final String TIMESTAMP_FORMAT_STR = "dd/MM/yyyy HH:mm";
	public static final String CAL_TIMESTAMP_FORMAT_STR = "yyyyMMdd'T'HHmmss'Z'";

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STR);
	private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat(TIMESTAMP_FORMAT_STR);

	public static Date parseDate(String s) {

		return parseDate((SimpleDateFormat) DATE_FORMAT.clone(), s);
	}

	public static Date parseTimestamp(String s) {

		return parseDate((SimpleDateFormat) TIMESTAMP_FORMAT.clone(), s);
	}

	public static Date parseDate(SimpleDateFormat sf, String s) {

		Date d = null;
		try {
			if (s != null) {
				s = s.trim();
				if (s.length() > 0)
					d = sf.parse(s);
			}
		}
		catch (ParseException ex) {
		}

		return d;
	}

	public static String formatDate(Date d) {

		return formatDate((SimpleDateFormat) DATE_FORMAT.clone(), d);
	}

	public static String formatTimestamp(Date d) {

		return formatDate((SimpleDateFormat) TIMESTAMP_FORMAT.clone(), d);
	}

	public static String formatDate(SimpleDateFormat sf, Date d) {

		return sf.format(d);
	}

	public static int parseInt(String s, int defValue) {

		int v = defValue;
		try {
			v = Integer.parseInt(s);
		}
		catch (Exception e) {
		}

		return v;
	}

	public static long parseLong(String s, long defValue) {

		long v = defValue;
		try {
			v = Long.parseLong(s);
		}
		catch (Exception e) {
		}

		return v;
	}

	public static String arrayToString(String[] arrayString){
		StringBuilder sb=new StringBuilder();
		
		for(String str:arrayString){
			sb.append(str+"\n");
		}
		
		
		return sb.toString();
		
	}
	
	public static float parseLong(String s, float defValue) {

		float v = defValue;
		try {
			v = Float.parseFloat(s);
		}
		catch (Exception e) {
		}

		return v;
	}
	
	public static String formNameVersion(String name, int version) {

		return (name + "v" + version);
	}

	public static double parseDouble(String s, double defValue) {

		double v = defValue;
		try {
			v = Double.parseDouble(s);
		}
		catch (Exception e) {
		}

		return v;
	}
	
	
}
