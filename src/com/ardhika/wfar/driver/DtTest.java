package com.ardhika.wfar.driver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.dt.DtTemplateCombo;
import uk.co.jmr.sdp.domain.dt.DtTemplateComboValue;
import uk.co.jmr.sdp.service.DoctypeService;
import uk.co.jmr.sdp.service.DtAttributeService;
import uk.co.jmr.sdp.service.DtAttributeValueService;
import uk.co.jmr.sdp.service.DtTemplateComboService;

public class DtTest {

	public static <E> void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("root-context.xml");
		DtTemplateComboService templ = context.getBean("dtTemplateComboService", DtTemplateComboService.class);
		DtAttributeValueService av = context.getBean("dtAttributeValueService", DtAttributeValueService.class);

		Set<DtTemplateCombo> dtc1 = templ.findMatchForFirstLevelAttrValue(1);
		System.out.println("Match for 1 " + dtc1);
		Set<AttributeValue> attr2 = templ.getSecondLevelAttrValuesFromTemplateCombos(dtc1);
		for (AttributeValue v : attr2) {
			System.out.println(v);
		}

		AttributeValue secondLevelAttrValue = av.findDtAttrValueById(6);
		AttributeValue thirdLevelAttrValue = av.findDtAttrValueById(8);
		System.out.println("secondLevelAttrValue " + secondLevelAttrValue);

		Set<DtTemplateCombo> dtc2 = templ.findMatchForSecondLevelAttrValue(dtc1, secondLevelAttrValue);
		System.out.println("dtc2 -> " + dtc2);
		Set<AttributeValue> attr3 = templ.getThirdLevelAttrValuesFromTemplateCombos(dtc2);
		System.out.println(attr3);

		Set<DtTemplateCombo> dtc3 = templ.findMatchForThirdLevelAttrValue(dtc2, thirdLevelAttrValue);
		System.out.println("dtc3 -> " + dtc3);
		Set<AttributeValue> attr4 = templ.getFourthLevelAttrValuesFromTemplateCombos(dtc3);
		System.out.println(attr4);

	}

	private static void test(ApplicationContext context) {

		DoctypeService doctype = context.getBean("doctypeService", DoctypeService.class);
		Doctype dc = doctype.findDoctypeById(1);
		DtTemplateComboService templ = context.getBean("dtTemplateComboService", DtTemplateComboService.class);
		Set<DtTemplateCombo> dtc = templ.findMatchForFirstLevelAttrValue(1);

		List<AttributeValue> attr2 = new ArrayList<AttributeValue>();
		Set<DtTemplateComboValue> dset = new HashSet<DtTemplateComboValue>();

		for (DtTemplateCombo d1 : dtc) {
			System.out.println("d1->>>> " + d1);
			System.out.println("dc" + dc);
			if (d1.getDoctype().equals(dc)) {
				System.out.println("In IF");
				for (DtTemplateComboValue dv : d1.getTemplateComboValues()) {
					dset.add(dv);
				}
			}
		}
		System.out.println("dset-> " + dset);
		for (DtTemplateComboValue dv : dset) {
			attr2.add(dv.getAttributeValue());
		}
		System.out.println(attr2);
	}
}
