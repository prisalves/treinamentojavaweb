/*package sistemafenicia.service;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sistemafenicia.dao.PerfilDao;
import sistemafenicia.dao.PermissaoDao;
import sistemafenicia.model.Perfil;
import sistemafenicia.model.Permissao;
import sistemafenicia.utils.FacesUtil;
import sistemafenicia.utils.MessagesUtil;

@ManagedBean
@RequestScoped
@Service("perfilService")
@Controller
public class PerfilService2 extends basicService<Perfil> {

	static final Logger log = Logger.getLogger(PerfilService2.class);

	@Autowired
	@Qualifier("perfilDao")
	PerfilDao perfilDao;

	@Autowired
	@Qualifier("permissaoDao")
	PermissaoDao permissaoDao;

	@PostConstruct
	public void init() {
		super.setDao(perfilDao);
		super.setClazz(Perfil.class);
	}

	@RequestMapping(value = "/perfis", method=RequestMethod.GET)
	public String pagePerfil() {
		return "administracao/usuario/perfis";
	}

	@PreAuthorize("hasPermission(#user, 'SAVE') or hasPermission(#user, 'UPDATE')")
	@Transactional
	public void saveOrUpdate(){
		String title = null;
		try {
			//PERMISSOES
			if( permissao==null ){
				MessagesUtil.message("Favor selecione pelo menos uma permissão","error");
				return;
			}
			String[] permissaoArray = permissao.split(",");
			Set<Permissao> setPermissoes = new HashSet<Permissao>();
			for (String string : permissaoArray) {
				Permissao permissao = (Permissao) permissaoDao.findByField("nome", string);
				setPermissoes.add(permissao);
			}
			perfil.setPermissoes(setPermissoes);
			//SAVE OR UPDATE
			if(perfil.getIdPerfil()!=null){
				title = "atualizado";
				update(perfil);
			}else{
				title = "adicionado";
				save(perfil);
			}
			MessagesUtil.sucess("O registro \""+perfil+"\" foi "+title+".");
			perfil = new Perfil();
			permissao = null;
			FacesUtil.fecharModal("modalPerfil");
			RequestContext.getCurrentInstance().update("formDataTable");
			RequestContext.getCurrentInstance().update("formUsuario");
			RequestContext.getCurrentInstance().update("@form");
		} catch (Exception e) {
			log.debug("ERRO SALVAR PERFIL");
			MessagesUtil.error("O registro \""+perfil+"\" NÂO foi "+title+".");
		}
	}

	@Override
	@Transactional
	@PreAuthorize("hasPermission(#user, 'DELETE')")
	public void delete(Perfil newPerfil) {
		//VERIFICA USUARIOS
		try {
			perfil = (Perfil) perfilDao.findById(newPerfil.getIdPerfil());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		if(perfil.getUsuarios().size()>0){
			MessagesUtil.error("O registro \""+perfil+"\" não pode ser excluido, porque ele possui dependências.");
			perfil = new Perfil();
			return;
		}
		//DELETE
		super.delete(newPerfil);
		RequestContext.getCurrentInstance().update("formDataTable");
	}

	@Transactional
	@PreAuthorize("hasPermission(#user, 'UPDATE')")
	public void alterar(Perfil newPerfil){
		try {
			perfil = (Perfil) perfilDao.findById(newPerfil.getIdPerfil());

			StringBuilder builder = new StringBuilder();
			for (Permissao permissao : perfil.getPermissoes()) {
				builder.append(permissao.getNome());
				builder.append(",");
			}
			permissao = builder.toString();

			RequestContext.getCurrentInstance().update("formPerfil");
			FacesUtil.abrirModal("modalPerfil");
		} catch (Exception e) {
			log.debug(e.getCause());
			log.debug(e.getMessage());
		}
	}

	@PreAuthorize("hasPermission(#user, 'SAVE')")
	public void add(){
		perfil = new Perfil();
		permissao = null;
		RequestContext.getCurrentInstance().update("formPerfil");
		FacesUtil.abrirModal("modalPerfil");
	}

	Perfil perfil = new Perfil();

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@NotNull(message="Permissao: obrigatório.")
	@NotEmpty(message="Permissao: obrigatório2.")
	@NotBlank(message="Permissao: não pode ser vazio.")
	@Size(min=2, max=30, message="Permissao: minimo 2 maximo 30 caracter.")
	private String permissao = null;

	public String getPermissao() {
		return permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}

}
*/