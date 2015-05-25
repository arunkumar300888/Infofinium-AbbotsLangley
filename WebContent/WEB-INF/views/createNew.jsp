<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <div class="title">Create New Case</div> -->

<style>
h6.expand_heading {
	padding: 0px;
	margin: 0px;
	 background: url(resources/images/expand_collapse1.png) no-repeat; 
	/* background: url(resources/images/download_btn.png) no-repeat; */
	 height: 38px;
	line-height: 38px;
	width: 170px;
	font-size: 2em;
	font-weight: normal;
	text-align: left;
	text-indent: 20px;

		
}

.expand_all {
	cursor: default;
}

h6.expand_heading a {
	color: #fff;
	text-decoration: none;
    display: block;
}


h6.expand_heading a:hover {
	color: #ccc;
}

h6.active {
	background-position: left bottom;
}

.toggle_container {


} 

</style>


<script type="text/javascript">
	$(document).ready(function() {
	    $(".toggle_container").hide();
		$("h6.expand_heading").toggle(function() {
			$(this).addClass("active");
		}, function() {
			$(this).removeClass("active");
		});
		$("h6.expand_heading").click(function() {
			$(this).next(".toggle_container").slideToggle("slow");
		});
	 
	});
</script>


<div id="header1" class="title">Download Template</div>
<div id="header2" class="title">Upload Document</div>
<div id="crNewDiv">
	<div id="createDiv">
		<table cellspacing="8" style="padding-left: 90px;">

			<tr valign="top">
				<!-- <td class="value">Select Model</td> -->
				<td class="value">Select Site</td>
				<td><select name="modelForCase" id="modelForCase"
					style="width: 190px;">
						<c:forEach var="i" items="${models}">
							<option value="${i.id}">${i.name}</option>
						</c:forEach>
				</select></td>
			</tr>

			<!--  <tr valign="top">
					<td class="value">Select Category</td>
					<td><select name="category" id="category" style="width: 190px;" onChange="checkCategory(this.value)" >
							<option value="1">Contractual</option>
                            <option value="2">Internal</option>
					</select></td>
				</tr>
				 -->
			<tr valign="top">
				<td class="value">Select Discipline</td>
				<td><select style="width: 190px;" name="discipline"
					id="discipline">

						<c:forEach var="i" items="${disciplines}">
							<option value="${i.id}">${i.disciplineName}</option>
						</c:forEach>
				</select></td>
				<!-- <td><select name="discipline" id="discipline" style="width: 190px;"
					onChange="">
						<option value="1">Contract Administration</option>
						<option value="2">Environmental</option>
						<option value="3">Construction Process</option>

				</select></td> -->
			</tr>


			<tr valign="top">
				<td class="value">Select Document Type</td>
				<td><select style="width: 190px;" name="documentType"
					id="documentType">
						<c:forEach var="i" items="${doctypes}">
							<option value="${i.id}">${i.doctypeName}</option>
						</c:forEach>
				</select></td>
				<!-- <td><select name="discipline" id="discipline" style="width: 190px;">
						<option value="1">Method Statement</option>
				</select></td> -->


			</tr>

		</table>

		<!-- <table>
			<tr valign="top">
				<td align="left"><h3 style="color: white;">Download
						Template here</h3>
				<td><input style="padding-right: 15px;" class="showattribute"
					type="button" value="Templates" onclick="listTemplatesClicked();"></td>
			</tr>

		</table> -->

		<!-- <div id="listTemplatesDiv" align="center"></div> -->


	<!-- 	<div id="uploadDocument">
			<table cellspacing="8">
				<tr>
					<td><h3 style="color: white;">Upload your Document</h3></td>
					<td><input class="showattribute" type="button"
						value="Upload Document" onclick="createCaseClicked();"></td>
				</tr>
			</table>
		</div> -->
		
		<div id="expandcollapse" align="center">

		<h6 class="expand_heading"><a href="#" onclick="listTemplatesClicked();">Templates</a></h6>
		
			<div class="toggle_container">
				<div id="listTemplatesDiv" align="center"></div>			
				</div>
</div>
		
		
		

	</div>

	<div id="attrFormDiv"></div>
</div>


	





