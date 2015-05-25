package uk.co.jmr.webforms.handlers;

import java.io.OutputStream;
import java.util.HashMap;

public class FRM001V1 implements FormHandler {
	public FRM001V1() {

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

		throw new UnsupportedOperationException("Not supported yet.");
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
