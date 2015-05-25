package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.KeepAliveDao;
import uk.co.jmr.sdp.domain.KeepAlive;
import uk.co.jmr.sdp.service.KeepAliveService;

@Service("keepAliveService")
public class KeepAliveServiceImpl implements KeepAliveService{

	@Autowired
	private KeepAliveDao keepAliveDao;

	@Override
	public List<KeepAlive> findAllKeepAlive() {
		// TODO Auto-generated method stub
		return keepAliveDao.findAllKeepAlive();
	}

	@Override
	public KeepAlive findKeepAliveMessageById(long id) {
		// TODO Auto-generated method stub
		return keepAliveDao.findKeepAliveMessageById(id);
	}

	@Override
	public void save(KeepAlive keepAlive) {
		// TODO Auto-generated method stub
		keepAliveDao.save(keepAlive);
	}

	@Override
	public void delete(KeepAlive keepAlive) {
		// TODO Auto-generated method stub
		keepAliveDao.delete(keepAlive);
	}

	@Override
	public void deleteAll(List<KeepAlive> keepAlive) {
		// TODO Auto-generated method stub
		keepAliveDao.deleteAll(keepAlive);
	}
	
	
	
}
