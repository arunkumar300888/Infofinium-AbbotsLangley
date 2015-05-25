package uk.co.jmr.sdp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.co.jmr.sdp.dao.DisciplineDao;
import uk.co.jmr.sdp.dao.DoctypeDao;
import uk.co.jmr.sdp.dao.DocumentDao;
import uk.co.jmr.sdp.dao.ReviewNoteDao;
import uk.co.jmr.sdp.domain.Discipline;
import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.service.DocumentService;
import uk.co.jmr.sdp.service.ReviewNoteService;
import uk.co.jmr.sdp.service.UserService;

@Controller
@RequestMapping(value = "/client")
public class ClientController {

	@Autowired
	private ReviewNoteDao reviewNoteDao;
	@Autowired
	private ReviewNoteService reviewNoteService;
	@Autowired
	private DocumentDao documentDao;
	@Autowired
	private DocumentService docService;
	@Autowired
	private UserService userService;
	@Autowired
	private DisciplineDao disciplineDao;
	@Autowired
	private DoctypeDao docTypeDao;

	@ResponseBody
	@RequestMapping(value = "/test")
	public void tests() {

		// Document doc =
		// docService.findDocumentByDocNameAndPath("ER1101_Combination-Padlock.jpg",
		// "/app:company_home/app:user_homes/cm:Test_x0020_2");
		// System.out.println(doc);
		// User user = userService.findUserByUserName("admin");
		// ReviewNote reviewNote = new ReviewNote(new
		// Date(),"Hi test Save",doc,user);
		// System.out.println(reviewNote);
		// reviewNoteDao.save(reviewNote);

		Discipline disc = disciplineDao.findDisciplineById(2);
		Doctype doctyp = docTypeDao.findDoctypeById(2);

		/*
		 * List<Document> docsSearch =
		 * documentDao.findDocumentsForSearch(disc,doctyp,"","","","","");
		 * for(Document d : docsSearch){ System.out.println(d); }
		 */

		/*
		 * ReviewNote r = this.reviewNoteDao.findReviewNoteById(1); ReviewNote
		 * rv = reviewNoteService.findReviewNoteById(1); System.out.println(rv);
		 * List<ReviewNote> reviews = reviewNoteService.findAllReviewsById(1);
		 * System
		 * .out.println("======================================================"
		 * ); for(ReviewNote rvn : reviews){ System.out.println(rvn); }
		 * System.out
		 * .println("======================================================");
		 * 
		 * System.out.println("user1: "+this.reviewNoteService.findByReviewerName
		 * ("user1")); System.out.println(
		 * "======================================================");
		 * 
		 * System.out.println("jai : "+this.reviewNoteService.findByReviewerName(
		 * "Jai")); return rv.toString();
		 */
	}
}
