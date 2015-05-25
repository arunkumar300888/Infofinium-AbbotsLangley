<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Test</title>
		<link rel="stylesheet" type="text/css" href="form-styles.css" />
		<script type="text/javascript">				
			function cbChanged(cb)
			{
				alert(1);
				/*
				var item = cb.name + "." + cb.value;
				var cResource = document.getElementById(item);
				
				if(cb.checked)
				{
					cResource.style.display = "block";
				}
				else
				{
					cResource.style.display = "none";
				}
				*/
			}
			function rbChanged(rb)
			{
				alert(rb.value);
			}

		</script>		
	</head>

	<body style="font-size:14px;">
<div class="bg_title"> Form Definition </div>
<div class="content">
<form action="/forms/create" method="post" id="FRM001">
${formDefinition}
</form>
</div>
</body>
</html>