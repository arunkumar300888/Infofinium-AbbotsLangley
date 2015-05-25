package uk.co.jmr.sdp.ds.alfimpl;

import java.rmi.RemoteException;

import org.alfresco.webservice.authentication.AuthenticationFault;
import org.alfresco.webservice.repository.QueryResult;
import org.alfresco.webservice.repository.RepositoryFault;
import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.ResultSet;
import org.alfresco.webservice.types.ResultSetRow;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.ISO9075;
import org.alfresco.webservice.util.WebServiceFactory;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.domain.ds.DMNode;
import uk.co.jmr.sdp.ds.WorkspaceSpider;

@Service("workspaceSpider")
public class WorkspaceSpiderImpl extends AlfrescoBase implements WorkspaceSpider {
	protected static final Store STORE = new Store(Constants.WORKSPACE_STORE, "SpacesStore");

	// public static void main(String[] args) throws RepositoryFault,
	// RemoteException {
	//
	// WorkspaceSpider spider = new WorkspaceSpider();
	// DMNode root = spider.fetchRoot("admin", "admin");
	// TreeBuilder builder = new TreeBuilder();
	// root.print();
	// String html = builder.build(root);
	// System.out.println(html);
	//
	// }

	public DMNode fetchRoot(String dsUser, String dsPassword) throws RepositoryFault, RemoteException {

		DMNode root = new DMNode("/", ALFRESCO_USERS_HOME, "-1");
		fetchChildren(root, dsUser, dsPassword);
		return root;

	}

	private void fetchChildren(DMNode parent, String userName, String passWord) throws RepositoryFault, RemoteException {

		try {
			AuthenticationUtils.startSession(userName, passWord);
		}

		catch (AuthenticationFault e) {
			System.out.println(e);
		}
		RepositoryServiceSoapBindingStub repositoryService = WebServiceFactory.getRepositoryService();

		Reference reference = new Reference(STORE, null, parent.getPath());
		QueryResult queryResult = repositoryService.queryChildren(reference);
		ResultSet resultSet = queryResult.getResultSet();
		ResultSetRow[] rows = resultSet.getRows();
		if (rows == null)
			return;
		for (ResultSetRow row : rows) {
			if (row.getNode().getType().contains("folder") == true) {
				String uuid = row.getNode().getId();
				String name = null, path = null;
				for (NamedValue namedValue : row.getColumns()) {
					if (namedValue.getName().endsWith(Constants.PROP_NAME) == true) {
						name = namedValue.getValue();
						path = parent.getPath() + "/cm:" + ISO9075.encode(name);

					}
				}
				DMNode child = new DMNode(name, path, uuid);
				parent.getChildren().add(child);
			}
		}

		for (DMNode c : parent.getChildren()) {
			fetchChildren(c, userName, passWord);
		}
	}
}
