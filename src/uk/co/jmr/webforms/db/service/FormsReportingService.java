package uk.co.jmr.webforms.db.service;

import com.ardhika.wfar.WfCase;
import java.util.List;
import java.util.Set;

import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.web.util.DocListInput;

public interface FormsReportingService {
	public List<WfCase> search(DocListInput docListInput,String securityGroup);
	public List<WfCase> searchForms(DocListInput docListInput,Set<SecurityGroup> securityGroup);
}
