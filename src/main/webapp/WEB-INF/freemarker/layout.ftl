<#macro layout>

<!DOCTYPE html>

<html>
<head>
	<title>Trackr - Track your social stats</title>
	
	<link rel="stylesheet" type="text/css" href="/stylesheets/bootstrap.css"></link>
	<link rel="stylesheet" type="text/css" href="/stylesheets/style.css"></link>
	
	<script src="/javascripts/underscore.js"></script>
	<script src="/javascripts/jquery.js"></script>
	<script src="/javascripts/bootstrap.js"></script>
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