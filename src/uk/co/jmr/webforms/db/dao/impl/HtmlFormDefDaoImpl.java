package uk.co.jmr.webforms.db.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.webforms.db.dao.HtmlFormDefDao;
import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.pojo.HtmlFormDef;

@Repository("htmlFormDefDao")
public class HtmlFormDefDaoImpl implements HtmlFormDefDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(HtmlFormDef obj) {

		hibernateTemplate.saveOrUpdate(obj);
	}

	@Override
	public void delete(HtmlFormDef obj) {

		hibernateTemplate.delete(obj);
	}

	@Override
	public HtmlFormDef findHtmlFormDefById(long id) {

		return hibernateTemplate.get(HtmlFormDef.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HtmlFormDef> findAllHtmlFormDef() {

		return hibernateTemplate.find("from HtmlFormDef");
	}

	@Override
	public HashMap<String, String> findLableNameForForms(Forms forms) {
		// TODO Auto-generated method stub
		HashMap<String, String> hm=new HashMap<String,String>();
		List<HtmlFormDef> htfd= hibernateTemplate.find("from HtmlFormDef where forms.id=?",forms.getId());
		for(HtmlFormDef hfd:htfd){
			hm.put(hfd.getName(), hfd.getFormFieldMap().getFields().getDescription());
		}
		
		return hm;
	}
}
