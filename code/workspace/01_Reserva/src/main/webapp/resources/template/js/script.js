

jQuery(window).ready(function() { //LOAD DOM
    div = $( "<div id='infoPage' />" );
	//$("#wrapper").prepend(div);
	//jQuery("#infoPage").html("WIDTH: "+jQuery(window).width()+"<br/>"+"HEIGHT: "+jQuery(window).height());
});
jQuery(window).load(function() { // LOAD CSS,IMGs
	
	//alert('aaa');
	//console.log( index + ": " + $(this).("input[type=checkbox]").text() );
	//$('.myCheckbox').is(':checked');

	/*
		$.fn.datepicker.dates['pt-BR'] = {
			days: ["Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"],
			daysShort: ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"],
			daysMin: ["Do", "Se", "Te", "Qu", "Qu", "Se", "Sa"],
			months: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"],
			monthsShort: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"],
			today: "Hoje",
			clear: "Limpar",
			format: "dd/mm/yyyy"
		};
	
	var defaults = $.fn.datepicker.defaults = {
        language: 'pt-BR'
    };
	
	 
	*/
	
	$.fn.datepicker.defaults = {
	        language: 'pt-BR'
	    };
	
	$('.datetimepicker input').datetimepicker({ 
		language: "pt-BR" 
	});
	
	/*
	function removejscssfile(filename, filetype){
	    var targetelement=(filetype=="js")? "script" : (filetype=="css")? "link" : "none" //determine element type to create nodelist from
	    var targetattr=(filetype=="js")? "src" : (filetype=="css")? "href" : "none" //determine corresponding attribute to test for
	    var allsuspects=document.getElementsByTagName(targetelement)
	    for (var i=allsuspects.length; i>=0; i--){ //search backwards within nodelist for matching elements to remove
	    if (allsuspects[i] && allsuspects[i].getAttribute(targetattr)!=null && allsuspects[i].getAttribute(targetattr).indexOf(filename)!=-1)
	        allsuspects[i].parentNode.removeChild(allsuspects[i]) //remove element by calling parentNode.removeChild()
	    }
	}
	*/

	//removejscssfile("modal.js", "js"); //remove all occurences of "somescript.js" on page
	//removejscssfile("modals.css", "css"); //remove all occurences "somestyle.css" on page
	
	//$('link[href*="bsf"]').remove();
	
	//ui-button ui-widget ui-state-default ui-corner-all ui-button-text-icon-left zoom btn btn-primary btn-lg
	//ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only btn btn-warning
	//$(".noui").removeClass("ui-button");
	//$(".btn").removeClass("ui-widget");
	//$(".btn").removeClass("ui-state-default");
	//$(".btn").removeClass("ui-corner-all");
	//$(".btn").removeClass("ui-button-text-icon-left");
	//$(".btn").removeClass("ui-button-icon-only");
	
	//PEGAR LOCALIZACAO
	/*if (Modernizr.geolocation && isMobileDevice() ) {
		window.navigator.geolocation.getCurrentPosition(function (e) {
			var coord = e.coords;
			//console.log("longitude:"+coord.longitude);
			//console.log("latitude:"+coord.latitude);
			jQuery("#infoPage").html(" longitude: "+coord.longitude+", latitude: "+coord.latitude+"<br/>");
		}, function(e) {
			//alert(e.message);
		});
	}*/
	
	//MODELO ANIMACAO ACTUATE
	
	/*
	
	https://daneden.github.io/animate.css/
	 
	bounce, flash, pulse, rubberBand, shake, swing, tada, wobble, 
	bounceIn, bounceInDown, bounceInLeft, bounceInRight, bounceInUp, 
	bounceOut, bounceOutDown, bounceOutLeft, bounceOutRight, bounceOutUp, 
	fadeIn, fadeInDown, fadeInDownBig, fadeInLeft, fadeInLeftBig, fadeInRight, 
	fadeInRightBig, fadeInUp, fadeInUpBig, fadeOut, fadeOutDown, fadeOutDownBig, 
	fadeOutLeft, fadeOutLeftBig, fadeOutRight, fadeOutRightBig, fadeOutUp, 
	fadeOutUpBig, flipInX, flipInY, flipOutX, flipOutY, lightSpeedIn, 
	lightSpeedOut, rotateIn, rotateInDownLeft, rotateInDownRight, rotateInUpLeft, 
	rotateInUpRight, rotateOut, rotateOutDownLeft, rotateOutDownRight, 
	rotateOutUpLeft, rotateOutUpRight, hinge, rollIn, rollOut, zoomIn, 
	zoomInDown, zoomInLeft, zoomInRight, zoomInUp, zoomOut, zoomOutDown, 
	zoomOutLeft, zoomOutRight, zoomOutUp, slideInDown, slideInLeft, slideInRight, 
	slideInUp, slideOutDown, slideOutLeft, slideOutRight, slideOutUp
	
	*/
	
	/*$('<h1>Superman</h1>').appendTo('body')
	.css('animation-delay','3s')
	.css('animation-duration','2s')
	//.css('animation-iteration-count','infinite')
	.actuate('lightSpeedIn', function(x) {
		console.log('StartAnimating', x);
	   	x.css('animation-delay','3s')
	  	 .css('animation-duration','2s');
		x.actuate('fadeOut', function(y) {
			console.log('FinishedAnimating', y);
			y.remove();
		});
	});*/
	
	
});
jQuery(window).resize(function() {
	//$("#infoPage").html("WIDTH: "+jQuery(window).width()+"<br/>"+"HEIGHT: "+jQuery(window).height());
});


