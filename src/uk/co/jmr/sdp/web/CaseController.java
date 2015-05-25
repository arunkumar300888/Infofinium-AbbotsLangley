package uk.co.jmr.sdp.web;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.alfresco.webservice.util.ISO9075;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import uk.co.jmr.sdp.domain.Discipline;
import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.DocumentTrail;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.ds.DocumentStorage;
import uk.co.jmr.sdp.service.DisciplineService;
import uk.co.jmr.sdp.service.DoctypeService;
import uk.co.jmr.sdp.service.DocumentService;
import uk.co.jmr.sdp.service.DocumentTrailService;
import uk.co.jmr.sdp.web.util.UserInfo;

import com.ardhika.wfar.WfAttribute;
import com.ardhika.wfar.WfAttributeType;
import com.ardhika.wfar.WfCase;
import com.ardhika.wfar.WfModel;
import com.ardhika.wfar.WfStep;
import com.ardhika.wfar.service.CaseService;
import com.ardhika.wfar.service.ModelService;

@Controller
@RequestMapping(value = "/case")
public class CaseController {

	private ServletContext servletContext;
	@Autowired
	private DocumentStorage documentStorage;
	@Autowired
	private CaseService caseService;
	@Autowired
	private ModelService modelService;
	@Autowired
	private DoctypeService docTypeService;
	@Autowired
	private DisciplineService disciplineService;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private DocumentTrailService documentTrailService;
	
	Logger logger=Logger.getLogger(CaseController.class);

	@RequestMapping(value = "/goCreate", method = RequestMethod.GET)
	public String createForm(Model model, HttpSession session) {

		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		List<WfModel> models = modelService.canCreateCase(userInfo);
		model.addAttribute("models", models);
		return "createNew";
	}

	@RequestMapping(value = "/listTemplates", method = RequestMethod.GET)
	public String listTemplates(Model model, HttpSession session, @RequestParam("modelId") long modelId) {

		WfModel wfModel = modelService.findModelById(modelId);
		List<String> dtAttribs = new ArrayList<String>();
		for (WfAttribute a : wfModel.getAttribs()) {
			String name = a.getName().trim();
			//System.out.println("Name:" + name);
			// System.out.println("Dt Name"+name.substring(0,3));
				
			if (name.substring(0, 3).equalsIgnoreCase("dt:")) {

				String docType = name.substring(3);
				//System.out.println("DT:" + docType);
				
				// String value = a.getAttrText();
				dtAttribs.add(docType);
			}
		}
		model.addAttribute("dtAttribs", dtAttribs);
		model.addAttribute("modelId", modelId);
		return "listTemplates";
	}

	@RequestMapping(value = "/createCase", method = RequestMethod.GET)
	public String createCase(HttpSession session, Model model, @RequestParam("modelId") long modelId) throws Exception {

		WfModel wfModel = modelService.findModelById(modelId);

		Set<WfAttribute> modelAttr = wfModel.getAttribs();
		Set<WfAttribute> attrSet = new HashSet<WfAttribute>();
		for (WfAttribute a : modelAttr) {
			if (a.getUserEditable().equalsIgnoreCase("Y")) {
				attrSet.add(a);
			}
		}
		WfAttribute[] attr = attrSet.toArray(new WfAttribute[attrSet.size()]);
		Arrays.sort(attr);
		//System.out.println("----------------------------------------------------------------------------------");

		model.addAttribute("modelName", wfModel.getName());
		model.addAttribute("modelId", modelId);
		model.addAttribute("timeBound", wfModel.getTimeBound());
		model.addAttribute("attr", attr);
		return "editAttributes";
	}

	private WfCase saveCaseWithAttributes(HttpServletRequest request, HttpSession session, WfModel wfModel) throws Exception {

		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		WfStep step = modelService.createCase(wfModel.getName(), userInfo);
		WfCase wfCase = step.getOwningCase();

		String s;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		for (WfAttribute a : wfCase.getAttributes()) {
			if (a.getUserEditable().equalsIgnoreCase("Y")) {

				if (a.getType() == WfAttributeType.WF_ATTR_TEXT) {
					//System.out.println("*** Name:" + a.getName());
					s = request.getParameter("it_" + a.getName());

					// s=a.getName();
					//System.out.println("***** s:" + s);
					if (s.trim().length() > 0) {
						wfCase.setAttribute(a.getName(), s);
					}
				}
				else if (a.getType() == WfAttributeType.WF_ATTR_DATE) {
					s = request.getParameter("id_" + a.getName());
					//System.out.println("date s" + s);
					if (s != null && s.trim().length() != 0) {
						// date = dateFormat.parse(s);
						wfCase.setAttribute(a.getName(), dateFormat.parse(s));
					}
				}
				if (a.getType() == WfAttributeType.WF_ATTR_DECIMAL) {
					s = request.getParameter("if_" + a.getName());
					Double d = doubleParse(s);
					wfCase.setAttribute(a.getName(), d);
				}
				if (a.getType() == WfAttributeType.WF_ATTR_NUMBER) {
					s = request.getParameter("in_" + a.getName());
					Long l = longParse(s);
					wfCase.setAttribute(a.getName(), l);
				}
			}
		}
		this.caseService.saveCase(wfCase);
		return wfCase;
	}

	private double doubleParse(String s) {

		try {
			double d = Double.parseDouble(s);
			return d;
		}
		catch (Exception e) {
			logger.error("CaseController doubleParse() Error message "+e);
			return 0.0d;
		}
	}

	private long longParse(String s) {

		try {
			long l = Long.parseLong(s);
			return l;
		}
		catch (Exception e) {
			logger.error("CaseController longParse() Error message " +e);
			return 0;
			
		}
	}

	// @RequestMapping(value = "/downloadTemplate", method = RequestMethod.POST)
	// public void downloadTemplate(HttpSession session,
	// HttpServletResponse response,
	// @RequestParam("modelId") long modelId,
	// @RequestParam("docType") String docType) throws Exception {
	//
	// String dsUser = (String) servletContext.getAttribute("dsUser");
	// String dsPassword = (String) servletContext.getAttribute("dsPassword");
	// System.out.println("In downloadtemp ");
	//
	// WfModel wfModel = modelService.findModelById(modelId);
	//
	// String path = wfModel.getAttribute("dt:" + docType).getAttrText();
	// System.out.println("Path: " + path);
	// InputStream is = documentStorage.downloadTemplate(dsUser, dsPassword,
	// path);
	// String docName = path.substring(path.lastIndexOf(":") + 1);
	// docName = ISO9075.decode(docName);
	// System.out.println("docName-> " + docName);
	// response.setHeader("Content-Disposition", "attachment;filename=\""
	// + docName + "\"");
	// byte[] bytes = new byte[2048];
	// OutputStream os = response.getOutputStream();
	// int read;
	// while ((read = is.read(bytes)) != -1) {
	// os.write(bytes, 0, read);
	// }
	// os.flush();
	// os.close();
	// is.close();
	// }

	@RequestMapping(value = "/uploadDoc", method = RequestMethod.GET)
	public String uploadDocument(@RequestParam("caseId") long caseId, Model model) {

		model.addAttribute("caseId", caseId);
		return "uploadDoc";
	}

	@RequestMapping(value = "/showSteps", method = RequestMethod.GET)
	public String showSteps(@RequestParam("caseId") long caseId, HttpSession session, Model model) {

		//System.out.println("case id---=== " + caseId);
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		WfCase wfCase = caseService.findCaseById(caseId);

		List<WfStep> steps = caseService.findAssignedSteps(userInfo);
		//System.out.println("steps->" + steps);
		model.addAttribute("steps", steps);
		model.addAttribute("caseId", caseId);
		return "showStepTable";

	}

}
