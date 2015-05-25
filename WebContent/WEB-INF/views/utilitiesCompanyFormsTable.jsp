<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
$(document).ready(function(){
	
	$('#formsList tr').hover(function() {
        $(this).css('cursor','pointer');
    });
	
	$('#formsList').dataTable( {
        "bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"iDisplayLength" : 25,
		"bFilter" : true

    } );
});

function utilitiesMetaData(id){
	$.ajax({
		type : "GET",
		url : "forms/showUtilitiesCompanyDetails" + "?utilitiesCompanyId="
				+ id,
		cache : false,
		success : function(data) {
			 $("#search").hide();	
			//Next 3 lines for properties page to Dashboard Page
			 $('#csvDownloadSearch').hide();
			$('#dynContentMetadata').hide();
			
			$("#header2").show();				
			$("#dynContent").html(data);
			$('#dynContent').show();
			$("#header3").show();
			
		}
	});
}
</script>
<div >
	<span class="grid-box-header ">Utilities Company Details 
	<input type="button" class="download float-right" style="margin-right: 5%"  onclick="utilitiesMetaData('-1');" value="Add New" />
	</span>
	<table id="formsList" class="display">
		<thead>
			<tr>
				<th>Company Name</th>
				<th>Company Type</th>
				<th>Main Contact Name</th>
				<th>Main Contact Phone</th>
				<th>Main Contact Email Address</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${utilitiesCompanyList}" varStatus="status">
						<tr onclick="utilitiesMetaData('${i.id}');">
				<td>${i.companyName}</td>
				<td>${i.companyType}</td>
				<td>${i.mainContactName}</td>
				<td>${i.mainContactTelephone}</td>
				<td>${i.mainContactEmailAddress}</td>
				
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>