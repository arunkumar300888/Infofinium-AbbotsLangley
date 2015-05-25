package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.RoomDetails;

public interface RoomDetailsDao {

	public void save(RoomDetails roomDetails);
	
	public void delete(List<RoomDetails> roomDetails);
	
	public List<RoomDetails> findRoomDetailsByPropertyDetailsFormId(PropertyDetailsForm propertyDetailsForm);
	
	public RoomDetails findRoomDetailsById(long id);
	
	public RoomDetails findRoomDetailsByPropertyDetailsFormIdAndRoomNo(
			PropertyDetailsForm propertyDetailsForm, String roomNo);
}
