<div id="enterReasonDialog"  title="Document Deletion" class="main">
       <div title='Document Deletion'><p><span class='ui-icon ui-icon-alert' style='float: left; margin: 0 7px 20px 0;'> 
										</span><b>Why are you deleting this document?</b></p></div>
										<br />
		<div >
			<div align="left"  class="value controlspan">	
			<form id="deleteReasonFrm" method="post">
			<input type="hidden" name="documentId" id="documentId" value="${documentId}">
			<textarea cols="50" class="text" rows="5" id="reason" maxlength="500"
								name="reason" style="resize: none;"></textarea>
								</form>
							<br /> 
			</div>
			</div>
	</div>