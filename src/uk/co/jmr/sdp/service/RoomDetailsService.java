package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.RoomDetails;

public interface RoomDetailsService {
	
	public void save(RoomDetails roomDetails);
	
	public List<RoomDetails> findRoomDetailsByPropertyDetailsFormId(PropertyDetailsForm propertyDetailsForm);
	public RoomDetails findRoomDetailsById(long id);
	RoomDetails findRoomDetailsByPropertyDetailsFormIdAndRoomNo(
			PropertyDetailsForm propertyDetailsForm, String roomNo);
	void delete(List<RoomDetails> roomDetails);

}
