<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header id="header1">				
				<span class="logo float-left">
					<img src="resources/images/wizart/logo.png"/>
				</span>
				<span class="main-menu">
				
							
					<span class="menu1 float-left">						
						<ul>
							<li id="li_home">
								<a href="#" onclick="homeClicked();">
									<img  src="resources/images/wizart/house.png" />
								</a>
							</li>		 <c:if test="${canShow ==true}">					
							<c:if test="${canShowDownloadTemplate ==true}">
							<li id="li_download">
								<a href="#"  onclick="downloadTemplate();" >
									<img src="resources/images/wizart/down.png" />
								</a>
							</li>
							</c:if>
							<li  class="dropdown-action">
							<a href="#" onclick="uploadDocument();">
							<img src="resources/images/wizart/up.png" />
							</a>
							
							</li>
							
							
							
							<%-- <c:if test="${canShowForm ==true}">
							<li class="dropdown-action">
							 <a href="#">
							<img src="resources/images/wizart/book.png" />
							</a>
							<ul class="dropdown-menu" style="width: 150px;">
							
											<c:set var="active" scope="page">${i.active}</c:set>
							<li id="li_forms">
							<form method="get"> 
							<a href="#" onclick="propertyFormSelected();">
								Property Details
								</a>
								
							
								</form>
								
							</li>	
							<li id="li_forms">
							<form method="get"> 
							<a href="#" onclick="builderDetailsFormSelected();">
								Builder Details
								</a>
								</form>
								</li>
								<li id="li_forms">
							<form method="get"> 
							<a href="#" onclick="inventoryFormSelected();">
								Inventory
								</a>
								</form>
								</li>
								<li id="li_forms">
							<form method="get"> 
							<a href="#" onclick="potentialTenantFormSelected();">
								Potential Tenant
								</a>
								</form>
								</li>
								
								<li id="li_forms">
							<form method="get"> 
							<a href="#" onclick="utilitiesCompanyFormSelected();">
								Utilities Company Details
								</a>
								</form>
								</li>
								
								<li id="li_forms">
							<form method="get"> 
							<a href="#" onclick="checkingOutFormSelected();">
								Checking Out
								</a>
								</form>
								</li>
								<li id="li_forms">
							<form method="get"> 
							<a href="#" onclick="tenancyFormSelected();">
								Tenancy
								</a>
								</form>
								</li>
						
							</ul>
							</c:if> --%>
							</c:if>												
						</ul>
						
					</span>
					
					
				
					<span class="search float-left" id="li_search"  style="">	
						<a href='#' id='show-search-widget' onclick="showSearch();">SEARCH <i class='search-icon'></i></a>		
					</span>
					
					
				</span>
				
				
				
				
				
				<span class="menu3 float-right">
				
					<ul class="float-right" id="rightlinks">
					
						<li>
						<c:if test="${canShowAdminMain ==true}">
							<div class="hover">
								<img src="resources/images/wizart/head_icon.png" />
								<ul class='show-on-a-hover' id='meniu-admin'>
								    
									<li><a href="#" onclick="adminTemplate();">ADMIN PANEL</a></li>
									<!-- <li><a href="#" onclick="saveActiveBoxes()">SAVE HOMEPAGE CONFIGURATION</a></li>
									<li><a href="#" onclick="resetWidgets()">RESET HOMEPAGE CONFIGURATION</a></li>
									<li><a href="#" onclick="toggleLayoutLock()" id='toggleLayoutLock'>LOCK LAYOUT</a></li> -->
								</ul>
							</div>
							</c:if>
						</li> 
						
						<li>
							<a href="#" onclick="showUsefulInfoForTenants();">
								<img src="resources/images/wizart/info.png" />								
							</a>
						</li> 
						
						<li>
							<a href="#" onclick="helpIconClicked();">
								<img src="resources/images/wizart/question.png" />								
							</a>
						</li>
						
						<!-- <li>
							<a href="#" class="with-hover">
								<img src="resources/images/wizart/view.png" />
								<ul class='show-on-a-hover' id='view-widget-checkbox-container'>
									<li><input type='checkbox' id='view-action-trays' name='visibleWidgets' value='view-action-trays'><label for='view-action-trays'>ACTION TRAYS</label></li>
									<li><input type='checkbox' id='view-search'  name='visibleWidgets' value='view-search'><label for='view-search'>SEARCH</label></li>
									<li><input type='checkbox' id='view-my-documents' name='visibleWidgets'  value='view-my-documents'><label for='view-my-documents'>MY DOCUMENTS</label></li>
									<li><input type='checkbox' id='view-last-downloads' name='visibleWidgets'  value='view-last-downloads'><label for='view-last-downloads'>LAST DOWNLOADED DOCUMENTS</label></li>
									<li><input type='checkbox' id='view-last-actioned'  name='visibleWidgets' value='view-last-actioned'><label for='view-last-actioned'>LAST ACTIONED DOCUMENT</label></li>
									<li><input type='checkbox' id='view-downloaded'  name='visibleWidgets' value='view-downloaded'><label for='view-downloaded'>DOWNLOAD</label></li>
									<li><input type='checkbox' id='view-upload'  name='visibleWidgets' value='view-upload'><label for='view-upload'>UPLOAD</label></li>
									<li><input type='checkbox' id='view-new-form'  name='visibleWidgets' value='view-new-form'><label for='view-new-form'>NEW FORM</label></li>
								</ul>
							</a>
						</li> -->
					</ul>
					<div class="clear"></div>
					<p class="strong font12px">INFORMATION GOVERNANCE FRAMEWORK</p>
					
				</span>
				
							
				<span class="menu2 float-right">
					<ul>
					
						<li class="clear">
							<a href="#" class="notunderline">Welcome <span style="text-transform: uppercase">${username}</span></a>
						</li>
						
						<li>
						   <form id="logoutFrm" method="get">
							<a href="#" onclick="logoutClicked();" id="btnLogout">Logout</a>
							 <img src="resources/images/wizart/play.png" />
							</form>
							
						</li>
						
					</ul>	
									
				</span>	
				
				</header>
						
				<div class="clear"></div>
				
					<c:if test="${canShow ==true}">
				 <c:if test="${canShowForm ==true}">
				
							<span class="search float-left" style="margin-left:0.4%">
							<a href="#" class="prop-menu" onclick="propertyFormSelected();">
								Property Details
								</a>
								</span>
							<%-- <c:if test="${active eq 'y'}">
							<a href="#" onclick="formDefinitionClicked('${i.id}');">
								${i.description}
								</a>
								</c:if> --%>
								
								
				
				
								<ul>
									<li class="search float-left dropdown-action">
										<a href="#" class="prop-menu" onclick="">  
 											Tenancy</a>
 										<ul class="dropdown-menu">
 										<li onclick="tenancyFormSelected()">
 											Tenancy Details
 										</li>
 										<li onclick="potentialTenantFormSelected()">
 											Potential Tenants
 										</li>
 								</ul> 
 								  
				</li>
				</ul>
								<span class="search float-left">
							<a href="#" class="prop-menu" onclick="inventoryFormSelected();">
								Inventory
								</a>
								</span>
								
							<span class="search float-left">
							<a href="#" class="prop-menu" onclick="builderDetailsFormSelected();">
								Builder Details
								</a>
								</span>	
								
								
								
								
								<span class="search float-left">
							<a href="#" class="prop-menu" onclick="utilitiesCompanyFormSelected();" >
								Utilities Company Details
								</a>
								</span>
								<span class="search float-left">
							<a href="#" class="prop-menu" onclick="checkingOutFormSelected();" >
								Checking Out
								</a>
								</span>
								
								<span class="search float-left">
							<a href="#" class="prop-menu" onclick="newJobFormSelected();">
								New Job 
								</a>
								</span>	
								
								</c:if>
								</c:if>
								
								<c:if test="${isTenant eq true}">
								<span class="search float-left">
							<a href="#" class="prop-menu" onclick="rentReceiptFormSelected();" >
								Rent Receipt
								</a>
								</span>
								<span class="search float-left">
							<a href="#" class="prop-menu" onclick="givingNoticeFormSelected();">
								Giving Notice
								</a>
								</span>
								
								<span class="search float-left">
							<a href="#" class="prop-menu" onclick="newJobFormSelected();">
								New Job 
								</a>
								</span>	
								
								</c:if>
				
								<c:if test="${isBuilder eq true}">
									
								<span class="search float-left">
								<a href="#" class="prop-menu" onclick="newJobFormSelected();">
								New Job
								</a>
								</span>	
							
								</c:if>
				<div class="clear"></div>
			