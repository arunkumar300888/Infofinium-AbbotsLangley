package uk.co.jmr.sdp.ds.alfimpl;

import org.alfresco.webservice.authoring.AuthoringServiceSoapBindingStub;
import org.alfresco.webservice.content.ContentServiceSoapBindingStub;
import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.types.ParentReference;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.WebServiceFactory;

public class AlfrescoBase {

	protected static final Store STORE = new Store(Constants.WORKSPACE_STORE, "SpacesStore");

	protected static final String ALFRESCO_USERS_HOME = "/app:company_home/app:user_homes";

	protected static final String ALFRESCO_COMPANY_HOME = "/app:company_home";

	protected static final String ASSOC_CONTAINS = "{http://www.alfresco.org/model/content/1.0}contains";

	// Alfresco Webservices Reference

	RepositoryServiceSoapBindingStub repositoryService = WebServiceFactory.getRepositoryService();
	ContentServiceSoapBindingStub contentService = WebServiceFactory.getContentService();
	AuthoringServiceSoapBindingStub authoringService = WebServiceFactory.getAuthoringService();

	protected ParentReference getUserHomes() {

		ParentReference userHomeParentRef = new ParentReference(STORE, null, ALFRESCO_USERS_HOME, Constants.ASSOC_CONTAINS, null);
		return userHomeParentRef;
	}

	protected ParentReference getCompanyHome() {

		ParentReference companyHomeParent = new ParentReference(STORE, null, ALFRESCO_COMPANY_HOME, Constants.ASSOC_CONTAINS,
			null);
		return companyHomeParent;
	}

	protected ContentServiceSoapBindingStub getContentService() {

		return WebServiceFactory.getContentService();
	}

	protected RepositoryServiceSoapBindingStub getRepositoryService() {

		return WebServiceFactory.getRepositoryService();
	}

}
