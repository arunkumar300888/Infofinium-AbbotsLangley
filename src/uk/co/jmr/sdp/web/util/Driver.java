package uk.co.jmr.sdp.web.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.co.jmr.sdp.dao.ReviewNoteDao;

public class Driver {

	public static void main(String[] args) throws Exception {

		// File file = new File("root-context.xml");
		// String filePath = file.getAbsolutePath();
		// System.out.println(filePath);
		ApplicationContext context = new ClassPathXmlApplicationContext("root-context.xml");
		ReviewNoteDao reviewNoteDao = (ReviewNoteDao) context.getBean("reviewNoteDao", ReviewNoteDao.class);
		reviewNoteDao.findReviewNoteById(1);

		// ReviewNoteService reviewNoteservice =(ReviewNoteService)
		// context.getBean("reviewNoteService",ReviewNote.class);
		// ReviewNote rv = reviewNoteservice.findReviewNoteById(1);
		// System.out.println(rv);
		// List<ReviewNote> reviews = reviewNoteservice.findAllReviewsById(1);
		// for(ReviewNote r : reviews){
		// System.out.println(r);
		// }

		// UserService userService = (UserService)
		// context.getBean("userService",UserService.class);

		// DocumentStorage docStore = new DocumentStorageAlfImpl();
		// String b = " This is test creaste new";
		// docStore.createContent(new User("admin","admin"),
		// "/app:company_home/app:user_homes", "TestCreate.txt", "text/txt",
		// b.getBytes());
		// List<Object> ck = docStore.checkOut(new User("admin", "admin"),
		// "Grails Intro.pdf");
		// System.out.println("working Copy:-> "+ck.get(0));
		// System.out.println(" "+ck.get(1));
	}

}
