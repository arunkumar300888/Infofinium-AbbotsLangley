<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>

function showInWindow(value1)
{
	/* alert("value1"+value1);
	alert("value2"+ value2); */
	 //alert("value1"+value1);
	var url = value1+"&TB_iframe=true&height=585&width=900";
		       /*  window.open(url, "pop", "width=400,height=800"); */
	tb_show('Details', url, null);
}


$(document).ready(function(){
	
	$('#formsList tr').hover(function() {
        $(this).css('cursor','pointer');
    });
	
	
});

function giveNoticeMetaData(property,tenancy){
	$.ajax({
		type : "GET",
		url : "forms/showGivingNotice?propertyId="+property+"&tenancyId="+tenancy ,
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
<div  >
				<span class="grid-box-header ">Giving Notice 
				<c:if test="${empty givingNoticeList}">
				<input type="button" class="download float-right" style="margin-right:5%" value="Give Notice" onclick="giveNoticeMetaData('${property}','${tenancy }')"/>
				</c:if>
				
				<c:if test="${not empty givingNoticeList  }">
				<input type="button" class="download float-right" style="margin-right:5%" value="View Notice" onclick="showInWindow('forms/viewGivingNoticeForm?tenancyId=${tenancy}');"/>
				</c:if>
				</span>
				<table id="formsList" class="documents-table display">
					<thead>
						<tr>
							<th>Tenant Name</th>
							<th>Tenant Address</th>
							
						</tr>
					</thead>
					<tbody>
						<c:forEach var="i" items="${givingNoticeList}" varStatus="status">
						<tr >
							<td>${i.tenantName}</td>
							<td>${i.address}</td>
							
						</tr>
						</c:forEach>
					</tbody>
				</table>
	</div>