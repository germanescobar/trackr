<#ftl encoding="UTF-8">
<#import "layout.ftl" as layout>
<@layout.layout>
	<div id="homepage" class="container">
		
		<div class="hero">
			<h2>Track your social stats.</h2>
			<p>From Facebook updates to Github commits, know how much content you are producing on the Web.</p>
			<div class="fb-login-button" scope="read_stream,user_checkins,user_status">Login with Facebook</div>
		</div>
		
		<p>This project was created for the <strong><a href="http://wayra.org/es/wayra-developer-day-home" target="_blank">Wayra Developer Day Colombia 2012</a></strong>.</p> 
	
		<p>It's a simple Web Application that shows the number of posts/tweets/commits that you have <em>generated</em> today - and the last 7 days -. By default, it will show only Facebook updates but you can activate other "services" as well (currently Twitter and Github are available). </p>

		<p>Additionally, it will generate a nice graph of the last 7 days comparing the activity of your "services".</p>
		
		<h3>Technology Stack</h3>
		
		<h4>Front End</h4>
		
		<ul>
			<li>HTML5</li>
			<li><a href="http://twitter.github.com/bootstrap/" target="_blank">Twitter Bootstrap</a></li>
			<li>Javascript</li>
			<li><a href="http://lesscss.org/" target="_blank">LESS</a></li>
			<li><a href="http://jquery.org/" target="_blank">JQuery</a></li>
			<li><a href="http://www.highcharts.com/" target="_blank">Highcharts</a></li>
		</ul>
		
		<h4>Back End</h4>
		
		<ul>
			<li>Java</li>
			<li>Spring</li>
			<li><strong><a href="http://github.com/germanescobar/jogger" target="_blank">Jogger</a></strong>. A Web Framework I made ;)</li>
		</ul>
		
		<h4>Integration API's</h4>
		
		<ul>
			<li><a href="http://developers.facebook.com/" target="_blank">Facebook</a></li>
			<li><a href="https://dev.twitter.com/" target="_blank">Twitter</a></li>
			<li><a href="http://develop.github.com/" target="_blank">Gihub</a></li>
		</ul>
		
		<p>You can find an online version of this application at <a href="http://wayra-trackr.herokuapp.com/">http://wayra-trackr.herokuapp.com/</a>. The source code is at <a href="http://github.com/germanescobar/trackr">http://github.com/germanescobar/trackr</a>.</p>
		
		<h3>Contact</h3>
		
		<p>Find me on Twitter <a href="https://twitter.com/#!/germanescobar" target="_blank">@germanescobar</a> or email me at <a href="mailto:german.escobarc@gmail.com" target="_blank">german.escobarc@gmail.com</a>.</p>
		
		<div style="padding-top:80px;"></div>
		
	</div>
	
	<div id="fb-root"></div>
     <script>
     	function setCookie(c_name,value,exdays) {
			var exdate=new Date();
			exdate.setDate(exdate.getDate() + exdays);
			var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
			document.cookie=c_name + "=" + c_value;
		}
     
       window.fbAsyncInit = function() {
         FB.init({
           appId      : '367771223287679',
           status     : true, 
           cookie     : true,
           xfbml      : true,
           oauth      : true,
         });
         
		FB.Event.subscribe('auth.statusChange', function(response) {
          	if (response.authResponse) {
				setCookie("accessToken", response.authResponse.accessToken, 1);
				window.location.href = "/";
			}
		})
         
       };
       
       (function(d){
          var js, id = 'facebook-jssdk'; if (d.getElementById(id)) {return;}
          js = d.createElement('script'); js.id = id; js.async = true;
          js.src = "//connect.facebook.net/en_US/all.js";
          d.getElementsByTagName('head')[0].appendChild(js);
        }(document));
     </script>

</@layout.layout>