package uk.co.jmr.sdp.dao;

import java.util.List;

import com.ardhika.wfar.WfCase;

import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.ReviewNote;
import uk.co.jmr.sdp.domain.User;

public interface ReviewNoteDao {
	List<ReviewNote> findAllReviews(Document document);

	List<ReviewNote> findByReviewer(User user);

	ReviewNote findReviewNoteById(long id);

	void save(ReviewNote reviewNote);

	void delete(ReviewNote reviewNote);

	public ReviewNote findByReviewerLastComment(User user, Document doc);

	// For Case
	List<ReviewNote> findAllReviews(WfCase wfCase);

	public ReviewNote findByReviewerLastComment(User user, WfCase wfCase);
}
