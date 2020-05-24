<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div ng-show="loading" class="loading">Aguarde...</div>

<div ng-include="'template/menu.jsp'"></div>
<div ng-include="'template/modal.jsp'"></div>


<div class="container" style="margin-top: 60px;">
	<div class="row">
		<div ng-bind-html="msg | unsafe"></div>
	</div>
	<div class="row">
		<div class="col-md-12">

			<div class="panel panel-warning" style="max-width: 500px;margin:0 auto;">

				<form name="nasaForm">
					<div class="panel-body">

						<h3>Câmera:</h3>
						<select id="camera" name="camera" class="form-control"
							ng-model="nasa.camera">
							<option selected="selected">Todas as Câmeras</option>
							<option>FHAZ - Front Hazard Avoidance Camera</option>
							<option>RHAZ - Rear Hazard Avoidance Camera</option>
							<option>NAVCAM - Navigation Camera</option>
						</select> <br>
						<h3>Data Terrestre:</h3>
						<div class="row">
							<div class="col-md-4">
								<h4>Dia:</h4>
								<input type="number" id="dia" name="dia" max="31" min="1" class="form-control"
									ng-model="nasa.dia">
							</div>
							<div class="col-md-4">
								<h4>Mês:</h4>
								<input type="number" id="mes" name="mes" max="12" min="1" class="form-control"
									ng-model="nasa.mes">
							</div>
							<div class="col-md-4">
								<h4>Ano:</h4>
								<input type="number" id="ano" name="ano" class="form-control" max="2016" min="2012"
									ng-model="nasa.ano">
							</div>
						</div>
					</div>
					<!-- panel body -->
					<div class="panel-footer text-center">
						<button type="submit" class="btn btn-block btn-raised btn-warning"
							ng-click="buscarNasa(nasa)" ng-disabled="nasaForm.$invalid">Buscar</button>
					</div>
					<!-- panel footer -->
				</form>
			</div>
			<!-- panel -->

		</div>
	</div>
	<div class="row">

		<div class="col-md-12">
			<div class="">

				<ul id="grid" class="list-unstyled">
					<li class="col-md-3 col-sm-4 col-xs-6 list-unstyled" data-ng-repeat="foto in fotos"><a href="{{foto.img_src}}" target="_blank"><img  class="img-responsive"
						ng-src="{{foto.img_src}}" /></a></li>
				</ul>
			</div>
		</div>

	</div>

</div>

