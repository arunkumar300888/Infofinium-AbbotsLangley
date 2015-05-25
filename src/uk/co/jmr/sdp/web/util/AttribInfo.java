package uk.co.jmr.sdp.web.util;

import java.util.ArrayList;
import java.util.List;

import uk.co.jmr.sdp.domain.dt.AttributeValue;

public class AttribInfo {

	private AttributeValue dtAttributeValue;
	List<AttributeValue> otherAttrs = new ArrayList<AttributeValue>();

	public AttribInfo() {

	}

	public AttribInfo(AttributeValue dtAttributeValue) {

		this.dtAttributeValue = dtAttributeValue;
	}

	public AttribInfo(AttributeValue dtAttributeValue, List<AttributeValue> otherAttrs) {

		this.dtAttributeValue = dtAttributeValue;
		this.otherAttrs = otherAttrs;
	}

	public AttributeValue getDtAttributeValue() {

		return dtAttributeValue;
	}

	public void setDtAttributeValue(AttributeValue dtAttributeValue) {

		this.dtAttributeValue = dtAttributeValue;
	}

	public List<AttributeValue> getOtherAttrs() {

		return otherAttrs;
	}

	public void setOtherAttrs(List<AttributeValue> otherAttrs) {

		this.otherAttrs = otherAttrs;
	}

	@Override
	public String toString() {

		return "AttribInfo [dtAttributeValue=" + dtAttributeValue + ", otherAttrs=" + otherAttrs + "]";
	}

}
