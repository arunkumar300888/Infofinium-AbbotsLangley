package uk.co.jmr.webforms.db.dao.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.webforms.db.dao.FormsDao;
import uk.co.jmr.webforms.db.pojo.FormDefs;
import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.pojo.UserForms;

@Repository("formsDao")
public class FormsDaoImpl implements FormsDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<Forms> findAllForms() {

		return hibernateTemplate.find("from Forms order by name");
	}

	@Override
	public Forms findFormsById(long id) {

		return hibernateTemplate.get(Forms.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Forms> findFormsByName(String name) {

		return hibernateTemplate.find("from Forms as d where d.name=? order by version", name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Forms> findFormsByFormDefNameByDate(String name, Date date) {

		if (date != null) {
			return hibernateTemplate.find(
				"from Forms where ? between d.dateStart and coalesce(d.dateEnd, ?) and formDefs.name = ? order by version", date,
				date, name);
		}

		return hibernateTemplate.find("from Forms where formDefs.name = ? order by version");
	}

	@Override
	public void save(Forms obj) {

		hibernateTemplate.saveOrUpdate(obj);
	}

	@Override
	public void delete(Forms obj) {

		hibernateTemplate.delete(obj);
	}

	@Override
	public Forms findFormsByFormDefId(FormDefs formDefs) {
		// TODO Auto-generated method stub
		List<Forms> forms=hibernateTemplate.find("from Forms where formDefs=?",formDefs);
		if(forms.size()>=1)
		return forms.get(0);
		return null;
	}

}
