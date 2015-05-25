<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
//<![CDATA[           
           $('#_pd_data_entry').on('keypress', function (event) {
               if(event.which == '13'){
                //  alert("inside popuplist");
                   return false;
               }
         });
    
	function _popupDialogContentOk(ajaxControl)
	{
		var dest = document.getElementById(ajaxControl);
		var lb = document.getElementById("_pd_data_listbox");
		var ef = document.getElementById("_pd_data_entry");
		try
		{
			var s = null;
			if (ef != null)
			{
				s = trim(ef.value);
			}

			if (s == null)
			{
				if (lb.selectedIndex != -1 && lb.type == "select-one")
				{
					s = lb.options[lb.selectedIndex].value;
				}
			}

			if (dest.type == "select-one" || dest.type == "select-multiple")
			{
				if(s != null)
				{
					_popupDialogAddOption(dest, s.split("[#")[0]);
				}
				else
				{
					var j = 0;
					for(j=0; j<lb.options.length; j++)
					{
						if(lb.options[j].selected)
						{
							_popupDialogAddOption(dest, lb.options[j].value.split("[#")[0]);
						}
					}
				}
			}
			else if (s != null && (dest.type == "text" || dest.type == "password"))
			{
					dest.value = s;
			}
		}
		catch (e)
		{
			alert("Exception " + e);
		}
	}

	function _popupDialogAddOption(dest, value)
	{
		var s = value.split("[#")[0].trim().replace(/\s+/g, "_");
		var option = dest.jmrDomExt.extraData[s];
		if(option == null)
		{
			option = new  Option();
			dest.jmrDomExt.extraData[s] = dest.options[dest.options.length] = option;
		}

		option.text = option.value = value;
	}

	function _popupDialogContentInit(ajaxControl)
	{
		var elem = document.getElementById(ajaxControl);
		if (elem.type == "select-one" || elem.type == "select-multiple")
		{
			elem = document.getElementById("_pd_data_listbox");
			elem.multiple = "multiple";
		}

		return;
	}
	
	

//]]>
						
							
	function docDownloadClicked() {
		var nPath=$('#_pd_data_listbox option:selected').attr('docDow');
		var uId=$('#_pd_data_listbox option:selected').attr('pdfDow');
		/* alert(nPath);
		alert(uId); */
		if(nPath!=null){
		
		nPath=nPath.split(',');
		path=nPath[0];
		documentName=nPath[1];
		docId=nPath[2];
		stepList=nPath[3];
		
		var url = "forms/showDocDownload" + "?path=" + path + "&documentName="
			+ documentName +"&documentId="+docId+  "&stepId="
			+ stepList;
		var newwindow = window
		.open(url,'Document','height=600,width=1000,left=100,top=50,resizable=yes,location=no,scrollbars=yes,toolbar=yes,status=yes');
		if (window.focus) {
			newwindow.focus();
			// newwindow.location.href=aurl;
			}
			return false;
		
		 /* window.open("forms/showDocDownload" + "?path=" + path + "&documentName="
					+ documentName +"&documentId="+docId+  "&stepId="
					+ stepList, "height=600,width=1000,left=100,top=50,resizable=yes,location=no,scrollbars=yes,toolbar=yes,status=yes"); */
		}else if(uId!=null){
			
			window.open("forms/showStaticForm?userFormId=" + uId, "userform_"
					+ uId, "status=1,width=500,height=500,scrollbars=1");
		}
	}
		
	
	function pdfDownloadFormClicked(userFormId){
		//alert("coming into download");
		$.ajax({
			type : "Post",
			url : "forms/pdfDownloadForm" + "?userFormId=" + userFormId ,
			cache : false,
			data: {'submit':true},
			  success : function(data) {
				  alert("Download Completed"); 
			}  
		});
	}
</script>
<p>
	<select size="5" class="m_select_text" style="width: 100%;" id="_pd_data_listbox" name="_pd_data_listbox">
		<c:forEach var="i" items="${docs}">
		<c:set var="discriminator" scope="page">${i.discriminator}</c:set>
		<c:choose>
					<c:when test="${discriminator eq 'D'}">
		
			<option value="${i.name}" docDow="${i.filePath},${i.name},${i.id},0">${i.name}</option>
			
			</c:when>
			<c:otherwise>
			<%-- <form method="post" id="pdfDownloadForm" name="pdfDownloadFrm">
			<input type="hidden" name="userFormId" value="${i.userFormId }" id="userFormId" />
			</form> --%>
			<option value="${i.name}" pdfDow="${i.userFormId }">${i.name} </option>
					
			</c:otherwise>
			</c:choose>
		</c:forEach>		
	</select>
	<input type="button" class="butn" onclick="docDownloadClicked();" value="Open">
</p>
