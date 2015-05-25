<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
$(function() {
	$("#datepicker").datepicker();
});
</script>

 <div class="title">My Documents <img alt="" src="resources/images/forward_disabled.png" /> ${taskName}</div>
  <div class="inden">
                    
        <c:if test="${canShowAll ==true}">
	
	<div id="loadAllDocuments" align="left">
									<input type="button" class="butn"
										style="margin-bottom: 5px; width: 120px;" value="All Documents"
										onclick="loadAllDocuments();" />
										</div>
		</c:if>             
<div id="myDocsDiv">
	<div id="dt_example">
		<div class="tdhover" id="docList">${docs}</div>
	</div>
	
	</div>
	
	
</div>

