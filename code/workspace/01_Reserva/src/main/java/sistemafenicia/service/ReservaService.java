package sistemafenicia.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import sistemafenicia.dao.ApartamentoDao;
import sistemafenicia.dao.EstacionamentoDao;
import sistemafenicia.dao.PessoaFisicaDao;
import sistemafenicia.dao.PredioDao;
import sistemafenicia.dao.ReservaDao;
import sistemafenicia.dao.ReservaTipoDao;
import sistemafenicia.model.Apartamento;
import sistemafenicia.model.Estacionamento;
import sistemafenicia.model.PessoaFisica;
import sistemafenicia.model.Predio;
import sistemafenicia.model.Reserva;
import sistemafenicia.model.ReservaMovimentacao;
import sistemafenicia.model.ReservaTipo;
import sistemafenicia.utils.FacesUtil;
import sistemafenicia.utils.MessagesUtil;

@ManagedBean
@ViewScoped
@Service
@Controller
public class ReservaService extends basicService<Reserva> implements Serializable {

	private static final long serialVersionUID = 1L;

	static final Logger log = Logger.getLogger(ReservaService.class);

	@Autowired
	private ReservaDao reservaDao;
	@Autowired
	private CaixaService caixaService;
	@Autowired
	private EstacionamentoDao estacionamentoDao;
	@Autowired
	private ApartamentoDao apartamentoDao;
	@Autowired
	private PredioDao predioDao;
	@Autowired
	private PessoaFisicaDao pessoaFisicaDao;
	@Autowired
	private ReservaTipoDao reservaTipoDao;
	
	@PostConstruct
	public void init() {
		super.setDao(reservaDao);
		super.setClazz(Reserva.class);
	}

	@RequestMapping(value = "/reservas", method=RequestMethod.GET)
	public String pageReserva() {
		reserva = new Reserva();
		return "administracao/reserva/reservas";
	}
	
	@RequestMapping(value = "/reservas_agenda", method=RequestMethod.GET)
	public String pageAgenda() {
		return "administracao/reserva/reservas_agenda"; 
	}
		
	
	@SuppressWarnings("unused")
	private class InformacaoTabela{
		private Integer codigo;
		private String reserva;
		private String situacao;
		private String apartamento;
		private String acao;
	}
	
	@RequestMapping(value = "/reservas/lista", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@Transactional
	public String getLista(HttpServletRequest request) {
		List<InformacaoTabela> listaInfo = new ArrayList<InformacaoTabela>();
		InformacaoTabela info = new InformacaoTabela();
		Integer totalRegistro = 0;
		try {
			boolean status = false;
			totalRegistro = reservaDao.findAll().size();
			for (Reserva reserva : reservaDao.findAll()) {
				info = new InformacaoTabela();
				info.codigo = reserva.getIdReserva();
				String reservaString = FacesUtil.converteData(reserva.getDataPretencaoInicio(),1) +" <br/>a "+
								FacesUtil.converteData(reserva.getDataPretencaoFim(), 1);	
				info.reserva = reservaString;
				info.situacao = reserva.getReservaTipo().getNome();
				info.apartamento = reserva.getApartamento().toString();
				status = reserva.isStatus();
				String checked = null;
				if(status)
					checked = "checked";
				String botaoStatus = "<div class=\"ios-switch primary item-status\">"+
						"<div class=\"switch\" id=\"status-"+info.codigo+"\">"+
							"<input type=\"checkbox\" "+checked+" onclick=\"getAcao("+info.codigo+",'status')\" onchange=\"getAcao("+info.codigo+",'status')\" style=\""+status+"\" />"+
						"</div>"+
					 "</div>";
				String botaoEditar = " <a data-original-title=\"Edit\" href=\"#\" "
					+ " class=\"ui-commandlink ui-widget btn btn-primary btn-xs\" style=\"margin-left:5px;\" aria-label=\"Edit\" "
					+ "onclick=\"getAcao("+info.codigo+",'alterar');return false;\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"Editar\">"
					+ "<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span></a> ";
				String botaoExluir = " <a data-original-title=\"Excluir\" href=\"#\" "
					+ " class=\"ui-commandlink ui-widget btn btn-danger btn-xs\" style=\"margin-left:5px;\" aria-label=\"Excluir\" "
					+ "onclick=\"getAcao("+info.codigo+",'excluir')\" data-confirm=\"Deseja excluir o registro "+info.codigo+" ?\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"Excluir\">"
					+ "<span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></a> ";
				
				if(FacesUtil.hasRole("ADMIN"))
					info.acao = botaoEditar + botaoExluir;
				else
					info.acao = botaoEditar;
				listaInfo.add(info);
			}
		} catch (Exception ex) {
			log.debug(ex.getMessage());
		}
		Integer sEcho = totalRegistro;
		Integer iTotalRecords = totalRegistro;
		Integer iTotalDisplayRecords = totalRegistro;
		Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();
        try {
            jsonResponse.addProperty("sEcho", sEcho);
            jsonResponse.addProperty("iTotalRecords", iTotalRecords);
            jsonResponse.addProperty("iTotalDisplayRecords", iTotalDisplayRecords);
            jsonResponse.add("aaData", gson.toJsonTree(listaInfo));
        }catch (JsonIOException e) {
            log.debug(e.getMessage());
        }
        return jsonResponse.toString();
	}
	
	@RequestMapping(value = "/reservas/acao", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String acao(@RequestBody String value){
		setId(Integer.parseInt(value));
		JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("msg","OK");
        return jsonResponse.toString();
	}
	
	@PreAuthorize("hasPermission(#user, 'DELETE')")
	@Transactional
	@RequestMapping(value = "/reservas/deleteall", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String deleteAll(@RequestBody JsonNode value){
		String registros = null;
		JsonObject jsonResponse = new JsonObject();
		JsonNode marksNode = value.path("myArray");
		if(marksNode.size()>1){
			Iterator<JsonNode> iterator = marksNode.elements();
		    StringBuilder excluidos = new StringBuilder();
		    while (iterator.hasNext()) {
	            JsonNode marks = iterator.next();
	            try {
	            	Integer id = Integer.parseInt(marks.toString());
            		reservaDao.deleteById(id);
	            	excluidos.append(marks.toString());
	            	excluidos.append(",");
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
	        }
		    if(excluidos.length()>0){
		    	registros = excluidos.deleteCharAt(excluidos.length()-1).toString();
		    }
		    jsonResponse.addProperty("msg","Os registros: "+registros+" foram exluidos!");
		    jsonResponse.addProperty("update",true);
		}else
			jsonResponse.addProperty("msg","Selecione mais de 1 registro para exluir,<br/> nenhum registro foi exluido!");
		
	    return jsonResponse.toString();
	}
	
	@Transactional
	@PreAuthorize("hasPermission(#user, 'SAVE') or hasPermission(#user, 'UPDATE')")
	public void saveOrUpdate(){
		String title = null;
		try {
			
			boolean liberaAcesso = false;
			
			if(UsuarioSistemaService.getUsername().equals("adm"))
				liberaAcesso = true;
			
			//SITUACAO
			if(reservaTipo==null){
	    		MessagesUtil.error("Selecione uma situação");
	    		return;
	    	}else if(
	    			reserva.getIdReserva()!=null &&
	    			( reserva.getReservaTipo().getNome().equals("Cancelado")
	    			|| reserva.getReservaTipo().getNome().equals("CheckOut"))
	    			&& !liberaAcesso ){
	    		MessagesUtil.error("Esta reserva está \""+reserva.getReservaTipo().getNome()+"\",<br/> só pode ser alterado para \""+reservaTipo+"\"<br/> pelo administrador.");
	    		return;
	    	}
	    	ReservaTipo reservaTipoReturn = (ReservaTipo) reservaTipoDao.findByField("nome",reservaTipo);
	    	reserva.setReservaTipo(reservaTipoReturn);
	    	
	    	//CLIENTE
			if(cliente==null){
	    		MessagesUtil.error("Selecione um cliente");
	    		return;
	    	}
	    	PessoaFisica clienteReturn = (PessoaFisica) pessoaFisicaDao.findByField("nome",cliente);
	    	reserva.setCliente(clienteReturn);
			
			//APARTAMENTO
			if(apartamento==null){
	    		MessagesUtil.error("Selecione um apartamento");
	    		return;
	    	}
			String[] apartamentoArray = apartamento.split("-");
			Predio predio = (Predio) predioDao.findByField("nome", apartamentoArray[0]);
			DetachedCriteria criteria = DetachedCriteria.forClass(Apartamento.class);
			criteria.add( Restrictions.eq( "predio",predio) );
			criteria.add( Restrictions.eq( "identificador",apartamentoArray[1] ) );
			List<Apartamento> listaApartamentos = apartamentoDao.findCriteria(criteria);
			Apartamento apartamentoReturn = new Apartamento();
			for (Apartamento apartamento : listaApartamentos) {
				apartamentoReturn = apartamento;
			}
	    	//Apartamento apartamentoReturn = (Apartamento) apartamentoDao.findById(Integer.parseInt(apartamento));
			reserva.setApartamento(apartamentoReturn);
	    	
			///ESTACIONAMENTOS
			Set<Estacionamento> setArray = new HashSet<Estacionamento>();
			if( estacionamentos!=null ){
				String[] stringArray = estacionamentos.split(",");
				Estacionamento estacionamento = new Estacionamento();
				for (String string : stringArray) {
					estacionamento = (Estacionamento) estacionamentoDao.findByField("identificador", string);
					setArray.add(estacionamento);
				}
			}else{
				setArray = new HashSet<Estacionamento>();
			}
			reserva.setEstacionamentos(setArray);
			//SAVE OR UPDATE
			if(reserva.getIdReserva()!=null){
				title = "atualizado";
				update(reserva);
			}else{
				title = "adicionado";
				save(reserva);
			}
			MessagesUtil.sucess("O registro \""+reserva+"\" foi "+title+".");
			reserva = new Reserva();
			FacesUtil.fecharModal("modalReserva");
			FacesUtil.atualizaTable("formDataTable:dataTableBlock");
			RequestContext.getCurrentInstance().update("formReserva");
			if(FacesUtil.findComponent("formPerfil:selectPermissoes"))
				RequestContext.getCurrentInstance().update("formPerfil:selectPermissoes");
			if(FacesUtil.findComponent("barraTopCaixa"))
				RequestContext.getCurrentInstance().update("barraTopCaixa");
		} catch (Exception e) {
			log.debug("ERRO SALVAR RESERVA");
			MessagesUtil.error("O registro \""+reserva+"\" NÂO foi "+title+".");
		}
	}

	@PreAuthorize("hasPermission(#user, 'DELETE')")
	public void excluir() {
		Reserva reserva = new Reserva();
		try {
			reserva = (Reserva) reservaDao.findByField("id",this.id);
			if(reserva==null){
				MessagesUtil.error("registro "+this.id+" não encontrado.");
			}else{
	    		super.delete(reserva);
				FacesUtil.atualizaTable("formDataTable:dataTableBlock");
	    	}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		this.id = null;
	}
	
	@Transactional
	@PreAuthorize("hasPermission(#user, 'UPDATE')")
	public void alterar(){
		try {
			reserva = (Reserva) reservaDao.findById(this.id);
			reservaMovimentacao = new ReservaMovimentacao();
			addMovimentacoes = reserva.getReservaMovimentacoes();
			cliente = reserva.getCliente().toString();
			apartamento = reserva.getApartamento().getPredio().getNome()+"-"+reserva.getApartamento().getIdentificador();
			reservaTipo = reserva.getReservaTipo().getNome();
			if(reserva.getEstacionamentos().size()>0){
				StringBuilder builder = new StringBuilder();
				for (Estacionamento estacionamento : reserva.getEstacionamentos()) {
					builder.append(estacionamento.getIdentificador());
					builder.append(",");
				}
				estacionamentos = builder.toString();
			}else{
				estacionamentos = null;
			}
			RequestContext.getCurrentInstance().update("formReserva");
			FacesUtil.abrirModal("modalReserva");
		} catch (Exception e) {
			log.debug(e.getCause());
			log.debug(e.getMessage());
		}
		this.id = null;
	}

	@PreAuthorize("hasPermission(#user, 'SAVE')")
	public void add(){
		reserva = new Reserva();
		reservaMovimentacao = new ReservaMovimentacao();
		addMovimentacoes = new HashSet<ReservaMovimentacao>();
		estacionamentos = null;
		apartamento = null;
		cliente = null;
		reservaTipo = null;
		RequestContext.getCurrentInstance().update("formReserva");
		FacesUtil.abrirModal("modalReserva");
	}
	
	@Transactional
	@PreAuthorize("hasPermission(#user, 'UPDATE')")
	public void status() {
		String title = null;
		try {
			reserva = (Reserva) reservaDao.findById(this.id);
			if(reserva.isStatus()){
				title = "desativado";
				reserva.setStatus(false);
			}else{
				title = "ativado";
				reserva.setStatus(true);
			}
			reservaDao.update(reserva);
			if(title.equals("desativado"))
				MessagesUtil.warn(" \""+reserva+"\" foi "+title+".");
			else
				MessagesUtil.sucess(" \""+reserva+"\" foi "+title+".");
			FacesUtil.atualizaTable("formDataTable:dataTableBlock");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
	
	public void addMovimentacao(){
		if(caixaService.getCaixa().getDataCaixa()==null){
			MessagesUtil.error(" Para efetuar movimentação desta reserva é necessário abrir o caixa.");
			return;
		}else if(reservaMovimentacao.getTipoPagamento()!=null && reservaMovimentacao.getValor()!=null){
				reservaMovimentacao.setCaixa(caixaService.getCaixa());
				addMovimentacoes.add(reservaMovimentacao);
				reserva.setReservaMovimentacoes(addMovimentacoes);
				reservaMovimentacao = new ReservaMovimentacao();
		}
	}
	
	public String valorDiaria(){
    	if(reserva!=null && reserva.getApartamento().getValorDiaria()!=null){
    		BigDecimal tmp = reserva.getApartamento().getValorDiaria();
    		String valor = dfMoeda.format( tmp );
    		return "<br/> <b> Valor diária normal: </b> R$ "+valor;
    	}
    	return null; 
    }
	
	public String valorReserva(){
    	if( reserva!=null && reserva.getDataPretencaoInicio()!=null && reserva.getDataPretencaoFim()!=null && reserva.getApartamento().getValorDiaria()!=null){
    		double myDouble = FacesUtil.diferencaEmDias( reserva.getDataPretencaoInicio(),reserva.getDataPretencaoFim() );
    		BigDecimal tmp = reserva.getApartamento().getValorDiaria();
    		tmp = tmp.multiply(new BigDecimal(myDouble));
    		String valor = dfMoeda.format( tmp );
    		//return "<br/> <b> Valor reserva: </b> R$ "+valor+" | "+ String.format( "%.2f", myDouble ) +" dia(s)";
    		return "<br/> <b> DIAS: </b> "+ String.format( "%.2f", myDouble ) +" dia(s)";
    	}
    	return null; 
    }
    
    public String valorPago(){
    	if( reserva!=null && reserva.getReservaMovimentacoes().size()>0 
    			&& reserva.getDataPretencaoInicio()!=null 
    			&& reserva.getDataPretencaoFim()!=null 
    			&& reserva.getApartamento().getValorDiaria()!=null){
    		double myDouble = FacesUtil.diferencaEmDias( reserva.getDataPretencaoInicio(),reserva.getDataPretencaoFim() );
    		BigDecimal tmp = reserva.getApartamento().getValorDiaria();
    		tmp = tmp.multiply(new BigDecimal(myDouble));
    		BigDecimal tmpSaldo = tmp;
    		for (ReservaMovimentacao reservaMovimentacao : reserva.getReservaMovimentacoes()) {
				if(reservaMovimentacao.getTipoPagamento().equals("devolucao") )
					tmpSaldo = tmpSaldo.add(reservaMovimentacao.getValor());
				else
					tmpSaldo = tmpSaldo.subtract(reservaMovimentacao.getValor());
			}
    		tmp = tmp.subtract(tmpSaldo);
    		String valorAberto = dfMoeda.format( tmpSaldo );
    		String valorPago = dfMoeda.format( tmp );
    		//return "<br/> <b> SALDO ABERTO: </b> R$ "+valorAberto+" | <b>PAGO:</b> R$ "+valorPago;
    		return "<br/> <b>VALOR PAGO:</b> R$ "+valorPago;
    	}
    	return null;
    }
    
	public void atualizaDatas(){
		try {
			log.debug(apartamento);
			//log.debug(reserva.getApartamento());
			//log.debug("---");
	    	Date diaria = new Date();
	    	Date dataInicio = reserva.getDataPretencaoInicio();
	    	Date dataFim = reserva.getDataPretencaoFim();
	    	if(apartamento==null){
	    		//MessagesUtil.error("Selecione um apartamento");
	    		//reserva.setDataPretencaoInicio(null);
	    		//reserva.setDataPretencaoFim(null);
	    		//return;
	    	}else{
	    		String[] apartamentoArray = apartamento.split("-");
				Predio predio = (Predio) predioDao.findByField("nome", apartamentoArray[0]);
				DetachedCriteria criteria = DetachedCriteria.forClass(Apartamento.class);
				criteria.add( Restrictions.eq( "predio",predio) );
				criteria.add( Restrictions.eq( "identificador",apartamentoArray[1] ) );
				List<Apartamento> listaApartamentos = apartamentoDao.findCriteria(criteria);
				Apartamento apartamentoReturn = new Apartamento();
				for (Apartamento apartamento : listaApartamentos) {
					apartamentoReturn = apartamento;
				}
		    	//Apartamento apartamentoReturn = (Apartamento) apartamentoDao.findById(Integer.parseInt(apartamento));
				reserva.setApartamento(apartamentoReturn);
	    	}
	    	if(dataInicio!=null && dataInicio.before(diaria)){
	    		MessagesUtil.error("Data inicio tem que ser maior que data atual: "+FacesUtil.converteData(null,1));
	    		reserva.setDataPretencaoInicio(null);
	    		reserva.setDataPretencaoFim(null);
	    	}else if(dataInicio!=null){
	    		diaria.setTime(dataInicio.getTime() + (1 * 24 * 60 * 60 * 1000));
	    		reserva.setDataPretencaoFim(diaria);
	    	}else if(dataFim!=null && dataInicio==null){
	    		reserva.setDataPretencaoFim(null);
	    		MessagesUtil.error("Selecione a data inicio");
	    	}else if(dataFim!=null && dataInicio!=null && dataFim.before(dataInicio)){
	    		MessagesUtil.error("Data fim deve ser maior que data incio.");
	    	}
	    	RequestContext.getCurrentInstance().update("formReserva:pretencaoID");
	    	RequestContext.getCurrentInstance().execute("$('.datetimepicker').hide()");
	    	//if(reserva.getDataPretencaoInicio()!=null && reserva.getDataPretencaoFim()!=null && reserva.getApartamento()!=null)
	    		RequestContext.getCurrentInstance().update("formReserva:valores");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
    }
	
	private Integer id = null;
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	
	private Reserva reserva;
	public Reserva getReserva() {
		return reserva;
	}
	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	
	private ReservaMovimentacao reservaMovimentacao;
	public ReservaMovimentacao getReservaMovimentacao() {
		return reservaMovimentacao;
	}
	public void setReservaMovimentacao(ReservaMovimentacao reservaMovimentacao) {
		this.reservaMovimentacao = reservaMovimentacao;
	}
	
	private Set<ReservaMovimentacao> addMovimentacoes;
	
	DecimalFormat dfMoeda = new DecimalFormat("#,###.00");
	
	/*AGENDA -----------------------------------------------------------------------*/
	
	@Transactional
	public void initAgenda(){
		try {
			String titulo;
			eventModel = new DefaultScheduleModel();
			for (Reserva reserva : reservaDao.findAll()) {
				if(reserva.getReservaTipo().getNome().equals("Pré-reserva")){
					
					evento = new DefaultScheduleEvent();
		            
					//evento.setAllDay(agenda.isDiaTodo());
		            evento.setStartDate(reserva.getDataPretencaoInicio());
		            evento.setEndDate(reserva.getDataPretencaoFim());
		            
		            titulo = null;
		            if(reserva.getCliente().toString().length()>15)
		            	titulo = reserva.getCliente().toString().substring(0, 12)+"...";
		            else
		            	titulo = reserva.getCliente().toString();
		            evento.setTitle(titulo);
		            
		            evento.setData(reserva);
		            evento.setEditable(true);
		            eventModel.addEvent(evento);
					
				}
			}
			
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
	
	private DefaultScheduleEvent evento = new DefaultScheduleEvent();
	public DefaultScheduleEvent getEvento() {
		return evento;
	}
	public void setEvento(DefaultScheduleEvent evento) {
		this.evento = evento;
	}
	
	private ScheduleModel lazyEventModel;
	public ScheduleModel getLazyEventModel() {
		return lazyEventModel;
	}
	public void setLazyEventModel(ScheduleModel lazyEventModel) {
		this.lazyEventModel = lazyEventModel;
	}
	
	private ScheduleModel eventModel;
	public ScheduleModel getEventModel() {
		return eventModel;
	}
	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}
	
	//@RequestMapping(value = "/agenda", method=RequestMethod.POST)
	public void onDateSelect(SelectEvent selectEvent) {
		log.debug("data selecionada: "+selectEvent);
	}

	//@RequestMapping(value = "/agenda", method=RequestMethod.POST)
	public void onEventSelect(SelectEvent selectEvent) {
		log.debug("data selecionada: "+selectEvent);
	}
	
	/*FIM AGENDA ----------------------------------------------------------------------------*/

	private Date pretencaoInicio = new Date();
	public Date getPretencaoInicio() {
		return pretencaoInicio;
	}
	public void setPretencaoInicio(Date pretencaoInicio) {
		this.pretencaoInicio = pretencaoInicio;
	}
	
    private Date pretencaoFim = new Date();
    public Date getPretencaoFim() {
		return pretencaoFim;
	}
    public void setPretencaoFim(Date pretencaoFim) {
		this.pretencaoFim = pretencaoFim;
	}
    
    private String estacionamentos;
    public String getEstacionamentos() {
		return estacionamentos;
	}
    public void setEstacionamentos(String estacionamentos) {
		this.estacionamentos = estacionamentos;
	}
    
    private String reservaTipo;
    public String getReservaTipo() {
		return reservaTipo;
	}
    public void setReservaTipo(String reservaTipo) {
		this.reservaTipo = reservaTipo;
	}
    
    private String cliente;
    public void setCliente(String cliente) {
		this.cliente = cliente;
	}
    public String getCliente() {
		return cliente;
	}
    
    private String apartamento;
    public String getApartamento() {
		return apartamento;
	}
    public void setApartamento(String apartamento) {
		this.apartamento = apartamento;
	}

}
