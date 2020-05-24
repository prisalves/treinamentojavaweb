function notice(title,texto,cor,tempo,som) {
	new jBox('Notice', {
		animation: {
			open: 'flip',
			close: 'zoomOut' //slide:right
		},
		theme: 'NoticeBorder',
		audio: '/sistemafenicia/javax.faces.resource/jbox/audio/'+som,
		volume: 80,
		position: {
			x: 20,
			y: 40
		},
		title: title,
		content: texto,
		autoClose: tempo,
		color: cor,
		zIndex: 999999,
		onInit: function() {
			//this.options.color = colorsN2[currentColorN2];
			//currentColorN2++;
			//(currentColorN2 >= colorsN2.length) && (currentColorN2 = 0)
		}
	})
}

function swith(){
	setTimeout(function(){ 
		$(".switch").each(function(index) {
			var verifica = $(this).find("div").hasClass("switch-animate");
			if(!verifica){
				//$('.switch')['bootstrapSwitch']();
				$(this).bootstrapSwitch();
			}
		});
		$('.selectpicker,.dataTables_length .form-control').selectpicker({style:'btn-info',size:4,width:"auto"});
	},50);
}

var load = new jBox('Modal', {
	delayOpen:0,
	delayClose:0,
	theme: 'NoticeBorder',
	draggable: 'title',
	blockScroll: true,
	//title: 'Carregando...',
	title: false,
	adjustPosition: true,
	adjustTracker: true,
	animation: {open: 'zoomIn', close: 'zoomOut'},
	overlay: true,
	fixed: false,
	fade:100,
	reposition: true,
	repositionOnOpen: true,
	repositionOnContent: true,
	content: jQuery('#msmLoading'),
	//content: "teste",
	closeOnEsc: false,
	closeOnClick: false,
	closeButton: false,
	zIndex: 99999,
	appendTo: jQuery('body'),
	onInit: function() {
		//alert('a2');
	},
	onClose:function() {
		$('.noui').removeClass('ui-button');
		//$('.switch')['bootstrapSwitch']();
		$("[data-toggle=tooltip]").tooltip();
		
		if($("#dataTable").length){
				$('.selectpicker,.dataTables_length .form-control').selectpicker({style:'btn-info',size:4,width:"auto"});
				$('#dataTable_length select').hide();
				$('#dataTable_length').change(function(){ swith(); });
				$('#dataTable thead,#dataTable tbody').on('click','tr','th',function(){ swith(); });
				$('#dataTable').on('page.dt',function(){ swith(); });
				$('#dataTable').on('search.dt',function(){ swith(); });
		}
		
	},
});

jQuery(window).load(function() {
	//new jBox('Confirm',{confirmButton: 'Do it!',cancelButton: 'Nope'});
});

