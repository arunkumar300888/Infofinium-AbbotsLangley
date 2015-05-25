<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="resources/style/jquery.multiselect.css" />
<script type="text/javascript" src="resources/js/jquery.multiselect.js"></script>
<script type="text/javascript" src="resources/js/angular.js"></script>
<style>
 .glyphicon{
 	cursor:pointer;
 }
</style>
<script type="text/javascript">

var items=[];
function uploadPic(path){
	
}


$(document).ready(function() {
	//var	everythingOk="${everythingOk}";

		$("#photosLink").hide();
		$("#submissionDate").val($.datepicker.formatDate("dd-mm-yy", new Date()));
		
		/* if(everythingOk!="" && everythingOk!=null && everythingOk=="Required"){
		$( "#everythingOk" ).prop( "checked", true );
		} */
		
	//var propertyName="${propertyName}";
	//var userFormId="${userFormId}";
	//alert(propertyName);

	//propertySelectedForRet("${propertyName}", userFormId);



	//tenancySelectedForRet("${tenantName}", userFormId);


	/* $("#propertyName option[value='${propertyName}']").attr('selected', 'selected');
	$("#tenancyName option[value='${tenantName}']").attr('selected', 'selected'); */

	var roomNamesSel="${roomNamesRet}";



	if(roomNamesSel!=""){
		$('#rooms').append(new Option("--Select--", "-1", true, true));

	    var str_array = roomNamesSel.split(',');
	  	for(var j = 0; j <= str_array.length-1; j++) {
	    	$('#rooms').append(new Option(str_array[j], str_array[j], false, false));
	    }
	}
	/* var roomNames="${roomNamesRet}";

	var wrapper         = $("#roomForTenant");

	var i=1;
		   
	  	if(roomNames!=""){
	     var str_array = roomNames.split(',');
	     
	     for(var j = 0; j < str_array.length-1; j++) {
	    	//alert(str_array[j]);
	     $(wrapper).append('<section><p> <label class="FormFieldLabelRequired" for="room'+i+'" title="Room Name">Room Name:</label>'+
			'<input class="text" id="room'+i+'"  name="room'+i+'" value="'+str_array[j]+'" type="text" /></p>'+
	     '<p> <label class="FormFieldLabelRequired" for="tenantName'+i+'" title="Tenant Name">Tenant Name:</label>'+
	 			'<input class="text" id="tenantName'+i+'"  name="tenantName'+i+'" value="'+str_array[++j]+'" type="text" /></p>'+
	 			'<p> <label class="FormFieldLabelRequired" for="tenantAddress'+i+'" title="Tenant Address">Tenant Address:</label>'+
	 			'<textarea class="text" cols="43" id="tenantAddress'+i+'" name="tenantAddress'+i+'" rows="6" maxlength="500">'+str_array[++j]+'</textarea></p>'+
	   	   
			'<p> <label class="FormFieldLabelRequired" for="listOfFurnishing'+i+'" title="List Of Furninshing">List Of Furnishing:</label>'+
			'<input class="text" id="listOfFurnishing'+i+'"  name="listOfFurnishing'+i+'"  type="text" /></p>'+
			'<p> <label class="FormFieldLabelRequired" for="everythingOk'+i+'" title="Everything Ok?">Everything OK?</label>'+
			'<input id="everythingOk'+i+'"  name="everythingOk'+i+'"  type="checkbox" value="Required"/></p><br/>'+
			'<p> <label class="FormFieldLabelRequired" for="notes'+i+'" title="Notes/Details">Notes/Details</label>'+
			'<textarea class="text" cols="43" id="notes'+i+'" name="notes'+i+'" rows="6" maxlength="500"></textarea></p><br/>'+
			'<p> <label class="FormFieldLabelRequired" for="photo'+i+'" title="Photo">Photo</label>'+
			'<input class="text" id="photo'+i+'"  name="photo'+i+'"  type="text" /></p><br/>'+
			'</section>');
	     i++;
	     }
	   	
		   
	}
	 */

	//propertySelectedForRet("${propertyName}",tableValues, userFormId);

		
		 var tableValues="${tableValues}";
		var finalval = tableValues.split(",");
		
		var val;
		//var spl;
		for(var i=0;i<finalval.length-1;i++){
			 val=finalval[i]; 
			
			//document.getElementById(finalval[i]).value=finalval[++i];
			 
			$("#"+finalval[i]).attr("value",finalval[++i]);
			
			
		}
		
		 for(var i=0;i<finalval.length-1;i++){
			 val=finalval[i];	
			 if($('#'+finalval[i]).is(':checkbox')){
				//	alert(finalval[i]);
					$("#"+finalval[i]).prop("checked", true);
				}
			
		} 
		 
		 var pics="${pictures}";
		 if(pics!=""){
			var picsplit = pics.split(",");
			
			var picvalId;
			var picval;
			for(var i=0;i<picsplit.length-1;i++){
				picvalId=picsplit[i]; 
				picval=picsplit[++i];
				var k=2;
				var id="pic"+k;
				k++;
			//	alert(picval);
				//document.getElementById(finalval[i]).value=finalval[++i];
				 
				//$("#"+finalval[i]).attr("value",finalval[++i]);
				var wrapper         = $("#pics");
				//var id="pic"+cnt;
				$(wrapper).append('<p id="pic'+k+'"><label class="FormFieldLabelRequired" for="'+picvalId+'" title="Photo '+k+ '">Photos:</label>'+
						'<input class="text searchtext" id="'+picvalId+'" name="'+picvalId+'" onchange="saveToLocal(\''+picvalId+'\')" value="'+picval+'"  readonly="readonly"  type="file" accept="image/*" />'+
						'<input type="button"  class="addPic" id="addPic1" name="addPic1" style="margin-left:1%;width: 22px; height: 22px; border: 0px;" onclick="addPicture();"/>'+
						'<input class="clearFile" type="button"  onclick="removePicture(\''+id+'\',\''+picvalId+'\');"/><br/>');
				
			}
		
		 } 
		
	$("#propertyForm option[value='${propertyName}']").attr('selected', 'selected');
	$("#rooms option[value='${roomNameSel}']").attr('selected', 'selected');

		/* $("#furnishingsList option[value='${furnishingsList}']").attr('selected', 'selected'); */
		
		$("#secGrp option[value='${securityGrp}']").attr('selected', 'selected');
		$("#compGrp option[value='${companyGrp}']").attr('selected', 'selected');
		$("#wrkFlw option[value='${modelId}']").attr('selected', 'selected');
		
		
		});


var cnt=1;
	function addPicture(){
		//alert("pictre");
		
		cnt++;
		var wrapper         = $("#pics");
		var id="pic"+cnt;
		var photoId="photo"+cnt;
		$(wrapper).append('<p id="pic'+cnt+'"><label class="FormFieldLabelRequired" for="photo'+cnt+'" title="Photo '+cnt+ '">Photos:</label>'+
				'<input class="text searchtext" id="photo'+cnt+'" name="photo'+cnt+'" onchange="saveToLocal(\''+photoId+'\')" readonly="readonly"  type="file" accept="image/*" />'+
				'<input type="button"  class="addPic" id="addPic1" name="addPic1" style="margin-left:1%;width: 22px; height: 22px; border: 0px;" onclick="addPicture();"/>'+
				'<input class="clearFile" type="button"  onclick="removePicture(\''+id+'\',\''+photoId+'\');" /><br/>');
	       
	}
	

	function removePicture(id,photoId){
		//alert("pictre");
		//var cnt=1;
		//alert("pictre"+id);
		deletePhoto($("#propertyForm").val()+"_"+$("#tenantName").val(),$("#new"+photoId).val());
		$("#"+id ).remove();
	}
	



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
	
	//start of validation
	
	function saveCheck(){
		if($("#propertyForm").val()){
			return 1;
		}
		else{
			dialogTemplate('Required Fields','Please select the property field');
			return 0;
		}
	}

	//end of validation

	function saveForm(){
		
		/* document.getElementById("json").value=JSON.stringify(eval("("+items+")")); */
		document.getElementById("json").value=JSON.stringify(items);
		var check=saveCheck()
		if(check){
		loadProgress();
		$("#formDatas").attr("action", "forms/saveInventoryForDraft");
		$("#formDatas").submit();
		}
		//loadAllDocuments();
		//$("#trayDiv").html(data);
		//removeProgress();
		
	}

	function submitForm(){
		document.getElementById("json").value=JSON.stringify(items);
		var check=saveCheck()
		if(check){
		loadProgress();
		$("#formDatas").attr("action", "forms/createInventory");
		$("#formDatas").submit();
		}
		//loadAllDocuments();
		//$("#trayDiv").html(data);
		//removeProgress();
		
	}

	$(function() {
		
		$(".datepicker").datepicker({
			dateFormat : 'dd-mm-yy' 
		});
	});

	/* function tenancySelected(id,userFormId){
		
		var tenancyName=document.getElementById(id).value;
		
		if(tenancyName=="-1"){
			$('#roomForTenant').hide();
	 	   $('#roomForTenant').find('section').remove();
		}else{
			
		$.ajax({
			type : "GET",
			url : "forms/getRoomsForTenancy?tenancyName=" + tenancyName+"&userFormId="+userFormId,
			cache : false,
			success : function(data) {
				//document.getElementById(id).value="";
				
				
				tenancySelectCompleted(data);
			}
		});
		}
	}

	function tenancySelectCompleted(roomNames){
		
		
		$('#roomForTenant').show();
		var wrapper         = $("#roomForTenant");
			  
		var i=1;
			   
	      	if(roomNames!=""){
	         var str_array = roomNames.split(',');
	         
	         for(var j = 0; j < str_array.length-1; j++) {
	        	//alert(str_array[j]);
	         $(wrapper).append('<section><p> <label class="FormFieldLabelRequired" for="room'+i+'" title="Room Name">Room Name:</label>'+
				'<input class="text" id="room'+i+'"  name="room'+i+'" value="'+str_array[j]+'" type="text" /></p>'+
				'<p> <label class="FormFieldLabelRequired" for="listOfFurnishing'+i+'" title="List Of Furninshing">List Of Furnishing:</label>'+
				'<input class="text" id="listOfFurnishing'+i+'"  name="listOfFurnishing'+i+'"  type="text" /></p>'+
				'<p> <label class="FormFieldLabelRequired" for="everythingOk'+i+'" title="Everything Ok?">Everything OK?</label>'+
				'<input id="everythingOk'+i+'"  name="everythingOk'+i+'"  type="checkbox" value="Required"/></p><br/>'+
				'<p> <label class="FormFieldLabelRequired" for="notes'+i+'" title="Notes/Details">Notes/Details</label>'+
				'<textarea class="text" cols="43" id="notes'+i+'" name="notes'+i+'" rows="6" maxlength="500"></textarea></p><br/>'+
				'<p> <label class="FormFieldLabelRequired" for="photo'+i+'" title="Photo">Photo</label>'+
				'<input class="text" id="photo'+i+'"  name="photo'+i+'"  type="text" /></p><br/>'+
				'</section>');
	         i++;
	         }
		   	
			   
	 }
	} */

	function propertySelectedForRet(value,tableValues,userFormId){
		if(userFormId!=-1){
		$.ajax({
			type : "GET",
			url : "forms/getTenancyForInventory?propertyName=" + value+"&userFormId="+userFormId,
			cache : false,
			success : function(data) {
				//document.getElementById(id).value="";
				
				
				propertySelectCompleted(data);
				
				
			}
		}); 
		
		/* $(document).ready(function() {
			//alert(" "+tableValues);
			var finalval = tableValues.split(",");
			//alert(finalval.length);
			var val;
			var spl;
			for(var i=0;i<finalval.length-1;i++){
				 val=finalval[i];
				// alert(i);
					//alert("val"+val);
					
					//alert("final val "+finalval[++i]);
				//window.onload=function(){
				document.getElementById(finalval[i]).value=finalval[++i];
				//}
			}
		}); */
		
		
		
		}
	}



	function propertySelected(id,userFormId){

		//alert(id);
		$('#rooms')
	    .find('option')
	    .remove();
		var propertyName=document.getElementById(id).value;
		//alert(propertyName);
		/* if(propertyName=="-1"){
			$('#tenantName').find('option').remove();
			$('#roomForTenant').hide();
	 	   $('#roomForTenant').find('section').remove();
		}else{
			
			
			$('#tenantName').find('option').remove();
			$('#roomForTenant').hide();
	 	   $('#roomForTenant').find('section').remove();
		
		$.ajax({
			type : "GET",
			url : "forms/getTenancyForInventory?propertyName=" + propertyName+"&userFormId="+userFormId,
			cache : false,
			success : function(data) {
				//document.getElementById(id).value="";
				
				
				propertySelectCompleted(data);
			}
		});
		} */
		
		if(propertyName=="-1"){
			$('#rooms').find('option').remove();
			/* $('#roomForTenant').hide(); */
	 	   $('#roomForTenant').find('section').remove();
		}else{
			
			
			$('#rooms').find('option').remove();
			$('#roomForTenant').hide();
	 	   $('#roomForTenant').find('section').remove();
		
		$.ajax({
			type : "GET",
			url : "forms/getTenancyForInventory?propertyName=" + propertyName+"&userFormId="+userFormId,
			cache : false,
			success : function(data) {
				//document.getElementById(id).value="";
				
				
				propertySelectCompleted(data);
			}
		});
		}
	}

	function propertySelectCompleted(roomNames){
		
		
		$('#rooms')
	    .find('option')
	    .remove();
		
		$('#rooms').append(new Option("--Select--", "-1", true, true));
		
		if(roomNames!=""){
	        var str_array = roomNames.split(',');
	      	for(var j = 0; j <= str_array.length-1; j++) {
	        	
	        	
	        	$('#rooms').append(new Option(str_array[j], str_array[j], false, false));
	        }
	       
		}
		
		/* $('#roomForTenant').show();
		var wrapper         = $("#roomForTenant");
			  
		var i=1;
			   
	      	if(roomNames!=""){
	         var str_array = roomNames.split(',');
	         
	         for(var j = 0; j < str_array.length-1; j++) {
	        	//alert(str_array[j]);
	         $(wrapper).append('<section><p> <label class="FormFieldLabelRequired" for="room'+i+'" title="Room Name">Room Name:</label>'+
				'<input class="text" id="room'+i+'"  name="room'+i+'" value="'+str_array[j]+'" type="text" /></p>'+
	         '<p> <label class="FormFieldLabelRequired" for="tenantName'+i+'" title="Tenant Name">Tenant Name:</label>'+
	     			'<input class="text" id="tenantName'+i+'"  name="tenantName'+i+'" value="'+str_array[++j]+'" type="text" /></p>'+
	     			'<p> <label class="FormFieldLabelRequired" for="tenantAddress'+i+'" title="Tenant Address">Tenant Address:</label>'+
	     			'<textarea class="text" cols="43" id="tenantAddress'+i+'" name="tenantAddress'+i+'" rows="6" maxlength="500">'+str_array[++j]+'</textarea></p>'+
	       	   
				'<p> <label class="FormFieldLabelRequired" for="listOfFurnishing'+i+'" title="List Of Furninshing">List Of Furnishing:</label>'+
				'<input class="text" id="listOfFurnishing'+i+'"  name="listOfFurnishing'+i+'"  type="text" /></p>'+
				'<p> <label class="FormFieldLabelRequired" for="everythingOk'+i+'" title="Everything Ok?">Everything OK?</label>'+
				'<input id="everythingOk'+i+'"  name="everythingOk'+i+'"  type="checkbox" value="Required"/></p><br/>'+
				'<p> <label class="FormFieldLabelRequired" for="notes'+i+'" title="Notes/Details">Notes/Details</label>'+
				'<textarea class="text" cols="43" id="notes'+i+'" name="notes'+i+'" rows="6" maxlength="500"></textarea></p><br/>'+
				'<p> <label class="FormFieldLabelRequired" for="photo'+i+'" title="Photo">Photo</label>'+
				'<input class="text" id="photo'+i+'"  name="photo'+i+'"  type="text" /></p><br/>'+
				'</section>');
	         i++;
	         }
		   	
			   
	 } */
	      	
	}

	function roomSelected(){
		var room=document.getElementById("rooms").value;
		
		//alert(room);
		if(room!=-1){
			
			$.ajax({
				type : "GET",
				url : "forms/getTenancyDetailsForRoom?roomName="+room,
				cache : false,
				success : function(data) {
					//document.getElementById(id).value="";
					roomSelectCompleted(data);
					
					
				}
			});
		}
		
	}

	function roomSelectCompleted(values){
		//alert(values);
		if(values!=""){
	        var str_array = values.split(',');
	        
	        for(var j = 0; j < str_array.length-1; j++) {
	        	document.getElementById("tenantName").value=str_array[j];
	        	document.getElementById("tenantAddress").value=str_array[j++];
	        }
		}
	}




function clearFunction(){

	$("#make").val("");
    $("#itemName").val("");
    $("#itemDesc").val("");
    $("#itemPhoto").val("");
}


// function readURL(input,rowId) {
//     if (input.files && input.files[0]) {
//         var reader = new FileReader();

//         reader.onload = function (e) {
//             $('#'+rowId).find('a').attr('href', e.target.result);
//         };

//         reader.readAsDataURL(input.files[0]);
//     }
// }


function setLink(rowId){
	var link="${path}"+$("#propertyForm").val()+"_"+$("#tenantName").val()+"/"+$("#itemPhoto").val();
	alert(link);
	$('#'+rowId).find('a').attr('href',link);
}


function modalPopup(id,index,photo){
	var photoName;
	 $("#modalForm").dialog({
	      modal: true,
	      buttons: {
	        Ok: function() {
	        	if(photo!=""){
	        		photoName=photo;
	        	}
	        	else{
	        		photoName=$("#itemPhoto").val().replace("C:\\fakepath\\", "");
	        	}
	        	var dataTitle=$("#make").val()+"-"+$("#itemName").val();
	        	var itemId="#"+id;
		          items[index].itemMake=$("#make").val();
		          items[index].itemName=$("#itemName").val();
		          items[index].itemDesc=$("#itemDesc").val();
		          if(photo==""){
		          //deleting old photo
		          deletePhoto($("#propertyForm").val()+"_"+$("#tenantName").val(),items[index].itemPhoto);
		          //updating the new one
		          items[index].itemPhoto=photoName;
		          $("#itemPhotoForm").attr("action","forms/savePhotoForm?propertyForm="+$("#propertyForm").val()+"&tenant="+$("#tenantName").val());
		          $("#itemPhotoForm").submit();
		          }
		          $(itemId).html("");
		          $(itemId).append('<td>'+$("#make").val()+'</td>'+
       		  '<td>'+$("#itemName").val()+'</td><td>'+$("#itemDesc").val()+'</td>'+
       		  '<td><a href="resources/uploads/inventory/'+$("#propertyForm").val()+'_'+$("#tenantName").val()+'/'+photoName+'" data-lightbox="items"'+
       		  'data-title='+dataTitle+'>'+photoName+'</a></td>'+
       		  '<td>&nbsp;&nbsp;<span class="glyphicon glyphicon-trash" onclick="deleteItem(\''+id+'\')"></span>&nbsp;&nbsp;&nbsp;'+
       		  '<span class="glyphicon glyphicon-edit" onclick="editItem(\''+id+'\')"></span></td>');
	          	  $( this ).dialog( "close" );
		          clearFunction();
	        }
	      }
	    });
}



//edit items
function editItem(id){
	items.forEach(function(item){
		if(item.id==id){
			index=items.indexOf(item);
			$("#make").val(item.itemMake);
	        $("#itemName").val(item.itemName);
	        $("#itemDesc").val(item.itemDesc);
// 	        $("#itemPhoto").val(item.itemPhoto);
		}
	});
	
	 $( "#edit-confirm" ).dialog({
	      resizable: false,
	      height:140,
	      modal: true,
	      buttons: {
	        "Yes": function() {
	          $( this ).dialog( "close" );
	          $("#itemPhotoForm").show();
	          $("#itemPhoto").val("");
	          modalPopup(id,index,"");
	        },
	        No: function() {
	          $( this ).dialog( "close" );
	          $("#itemPhotoForm").hide();
	          modalPopup(id,index,items[index].itemPhoto);
	        }
	      }
	 });
	
	
	
}


//delete items
function deleteItem(id){
	var itemId="#"+id;
	 $( "#dialog-confirm" ).dialog({
	      resizable: false,
	      height:140,
	      modal: true,
	      buttons: {
	        "Yes": function() {
	        	items.forEach(function(item){
	        		if(item.id==id){
	        			deletePhoto($("#propertyForm").val()+"_"+$("#tenantName").val(),item.itemPhoto);
	        			items.splice(items.indexOf(item),1);
		        		$(itemId).html("");
		        		if(items.length==0){
		        			$("#noItem").show();
		        		}	
	        		}
	        	});
	          $( this ).dialog( "close" );
	        },
	        No: function() {
	          $( this ).dialog( "close" );
	        }
	      }
	 });
}


$(document).ready(function(){
	
	 var tab='${tab}';
	
	if(tab!=""){
	items=JSON.parse(tab);
	}
	//console.log(items);
	
	var i=0;
	if(items.length){
		i=items.length;
		$("#noItem").hide();
		items.forEach(function(singleItem){
			$("#tbody").append('<tr id="'+singleItem.id+'" class="odd"><td>'+singleItem.itemMake+'</td>'+
	        		  '<td>'+singleItem.itemName+'</td><td>'+singleItem.itemDesc+'</td>'+
	        		  '<td><a href="resources/uploads/inventory/'+$("#propertyForm").val()+'_'+$("#tenantName").val()+'/'+$("#itemPhoto").val()+'" data-lightbox="items">'+$("#itemPhoto").val()+'</a></td>'+
	        		  '<td>&nbsp;&nbsp;<span class="glyphicon glyphicon-trash" onclick="deleteItem(\''+singleItem.id+'\')"></span>&nbsp;&nbsp;&nbsp;'+
	        		  '<span class="glyphicon glyphicon-edit" onclick="editItem(\''+singleItem.id+'\')"></span></td></tr>'); 
		});
	}
	
	//adding new item
	
	$("#addItem").click(function(){
		//alert("add");
		$("#itemPhotoForm").show();
		if($("#propertyForm").val()==0|| $("#tenantName").val()==""){
			dialogTemplate("Required Fields", "Please select the property and tenant name !");
		}
		else{
		i++;
		clearFunction();
		$("#modalForm").dialog({
		      modal: true,
		      buttons: {
		        Ok: function() {
		          var imageFile = $("#itemPhoto").val().replace("C:\\fakepath\\", "");
		          var dataTitle=$("#make").val()+"-"+$("#itemName").val();
 		          $("#itemPhotoForm").attr("action","forms/savePhotoForm?propertyForm="+$("#propertyForm").val()+"&tenant="+$("#tenantName").val());
 		          $("#itemPhotoForm").submit();
		          $( this ).dialog( "close" );
		          items.push({id:'item'+i,itemMake:$("#make").val(),itemName:$("#itemName").val(),itemDesc:$("#itemDesc").val(),itemPhoto:imageFile});
		          $("#noItem").hide();
		          singleItem=items[items.length-1];
		       //   setLink(singleItem.id);
		        	  $("#tbody").append('<tr id="'+singleItem.id+'" class="odd"><td>'+singleItem.itemMake+'</td>'+
			        		  '<td>'+singleItem.itemName+'</td><td>'+singleItem.itemDesc+'</td>'+
			        		  '<td><a href="resources/uploads/inventory/'+$("#propertyForm").val()+'_'+$("#tenantName").val()+'/'+imageFile+'" data-lightbox="items"'+
			        		  'data-title='+dataTitle+'>'+imageFile+'</a></td>'+
			        		  '<td>&nbsp;&nbsp;<span class="glyphicon glyphicon-trash" onclick="deleteItem(\''+singleItem.id+'\')"></span>&nbsp;&nbsp;&nbsp;'+
			        		  '<span class="glyphicon glyphicon-edit" onclick="editItem(\''+singleItem.id+'\')"></span></td></tr>'); 
		        }
		      }
		    });
		}
	});
	
	
});



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

function removeElement(id){
 	$("#photosLink").hide();
// 	var documentName=document.getElementById(id).value;
// 	deletePhoto($("#propertyForm").val()+"_"+$("#tenantName").val(),documentName);
// 	//alert(documentName);
// 	$.ajax({
// 		type : "GET",
// 		url : "forms/removeTempDoc?docName=" + documentName,
// 		cache : false,
// 		success : function(data) {
// 			document.getElementById(id).value="";
// 		}
// 	});
	deletePhoto($("#propertyForm").val()+"_"+$("#tenantName").val(),$("#new"+id).val());
	$("#new"+id).after('<input type="file" class="text" id="photo1" onchange="saveToLocal('+id+')">');
	$("#new"+id).remove();
	
}


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




	
	function removePhotoFrmLocal(fileName,spanId){
		deletePhoto($("#propertyForm").val()+"_"+$("#tenantName").val(),fileName);
		$("#"+spanId).hide();
	}
var idCount=1;
function saveToLocal(id){
	var photoId="#"+id;
	var imageFile = $(photoId).val().replace("C:\\fakepath\\", "");
//	if ($("#photosLink").is(':hidden')) {
//		$("#photosLink").show();
//		$("#photosLink").attr("href","resources/uploads/inventory/"+$("#propertyForm").val()+"_"+$("#tenantName").val()+"/"+imageFile);	
//	}
//	else{
		$("#uploadedPhotos").append('<span id="link'+idCount+'" style="padding-right:1em"><a href="resources/uploads/inventory/'+$("#propertyForm").val()+'_'+$("#tenantName").val()+'/'+imageFile+'" data-lightbox="additionalPhotos"'+
     		  '>'+imageFile+'</a><span onclick="removePhotoFrmLocal(\''+imageFile+'\',\'link'+idCount+'\')" class="removePhoto glyphicon glyphicon-remove"></span></span>');
//	}
	$("#additionalPhotoForm").html("");
	$(photoId).hide();
	$(photoId).after('<input id="'+id+'" onchange="saveToLocal(\''+id+'\')"  type="file" class="text" value="'+imageFile+'">'); 
	$(photoId).appendTo("#additionalPhotoForm")
// 	$(photoId).clone().appendTo("#additionalPhotoForm");
	$("#additionalPhotoForm").find("input").attr("name","itemPhoto");
	$("#additionalPhotoForm").attr("action","forms/savePhotoForm?propertyForm="+$("#propertyForm").val()+"&tenant="+$("#tenantName").val());
    $("#additionalPhotoForm").submit();
	idCount++;
}




</script>



<span class="grid-box-header" id="header2title">Inventory <font class="mand">* Mandatory fields</font>
	<a href='#' class='close-widget-box'><img
		src="resources/images/wizart/x.png" class="float-right" /></a>
</span>


<div  style="display:none" id="modalForm" style="display:none" title="Add Furnishings">
				<label class="FormFieldLabelRequired" for="make" title="make">Item Make:</label>
				<input class="text" id="make" name="make" type="text" maxlength="50"/>
					
				<label class="FormFieldLabelRequired" for="itemName" title="itemName">Item Name:</label>
				<input class="text" id="itemName" name="itemName" type="text" maxlength="50"/>
			
				<label class="FormFieldLabelRequired" for="itemDesc" title="itemDesc">Item Description:</label>
				<input class="text" id="itemDesc" name="itemDesc" type="text" maxlength="50"/>
				<form id="itemPhotoForm" action="" enctype="multipart/form-data" method="POST" target="hiddenNewFrame">
				<label class="FormFieldLabelRequired" for="itemDesc" title="itemDesc">Photo:</label>
				<input class="text" id="itemPhoto" name="itemPhoto" type="file" accept="image/*"/>
				</form>
</div>

<div  style="display:none" id="dialog-confirm" style="display:none" title="Delete Item">
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Do you want to delete the item?</p>
</div>


<div  style="display:none" id="edit-confirm" style="display:none" title="Delete Item">
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Do you want to change the Photo?</p>
</div>

<form method="post" id="formDatas">


		<div id="at2" class="form2" >
	
		<input type="hidden" value="${inventoryId }" id="inventoryId" name="inventoryId"/>
		
		<input type="hidden"  id="json" name="json"/>
		
		
		<h4>General Informations</h4>
			<div class="hideFalse">
			<section>
			
			
			
			<p>
				<label class="FormFieldLabelRequired" for="propertyForm" title="Property">Property<em class="mand">* </em>:</label>
				<select class="text" id="propertyForm" name="propertyForm" onchange="propertySelected('propertyForm');">
				<option selected="selected" value="0">--Select--</option>
			<c:forEach var="i" items="${properties}">
			<option  value="${i.id }">${i.propertyTitle }</option>
			</c:forEach>
			</select>
				
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="room" title="Property">Rooms<em class="mand">* </em>:</label>
				<select class="text" id="room" name="room" onchange="roomSelected();">
				<%-- <option selected="selected" value="-1">--Select--</option>
			<c:forEach var="i" items="${properties}">
			<option  value="${i.name }">${i.name }</option>
			</c:forEach> --%>
			</select>
				
			</p>
		<%-- 	<p>
				<label class="FormFieldLabelRequired" for="propertyForm" title="Property Form">Property Form:</label>
				<input class="text searchtext" id="propertyForm" name="propertyForm"  readonly="readonly" value="${propertyForm }" type="text" onclick="openUploadForm('propertyForm');"/>
				<input class="openFile" type="button"  onclick="openDoc('propertyForm');">
				<input class="clearFile" type="button"  onclick="removeElement('propertyForm');">
			</p> --%>
		
			
			<p>
				<label class="FormFieldLabelRequired" for="tenantName" title="Tenant Name">Tenant Name:</label>
				
				<input class="text" id="tenantName" name="tenantName" type="text" value="${tenantName }" maxlength="50"/>
				
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="tenantAddress" title="Tenant Address">Tenant Address:</label>				
				<textarea class="text" cols="43" id="tenantAddress" name="tenantAddress" rows="6">${tenantAddress } </textarea>
			</p>
			
			
			
			
			</section>
			
			<section>
			
			<p>
				<label class="FormFieldLabelRequired" for="everythingOk" title="Everything OK">Everything Ok?</label>
				<input  id="everythingOk" name="everythingOk"  type="checkbox" value="Required"/>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="notes" title="First Name">Notes/Details:</label>
				<textarea class="text" cols="43" id="notes" name="notes" rows="6" maxlength="500"> ${notes }</textarea>
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="photos" title="Photos">Photos:</label>
				<input class="text searchtext" id="photo1" onchange="saveToLocal('photo1')" name="photo1"  readonly="readonly"  type="file"/>
				<!--<input type="button"  class="addPic" id="addPic1" name="addPic1" style="width: 22px; height: 22px; border: 0px;" onclick="addPicture();"/>
				<input class="clearFile" type="button"  onclick="removeElement('photo1');">-->
				
			</p>
			
			<p>
				<label class="FormFieldLabelRequired" for="notes" title="Uploaded Photos">Uploaded Photos:</label>
				<span id="uploadedPhotos"></span>
			</p>
			
			<div id="pics">
			
			</div>
<!--			<p>
			<a id="photosLink" href="" data-lightbox="additionalPhotos">View Additional Photos</a>
			</p>-->
			</section>
			
			</div>
			<section style="margin-left:25%">
			
			
			<div align="center" class="inner-box" style="margin-left:3%;margin-top:5%">
			<span class="grid-box-header">List Of Furnishings
			<input type="button" class="download float-right" style="margin-right:5%" value="Add" id="addItem"/>
			</span>
			<table class="documents-table display">
		<thead>
			<tr>
				<th>Make</th>
				<th>Item Name</th>
				<th>Item Description</th>
				<th>Photo</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody id="tbody">
			<tr class="odd" id="noItem">
				<td colspan="5" align="center">No items available</td>
			</tr>
		</tbody>
		</table>
		</div>
		</section>
		
			
			</div>
			<!-- </div> -->
			
			
		
</form>
		<div id="saveSubmit" align="center" style="margin-top:3%;margin-bottom:2%">
					<span><input
					type="submit" class="download" id="saveFormAttr"
					value="Save" onclick="saveForm();"></span>
					<span><input
					type="submit" class="download" id="submitSaveAttr"
					value="Submit" onclick="submitForm();"></span>
				</div>
				
		<!-- <div id="attr3Div"></div> -->
<form id="additionalPhotoForm"  style="display:none" action="" enctype="multipart/form-data" method="POST" target="hiddenNewFrame">
</form>
	
	
	<div id="hiddenDivPhotoUpload">
	<iframe id="hiddenNewFrame" name="hiddenNewFrame" style="display: none">
	</iframe>
</div>

