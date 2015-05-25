/*
 * package uk.co.jmr.sdp.service.impl;
 * 
 * import java.util.ArrayList; import java.util.List;
 * 
 * import org.springframework.stereotype.Service;
 * 
 * import uk.co.jmr.sdp.domain.forms.FormDefinition; import
 * uk.co.jmr.sdp.service.FormService;
 * 
 * @Service("formService") public class FormServiceImpl implements FormService {
 * 
 * @Override public List<FormDefinition> formDefinitions() {
 * List<FormDefinition> formDefinitionsList=new ArrayList<FormDefinition>();
 * FormDefinition formDefinition1=new FormDefinition(1,"School Event Form",
 * "School Event Form", 'Y'); FormDefinition formDefinition2=new
 * FormDefinition(2,"Special Leave Form","Special Leave Form",'Y');
 * formDefinitionsList.add(formDefinition1);
 * formDefinitionsList.add(formDefinition2); return formDefinitionsList; }
 * 
 * @Override public String fetchFormDefinition(long formDefinitionId){ //Put
 * code of generation of html return "html"; //return null; }
 * 
 * @Override public int saveFormData(long formDefinitionId,String formData){
 * //Return Form Data ID return 1; }
 * 
 * @Override public int fetchDraftFormCount(){ int count=1; return count; }
 * 
 * @Override public String fetchDraftData(){ return null; }
 * 
 * @Override public List<?> fetchFormData(long formDataId){ return null; }
 * 
 * @Override public String fetchFormAndData(long formDataId){ return null; } }
 */