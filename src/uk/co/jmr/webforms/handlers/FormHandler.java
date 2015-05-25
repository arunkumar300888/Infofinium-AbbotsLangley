package uk.co.jmr.webforms.handlers;

import java.io.OutputStream;
import java.util.HashMap;

public interface FormHandler {
	public String[] getFormParams();

	public String getFormValue(String name);

	public String[] getFormValues(String name);

	public String[] getReportParams(String report);

	public String[] getReports();

	public void runReport(String report, HashMap<String, String> params, OutputStream os);
}
