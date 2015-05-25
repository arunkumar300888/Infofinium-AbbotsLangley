package uk.co.jmr.sdp.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.service.HolidayService;

import com.ardhika.wfar.WfCaseStatus;
import com.ardhika.wfar.WfCase;
import com.ardhika.wfar.WfStep;
import java.util.ArrayList;
import java.util.Set;
import uk.co.jmr.sdp.service.CaseUserFormsService;
import uk.co.jmr.sdp.service.DocumentService;
import uk.co.jmr.sdp.service.UserService;
import uk.co.jmr.webforms.db.service.UserFormsService;

public class GridBuilder {
	public String buildDocumentList(List<uk.co.jmr.sdp.domain.Document> docList, List<WfCase> casesWithDocs,
		List<WfCase> casesWithoutDocs, Map<Long, WfStep> stepMap, Map<Long, Character> readStatus, boolean showReadRagStatus,
		HolidayService holidayService, CaseUserFormsService caseUserFormsService, UserFormsService userFormsService,
		UserService userService,DocumentService documentService) {

		StringBuilder sb = new StringBuilder();
		RagoStatus ragoStatus = new RagoStatus(holidayService);
		Map<Long, WfCase> casesMap = new HashMap<Long, WfCase>();
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	//	sb.append("<div style='width: 675px; overflow-x: scroll;'><table cellpadding='0' cellspacing='0' border='0' class='display' id='example' width='1400px'><thead><tr>");
		sb.append("<div class='my-document complete'><div class='place-table'>").append(
				"<table class='documents-table display' id='example'>").append(
						"<thead><tr class='first-row'>");
		if (showReadRagStatus) {
			sb.append("<th class='col order' title='RAG Status'>RAG</th>").append("<th  class='col order' title='Unread'>Unread</th>");
		}
		sb.append("<th class='col order'>Latest-Revision</th>");
		sb.append("<th class='col order'>Name</th>").append("<th class='col order'>Status - Action</th>").append("<th class='col order'>Submission Date</th>")
			.append("<th class='col order'>Document Owner</th>").append("<th class='col order'>Workflow Process</th>").append("<th class='col order'>Document Type</th>")
			.append("<th class='col order'>Ref. No</th>").append("<th class='col order'>Raised Date & Time</th><th class='col order'>Model Category</th>").append("</tr></thead><tbody>");
		for (WfCase c : casesWithoutDocs) {
			if (c.getModel().getModelCategory() == 'F') {
				Document doc=documentService.findDocumentForCaseId(c.getId());
				Set<WfStep> stps = c.getSteps();
				StringBuilder stBuf = new StringBuilder();
				stBuf.append("[");
				for (WfStep s : stps) {
					stBuf.append(s.getId());
					stBuf.append(",");
					break;
				}
				String stepList = stBuf.substring(0, stBuf.length() - 1);
				stepList += "]";
				String taskStatus = "";
				for (WfStep s : stps) {
					taskStatus += s.getStatusLabel() + " - " + s.getNode().getName();
					taskStatus += "|";
					break;
				}
				Object val = c.getAttribute("FilePath");
				String p = (val == null) ? "-" : (String) val;
				ArrayList<String> formValue = ExtGridBuilder
					.getFormValues(c, caseUserFormsService, userFormsService, userService,documentService);
				
				//if (caseUserFormsService.findCaseUserFormsByCaseId(c.getId()).isAbandon()) {
				if (documentService.findDocumentForCaseId(c.getId()).isAbandon()) {
				
					stepList = "'Nil'";
					taskStatus = "Abandoned|";
				}
				sb.append("<tr class='gradeB'onClick=\"loadFormProperties('").append(formValue.get(1).replaceAll("&", "%26"))
					.append("', '").append(p).append("', ").append(c.getId()).append(",").append(stepList).append(",'false');\"")
					.append(" title='").append(formValue.get(0).replaceAll("&", "&amp;")).append("'>");
				if (showReadRagStatus) {
					sb.append(ExtGridBuilder.buildReadRagStatus(ragoStatus.getRagoStatus(c), readStatus.get(c.getId())
						.charValue()));
				}
				String createdDate = dateTimeFormat.format(c.getDateCreated());
				sb.append("<td></td>");
				sb.append("<td>").append(formValue.get(1)).append("</td>").append("<td>")
					.append(taskStatus.substring(0, taskStatus.length() - 1)).append("</td>").append("<td>")
					.append(formValue.get(2)).append("</td>").append("<td>").append(c.getCreator()).append("</td>")
					.append("<td>").append(c.getModel().getName()).append("</td>").append("<td>").append(formValue.get(3))
					.append("</td>").append("<td>--</td>").append("<td>").append(createdDate).append("</td>").append("<td>").append(doc.getDiscriminator()).append("</td>").append("</tr>");
			}
			else {
				Object val = c.getAttribute("FilePath");
				String p = (val == null) ? "-" : (String) val;
				sb.append("<tr class='gradeB' onClick=\"loadMetadata('" + "-" + "', '").append(p).append("', ").append(c.getId())
					.append(",'false');\"> ");
				if (showReadRagStatus) {
					sb.append(ExtGridBuilder.buildReadRagStatus(ragoStatus.getRagoStatus(c), readStatus.get(c.getId())
						.charValue()));
				}
				sb.append("<td></td>").append("<td></td>").append("<td></td>").append("<td></td>").append("<td></td>")
					.append("<td></td>").append("<td></td>").append("<td></td>").append("<td>").append(c.getCreator()).append("</td>")
					.append("</tr>");
			}
		}
		if ((docList != null) && !docList.isEmpty()) {
			for (WfCase c : casesWithDocs) {
				casesMap.put(c.getId(), c);
			}
			for (uk.co.jmr.sdp.domain.Document d : docList) {
				Date date = d.getTargetDate();
				String targetDate;
				String createdDate;
				
				createdDate = dateTimeFormat.format(d.getDateCreated());
				if (date != null) {
					SimpleDateFormat commonFormat = new SimpleDateFormat("dd-MM-yyyy");
					targetDate = commonFormat.format(date);
				}
				else
					targetDate = "-";
				WfCase c = casesMap.get(d.getCaseId());
				sb.append("<tr class='gradeB' onClick=\"loadMetadata('").append(d.getName()).append("', '")
					.append(d.getFilePath()).append("', ").append(d.getCaseId()).append(",'Nil','false');\"> ");
				if (showReadRagStatus) {
					sb.append(ExtGridBuilder.buildReadRagStatus(ragoStatus.getRagoStatus(c), readStatus.get(c.getId())
						.charValue()));
				}
				//Revisioned Document Flag
				if (d.isRevisionable() == true && d.getDiscriminator()=='D') {
					sb.append("<td><h1 style='display:none;'>1</h1><img src='resources/images/green_flag.png'/></td>");
				}
				else {
					sb.append("<td ><h1 style='display:none;'>2</h1></td>");
				}
				sb.append("<td>").append(d.getName()).append("</td>");
				if (c != null) {
					if (showReadRagStatus)
						sb.append("<td>").append(getTaskStatus(c, stepMap)).append("</td>");
					else {
						if (c.getCaseStatus() == WfCaseStatus.WF_CASE_CANCELLED)
							sb.append("<td> Abandoned </td>");
						else
							sb.append("<td> Published </td>");
					}
				}
				else {
					sb.append("<td>" + "Published" + "</td>");
				}
				sb.append("<td>").append(targetDate).append("</td>");
				if (c != null) {
					if(d.getReassignOwner()==null){
					sb.append("<td>").append(c.getCreator()).append("</td>");
					}else{
						sb.append("<td>").append(d.getReassignOwner()).append("</td>");
					}
					sb.append("<td>").append(c.getModel().getName()).append("</td>");
				}
				else {
					sb.append("<td>").append(d.getAuthor()).append("</td>");
					sb.append("<td>" + "-----" + "</td>");
				}
				if(d.getDoctype()!=null){
					sb.append("<td>").append(d.getDoctype().getDoctypeName()).append("</td>");
				}
				else{
					sb.append("<td>").append("-").append("</td>");
				}
				if (d.getEbNo() != null) {
					sb.append("<td>").append(d.getEbNo()).append("</td>");
				}
				else {
					sb.append("<td>" + "-----" + "</td>");
				}
				sb.append("<td>").append(createdDate).append("</td>");
				sb.append("<td>").append(d.getDiscriminator()).append("</td>");
				sb.append("</tr>");
			}
		}
		sb.append("</tbody></table></div>");
		return sb.toString();
	}

	private String getTaskStatus(WfCase c, Map<Long, WfStep> stepMap) {

		String taskStatus = null;
		WfStep step = stepMap.get(c.getId());
		taskStatus = step.getStatusLabel() + " - " + step.getNode().getName();
		return taskStatus;
	}
}
