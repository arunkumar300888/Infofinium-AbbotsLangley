package uk.co.jmr.sdp.ds.alfimpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.alfresco.cmis.client.AlfrescoDocument;
import org.alfresco.webservice.authentication.AuthenticationFault;
import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.repository.QueryResult;
import org.alfresco.webservice.repository.RepositoryFault;
import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLAddAspect;
import org.alfresco.webservice.types.CMLCreate;
import org.alfresco.webservice.types.ContentFormat;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.ParentReference;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Query;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.ResultSet;
import org.alfresco.webservice.types.ResultSetRow;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.ISO9075;
import org.alfresco.webservice.util.Utils;
import org.alfresco.webservice.util.WebServiceFactory;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.Action;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisBaseException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import uk.co.jmr.sdp.domain.ds.Document;
import uk.co.jmr.sdp.ds.DocumentStorage;

@Service("documentStorage")
public class DocumentStorageAlfImpl extends AlfrescoBase implements DocumentStorage {

	// Cleared
	@Override
	public InputStream download(String dsUser, String dsPassword, String path, String documentName, ArrayList<String> serviceUrls)
		throws Exception {

		Session session = AlfrescoBaseCMIS.createSession(dsUser, dsPassword, serviceUrls);
		String downloadPath = path.concat("/").concat(documentName);
		CmisObject object = session.getObjectByPath(downloadPath);
		String id = object.getId();
		org.apache.chemistry.opencmis.client.api.Document doc = (org.apache.chemistry.opencmis.client.api.Document) session
			.getObject(id);

		try {
			ContentStream contentStream = doc.getContentStream(); // returns
																	// null if
																	// the
																	// document
																	// has no
																	// content
			if (contentStream != null) {
				// System.out.println("Contents are: " + contentStream);
				InputStream in = contentStream.getStream();
				return in;

			}
			else {
				System.out.println("No content.");

			}

		}
		catch (Exception e) {
			System.err.println("Cannot get the content");
			throw e;
		}

		return null;
	}

	public InputStream downloadTemplate(String dsUser, String dsPassword, String templatePath, ArrayList<String> serviceUrls)
		throws Exception {

		// String paths[]=ISO9075.decode(templatePath).split("/cm:");
		// String docName=paths[paths.length-1].toString();
		// String
		// path="/User Homes/DSJV/2.0 Project Controls".concat("/").concat(docName);
		// System.out.println("Template Path:" +templatePath);
		Session session = AlfrescoBaseCMIS.createSession(dsUser, dsPassword, serviceUrls);
		// String downloadPath = path.concat("/").concat(documentName);
		CmisObject object = session.getObjectByPath(templatePath);
		String id = object.getId();
		org.apache.chemistry.opencmis.client.api.Document doc = (org.apache.chemistry.opencmis.client.api.Document) session
			.getObject(id);

		try {
			ContentStream contentStream = doc.getContentStream(); // returns
																	// null if
																	// the
																	// document
																	// has no
																	// content
			if (contentStream != null) {
				// System.out.println("Contents are: " + contentStream);
				InputStream in = contentStream.getStream();
				return in;

			}
			else {
				// System.out.println("No content.");

			}

		}
		catch (Exception e) {
			System.err.println("Cannot get the content");
			throw e;
		}

		return null;
	}

	// cleared
	@Override
	public List<Object> checkOut(String dsUser, String dsPassword, String path, String documentName, ArrayList<String> serviceUrls)
		throws Exception {

		// String newPath = "/User Homes/DSJV/11.0 Construction Process/";
		// newPath=newPath.concat(documentName);
		String newPath = path + "/" + documentName;

		Session session = AlfrescoBaseCMIS.createSession(dsUser, dsPassword, serviceUrls);
		// Session session=null;
		// CmisObject object = session.getObjectByPath(newPath);
		// String id = object.getId();
		org.apache.chemistry.opencmis.client.api.Document document = (org.apache.chemistry.opencmis.client.api.Document) session
			.getObjectByPath(newPath);
		if (document.getAllowableActions().getAllowableActions().contains(Action.CAN_CHECK_OUT)) {
			// System.out.println("can check out " + document.getName());
			org.apache.chemistry.opencmis.client.api.Document pwc = (org.apache.chemistry.opencmis.client.api.Document) session
				.getObject(document.checkOut());
			// System.out.println("PWC:" + pwc);
			ContentStream contentStream = pwc.getContentStream();
			InputStream in = contentStream.getStream();
			List<Object> list = new ArrayList<Object>();
			list.add(pwc.getName());
			list.add(in);
			// System.out.println("Check out Document Successfully");
			return list;
			// System.out.println("Check out:" + document.checkOut().getId());

		}
		else {
			//System.out.println("can not check out " + document.getName());
		}
		return null;
	}

	// cleared
	@Override
	public String checkIn(String dsUser, String dsPassword, String path, String fileName, CommonsMultipartFile file,
		ArrayList<String> serviceUrls) throws Exception {

		String msg = "";
		try {

			// String ref = "";
			// AuthenticationUtils.startSession(dsUser,dsPassword);
			String ext = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
			//System.out.println("ext name:::: " + ext);
			// String contType = file.getContentType();

			Session session = AlfrescoBaseCMIS.createSession(dsUser, dsPassword, serviceUrls);
			String newPath = path + "/" + fileName;
			// System.out.println("PATH:" +newPath );
			// Session session=null;

			// String newPath = "/User Homes/DSJV/11.0 Construction Process/";
			// newPath=newPath.concat(fileName);
			// Document pwc = (Document) session.getObjectByPath(newPath);

			org.apache.chemistry.opencmis.client.api.Document pwc = (org.apache.chemistry.opencmis.client.api.Document) session
				.getObjectByPath(newPath);

			// File content = new File(
			// "C:/Users/kad019/Downloads/alfresco-community-3.4.d-installer-win-x32 (Working Copy).exe");
			// String mimeType = new
			// MimetypesFileTypeMap().getContentType(file);

			String mimeType = file.getContentType();
			ByteArrayInputStream input = new ByteArrayInputStream(file.getBytes());
			ContentStream cs = session.getObjectFactory().createContentStream(fileName, file.getBytes().length, mimeType, input);
			
			pwc.checkIn(false, null, cs, "Check In Document");
			//System.out.println("Finished");
			msg = "Successfully Checkedin";
			return msg;
		}

		finally {
			// AuthenticationUtils.endSession();
		}

	}

	// Cleared
	@Override
	public List<Document> getDocuments(String dsUser, String dsPassword, String path) throws RepositoryFault, RemoteException {

		try {
			AuthenticationUtils.startSession(dsUser, dsPassword);
		}
		catch (AuthenticationFault e) {
			e.printStackTrace();
		}
		List<Document> results = new ArrayList<Document>();

		RepositoryServiceSoapBindingStub repositoryService = WebServiceFactory.getRepositoryService();

		// Get a reference to the space we have named
		// Reference reference = new Reference(STORE, null,
		// ALFRESCO_USERS_HOME + ((path.length()) >0 ? "/cm:" + path : ""));

		Reference reference = new Reference(STORE, null, path);

		QueryResult queryResult = repositoryService.queryChildren(reference);

		ResultSet resultSet = queryResult.getResultSet();
		ResultSetRow[] rows = resultSet.getRows();

		if (rows != null) {
			// Get the infomation from the result set
			for (ResultSetRow row : rows) {
				// String nodeId = row.getNode().getId();
				// ContentResult contentResult = new ContentResult(nodeId);
				if (!row.getNode().getType().contains("folder")) {
					Document doc = new Document();

					for (NamedValue namedValue : row.getColumns()) {
						if (namedValue.getName().endsWith(Constants.PROP_CREATED) == true) {
							doc.setCreateDate(namedValue.getValue());
						}
						else if (namedValue.getName().endsWith(Constants.PROP_NAME) == true) {
							doc.setName(namedValue.getValue());
						}
						else if (namedValue.getName().endsWith(Constants.PROP_DESCRIPTION) == true) {
							doc.setDescription(namedValue.getValue());
						}
						else if (namedValue.getName().endsWith(Constants.PROP_TITLE) == true) {
							doc.setTitle(namedValue.getValue());
						}
						doc.setPath(path);
					}
					results.add(doc);
				}
			}
		}
		return results;
	}

	// Cleared
	@Override
	public List<Document> getDocuments(String dsUser, String dsPassword) throws RepositoryFault, RemoteException {

		return getDocuments(dsUser, dsPassword, ALFRESCO_USERS_HOME);
	}

	// Cleared
	@SuppressWarnings("finally")
	@Override
	public HashMap<String, String> getMetaData(String dsUser, String dsPassword, String path, String documentName)
		throws ServiceException, RemoteException, RepositoryFault {

		HashMap<String, String> result = new HashMap<String, String>();
		try {
			AuthenticationUtils.startSession(dsUser, dsPassword);
			RepositoryServiceSoapBindingStub repositoryService = WebServiceFactory.getRepositoryService();
			String qs = "PATH:\"" + path + "/cm:" + ISO9075.encode(documentName) + "\"";
			//System.out.println("QS = " + qs);
			Query query = new Query(Constants.QUERY_LANG_LUCENE, qs);
			QueryResult queryResult = repositoryService.query(STORE, query, false);
			ResultSet resultSet = queryResult.getResultSet();
			ResultSetRow[] rows = resultSet.getRows();
			if (rows != null) {
				//System.out.println("Results from Lucene query:");
				for (int x = 0; x < rows.length; x++) {
					ResultSetRow row = rows[x];
					NamedValue[] columns = row.getColumns();
					for (int y = 0; y < columns.length; y++) {
						result.put(row.getColumns(y).getName().split("}")[1], row.getColumns(y).getValue());
					}
				}
			}
		}
		finally {
			AuthenticationUtils.endSession();
			return result;
		}
	}

	@Override
	public void createContent(String dsUser, String dsPassword, String path, String documentName, String contentType, byte[] bytes)
		throws Exception {

		try {
			// String docQName;
			// docQName=System.currentTimeMillis()+"-"+documentName;
			AuthenticationUtils.startSession(dsUser, dsPassword);
			ParentReference parentReference = new ParentReference(STORE, null, path, ASSOC_CONTAINS, "{"
				+ Constants.NAMESPACE_CONTENT_MODEL + "}" + documentName);
			ContentFormat contentFormat = new ContentFormat(contentType, "UTF-8");

			NamedValue[] properties = new NamedValue[] { Utils.createNamedValue(Constants.PROP_NAME, documentName) };
			CMLCreate create = new CMLCreate("1", parentReference, null, null, null, Constants.TYPE_CONTENT, properties);
			CML cml = new CML();
			cml.setCreate(new CMLCreate[] { create });
			UpdateResult[] result = WebServiceFactory.getRepositoryService().update(cml);

			Reference newContentNode = result[0].getDestination();
			Content content = contentService.write(newContentNode, Constants.PROP_CONTENT, bytes, contentFormat);
			makeVersionable(repositoryService, content.getNode());
		}
		finally {
			AuthenticationUtils.endSession();
		}

	}

	private static void makeVersionable(RepositoryServiceSoapBindingStub respositoryService, Reference reference)
		throws Exception {

		// Create the add aspect query object
		Predicate predicate = new Predicate(new Reference[] { reference }, null, null);
		CMLAddAspect addAspect = new CMLAddAspect(Constants.ASPECT_VERSIONABLE, null, predicate, null);

		// Create the content management language query
		CML cml = new CML();
		cml.setAddAspect(new CMLAddAspect[] { addAspect });

		// Execute the query, which will add the versionable aspect to the node
		// is question
		respositoryService.update(cml);
	}

	public void copy(String dsUser, String dsPassword, String inputPath, String outputPath, String documentName,
		String outputFileName, ArrayList<String> serviceUrls) throws Exception {

		// outputPath="/User homes/DSJV/18.0 Common Folder";
		// Session session=null;
		Session session = AlfrescoBaseCMIS.createSession(dsUser, dsPassword, serviceUrls);
		Folder parentFolder = (Folder) session.getObjectByPath(outputPath);
		String copyPath = inputPath + "/" + documentName;
		org.apache.chemistry.opencmis.client.api.Document doc = (org.apache.chemistry.opencmis.client.api.Document) session
			.getObjectByPath(copyPath);
		Map<String, Object> documentProperties = new HashMap<String, Object>(2);
		documentProperties.put(PropertyIds.NAME, outputFileName);
		documentProperties.put(PropertyIds.OBJECT_TYPE_ID, doc.getBaseTypeId().value());
		doc.copy(parentFolder, documentProperties, null, null, null, null, null);

	}

	public void undoCheckOut(String dsUser, String dsPassword, String path, String documentName, ArrayList<String> serviceUrls)
		throws Exception {

		// String newPath = "/User Homes/DSJV/11.0 Construction Process/";
		// newPath=newPath.concat(documentName);
		String newPath = path + "/" + documentName;
		Session session = AlfrescoBaseCMIS.createSession(dsUser, dsPassword, serviceUrls);
		// Session session=null;
		// CmisObject object = session.getObjectByPath(newPath);
		// String id = object.getId();
		org.apache.chemistry.opencmis.client.api.Document document = (org.apache.chemistry.opencmis.client.api.Document) session
			.getObjectByPath(newPath);
		if (!document.getAllowableActions().getAllowableActions().contains(Action.CAN_CHECK_OUT)) {
			// System.out.println("Inside Undo");
			document.cancelCheckOut();
		}
	}

	// For testing Abandon Document ie) Rename Document

	@Override
	public void update(String dsUser, String dsPassword, String path, String documentName, String newDocName,
		ArrayList<String> serviceUrls) throws Exception {

		// Session session=null;
		Session session = AlfrescoBaseCMIS.createSession(dsUser, dsPassword, serviceUrls);
		Map<String, Object> properties = new HashMap<String, Object>();
		String newPath = path + "/" + documentName;
		org.apache.chemistry.opencmis.client.api.Document doc = (org.apache.chemistry.opencmis.client.api.Document) session
			.getObjectByPath(newPath);
		properties.put(PropertyIds.NAME, newDocName);
		doc.updateProperties(properties);

		// AlfrescoDocument alfDoc = (AlfrescoDocument) doc;
		// alfDoc.addAspect("P:cm:taggable");
		// properties.put("cm:description", "JMR Test Update");
		// properties.put("cm:title",newDocName);
		// alfDoc.updateProperties(properties);

		// properties.put(PropertyIds.OBJECT_TYPE_ID,
		// "cmis:document,P:cm:titled");
		// properties.put("cm:title", "some title");
		// properties.put("cm:description", "some description");

		// doc.updateProperties(properties);
	}

	// For Testing Large File Upload Start
	public void createContent(String dsUser, String dsPassword, String path, String documentName, String contentType,
		CommonsMultipartFile file, ArrayList<String> serviceUrls) throws Exception {

		// System.out.println("PATH in Upload DMS:" +path);
		Session session = AlfrescoBaseCMIS.createSession(dsUser, dsPassword, serviceUrls);
		try {
			String mimeType = file.getContentType();
			// logger.debug("mimetype: " + mimeType);
			// logger.debug("file: " + content.getAbsolutePath());
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(PropertyIds.OBJECT_TYPE_ID, BaseTypeId.CMIS_DOCUMENT.value());
			properties.put(PropertyIds.NAME, documentName);

			// properties.put(PropertyIds.OBJECT_TYPE_ID,
			// "cmis:document,P:cm:titled");
			// properties.put("cm:title",documentName);
			// properties.put("cm:description", "My document");

			ByteArrayInputStream input = new ByteArrayInputStream(file.getBytes());
			ContentStream cs = session.getObjectFactory().createContentStream(documentName, file.getBytes().length, mimeType,
				input);
			Folder folder = (Folder) session.getObjectByPath(path);
			// Document doc = folder.createDocument(properties, cs,
			// VersioningState.MAJOR);
			org.apache.chemistry.opencmis.client.api.Document doc = folder.createDocument(properties, cs, VersioningState.MAJOR);
			String docId = doc.getId();
			//System.out.println("Doc ID:" + docId);

		}
		catch (CmisBaseException e) {
			// System.err.println("Exce:" +e.toString());
			e.printStackTrace();
			// logger.error("error uploading file: "+ e.getMessage(), e);
		}
	}
	
	
	//To Upload Files into alfresco from local directory
		public void createContentLocalFile(String dsUser, String dsPassword, String path, String documentName, String contentType,
			File file, ArrayList<String> serviceUrls) throws Exception {

			// System.out.println("PATH in Upload DMS:" +path);
			Session session = AlfrescoBaseCMIS.createSession(dsUser, dsPassword, serviceUrls);
			try {
				String mimeType = contentType;
				// logger.debug("mimetype: " + mimeType);
				// logger.debug("file: " + content.getAbsolutePath());
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(PropertyIds.OBJECT_TYPE_ID, BaseTypeId.CMIS_DOCUMENT.value());
				properties.put(PropertyIds.NAME, documentName);

				// properties.put(PropertyIds.OBJECT_TYPE_ID,
				// "cmis:document,P:cm:titled");
				// properties.put("cm:title",documentName);
				// properties.put("cm:description", "My document");

				 byte[] bytes  = getBytesFromFile(file);
				ByteArrayInputStream input = new ByteArrayInputStream(bytes);
				ContentStream cs = session.getObjectFactory().createContentStream(documentName, bytes.length, mimeType,
					input);
				Folder folder = (Folder) session.getObjectByPath(path);
				// Document doc = folder.createDocument(properties, cs,
				// VersioningState.MAJOR);
				org.apache.chemistry.opencmis.client.api.Document doc = folder.createDocument(properties, cs, VersioningState.MAJOR);
				String docId = doc.getId();
				//System.out.println("Doc ID:" + docId);

			}
			catch (CmisBaseException e) {
				// System.err.println("Exce:" +e.toString());
				e.printStackTrace();
				// logger.error("error uploading file: "+ e.getMessage(), e);
			}
		}


		// Returns the contents of the file in a byte array.
	    public static byte[] getBytesFromFile(File file) throws IOException {        
	        // Get the size of the file
	        long length = file.length();

	        // You cannot create an array using a long type.
	        // It needs to be an int type.
	        // Before converting to an int type, check
	        // to ensure that file is not larger than Integer.MAX_VALUE.
	        if (length > Integer.MAX_VALUE) {
	            // File is too large
	            throw new IOException("File is too large!");
	        }

	        // Create the byte array to hold the data
	        byte[] bytes = new byte[(int)length];

	        // Read in the bytes
	        int offset = 0;
	        int numRead = 0;

	        InputStream is = new FileInputStream(file);
	        try {
	            while (offset < bytes.length
	                   && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	                offset += numRead;
	            }
	        } finally {
	            is.close();
	        }

	        // Ensure all the bytes have been read in
	        if (offset < bytes.length) {
	            throw new IOException("Could not completely read file "+file.getName());
	        }
	        return bytes;
	    }

}



