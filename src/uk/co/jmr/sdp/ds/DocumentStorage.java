package uk.co.jmr.sdp.ds;

import java.io.File;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.alfresco.webservice.authentication.AuthenticationFault;
import org.alfresco.webservice.repository.RepositoryFault;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import uk.co.jmr.sdp.domain.ds.Document;

public interface DocumentStorage {

	// InputStream download(String dsUser,String dsPassword,String path,String
	// documentName) throws Exception;
	InputStream download(String dsUser, String dsPassword, String path, String documentName, ArrayList<String> serviceUrls)
		throws Exception;

	// InputStream downloadTemplate(String dsUser,String dsPassword,String path)
	// throws Exception;
	InputStream downloadTemplate(String dsUser, String dsPassword, String path, ArrayList<String> serviceUrls) throws Exception;

	List<Object> checkOut(String dsUser, String dsPassword, String path, String Documentname, ArrayList<String> serviceUrls)
		throws Exception;

	String checkIn(String dsUser, String dsPassword, String path, String fileName, CommonsMultipartFile file,
		ArrayList<String> serviceUrls) throws Exception;

	List<Document> getDocuments(String dsUser, String dsPassword, String path) throws RepositoryFault, RemoteException;

	List<Document> getDocuments(String dsUser, String dsPassword) throws RepositoryFault, RemoteException;

	HashMap<String, String> getMetaData(String dsUser, String dsPassword, String path, String documentName)
		throws ServiceException, RemoteException, RepositoryFault;

	void createContent(String dsUser, String dsPassword, String path, String documentName, String contentType, byte[] bytes)
		throws Exception;

	public void copy(String dsUser, String dsPassword, String inputPath, String outputPath, String documentName,
		String outputFileName, ArrayList<String> serviceUrls) throws Exception;

	public void undoCheckOut(String dsUser, String dsPassword, String path, String documentName, ArrayList<String> serviceUrls)
		throws Exception;

	public void update(String dsUser, String dsPassword, String path, String documentName, String newDocName,
		ArrayList<String> serviceUrls) throws Exception;

	// For Testing Large File Upload

	public void createContent(String dsUser, String dsPassword, String path, String documentName, String contentType,
		CommonsMultipartFile file, ArrayList<String> serviceUrls) throws Exception;

	//To Upload Files into alfresco from local directory
	public void createContentLocalFile(String dsUser, String dsPassword, String path, String documentName, String contentType,
			File file, ArrayList<String> serviceUrls) throws Exception;
	// public void createContent(String dsUser, String dsPassword, String path,
	// String documentName, String contentType, CommonsMultipartFile file,
	// String restserviceUrl) throws Exception;

}
