<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bg_title">

					<span class="grid-box-header">${title}		</span>	
	
</div>
<table id='showTemplateAssignmentLists' class='documents-table display'>
		<thead>
			<tr>
			    <th>Template Name</th>
			    <th>${attr1Title}</th>
			    <th>${attr2Title}</th>
			    <th>${attr3Title}</th>
			    <%-- <th>${attr4Title}</th>
			    <th>${attr5Title}</th> --%>
			     <th>Doctype</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody><tr>
						<td id="name">${templateName}</td>
						<td id="attrValue1">${company}</td>
						<td id="attrValue1">${project}</td>
						<td id="attrValue2">${category}</td>
						<%-- <td id="attrValue3">${discipline}</td>
						<td id="attrValue4">${site}</td> --%>
						<td id="doctype">${doctype}</td>
						<td id="deleteTemplateAssignment"><a href="#" onclick="deleteModeTemplateAssignmentClicked('${templateId}');">Delete</a></td>
					</tr>
		</tbody>
	</table>
	
	<div id="goBackTotemplates" align="right">
									<input type="button" class="download"
										style="margin-bottom: 4px; width: 80px;" value="Back"
										onclick="showTemplates();" />
										</div>	