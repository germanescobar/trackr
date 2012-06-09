<#ftl encoding="UTF-8">
<#import "layout.ftl" as layout>
<@layout.layout>

	<div id="content" class="container">
		<div class="row">
			<div class="span8">
				<h2>Today</h2>
				<div id="facebook" class="count">
					<strong><img src="/images/ajax-loader.gif"></strong> 
					<small>Facebook updates</small>
				</div>
				<#list user.services as service>
					<div id="${service.name}" class="count">
						<strong><img src="/images/ajax-loader.gif"></strong> 
						<small>${service.label}</small>
					</div>
				</#list>
				
				<h2 style="clear:both; padding-top:30px;">Last 7 days</h2>
				<div id="chart" style="height:300px; margin:20px;"></div>
			</div>
			
			<div class="span4">
				<div id="services">
					<ul>
						<li>Facebook <span><img src="/images/ok.png" /></span></li>
						<li>Twitter 
							<span>
								<#if user.hasService('twitter') >
									<img src="/images/ok.png" />
								<#else>
									<button class="btn" data-toggle="modal" href="#twitter">Activate</button>
								</#if>
							</span>
						</li>
						<li>Github 
							<span>
								<#if user.hasService('github') >
									<img src="/images/ok.png" />
								<#else>
									<button class="btn" data-toggle="modal" href="#github">Activate</button>
								</#if>
							</span>
						</li>
						<li class="disabled">Flickr <span>Soon</span></li>
						<li class="disabled">Blogger <span>Soon</span></li>
					</ul>
				</div>
			</div>
		</div>
		
		<div class="modal hide fade" id="github" style="display:none;">
  			<div class="modal-header">
    			<button class="close" data-dismiss="modal">×</button>
    			<h3>Activate Github</h3>
  			</div>
  			<div class="modal-body">
    			<form class="form" style="width:500px; margin:auto;">
    				<div class="field">
    					<label for="github_user">Username:</label>
						<input id="github_user" type="text" style="width:200px;"/>
						<span class="error">Ingresa el usuario</span>
    				</div>
    				
    			</form>
  			</div>
			<div class="modal-footer">
    			<a id="activate_github" href="#" class="btn btn-primary">Activate</a>
  			</div>
		</div>
		
		<div class="modal hide fade" id="twitter" style="display:none;">
  			<div class="modal-header">
    			<button class="close" data-dismiss="modal">×</button>
    			<h3>Activate Twitter</h3>
  			</div>
  			<div class="modal-body">
    			<form class="form" style="width:500px; margin:auto;">
    				<div class="field">
    					<label for="twitter_user">Username:</label>
						<input id="twitter_user" type="text" style="width:200px;"/>
						<span class="error">Ingresa el usuario</span>
    				</div>
    				
    			</form>
  			</div>
			<div class="modal-footer">
    			<a id="activate_twitter" href="#" class="btn btn-primary">Activate</a>
  			</div>
		</div>

	</div>
	
	<script type="text/javascript">
	
		$('#activate_github').click(function() {
			
			$.ajax({
				type: 'POST',
				url: '/user/services',
				data: '{"userId": "${user.id?c}",'
					+ '"service": "github",'  
					+ '"data": 	"' + $('input#github_user').val() + '"}'
			});
			
			request.done(function() {
				window.location.href = "/";	
			});
		
		});
		
		$('#activate_twitter').click(function() {
			
			$.ajax({
				type: 'POST',
				url: '/user/services',
				data: '{"userId": "${user.id?c}",'
					+ '"service": "twitter",' 
					+ '"data": 	"' + $('input#twitter_user').val() + '"}'
			});
			
			request.done(function() {
				window.location.href = "/";
			});
		
		});
	
		var chart = new Highcharts.Chart({
				chart: {
			    	renderTo: 'chart',
			      	defaultSeriesType: 'line',
			      	marginTop:20,
			     	marginRight: 120,
			     	marginBottom: 50,
			     	borderColor: '#CCC',
			     	shadow: true
			   	},
			   	title: {
			      	text: ''
			   	},
			   	xAxis: {
			     	categories: ${xLabels}
			   	},
			   	yAxis: {
			     	title: {
			        	text: ''
			       	},
			       	min: 0,
			      	plotLines: [{
			         	value: 0,
			           	width: 1,
			           	color: '#808080'
			       	}]
			   	},
			   	tooltip: {
			     	formatter: function() {
			         	return this.x + ': $' + Highcharts.numberFormat(this.y, 2);
			      	}
			   	},
			  	legend: {
			     	layout: 'vertical',
			      	align: 'right',
			      	verticalAlign: 'top',
			       	x: -10,
			       	y: 90,
			       	borderWidth: 0
			  	},
			   	series: []
			});
			
			setTimeout(function() {
				request = $.ajax({
					type: 'GET',
					url: '/user/${user.id?c}/stats?service=facebook'
				});
					
				request.done(function(data) {
					$('div#facebook strong').html(data.today);
					chart.addSeries( { name: 'Facebook', data: data.data } );	
				});
					
			}, 50);
		
			<#if user.hasService('twitter') >
				setTimeout(function() {
					request = $.ajax({
						type: 'GET',
						url: '/user/${user.id?c}/stats?service=twitter'
					});
					
					request.done(function(data) {
						$('div#twitter strong').html(data.today);
						chart.addSeries( { name: 'Twitter', data: data.data } );	
					});
					
				}, 50);
			</#if>
			
			<#if user.hasService('github') >
				setTimeout(function() {
					request = $.ajax({
						type: 'GET',
						url: '/user/${user.id?c}/stats?service=github'
					});
					
					request.done(function(data) {
						$('div#github strong').html(data.today);
						chart.addSeries( { name: 'Github', data: data.data } );	
					});
					
				}, 50);
			</#if>
			
		
	</script>
</@layout.layout>