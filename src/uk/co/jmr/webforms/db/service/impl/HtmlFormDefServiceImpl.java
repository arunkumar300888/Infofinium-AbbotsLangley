package uk.co.jmr.webforms.db.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.webforms.db.dao.HtmlFormDefDao;
import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.pojo.HtmlFormDef;
import uk.co.jmr.webforms.db.service.HtmlFormDefService;

@Service("htmlFormDefService")
public class HtmlFormDefServiceImpl implements HtmlFormDefService {
	@Autowired
	private HtmlFormDefDao htmlFormDefDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public HtmlFormDef findHtmlFormDefById(long id) {

		return this.htmlFormDefDao.findHtmlFormDefById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(HtmlFormDef obj) {

		this.htmlFormDefDao.save(obj);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {

		HtmlFormDef obj = this.htmlFormDefDao.findHtmlFormDefById(id);
		this.htmlFormDefDao.delete(obj);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<HtmlFormDef> findAllHtmlFormDef() {

		return this.htmlFormDefDao.findAllHtmlFormDef();
	}

	@Override
	public HashMap<String, String> findLableNameForForms(Forms forms) {
		// TODO Auto-generated method stub
		return this.htmlFormDefDao.findLableNameForForms(forms);
	}
}
