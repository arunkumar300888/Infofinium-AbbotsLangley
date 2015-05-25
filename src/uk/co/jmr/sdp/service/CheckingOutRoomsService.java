package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.CheckingOutForm;
import uk.co.jmr.sdp.domain.CheckingOutRooms;

public interface CheckingOutRoomsService {
	
	public void save(CheckingOutRooms checkingOutRooms);
	
	public void delete(List<CheckingOutRooms> checkingOutRooms);
	
	public List<CheckingOutRooms> findCheckingOutRoomsByCheckingOutFormId(CheckingOutForm checkingOutForm);
	
}
