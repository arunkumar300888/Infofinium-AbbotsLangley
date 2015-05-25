package uk.co.jmr.webforms.db.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.webforms.db.dao.FormDataDao;
import uk.co.jmr.webforms.db.pojo.FieldTypes;
import uk.co.jmr.webforms.db.pojo.Fields;
import uk.co.jmr.webforms.db.pojo.FormData;
import uk.co.jmr.webforms.db.pojo.FormFieldMap;
import uk.co.jmr.webforms.db.pojo.HtmlFormDef;
import uk.co.jmr.webforms.db.pojo.UserForms;

@Repository("formDataDao")
public class FormDataDaoImpl implements FormDataDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<FormData> findAllFormData() {

		return hibernateTemplate.find("from FormData");
	}

	@Override
	public FormData findFormDataById(long id) {

		return hibernateTemplate.get(FormData.class, id);
	}

	@Override
	public void save(FormData obj) {

		hibernateTemplate.saveOrUpdate(obj);
	}

	@Override
	public void delete(FormData obj) {

		hibernateTemplate.delete(obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FormData> findFormDatasByUserForm(UserForms userForms) {
		// TODO Auto-generated method stub
		
		Session session = sessionFactory.openSession();
		/*String sb=
		"select * from form_data where html_form_def_id in "+
"(SELECT id FROM html_form_def where form_field_map_id in"+
"(SELECT id FROM form_field_map where field_id in"+
"(SELECT id FROM fields where field_type_id in"+
"(SELECT id FROM field_types where name='file')))) and user_form_id = '"+userForms.getId()+"'";*/
	/*String sb="	select * from form_data f,html_form_def ht,form_field_map fm, fields fi,field_types ft where "+
		"f.html_form_def_id = ht.id and ht.form_field_map_id=fm.id and fm.field_id = fi.id" +
		" and fi.field_type_id=ft.id and ft.name='file' and f.user_form_id='"+userForms.getId()+"'";
		SQLQuery query = session.createSQLQuery(sb);
		System.out.println(query);
		List<FormData>  fds = (List<FormData>)query.list();
		List<String> filesList = new ArrayList<String>();
		for(int i=0; i<fds.size();i++){
			FormData fd = (FormData)fds.get(i);
			
			filesList.add(fd.getTextValue());	
		}
		return filesList;*/
		DetachedCriteria fieldTypeCriteria = DetachedCriteria.forClass(FieldTypes.class);
		DetachedCriteria fieldsCriteria = DetachedCriteria.forClass(Fields.class);
		DetachedCriteria fieldsMapCriteria = DetachedCriteria.forClass(FormFieldMap.class);
		DetachedCriteria htmlCriteria = DetachedCriteria.forClass(HtmlFormDef.class);
		DetachedCriteria formDataCriteria = DetachedCriteria.forClass(FormData.class);
		
		List<FieldTypes> ft=hibernateTemplate.findByCriteria(fieldTypeCriteria.add(Restrictions.eq("name", "file")));
		List<Fields> fields=hibernateTemplate.findByCriteria(fieldsCriteria.add(Restrictions.in("fieldTypes",ft)));
		List<FormFieldMap> fieldsMap=hibernateTemplate.findByCriteria(fieldsMapCriteria.add(Restrictions.in("fields",fields)));
		List<HtmlFormDef> htmlFormFieldMap=hibernateTemplate.findByCriteria(htmlCriteria.add(Restrictions.in("formFieldMap",fieldsMap)));
		formDataCriteria.add(Restrictions.eq("userForms",userForms));
		List<FormData> formDatas=hibernateTemplate.findByCriteria(formDataCriteria.add(Restrictions.in("htmlFormDef",htmlFormFieldMap)));
		
		
		
		/*Disjunction disCriteria = Restrictions.disjunction();
		
		disCriteria.add(fieldTypeCriteria.add(Restrictions.eq("name", "file")));*/
		
		return formDatas;
		
	}
}
