<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="resources/style/jquery.multiselect.css" />
<script type="text/javascript" src="resources/js/jquery.multiselect.js"></script>
<script type="text/javascript" src="/resources/js/jquery.js"></script>
<script type="text/javascript" src="/resources/js/FileReader.js"></script>
<script type="text/javascript" src="/resources/js/jquery.fileupload-ui.js"></script>
<script type="text/javascript" src="/resources/js/jquery.iframe-transport.js"></script>


<script type="text/javascript">

<%
String RoleDetails = "";
ServletContext sc = request.getServletContext();
String test =(String)sc.getAttribute("foldername.localUpload");
System.out.println("The test value is here "+ test);
RoleDetails = (String) request.getAttribute("sbRole");
Object status = request.getAttribute("status");
Object tenancy = request.getAttribute("linkToTenancy");
System.out.println(status);

%>

function saveForm(){
	//alert('save form called');
	loadProgress();
	//alert('after load progress');
	$("#formDatas").attr("action", "forms/createJobDetail");
	$("#formDatas").submit();
	
}

function uploadTestClick(value)
{
	alert("uploadTestClick");
}


function downloadImage(path, fileName){
	//loadProgress();
	var p1 = path;
	//alert("downloadImage");
	console.log(p1);
	//alert(p1);
	var f1 = fileName;
	console.log(f1);
	//alert(f1);

	 var mime;
	 var a;
	
	//alert("did it even get here?");
  
    var loc="D:/";		
	$.ajax({
		type : "GET",
		url : "forms/docDownloadByPathFilnameFormId?fileName="+f1+"&path="+p1,
		cache : false,
		success : function(data,status, statusText, responses, headers) {
			//data.setContentType = 'application/download';

			alert("formsNew/docDownloadByPathFilnameFormId called");
			console.log("status>>"+ status+">>>>>>>");
			console.log("statusText>>"+ statusText+">>>>>>>");
			console.log("responses"+ responses+">>>>>>>");
			//console.log("headers"+ headers+">>>>>>>");
			console.log("data>>>"+ data+">>>>>>>");
			//alert("data"+ data);
			console.log("data length"+ data.length);
			//alert("data length"+ data.length);
			//alert("after save as");
						
		            
		                if (data) {
		                    var iframe = $("<iframe>").attr({
		                        src: "forms/docDownloadByPathFilnameFormId?fileName="+f1+"&path="+p1,
		                        style: "visibility:hidden;display:none"
		                    }).appendTo("#addButton");
		                } else {
		                    alert('Something went wrong');
		                }
		            
		       
			
			var b = new Blob([data]);
			var fr = new FileReader();

			fr.onload = function() {
			    ua = new Uint8Array(fr.result);
			    // This will log "3|226|130|172"
			    //                  E2  82  AC
			    // In UTF-16, it would be only 2 bytes long
			    console.log(fr.result.byteLength + '|' + 
			        ua[0]  + '|' + 
			        ua[1] + '|' + 
			        ua[2] + ''
			    );
			    
			    alert(fr.result.byteLength + '|' + 
				        ua[0]  + '|' + 
				        ua[1] + '|' + 
				        ua[2] + '');
			    
			var dataUri =fr.result,  img = document.createElement("img");
		    img.src = dataUri;
		    document.body.appendChild(img);
			};
			fr.readAsArrayBuffer(b);
			
			
			//var fs = new FileSaver(blob, name);
			
			//var buffer = new ArrayBuffer(512);
			/*
			var u8=new Uint8Array(data);
			var ic=u8.length;
			var bs=[];
			
			while(ic--)
			{
				bs[ic]=String.fromCharCode(u8[ic]);
			};
			funcWrite('data:image/jpeg;base64,'+btoa(bs.join('')));
			*/
			//var blob = new Blob(data, { type: "image/jpeg" });
			//window.location(blob, 'D:/msSaveBlob_testFile.jpeg');
			//saveAs(blob);
			//document.location.href= 'D:\\'+data.01.jpg;
			//alert('File save request made using msSaveBlob() - note the single "Save" button below.');
			/*
			   ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    response.getEntity().writeTo(baos);
			    byte[] bytes = baos.getBytes();
			    
			    
			 a = new Uint8Array(myArray);
			 alert(a.length);
			
			 var nb = a.length;
			    
			    var binary = "";
			    for (var i = 0; i < a.length; i++)
			    	{
			    		alert("binary"+binary);
			       		 binary += String.fromCharCode(a[i]);
			    	}
			    var base64 = window.btoa(binary);
			    alert("base64"+ base64);
			   
			    
			    var bytes = new Uint8Array(str.length);
			    for (var i=0; i<str.length; i++)
			        bytes[i] = str.charCodeAt(i);
			  
			    var image = new Image();
			    //image.onload = onLoad;
			     
			    image.src = 'data:' + mime + ';base64,' + base64;
			    return image;
			    //  loc = image;
			    */
			   
		
	},
	error : function(data) {
		console.log("error data"+data)
		alert("Error: " + data);
	}
	});
}




$(function() {
	
	$(".datepicker").datepicker({
		dateFormat : 'dd-mm-yy' 
	});
});


var counter=2;
var counterPhotoOfFixes = 2;

function myfunction()
{
	 	var msg = '';
	    for(i=1; i<counter; i++){
	    	var len=$('#textbox' + i).val().split('\\').pop().length;
	    	console.log("length is >>>>>>>>"+len);   	
	    	msg += $('#textbox' + i).val().split('\\').pop()+',';;
	    }
	    $('#photoOfIssue').val(msg);
}

function uploadClick1(value)
{
	/*
	var iframe = $("<iframe>").attr({
        src: "forms/uploadFile1",
        style: "visibility:hidden;display:none"
    }).appendTo("#addButton");
	
	
	$("iframe").load(function(){

		alert("The file is uploaded");
		$.post('forms/uploadFile1',null,function(){
				var file = "textbox1";
			     $("#ajaxResultTest").append("<h4>" + file.name + ":" +   file.id +"</h4>");

			 },'json');
		

		});
	*/
	
	alert("upload clicked");
	//alert(value);
	//alert($(value).attr('id'))
	var idUploadButtonID = $(value).attr('id');
	var trimUpload = idUploadButtonID.substring(6, idUploadButtonID.length);
//	alert("trimUpload>>>"+trimUpload);
	var currentUploadFileName = 'textbox'+trimUpload;
	//alert("currentUploadFileName>>>>>>>>"+currentUploadFileName);
	
	var photoVal= document.getElementById(currentUploadFileName).files[0];
	//alert("photoVal>>>>>"+ photoVal);
	var fileSelect = document.getElementById(currentUploadFileName).value;
	//alert("fileSelect>>>>>>>"+fileSelect);
	 var filePath=$('#textbox'+trimUpload).val(); 
	 var fileList = $('#textbox'+trimUpload).files;
	//alert(" Selected File Path"+filePath);
	
	
	
	var MyForm = new FormData();
	MyForm.append("file", currentUploadFileName.files);
	
	var img1 = new Image();
	
	img1.src = "C:\\Users\\Zeroone39\\Downloads\\1375813806_user.png";
	var path ="C:\\Users\\Zeroone39\\Downloads\\1366306826_tree_1.png";
	//var inp = document.getElementById('textbox1');
	var Type ="hai i m type from client call"; 
	// var formData = new FormData($(this)[0]);
	
	// var file = currentUploadFileName.files;
    // var fr = new FileReader();
    // fr.onload = receivedText;
     //fr.readAsText(file);
     //fr.readAsDataURL(file);
     
   //  alert(fr.result);
	 
	//alert("uploadClick1");
	$.ajax({		
		 type : "POST",
		 //enctype:"multipart/form-data",
		 contentType: 'image/jpeg',
		 data : MyForm,
		 url : "forms/uploadFile1?textbox1="+fileSelect+"&fileContent="+MyForm,
		 processData: false,
		 success : function(result) {
			alert("formsNew/uploadFile1 called");
// 			alert('success>>>>>successsuccesssuccesssuccess');
// 			alert('success>>>>>'+result);
			alert("Success");
			
		},
		error : function(result) {
// 			console.log("error data"+result)
// 			alert("Error: " + result);
// 			alert("failed >>>>>>>>>failedfailedfailedfailed");
			alert("Failure");
		}
	});

}

function uploadClick(value)
{
	alert("upload clicked");
	alert(value);
	alert($(value).attr('id'))
	var idUploadButtonID = $(value).attr('id');
	var trimUpload = idUploadButtonID.substring(6, idUploadButtonID.length);
	alert(trimUpload);
	var currentUploadFileName = 'textbox'+trimUpload;
	alert("currentUploadFileName>>>>>>>>"+currentUploadFileName);
	
	var photoVal= document.getElementById(currentUploadFileName).files[0];
	alert("photoVal>>>>>"+ photoVal);
	var fileSelect = document.getElementById(currentUploadFileName).value;
	alert("fileSelect>>>>>>>"+fileSelect);
	//var files = fileSelect.files[0];
	//alert("files.length>>>>>"+files.length);
	
	//var MyForm = new FormData();
   // MyForm.append("file", currentUploadFileName.files[0]);
   
   $.ajax({
		type : "GET",
		url : "forms/docDownloadByPathFilnameFormId?fileName="+'asdf.jpg'+"&path="+'D:',
		cache : false,
		success : function(data,status, statusText, responses, headers) {
			alert("success");
			}
		});
 
   /*
	$.ajax({		
		 type : "GET",
		 url : "forms/uploadFile1",
		 cache : false,
			 //"forms/uploadFile?fileName="+fileSelect+"&path="+'D:/SDP/TempAlfDoc',
		 success : function(data) {
			alert("formsNew/uploadFile called");
			alert('success>>>>>'+data);
		},
		error : function(data) {
			console.log("error data"+data)
			alert("Error: " + data);
		}
		
	});*/
	
}


function propertySelected(id){

	//alert(id);
	
	var propertyName=document.getElementById(id).value;
	
	
	if(propertyName=="0"){
		$('#linkToTenancy').find('option').remove();
		
	}else{
		
		
		$('#linkToTenancy').find('option').remove();
		
	
	$.ajax({
		type : "GET",
		url : "forms/getTenancyList?propertyId=" + propertyName,
		cache : false,
		success : function(data) {
			//document.getElementById(id).value="";
			
			
			propertySelectCompleted(data);
		}
	});
	}
}



function propertySelectCompleted(tenancyList){
	
	
	$('#linkToTenancy')
    .find('option')
    .remove();
	
	$('#linkToTenancy').append(new Option("--Select--", "0", true, true));
	
	if(tenancyList!=""){
        var str_array = tenancyList.split(',');
      	for(var j = 0; j <= str_array.length-1; j++) {
        	
        	
        	$('#linkToTenancy').append(new Option(str_array[j], str_array[++j], false, false));
        }
       
	}
}

function myfunctionPhotoOfFixes()
{
	var fileNamePhotoOfFixes = '';
	//alert(' called myfunctionPhotoOfFixes');
	for(i=1;i<counterPhotoOfFixes;i++){
		var len=$('#fileUldPhotoOfFixes' + i).val().split('\\').pop().length;
		console.log("length is >>>>>>>>"+len);
		fileNamePhotoOfFixes += $('#fileUldPhotoOfFixes' + i).val().split('\\').pop()+',';;	
	}
	 $('#f_5_5_PhotoOfFixes').val(fileNamePhotoOfFixes);
	
}

/*
function linkOfBuilderFormPopulateOption(tenancyList){
	
	
	$('#linkOfBuilderForm')
    .find('option')
    .remove();
	
	$('#linkOfBuilderForm').append(new Option("--Select--", "0", true, true));
	
	if(tenancyList!=""){
        var str_array = tenancyList.split(',');
      	for(var j = 0; j <= str_array.length-1; j++) {
        	
        	
        	$('#linkOfBuilderForm').append(new Option(str_array[j], str_array[++j], false, false));
        }
       
	}
}
*/

$(document).ready(function(){
	$( "#dateRaised" ).removeClass( "datepicker" );
	//var counter = 2;
	//var j=1;
	//var fileNameString = '';
	//alert(counter);
	alert("newjobform.jsp ready");
	alert("RoleDetailshidden>>>>>>>>>>>>>>>>>"+document.getElementById("RoleDetailshidden").value);
	var RoleDetailshiddenValue = document.getElementById("RoleDetailshidden").value;
	
	
	$("#linkOfBuilderForm").hide();$("#linkOfBuilderForm_Label").hide();
	$("#label_appoinmentDate").hide();$("#appoinmentDate").hide();
	$("#label_appoinmentTimeRange").hide();$("#appoinmentTimeRange").hide();
	$("#label_linkToInvoiceForJob").hide();$("#linkToInvoiceForJob").hide();
	$("#label_linkToCompanyContactInfo").hide();$("#linkToCompanyContactInfo").hide();
		
	var tokens = new Array();
	tokens = RoleDetailshiddenValue.split(",");

	for(var x=0;x<tokens.length-1;x++){
		if(tokens[x] == 1)
		{
				$("#linkOfBuilderForm").show();
				$("#linkOfBuilderForm_Label").show();
				break;
		}
		if(tokens[x] == 11)
		{
			//for show hide tenant
			break;
		}
		if(tokens[x] == 12)
		{
			//for show hide builder
			$("#label_appoinmentDate").show();
			$("#appoinmentDate").show();
			$("#label_appoinmentTimeRange").show();
			$("#appoinmentTimeRange").show();
			$("#label_linkToInvoiceForJob").show();
			$("#linkToInvoiceForJob").show();
			$("#label_linkToCompanyContactInfo").show();
			$("#linkToCompanyContactInfo").show();
			break;
		}
	}
	
	
	var status = "${status}";
	var tenancy = "${tenancy}";
	alert("status::::"+ status);
	if (status == "Job Created" && status!="") {
		alert("Inside if of Job Created::::");
		//$( ".text").removeClass("datepicker"); 
		$( "#dateRaised" ).removeClass( "datepicker" );
		document.getElementById('jobNumber').readOnly = true;
		document.getElementById('descriptionOfJob').readOnly = true;
		document.getElementById('dateRaised').readOnly = true;
		document.getElementById('linkToTenancy').value = tenancy;
	}
	
	/*
	var builderShowFlag=-1;
	builderShowFlag = RoleDetailshiddenValue.search("1");
	
	if(builderShowFlag>0)
	{
		$("#linkOfBuilderForm").show();
		$("#linkOfBuilderForm_Label").show();
	}
	*/
	
	
	
		
	
	 $("#addButton").click(function () {
		if(counter>20){
		            alert("Only 20 Images are allowed");
		            return false;
		    }   
		    var newTextBoxDiv = $(document.createElement('div'))
		         .attr("id", 'TextBoxDiv' + counter);
		    newTextBoxDiv.append('<p><label class="FormFieldLabelRequired">Image #'+ counter + ' : </label>' +
		          '<input type="file" name="textbox' + counter + 
		          '" id="textbox' + counter + '" value="" onchange="myfunction();" accept="image/*"/><input type="button" name="upload'+counter+'" id="upload'+counter+'" value="Upload" onclick="uploadClick1(this);"/></p>');
		    newTextBoxDiv.appendTo("#TextBoxesGroup");
		    counter++;
			//finalCounter =counter;
			});
	 
	 $("#addImagePhotoOfFixes").click(function(){
			console.log('addImagePhotoOfFixes clicked'); 
			if(counterPhotoOfFixes>20){
				alert("Only 20 Images are allowed");
	            return false;
			}
			 var newFUDDiv = $(document.createElement('div'))
			         .attr("id", 'FUDDiv' + counterPhotoOfFixes);
			    newFUDDiv.append('<p><label class="FormFieldLabelRequired">Image #'+ counterPhotoOfFixes + ' : </label>' +
			          '<input type="file" name="fileUldPhotoOfFixes' + counterPhotoOfFixes + 
			          '" id="fileUldPhotoOfFixes' + counterPhotoOfFixes + '" value="" onchange="myfunctionPhotoOfFixes();" accept="image/*"/></p>');
			    newFUDDiv.appendTo("#fileUploadGroupPhotoOfFixes");
			    counterPhotoOfFixes++;
			
		 });	
		
	$("#removeButton").click(function () {
		alert('removeButton clicked');
	});
	
	
	$("#getButtonValue").click(function () {
		var msg = '';
	    for(i=1; i<counter; i++){
	      msg += $('#textbox' + i).val().split('\\').pop()+',';;
	    }
	          //alert(msg);
	          $('#photoOfIssue').val(msg);
	     });

	
	
});



// var jsonString=${jsonStringValue};
</script>
 

<span class="grid-box-header" id="header2title">Form Upload <font class="mand">* Mandatory fields</font>
	<a href='#' class='close-widget-box'><img
		src="resources/images/wizart/x.png" class="float-right" /></a>
</span>




<form method="post" id="formDatas">
		<div id="ajaxResultTest">
		</div>
		<div id="at2" >
		 
		<input type="hidden" value="${jobId }" id="jobId" name="jobId"/>
		<input type="hidden" name="RoleDetailshidden" value="<%=RoleDetails%>" id="RoleDetailshidden" />
		
		
		
	
		<p align="left">
				<b><font size="3" >New Job Form</font></b>
			</p>
			<p>
			
			<p>
				<label class="FormFieldLabelRequired" for="linkOfPropertyForm" title="First Name">Property:</label>
				
			<select class="text" id="linkOfPropertyForm" name="linkOfPropertyForm" onchange="propertySelected('linkOfPropertyForm');">
			<option selected="selected" value="0">--Select--</option>
			<c:forEach var="i" items="${properties}">
			<option  value="${i.id }">${i.propertyTitle }</option>
			</c:forEach>
			</select>
			<c:out value="${linkOfPropertyForm}"></c:out><br/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="linkToTenancy" title="Tenancy">Tenancy:</label>
			<select class="text" id="linkToTenancy" name="linkToTenancy" onchange="tenancySelected('linkToTenancy');">
				
			</select>
			
			<c:out value="${linkToTenancy}"></c:out><br/>
			</p>
		<!-- 	
			<p>
				<label class="FormFieldLabelRequired" for="f_5_3" title="First Name">Link Of Tenant Form:</label>
				<input class="text" id="linkOfTenantForm" name="linkOfTenantForm" type="text" maxlength="50" value="${linkOfTenantForm}"/>
			</p>
			
		 -->
			
			<p>
			<label class="FormFieldLabelRequired" for="linkOfBuilderForm" title="Tenancy" name="linkOfBuilderForm_Label" id="linkOfBuilderForm_Label">Builder:</label>
			
			<select class="text" id="linkOfBuilderForm" name="linkOfBuilderForm" >
			<option selected="selected" value="0">--Select--</option>
			<c:forEach var="i" items="${buildersList}">
			<option  value="${i.id }">${i.userName}</option>
			</c:forEach>
			</select>
			<c:out value="${linkOfBuilderForm}"></c:out><br/>
			</p>
			
			<!-- 
			<p>
				<label class="FormFieldLabelRequired" for="f_5_3" title="First Name">Link To Builder Form:</label>
				<input class="text" id="linkOfBuilderForm" name="linkOfBuilderForm" type="text" maxlength="50" value="${linkOfBuilderForm}"/>
			</p>
			  -->
		
			
			<p>	
				<label class="FormFieldLabelRequired" for="f_5_5" title="Landline Number">Date Raised:</label>
				<input class="text  datepicker" id="dateRaised" name="dateRaised" type="text" value="${dateRaised}" readonly="readonly" format="dd-mm-yyyy" type="text"/><br/>
			</p>
			<p>	
				<label class="FormFieldLabelRequired" for="f_9_2" title="Product Name">Job Name:</label>
				<input class="text" id="jobNumber" name="jobNumber" type="text" maxlength="50" value="${jobNumber}"/><br/>
			</p>	
			<p>	
				<label class="FormFieldLabelRequired" for="f_9_3" title="Product Name">Description of Job:</label>
				<input class="text" id="descriptionOfJob" name="descriptionOfJob" type="text" maxlength="50" value="${descriptionOfJob}"/><br/>
			</p>	
<!-- 
			<p>	
				<label class="FormFieldLabelRequired" for="f_9_6" title="Product Name">Photo Of Issue:</label>
				<input class="text" id="photoOfIssue" name="photoOfIssue" type="text" maxlength="500" value="${photoOfIssue}"/>
			<input class="download" type='button' value='Add Image' id='addButton'>	<br/>
				<c:forTokens var="photo" items="${photoOfIssue}" delims=",">
				   <a href="#" onclick="downloadImage('<%=test%>','${photo}');">${photo}</a> 
        		</c:forTokens>							
				<br/>
		</p>		
				
				
			  		
								
				<div id='TextBoxesGroup'>
    			<div id="TextBoxDiv1">
    			<p>
        		<label class="FormFieldLabelRequired">Image #1 : </label><input type='file' id='textbox1' name='textbox1' onchange="myfunction();" accept="image/*"/><input type="button" name="upload1" id="upload1" value="Upload" onclick="uploadClick1(this);"/>
        		</p>
    			</div>
				</div>
				
	 -->			
		<!-- 
		<p>
		
		
<label class="FormFieldLabelRequired">Image #Test : </label><input type='file' name='fileTest' id='fileTest' onchange="myfunctionTest();" />
<input type="button" name="uploadTest" id="uploadTest" value="uploadTest" onclick="uploadTestClick(this);"/>
			</p>	
			 -->
			
				<p>
				<label class="FormFieldLabelRequired" for="f_9_4" title="Product Name">Urgency:</label>
				<select class="text" id="isUrgency" name="isUrgency">
				<option value="-1">Select</option> 
				<option value="Urgent">Urgent</option>
				<option value="Not Urgent">Not Urgent</option>
				<option value="Priority">Priority</option>
				</select>
				<c:out value="${isUrgency}"></c:out><br/>
				</p>
			
			
			<p>
				<label class="FormFieldLabelRequired" for="f_5_5" title="Landline Number" id="label_appoinmentDate">Appointment Date:</label>
				<input class="text datepicker" id="appoinmentDate" name="appoinmentDate" type="text" value="${appoinmentDate}" readonly="readonly" format="dd-mm-yyyy" type="text"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_5_5" title="Landline Number" id="label_appoinmentTimeRange">Appointment Time Range:</label>
				<input class="text datepicker" id="appoinmentTimeRange" name="appoinmentTimeRange" type="text" value="${appoinmentTimeRange}" readonly="readonly" format="dd-mm-yyyy" type="text"/>
			</p>
	<!-- 		
			<p>
				<label class="FormFieldLabelRequired" for="f_5_5" title="Landline Number">Photo Of Fixes:</label>
				<input class="text" id="f_5_5_PhotoOfFixes" name="f_5_5_PhotoOfFixes" type="text" value="${photoOfFixes}"/>
				<!--<c:out value="${photoOfFixes}"></c:out>-->
	<!-- 						
			<input class="download" type='button' value='Add Image' id='addImagePhotoOfFixes' /><br/>
				<c:forTokens var="photo" items="${photoOfFixes}" delims=",">
            		<a href="<c:url value="${photo}"/>" class="FormFieldLabelRequired">${photo}</a> <br/>
        		</c:forTokens>				
				
				
				<div id='fileUploadGroupPhotoOfFixes'>
    			<div id="fileUploadGroupPhotoOfFixesDiv1">
    			<p>
        		<label class="FormFieldLabelRequired">Image #1 : </label><input type='file' id='fileUldPhotoOfFixes1' onchange="myfunctionPhotoOfFixes();" accept="image/*"/>
        		</p>
    			</div>
				</div>
		 -->	
			
			<br/>
			
			<!-- 
			<p>
				<label class="FormFieldLabelRequired" for="f_5_3" title="First Name">Link Of Property Form:</label>
				<input class="text" id="linkOfPropertyForm" name="linkOfPropertyForm" type="text" maxlength="50" value="${linkOfPropertyForm}"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_5_3" title="First Name">Link Of Tenant Form:</label>
				<input class="text" id="linkOfTenantForm" name="linkOfTenantForm" type="text" maxlength="50" value="${linkOfTenantForm}"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_5_3" title="First Name">Link To Builder Form:</label>
				<input class="text" id="linkOfBuilderForm" name="linkOfBuilderForm" type="text" maxlength="50" value="${linkOfBuilderForm}"/>
			</p>
			 -->
			<p>
				<label class="FormFieldLabelRequired" for="f_5_3" title="First Name" id="label_linkToInvoiceForJob">Link To Invoice For This Job:</label>
				<input class="text" id="linkToInvoiceForJob" name="linkToInvoiceForJob" type="text" maxlength="50" value="${linkToInvoiceForJob}"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="f_5_3" title="First Name" id="label_linkToCompanyContactInfo">Link To Company Contact Information:</label>
				<input class="text" id="linkToCompanyContactInfo" name="linkToCompanyContactInfo" type="text" maxlength="50" value="${linkToCompanyContactInfo}"/>
			</p>
			
			
			
			
		
		 	
		</div>
		
		<div id="show"></div>
</form>
		<div id="saveSubmit" align="center">
				<span><input
					type="button" class="download" id="cancelFormAttr"
					value="cancel" onclick="homeClicked();"></span>
					<span><input
					type="submit" class="download" id="saveFormAttr"
					value="Save" onclick="saveForm();"></span>
					<span><input
					type="submit" class="download" id="submitSaveAttr"
					value="Submit" onclick="saveForm();"></span>
				</div>
				
		<!-- <div id="attr3Div"></div> -->
	
	
	
	

