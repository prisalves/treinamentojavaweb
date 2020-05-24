/*package sistemafenicia.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sistemafenicia.dao.ReservaDao;
import sistemafenicia.model.Reserva;
import sistemafenicia.model.ReservaMovimentacao;
import sistemafenicia.utils.FacesUtil;
import sistemafenicia.utils.MessagesUtil;

@ManagedBean
@RequestScoped
@Service
@Controller
//@RequestMapping(value={"/reserva", "/reserva/**"})
public class ReservaService2 extends basicService<Reserva> {

	static final Logger log = Logger.getLogger(ReservaService2.class);

	@Autowired
	ReservaDao reservaDao;
	
	Reserva reserva;
	ReservaMovimentacao reservaMovimentacao;
	Set<ReservaMovimentacao> addItens;
	DecimalFormat dfMoeda = new DecimalFormat("#,###.00");
	
	private ScheduleModel eventModel;

	@PostConstruct
	public void init() {
		super.setDao(reservaDao);
		super.setClazz(Reserva.class);
		eventModel = new DefaultScheduleModel();
		reserva = null;
	}

	@RequestMapping(value = "/reserva", method=RequestMethod.GET)
	public String page() {
		return "administracao/reserva/reservas";
	}
	
	@RequestMapping(value = "/agenda", method=RequestMethod.GET)
	public String pageAgenda() {
		return "administracao/reserva/agenda";
	}

	@PreAuthorize("hasPermission(#user, 'SAVE') or hasPermission(#user, 'UPDATE')")
	@Transactional
	public void saveOrUpdate() {
		try {
			
			if( reserva.getDataPretencaoFim().before(reserva.getDataPretencaoInicio()) ){
				MessagesUtil.error("A data final não pode ser menor que data inicial.");
				return;
			}
			if( reserva.getDataPretencaoInicio().before(new Date()) ){
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				MessagesUtil.error("A data inicial tem que ser maior que "+df.format(new Date()));
				return;
			}
			if( reserva.getQuantidadeAdulto()>reserva.getApartamento().getLimiteAdulto() ){
				MessagesUtil.error("O apartamento selecionado só aceita "+reserva.getApartamento().getLimiteAdulto()+" adulto(s).");
				return;
			}
			if( reserva.getQuantidadeCrianca()>reserva.getApartamento().getLimiteCrianca() ){
				MessagesUtil.error("O apartamento selecionado só aceita "+reserva.getApartamento().getLimiteCrianca()+" crianças(s).");
				return;
			}
			if( reserva.getQntVagas()>reserva.getApartamento().getQntVagas() ){
				MessagesUtil.error("O apartamento selecionado só aceita "+reserva.getApartamento().getQntVagas()+" vagas(s) para carro.");
				return;
			}
			if( reserva.isComAnimal()!=reserva.getApartamento().isAceitaAnimal() ){
				MessagesUtil.error("O apartamento selecionado não aceita animais.");
				return;
			}
				
			
			String title = null;
			if(reserva.getIdReserva()!=null){
				update(reserva);
				title = "atualizado";
			}else{
				save(reserva);
				title = "adicionado";
			}
			MessagesUtil.sucess("O registro \""+reserva+"\" foi "+title+".");
			reserva = new Reserva();
			FacesUtil.fecharModal("modalReserva");
			RequestContext.getCurrentInstance().update("formDataTable");
			RequestContext.getCurrentInstance().update("formReserva");
		}catch(Exception e){
			log.debug(e.getMessage());
			log.debug("ERRO SALVAR");
	    }
	}

	@PreAuthorize("hasPermission(#user, 'UPDATE')")
	@Transactional
	public void alterar(Reserva entity){
		try {
			reserva = (Reserva) reservaDao.findById(entity.getIdReserva());
			addItens = reserva.getReservaMovimentacoes();
			reservaMovimentacao = new ReservaMovimentacao();
			RequestContext.getCurrentInstance().update("formReserva");
			FacesUtil.abrirModal("modalReserva");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	@Override
	@PreAuthorize("hasPermission(#user, 'DELETE')")
	public void delete(Reserva entity) {
		super.delete(entity);
		RequestContext.getCurrentInstance().update("formDataTable");
	}

	@PreAuthorize("hasPermission(#user, 'SAVE')")
	public void add(){
		reserva = new Reserva();
		addItens = new HashSet<ReservaMovimentacao>();
		reservaMovimentacao = new ReservaMovimentacao();
		RequestContext.getCurrentInstance().update("formReserva");
		FacesUtil.abrirModal("modalReserva");
	}
	

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	public Reserva getReserva() {
		return reserva;
	}
	
	private Date pretencaoInicio = new Date();
    private Date pretencaoFim= new Date();

    public void ajustaData(SelectEvent event){
    	if(reserva.getDataPretencaoFim()==null)
    		reserva.setDataPretencaoFim(reserva.getDataPretencaoInicio());
    	if( reserva.getDataPretencaoFim().before(reserva.getDataPretencaoInicio()) )
    		reserva.setDataPretencaoInicio(reserva.getDataPretencaoFim());
        //pretencaoFim = pretencaoInicio;
    }
    
    public Date getPretencaoInicio() {
		return pretencaoInicio;
	}
    public void setPretencaoInicio(Date pretencaoInicio) {
		this.pretencaoInicio = pretencaoInicio;
	}
    public Date getPretencaoFim() {
		return pretencaoFim;
	}
    public void setPretencaoFim(Date pretencaoFim) {
		this.pretencaoFim = pretencaoFim;
	}
    
    
    public void addItem(){
		if(reservaMovimentacao.getMovimentacao()!=null && reservaMovimentacao.getValor()!=null){
				addItens.add(reservaMovimentacao);
				reserva.setReservaMovimentacoes(addItens);
				reservaMovimentacao = new ReservaMovimentacao();
		}
	}
	
    public ReservaMovimentacao getReservaMovimentacao() {
		return reservaMovimentacao;
	}
    public void setReservaMovimentacao(ReservaMovimentacao reservaMovimentacao) {
		this.reservaMovimentacao = reservaMovimentacao;
	}
    
    @SuppressWarnings("unchecked")
	public List<ReservaMovimentacao> listaMovimentacoes(){
    	Set<ReservaMovimentacao> listaMovimentacoes = new HashSet<ReservaMovimentacao>();
    	listaMovimentacoes = reserva.getReservaMovimentacoes();
    	List<ReservaMovimentacao> listaMovimentacoesArray = new ArrayList<ReservaMovimentacao>(listaMovimentacoes);
    	Collections.sort(listaMovimentacoesArray, new BeanComparator("tipoApagamento"));
		return listaMovimentacoesArray;
    }
    
    public static double diferencaEmDias(Date dataInicial, Date dataFinal){
    	double result = 0;
    	long diferenca = dataFinal.getTime() - dataInicial.getTime();
    	double diferencaEmDias = (diferenca /1000) / 60 / 60 /24; //resultado é diferença entre as datas em dias
    	long horasRestantes = (diferenca /1000) / 60 / 60 %24; //calcula as horas restantes
    	result = diferencaEmDias + (horasRestantes /24d); //transforma as horas restantes em fração de dias
    	return result;
    }
    
    public String valorDiaria(){
    	if(reserva!=null && reserva.getApartamento()!=null){
    		BigDecimal tmp = reserva.getApartamento().getValorDiaria();
    		String valor = dfMoeda.format( tmp );
    		return "<br/> <b> Valor diária: </b> R$ "+valor;
    	}
    	return null; 
    }
    
    public String valorReserva(){
    	if( reserva!=null && reserva.getDataPretencaoInicio()!=null && reserva.getDataPretencaoFim()!=null ){
    		double myDouble = diferencaEmDias( reserva.getDataPretencaoInicio(),reserva.getDataPretencaoFim() );
    		BigDecimal tmp = reserva.getApartamento().getValorDiaria();
    		tmp = tmp.multiply(new BigDecimal(myDouble));
    		String valor = dfMoeda.format( tmp );
    		return "<br/> <b> Valor reserva: </b> R$ "+valor+" | "+ String.format( "%.2f", myDouble ) +" dia(s)";
    	}
    	return null; 
    }
    
    public String valorPago(){
    	if( reserva!=null && reserva.getReservaMovimentacoes().size()>0 && reserva.getDataPretencaoInicio()!=null && reserva.getDataPretencaoFim()!=null){
    		
    		double myDouble = diferencaEmDias( reserva.getDataPretencaoInicio(),reserva.getDataPretencaoFim() );
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
    		return "<br/> <b> SALDO ABERTO: </b> R$ "+valorAberto+" | <b>PAGO:</b> R$ "+valorPago;
    	}
    	return null;
    }
    
    AGENDA -----------------------
    
    
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

	@RequestMapping(value = "/agenda", method=RequestMethod.POST)
	public void onEventSelect(SelectEvent selectEvent) {
		log.debug("data selecionada: "+selectEvent);
	}
	

}
*/