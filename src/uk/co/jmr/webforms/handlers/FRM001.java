package uk.co.jmr.webforms.handlers;

import java.io.OutputStream;
import java.util.HashMap;

public class FRM001 implements FormHandler {
	HashMap<String, String[]> listValues = new HashMap<String, String[]>();

	public FRM001() {

		String[] saluations = new String[6];
		int i = 0;
		saluations[i++] = "Mr";
		saluations[i++] = "Mrs";
		saluations[i++] = "Miss";
		saluations[i++] = "Dr.";
		saluations[i++] = "Rev.";
		saluations[i++] = "Master";
		listValues.put("saluations", saluations);
	}

	@Override
	public String[] getFormParams() {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getFormValue(String name) {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String[] getFormValues(String name) {

		return listValues.get(name);
	}

	@Override
	public String[] getReportParams(String report) {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String[] getReports() {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void runReport(String report, HashMap<String, String> params, OutputStream os) {

		throw new UnsupportedOperationException("Not supported yet.");
	}
}
