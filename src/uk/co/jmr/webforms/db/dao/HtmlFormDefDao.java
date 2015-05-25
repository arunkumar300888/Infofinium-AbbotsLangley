package uk.co.jmr.webforms.db.dao;

import java.util.HashMap;
import java.util.List;

import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.pojo.HtmlFormDef;

public interface HtmlFormDefDao {
	HtmlFormDef findHtmlFormDefById(long id);

	void save(HtmlFormDef obj);

	void delete(HtmlFormDef obj);

	List<HtmlFormDef> findAllHtmlFormDef();
	
	HashMap<String ,String> findLableNameForForms(Forms forms);
}
