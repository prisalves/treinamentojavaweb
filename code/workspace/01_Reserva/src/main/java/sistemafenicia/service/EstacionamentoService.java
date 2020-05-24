package sistemafenicia.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import sistemafenicia.dao.EstacionamentoDao;
import sistemafenicia.dao.ReservaDao;
import sistemafenicia.dao.ReservaTipoDao;
import sistemafenicia.model.Estacionamento;
import sistemafenicia.model.PessoaFisica;
import sistemafenicia.model.Reserva;
import sistemafenicia.model.ReservaTipo;
import sistemafenicia.utils.FacesUtil;
import sistemafenicia.utils.MessagesUtil;

@ManagedBean
@SessionScoped
@Service
@Controller
public class EstacionamentoService extends basicService<Estacionamento> {

	static final Logger log = Logger.getLogger(EstacionamentoService.class);

	@Autowired
	private EstacionamentoDao estacionamentoDao;
	@Autowired
	private ReservaDao reservaDao;
	@Autowired
	private ReservaTipoDao reservaTipoDao;
	
	Estacionamento estacionamento;

	@PostConstruct
	public void init() {
		super.setDao(estacionamentoDao);
		super.setClazz(Estacionamento.class);
	}

	@RequestMapping(value = "/estacionamentos", method=RequestMethod.GET)
	public String page() {
		estacionamento = new Estacionamento();
		return "administracao/reserva/estacionamentos";
	}
	
	private String filtroSituacao = "reserva";
	public String getFiltroSituacao() {
		return filtroSituacao;
	}
	public void setFiltroSituacao(String filtroSituacao) {
		this.filtroSituacao = filtroSituacao;
	}
	
	private Date filtroData = new Date();
	public Date getFiltroData() {
		return filtroData;
	}
	public void setFiltroData(Date filtroData) {
		this.filtroData = filtroData;
	}
	
	public void atualizaTable(){
		if (filtroData==null) {
			MessagesUtil.error("Selecione a data");
			return;
		}
		RequestContext.getCurrentInstance().execute("updateDataTable()");
		RequestContext.getCurrentInstance().execute("$('.datetimepicker').hide()");
	}
	
	@SuppressWarnings("unused")
	private class InformacaoTabela{
		private String identificador;
		private String apartamento;
		private String cliente;
		private String carro;
		private String reserva;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/estacionamentos/lista", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@Transactional
	public String getLista(HttpServletRequest request) {
		List<InformacaoTabela> listaInfo = new ArrayList<InformacaoTabela>();
		InformacaoTabela info = new InformacaoTabela();
		List<Estacionamento> listaEstacionamento = new ArrayList<Estacionamento>();
		int totalRegistro = 0;
		boolean reservaHoje = false;
		StringBuilder estacionamentosString = new StringBuilder();
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(Reserva.class);
			if(filtroData!=null){
				criteria.add( Restrictions.ge( "dataPretencaoFim",filtroData) );
				criteria.add( Restrictions.lt( "dataPretencaoInicio",filtroData ) );
			}
			if(!filtroSituacao.equals("todos")){
				ReservaTipo reservaTipo = (ReservaTipo) reservaTipoDao.findByField("nome", filtroSituacao);
				criteria.add(Restrictions.eq("reservaTipo",reservaTipo));
			}
			List<Reserva> listaReservas = reservaDao.findCriteria(criteria);
			//log.debug(listaReservas);
			/*for (Reserva reserva : listaReservas) {
				for (Estacionamento estacionamento : reserva.getEstacionamentos()) {
					listaEstacionamento.add(estacionamento);
				}
			}*/
			totalRegistro  = listaReservas.size();
			for (Reserva reserva : listaReservas) {
				info = new InformacaoTabela();
				PessoaFisica cliente = (PessoaFisica) reserva.getCliente();
				info.cliente = cliente.getNome();
				info.carro = "Placa: "+cliente.getCarroPlaca()+"<br/> - "+cliente.getCarroDescricao();
				info.apartamento = reserva.getApartamento().toString();
				String context = request.getContextPath();
				info.reserva = "<a href=\""+context+"/reservas\">"+reserva.getIdReserva().toString()+"</a>";
				estacionamentosString = new StringBuilder();
				for (Estacionamento estacionamento : reserva.getEstacionamentos()) {
					estacionamentosString.append(estacionamento.getIdentificador()+",");
				}
				if(estacionamentosString.length()>0){
					info.identificador = estacionamentosString.deleteCharAt(estacionamentosString.length()-1).toString();
					listaInfo.add(info);
			    }
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

	@PreAuthorize("hasPermission(#user, 'SAVE') or hasPermission(#user, 'UPDATE')")
	@Transactional
	public void saveOrUpdate() {
		try {
			String title = null;
			if(estacionamento.getIdEstacionamento()!=null){
				update(estacionamento);
				title = "atualizado";
			}else{
				save(estacionamento);
				title = "adicionado";
			}
			MessagesUtil.sucess("O registro \""+estacionamento+"\" foi "+title+".");
			estacionamento = new Estacionamento();
			FacesUtil.fecharModal("modalEstacionamento");
			//RequestContext.getCurrentInstance().update("formDataTable");
			RequestContext.getCurrentInstance().update("formEstacionamento");
			if(FacesUtil.findComponent("formReserva:selectEstacionamentos"))
				RequestContext.getCurrentInstance().update("formReserva:selectEstacionamentos");
		}catch(Exception e){
			log.debug(e.getMessage());
			log.debug("ERRO SALVAR");
	    }
	}

	@PreAuthorize("hasPermission(#user, 'UPDATE')")
	@Transactional
	public void alterar(Estacionamento entity){
		try {
			estacionamento = (Estacionamento) estacionamentoDao.findById(entity.getIdEstacionamento());
			RequestContext.getCurrentInstance().update("formEstacionamento");
			FacesUtil.abrirModal("modalEstacionamento");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	@Override
	@PreAuthorize("hasPermission(#user, 'DELETE')")
	public void delete(Estacionamento entity) {
		super.delete(entity);
		RequestContext.getCurrentInstance().update("formDataTable");
	}

	@PreAuthorize("hasPermission(#user, 'SAVE')")
	public void add(){
		estacionamento = new Estacionamento();
		RequestContext.getCurrentInstance().update("formEstacionamento");
		FacesUtil.abrirModal("modalEstacionamento");
	}

	public void setEstacionamento(Estacionamento estacionamento) {
		this.estacionamento = estacionamento;
	}
	public Estacionamento getEstacionamento() {
		return estacionamento;
	}


}
