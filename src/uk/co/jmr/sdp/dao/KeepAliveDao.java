package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.KeepAlive;

public interface KeepAliveDao {
	
	List<KeepAlive> findAllKeepAlive();
	KeepAlive findKeepAliveMessageById(long id);
	void save(KeepAlive keepAlive);
	void delete(KeepAlive keepAlive); 
	void deleteAll(List<KeepAlive> keepAlive);
}
