package uk.co.jmr.sdp.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.ardhika.wfar.WfStep;
import com.ardhika.wfar.service.CaseService;

import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.DocumentTrail;
import uk.co.jmr.sdp.domain.DocumentVersion;
import uk.co.jmr.sdp.domain.ReviewNote;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.ds.DocumentStorage;
import uk.co.jmr.sdp.service.DisciplineService;
import uk.co.jmr.sdp.service.DoctypeService;
import uk.co.jmr.sdp.service.DocumentService;
import uk.co.jmr.sdp.service.DocumentTrailService;
import uk.co.jmr.sdp.service.DocumentVersionService;
import uk.co.jmr.sdp.service.ReviewNoteService;
import uk.co.jmr.sdp.web.util.Util;

@Controller
@RequestMapping(value = "/docs")
public class DocumentController {
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private DocumentStorage documentStorage;

	@Autowired
	private DoctypeService docTypeService;

	@Autowired
	private DisciplineService disciplineService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private ReviewNoteService reviewNoteService;

	@Autowired
	private DocumentTrailService documentTrailService;
	@Autowired
	private CaseService caseService;
	@Autowired
	private DocumentVersionService documentVersionService;
	
	Logger logger=Logger.getLogger(DocumentController.class);

	private ArrayList<String> getServiceUrls() {

		ArrayList<String> serviceUrlLists = new ArrayList<String>();
		serviceUrlLists.add((String) servletContext.getAttribute("restServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("navigationServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("objectServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("versioningServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("discoveryServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("multifilingServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("relationshipServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("aclServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("policyServiceWsdlUrl"));
		return serviceUrlLists;
	}

	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public void downloadDocument(@RequestParam("path") String path, @RequestParam("documentName") String documentName,
		@RequestParam("documentId") String documentId, @RequestParam("stepId") long stepId, HttpServletResponse response,
		HttpSession session) throws Exception {

		// System.out.println(path + " " + documentId);
		Document doc=documentService.findDocumentByDocNameAndPath(documentName, path);
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		
		String dsUser = (String) servletContext.getAttribute("dsUser");
		String dsPassword = (String) servletContext.getAttribute("dsPassword");

		ArrayList<String> serviceUrlLists = getServiceUrls();

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		// InputStream is = null;
		// OutputStream os = null;
		try {
			if(doc.getDoctype().getVersion().equals("Mj")){
				String downDoc=null;
				if(stepId==0){
				List<DocumentVersion> documentVersions=documentVersionService.findDocumentVersionsByDocumentId(Long.parseLong(documentId));
				if(!documentVersions.isEmpty()){
				for(DocumentVersion dv:documentVersions){
					downDoc=dv.getDocumentName();
					break;
				}
				}else{
					downDoc=documentName;
				}	
				}else{
					downDoc=documentName;
				}
			bis = new BufferedInputStream(documentStorage.download(dsUser, dsPassword, path, downDoc, serviceUrlLists));
			}else{
				bis = new BufferedInputStream(documentStorage.download(dsUser, dsPassword, path, documentName, serviceUrlLists));
			}
			response.setHeader("Content-Disposition", "attachment;filename=\"" + documentName + "\"");
			byte[] bytes = new byte[2048];
			bos = new BufferedOutputStream(response.getOutputStream());
			int read;
			while ((read = bis.read(bytes)) != -1) {
				bos.write(bytes, 0, read);
			}
		}
		finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.flush();
				bos.close();
			}
		}

		Long docId = Long.parseLong(documentId);
		if (stepId != 0) {
			WfStep wfStep = caseService.findStepById(stepId);
			String taskName = wfStep.getNode().getName();
			Document document = documentService.findDocumentById(docId);
			DocumentTrail documentTrail = new DocumentTrail(document, user, "Download", new Date(), taskName);
			// System.out.println("document Trail download with step-> "
			// + documentTrail);
			documentTrailService.save(documentTrail);
		}
		else {
			Document document = documentService.findDocumentById(docId);
			DocumentTrail documentTrail = new DocumentTrail(document, user, "Download", new Date(), null);
			// System.out.println("document Trail download without step-> "
			// + documentTrail);
			documentTrailService.save(documentTrail);
		}
	}

	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public void checkoutDocument(@RequestParam("path") String path, @RequestParam("documentName") String documentName,
		@RequestParam("documentId") String documentId, @RequestParam("stepId") long stepId, HttpServletResponse response,
		HttpSession session) throws Exception {

		// System.out.println(path + " " + documentName);
		Document doc=documentService.findDocumentById(Long.parseLong(documentId));
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		String dsUser = (String) servletContext.getAttribute("dsUser");
		String dsPassword = (String) servletContext.getAttribute("dsPassword");
		ArrayList<String> serviceUrlLists = getServiceUrls();

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		List<Object> output = null;
		

		try {
			
			if(doc.getDoctype().getVersion().equals("Mj")){
				if(stepId!=0){
				DocumentVersion dv=new DocumentVersion(doc.getId(),doc.getFilePath(), doc.getName());
				documentVersionService.save(dv);
				bis = new BufferedInputStream(documentStorage.download(dsUser, dsPassword, path, documentName, serviceUrlLists));
				String dName=Util.removeFileExtention(documentName);
				documentName=dName+" (Working Copy)."+FilenameUtils.getExtension(documentName);
				response.setHeader("Content-Disposition", "attachment;filename=\"" + documentName + "\"");
				byte[] bytes = new byte[2048];
				bos = new BufferedOutputStream(response.getOutputStream());
				int read;
				while ((read = bis.read(bytes)) != -1) {
					bos.write(bytes, 0, read);
				}
			}else{
				List<DocumentVersion> documentVersions=documentVersionService.findDocumentVersionsByDocumentId(Long.parseLong(documentId));
				String downDoc=null;
				if(!documentVersions.isEmpty()){
				for(DocumentVersion dv:documentVersions){
					downDoc=dv.getDocumentName();
					break;
				}
				output = documentStorage.checkOut(dsUser, dsPassword, path, downDoc, serviceUrlLists);
				String dName=Util.removeFileExtention(doc.getName());
				downDoc=dName+" (Working Copy)."+FilenameUtils.getExtension(downDoc);
				response.setHeader("Content-Disposition", "attachment;filename=\"" + downDoc + "\"");

				// InputStream is = (InputStream) output.get(1);
				bis = new BufferedInputStream((InputStream) output.get(1));
				byte[] bytes = new byte[2048];
				// OutputStream os = response.getOutputStream();
				bos = new BufferedOutputStream(response.getOutputStream());
				int read;
				while ((read = bis.read(bytes)) != -1) {
					bos.write(bytes, 0, read);
				}
				}else{
					output = documentStorage.checkOut(dsUser, dsPassword, path, documentName, serviceUrlLists);
					
					// System.out.println("get(0)-> " + output.get(0));

					response.setHeader("Content-Disposition", "attachment;filename=\"" + output.get(0) + "\"");

					// InputStream is = (InputStream) output.get(1);
					bis = new BufferedInputStream((InputStream) output.get(1));
					byte[] bytes = new byte[2048];
					// OutputStream os = response.getOutputStream();
					bos = new BufferedOutputStream(response.getOutputStream());
					int read;
					while ((read = bis.read(bytes)) != -1) {
						bos.write(bytes, 0, read);
					}
				}
			}
			}else{
			output = documentStorage.checkOut(dsUser, dsPassword, path, documentName, serviceUrlLists);
			
			// System.out.println("get(0)-> " + output.get(0));

			response.setHeader("Content-Disposition", "attachment;filename=\"" + output.get(0) + "\"");

			// InputStream is = (InputStream) output.get(1);
			bis = new BufferedInputStream((InputStream) output.get(1));
			byte[] bytes = new byte[2048];
			// OutputStream os = response.getOutputStream();
			bos = new BufferedOutputStream(response.getOutputStream());
			int read;
			while ((read = bis.read(bytes)) != -1) {
				bos.write(bytes, 0, read);
			}
			}
		}
		finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.flush();
				bos.close();
			}
		}

		// os.flush();
		// os.close();
		// is.close();
		Long docId = Long.parseLong(documentId);
		Document document = documentService.findDocumentById(docId);
		documentService.lock(docId);
		// System.out.println("-------------------------------------------->" + document.getLocked());

		if (stepId != 0) {
			WfStep wfStep = caseService.findStepById(stepId);
			String taskName = wfStep.getNode().getName();
			DocumentTrail documentTrail = new DocumentTrail(document, user, "CheckOut", new Date(), taskName);
			// System.out.println("document Trail checkout with step-> "
			// + documentTrail);
			documentTrailService.save(documentTrail);
		}
		else {
			DocumentTrail documentTrail = new DocumentTrail(document, user, "CheckOut", new Date(), null);
			// System.out.println("document Trail checkout step-> "
			// + documentTrail);
			documentTrailService.save(documentTrail);
		}
	}

	// @SuppressWarnings("finally")
	// @RequestMapping(value = "/checkin", method = RequestMethod.POST, headers
	// = { "content-type=multipart/form-data" })
	// @ResponseBody
	// public String checkinDocument(@RequestParam("path") String path,
	// @RequestParam("docFile") CommonsMultipartFile file,
	// @RequestParam("documentId") String documentId,
	// HttpSession session) {
	// String msg = null;
	// int result = 0;
	// try {
	// User user = (User) session.getAttribute("LOGGED_IN_USER");
	// String dsUser = (String) servletContext.getAttribute("dsUser");
	// String dsPassword = (String) servletContext.getAttribute("dsPassword");
	//
	// String fileName = file.getOriginalFilename();
	// byte[] bytes = file.getBytes();
	// System.out.println("filename -> " + fileName);
	// System.out.println("type -> " + file.getContentType());
	// msg = documentStorage.checkIn(dsUser,dsPassword, path, fileName, bytes);
	// result=1;
	// Long docId = Long.parseLong(documentId);
	// Document document = documentService.findDocumentById(docId);
	// documentService.unLock(docId);
	// DocumentTrail documentTrail = new DocumentTrail(document, user,
	// "CheckIn", new Date());
	// System.out.println("document Trail checkin-> "+documentTrail);
	// documentTrailService.save(documentTrail);
	// result=1;
	//
	// } catch (Exception ex) {
	// msg = "Unknown Error";
	// result=0;
	// } finally {
	// String html =
	// "<script language='javascript' type='text/javascript'> window.top.window.checkinCompleted("+
	// result + ");</script>";
	// return html;
	// }
	// }

	@SuppressWarnings("finally")
	@RequestMapping(value = "/checkin", method = RequestMethod.POST, headers = { "content-type=multipart/form-data" })
	@ResponseBody
	public String checkinDocument(@RequestParam("path") String path, @RequestParam("docFile123") CommonsMultipartFile file,
		@RequestParam("documentId") String documentId, @RequestParam("stepId") long stepId,@RequestParam("adminMeta") boolean adminMeta, HttpSession session) {

	
		String msg = null;
		int result = 0;
		try {
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			String dsUser = (String) servletContext.getAttribute("dsUser");
			String dsPassword = (String) servletContext.getAttribute("dsPassword");
			ArrayList<String> serviceUrlLists = getServiceUrls();

			Document ds = documentService.findDocumentById(Long.valueOf(documentId));
			String fileName = file.getOriginalFilename();
			//String fileN=file.getName();
			
			String dsName=Util.removeFileExtention(ds.getName());
			String dsN=dsName+" (Working Copy)."+FilenameUtils.getExtension(ds.getName());
			if(!dsN.equals(fileName)){
				ds=null;
			}
			//docname, path, caseid,stepList,adminMeta
			
			if (ds == null) {
				//String stepList="["+stepId+"]";
				result = 0;
				String html = "<script language='javascript' type='text/javascript'> window.top.window.checkinCompleted("
					+ result + ",'"+ds.getName()+"','"+path+"','"+ds.getCaseId()+"','"+stepId+"','"+adminMeta+"');</script>";
				return html;

			}

			String status = ds.getLocked();

			String tempext = Util.getType(ds.getName());

			String docName = ds.getName().replace(tempext, "");
			//docName=docName.concat("Check");
			if (!(status.equalsIgnoreCase("yes") && fileName.contains(docName))) {

				result = 0;
				String html = "<script language='javascript' type='text/javascript'> window.top.window.checkinCompleted("
					+ result + ");</script>";
				return html;

			}

			// String fileName = file.getOriginalFilename();
			byte[] bytes = file.getBytes();
			// System.out.println("filename -> " + fileName);
			// System.out.println("type -> " + file.getContentType());
			//fileName=fileName.concat("Check");
			if(ds.getDoctype().getVersion().equals("Mj")){
				if(stepId!=0){
				String tempName = Util.removeFileExtention(ds.getName());
				msg = "Successfully Checkedin";
				String[] sp=tempName.split("-");
				int length=sp.length-1;
				String newName=sp[length].toString();
				String rev=null;
				if(newName.contains(".")){
					String[] sp2=newName.split("\\.");
					newName=sp2[1];
					rev=sp2[0];
				}
				int i=Integer.valueOf(newName);
				i++;
				tempName=sp[0]+"-"+sp[1]+"-"+sp[2]+"-"+sp[3]+"-"+sp[4]+"-"+sp[5]+"-"+rev+"."+i+""+Util.getType(ds.getName());
				documentStorage.createContent(dsUser, dsPassword, path, tempName, file.getContentType(), file, serviceUrlLists);
				ds.setName(tempName);
				documentService.save(ds);
				}else{
					List<DocumentVersion> documentVersions=documentVersionService.findDocumentVersionsByDocumentId(Long.parseLong(documentId));
					String downDoc=null;
					if(!documentVersions.isEmpty()){
					for(DocumentVersion dv:documentVersions){
						downDoc=dv.getDocumentName();
						break;
					}
					String dName=Util.removeFileExtention(downDoc);
					downDoc=dName+" (Working Copy)."+FilenameUtils.getExtension(downDoc);
					msg = documentStorage.checkIn(dsUser, dsPassword, path, downDoc, file, serviceUrlLists);
				}else{
					msg = documentStorage.checkIn(dsUser, dsPassword, path, fileName, file, serviceUrlLists);
				}
				}
			}else{
			msg = documentStorage.checkIn(dsUser, dsPassword, path, fileName, file, serviceUrlLists);
			}
			result = 1;
			Long docId = Long.parseLong(documentId);

			Document document = documentService.findDocumentById(docId);
			documentService.unLock(docId);
			if (stepId != 0) {
				WfStep wfStep = caseService.findStepById(stepId);
				String taskName = wfStep.getNode().getName();
				DocumentTrail documentTrail = new DocumentTrail(document, user, "CheckIn", new Date(), taskName);
				// System.out.println("document Trail checkin with step-> "
				// + documentTrail);
				documentTrailService.save(documentTrail);
			}
			else {
				DocumentTrail documentTrail = new DocumentTrail(document, user, "CheckIn", new Date(), null);
				// System.out.println("document Trail checkin without step-> "
				// + documentTrail);
				documentTrailService.save(documentTrail);
			}
			result = 1;
			

		}
		catch (Exception ex) {
			logger.error("/checkin Error message "+ex);
			msg = "Unknown Error";
			result = 0;
		}
		finally {
			Document docu=documentService.findDocumentById(Long.parseLong(documentId));
			
			String docName=docu.getName();
			long caseId=docu.getCaseId();
			String html = "<script language='javascript' type='text/javascript'> window.top.window.checkinCompleted("
					+ result + ",'"+docName+"','"+path+"','"+caseId+"','"+stepId+"','"+adminMeta+"');</script>";
			return html;
		}
	}

	// Not used now
	@RequestMapping(value = "/review", method = RequestMethod.GET)
	public String reviewDocument(@RequestParam("documentName") String documentName, @RequestParam("path") String path,
		@RequestParam("caseId") String caseIdString, @RequestParam("stepAssigned") boolean stepAssigned, Model model) {

		// System.out.println("***********>" + path + " , " + documentName);
		Document document = documentService.findDocumentByDocNameAndPath(documentName, path);
		List<ReviewNote> reviews = reviewNoteService.findAllReviewsByDocument(document);

		model.addAttribute("stepAssigned", stepAssigned);
		model.addAttribute("reviewDocs", reviews);
		model.addAttribute("documentName", documentName);
		model.addAttribute("path", path);
		model.addAttribute("caseId", Long.parseLong(caseIdString));
		return "reviewData";
	}

	@RequestMapping(value = "/cancelcheckout", method = RequestMethod.GET)
	public void cancelCheckout(@RequestParam("path") String path, @RequestParam("documentName") String documentName,
		@RequestParam("documentId") String documentId, @RequestParam("stepId") long stepId, HttpServletResponse response,
		HttpSession session) throws Exception {
		Document doc=documentService.findDocumentById(Long.parseLong(documentId));
		// System.out.println(path + " " + documentName);
		if (documentName.contains("*")) {
			// System.out.println("***** In Ctrller Match 1");
			documentName = documentName.replace("*", "&");
		}
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		String dsUser = (String) servletContext.getAttribute("dsUser");
		String dsPassword = (String) servletContext.getAttribute("dsPassword");
		ArrayList<String> serviceUrlLists = getServiceUrls();

		// System.out.println("check out canceled called");

		String name = Util.getWorkingCopyName(documentName);
		if(doc.getDoctype().getVersion().equals("Mj")){
			if(stepId!=0){
			DocumentVersion dv=documentVersionService.findDocumentVersionByDocumentName(documentName);
			documentVersionService.delete(dv);
			}else{
				List<DocumentVersion> documentVersions=documentVersionService.findDocumentVersionsByDocumentId(Long.parseLong(documentId));
				String downDoc=null;
				if(!documentVersions.isEmpty()){
				for(DocumentVersion dv:documentVersions){
					downDoc=dv.getDocumentName();
					break;
				}
				String dName=Util.removeFileExtention(downDoc);
				downDoc=dName+" (Working Copy)."+FilenameUtils.getExtension(downDoc);
				documentStorage.undoCheckOut(dsUser, dsPassword, path, downDoc, serviceUrlLists);
				}else{
					documentStorage.undoCheckOut(dsUser, dsPassword, path, name, serviceUrlLists);
				}
			}
		}else{
		documentStorage.undoCheckOut(dsUser, dsPassword, path, name, serviceUrlLists);
		}
		Long docId = Long.parseLong(documentId);
		Document document = documentService.findDocumentById(docId);
		documentService.unLock(docId);
		// System.out.println("-------------------------------------------->"
		// + document.getLocked());
		WfStep wfStep = caseService.findStepById(stepId);
		if(wfStep!=null){
		String taskName = wfStep.getNode().getName();
		DocumentTrail documentTrail = new DocumentTrail(document, user, "Undo Check Out", new Date(), taskName);
		// System.out.println("document Trail checkout-> " + documentTrail);
		documentTrailService.save(documentTrail);
		}
	}
	
	@RequestMapping(value = "/subVersionDocuments", method = RequestMethod.GET)
	public String subDocument(@RequestParam("docId") String documentId, Model model) {

		// System.out.println("***********>" + path + " , " + documentName);
		HashMap<String,String> hm=new HashMap<String,String>();
		Document d=documentService.findDocumentById(Long.parseLong(documentId));
		List<DocumentVersion> documentVersions=documentVersionService.findDocumentVersionsByDocumentId(Long.parseLong(documentId));
		int i=documentVersions.size();
		int j=i-1;
		
		if(!documentVersions.isEmpty()){
			if(d.getWip()=='N'){
		for(DocumentVersion dv:documentVersions){
			i--;
			if(i==j)
				continue;
			else
			hm.put(dv.getDocumentName(), dv.getFilePath());
		}
		}
			else{
				for(DocumentVersion dv:documentVersions){
					hm.put(dv.getDocumentName(), dv.getFilePath());
				}
			}
		}
		model.addAttribute("searchResult", hm);
		return "subVersionDocuments";
	}
	
	@RequestMapping(value = "/downloadSubDocument", method = RequestMethod.POST)
	public void downloadDocumentSub(@RequestParam("path") String path, @RequestParam("documentName") String documentName,
		 HttpServletResponse response,
		HttpSession session) throws Exception {

		
		
		String dsUser = (String) servletContext.getAttribute("dsUser");
		String dsPassword = (String) servletContext.getAttribute("dsPassword");

		ArrayList<String> serviceUrlLists = getServiceUrls();

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		
		try {
			
				bis = new BufferedInputStream(documentStorage.download(dsUser, dsPassword, path, documentName, serviceUrlLists));
			
			response.setHeader("Content-Disposition", "attachment;filename=\"" + documentName + "\"");
			byte[] bytes = new byte[2048];
			bos = new BufferedOutputStream(response.getOutputStream());
			int read;
			while ((read = bis.read(bytes)) != -1) {
				bos.write(bytes, 0, read);
			}
		}
		finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.flush();
				bos.close();
			}
		}

		
	}
}
