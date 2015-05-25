<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>

<body>
	<form id="urlBlock" name="urlBlock" method="post" action="history">

		<input type="hidden" name="caseIdFromUrlBlock" id="caseIdFromUrlBlock"  value="${caseIdForHistory}">
	</form>
</body>


<script type="text/javascript">
	document.forms["urlBlock"].submit();
</script>

</html>