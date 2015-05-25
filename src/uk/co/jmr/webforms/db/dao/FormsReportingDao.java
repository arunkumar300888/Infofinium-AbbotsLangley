package uk.co.jmr.webforms.db.dao;

import java.util.List;
import java.util.Set;

import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.web.util.DocListInput;

public interface FormsReportingDao {
	List<Long> search(DocListInput docListInput,String securityGroup);
	List<Long> searchForms(DocListInput docListInput,Set<SecurityGroup> securityGroup);
}
