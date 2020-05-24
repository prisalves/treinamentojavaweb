package sistemafenicia.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sistemafenicia.dao.CaixaDao;
import sistemafenicia.dao.MovimentacaoDao;
import sistemafenicia.dao.ReservaMovimentacaoDao;
import sistemafenicia.model.Caixa;
import sistemafenicia.model.Movimentacao;
import sistemafenicia.model.ReservaMovimentacao;
import sistemafenicia.utils.FacesUtil;
import sistemafenicia.utils.MessagesUtil;

@ManagedBean
@SessionScoped
@Service("caixaService")
@Controller
public class CaixaService extends basicService<Caixa> {

	static final Logger log = Logger.getLogger(CaixaService.class);

	@Autowired
	CaixaDao caixaDao;
	@Autowired
	UsuarioSistemaService usuarioSistemaService;
	@Autowired
	MovimentacaoDao movimentacaoDao;
	@Autowired
	ReservaMovimentacaoDao reservaMovimentacaoDao;

	@PostConstruct
	public void init() {
		super.setDao(caixaDao);
		super.setClazz(Caixa.class);
		try {
			for (Caixa caixanew : caixaDao.findAll()) {
				if(caixanew.isStatus() && caixanew.getOperador()==usuarioSistemaService.getUsuarioSistema())
					caixa = caixanew;
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	@RequestMapping(value = "/caixas", method=RequestMethod.GET)
	public String pageCaixas() {
		return "administracao/caixa/caixas";
	}
	
	public BigDecimal saldoAtual(){
		BigDecimal saldoAtual = new BigDecimal(0);
		try {
			if(caixa.getDataCaixa()!=null){
				saldoAtual = saldoAtual.add(caixa.getSaldo());
				for (ReservaMovimentacao reservaMovimentacao : reservaMovimentacaoDao.findAllByField("caixa",caixa)) {
					if (FacesUtil.converteData(reservaMovimentacao.getCaixa().getDataCaixa(),null).equals(FacesUtil.converteData(caixa.getDataCaixa(), null))) {
						if(reservaMovimentacao.getTipoPagamento().equals("devolucao") || reservaMovimentacao.getTipoPagamento().equals("desconto")){
							saldoAtual = saldoAtual.subtract(reservaMovimentacao.getValor());
						}else{
							saldoAtual = saldoAtual.add(reservaMovimentacao.getValor());
						}
					}
				}
				for (Movimentacao movimentacao : movimentacaoDao.findAllByField("caixa",caixa)) {
					if(movimentacao.getCaixa().getOperador().getIdUsuario()==usuarioSistemaService.getUsuarioSistema().getIdUsuario()
							//&& movimentacao.getCaixa().getDataCaixa().equals(caixa.getDataCaixa())
							&& FacesUtil.converteData(movimentacao.getCaixa().getDataCaixa(),null).equals(FacesUtil.converteData(caixa.getDataCaixa(), null))
					  ){
						saldoAtual = saldoAtual.subtract(movimentacao.getValor());
					}
				}
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return saldoAtual;
	}
	
	//@PreAuthorize("hasPermission(#user, 'SAVE') or hasPermission(#user, 'UPDATE')")
	@Transactional
	public boolean getCaixaDia() {
		Date data_atual = FacesUtil.formataData(null,4);
		Caixa caixa = new Caixa();
		try {
			caixa = (Caixa) caixaDao.findByField("dataCaixa",data_atual);
			if(caixa!=null)
				return true;
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return false;
	}
	
	public List<Caixa> getLista(){
		List<Caixa> lista = new ArrayList<Caixa>();
		try {
			lista = caixaDao.findAllByField("operador", usuarioSistemaService.getUsuarioSistema());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return lista;
	}
	
	@PreAuthorize("hasPermission(#user, 'SAVE') or hasPermission(#user, 'UPDATE')")
	@Transactional
	public void abrirCaixa(Integer id) {
		if(caixa.getIdCaixa()!=null){
			MessagesUtil.error("Caixa do dia "+caixa+" está aberto,<br/> favor fechar o caixa para abrir outro caixa.");
			return;
		}else{
			try {
				caixa = (Caixa) caixaDao.findById(id);
				caixa.setStatus(true);
				super.update(caixa);
				MessagesUtil.sucess("Caixa aberto com sucesso.");
				RequestContext.getCurrentInstance().update("formDataTable");
				RequestContext.getCurrentInstance().update("barraTopCaixa");
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
	}
	
	@PreAuthorize("hasPermission(#user, 'SAVE') or hasPermission(#user, 'UPDATE')")
	@Transactional
	public void fecharCaixa() {
		if(caixa.getIdCaixa()!=null){
			
			BigDecimal saldoAtualCaixa = new BigDecimal(0);
			saldoAtualCaixa = saldoAtualCaixa.add(caixa.getSaldo());
			saldoAtualCaixa = saldoAtualCaixa.add(this.saldoAtual());
			caixa.setSaldo(saldoAtualCaixa);
			
			if(caixa.getSaldo().signum()==-1){
				MessagesUtil.error("Caixa com saldo negativo, verifique o caixa antes de fechar.");
				return;
			}
			try {
				caixa.setStatus(false);
				caixa.setDataFechamento(new Date());
				super.update(caixa);
				caixa = new Caixa();
				MessagesUtil.sucess("Caixa fechado com sucesso.");
				RequestContext.getCurrentInstance().update("formDataTable");
				RequestContext.getCurrentInstance().update("barraTopCaixa");
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
	}

	@PreAuthorize("hasPermission(#user, 'SAVE') or hasPermission(#user, 'UPDATE')")
	@Transactional
	public void criarCaixa() {
		try {
			
			/*Date dataAtual = FacesUtil.formataData(null,null);
			String dataCaixaString = FacesUtil.converteData(caixa.getDataCaixa(),null);
			Date dataCaixa = FacesUtil.formataData(dataCaixaString,null);
			double myDouble = FacesUtil.diferencaEmDias(dataAtual, dataCaixa);
			Integer dia = (int) Math.round(myDouble);*/
			
			if(getCaixaDia()){
				MessagesUtil.error("Caixa do dia "+FacesUtil.converteData(null,null)+" já esta criado.");
				return;
			}else{
				caixa = new Caixa();
				caixa.setOperador(usuarioSistemaService.getUsuarioSistema());
				caixa.setDataCaixa(new Date());
				super.save(caixa);
				MessagesUtil.sucess("Caixa criado com sucesso.");
				RequestContext.getCurrentInstance().update("formDataTable");
				RequestContext.getCurrentInstance().update("barraTopCaixa");
			}
		}catch(Exception e){
			//log.debug(e.getMessage());
			log.debug("ERRO CRIAR CAIXA");
	    }
	}

	/*@PreAuthorize("hasPermission(#user, 'UPDATE')")
	@Transactional
	public void alterar(Caixa novaCaixa){
		try {
			caixa = (Caixa) caixaDao.findById(novaCaixa.getIdCaixa());
			RequestContext.getCurrentInstance().update("formCaixa");
			FacesUtil.abrirModal("modalCaixa");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}*/

	/*@Override
	@PreAuthorize("hasPermission(#user, 'DELETE')")
	public void delete(Caixa entity) {
		super.delete(entity);
		RequestContext.getCurrentInstance().update("formDataTable");
	}*/


	private Caixa caixa = new Caixa();
	public Caixa getCaixa() {
		return caixa;
	}
	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

}
