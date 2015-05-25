<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		<script type="text/javascript">
//<![CDATA[
/* $(function() {
				$('.scroll-pane, .box-content').jScrollPane(
					{
						hijackInternalLinks: true
					}
				);
			}); */
			
			$(function() {
				
				$(".datepicker").datepicker({
					dateFormat : 'dd-mm-yy' 
				});
			});

var model=${modelId};
var securityGrp=${securityGrp};		
var companyGrp=${companyGrp};
var formId = ${formId};
//alert(formId);
var userFormData = ${userFormData};

	//]]>
		</script>

		<script type="text/javascript">
//<![CDATA[

			var formIsValid = true;
			var _formDef;
			var current_tab = 0;

			function _form_init(ft)
			{
				//alert(ft);
				var i;
				var j;
				var k;
				var f;

				_formDef = ft;
				for(i=0; i< _formDef.length; i++)
				{
					for(j=0; j<_formDef[i].fields.length; j++)
					{
						f = _formDef[i].fields[j];
						//alert(f.id);
						fHtmlObject = document.getElementById(f.id);
						fHtmlObject.jmrDomExt = new Object();
						fHtmlObject.jmrDomExt.field = f;
						fHtmlObject.jmrDomExt.extraData = new Object();

						if(f.ownedBy != null)
							fHtmlObject.jmrDomExt.ownedBy = document.getElementById(f.ownedBy);
						else
							fHtmlObject.jmrDomExt.ownedBy = null;

						fHtmlObject.jmrDomExt.mask = 1 << (j%32);
						fHtmlObject.jmrDomExt.maskIndex = Math.round((j/32) - 0.5);
						if(f.isRequired)
						{
							_formDef[i].rmask[fHtmlObject.jmrDomExt.maskIndex] |= fHtmlObject.jmrDomExt.mask;
						}

						var ud = eval("userFormData." + f.name);
						if(fHtmlObject.type == "select-one" || fHtmlObject.type == "select-multiple")
						{
							fHtmlObject.length = 0;
							if(ud == null)
								continue;

							for(k=0; k<ud.length; k++)
							{
								try
								{
									var s = ud[k].split("[#")[0].trim().replace(/\s+/g, "_");
									var option = new Option(ud[k], ud[k]);
									fHtmlObject.jmrDomExt.extraData[s] = option;
									fHtmlObject.options[k] = option;
								}
								catch(ex)
								{
									alert(ex);
								}
							}
						}
						else if(fHtmlObject.type == "checkbox" || fHtmlObject.type == "radio")
						{
							if(ud != null)
							{
								if(fHtmlObject.value == ud[0])
								{
									if(!fHtmlObject.checked)
										fHtmlObject.click();

									continue;
								}
							}

							if(fHtmlObject.checked)
							{
								fHtmlObject.click();
							}
						}
						else  if(fHtmlObject.type == "textarea" || fHtmlObject.type == "text" || fHtmlObject.type == "password")
						{
							
							if(ud != null)
							{
								fHtmlObject.value = ud[0];
							}
							else
							{
								fHtmlObject.value = "";
							}

							if(f.isRequired)
							{
								_formDef[i].vmask[fHtmlObject.jmrDomExt.maskIndex] |= fHtmlObject.jmrDomExt.mask;
								fHtmlObject.onkeyup=_formOnKeyUp;
								fHtmlObject.validateField = validateInputField;

							}
						}

						if(f.isRequired)
						{
							fHtmlObject.validateField(fHtmlObject);
						}
					}
				}

				 if(securityGrp != null){
					var c = document.getElementById("secGrp");
					
					for (var k = 0; k < c.options.length; k++) 
					{	
						try
						{
						        if (c.options[k].value == securityGrp){
						            c.options[k].selected = true;
						            break;
						        }
						}
						catch(ex)
						{
							alert(ex);
						}
					}
				}
				 
				 if(companyGrp != null){
						var com = document.getElementById("compGrp");
						 
						for (var k = 0; k < com.options.length; k++) 
						{	
							try
							{
							        if (com.options[k].value == companyGrp){
							            com.options[k].selected = true;
							            break;
							        }
							}
							catch(ex)
							{
								alert(ex);
							}
						}
					}
				
				 if(model != null){
						var mod = document.getElementById("wrkFlw");
						 
						for (var k = 0; k < mod.options.length; k++) 
						{	
							try
							{
							        if (mod.options[k].value == model){
							            mod.options[k].selected = true;
							            break;
							        }
							}
							catch(ex)
							{
								alert(ex);
							}
						}
					}
				 
				validateForm();
			}

			function enableButton(button, enabled)
			{
				try
				{
					var c = document.getElementById(button);
					c.disabled = !enabled;
					if(enabled)
						c.className = "butn";
					else
						c.className = "butnDisabled";
				}
				catch(e)
				{
				}
			}

			function validateForm()
			{
				var i, k;
				var result = true;
				for(i=0; i< _formDef.length; i++)
				{
					for(k=0; k < _formDef[i].vmask.length; k++)
					{
						if((_formDef[i].rmask[k] ^ _formDef[i].vmask[k]) != 0)
						{
							result = false;
						}
					}
				}

				if(!_formValidationCheck())
					result = false;

				formIsValid = result;
				enableButton("FORM_BTN_UPDATE", formIsValid);
				enableButton("FORM_BTN_SUBMIT", formIsValid);
				enableButton("FORM_BTN_OK", formIsValid);
			}

			function _formOnKeyUp(event)
			{
				event.target.validateField(event.target);
			}

			function validateInputField(fHtmlObject)
			{
				
				if(doValidate(fHtmlObject, fHtmlObject.jmrDomExt.field.type))
				{
					_formDef[current_tab].vmask[fHtmlObject.jmrDomExt.maskIndex] |= fHtmlObject.jmrDomExt.mask;
					markFieldLabelValid(fHtmlObject, true);
				}
				else
				{
					_formDef[current_tab].vmask[fHtmlObject.jmrDomExt.maskIndex] &= (0xffffffff ^ fHtmlObject.jmrDomExt.mask);
					markFieldLabelValid(fHtmlObject, false);
				}

				validateForm();
			}

			function frmRbChanged(rb)
			{
				alert(rb.value);
			}

			function frmCbChanged(cb)
			{
				try
				{
					var i;
					var j = _formDef[current_tab];
					for(i=0; i < j.fields.length; i++)
					{
						var f = j.fields[i];
						if(f.ownedBy != null && f.ownedBy == cb.name)
						{
							var cResource = document.getElementById(f.name);
							if(cResource == null)
								return;

							if(cResource.tagName != "DIV")
							{
								cResource = cResource.parentNode;
							}

							if(cb.checked)
							{
								cResource.style.display = "block";
							}
							else
							{
								cResource.style.display = "none";
							}
						}
					}
				}
				catch(e)
				{
					alert(e);
				}
			}

			function ajaxCallDone(control, data)
			{
				openPopupDialog(control, data, null);
			}

			function frmLbDeleteItem(ctrl)
			{
				ctrl = document.getElementById(ctrl);
				if(ctrl.type == "select-one")
					ctrl.remove(ctrl.selectedIndex);
				else
				{
					var i;
					for(i=ctrl.options.length-1; i>=0; i--)
					{
						if(ctrl.options[i].selected)
						{
							ctrl.remove(i);
						}
					}	
				}
			}

			function frmLbAddItem(ctrl)
			{
				ctrl = document.getElementById(ctrl);
				$.ajax({
					type : "GET",
					url : "forms/ajaxDataRequest?requester=" + ctrl.name,
					cache : false,
					success : function(data) {
						ajaxCallDone(ctrl, data);
					}
				});
			}

			function frmFindItemAjax(ctrl)
			{
				ctrl = document.getElementById(ctrl);
				$.ajax({
					type : "GET",
					url : "forms/ajaxDataRequest?requester=" + ctrl.name,
					cache : false,
					success : function(data) {
						ajaxCallDone(ctrl, data);
					}
				});
			}

			function frmCalendarWidget(ctrl)
			{
				ctrl = document.getElementById(ctrl);
				$.ajax({
					type : "GET",
					url : "forms/ajaxDataRequest?requester=" + ctrl.name,
					cache : false,
					success : function(data) {
						ajaxCallDone(ctrl, data);
					}
				});
			}

			function isControlEnabled(control)
			{
				if(control.type == "checkbox")
				{
					if(!control.checked)
						return false;
				}
				else
				{
					if(control.style.display == "none" )
						return false;
				}

				if(control.jmrDomExt.ownedBy == null)
					return true;
				else
					return isControlEnabled(control.jmrDomExt.ownedBy);
			}

			function prepareFormData(frm)
			{
				var frmData="";
				var i;
				var j;
				var s;

				for(i=0; i<frm.elements.length; i++)
				{
					var item = frm.elements[i];
					//alert(item.id);
					if(item.id.charAt(0) == 'f' )
					{
						if(item.type == null || item.type == "div")
							continue;

						if(item.jmrDomExt.ownedBy != null)
						{
							if(!isControlEnabled(item.jmrDomExt.ownedBy))
								continue;
						}

						if(item.type == "radio")
						{
							if(item.checked)
							{
								frmData = frmData + "&" + item.name + "=" + encodeURIComponent(item.value);
							}
						}
						else if(item.type == "checkbox")
						{
							if(item.checked)
							{
								frmData = frmData + "&" + item.name + "=" + encodeURIComponent(item.value);
							}
						}
						else if(item.type == "text" || item.type == "password")
						{
							s = trim(item.value);
							if(s != null && s.length > 0)
							{
								frmData = frmData + "&" + item.name + "=" + encodeURIComponent(s);
							}
						}
						else if(item.type == "textarea")
						{
							s = trim(item.value);
							if(s != null && s.length > 0)
							{
								frmData = frmData + "&" + item.name + "=" + encodeURIComponent(s);
							}

						}
						else if(item.type == "select-one" || item.type == "select-multiple")
						{
							for(j=0; j<item.options.length; j++)
							{
								s = trim(item.options[j].value);
								if(s != null && s.length > 0)
								{
									frmData = frmData + "&" + item.name + "=" + encodeURIComponent(s);
								}
							}
						}
					}
				}

				if(frmData.length > 1)
				{
					frmData = "_formId=" + formId + "&_userFormId=" + userFormData.userFormId + frmData;
					//alert(frmData);

				}
				else
					frmData = "";
				return frmData;
			}

			function frmDataCancel(btn)
			{
				formDataCancel();
			}

			function frmCancel(btn)
			{
				showAllWipDocs();
			}
			
			

			function getWorkFlowType(btw){
				var NewDialog = $("<div id='MenuDialog' title='Document Comments'>\<p>\<span class='ui-icon ui-icon-info' "
						+ "style='float: left; margin: 0 7px 20px 0;'> "
						+ "</span><b>Do you want the Form to follow the Workflow?</b></p>\</div>");
				NewDialog.dialog({
					resizable : false,
					width : 300,
					height : 150,
					modal : true,
					buttons : {
						"Yes" : {
							text : 'Yes',
							click : function() {
								$(this).dialog("close");
								frmDataSubmit(btw, "Yes");
							}
						},
						"No" : {
							text : 'No',
							click : function() {
								$(this).dialog("close");
								frmDataSubmit(btw, "No");
							}
						}
					}
				});
			}
			
			function frmDataSubmit(btn,workFlow)
			{
				var wf = document.getElementById("wrkFlw").value;
				var sec = document.getElementById("secGrp").value;
				var com = document.getElementById("compGrp").value;
				var submissionDate = document.getElementById("submissionDate").value;
				if( sec !="-1"){
					if(formIsValid)
					{
						/* if (typeof _formCalculation == 'function') { 
							_formCalculation();
						} */
						var frmData = prepareFormData(btn.form);
						formDataSubmit(frmData,sec,workFlow,submissionDate,com,wf);
					}
				}else{
					alert ("Please select the Security Group for the Form.")
				}
			}
			
			function frmDataSave(btn,workFlow)
			{
				
				//alert("save clicked "+formIsValid);
				var wf = document.getElementById("wrkFlw").value;
				var sec = document.getElementById("secGrp").value;
				var submissionDate = document.getElementById("submissionDate").value;
				var com = document.getElementById("compGrp").value;
					if(formIsValid)
					{
						/* if (typeof _formCalculation == 'function') { 
							_formCalculation();
						} */
						var frmData = prepareFormData(btn.form);
						
						//alert(frmData);
						formDataSave(frmData,sec,submissionDate,com,wf);
					}
				
			}

			function frmDataUpdate(btn,workFlow)
			{
				var sec = document.getElementById("secGrp").value;
				
				var submissionDate = document.getElementById("submissionDate").value;
					if(formIsValid)
					{
						/* if (typeof _formCalculation == 'function') { 
							_formCalculation();
						} */
						var frmData = prepareFormData(btn.form);
						formDataUpdate(frmData,sec,submissionDate);
					}
				
			}
			
			
			
			function openUploadForm(id){
				
				var docName=document.getElementById(id).value;
				 
								
								var url = "forms/openForUpload?itemId="+id+"&docName="+docName;
								//alert(url);
								 window.open(url,"Document Upload",'height=600,width=1000,left=100,top=50,resizable=yes,location=no,scrollbars=yes,toolbar=yes,status=yes'); 
								 //tb_show('Update Links', url, null); 
							
					
			}
			
			function setValue(id,val){
				//alert(val+""+id);
				document.getElementById(id).value=val;
				
			}
			
			function removeElement(id){
				var documentName=document.getElementById(id).value;
				
				//alert(documentName);
				$.ajax({
					type : "GET",
					url : "forms/removeTempDoc?docName=" + documentName,
					cache : false,
					success : function(data) {
						document.getElementById(id).value="";
					}
				});
				
			}
			
			function openDoc(id){
				//alert(id);
				var docName=document.getElementById(id).value;
				//alert(docName);
				if(docName!=""){
					
				var url="forms/openDoc?docName=" + docName;
			
					var win=window.open(url,"Document Upload",'');
					setTimeout(function(){ win.close()}, 3000);
				}
				else{
					
					dialogTemplate("Document", "No item");
				}
			
			}
			

//]]>
		</script>

<c:if test="${(submitVisible=='add')}">
	<span class="grid-box-header">Form Definition ${title} <a href='#'
						class='close-widget-box'> <img
							src="resources/style/wizart/images/x.png" class="float-right" />
					</a></span>
</c:if>



<div style="overflow:auto;" class="content box-content">
	<form action="/forms/create" method="post" id="${formName}">
		<div id="FORM_Container">

<%
	try
	{
		String formDefinition = (String) request.getAttribute("formDefinition");
		InputStreamReader isr = new InputStreamReader(getServletContext().getResourceAsStream(formDefinition));
		if(isr != null)
		{
			char[] cb = new char[1024];
			while(true)
			{
				int i = isr.read(cb);
				if(i == -1)
					break;

				out.write(cb, 0, i);
			}

			isr.close();
		}
	}
	catch(Exception e)
	{
	}
%>

			<div id="FORM_ButtonBar">
			
				<c:choose>
						<c:when test="${secGroups.size() == 1}">
						<p align="left">
			<label title="Security Group" class="FormFieldLabelRequired" for="">Select Security</label>
			<select class="text" id="secGrp" name="secGrp">
			<c:forEach var="i" items="${secGroups}">
			<option selected="selected" value="${i.securityGroup.id }">${i.securityGroup.name }</option>
			</c:forEach>
			</select><br/>
			</p>
						</c:when>
						<c:otherwise>
			<p align="left">
			<label title="Security Group" class="FormFieldLabelRequired" for="">Select Security</label>
			<select class="text" id="secGrp" name="secGrp">
			<option value="-1">--Select--</option>
			<c:forEach var="i" items="${secGroups}">
			<option value="${i.securityGroup.id }">${i.securityGroup.name }</option>
			</c:forEach>
			</select><br/>
			</p>
			</c:otherwise>
			</c:choose>
			
			<c:choose>
						<c:when test="${compGroups.size() == 1}">
		<p align="left" id="comp">
			<label title="Company Group" class="FormFieldLabelRequired" for="">Select Company</label>
			<select class="text" id="compGrp" name="compGrp">
			<c:forEach var="i" items="${compGroups}">
			<option selected="selected" value="${i.id }">${i.value }</option>
			</c:forEach>
			</select><br/>
			</p>
		</c:when>
						<c:otherwise>
			<p align="left" id="comp">
			<label title="Company Group" class="FormFieldLabelRequired" for="">Select Company</label>
			<select class="text" id="compGrp" name="compGrp">
			<option value="-1">--Select--</option>
			<c:forEach var="i" items="${compGroups}">
			<option value="${i.id }">${i.value }</option>
			</c:forEach>
			</select><br/>
			</p>
			</c:otherwise>
			</c:choose>
			
			<c:choose>
		<c:when test="${workflows.size() == 1}">
		<p align="left" id="workflow">
			<label title="Company Group" class="FormFieldLabelRequired" for="">Select Workflow</label>
			<select class="text" id="wrkFlw" name="wrkFlw">
			<c:forEach var="i" items="${workflows}">
			<option selected="selected" value="${i.id }">${i.name }</option>
			</c:forEach>
			</select><br/>
			</p>
		</c:when>
						<c:otherwise>
			<p align="left" id="workflow">
			<label title="Company Group" class="FormFieldLabelRequired" for="">Select Workflow</label>
			<select class="text" id="wrkFlw" name="wrkFlw">
			<option value="-1">--Select--</option>
			<c:forEach var="i" items="${workflows}">
			<option value="${i.id }">${i.name }</option>
			</c:forEach>
			</select><br/>
			</p>
			</c:otherwise>
			</c:choose>
			
			<p align="left">
				<label class="FormFieldLabelRequired" for="submissionDate" title="submissionDate">Submission Date:</label>
				<input class="text datepicker" id="submissionDate" name="submissionDate" value="${submissionDate }" readonly="readonly" format="dd-mm-yyyy" type="text" />
			</p>
					<c:if test="${(showCancel == false)}">
					<button class="butn" id="FORM_BTN_CANCEL" type="button" onclick="frmDataCancel(this);">Cancel</button>
			</c:if>
			
			<c:if test="${(showCancel == true)}">
					<button class="butn" id="FORM_BTN_CANCEL" type="button" onclick="frmCancel(this);">Cancel</button>
			</c:if>

					<c:if test="${(submitVisible=='add')}">
						<button class="butn" id="FORM_BTN_OK" type="button" title="Save this form to your drafts" onclick="frmDataSave(this);">Save</button>
						<button class="butn" id="FORM_BTN_SUBMIT" type="button" title="Submit the form for approval." onclick="frmDataSubmit(this);">Submit</button>
						<!-- <button class="butn" id="FORM_BTN_SUBMIT" type="button" title="Submit the form for approval." onclick="getWorkFlowType(this);">Submit</button> -->
						<!-- <button class="butn" id="FORM_BTN_DOWNLOAD" type="button" title="Download the form." onclick="frmDataDownload(this);">Download</button> -->
					</c:if>

					<c:if test="${(submitVisible=='update')}">
						<button class="butn" id="FORM_BTN_UPDATE" title="Save changes." type="button" onclick="frmDataUpdate(this);">Update</button>
						<script>
						/* $('#compGrp').attr("disabled", true); */
						$('#comp').hide();
						</script>
					</c:if>
			</div>
		</div>
	</form>
</div>
