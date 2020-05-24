jQuery(window).load(function() {

	/*$(".switch").each(function(index) {
		var campo = $(this).find("input").hasClass("true");
		if(campo && $(this).hasClass("unchecked") ){
			$(this).removeClass("unchecked");
			$(this).children().first().removeClass("switch-off");
			$(this).addClass("checked");
			$(this).children().first().addClass("switch-on");
		}
	});*/
	
	/*$(".switch").each(function(index) {
		var verifica = $(this).find("div").hasClass("switch-animate");
		if(!verifica)
			$(this).bootstrapSwitch();
	});*/
	
	
	/*$('.selectpicker,.dataTables_length .form-control').selectpicker({style:'btn-info',size:4,width:"auto",});
	$('#dataTable_length').change(function(){$('.switch')['bootstrapSwitch']();});
	$('#dataTable_length select').hide();
	$('#dataTable thead').on('click','tr',function(){$('.switch')['bootstrapSwitch']();});*/
	//$('#dataTable').on('page.dt',function(){ $('.switch')['bootstrapSwitch'](); });
	/*$('.paginate_button a').on('click',function(){
		setTimeout(function(){ $('.switch')['bootstrapSwitch'](); },50);
	});*/

});
jQuery(window).resize(function() {
	if($("#dataTable").length){
		updateDataTable();
	}
});
function jsonConcat(o1, o2) {
 for (var key in o2) {
  o1[key] = o2[key];
 }
 return o1;
}

/*var settingsTable = {
		aoColumns: [],
}*/

/*window.settings = {};
settings = jsonConcat(settings, settingsTable);
settings = jsonConcat(settings, settingsTableDefault);*/

window.getAcao = function(id,acao) {
	var Acao = {
            id: id,
            acao: acao
    }
	$.ajax({
        type: 'POST',
        url: window.location.href + "/acao",
        dataType: 'json',
        data: JSON.stringify(id),
        contentType: "application/json;charset=UTF-8",
        processData:false,
        async: true,
        cache: false, 
        success: function (data) {
        	console.log("SUCESS");
        	if(acao=="alterar"){
        		//console.log("ALTERAR");
        		callAlterar();
        	}else if(acao=="excluir"){
        		//console.log("EXCLUIR");
        		callExcluir();
        		//updateDataTable();
        	}else if(acao=="status"){
        		//console.log("STATUS");
        		callStatus();
        		//updateDataTable();
        	}
        },
        error : function (e) {
        	//console.log("ERROR");
        },
        done : function(e) {
				//console.log("DONE");
			}
   });
}

window.settingsTableDefault = {
	/*
	aoColumns: [
				// visible:true, bSearchable:true, sClass:"left select-filter"
	            { mDataProp: "check" },
	            { mDataProp: "codigo" },
	            { mDataProp: "login" },
	            { mDataProp: "acao" },
	],
	*/
	//aoColumns: settingsTable,
	sAjaxSource: window.location.href + "/lista",
	fnServerData: function ( sSource, aoData, fnCallback ) {
	    $.ajax({
	        dataType: 'json',
	        contentType: "application/json;charset=UTF-8",
	        type: "POST",
	        url: sSource,
	        success: function(json){
	        	setTimeout(function(){
	            	new jBox('Confirm',{confirmButton: 'Excluir!',cancelButton: 'Cancelar'});
	        	},50);
	        	fnCallback(json);
	        	$('.switch')['bootstrapSwitch']();
	        },
	        error : function (e) { /*console.log(e);*/ },
	        done : function(e) { /*console.log("DONE");*/ },
	    });
	},
	columnDefs: [
		 {
	        //https://datatables.net/extensions/responsive/classes
			orderable: false,
	        className: 'select-checkbox',
	        targets: 'col-check',
	        width: "15px",
	        data: null,
	        defaultContent: '',
	        searchable: false,
	        visible:false,
	      },
	      {
		    targets: "col-codigo",
		    width: "5%",
		    data: "codigo",
		    searchable: true,
		    className: "center",
		  },   
	      {
	         targets: "col-acoes",
	         className: 'center min-tablet-p',
	         width: "115px",
	         orderable: false,
	         searchable: false,
	         render: function ( data, type, row ) { return data; },
	       }     
	],
	order: [[ 1, 'desc' ], [ 2, 'asc' ]],
	select: {
	      style: 'multi',
	      selector: 'td:first-child',
	},
	stateSave: false,
	language: {
	      search: 'Busca:',
	      lengthMenu: 'Mostrar _MENU_ resultados',
	      infoFiltered: ' - em _MAX_ registros',
	      info: 'Mostrando de _START_ até _END_ de _TOTAL_ registros encontrados',
	      zeroRecords: 'Nenhum registro encontrado!',
	      loadingRecords: " Aguarde - carregando lista... ",
	      infoEmpty: "No records available",
	      decimal: ',',
	      thousands: '.',
	      paginate: {
	          first:    '«',
	          previous: '‹',
	          next:     '›',
	          last:     '»'
	      },
	      aria: {
	          paginate: {
	              first:    'First',
	              previous: 'Previous',
	              next:     'Next',
	              last:     'Last'
	          }
	      },
	      buttons: {
	    	  colvis: 'Exibir colunas',
	    	  colvisRestore : 'RESTAURAR',
	          pageLength: {
	             "-1": 'Exibir todos',
	              _: 'Exibindo %d'
	          }
	      },
	      select: {
	          rows: "( %d registros selecionados )"
	      }
	},
	fixedHeader: {
        header: true,
        headerOffset: $('#header').height(),
        footer: false,
    },
	lengthMenu: [[5,10,25,50,-1],[5,10,25,50,"todos"]],
	pagingType: 'full_numbers',
	dom: 'lfrtipB',
	colVis: {
	      restore: "Restore",
	      showAll: "Show all",
	      showNone: "Show none",
	},
	buttons: [
				{
	                extend: 'colvis',
	                className: 'btn btn-default btn-click btn-sm',
	                postfixButtons: ['colvisRestore'],
	                collectionLayout: 'fixed two-column',
	                columns: ':not(.col-select)',				                    
	            },
	            {
					className: 'btn btn-default btn-click btn-sm',
					extend: 'print',
					autoPrint: false,
				    exportOptions: {
				        columns: [ ':visible:not(.noprint)' ],
				    },
	                customize: function ( win ) {
	                    $(win.document.body).css('background','none', 'important');
	                    $(win.document.body).css('padding','40px','important');
	                    $(win.document.body).find('table')
	                        .addClass( 'compact' )
	                        .css( 'font-size', 'inherit' );
	                    $(win.document.body).find('th')
	                    	.css( 'text-align', 'left','important' );
	                }
				},
	            {
	                text: 'Selecionar todos',
	                className: 'btn btn-info btn-click btn-sm',
	                action: function (e, dt, node, config) {
	                    dt.rows( function ( idx, data, node ) {
	                        return true;
	                    } ).select();
	                }
	            },
	            {
	                extend: 'selectNone',
	                text: 'Desmarcar todos',
	                className: 'btn btn-warning btn-click btn-sm',
	            },
	            {
	                extend: 'selected',
	                text: 'Excluir selecionados',
	                className: 'btn btn-danger btn-click btn-sm',
	                action: function ( e, dt, node, config ) {
	
	                	e = e || window.e;
	                    if(e.preventDefault)
	                        e.preventDefault();
	                    else
	                        e.returnValue = false;
	                    var total = dt.rows({selected:true}).data().length;
	                    window.selecionados = [];
	                    for (i = 0; i < total; i++) {
	                  		selecionados[i] = dt.rows({selected:true}).data()[i].codigo;
	                    }
	                    window.excluirSelecionados = function () {
	                    	myModal.close();
	                    	$.ajax({
	                            url: window.location.href+'/deleteall',
	                            data: JSON.stringify({myArray:selecionados}),
	                            dataType: 'json',
	                            type: "POST",
	                            timeout : 100000,
	                            contentType: "application/json; charset=utf-8",
	                            processData:false,
	                            async: true,
	                            cache: false,          
	                            success: function(data, textStatus) {
	                                notice("INFO!",data.msg,"azul",10000,"boop2"); 
	                                if(data.update)
	                                    updateDataTable();    
	                            },
	                            error: function(xhr, textStatus, errorThrown){
	                            	/* console.log("ERROR - "+ textStatus); */
	                            },
	                            done : function(e) {/* console.log("DONE"); */}
	                        });
	                        //FIM AJAX
	                    }
	                    //FIM EXCLUIRSELECIONADOS
	                    
	                    window.myModal = new jBox("Modal",{
	                	    title: "Confirmacao",
	                	    /*
	                	    content: "Deseja excluir o(s) registro(s): "+selecionados+
	                	    	" &lt;br/&gt; &lt;div style=\"margin: 3px; text-align: center; padding-top: 5px;\" &gt; "+
	                	    	" &lt;button type=\"button\" class=\"btn btn-default btn-sm\" onclick=\"myModal.close()\"&gt; Cancelar &lt;/button&gt; "+
	                	    	" &lt;button type=\"button\" class=\"btn btn-danger btn-sm\" onclick=\"excluirSelecionados()\"&gt; Excluir &lt;/button&gt; "+
	                	    	" &lt;/div&gt; ",
	                	    */
	                	    content: "Deseja excluir o(s) registro(s): "+selecionados+
	                	    	" <br/> <div style=\"margin: 3px; text-align: center; padding-top: 5px;\" > "+
	                	    	" <button type=\"button\" class=\"btn btn-default btn-sm\" onclick=\"myModal.close()\"> Cancelar </button> "+
	                	    	" <button type=\"button\" class=\"btn btn-danger btn-sm\" onclick=\"excluirSelecionados()\"> Excluir </button> "+
	                	    	" </div> ",
	                	});
	                    myModal.open();
	                    
	                },
	             },
	 ],
	 //responsive: true,
	 /*responsive: {
	        details: false
	 },*/
	 responsive: {
		 /*breakpoints: [
		               { name: 'desktop',  width: Infinity },
					    { name: 'tablet-l', width: 1024 },
					    { name: 'tablet-p', width: 768 },
					    { name: 'mobile-l', width: 480 },
					    { name: 'mobile-p', width: 320 }
         ],*/
         details: {
             //display: $.fn.dataTable.Responsive.display.modal( {
             //    header: function ( row ) {
             //        var data = row.data();
             //        return 'Registro:'+row.data().codigo;
             //    }
             //}),
        	 //type: 'column',
        	 //target: -1,
        	 type: 'column',
             target: 'tr',
             renderer: function ( api, rowIdx, columns ) {
                 var data = $.map( columns, function ( col, i ) {
                     if(col.title!='CHECK'){
                    	 return  '<tr>'+
			                      	'<td>'+col.title+':'+'</td> '+
			                      	'<td>'+col.data+'</td>'+
			                     '</tr>';
                     }
                     
                 }).join('');
                 return $('<table class="table tableDetails" />').append( data );
             }
         }
     },
     search: {
    	    caseInsensitive: false,
    	    regex: true,
     },
	 initComplete: function () {
        this.api().columns('.select-filter').every(function () {
            var column = this;
            /*var select = $('<select class="selectpicker"><option value="">Selecione</option></select>')
            .appendTo($(column.footer()).empty())
            .on('change',function () {
                    var val = $.fn.dataTable.util.escapeRegex(
                        $(this).val()
                    );
                    column
                        .search( val ? '^'+val+'$' : '', true, false )
                        .draw();
            });*/
            var select = $('<select class="selectpicker" ><option value="">Selecione</option></select')
            .appendTo(
                this.footer()
                //$(column.footer()).empty()
            )
            .on( 'change', function () {
            	column
                    .search( $(this).val() )
                    .draw();
            } );
            column.cache('search').unique().sort().each( function ( d, j ) {
                select.append( '<option value="'+d+'">'+d+'</option>' )
                });
            });
	 },
}