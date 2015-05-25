package uk.co.jmr.sdp.web.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.co.jmr.sdp.domain.CaseUserForms;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.ReviewNote;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.CaseUserFormsService;
import uk.co.jmr.sdp.service.DocumentService;
import uk.co.jmr.sdp.service.FormService;
import uk.co.jmr.sdp.service.HolidayService;
import uk.co.jmr.sdp.service.UserService;
import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.pojo.UserForms;
import uk.co.jmr.webforms.db.service.UserFormsService;
import uk.co.jmr.sdp.service.ReviewNoteService;

import com.ardhika.wfar.CaseStepInfo;
import com.ardhika.wfar.WfCase;
import com.ardhika.wfar.WfStep;

public class ExtGridBuilder {
	public String buildDocumentList(Map<Long, CaseStepInfo> csi, List<WfStep> steps, Map<Long, Document> docMap,
		boolean showReadRagStatus, Map<Long, Character> readStatus, HolidayService holidayService,
		CaseUserFormsService caseUserFormsService, UserFormsService userFormsService, UserService userService,DocumentService documentService, ReviewNoteService reviewNoteService) {

		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		RagoStatus ragoStatus = new RagoStatus(holidayService);
		StringBuilder sb = new StringBuilder("<div class='my-document complete box-content'><div class='place-table'>").append(
			"<table class='documents-table display' id='example'>").append(
			"<thead><tr class='first-row'>");

		if (showReadRagStatus) {
			sb.append("<th class='ceas-col'><img src='resources/images/wizart/clock.png'></th><th title='Unread' class='col order'>Unread</th>");
		}

		sb.append("<th class='col order'>Name</th>").append("<th class='col order'>Status - Action</th>").append("<th class='col order'>Submission Date</th>")
			.append("<th class='col order'>Document Owner</th>").append("<th class='col order'>Workflow Process</th>").append("<th class='col order'>Document Type</th>")
			.append("<th class='col order'>Ref. No</th>").append("<th class='col order'>Raised Date & Time</th>").append("<th class='col order'>Model Category</th><th class='col order'>Comments</th>")
			.append("</tr></thead><tbody>");

		for (Long caseId : csi.keySet()) {
			CaseStepInfo cs = csi.get(caseId);
			String createdDate;

			WfCase wfCase = cs.getWfCase();
			Document doc = docMap.get(caseId);
			List<WfStep> stps = cs.getStepsList();
			StringBuilder stBuf = new StringBuilder();
			stBuf.append("[");
			for (WfStep s : stps) {
				stBuf.append(s.getId());
				stBuf.append(",");
			}

			String stepList = stBuf.substring(0, stBuf.length() - 1);
			stepList += "]";
			if (wfCase.getModel().getModelCategory() == 'F') {
				// System.out.println("In Form Based");
				String taskStatus = "";
				for (WfStep s : stps) {
					taskStatus += s.getStatusLabel() + " - " + s.getNode().getName();
					taskStatus += "|";
				}

				Object val = wfCase.getAttribute("FilePath");

				String p = (val == null) ? "-" : (String) val;
				ArrayList<String> formValue = getFormValues(wfCase, caseUserFormsService, userFormsService, userService,documentService);
				if (formValue != null) {
					sb.append("<tr class='even'onClick=\"loadFormProperties('").append(formValue.get(1).replaceAll("&", "%26").replaceAll("'","%26"))
						.append("', '").append(p).append("', ").append(caseId).append(",").append(stepList).append(",'false');\"")
						.append(" title='").append(formValue.get(0).replaceAll("&", "&amp;")).append("'>");
				}
				else {
					sb.append("<tr class='odd'onClick=\"loadFormProperties('" + "-" + "', '").append(p).append("', ")
						.append(caseId).append(",").append(stepList).append(",'false');\"> ");
				}

				if (showReadRagStatus) {
					sb.append(buildReadRagStatus(ragoStatus.getRagoStatus(wfCase), readStatus.get(wfCase.getId()).charValue()));
				}

				if (formValue != null) {
					sb.append("<td class='col'>").append(formValue.get(1)).append("</td>");
				}
				else {
					sb.append("<td class='col'></td>");
				}

				sb.append("<td>").append(taskStatus.substring(0, taskStatus.length() - 1)).append("</td>");
				if (formValue != null) {
					sb.append("<td>").append(formValue.get(2)).append("</td>");
				}
				else {
					sb.append("<td></td>");
				}
				
					sb.append("<td>").append(wfCase.getCreator()).append("</td>").append("<td>").append(wfCase.getModel().getName())
					.append("</td>").append("<td>").append(formValue.get(3)).append("</td>").append("<td>-</td>");

				createdDate = dateTimeFormat.format(wfCase.getDateCreated());
				sb.append("<td>").append(createdDate).append("</td>").append("<td>").append(wfCase.getModel().getModelCategory())
					.append("</td>");
								
				List<ReviewNote> commentsList =  reviewNoteService.findAllReviewsByCaseId(wfCase.getId());
				if(commentsList.size() > 0){
					sb.append("<td> Y </td>");
				}else{
					sb.append("<td> N </td>");
				}
				
				sb.append("</tr>");

			}
			else { 
				/* For Target Date Validation */
				Date date = doc.getTargetDate();
				String targetDate;

				createdDate = dateTimeFormat.format(doc.getDateCreated());
				if (date != null) {
					SimpleDateFormat commonFormat = new SimpleDateFormat("dd-MM-yyyy");
					targetDate = commonFormat.format(date);
				}
				else {
					targetDate = "-";
				}
				/* End For Target Date Validation */
				Object val = wfCase.getAttribute("FilePath");
				String p = (val == null) ? "-" : (String) val;
				sb.append("<tr class='odd' onClick=\"loadMetadata('").append(doc.getName()).append("', '").append(p)
					.append("', ").append(caseId).append(",").append(stepList).append(",'false');\"> ");

				if (showReadRagStatus) {
					sb.append(buildReadRagStatus(ragoStatus.getRagoStatus(wfCase), readStatus.get(wfCase.getId()).charValue()));
				}

				sb.append("<td>").append(doc.getName()).append("</td>");
				String taskStatus = "";
				for (WfStep s : stps) {
					taskStatus += s.getStatusLabel() + " - " + s.getNode().getName();
					taskStatus += "|";
				}
				if (showReadRagStatus) {
					sb.append("<td>").append(taskStatus.substring(0, taskStatus.length() - 1)).append("</td>");
				}
				else {
					sb.append("<td> Published </td>");
				}

				sb.append("<td>").append(targetDate).append("</td>");
				sb.append("<td>").append(wfCase.getCreator()).append("</td>");
				sb.append("<td>").append(wfCase.getModel().getName()).append("</td>");
				sb.append("<td>").append(doc.getDoctype().getDoctypeName()).append("</td>");

				if (doc.getEbNo() != null) {
					sb.append("<td>").append(doc.getEbNo()).append("</td>");
				}
				else {
					sb.append("<td>-----</td>");
				}

				sb.append("<td>").append(createdDate).append("</td>");
				sb.append("<td>").append(wfCase.getModel().getModelCategory()).append("</td>");
				
				List<ReviewNote> commentsList =  reviewNoteService.findAllReviewsByCaseId(wfCase.getId());
				
				if(commentsList.size() > 0){
					sb.append("<td> Y </td>");
				}else{
					sb.append("<td> N </td>");
				}
				sb.append("</tr>");

			}
		}

		sb.append("</tbody></table></div></div>");
		return sb.toString();
	}

	public static ArrayList<String> getFormValues(WfCase wfCase, CaseUserFormsService caseUserFormsService,
		UserFormsService userFormsService, UserService userService,DocumentService documentService) {
		ArrayList<String> formList = new ArrayList<String>();
		Set<Forms> forms = wfCase.getModel().getForms();
		Iterator<Forms> fli = forms.iterator();
		while (fli.hasNext()) {
			Forms form = fli.next();
			Set<UserForms> uf = form.getUserFormses();
			for (UserForms userForms : uf) {
				long userId = userForms.getUserId();
				long userFormId = userForms.getId();
				String name = wfCase.getCreator();
				User userWorkflow = userService.findUserByUserName(name);
				if (userWorkflow.getId() == userId) {
//					CaseUserForms caseUserForms = caseUserFormsService.findCaseUserFormsByUserFormsIdAndCaseId(userFormId,
//						wfCase.getId());
			         Document docUserForms=documentService.findDocumentsByUserFormsIdAndCaseId(userFormId, 	wfCase.getId());
				//	if (caseUserForms != null) {
			         if(docUserForms!=null){
						String formDefName = userForms.getForms().getFormDefs().getName();
						String formDescription = userForms.getForms().getFormDefs().getDescription();
						//Date formDefDate = userForms.getCreatedOn();
						Date formDefDate=docUserForms.getTargetDate();
						SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy");
						String formCreatedDate = dateTimeFormat.format(formDefDate);
						formList.add(formDescription);
					//	formList.add(caseUserForms.getName());
						formList.add(docUserForms.getName());
						formList.add(formCreatedDate);
						formList.add(formDefName);
						return formList;
					}
				}
			}
		}

		return null;
	}

	
	public static String buildReadRagStatus(char rago, char readStatus) {
		StringBuilder sb = new StringBuilder();
		/* Rag Status */
		if (rago == 'N')
			sb.append("<td></td>");
		else if (rago == 'R')
			sb.append("<td width='2%' style='padding-left: 20px; padding-right: 4px;'><h1 style='display:none;'>1</h1><img src='resources/images/crclred.png' style='height:25px;' /></td>");
		else if (rago == 'G')
			sb.append("<td width='2%' style='padding-left: 20px; padding-right: 4px;'><h1 style='display:none;'>3</h1><img src='resources/images/crclgreen.png' style='height:25px;'/></td>");
		else if (rago == 'A')
			sb.append("<td width='2%' style='padding-left: 20px; padding-right: 4px;'><h1 style='display:none;'>2</h1><img src='resources/images/crclamber.png' style='height:25px;'/></td>");
		else if (rago == 'O')
			sb.append("<td width='2%' style='padding-left: 20px; padding-right: 4px;'><h1 style='display:none;'>0</h1><img src='resources/images/overdue.png' style='height:25px;'/></td>");
		else
			sb.append("<td></td>");
		/* End Rag Status */

		/* Read Status */
		sb.append("<td width='2%' title='Unread'>");
		if (readStatus == 'U') {
			sb.append("<h1 style='display:none;'>1</h1><img src='resources/images/unread.png' style='height:25px;'/>");
		}

		sb.append("</td>");
		return sb.toString();
	}
}
