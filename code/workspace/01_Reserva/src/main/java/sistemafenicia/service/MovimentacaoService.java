package sistemafenicia.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sistemafenicia.dao.ApartamentoDao;
import sistemafenicia.dao.CaixaDao;
import sistemafenicia.dao.MovimentacaoDao;
import sistemafenicia.dao.PredioDao;
import sistemafenicia.model.Apartamento;
import sistemafenicia.model.Caixa;
import sistemafenicia.model.Movimentacao;
import sistemafenicia.model.Predio;
import sistemafenicia.utils.FacesUtil;
import sistemafenicia.utils.MessagesUtil;

@ManagedBean
@RequestScoped
@Service
@Controller
public class MovimentacaoService extends basicService<Movimentacao> {

	static final Logger log = Logger.getLogger(MovimentacaoService.class);

	@Autowired
	MovimentacaoDao movimentacaoDao;
	@Autowired
	ApartamentoDao apartamentoDao;
	@Autowired
	private PredioDao predioDao;
	@Autowired
	CaixaDao caixaDao;
	
	@Autowired
	CaixaService caixaService;

	@PostConstruct
	public void init() {
		super.setDao(movimentacaoDao);
		super.setClazz(Movimentacao.class);
	}

	@RequestMapping(value = "/movimentacoes", method=RequestMethod.GET)
	public String pageMovimentacoes() {
		return "administracao/caixa/movimentacoes";
	}
	
	@RequestMapping(value = "/movimentacoes_saida", method=RequestMethod.GET)
	public String pageMovimentacoesSaida() {
		return "administracao/caixa/movimentacoes_saida";
	}
	
	@RequestMapping(value = "/movimentacoes_entrada", method=RequestMethod.GET)
	public String pageMovimentacoesEntrada() {
		return "administracao/caixa/movimentacoes_entrada";
	}
	
	private String filtroApartamentos;
	public String getFiltroApartamentos() {
		return filtroApartamentos;
	}
	public void setFiltroApartamentos(String filtroApartamentos) {
		this.filtroApartamentos = filtroApartamentos;
	}
	
	private List<Movimentacao> filtered;
	public List<Movimentacao> getFiltered() {
		return filtered;
	}
	public void setFiltered(List<Movimentacao> filtered) {
		this.filtered = filtered;
	}
	
	private Set<Apartamento> arrayFiltroApartamentos;
	
	public void atualizaTable(){
		try {
			log.debug("ATUALIZA TABLE");
			if(filtroApartamentos==null){
				MessagesUtil.error("Selecione pelo menos um apartamento");
				return;
			}
			String[] stringArray = apartamentos.split(",");
			arrayFiltroApartamentos = new HashSet<Apartamento>();
			for (String string : stringArray) {
				Apartamento apartamento = (Apartamento) apartamentoDao.findByField("identificador",string);
				arrayFiltroApartamentos.add(apartamento);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
	
	public List<Movimentacao> getLista(){
		List<Movimentacao> lista = new ArrayList<Movimentacao>();
		try {
			/*DetachedCriteria criteria = DetachedCriteria.forClass(MovimentacaoService.class);
			String[] stringArray = apartamentos.split(",");
			Set<Movimentacao> setMovimentacoes = new HashSet<Movimentacao>();
			for (String string : movimentacaoArray) {
				Movimentacao movimentacao = (Movimentacao) permissaoDao.findByField("nome", string);
				setPermissoes.add(permissao);
			}
			if(arrayFiltroApartamento!=null){
				criteria.add(Restrictions.eq("situacao",filtroSituacao));
			}
			List<Movimentacao> listaDados = movimentacaoDao.findCriteria(criteria);
			*/
			Apartamento apartamento = null;
			if(apartamentos!=null)
				apartamento = (Apartamento) apartamentoDao.findByField("identificador",apartamentos);
			List<Movimentacao> listaDados = movimentacaoDao.findAll();
			for (Movimentacao movimentacao : listaDados) {
				//if(apartamentos!=null && movimentacao.getApartamentos().contains(o))
				if( ( movimentacao.getCaixa().getOperador().getLogin().equals(UsuarioSistemaService.getUsername())
						|| UsuarioSistemaService.getUsername().equals("adm") )
						//&& FacesUtil.converteData(movimentacao.getCaixa().getDataCaixa(),null).equals(FacesUtil.converteData(caixaService.getCaixa().getDataCaixa(), null))
					){
					//if(apartamento!=null && movimentacao.getApartamentos().contains(apartamento))
					lista.add(movimentacao);
				}
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return lista;
	}
	
	public BigDecimal getTotal(){
		BigDecimal total = new BigDecimal(0);
		for (Movimentacao movimentacao : getLista()) {
			total = total.add(movimentacao.getValor());
		}
		return total;
	}
	
	public void saida(){
		log.debug("SAIDA");
		apartamentos = null;
		movimentacao = new Movimentacao();
		RequestContext.getCurrentInstance().update("formSaida");
		FacesUtil.abrirModal("modalSaida");
	}
	
	@Transactional
	@PreAuthorize("hasPermission(#user, 'SAVE') or hasPermission(#user, 'UPDATE')")
	public void saveOrUpdateSaida(){
		String title = null;
		try {
			
			//CAIXA
			Caixa caixa = caixaService.getCaixa();
			if(caixa.getDataCaixa()==null){
				MessagesUtil.error("Nenhum caixa aberto");
				return;
			}
			movimentacao.setCaixa(caixa);
			
			///APARTAMENTOS
			if( apartamentos==null ){
				MessagesUtil.error("Selecione pelo menos um apartamento");
				return;
			}
			String[] stringArray = apartamentos.split(",");
			Set<Apartamento> arrayApartamentos = new HashSet<Apartamento>();
			Apartamento apartamentoReturn = new Apartamento();
			for (String string : stringArray) {
				String[] apartamentoArray = string.split("-");
				Predio predio = (Predio) predioDao.findByField("nome", apartamentoArray[0]);
				DetachedCriteria criteria = DetachedCriteria.forClass(Apartamento.class);
				criteria.add( Restrictions.eq( "predio",predio) );
				criteria.add( Restrictions.eq( "identificador",apartamentoArray[1] ) );
				List<Apartamento> listaApartamentos = apartamentoDao.findCriteria(criteria);
				apartamentoReturn = new Apartamento();
				for (Apartamento apartamento : listaApartamentos) {
					apartamentoReturn = apartamento;
				}
				//Apartamento apartamento = (Apartamento) apartamentoDao.findByField("identificador",string);
				arrayApartamentos.add(apartamentoReturn);
			}
			movimentacao.setApartamentos(arrayApartamentos);
			
			//SAVE OR UPDATE
			if(movimentacao.getIdMovimentacao()!=null){
				title = "atualizado";
				update(movimentacao);
			}else{
				title = "adicionado";
				save(movimentacao);
			}
			MessagesUtil.sucess("O registro \""+movimentacao+"\" foi "+title+".");
			movimentacao = new Movimentacao();
			FacesUtil.fecharModal("modalSaida");
			FacesUtil.atualizaTable("formDataTable");
			RequestContext.getCurrentInstance().update("formSaida");
			if(FacesUtil.findComponent("barraTopCaixa"))
				RequestContext.getCurrentInstance().update("barraTopCaixa");
		} catch (Exception e) {
			log.debug("ERRO SALVAR MOVIMENTACAO");
			MessagesUtil.error("O registro \""+movimentacao+"\" NÂO foi "+title+".");
		}
	}
	
	public void entrada(){
		log.debug("ENTRADA");
		this.tipoMovimentacao = "Entrada";
		RequestContext.getCurrentInstance().update("movimentacaoBlock");
	}
	
	private String tipoMovimentacao = null;
	public String getTipoMovimentacao() {
		return tipoMovimentacao;
	}
	public void setTipoMovimentacao(String tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}
	
	private String apartamentos;
	public String getApartamentos() {
		return apartamentos;
	}
	public void setApartamentos(String apartamentos) {
		this.apartamentos = apartamentos;
	}
	
	private Movimentacao movimentacao;
	public Movimentacao getMovimentacao() {
		return movimentacao;
	}
	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}



}
