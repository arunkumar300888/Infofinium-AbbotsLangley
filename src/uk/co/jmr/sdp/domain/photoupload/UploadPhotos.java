package uk.co.jmr.sdp.domain.photoupload;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.activation.MimetypesFileTypeMap;

import org.alfresco.webservice.authentication.AuthenticationFault;
import org.alfresco.webservice.content.ContentFault;
import org.alfresco.webservice.repository.RepositoryFault;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import uk.co.jmr.sdp.ds.DocumentStorage;
import uk.co.jmr.sdp.web.util.Util;

public class UploadPhotos {

	private String filePathString;

	private String dsUser;

	private String dsPassword;

	private CommonsMultipartFile file;
	
	private File file1;

	public UploadPhotos(String filePathString, String dsUser, String dsPassword, CommonsMultipartFile file) {

		super();
		this.filePathString = filePathString;
		this.dsUser = dsUser;
		this.dsPassword = dsPassword;
		this.file = file;
	}
	public UploadPhotos(String filePathString, String dsUser, String dsPassword, File file) {

		super();
		this.filePathString = filePathString;
		this.dsUser = dsUser;
		this.dsPassword = dsPassword;
		this.file1 = file;
	}

	public void upload(DocumentStorage documentStorage, String folderName) throws RemoteException {

		try {

			documentStorage.createContent(dsUser, dsPassword, filePathString + Util.encode(folderName),
				file.getOriginalFilename(), file.getContentType(), file.getBytes());
		}
		catch (Exception e) {

			throw new RemoteException(
				"This error may Occur in following reasons.Please check userName and password combination or FileName already exists or Uploaded content file may be  damaged or RepositoryFault");

		}

	}

	// public void upload(DocumentStorage documentStorage, String folderName,
	// String fileName)
	// throws RemoteException {
	// public void upload(DocumentStorage documentStorage, String folderName,
	// String fileName,String restServiceUrl)
	public void upload(DocumentStorage documentStorage, String folderName, String fileName, ArrayList<String> serviceUrls)
		throws RemoteException {

		try {

			// documentStorage.createContent(dsUser, dsPassword, filePathString
			// + Util.encode(folderName), fileName,
			// file.getContentType(), file.getBytes());

			// documentStorage.createContent(dsUser, dsPassword, filePathString+
			// Util.encode(folderName), fileName,
			// file.getContentType(), file, restServiceUrl);

			documentStorage.createContent(dsUser, dsPassword, filePathString.concat(folderName), fileName, file.getContentType(),
				file, serviceUrls);
		}
		catch (Exception e) {

			throw new RemoteException(
				"This error may Occur in following reasons.Please check userName and password combination or FileName already exists or Uploaded content file may be  damaged or RepositoryFault");

		}

	}
	
	//To Upload Files into alfresco from local directory
	// public void uploadLocalFile(DocumentStorage documentStorage, String folderName,
		// String fileName)
		// throws RemoteException {
		// public void upload(DocumentStorage documentStorage, String folderName,
		// String fileName,String restServiceUrl)
		public void uploadLocalFile(DocumentStorage documentStorage, String folderName, String fileName, ArrayList<String> serviceUrls)
			throws RemoteException {

			try {

				// documentStorage.createContent(dsUser, dsPassword, filePathString
				// + Util.encode(folderName), fileName,
				// file.getContentType(), file.getBytes());

				// documentStorage.createContent(dsUser, dsPassword, filePathString+
				// Util.encode(folderName), fileName,
				// file.getContentType(), file, restServiceUrl);

				documentStorage.createContentLocalFile(dsUser, dsPassword, filePathString.concat(folderName), fileName, new MimetypesFileTypeMap().getContentType( file1),
					file1, serviceUrls);
			}
			catch (Exception e) {

				throw new RemoteException(
					"This error may Occur in following reasons.Please check userName and password combination or FileName already exists or Uploaded content file may be  damaged or RepositoryFault");

			}

		}


}
