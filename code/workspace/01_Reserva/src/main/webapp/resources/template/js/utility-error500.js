	var canvas, lienzo;
	var tam_letra = 16;
	var columna = new Array();
	//--------------------------------------------------------------------------
	document.addEventListener("DOMContentLoaded", function() {

		canvas = document.getElementById('canvas');
		canvas.height = window.innerHeight - 5;
		canvas.width = window.innerWidth;

		lienzo = canvas.getContext('2d');
		total_columnas =  Math.round(canvas.width / tam_letra);

		for (var i = 0; i < total_columnas; i++) {
			columna[i] = 1;
		}
		setInterval(dibujar, 85);

		window.addEventListener('resize', recargar);
	});
	//--------------------------------------------------------------------------
	function dibujar(){
		lienzo.fillStyle = "rgba(0, 0, 0, 0.05)";
	 	lienzo.fillRect(0, 0, canvas.width, canvas.height);
	 				//lienzo.fillStyle = "#00FF00";
	 				lienzo.fillStyle = "rgba(51,219,51,0.8)";
	            	lienzo.font = tam_letra + "px Arial";

	            	for (var i = 0; i < columna.length; i++) {
	              		lienzo.fillText(texto_aleatorio(), (i*tam_letra), (columna[i]*tam_letra));
	               		if ((columna[i] * tam_letra) > canvas.height && Math.random() > 0.975) {
		               		columna[i] = 0;
	               		}
	               		columna[i]++;
	             	}
	}
	//--------------------------------------------------------------------------
	function texto_aleatorio(){
		return String.fromCharCode(parseInt(Math.floor((Math.random() * 94) + 33)));
	}
	//--------------------------------------------------------------------------
	function recargar () {
		canvas.height = window.innerHeight - 5;
		canvas.width = window.innerWidth;

		total_columnas =  Math.round(canvas.width / tam_letra);
		columna = new Array();

		for (var i = 0; i < total_columnas; i++){
			columna[i] = 1;
		}
	}
