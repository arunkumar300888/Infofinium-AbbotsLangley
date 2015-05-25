package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ardhika.wfar.WfCase;
import com.ardhika.wfar.dao.CaseDao;

import uk.co.jmr.sdp.dao.DocumentDao;
import uk.co.jmr.sdp.dao.ReviewNoteDao;
import uk.co.jmr.sdp.dao.UserDao;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.ReviewNote;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.ReviewNoteService;

@Service("reviewNoteService")
public class ReviewNoteServiceImpl implements ReviewNoteService {
	@Autowired
	private ReviewNoteDao reviewNoteDao;
	@Autowired
	private DocumentDao documentDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CaseDao caseDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ReviewNote> findAllReviewsById(long documentId) {

		Document document = this.documentDao.findDocumentById(documentId);
		return this.reviewNoteDao.findAllReviews(document);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ReviewNote> findAllReviewsByCaseId(long wfcaseId) {

		WfCase wfCase = this.caseDao.findCaseById(wfcaseId);
		return this.reviewNoteDao.findAllReviews(wfCase);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ReviewNote> findAllReviewsByWfCase(WfCase wfCase) {

		return this.reviewNoteDao.findAllReviews(wfCase);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ReviewNote> findByReviewerName(String userName) {

		User user = this.userDao.findUserByUserName(userName);
		return this.reviewNoteDao.findByReviewer(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(ReviewNote reviewNote) {

		this.reviewNoteDao.save(reviewNote);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long reviewNoteId) {

		ReviewNote reviewNote = this.reviewNoteDao.findReviewNoteById(reviewNoteId);
		this.reviewNoteDao.delete(reviewNote);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ReviewNote findReviewNoteById(long id) {

		return this.reviewNoteDao.findReviewNoteById(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ReviewNote> findAllReviewsByDocument(Document document) {

		return this.reviewNoteDao.findAllReviews(document);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ReviewNote findByReviewerLastComment(User user, Document doc) {

		return reviewNoteDao.findByReviewerLastComment(user, doc);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ReviewNote findByReviewerLastComment(User user, WfCase wfCase) {

		return reviewNoteDao.findByReviewerLastComment(user, wfCase);
	}
}
