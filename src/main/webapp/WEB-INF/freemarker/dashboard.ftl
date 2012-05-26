<#ftl encoding="UTF-8">
<#import "layout.ftl" as layout>
<@layout.layout>
	<div id="content" class="container">
		<div class="row">
			<div class="span8">
				<h2>Today</h2>
				<div class="count">
					<strong>2</strong> 
					<small>Tweets</small>
				</div>
				<div class="count">
					<strong>3</strong> 
					<small>Facebook Posts</small>
				</div>
				<div class="count">
					<strong>1</strong> 
					<small>Github Commits</small>
				</div>
				
				<h2 style="clear:both; padding-top:30px;">Last 7 days</h2>
				<div id="chart" style="height:300px; margin:20px;"></div>
			</div>
			
			<div class="span4">
				<div id="services">
					<ul>
						<li>Facebook <span><img src="/images/ok.png" /></span></li>
						<li>Twitter <span><img src="/images/ok.png" /></span></li>
						<li>Github <span><button class="btn ">Activate</button></span></li>
						<li class="disabled">Flickr <span>Soon</span></li>
						<li class="disabled">Blogger <span>Soon</span></li>
					</ul>
				</div>
			</div>
		</div>

	</div>
	
	<script type="text/javascript">
		new Highcharts.Chart({
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
			     	categories: [ '20 May', '21 May', '22 May', '23 May', '24 May', '25 May', '26 May' ]
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
			   	series: [{
			      	name: 'Tweets',
			       	data: [7, 2, 12, 5, 8, 10, 12]
			     },
			     {
			      	name: 'Facebook',
			       	data: [2, 1, 4, 2, 0, 3, 2]
			     },
			     {
			      	name: 'Github',
			       	data: [0, 0, 1, 3, 4, 5, 6]
			     }]
			});
	</script>
</@layout.layout>