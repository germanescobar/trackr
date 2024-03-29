<#macro layout>

<!DOCTYPE html>

<html>
<head>
	<title>Trackr - Your social stats</title>
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<style type="text/css">
		body {
			padding-top:30px;
		}
		
		.fb_button_medium, .fb_button_medium_rtl {
			font-size:14px !important;
			line-height:18px !important;
		}
	</style>
	
	<link rel="stylesheet" type="text/css" href="/stylesheets/bootstrap.css"></link>
	<link rel="stylesheet" type="text/css" href="/stylesheets/bootstrap-responsive.css"></link>
	<link rel="stylesheet" type="text/css" href="/stylesheets/style.css"></link>
	
	<script src="/javascripts/underscore.js"></script>
	<script src="/javascripts/jquery.js"></script>
	<script src="/javascripts/bootstrap.min.js"></script>
	<script src="/javascripts/highcharts.js"></script>
	
</head>

<body>

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
    		<div class="container">
      			<h1>trackr</h1>
    		</div>
  		</div>
	</div>

	<#nested/>
</body>

</html>

</#macro>