<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../Extn/adminstyleDSJV.css" rel="StyleSheet" type="text/css">
<script type="text/javascript" src="resources/js/ReferenceLinks.js"></script>
<title>Document Types</title>
</head>
<body>

<div class="bg_title"> <span class="grid-box-header">${title}</span> </div>
    <form method="post" id="createDocTypesFrm" name="createDocTypesFrm">
    <input type="hidden" id="docTypeID" name="docTypeID" value="${docTypeId}">
     <table cellspacing="7">
            <tr>
              <td class="label">Doc Type</td>
              <td class="star">*</td>
              <td><input name="docType" id="docType" type="text" class="text"  maxlength="99" value="${docTypeName}" /></td>
               <td><div style="color: red;" align="right">${result}
	</div></td>
            </tr>
            <tr>
              <td class="label">Description</td>
              <td class="star">*</td>
              <td><input name="description" id="description" type="text" class="text"  maxlength="99" value="${description}" />  
              
             <!--  <br />
              <input name="isWorkflow" id="isWorkflow" type="checkbox" value="1" /><span>isWorkFlow</span> -->
             </td>
            </tr>
            
            <tr>
            <td class="label">Choose</td>
            <td></td>
            <td>
              <input type="radio" name="diffValue" class="radio" id="diffQValue" value="0" checked="checked"> <label for="diffQValue" class="radio">Quick</label>
              <input type="radio" name="diffValue" id="diffCValue" value="1"> <label for="diffCValue" class="radio">Controlled</label>
            </td>
            </tr>
             <tr>
            <td class="label">Version</td>
            <td></td>
            <td>
             <input type="radio" name="verValue" class="radio" id="verNoValue" value="No" checked="checked"> <label for="verNoValue" class="radio">Final Versions</label>
              <input type="radio" name="verValue" class="radio" id="verMjValue" value="Mj" > <label for="verMjValue" class="radio">All Versions</label>
              <!-- <input type="radio" name="verValue" id="verMiValue" value="Mi"> <label for="verMiValue" class="radio">Minor Version</label> -->
            </td>
            </tr>

            <tr>
              <td class="label">Abbreviation</td>
              <td class="star">*</td>
              <td><input name="abbreviation" id="abbreviation" value="${abbreviation}"  maxlength="6" type="text" class="text" /></td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td><input type="button" class="butn" value="Save"  onclick="docTypesCreation();"/>
              <c:if test="${docTypeId==-1}"> 
              <input name="" type="reset" class="butn" value="Reset" />
               </c:if>
                <input name="" type="button" class="butn" value="Back" onclick="createDocTypes();" />
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

$(function() {	
	var isVersion='<c:out value="${isVersion}"/>';
	if(isVersion=='Mj'){
		//alert(isWorkflow);
		document.getElementById("verMjValue").checked=true;
	/* $('#diffValue').val("1"); */
	
	}else if(isVersion=='Mi'){
		document.getElementById("verMiValue").checked=true;
	}

});
					 

</script>

</body>
</html>