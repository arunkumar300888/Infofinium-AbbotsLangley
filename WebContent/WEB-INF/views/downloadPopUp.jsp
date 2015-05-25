<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<link
	href='http://fonts.googleapis.com/css?family=Roboto:300,400,700,100'
	rel='stylesheet' type='text/css' />
<link rel="stylesheet" type="text/css"
	href="../resources/style/wizart/style.vali.css" />
<link rel="stylesheet" type="text/css"
	href="../resources/style/wizart/style.css" />


<link rel="stylesheet" type="text/css"
	href="../resources/style/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="../resources/style/demo_table_jui.css" />
<link rel="stylesheet" type="text/css"
	href="../resources/style/TableTools_JUI.css" />
<link rel="stylesheet" type="text/css"
	href="../resources/style/TableTools.css" />


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>

<script src='../resources/js/jquery-ui-1.8.20.custom.min.js'
	type="text/javascript"></script>
<script src='../resources/js/jquery.dataTables.min.js'
	type="text/javascript"></script>
<script src="../resources/js/ReferenceLinks.js" type="text/javascript"></script>

<style>
body {
	background: none;
}
</style>

<script type="text/javascript">
function downloadDoc(){
	
	//alert("Download clicked");
	
	
	$("#downForm").attr("action", "downloadDocForm");
	$("#downForm").submit();
	//window.opener.document.write("Downloading File");
	//window.close();
	
	
}
	
/* $(document).ready(function() {
	alert("func");
	$('a[href="#"]').click(function(){
		alert("click");
		  window.close(); 
		});  
}); */

$( "#submitDown" ).click(function() {
	//alert( "Handler for .click() called." );
	downloadDoc();
	
	});
	
</script>
</head>

<body onload="downloadDoc();">

	

	

<div style="width: 98%; margin: 0 auto;">
			

<form method="post" id="downForm" name="downForm" >
<input type="hidden" id="docName" name="docName" value="${documentName }">
<!-- <input type="submit" value="Download"  id="submitDown" name="submitDown"> -->

</form>

<%-- <a href="#" onclick="downloadDoc();">${documentName }</a> --%>


	</div>







</body>
</html>