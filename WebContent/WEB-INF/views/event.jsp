<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Test</title>
		<link rel="stylesheet" type="text/css" href="form-styles.css" />
		<script type="text/javascript">				
			function cbChanged(cb)
			{
				alert(1);
				/*
				var item = cb.name + "." + cb.value;
				var cResource = document.getElementById(item);
				
				if(cb.checked)
				{
					cResource.style.display = "block";
				}
				else
				{
					cResource.style.display = "none";
				}
				*/
			}
			function rbChanged(rb)
			{
				alert(rb.value);
			}

		</script>		
	</head>

	<body style="font-size:14px;">
	 <c:if test="${(submitVisible=='add')}">
<div class="bg_title"> Form Definition </div>
</c:if>
		<div class="content">
<form action="/forms/create" method="post" id="FRM001">
    <div id="FORM_Container">
        <div id="FORM_Form">
            <p style="margin:0px;font-size:large;text-align:center;">Event &amp; Function &amp; Meeting Form</p>
            <div id="FRM001_tab_0" style="display:block;">
                <!--Tab : FRM001_tab_0 -->
                <fieldset class="Form" id="f_1" style="border:none;">
                    <p>
                        <label class="FormFieldLabel" for="f_1_1r0" title="Request for">Request for&nbsp;:&nbsp;</label>
                        <input checked="checked" id="f_1_1r0" name="f_1_1" type="radio" value="Event"/>Event
						<input id="f_1_1r1" name="f_1_1" type="radio" value="Function"/>Function
						<input id="f_1_1r2" name="f_1_1" type="radio" value="Meeting"/>Meeting</p>
                    <p>
                        <label class="FormFieldLabelRequired" for="f_1_2" title="Title">Title&nbsp;:&nbsp;</label>
                        <input class="FormInputText" id="f_1_2" name="f_1_2" type="text"/>
                    </p>
                    <p>
                        <label class="FormFieldLabelRequired" for="f_1_3" title="Start">Start&nbsp;:&nbsp;</label>
                        <input class="FormInputTsAjax" id="f_1_3" name="f_1_3" readonly="readonly" type="text"/>
						<img alt="calendar" src="resources/images/forms/calendar.png" class="actionIcon" />
                    </p>
                    <p>
                        <label class="FormFieldLabelRequired" for="f_1_4" title="End">End&nbsp;:&nbsp;</label>
                        <input class="FormInputTsAjax" id="f_1_4" name="f_1_4" readonly="readonly" type="text"/>
						<img alt="calendar" src="resources/images/forms/calendar.png" class="actionIcon" />
                    </p>
                    <p>
                        <label class="FormFieldLabelRequired" for="f_1_5" title="Staff in Charge">Staff in Charge&nbsp;:&nbsp;</label>
                        <input class="FormInputTextAjax" id="f_1_5" name="f_1_5" readonly="readonly" type="text"/>
						<img alt="find" src="resources/images/forms/find.png" class="actionIcon" />
                    </p>
                    <p>
                        <label class="FormFieldLabel" for="f_1_6" title="Required">Staff Required&nbsp;:&nbsp;</label>
                        <input checked="checked" id="f_1_6" name="f_1_6" onchange="cbChanged(this);" type="checkbox" value="Required"/>
					</p>
                    <p>
                        <label class="FormFieldLabelRequired" for="f_1_7" title="Staff assisting">Staff assisting&nbsp;:&nbsp;</label>
                        <select class="FormListboxAjax" id="f_1_7" name="f_1_7" size="8" >
                        <c:forEach var="i" items="${staffAssist}">
													<option value="${i}">${i}</option>
												</c:forEach>
                          <!--   <option/> -->
                        </select>
						<img alt="add" src="resources/images/forms/add.png" class="actionIcon" />
						<img alt="delete" src="resources/images/forms/delete.png" class="actionIcon" />
                    </p>
                    <p>
                        <label class="FormFieldLabelRequired" for="f_1_8" title="Area of Use">Area of Use&nbsp;:&nbsp;</label>
                        <select class="FormListboxAjax" id="f_1_8" name="f_1_8" size="8">
                        <c:forEach var="i" items="${areaOfUse}">
													<option value="${i}">${i}</option>
												</c:forEach>
                         <!--    <option/> -->
                        </select>
						<img alt="add" src="resources/images/forms/add.png" class="actionIcon" />
						<img alt="delete" src="resources/images/forms/delete.png" class="actionIcon" />
                    </p>
                    <p>
                        <label class="FormFieldLabel" for="f_1_9" title="Required equipment">Equipment Required&nbsp;:&nbsp;</label>
                        <input checked="checked" id="f_1_9" name="f_1_9" onchange="cbChanged(this);" type="checkbox" value="Required"/>
					</p>
                    <p>
                        <label class="FormFieldLabelRequired" for="f_1_10" title="Required equipment">Required equipment&nbsp;:&nbsp;</label>
                        <select class="FormListboxAjax" id="f_1_10" name="f_1_10" size="8">
                        <c:forEach var="i" items="${reqEquipment}">
													<option value="${i}">${i}</option>
												</c:forEach>
                            <!-- <option/> -->
                        </select>
						<img alt="add" src="resources/images/forms/add.png" class="actionIcon" />
						<img alt="delete" src="resources/images/forms/delete.png" class="actionIcon" />
                    </p>
                    <p>
                        <label class="FormFieldLabelRequired" for="f_1_11" title="No of Attendees">No of Attendees&nbsp;:&nbsp;</label>
                        <input class="FormInputInteger" id="f_1_11" name="f_1_11" type="text"/>
                    </p>
                    <p>
                        <label class="FormFieldLabel" for="f_1_12" title="Required">Hospitality Required&nbsp;:&nbsp;</label>
                        <input checked="checked" id="f_1_12" name="f_1_12" onchange="cbChanged(this);" type="checkbox" value="Required"/>
					</p>
                    <fieldset class="Form" id="f_1_13" style="border:solid thin;">
                        <legend>&nbsp;Hospitality Requirements&nbsp;</legend>
                        <p>
                            <label class="FormFieldLabel" for="f_1_13_15" title="Location">Location&nbsp;:&nbsp;</label>
                            <input class="FormInputTextAjax" id="f_1_13_15" name="f_1_13_15" readonly="readonly" type="text"/>
							<img alt="find" src="resources/images/forms/find.png" class="actionIcon" />
                        </p>
                        <p>
                            <label class="FormFieldLabel" for="f_1_13_16" title="Require">Requirement&nbsp;:&nbsp;</label>
                            <select class="FormListboxAjax" id="f_1_13_16" name="f_1_13_16" size="8">
                              <c:forEach var="i" items="${hospitalityResource}">
													<option value="${i}">${i}</option>
												</c:forEach>
                                <!-- <option/> -->
                            </select>
							<img alt="add" src="resources/images/forms/add.png" class="actionIcon" />
							<img alt="delete" src="resources/images/forms/delete.png" class="actionIcon" />
                        </p>
                    </fieldset>
                    <p>
                        <label class="FormFieldLabel" for="f_1_14" title="Other Requirements">Other Requirements&nbsp;:&nbsp;</label>
                        <textarea class="FormTextarea" cols="20" id="f_1_14" name="f_1_14" rows="50"> </textarea>
                    </p>
                </fieldset>
            </div>
        </div>
        <div id="FORM_ButtonBar">
            <button class="butn"  id="FORM_BTN_CANCEL" type="button" onclick="formDataCancel();">Cancel</button>
            <c:if test="${(submitVisible=='add')}">
             <button class="butn" id="FORM_BTN_OK" type="button" onclick="formDataSave();">Save</button>
            <button class="butn" id="FORM_BTN_SUBMIT" type="button" onclick="formDataSubmit();">Submit</button>
            </c:if>
            <c:if test="${(submitVisible=='edit')}">
            <button class="butn" id="FORM_BTN_UPDATE" type="button" onclick="formDataUpdate();">Update</button>
            </c:if>
            
        </div>
    </div>
</form>


		</div>
	</body>
</html>
