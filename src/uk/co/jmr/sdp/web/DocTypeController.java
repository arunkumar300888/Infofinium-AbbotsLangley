package uk.co.jmr.sdp.web;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.service.DoctypeService;
import uk.co.jmr.sdp.service.DocumentService;

@Controller
@RequestMapping(value = "/docTypes")
public class DocTypeController {

	@Autowired
	private DoctypeService docTypeService;
	@Autowired
	private DocumentService documentService;

	@RequestMapping(value = "/goCreateDoctype", method = RequestMethod.GET)
	public String createDocType(Model model, HttpSession session) {

		// return "adminTemplate";
		// model.addAttribute("title","Doc Type Creation");
		// List<Doctype> docTypeLists = docTypeService.findAllDoctype();
		List<Doctype> docTypeLists = docTypeService.findAllDoctypeWithInActive();
		Collections.sort(docTypeLists);
		model.addAttribute("title", "Doc Type Lists");
		model.addAttribute("doctypes", docTypeLists);
		return "showDocTypeLists";
		// return "createDocTypes";
	}

	private List<Doctype> getDocTypeLists() {

		// List<Doctype> docTypeLists = docTypeService.findAllDoctype();
		List<Doctype> docTypeLists = docTypeService.findAllDoctypeWithInActive();
		Collections.sort(docTypeLists);
		return docTypeLists;
	}

	@RequestMapping(value = "/goCreateNewDocTypes", method = RequestMethod.GET)
	public String gocreateNew(@RequestParam("id") long docTypeId, Model model, HttpSession session) {

		model.addAttribute("title", "Doc Type Creation");
		model.addAttribute("docTypeId", docTypeId);
		if (docTypeId != -1) {
			Doctype docType = docTypeService.findDoctypeById(docTypeId);
			if (docType != null) {

				model.addAttribute("docTypeName", docType.getDoctypeName());
				model.addAttribute("description", docType.getDescription());
				model.addAttribute("isWorkflow", docType.isWorkflow());
				model.addAttribute("abbreviation", docType.getAbbreviation());
				model.addAttribute("isVersion", docType.getVersion());
				model.addAttribute("title", "Doc Type Updation");
			}
		}
		return "createDocTypes";
	}

	@RequestMapping(value = "/deleteDocType", method = RequestMethod.GET)
	public String goDeleteDoctype(@RequestParam("id") long docTypeId, Model model, HttpSession session) {

		Doctype docTypeToDelete = docTypeService.findDoctypeById(docTypeId);
		docTypeToDelete.setIsActive('N');
		docTypeService.save(docTypeToDelete);
		List<Doctype> docTypeLists = getDocTypeLists();
		model.addAttribute("title", "Doc Type Lists");
		model.addAttribute("doctypes", docTypeLists);
		return "showDocTypeLists";
	}

	@RequestMapping(value = "/unDeleteDocType", method = RequestMethod.GET)
	public String goUnDeleteDoctype(@RequestParam("id") long docTypeId, Model model, HttpSession session) {

		Doctype docTypeToUnDelete = docTypeService.findDoctypeById(docTypeId);
		docTypeToUnDelete.setIsActive('Y');
		docTypeService.save(docTypeToUnDelete);
		List<Doctype> docTypeLists = getDocTypeLists();
		model.addAttribute("title", "Doc Type Lists");
		model.addAttribute("doctypes", docTypeLists);
		return "showDocTypeLists";
	}

	@RequestMapping(value = "/createDocType", method = RequestMethod.POST)
	public String gocreateDocType(@RequestParam("docTypeID") long oldDocTypeId, @RequestParam("docType") String docType,
		@RequestParam("description") String description, @RequestParam("diffValue") boolean isWorkflow,
		@RequestParam("abbreviation") String abbreviation,@RequestParam("verValue")String version, Model model, HttpSession session) {

		// boolean isWorkflow = (request.getParameter("isWorkflow") != null &&
		// request.getParameter("isWorkflow").equals("1"));

		if (oldDocTypeId != -1) {
			List<Document> documents=documentService.findByDocType(oldDocTypeId);
			Doctype doctype = docTypeService.checkDoctypeExists(docType.trim(), abbreviation.trim());
			if (doctype != null) {
				if (doctype.isWorkflow() == isWorkflow) {
					model.addAttribute("title", "Doc Type Updation");
					model.addAttribute("docTypeId", oldDocTypeId);
					model.addAttribute("result", "Doctype already exists");
					model.addAttribute("docTypeName", docType);
					model.addAttribute("description", description);
					model.addAttribute("isWorkflow", isWorkflow);
					model.addAttribute("abbreviation", abbreviation);
					return "createDocTypes";
				}

				else {
					Doctype docTypeUpdateWorkflow = docTypeService.findDoctypeById(oldDocTypeId);
					docTypeUpdateWorkflow.setWorkflow(isWorkflow);
					docTypeService.save(docTypeUpdateWorkflow);
					List<Doctype> docTypeLists = getDocTypeLists();
					model.addAttribute("title", "Doc Type Lists");
					model.addAttribute("doctypes", docTypeLists);
					return "showDocTypeLists";
				}

			}
			boolean isDoc=false;
			for(Document d: documents){
				if(!version.equals(d.getDoctype().getVersion())){
					if(d.getWip()=='Y'){
					isDoc=true;
					break;
					}
				}
			}
			if(!documents.isEmpty()){
				if(isDoc){
				model.addAttribute("title", "Doc Type Updation");
				model.addAttribute("docTypeId", oldDocTypeId);
				model.addAttribute("result", "Doctype Version cannot be changed.It is used for WIP Documents");
				model.addAttribute("docTypeName", docType);
				model.addAttribute("description", description);
				model.addAttribute("isWorkflow", isWorkflow);
				model.addAttribute("abbreviation", abbreviation);
				return "createDocTypes";
				}
				}
			
			Doctype docTypeUpdate = docTypeService.findDoctypeById(oldDocTypeId);
			docTypeUpdate.setDoctypeName(docType.trim());
			docTypeUpdate.setDescription(description.trim());
			docTypeUpdate.setAbbreviation(abbreviation.trim());
			docTypeUpdate.setWorkflow(isWorkflow);
			docTypeUpdate.setVersion(version);
			docTypeService.save(docTypeUpdate);

			List<Doctype> docTypeLists = getDocTypeLists();
			model.addAttribute("title", "Doc Type Lists");
			model.addAttribute("doctypes", docTypeLists);
			return "showDocTypeLists";

			// model.addAttribute("title","Doc Type Updation");
			// model.addAttribute("result", "Doctype updated successfully");
			// createDocType(model,session);

		}
		else {
			Doctype doctype = docTypeService.checkDoctypeExists(docType.trim(), abbreviation.trim());
			if (doctype != null) {

				if (doctype.getIsActive() == 'N') {
					model.addAttribute("docTypeId", oldDocTypeId);
					model.addAttribute("title", "Inactive DocType Exists");
					model.addAttribute("result", "Please use a different doctype name & abbreviation");
					return "createDocTypes";
				}

				model.addAttribute("docTypeId", oldDocTypeId);
				model.addAttribute("title", "DocType Exists");
				model.addAttribute("result", "Doctype already exists");
				return "createDocTypes";
			}

			Doctype docTypeObj = new Doctype(docType.trim(), description.trim(), abbreviation.trim(), isWorkflow, 'Y',version);
			docTypeService.save(docTypeObj);
			model.addAttribute("docTypeId", oldDocTypeId);
			model.addAttribute("title", "Doc Type Creation");
			model.addAttribute("result", "Doctype created successfully");

		}
		// System.out.println("Inside");
		return "createDocTypes";
	}

}
