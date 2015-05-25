<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="resources/style/jquery.multiselect.css" />
<script type="text/javascript" src="resources/js/jquery.multiselect.js"></script>
<script type="text/javascript" src="resources/js/angular.js"></script>



<script type="text/javascript">


$(document).ready(function() {
	$("h4").next("div").not(".hideFalse").prev().addClass("accordBack");
	$("h4").click(function(){
		
		if($(this).find("span").hasClass("glyphicon-triangle-bottom")){
			$(this).addClass('accordBack');
			$(this).find("span.glyphicon-triangle-bottom").addClass("glyphicon-triangle-right").removeClass("glyphicon-triangle-bottom");
		}
		else if($(this).find("span").hasClass("glyphicon-triangle-right")){
			$(this).removeClass('accordBack');
			$(this).find("span.glyphicon-triangle-right").toggleClass("glyphicon-triangle-bottom");
		}
		
	})
	
});

function openUploadForm(id){
	
	var docName=document.getElementById(id).value;
	 
					
					var url = "formsNew/openForUpload?itemId="+id+"&docName="+docName;
					//alert(url);
					 window.open(url,"Document Upload",'height=600,width=1000,left=100,top=50,resizable=yes,location=no,scrollbars=yes,toolbar=yes,status=yes'); 
					 //tb_show('Update Links', url, null); 
				
		
}

function setValue(id,val){
	//alert(val+""+id);
	document.getElementById(id).value=val;
	
}

function openDoc(id){
	//alert(id);
	var docName=document.getElementById(id).value;
	//alert(docName);
	if(docName!=""){
		
	var url="formsNew/openDoc?docName=" + docName;

		var win=window.open(url,"Document Upload",'');
		setTimeout(function(){ win.close()}, 3000);
	}
	else{
		
		dialogTemplate("Document", "No item");
	}

}

function removeElement(id){
	var documentName=document.getElementById(id).value;
	
	//alert(documentName);
	$.ajax({
		type : "GET",
		url : "formsNew/removeTempDoc?docName=" + documentName,
		cache : false,
		success : function(data) {
			document.getElementById(id).value="";
		}
	});
	
}


$("#addPic2").click(function(){
	  $("#pic2").show();
	});
	
$("#removePic2").click(function(){
	  $("#pic2").hide();
	  removeElement('picture2');
	});
	
$("#addPic3").click(function(){
	  $("#pic3").show();
	});
	
$("#removePic3").click(function(){
	  $("#pic3").hide();
	  removeElement('picture3');
	});
	
$("#addPic4").click(function(){
	  $("#pic4").show();
	});
	
$("#removePic4").click(function(){
	  $("#pic4").hide();
	  removeElement('picture4');
	});
	
$("#addPic5").click(function(){
	  $("#pic5").show();
	  
	});
	
$("#removePic5").click(function(){
	  $("#pic5").hide();
	  removeElement('picture5');
	});

$(document).ready(function(){
	var pic2="${picture2 }";
	var pic3="${picture3}";
	var pic4="${picture4}";
	var pic5="${picture5}";
	
	if(pic2!=""){
		$("#pic2").show();
	}else{
		$("#pic2").hide();
	}
	if(pic3!=""){
		$("#pic3").show();
	}
	else{
		$("#pic3").hide();
	}
	if(pic4!=""){
		$("#pic4").show();
	}else{
		$("#pic4").hide();
	}
	if(pic5!=""){
		$("#pic5").show();
	}else{
		$("#pic5").hide();
	}
	
	
	
	
	
	
	
});



$(function(){
	var alternate=false;
	$('.form2 h4').click(function(){
		if(alternate==true)
			{
			$('.form2 > div').hide();
			$(this).next('div').show(300);
			}		
		else		
		$(this).next('div').toggle(300);
	});
	
});

$(document).ready(function() {
	$("#submissionDate").val($.datepicker.formatDate("dd-mm-yy", new Date()));
	$("#productType option[value='${productType}']").attr('selected', 'selected');
	
	$("#secGrp option[value='${productType}']").attr('selected', 'selected');
	$("#compGrp option[value='${companyGrp}']").attr('selected', 'selected');
	$("#wrkFlw option[value='${modelId}']").attr('selected', 'selected');
	});


function loadProgress() {
	//alert("comes in load progress");
	var ProgressDialog = $("<div id='ProgressBarDialog' title='Please wait...' align='center' style='padding-top: 6px'><img align='middle' src='resources/images/progress_bar.gif' /></div>");
	ProgressDialog.dialog({
		height : 125,
		width : 254,
		modal : true,
		closeOnEscape : false,
		resizable : false,
		draggable : false
	});
}

function removeProgress() {
	//alert("comes in");
	$('#ProgressBarDialog').dialog('destroy').remove();
}

function saveForm(){
	loadProgress();
	$("#formDatas").attr("action", "formsNew/saveStandardProductForDraft");
	$("#formDatas").submit();
	
	//loadAllDocuments();
	//$("#trayDiv").html(data);
	//removeProgress();
	
}

function submitForm(){
	loadProgress();
	$("#formDatas").attr("action", "formsNew/createStandardProductForDraft");
	$("#formDatas").submit();
	
	//loadAllDocuments();
	//$("#trayDiv").html(data);
	//removeProgress();
	
}

$(function() {
	
	$(".datepicker").datepicker({
		dateFormat : 'dd-mm-yy' 
	});
});


// var jsonString=${jsonStringValue};
</script>
 

<span class="grid-box-header" id="header2title">Form Upload <font class="mand">* Mandatory fields</font>
	<a href='#' class='close-widget-box'><img
		src="resources/images/wizart/x.png" class="float-right" /></a>
</span>


<form method="post" id="formDatas">
		<div id="at2" class="form2">
		<input type="hidden" value="${userFormId }" id="userFormId" name="userFormId"/>
		<input type="hidden" value="${formDefId }" id="formDefId" name="formDefId"/>
		
		
		<h4><span class="glyphicon glyphicon-triangle-bottom"></span>  Standard Product Form</h4>
			<div class="hideFalse">
			<section>
		
		<!-- <p align="center">
				<b><font size="3" >Standard Product Form</font></b>
			</p> -->
			<p>
				<label class="FormFieldLabelRequired" for="productName" title="Product Name">Product Name:</label>
				<input class="text" id="productName" name="productName" value="${productName}" type="text" maxlength="50"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="productType" title="Product Type">Product Type:</label>
				<!-- <input class="text searchtext" id="f_7_2" name="f_7_2" readonly="readonly" type="text" onclick="frmFindItemAjax('f_7_2');"/> -->
				<select class="text" id="productType" name="productType">
				<option value="-1">Select</option> 
				<option value="Earrings">Earrings</option>
				<option value="Necklace">Necklace</option>
				<option value="Ring">Ring</option>
				</select>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="productCode" title="Product Code">Product Code:</label>
				<input class="text" id="productCode" name="productCode" value="${productCode}" type="text" maxlength="10"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="productDescription" title="Product Description">Product Description:</label>
				<textarea class="text" cols="43" id="productDescription"  name="productDescription" rows="5">${productDescription} </textarea>
			</p>
			
			
			
			<p>
				<label class="FormFieldLabelRequired" for="sellingPricePerUnit" title="Selling Price per Unit">Selling Price per Unit:</label>
				<input class="text" id="sellingPricePerUnit" value="${sellingPricePerUnit}" name="sellingPricePerUnit" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="picture1" title="Picture">Picture:</label>
				<input class="text searchtext" id="picture1" value="${picture1 }" name="picture1" readonly="readonly" type="text" onclick="openUploadForm('picture1');"/>
				<input class="openFile" type="button"  onclick="openDoc('picture1');">
				<input class="clearFile" type="button"   onclick="removeElement('picture1');">
				<br/>
				<input type="button"  class="addPic" id="addPic2" name="addPic2" style="width: 22px; height: 22px; border: 0px;"/>
			</p>
			
			<div id="pic2">
			<p>
				<label class="FormFieldLabelRequired" for="picture2" title="Picture">Picture:</label>
				<input class="text" id="picture2" name="picture2" value="${picture2 }" readonly="readonly" type="text" onclick="openUploadForm('picture2');"/>
				<input class="openFile" type="button"  onclick="openDoc('picture2');">
				<input class="clearFile" type="button"  onclick="removeElement('picture2');">
				<br/>
				<input type="button"  class="addPic" id="addPic3" name="addPic3" style="width: 22px; height: 22px; border: 0px;"/>
				<input type="button"  id="removePic2" class="removePic" name="RemovePic2" style="width: 22px; height: 22px; border: 0px;"/>
			</p>
			</div>
			</section>
			<section>
			
			
			<div id="pic3">
			<p>
				<label class="FormFieldLabelRequired" for="picture3" title="Picture">Picture:</label>
				<input class="text" id="picture3" name="picture3" value="${picture3 }" readonly="readonly" type="text" onclick="openUploadForm('picture3');"/>
				<input class="openFile" type="button"  onclick="openDoc('picture3');">
				<input class="clearFile" type="button"  onclick="removeElement('picture3');">
				<br/>
				<input type="button"  class="addPic" id="addPic4" name="addPic4" style="width: 22px; height: 22px; border: 0px;"/>
				<input type="button" class="removePic" id="removePic3" name="RemovePic3" style="width: 22px; height: 22px; border: 0px;"/>
			</p>
			</div>
			<div id="pic4">
			<p>
				<label class="FormFieldLabelRequired" for="picture4" title="Picture">Picture:</label>
				<input class="text" id="picture4" name="picture4" value="${picture4 }" readonly="readonly" type="text" onclick="openUploadForm('picture4');"/>
				<input class="openFile" type="button"  onclick="openDoc('picture4');">
				<input class="clearFile" type="button"  onclick="removeElement('picture4');">
				<br/>
				<input type="button"  class="addPic" id="addPic5" name="addPic5" style="width: 22px; height: 22px; border: 0px;"/>
				<input type="button" class="removePic" id="removePic4" name="RemovePic4" style="width: 22px; height: 22px; border: 0px;"/>
			</p>
			</div>
			<div id="pic5">
			<p>
				<label class="FormFieldLabelRequired" for="picture5" title="Picture">Picture:</label>
				<input class="text" id="picture5" name="picture5" value="${picture5 }" readonly="readonly" type="text" onclick="openUploadForm('picture5');"/>
				<input class="openFile" type="button"  onclick="openDoc('picture5');">
				<input class="clearFile" type="button" class="download" onclick="removeElement('picture5');">
				<br/>
				<input type="button"  class="removePic" id="removePic5" name="RemovePic5" style="width: 22px; height: 22px; border: 0px;"/>
			</p>
			</div>
			<p>
				<label class="FormFieldLabelRequired" for="stockLevels" title="Stock Levels">Stock Levels:</label>				
				<input class="text" id="stockLevels" name="stockLevels" value="${stockLevels }" type="text"/>
			</p>
			
			<%-- <p>
				<label class="FormFieldLabelRequired" for="agentDistributionOfStock" title="Agent Distribution of Stock">Agent Distribution of Stock:</label>
				<input class="text" id="agentDistributionOfStock" name="agentDistributionOfStock" value="${agentDistributionOfStock }" type="text"/>
			</p> --%>
			
			<p>
				<label class="FormFieldLabelRequired" for="stockLevelNewSupplierOrderAlert" title="Stock Level New Supplier Order Alert">Stock Level New Supplier Order Alert:</label>
				<input class="text" id="stockLevelNewSupplierOrderAlert" name="stockLevelNewSupplierOrderAlert" value="${stockLevelNewSupplierOrderAlert }" type="text"/>
			</p>
			
			
			
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
		<p style="display: none;" align="left" id="comp">
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
		<p style="display: none;" align="left" id="workflow">
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
			
			<c:choose>
						<c:when test="${subDate == true}">
			
			<p align="left">
				<label class="FormFieldLabelRequired" for="submissionDate" title="submissionDate">Submission Date:</label>
				<input class="text datepicker" id="submissionDate" name="submissionDate" value="${submissionDate }" readonly="readonly" format="dd-mm-yyyy" type="text" />
			</p>
			</c:when>
			<c:otherwise>
			<p style="display: none;" align="left">
				<label class="FormFieldLabelRequired" for="submissionDate" title="submissionDate">Submission Date:</label>
				<input class="text datepicker" id="submissionDate" name="submissionDate"  readonly="readonly" format="dd-mm-yyyy" type="text" />
			</p>
			</c:otherwise>
			</c:choose>
			</section>
			</div>
		</div>
</form>
		<div id="saveSubmit" align="center">
				<span><input
					type="button" class="download" id="cancelFormAttr"
					value="Cancel" onclick="homeClicked();"></span>
					<span><input
					type="submit" class="download" id="saveFormAttr"
					value="Save" onclick="saveForm();"></span>
					<span><input
					type="submit" class="download" id="submitSaveAttr"
					value="Submit" onclick="saveForm();"></span>
				</div>
				
		<!-- <div id="attr3Div"></div> -->
	
	
	
	

