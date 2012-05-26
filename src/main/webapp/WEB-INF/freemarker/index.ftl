<#ftl encoding="UTF-8">
<#import "layout.ftl" as layout>
<@layout.layout>
	<div style="background-color:whiteSmoke; padding:60px 0 90px 0;">
	<div class="container">
		<h2 style="font-size:68px;padding-top:30px; text-shadow: 1px 1px 0 
white; color:#333; font-family: Helvetica, Arial, sans-serif; ">track your social stats.</h2>

		<p style="line-height: 1.4em; font-size: 24px; color: #666; text-shadow: 1px 1px 0 
white; margin-top:25px;">From <em>Twitter updates</em> to <em>Github commits</em>, know how much content you are producing on the Web.</p>

		<div style="padding-top:45px;" class="fb-login-button" scope="read_stream,user_checkins,user_status">Login with Facebook</div>
		
	</div>
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