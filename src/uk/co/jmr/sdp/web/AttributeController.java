package uk.co.jmr.sdp.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.service.DtAttributeService;

@Controller
@RequestMapping(value = "/attributes")
public class AttributeController {

	@Autowired
	private DtAttributeService dtAttributeService;

	@RequestMapping(value = "/goShowAttribute", method = RequestMethod.GET)
	public String showAttribute(Model model, HttpSession session) {

		model.addAttribute("title", "Attributes List");
		boolean canShowAddNewAttr = false;
		List<Attribute> attrLists = dtAttributeService.findAllDtAttrs();
		if (attrLists.size() == 0) {
			canShowAddNewAttr = true;
			model.addAttribute("canShowAddNewAttr", canShowAddNewAttr);
		}
		model.addAttribute("attrLists", attrLists);
		return "showAttributes";
		// return "attributes";
	}

	@RequestMapping(value = "/goShowNewAttribute", method = RequestMethod.GET)
	public String showNewAttribute(Model model, HttpSession session) {

		List<Attribute> attrLists = dtAttributeService.findAllDtAttrs();
		if (attrLists == null) {
			model.addAttribute("title", "Add New Attributes");
			return "attributes";
		}
		return null;

	}

	private List<Attribute> showAllAttributes() {

		List<Attribute> attrLists = dtAttributeService.findAllDtAttrs();
		return attrLists;
	}

	@RequestMapping(value = "/saveAttribute", method = RequestMethod.POST)
	public String updateAttribute(@RequestParam("attrId") long attrId, @RequestParam("attr") String attr, Model model,
		HttpSession session) {

		attr = attr.trim();
	
		Attribute checkObj = dtAttributeService.findDtAttributeByName(attr);
		if (checkObj != null) {
			model.addAttribute("title", "Attribute Exists");
			model.addAttribute("attr", attr);
			model.addAttribute("attrId", attrId);
			model.addAttribute("result", "Please use a different attribute name");
			return "editAttr";
		}

		Attribute attrObj = dtAttributeService.findDtAttributeById(attrId);
		attrObj.setName(attr);
		// attrObj.setId(attrId);
		dtAttributeService.save(attrObj);
		List<Attribute> updateAttrList = showAllAttributes();
		model.addAttribute("title", "Updated Attributes");
		model.addAttribute("attrLists", updateAttrList);
		return "showAttributes";
		
	}

	@RequestMapping(value = "/goCreateAttribute", method = RequestMethod.GET)
	public String createAttribute(@RequestParam("id") long id, Model model, HttpSession session) {

		Attribute attr = dtAttributeService.findDtAttributeById(id);
		model.addAttribute("attr", attr.getName());
		model.addAttribute("attrId", attr.getId());
		model.addAttribute("title", "Edit Attributes");
		return "editAttr";
		// return "attributes";
	}

	@RequestMapping(value = "/createAttributes", method = RequestMethod.POST)
	public String gocreateAttributes(@RequestParam("attr1") String attr1, @RequestParam("attr2") String attr2,
		@RequestParam("attr3") String attr3, @RequestParam("attr4") String attr4, Model model, HttpSession session) {

		try {
			attr1 = attr1.trim();
			attr2 = attr2.trim();
			attr3 = attr3.trim();
			attr4 = attr4.trim();

			int attr1Value = 1;
			int attr2Value = 2;
			int attr3Value = 3;
			int attr4Value = 4;
			Attribute attrObj = null;

			List<String> attrStr = new ArrayList<String>();
			attrStr.add(attr1);
			attrStr.add(attr2);
			attrStr.add(attr3);
			attrStr.add(attr4);

			for (String attr : attrStr) {
				attrObj = dtAttributeService.findDtAttributeByName(attr);
				if (attrObj != null) {
					model.addAttribute("title", "Attributes");
					model.addAttribute("result", attrObj.getName() + " already exists");
					model.addAttribute("attr1", attr1);
					model.addAttribute("attr2", attr2);
					model.addAttribute("attr3", attr3);
					model.addAttribute("attr4", attr4);
					return "attributes";

				}
			}
			attr1 = WordUtils.capitalizeFully(attr1);
			attr2 = WordUtils.capitalizeFully(attr2);
			attr3 = WordUtils.capitalizeFully(attr3);
			attr4 = WordUtils.capitalizeFully(attr4);

			Attribute attr1Obj = new Attribute(attr1, attr1Value);
			Attribute attr2Obj = new Attribute(attr2, attr2Value);
			Attribute attr3Obj = new Attribute(attr3, attr3Value);
			Attribute attr4Obj = new Attribute(attr4, attr4Value);
			List<Attribute> attrLists = new ArrayList<Attribute>();
			attrLists.add(attr1Obj);
			attrLists.add(attr2Obj);
			attrLists.add(attr3Obj);
			attrLists.add(attr4Obj);

			// this.dtAttributeService.save(attr1Obj);
			for (Attribute attr : attrLists) {
				// System.out.println("Inside Attributes Save");
				this.dtAttributeService.save(attr);
			}
			model.addAttribute("title", "Attributes");
			model.addAttribute("result", "Attribute created successfully");
		}
		catch (Exception e) {
			System.err.println("Exception Occured");

		}

		return "attributes";
		// return "adminTemplate";
	}

}
