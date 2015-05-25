<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Form Definition</title>
<script>
$("#scheduledTime").datepicker({
	dateFormat : 'dd-mm-yy',
		 showOn : "button",
		buttonImage : "resources/images/calendar.gif",
		minDate: 0,
		buttonImageOnly : true 
/* 				 onClose : function() {

		this.focus();
	}  */
});

$("#scheduledTime").click(function(){
    $('input:[dtpk=true]').datepicker('show');
});

</script>
</head>
<body>
<div class="bg_title"> Form Definition </div>
    <form method="post" id="createFormDefinition" name="createFormDefinition">
     <table cellspacing="7">
      <tr>
            <td class="label">Request for</td>
            <td></td>
            <td>
              <input type="radio" name="diffValue" id="diffEValue" value="0" checked="checked"> <span>Event</span>
              <input type="radio" name="diffValue" id="diffFValue" value="1"> <span>Function</span>
              <input type="radio" name="diffValue" id="diffMValue" value="2"> <span>Meeting</span>
              
            </td>
            </tr>
            <tr>
              <td class="label">Title</td>
              <td class="star">*</td>
              <td><input name="title" id="title" type="text" class="txt"  maxlength="99" " /></td>
               <td><div style="color: red;" align="right">${result}
	</div></td>
            </tr>
            <tr>
              <td class="label">Scheduled Time</td>
              <td class="star">*</td>
              <td><input name="scheduledTime" id="scheduledTime" type="text" class="txt"  maxlength="99"/>  
             </td>
            </tr>
            <tr>
              <td class="label">Staff in Charge</td>
              <td class="star">*</td>
              <td><input name="inCharge" id="inCharge" maxlength="6" type="text" class="txt" /></td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td><input type="button" class="butn" value="Save"  onclick=";"/>
              <input name="" type="reset" class="butn" value="Submit" onclick="formDataSubmit();" />
              <input name="" type="button" class="butn" value="Cancel" onclick=";" />
              </td>
             
            </tr>
          </table>
     </form>
    
					
<script>
					 
$(function() {	
	var isWorkflow='<c:out value="${isWorkflow}"/>';
	if(isWorkflow=='true'){
		//alert(isWorkflow);
		document.getElementById("diffCValue").checked=true;
	/* $('#diffValue').val("1"); */
	
	}

});
					 

</script>

</body>
</html>