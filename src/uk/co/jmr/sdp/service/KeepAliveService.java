package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.KeepAlive;

public interface KeepAliveService {
	List<KeepAlive> findAllKeepAlive();
	KeepAlive findKeepAliveMessageById(long id);
	void save(KeepAlive keepAlive);
	void delete(KeepAlive keepAlive); 
	void deleteAll(List<KeepAlive> keepAlive);
}
