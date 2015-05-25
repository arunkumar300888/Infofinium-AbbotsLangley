<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
		<div class="list-box">
		
			<ul>
			  <c:forEach var="i" items="${tasks}">
            <c:choose>
                <c:when test="${i.taskName=='My Documents'}">
				<li class="one button-action-trays">
					<a href="#" class="button1 float-left" onclick="trayClicked('${i.taskName}');">
						<div class='float-left button-zeros'>
							${i.taskCount}
						</div>
						<div class='button-text'>
							${i.taskName}
						</div>
					</a>			
				</li>  
				</c:when>
				<c:otherwise> 					
				
				<li class="two button-action-trays">
					<a href="#" class="button2 float-left" onclick="trayClicked('${i.taskName}');">
						<div class='float-left button-zeros'>
							${i.taskCount}
						</div>
						<div class='button-text'>
							${i.taskName}
						</div>
					</a>
				</li>
				
                </c:otherwise>
                </c:choose>
                </c:forEach>
               	
				<li class="three button-action-trays">
				<c:forEach var="entry" items="${draftTray}">
					<a href="#" class="button3 float-left" onclick="trayDraftClicked();">
						<div class='float-left button-zeros'>
							${entry.value}
						</div>
						<div class='button-text'>
							${entry.key}
						</div>
					</a>
					</c:forEach>
				</li>				
			</ul>
		</div>
		
		


