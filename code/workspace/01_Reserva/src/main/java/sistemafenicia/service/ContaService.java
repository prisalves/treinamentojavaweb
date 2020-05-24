package sistemafenicia.service;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sistemafenicia.dao.ContaDao;
import sistemafenicia.model.Conta;
import sistemafenicia.utils.FacesUtil;
import sistemafenicia.utils.MessagesUtil;

@ManagedBean
@RequestScoped
@Service
@Controller
@RequestMapping(value={"/pessoa", "/pessoa/**"})
public class ContaService extends basicService<Conta> {

	static final Logger log = Logger.getLogger(ContaService.class);

	@Autowired
	ContaDao contaDao;
	
	Conta conta;

	@PostConstruct
	public void init() {
		super.setDao(contaDao);
		super.setClazz(Conta.class);
	}

	@RequestMapping(value = "/conta", method=RequestMethod.GET)
	public String page() {
		conta = new Conta();
		return "administracao/pessoa/contas";
	}

	@PreAuthorize("hasPermission(#user, 'SAVE') or hasPermission(#user, 'UPDATE')")
	@Transactional
	public void saveOrUpdate() {
		try {
			String title = null;
			if(conta.getIdConta()!=null){
				update(conta);
				title = "atualizado";
			}else{
				save(conta);
				title = "adicionado";
			}
			MessagesUtil.sucess("O registro \""+conta+"\" foi "+title+".");
			conta = new Conta();
			FacesUtil.fecharModal("modalConta");
			RequestContext.getCurrentInstance().update("formDataTable");
			RequestContext.getCurrentInstance().update("formConta");
		}catch(Exception e){
			log.debug(e.getMessage());
			log.debug("ERRO SALVAR");
	    }
	}

	@PreAuthorize("hasPermission(#user, 'UPDATE')")
	@Transactional
	public void alterar(Conta entity){
		try {
			conta = (Conta) contaDao.findById(entity.getIdConta());
			RequestContext.getCurrentInstance().update("formConta");
			FacesUtil.abrirModal("modalConta");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	@Override
	@PreAuthorize("hasPermission(#user, 'DELETE')")
	public void delete(Conta entity) {
		super.delete(entity);
		RequestContext.getCurrentInstance().update("formDataTable");
	}

	@PreAuthorize("hasPermission(#user, 'SAVE')")
	public void add(){
		conta = new Conta();
		RequestContext.getCurrentInstance().update("formConta");
		FacesUtil.abrirModal("modalConta");
	}
	
	

	public void setConta(Conta conta) {
		this.conta = conta;
	}
	public Conta getConta() {
		return conta;
	}
	


}
