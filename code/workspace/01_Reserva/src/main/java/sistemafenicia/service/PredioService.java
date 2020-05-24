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

import sistemafenicia.dao.PredioDao;
import sistemafenicia.model.Predio;
import sistemafenicia.utils.FacesUtil;
import sistemafenicia.utils.MessagesUtil;

@ManagedBean
@RequestScoped
@Service
@Controller
public class PredioService extends basicService<Predio> {

	static final Logger log = Logger.getLogger(PredioService.class);

	@Autowired
	PredioDao predioDao;
	
	Predio predio;

	@PostConstruct
	public void init() {
		super.setDao(predioDao);
		super.setClazz(Predio.class);
	}

	@RequestMapping(value = "/predio", method=RequestMethod.GET)
	public String page() {
		predio = new Predio();
		return "administracao/apartamento/predios";
	}

	@PreAuthorize("hasPermission(#user, 'SAVE') or hasPermission(#user, 'UPDATE')")
	@Transactional
	public void saveOrUpdate() {
		try {
			String title = null;
			if(predio.getIdPredio()!=null){
				update(predio);
				title = "atualizado";
			}else{
				save(predio);
				title = "adicionado";
			}
			MessagesUtil.sucess("O registro \""+predio+"\" foi "+title+".");
			predio = new Predio();
			FacesUtil.fecharModal("modalPredio");
			RequestContext.getCurrentInstance().update("formDataTable");
			RequestContext.getCurrentInstance().update("formPredio");
			if(FacesUtil.findComponent("formApartamento:selectPredio"))
				RequestContext.getCurrentInstance().update("formApartamento:selectPredio");
		}catch(Exception e){
			log.debug(e.getMessage());
			log.debug("ERRO SALVAR");
	    }
	}

	@PreAuthorize("hasPermission(#user, 'UPDATE')")
	@Transactional
	public void alterar(Predio entity){
		try {
			predio = (Predio) predioDao.findById(entity.getIdPredio());
			RequestContext.getCurrentInstance().update("formPredio");
			FacesUtil.abrirModal("modalPredio");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	@Override
	@PreAuthorize("hasPermission(#user, 'DELETE')")
	public void delete(Predio entity) {
		super.delete(entity);
		RequestContext.getCurrentInstance().update("formDataTable");
	}

	@PreAuthorize("hasPermission(#user, 'SAVE')")
	public void add(){
		predio = new Predio();
		RequestContext.getCurrentInstance().update("formPredio");
		FacesUtil.abrirModal("modalPredio");
	}

	public void setPredio(Predio predio) {
		this.predio = predio;
	}
	public Predio getPredio() {
		return predio;
	}


}
