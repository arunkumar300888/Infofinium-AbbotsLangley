<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../Extn/adminstyleDSJV.css" rel="StyleSheet" type="text/css">
<script type="text/javascript" src="resources/js/ReferenceLinks.js"></script>
<!-- <script src="resources/js/JmrSdp.js" type="text/javascript"></script> -->
<title>User Creation</title>
</head>
<body>
<div class="bg_title">

					<span class="grid-box-header">${title}	</span>	
	
</div>
		<form method="post" id="createCompanyFrm" name="createCompanyFrm">
 <table cellspacing="7">
            <tr>
              <td class="label">Company Name</td>
              <td class="star">*</td>
              <td><input id="companyName" name="companyName" type="text"  maxlength="44"  class="text" style="width: 196px;"/></td>
              <td><div id="result" style="color: red;">${result}
				</div>
              </td>
            </tr>
            
            <tr>
              <td class="label">Abbreviation</td>
              <td class="star">*</td>
              <td><input id="abbreviation" name="abbreviation" type="text"  maxlength="5"  class="text" style="width: 196px;"/></td>
            </tr>
            
            <tr>
              <td></td>
              <td></td>
              <td><input type="button" class="download" value="Create"  onclick="createCompany();"/> <input type="reset" class="download" value="Reset" />
               <input type="button" class="download" value="Back" onclick="showCompany();"/>
              </td>
            </tr>
          </table>
          </form>
</body>
</html>