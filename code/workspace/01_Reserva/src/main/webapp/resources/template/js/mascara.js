function moeda(z){  
	v = z.value;
	v = v.replace(/\D/g,"");  //permite digitar apenas n�meros
	v = v.replace(/[0-9]{12}/,"inv�lido");   //limita pra m�ximo 999.999.999,99
	v = v.replace(/(\d{1})(\d{8})$/,"$1.$2");  //coloca ponto antes dos �ltimos 8 digitos
	v = v.replace(/(\d{1})(\d{5})$/,"$1.$2");  //coloca ponto antes dos �ltimos 5 digitos
	v = v.replace(/(\d{1})(\d{1,2})$/,"$1,$2");        //coloca virgula antes dos �ltimos 2 digitos
	z.value =  v;
} 

/*function setaValorCampo(valor, campo){
	var valorMoeda = parseFloat(valor).toFixed(2);
	$("iframe").contents().find("#form2\\:"+campo+"").val(valorMoeda);
	alert(valorMoeda);
}*/

/*function reloadPage(){
	$(".ui-icon-closethick").on("click",function(){location.reload();});
}*/

function phoneMaskHandler(event) { 
	var target, phone, element;    
	target = (event.currentTarget) ? event.currentTarget : event.srcElement;   
	phone = target.value.replace(/\D/g, '');   
	element = $(target);   
	element.unmask();  
	if(phone.length > 10) {
		element.mask("(99) 99999-999?9");
	} else {
		element.mask("(99) 9999-9999?9");
	}
}


// Funcao que faz o texto ficar em uppercase  
function upperText() {  
    // Para tratar o colar  
    jQuery(".up").bind('paste', function(e) {  
        var el = jQuery(this);  
        setTimeout(function() {  
            var text = jQuery(el).val();  
            el.val(text.toUpperCase());  
        }, 100);  
    });  

    if ( jQuery.browser.mozilla) {  
        // Para tratar quando � digitado    
        jQuery(".up").keyup(function() {    
                var el = jQuery(this);    
                setTimeout(function() {    
                    var text = jQuery(el).val();    
                    el.val(text.toUpperCase());    
                }, 100);    
            });  
        }  
          
        if ( jQuery.browser.msie || jQuery.browser.safari||jQuery.browser.chrome) {  
        // Para tratar quando � digitado    
            jQuery(".up").keypress(function() {    
                var el = jQuery(this);    
                setTimeout(function() {    
                    var text = jQuery(el).val();    
                    el.val(text.toUpperCase());    
                }, 100);    
            });  
        }    
}  


//$(".dataFormato']").mask('99/99/9999');

