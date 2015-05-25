package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.DoctypeDao;
import uk.co.jmr.sdp.domain.Doctype;

@Repository("doctypeDao")
public class DoctypeDaoImpl implements DoctypeDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<Doctype> findAllDoctype() {
		// return hibernateTemplate.find("from Doctype");
		char isActive = 'Y';
		return hibernateTemplate.find("from Doctype as d where d.isActive=?", isActive);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Doctype> findAllDoctypeWithInActive() {
		return hibernateTemplate.find("from Doctype");
		// char isActive='Y';
		// return
		// hibernateTemplate.find("from Doctype as d where d.isActive=?",isActive);
	}

	@Override
	public Doctype findDoctypeById(long id) {

		return hibernateTemplate.get(Doctype.class, id);
	}

	@Override
	public Doctype findDoctypeByDoctypeName(String doctypeName) {

		return (Doctype) hibernateTemplate.find("from Doctype as d where d.doctypeName=?", doctypeName).get(0);
	}

	@Override
	public void save(Doctype doctype) {

		hibernateTemplate.saveOrUpdate(doctype);

	}

	@Override
	public void delete(Doctype doctype) {

		hibernateTemplate.delete(doctype);

	}

	@Override
	public Doctype checkDoctypeExists(String doctypeName, String abbreviation) {

		@SuppressWarnings("unchecked")
		List<Doctype> docTypes = hibernateTemplate.find("from Doctype as d where d.doctypeName=? and d.abbreviation=?",
			doctypeName, abbreviation);
		if (docTypes.size() >= 1) {
			return docTypes.get(0);
		}

		return null;

	}

}
