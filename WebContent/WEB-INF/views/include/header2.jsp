<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header id="header1">				
				<span class="logo float-left">
					<img src="resources/images/wizart/logo.png"/>
				</span>			
				<span class="menu2 float-right">
					<ul>
						<li class="clear">
							<a href="#" class="notunderline">Welcome <span style="text-transform: uppercase">${title}</span></a>
						</li>
						<li>
						   <form id="adminLogoutFrm" method="get">
							<a href="#" onclick="adminLogoutClicked();" id="btnLogout">Logout</a>
							 <img src="resources/images/wizart/play.png" />
							</form>
						</li>
						<li>   <form id="adminBack" method="get">
								<a href="#" onclick="adminBackBtnClicked();">
									Home
								</a>
							 <img src="resources/images/wizart/home.png" /></form>
						</li>
						
					</ul>					
					<span class="menu3 float-right">
					<p class="strong font12px">INFORMATION GOVERNANCE FRAMEWORK</p>
				</span></span>		
				<div class="clear"></div>
			</header>