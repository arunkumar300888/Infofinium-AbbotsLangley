<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		
	<jsp:include page="include/header2.jsp" />
		<section class="admin-general-page">

		<nav>
		<div class="inner-box">
			<span class="grid-box-header">User Administration</span>
			<ul>
				<c:if test="${canShowAdminAll}">
					<li><a href="#" onclick="showRoles();">Role Management</a></li>
					<li><a href="#" onclick="showGroups();">Workflow Group
							Management</a></li>
					<li><a href="#" onclick="showSecurityGroups();">Security
							Group Management</a></li>
					<li><a href="#" onclick="showFormsForSecGrp();">Form Type Security Group</a></li>
					<li><a href="#" onclick="showFormsForCompanyGrp();">Form Type Company Group</a></li>
					<li><a href="#" onclick="showFormsForModelMap();">Form Type WorkFlow Management</a></li>
					<li><a href="#" onclick="showUsers();">User Management</a></li>
					<li><a href="#" onclick="showFeatures();">Feature
							Management</a></li>
				</c:if>
				<c:if test="${canShowAdminRole}">
					<li><a href="#" onclick="showRoles();">Role Management</a></li>
				</c:if>
				<c:if test="${canShowAdminGroup}">
					<li><a href="#" onclick="showGroups();">Workflow Group
							Management</a></li>
				</c:if>
				<c:if test="${canShowAdminSecurityGroup}">
					<li><a href="#" onclick="showSecurityGroups();">Security
							Group Management</a></li>
				</c:if>
				<c:if test="${canShowAdminFormSecurity}">
					<li><a href="#" onclick="showFormsForSecGrp();">Form Type Security Group</a></li>
				</c:if>
				<c:if test="${canShowAdminUser}">
					<li><a href="#" onclick="showUsers();">User Management</a></li>
				</c:if>
				<c:if test="${canShowAdminFeature}">
					<li><a href="#" onclick="showFeatures();">Feature
							Management</a></li>
				</c:if>
			</ul>
		</div>
		<div class="inner-box">
			<span class="grid-box-header">Document Administration</span>
			<ul>
				<c:if test="${canShowAdminAll}">
					<li><a href="#" onclick="createAttributes();">Attributes</a></li>
					<li><a href="#" onclick="showAttributeValues();">Attributes
							Values</a></li>
					<li><a href="#" onclick="createDocTypes();">Doc type
							Creation</a></li>
					<li><a href="#" onclick="showModelCombo();">Workflow
							Management</a></li>
					<li><a href="#" onclick="showTemplates();">Template
							Management</a></li>
					<li><a href="#" onclick="showAllWipDocs();">WIP Documents</a></li>
				</c:if>
				<c:if test="${canShowAdminAttr}">
					<li><a href="#" onclick="createAttributes();">Attributes</a></li>
				</c:if>
				<c:if test="${canShowAdminAttrValue}">
					<li><a href="#" onclick="showAttributeValues();">Attributes
							Values</a></li>
				</c:if>
				<c:if test="${canShowAdminDocType}">
					<li><a href="#" onclick="createDocTypes();">Doc type
							Creation</a></li>
				</c:if>
				<c:if test="${canShowAdminWorkflow}">
					<li><a href="#" onclick="showModelCombo();">Workflow
							Management</a></li>
				</c:if>
				<c:if test="${canShowAdminTemplate}">
					<li><a href="#" onclick="showTemplates();">Template
							Management</a></li>
				</c:if>
				<c:if test="${canShowAdminMyDocument}">
				<li><a href="#" onclick="showAllWipDocs();">WIP Documents</a></li></c:if>
			</ul>
		</div>

		<div class="inner-box">
			<span class="grid-box-header">Workflow Administration</span>
			<ul>
				<c:if test="${canShowAdminAll}">
				<!-- <li><a href="#" onclick="showCompany();">Company Master</a></li> -->
					<li><a href="#" onclick="showDocumentsForDeletion();">Delete
							Document</a></li>
					<li><a href="#" onclick="showPickedSteps();">Unclaim
							Document</a></li>
					<li><a href="#" onclick="showAssignedUserTasks();">Reassign
							Task</a></li>
					<li><a href="#" onclick="showReassignOwnerDocs();">Reassign
							Document Owner</a></li>
				     			
				</c:if>
				<c:if test="${canShowAdminDelete}">
					<li><a href="#" onclick="showDocumentsForDeletion();">Delete
							Document</a></li>
				</c:if>
				<c:if test="${canShowAdminUnclaim}">
					<li><a href="#" onclick="showPickedSteps();">Unclaim
							Document</a></li>
				</c:if>
				<c:if test="${canShowAdminAssignTask}">
					<li><a href="#" onclick="showAssignedUserTasks();">Reassign
							Task</a></li>
				</c:if>
				<c:if test="${canShowAdminReassignOwner}">
					<li><a href="#" onclick="showReassignOwnerDocs();">Reassign
							Document Owner</a></li>
				</c:if>

			</ul>
		</div>
</nav>
		<section>

			<div class="inner-box">				

				<div id="dynamicContent">
				
					<span class="grid-box-header">Welcome ${title}</span>	
				</div>
				
				<div id="showDynamicContent"></div>
						
			</div>

		</section>
</section>

	<div class="clear"></div>
	<jsp:include page="include/footer.jsp" />

