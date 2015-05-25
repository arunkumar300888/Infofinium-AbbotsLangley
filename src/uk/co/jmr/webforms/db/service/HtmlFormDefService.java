package uk.co.jmr.webforms.db.service;

import java.util.HashMap;
import java.util.List;

import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.pojo.HtmlFormDef;

public interface HtmlFormDefService {
	HtmlFormDef findHtmlFormDefById(long id);

	void save(HtmlFormDef obj);

	void delete(long id);

	List<HtmlFormDef> findAllHtmlFormDef();
	
	HashMap<String ,String> findLableNameForForms(Forms forms);
}
