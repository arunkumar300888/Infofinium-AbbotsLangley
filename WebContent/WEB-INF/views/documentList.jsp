<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	$(function() {
		$("#datepicker").datepicker();
	});
</script>


		<div id="top">
			<span class="float-left grid-box-header top3">MY DOCUMENTS >> ${taskName}</span>
		
				<div id="nav2">
					<c:if test="${canShowAll ==true}">
		<div id="loadAllDocuments" align="left">
			<a href="#" onclick="loadAllDocuments();">All Documents</a>
		</div>
	</c:if>					
					<!-- <a href="#" class="arrow">Show 50 Entries</a> -->
				</div>
		
			<a href='#' class='close-widget-box'>
    				<img src="resources/style/wizart/images/x.png" class="float-right" />
   			</a>	
			<!-- <form id="" method="" action="" class="float-right" style="height:28px">
				<input type="text" class="text" name="" size="10" maxlength="120" value="" placeholder='Search' style="vertical-align:top; margin-top:7px;">
				<input type="button" class="button" value="" class="">
			</form>	 --> 
			<div class="clear"> </div>
		</div>	
		<div class="clear"> </div>
<div id="myDocsDiv" class="my-document complete box-content">
		<div id="dt_example">
			<div  id="docList" class="tdhover">${docs}</div>
		</div>
	</div>














