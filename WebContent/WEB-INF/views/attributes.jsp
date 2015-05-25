<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../Extn/adminstyleDSJV.css" rel="StyleSheet" type="text/css">
<script type="text/javascript" src="resources/js/ReferenceLinks.js"></script>
<title>Attributes</title>
</head>
<body>
<div class="bg_title"> ${title} </div>

    <form method="POST" id="attributesFrm" name="attributesFrm">
         <table cellspacing="7">
            <tr>
              <td class="label">Attr 1</td>
              <td class="star">*</td>
              <td><input name="attr1" id="attr1" type="text" class="txt" maxlength="40" value="${attr1}" /></td>
              <td><div style="color: red;">${result}</div></td>
            </tr>
            <tr>
              <td class="label">Attr 2</td>
              <td class="star">*</td>
              <td><input name="attr2" id="attr2" type="text" class="txt"  maxlength="40" value="${attr2}" /></td>
            </tr>
            <tr>
              <td class="label">Attr 3</td>
              <td class="star">*</td>
              <td><input name="attr3" id="attr3" type="text" class="txt"  maxlength="40" value="${attr3}" /></td>
            </tr>
            <tr>
              <td class="label">Attr 4</td>
              <td class="star">*</td>
              <td><input name="attr4" id="attr4" type="text" class="txt" maxlength="40"  value="${attr4}"/></td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td><input name="" type="button" class="butn" value="Submit" onclick="attributesCreation();" />
              <input name="" type="reset" class="butn" value="Reset" />
              </td>
            </tr>
          </table>
          </form>

</body>
</html>