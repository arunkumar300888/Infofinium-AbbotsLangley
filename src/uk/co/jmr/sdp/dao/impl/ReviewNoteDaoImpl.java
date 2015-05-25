package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.ardhika.wfar.WfCase;

import uk.co.jmr.sdp.dao.ReviewNoteDao;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.ReviewNote;
import uk.co.jmr.sdp.domain.User;

@Repository("reviewNoteDao")
public class ReviewNoteDaoImpl implements ReviewNoteDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<ReviewNote> findAllReviews(Document document) {

		return hibernateTemplate.find("from ReviewNote where document_id=?", document.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReviewNote> findAllReviews(WfCase wfCase) {

		return hibernateTemplate.find("from ReviewNote where case_id=?", wfCase.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReviewNote> findByReviewer(User user) {

		return hibernateTemplate.find("from ReviewNote where reviewer_id=?", user.getId());
	}

	@Override
	public void save(ReviewNote reviewNote) {

		hibernateTemplate.saveOrUpdate(reviewNote);
		//System.out.println(" RN saved ");
	}

	@Override
	public void delete(ReviewNote reviewNote) {

		hibernateTemplate.delete(reviewNote);
	}

	@Override
	public ReviewNote findReviewNoteById(long id) {

		return hibernateTemplate.get(ReviewNote.class, id);
	}

	public ReviewNote findByReviewerLastComment(User user, Document doc) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ReviewNote.class).add(Restrictions.conjunction());
		criteria.add(Restrictions.eq("reviewer", user));
		criteria.add(Restrictions.eq("document", doc));
		criteria.addOrder(Order.desc("reviewDate"));
		// criteria.add(Restrictions.sqlRestriction("LIMIT 1"));
		@SuppressWarnings("unchecked")
		List<ReviewNote> reviewNoteList = hibernateTemplate.findByCriteria(criteria, 0, 1);
		if (reviewNoteList.size() > 0) {
			return reviewNoteList.get(0);
		}
		return null;
	}

	@Override
	public ReviewNote findByReviewerLastComment(User user, WfCase wfCase) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ReviewNote.class).add(Restrictions.conjunction());
		criteria.add(Restrictions.eq("reviewer", user));
		criteria.add(Restrictions.eq("wfCase", wfCase));
		criteria.addOrder(Order.desc("reviewDate"));
		// criteria.add(Restrictions.sqlRestriction("LIMIT 1"));
		@SuppressWarnings("unchecked")
		List<ReviewNote> reviewNoteCaseList = hibernateTemplate.findByCriteria(criteria, 0, 1);
		if (reviewNoteCaseList.size() > 0) {
			return reviewNoteCaseList.get(0);
		}
		return null;
	}
}
