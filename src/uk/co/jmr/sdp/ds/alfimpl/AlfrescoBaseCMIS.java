package uk.co.jmr.sdp.ds.alfimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

public class AlfrescoBaseCMIS {

	public static Session createSession(String userName, String passWord, ArrayList<String> serviceUrls) {

		SessionFactory f = SessionFactoryImpl.newInstance();
		Map<String, String> parameter = new HashMap<String, String>();

		// user credentials
		parameter.put(SessionParameter.USER, userName);
		parameter.put(SessionParameter.PASSWORD, passWord);
		// connection settings
		// parameter.put(SessionParameter.ATOMPUB_URL,
		// "http://localhost:8080/alfresco/service/cmis");
		// parameter.put(SessionParameter.BINDING_TYPE,
		// BindingType.ATOMPUB.value());
		// parameter.put(SessionParameter.AUTH_HTTP_BASIC, "true" );
		// parameter.put(SessionParameter.COOKIES, "true" );

		// parameter.put(SessionParameter.WEBSERVICES_REPOSITORY_SERVICE,
		// "http://localhost:8080/alfresco/cmis/RepositoryService?wsdl");
		// parameter.put(SessionParameter.WEBSERVICES_NAVIGATION_SERVICE,
		// "http://localhost:8080/alfresco/cmis/NavigationService?wsdl");
		// parameter.put(SessionParameter.WEBSERVICES_OBJECT_SERVICE,
		// "http://localhost:8080/alfresco/cmis/ObjectService?wsdl");
		// parameter.put(SessionParameter.WEBSERVICES_VERSIONING_SERVICE,
		// "http://localhost:8080/alfresco/cmis/VersioningService?wsdl");
		// parameter.put(SessionParameter.WEBSERVICES_DISCOVERY_SERVICE,
		// "http://localhost:8080/alfresco/cmis/DiscoveryService?wsdl");
		// parameter.put(SessionParameter.WEBSERVICES_MULTIFILING_SERVICE,
		// "http://localhost:8080/alfresco/cmis/MultiFilingService?wsdl");
		// parameter.put(SessionParameter.WEBSERVICES_RELATIONSHIP_SERVICE,
		// "http://localhost:8080/alfresco/cmis/RelationshipService?wsdl");
		// parameter.put(SessionParameter.WEBSERVICES_ACL_SERVICE,
		// "http://localhost:8080/alfresco/cmis/ACLService?wsdl");
		// parameter.put(SessionParameter.WEBSERVICES_POLICY_SERVICE,
		// "http://localhost:8080/alfresco/cmis/PolicyService?wsdl");

		parameter.put(SessionParameter.WEBSERVICES_REPOSITORY_SERVICE, serviceUrls.get(0));
		parameter.put(SessionParameter.WEBSERVICES_NAVIGATION_SERVICE, serviceUrls.get(1));
		parameter.put(SessionParameter.WEBSERVICES_OBJECT_SERVICE, serviceUrls.get(2));
		parameter.put(SessionParameter.WEBSERVICES_VERSIONING_SERVICE, serviceUrls.get(3));
		parameter.put(SessionParameter.WEBSERVICES_DISCOVERY_SERVICE, serviceUrls.get(4));
		parameter.put(SessionParameter.WEBSERVICES_MULTIFILING_SERVICE, serviceUrls.get(5));
		parameter.put(SessionParameter.WEBSERVICES_RELATIONSHIP_SERVICE, serviceUrls.get(6));
		parameter.put(SessionParameter.WEBSERVICES_ACL_SERVICE, serviceUrls.get(7));
		parameter.put(SessionParameter.WEBSERVICES_POLICY_SERVICE, serviceUrls.get(8));

		parameter.put(SessionParameter.BINDING_TYPE, BindingType.WEBSERVICES.value());
		parameter.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");
		// create session
		return f.getRepositories(parameter).get(0).createSession();

	}

}
