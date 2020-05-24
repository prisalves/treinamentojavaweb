/*package sistemafenicia.service;

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

import sistemafenicia.dao.PermissaoDao;
import sistemafenicia.model.Permissao;
import sistemafenicia.utils.FacesUtil;
import sistemafenicia.utils.MessagesUtil;

@ManagedBean
@RequestScoped
@Service("permissaoService")
@Controller
public class PermissaoService2 extends basicService<Permissao> {

	static final Logger log = Logger.getLogger(PermissaoService2.class);

	@Autowired
	@Qualifier("permissaoDao")
	PermissaoDao permissaoDao;

	@PostConstruct
	public void init() {
		super.setDao(permissaoDao);
		super.setClazz(Permissao.class);
	}

	@RequestMapping(value = "/permissoes", method=RequestMethod.GET)
	public String pagePermissao() {
		return "administracao/usuario/permissoes";
	}

	@PreAuthorize("hasPermission(#user, 'SAVE') or hasPermission(#user, 'UPDATE')")
	@Transactional
	public void saveOrUpdate() {
		try {
			String title = null;
			if(permissao.getIdPermissao()!=null){
				update(permissao);
				title = "atualizado";
			}else{
				save(permissao);
				title = "adicionado";
			}
			MessagesUtil.sucess("O registro \""+permissao+"\" foi "+title+".");
			permissao = new Permissao();
			FacesUtil.fecharModal("modalPermissao");
			RequestContext.getCurrentInstance().update("formDataTable");
			RequestContext.getCurrentInstance().update("formPerfil");
			RequestContext.getCurrentInstance().update("@form");
		}catch(Exception e){
			log.debug(e.getMessage());
			log.debug("ERRO SALVAR PERMISSAO");
	    }
	}

	@PreAuthorize("hasPermission(#user, 'UPDATE')")
	@Transactional
	public void alterar(Permissao novaPermissao){
		try {
			permissao = (Permissao) permissaoDao.findById(novaPermissao.getIdPermissao());
			RequestContext.getCurrentInstance().update("formPermissao");
			FacesUtil.abrirModal("modalPermissao");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	@Override
	@PreAuthorize("hasPermission(#user, 'DELETE')")
	public void delete(Permissao entity) {
		super.delete(entity);
		RequestContext.getCurrentInstance().update("formDataTable");
	}

	@PreAuthorize("hasPermission(#user, 'SAVE')")
	public void add(){
		permissao = new Permissao();
		RequestContext.getCurrentInstance().update("formPermissao");
		FacesUtil.abrirModal("modalPermissao");
	}

	private Permissao permissao = new Permissao();

	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}


}
*/