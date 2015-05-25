package uk.co.jmr.sdp.web.util;

import java.util.Iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import com.ardhika.wfar.WfAttribute;
import com.visural.common.web.client.WebClient;

import org.alfresco.webservice.util.ISO9075;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import uk.co.jmr.sdp.domain.CompanyUser;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.UserSession;
import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;

/**
 * @author Administrator
 * 
 */
public class Util {
	/**
	 * @param attributeList
	 * @param id
	 * @return
	 */
	// Commenting on 09.01.13 for Update properties show all attributes change
	// request by JMR
	/*
	 * private static final String[] dummyAttri = { "MDL Number", "Target Date",
	 * "Keywords" };
	 */
	private static final String[] dummyAttri = { "Target Date" };
	// private static final String[] dummyAttri = {};
	private static final String seperator = " ";
	private static final String DIFFMACHINEMESSAGE = "You are already logged in to another machine. Would you like to close the previous session and log in again on this machine?";
	private static final String DIFFBROWSERMESSAGE = "You are already logged in to another session, please click the button to logout of the previous session and activate this current session";
	private static final String GENERALMESSAGE = "You are already logged in with different Machine or Browser.Make sure if you login using following link will make the other login to logout";
	public static final String ADMIN_ALL = "ADMIN_ALL";
	public static final String ADMIN_ATTR = "ADMIN_ATTR";
	public static final String ADMIN_AV = "ADMIN_AV";
	public static final String ADMIN_GROUP = "ADMIN_GROUP";
	public static final String ADMIN_ROLE = "ADMIN_ROLE";
	public static final String ADMIN_TM = "ADMIN_TM";
	public static final String ADMIN_USER = "ADMIN_USER";
	public static final String ADMIN_WF = "ADMIN_WF";
	public static final String ADMIN_FEATURE = "ADMIN_FEATURE";
	public static final String ADMIN_DT = "ADMIN_DT";
	public static final String ADMIN_SG = "ADMIN_SG";
	// Workflow Adminstration
	public static final String ADMIN_DELETE = "ADMIN_DELETE";
	public static final String ADMIN_UNCLAIM = "ADMIN_UNCLAIM";
	public static final String ADMIN_REASSIGN = "ADMIN_REASSIGN";
	public static final String ADMIN_REASSIGN_OWNER = "ADMIN_REASSIGN_OWNER";
	
	//Transaction Features		
			public static final String ADMIN_AUDIT_HISTORY="ADMIN_AUDIT_HISTORY";
			public static final String ADMIN_REF_LINKING="ADMIN_REF_LINKING";
			public static final String ADMIN_REVISION="ADMIN_REVISION";
			public static final String ADMIN_CLAIM="ADMIN_CLAIM";
			public static final String ADMIN_COMMENTS="ADMIN_COMMENTS";
			public static final String ADMIN_CHECKOUT="ADMIN_CHECKOUT";
			public static final String ADMIN_CHECKIN_UNDO_CHECKOUT="ADMIN_CHECKIN_UNDO_CHECKOUT";
			public static final String ADMIN_DOWNLOAD="ADMIN_DOWNLOAD";
			public static final String ADMIN_UP_PROPERTIES="ADMIN_UP_PROPERTIES";
			public static final String ADMIN_ABANDON="ADMIN_ABANDON";
			public static final String ADMIN_SEARCH="ADMIN_SEARCH";
			public static final String ADMIN_MY_DOCUMENT="ADMIN_WIP_DOCUMENT";
			public static final String ADMIN_SUB_VERSION="ADMIN_SUB_VERSION";
			public static final String ADMIN_FORM_SECURITY="ADMIN_FORM_SECURITY";
		

	public static Set<WfAttribute> getDummyWfAttribute() {

		Set<WfAttribute> dummyAttributes = new HashSet<WfAttribute>();
		for (String name : dummyAttri) {
			WfAttribute wfAttri = new WfAttribute(name);
			dummyAttributes.add(wfAttri);
		}
		return dummyAttributes;
	}

	public static List<SecurityGroup> securityGroupWithOutOpen(List<SecurityGroup> sgs){
		List<SecurityGroup> newSecGrps=new ArrayList<SecurityGroup>();
		for(SecurityGroup sg:sgs){
			if(!sg.getName().equalsIgnoreCase("OPEN")){
				newSecGrps.add(sg);
			}
		}
		return newSecGrps;
	}
	
	public static String setToString(Set<SecurityGroup> secGrp){
		String secGroups="";
		for(SecurityGroup sg:secGrp){
			secGroups=secGroups+sg.getId()+",";
		}
		secGroups=secGroups.substring(0, secGroups.lastIndexOf(",")) ;
		//System.out.println("-------------> Group of security "+secGroups);
		return secGroups;
	}
	
	public static Set<AttributeValue> getActiveAttributeValues(Set<AttributeValue> attr1Values) {

		Set<AttributeValue> attrValueSet = new TreeSet<AttributeValue>();
		for (AttributeValue attrValue : attr1Values) {
			if (attrValue.getIsActive() == 'Y') {
				attrValueSet.add(attrValue);
			}
		}
		return attrValueSet;
	}
	
	

	public static Set<AttributeValue> getAttributeBasedOnId(List<Attribute> attributeList, long id) {

		for (Iterator<Attribute> iterator = attributeList.iterator(); iterator.hasNext();) {
			Attribute attribute = (Attribute) iterator.next();
			if (attribute.getId() == id) {
				return new TreeSet<AttributeValue>(attribute.getAttrValues());
			}
		}
		return null;
	}

	public static String encode(String encodeString) {

		return ISO9075.encode(encodeString);
	}

	public static StringBuilder generateFileName(String project, String docabbreviation, String keyword, String site,
		String displine) {

		String temp = "-";
		StringBuilder sb = new StringBuilder(project.toUpperCase()).append(temp);
		sb.append(docabbreviation.toUpperCase()).append(temp).append(keyword.toUpperCase()).append(temp)
			.append(site.toUpperCase()).append(temp).append(displine.toUpperCase()).append(temp);
		return sb;
	}

	public static String getFirstThreeChars(String name) {

		if (name.length() >= 3) {
			return (name.substring(0, 3));
		}
		return null;
	}

	public static String getType(String fileName) {

		if (FilenameUtils.getExtension(fileName) != null && FilenameUtils.getExtension(fileName) != "") {
			return "." + FilenameUtils.getExtension(fileName);
		}
		else {
			return "";
		}
	}

	public static String splitUserName(String userName) {

		if (userName != null && !userName.equals("")) {
			String[] split = userName.split(":");
			if (split.length > 1) {
				return split[1];
			}
			return userName;
		}
		return null;
	}

	public static String[] split(String keywords) {

		if (keywords != null && keywords.length() != 0 && keywords.trim().length() != 0) {
			return keywords.split(seperator);
		}
		return null;
	}

	public static String getMachine(WebClient webclient, UserSession userSession, String ipAddress, Model model) {

		if (!ipAddress.equals(userSession.getIpaddress())) {
			model.addAttribute("message", DIFFMACHINEMESSAGE);
		}
		else if (!userSession.getBrowser().equals(webclient.getUserAgent().name())) {
			model.addAttribute("message", DIFFBROWSERMESSAGE);
		}
		else if (userSession.getDeviceos().equals(webclient.getPlatform().name())) {
			model.addAttribute("message", DIFFMACHINEMESSAGE);
		}
		else if (userSession.getBrowser().equals(webclient.getUserAgent().name())
			|| userSession.getVersion().equals(webclient.getFullVersion())) {
			model.addAttribute("message", DIFFMACHINEMESSAGE);
		}
		else {
			model.addAttribute("message", GENERALMESSAGE);
		}
		return "invalidSession";
	}

	public static boolean isNull(Object... args) {

		for (Object object : args) {
			if (object == null) {
				return true;
			}
		}
		return false;
	}

	public static String getFilePathFromWfAttribute(Set<WfAttribute> wfattribute) {

		for (WfAttribute attributes : wfattribute) {
			if (attributes.getName().equalsIgnoreCase("FilePath")) {
				return (String) attributes.getValue();
			}
		}
		return null;
	}

	public static String fileNameRevisioning(String tempFileName) {

		return null;
	}

	public static int getCount(String tempName, String revisionProp) {

		// For Revision No
		int count = 1;
		if (null != tempName) {
			String[] revName = tempName.split("-");
			int length = revName.length - 1;
			String revName1 = revName[length].toString();
			if (revName1.contains(revisionProp)) {
				revName1 = revName1.replace(revisionProp, "");
				
				count = revName1.length();
				
			}
		}
		//
		return count;
	}

	public static int getCurrentFileVersion(String fileName, String revisionProp) {

		String extention = Util.getType(fileName);
		String tempName = fileName.replaceAll(extention, "");
		int strLength = tempName.length();
		int count = getCount(tempName, revisionProp);
		// String revisionStr = tempName.substring(strLength - 1, strLength);
		//
		String revisionStr = tempName.substring(strLength - count, strLength);
		//
		return Integer.valueOf(revisionStr);
	}

	public static String removeFileExtention(String tempFileName) {

		return tempFileName.replaceAll(getType(tempFileName), "");
	}

	public static String getWorkingCopyName(String fileName) {

		String ext = Util.getType(fileName);
		StringBuilder sb = new StringBuilder(Util.removeFileExtention(fileName));
		sb.append(" (Working Copy)").append(ext);
		return sb.toString();
	}
}