<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../Extn/adminstyleDSJV.css" rel="StyleSheet" type="text/css">
<script type="text/javascript" src="resources/js/ReferenceLinks.js"></script>
<title>Attribute Values</title>
</head>
<body>

<div class="bg_title"><span class="grid-box-header"> ${title} </span></div>

    <form method="post" id="attributeValuesFrm" name="attributeValuesFrm" >
      <input type="hidden" id="attrValueId" name="attrValueId" value="${attrValueId}">
         <table cellspacing="7">
            <tr>
              <td class="label">Select Attribute</td>
              <td class="star">*</td>
              <td><select id="attr" name="attr" class="text" >
              <option value="-1" selected="selected">--Select--</option>
              	<c:forEach var="i" items="${attr}">
							<option title="${i.name}" value="${i.id}">${i.name}</option>
						</c:forEach>
              </select></td>
              <td><div style="color: red;">${result}</div></td>
            </tr>
            <tr>
              <td class="label">Attr Value</td>
              <td class="star">*</td>
              <td><input id="attrValue" name="attrValue" value="${attrValue}"  maxlength="40"  type="text" class="text" /></td>
            </tr>
            <tr>
              <td class="label">Abbreviation</td>
              <td class="star">*</td>
              <td><input id="abbreviation" name="abbreviation" value="${abbreviation}" maxlength="5" type="text" class="text" /></td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td><input name="" type="button" class="butn" value="Create" onclick="attributeValuesCreation();"/>
              <input name="" type="reset" class="butn" value="Reset" />
               <input name="" type="button" class="butn" value="Back" onclick="showAttributeValues();" />
              </td>
            </tr>
          </table>
          </form>

</body>
</html>