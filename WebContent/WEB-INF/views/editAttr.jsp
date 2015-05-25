<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../Extn/adminstyleDSJV.css" rel="StyleSheet" type="text/css">
<script type="text/javascript" src="resources/js/ReferenceLinks.js"></script>
<script type="text/javascript">
$(document).keypress(
	    function(event){
	     if (event.which == '13') {
	        event.preventDefault();
	     }

	});
</script>
<title>Edit Attributes</title>
</head>
<body>
<div class="bg_title"><span class="grid-box-header"> ${title} </span></div>

    <form method="POST" id="editAttributesFrm" name="editAttributesFrm">
      <input type="hidden" id="attrId" name="attrId" value="${attrId}">
         <table cellspacing="7">
            <tr>
              <td class="label">Enter the Attr</td>
              <td class="star">*</td>
              <td><input name="attr" id="attr" value="${attr}" type="text"  maxlength="40" class="text" /></td>
              <td><div style="color: red;">${result}</div></td>
            </tr>           
            <tr>
              <td></td>
              <td></td>
              <td><input name="" type="button" class="download" value="Save" onclick="attributeUpdate();" />
               <input name="" type="button" class="download" value="Back" onclick="createAttributes();" />
              </td>
             
            </tr>
          </table>
          </form>

</body>
</html>