package uk.co.jmr.sdp.ds;

import java.rmi.RemoteException;

import org.alfresco.webservice.repository.RepositoryFault;

import uk.co.jmr.sdp.domain.ds.DMNode;

public interface WorkspaceSpider {

	DMNode fetchRoot(String dsUser, String dsPassword) throws RepositoryFault, RemoteException;
}
