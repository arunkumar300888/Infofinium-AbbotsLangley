package uk.co.jmr.sdp.service;

import java.util.List;
import java.util.Map;

import uk.co.jmr.sdp.domain.forms.FormDefinition;
import uk.co.jmr.sdp.domain.forms.FormFieldValues;
import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.pojo.UserForms;

public interface FormService {
	List<FormDefinition> formDefinitions();

	Forms fetchFormDefinition(long formDefinitionId, long userFormId);

	long getFormDefinitionId(long userFormId);

	int fetchDraftFormCount(long userId);

	long saveFormData(long userId, Map<String, String[]> formData);

	void updateUserFormStatus(long userFormId, char status);

	void removeUserForm(long userFormId);

	List<FormFieldValues> getUserFormData(long formId, long userFormId);

	List<UserForms> getFromsByStatus(long userId, char status);

	String createHtmlRepresentation(UserForms userForm);
	
	String createHtmlRepresentationForPrint(UserForms userForm);
	
	String createHtmlRepresentationForPdfDownload(UserForms userForm);

	List<String> getDialogData(String element);

	Map<String, Object> getFormMetaData(long userFormId);

	void processWorkflowEvent(long userFormId, String author, String event);

	UserForms cloneUserForm(UserForms userForm, long userId);

	UserForms cloneUserForm(long userFormId, long userId);
	
	Forms findFormsByName(String name);
	String createHtmlRepresentationforRentPaymentTransactionHistorybyUserID(long userId);
	String createHtmlRepresentationforViewGivingNoticeForm(long userId);
	String createHtmlRepresentationforRentReceiptbyUserID(long userId);
}
