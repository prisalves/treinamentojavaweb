/*package sistemafenicia.backup;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sistemafenicia.backup.ComodoTipoDao;
import sistemafenicia.model.ComodoTipo;
import sistemafenicia.utils.FacesUtil;
import sistemafenicia.utils.MessagesUtil;

@ManagedBean
@RequestScoped
@Service("comodoTipoService")
@Controller
@RequestMapping(value={"/comodo", "/comodo/**"})
public class ComodoTipoService extends basicService<ComodoTipo> {

	static final Logger log = Logger.getLogger(ComodoTipoService.class);

	@Autowired
	@Qualifier("comodoTipoDao")
	ComodoTipoDao comodoTipoDao;

	@PostConstruct
	public void init() {
		super.setDao(comodoTipoDao);
		super.setClazz(ComodoTipo.class);
	}

	@RequestMapping(value = "/comodotipo", method=RequestMethod.GET)
	public String page() {
		return "administracao/comodo/comodotipo";
	}

	
	@PreAuthorize("hasPermission(#user, 'SAVE') or hasPermission(#user, 'UPDATE')")
	@Transactional
	public void saveOrUpdate(){
		try {
			String title = null;
			if(comodoTipo.getIdComodoTipo()!=null){
				update(comodoTipo);
				title = "atualizado";
			}else{
				save(comodoTipo);
				title = "adicionado";
			}
			MessagesUtil.sucess("O registro \""+comodoTipo+"\" foi "+title+".");
			comodoTipo = new ComodoTipo();
			FacesUtil.fecharModal("addComodoTipo");
			RequestContext.getCurrentInstance().update("formDataTable");
			RequestContext.getCurrentInstance().update("formCadastro");
			RequestContext.getCurrentInstance().update("@(form)");
		}catch(Exception e){
			log.debug(e.getMessage());
	    	log.debug("ERRO SALVAR!");
	    }
	}

	@PreAuthorize("hasPermission(#user, 'UPDATE')")
	@Transactional
	public void alterar(ComodoTipo entity){
		try {
			comodoTipo = (ComodoTipo) comodoTipoDao.findById(entity.getIdComodoTipo());
			RequestContext.getCurrentInstance().update("formCadastro");
			FacesUtil.abrirModal("addComodoTipo");
		} catch (Exception e) {
			log.debug(e.getMessage());
			log.debug("ERRO UPDATE!");
		}
	}

	@Override
	@PreAuthorize("hasPermission(#user, 'DELETE')")
	public void delete(ComodoTipo entity) {
		try {
			super.delete(entity);
			RequestContext.getCurrentInstance().update("formDataTable");
		} catch (Exception e) {
			log.debug(e.getMessage());
			log.debug("ERRO UPDATE!");
		}
	}

	@PreAuthorize("hasPermission(#user, 'SAVE')")
	public void add(){
		comodoTipo = new ComodoTipo();
		RequestContext.getCurrentInstance().update("formCadastro");
		FacesUtil.abrirModal("addComodoTipo");
	}

	private ComodoTipo comodoTipo = new ComodoTipo();
	
	public ComodoTipo getComodoTipo() {
		return comodoTipo;
	}
	public void setComodoTipo(ComodoTipo comodoTipo) {
		this.comodoTipo = comodoTipo;
	}

}
*/