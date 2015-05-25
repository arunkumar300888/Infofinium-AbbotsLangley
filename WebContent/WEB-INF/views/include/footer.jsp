 <script>
$(function() {
	$("#bottom_link a").click(function(){
		var title=$(this).attr('title');
		//alert(title);
		$('.tContent').hide();
		if(title=='#about'){
		$(title).show();
		title='About Us';
		}
		else if(title=='#terms'){
		$(title).show();
		title='Terms &amp;<br /> Conditions';
		}
		else if(title=='#privacy'){
		$(title).show();
		title='Privacy';
		}
		else if(title=='#contact'){
		$(title).show();
		title='Contact Us';
		}
$( "#footer-message" ).dialog({	
modal: true,
height: 400,
width: 800,
dialogClass: 'title_link',
title: "<table><tr><td><img src='resources/images/wizart/login-logo.png' /><td><td class='modal_title_text'>"+title+"</td></tr></table>"
})
})
});
</script>			
			<footer id="footer">
				<div class="float-left" id="bottom_link">					
					<a href="javascript:return false;" title="#about">About</a> | <a href="javascript:return false;" title="#terms" class="show-popup-btn">Terms and Conditions</a> | <a href="javascript:return false;" title="#privacy">Privacy</a> | <a href="javascript:return false;" title="#contact">Contact</a>				
				</div>
				<p class="float-right">Copyright &copy; 2015 JMR Consulting UK Ltd</p>
			</footer>
<div id="footer-message" style="display: none;">
<div id="about" class="tContent">
<p>infoFINIUM is an Information Governance Framework solution, that enables organisations to gain tighter control and visibility of all of their information and information processes; to improve the management, security and accountability of information.</p>
 
<p>Through the implementation of infoFINIUM organisations can minimise risk, improve effeciency and decision making, and additionally can yield cost and customer benefits.</p>

<h4>About JMR UK</h4>
 
<p>JMR Consulting UK Limited is a software sevices and solutions company that provide full project lifecycle, software servcies and solutions to companies across all industry sectors. Due to their experience and expertise, JMR UK have designed and developed infoFINIUM as a highly configurable Information Governance framework. infoFINIUM is versitle and adaptable to an array of organisational processes, and can be customised to further meet specific requirements. To find out more about JMR visit their website at www.jmruk.com.</p>
</div>
<div id="terms" class="tContent">
<h4>Under Progress</h4>
</div>
<div id="privacy" class="tContent">
<p>We only collect information that is necessary to provide you with the service you request, but typically we collect your name, company name, location details, e-mail address and any other information you provide to us through our Contact form. In short, we collect personal information about you and your company in order for JMR UK to help you</p>
</div>
<div id="contact" class="tContent">
<p>infoFINIUM can provide your organisation with true business value.</p>

<p>Find out more.</p>

<p>Get in contact with us to find out how infoFINIUM can help you.</p>
 
<p>Email us at info@jmruk.com, give us a call on 0845 052 0900 or use the contact form on the right.</p>
 
<p>Alternatively, you can post us a query to:<br />
11a Chenies Parade, Chalfont Station Road, Amersham, Buckinghamshire, HP7 9PH.</p>
</div>
</div>