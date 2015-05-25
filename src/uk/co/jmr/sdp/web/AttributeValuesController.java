package uk.co.jmr.sdp.web;

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
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.service.DtAttributeService;
import uk.co.jmr.sdp.service.DtAttributeValueService;

@Controller
@RequestMapping(value = "/attributeValues")
public class AttributeValuesController {

	@Autowired
	private DtAttributeService dtAttributeService;
	@Autowired
	private DtAttributeValueService dtAttributeValueService;

	@RequestMapping(value = "/goShowAttributeValues", method = RequestMethod.GET)
	public String showAttributeValues(Model model, HttpSession session) {

		model.addAttribute("title", "Attributes Values Lists");
		// List<AttributeValue>
		// attrValueLists=dtAttributeValueService.findAllDtAttrValues();
		// For All Lists With In Active
		List<AttributeValue> attrValueLists = dtAttributeValueService.findAllDtAttrValuesWithInActive();
		model.addAttribute("attrValueLists", attrValueLists);
		return "showAttributeValues";
	}

	private List<AttributeValue> getAttrValueLists() {

		// List<AttributeValue>
		// attrValueLists=dtAttributeValueService.findAllDtAttrValues();
		List<AttributeValue> attrValueLists = dtAttributeValueService.findAllDtAttrValuesWithInActive();
		return attrValueLists;
	}

	@RequestMapping(value = "/goEditAttribute", method = RequestMethod.GET)
	public String editAttributeValues(@RequestParam("id") long attrValueId, Model model, HttpSession session) {

		AttributeValue attrValue = dtAttributeValueService.findDtAttrValueById(attrValueId);
		String attrName = attrValue.getAttr().getName();
		model.addAttribute("attrName", attrName);
		model.addAttribute("attrValue", attrValue.getValue());
		model.addAttribute("abbreviation", attrValue.getAbbreviation());
		model.addAttribute("title", "Edit Attribute Values");
		model.addAttribute("attrValueId", attrValueId);
		return "editAttributeValues";
	}

	@RequestMapping(value = "/deleteAttributeValue", method = RequestMethod.GET)
	public String goDeleteAttributeValue(@RequestParam("id") long attrValueId, Model model) {

		// System.out.println("In Delete");
		AttributeValue attrValueDelete = dtAttributeValueService.findDtAttrValueById(attrValueId);
		attrValueDelete.setIsActive('N');
		dtAttributeValueService.save(attrValueDelete);
		// List<AttributeValue>
		// attrValueUpdateLists=dtAttributeValueService.findAllDtAttrValues();
		// For All Lists With In Active
		List<AttributeValue> attrValueUpdateLists = dtAttributeValueService.findAllDtAttrValuesWithInActive();
		model.addAttribute("title", "Attribute Values Lists");
		model.addAttribute("attrValueLists", attrValueUpdateLists);
		return "showAttributeValues";
	}

	@RequestMapping(value = "/unDeleteModeAttrValueClicked", method = RequestMethod.GET)
	public String goUnDeleteAttributeValue(@RequestParam("id") long attrValueId, Model model) {

		// System.out.println("In Delete");
		AttributeValue attrValueUnDelete = dtAttributeValueService.findDtAttrValueById(attrValueId);
		attrValueUnDelete.setIsActive('Y');
		dtAttributeValueService.save(attrValueUnDelete);
		// List<AttributeValue>
		// attrValueUpdateLists=dtAttributeValueService.findAllDtAttrValues();
		List<AttributeValue> attrValueUpdateLists = dtAttributeValueService.findAllDtAttrValuesWithInActive();
		model.addAttribute("title", "Attribute Values Lists");
		model.addAttribute("attrValueLists", attrValueUpdateLists);
		return "showAttributeValues";
	}

	@RequestMapping(value = "/editSaveAttributeValue", method = RequestMethod.POST)
	public String editAndSaveAttributeValue(@RequestParam("attrName") String attrName,
		@RequestParam("attrValueId") long attrValueId, @RequestParam("attrValue") String attrValue,
		@RequestParam("abbreviation") String abbreviation, Model model, HttpSession session) {

		attrName = attrName.trim();
		attrValue = WordUtils.capitalizeFully(attrValue).trim();
		abbreviation = abbreviation.toUpperCase().trim();
		AttributeValue checkAttrValueObj = dtAttributeValueService.findDtAttrValueByNameAndAbbreviation(attrValue, abbreviation);
		if (checkAttrValueObj != null) {
			model.addAttribute("attrName", attrName);
			model.addAttribute("attrValue", attrValue);
			model.addAttribute("abbreviation", abbreviation);
			model.addAttribute("title", "Edit Attribute Values");
			model.addAttribute("result", "Attribute value already exists");
			model.addAttribute("attrValueId", attrValueId);
			return "editAttributeValues";
		}
		AttributeValue attrValueObj = dtAttributeValueService.findDtAttrValueById(attrValueId);
		attrValueObj.setValue(attrValue);
		attrValueObj.setAbbreviation(abbreviation);
		dtAttributeValueService.save(attrValueObj);
		List<AttributeValue> attrValueUpdatedLists = getAttrValueLists();
		model.addAttribute("attrValueLists", attrValueUpdatedLists);
		model.addAttribute("title", "Attribute Values Lists");
		return "showAttributeValues";
	}

	@RequestMapping(value = "/goCreateAttributeValues", method = RequestMethod.GET)
	public String createNewAttributeValues(@RequestParam("id") long attrValueId, Model model, HttpSession session) {

		List<Attribute> attr = dtAttributeService.findAllDtAttrs();
		model.addAttribute("attr", attr);
		model.addAttribute("attrValueId", attrValueId);
		model.addAttribute("title", "Attributes Values");
		return "attributeValues";
	}

	// @RequestMapping(value = "/goCreateAttributeValues", method =
	// RequestMethod.GET)
	// public String createAttributeValues(Model model, HttpSession session) {
	// List<Attribute> attr=dtAttributeService.findAllDtAttrs();
	// model.addAttribute("attr",attr);
	// model.addAttribute("title","Attributes Values");
	// return "attributeValues";
	// }

	@RequestMapping(value = "/createAttributeValues", method = RequestMethod.POST)
	public String gocreateAttributeValues(@RequestParam("attr") long attrId, @RequestParam("attrValue") String attrValue,
		@RequestParam("abbreviation") String abbreviation, Model model, HttpSession session) {

		List<Attribute> attr = dtAttributeService.findAllDtAttrs();
		attrValue = WordUtils.capitalizeFully(attrValue).trim();
		abbreviation = abbreviation.toUpperCase().trim();
		Attribute attrObj = dtAttributeService.findDtAttributeById(attrId);
		AttributeValue checkAttrValueObj = dtAttributeValueService.findDtAttrValueByNameAndAbbreviation(attrValue, abbreviation);
		// System.out.println("Attr Val:" +checkAttrValueObj);
		if (checkAttrValueObj != null) {
			if (checkAttrValueObj.getIsActive() == 'N') {
				model.addAttribute("title", "Inactive Attribute Value Exists");
				model.addAttribute("result", "Please use a different attribute value name & abbreviation");
				model.addAttribute("attr", attr);
				return "attributeValues";
			}

			model.addAttribute("title", "Attribute Values");
			model.addAttribute("result", "Attribute value already exists");
			model.addAttribute("attr", attr);
			return "attributeValues";
		}

		// AttributeValue
		// attrValueCheck=dtAttributeValueService.checkAttributeValueExits(attrId,
		// attrValue, abbreviation);
		// AttributeValue attrValues=new AttributeValue(attrValue, attrObj,
		// abbreviation);
		AttributeValue attrValues = new AttributeValue(attrValue, attrObj, abbreviation, 'Y');
		// AttributeValue
		// attrValueCheck=dtAttributeValueService.checkAttributeValueExits(Attribute
		// attrObj, attrValue, abbreviation);
		dtAttributeValueService.save(attrValues);
		// System.out.println("Inside Attribute Values Ctrl");
		model.addAttribute("title", "Attribute Values");
		model.addAttribute("result", "Attribute value created successfully");
		model.addAttribute("attr", attr);
		return "attributeValues";

	}

}
