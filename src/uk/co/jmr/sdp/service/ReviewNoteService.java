package uk.co.jmr.sdp.service;

import java.util.List;

import com.ardhika.wfar.WfCase;

import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.ReviewNote;
import uk.co.jmr.sdp.domain.User;

public interface ReviewNoteService {
	List<ReviewNote> findAllReviewsById(long documentId);

	List<ReviewNote> findAllReviewsByDocument(Document document);

	List<ReviewNote> findByReviewerName(String userName);

	ReviewNote findReviewNoteById(long id);

	void save(ReviewNote reviewNote);

	void delete(long reviewNoteId);

	public ReviewNote findByReviewerLastComment(User user, Document doc);

	// For Case
	List<ReviewNote> findAllReviewsByCaseId(long wfcaseId);

	List<ReviewNote> findAllReviewsByWfCase(WfCase wfCase);

	public ReviewNote findByReviewerLastComment(User user, WfCase wfCase);
}
