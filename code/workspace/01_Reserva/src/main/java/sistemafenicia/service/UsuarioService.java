package sistemafenicia.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import sistemafenicia.dao.PerfilDao;
import sistemafenicia.dao.UsuarioDao;
import sistemafenicia.model.Perfil;
import sistemafenicia.model.Usuario;
import sistemafenicia.utils.FacesUtil;
import sistemafenicia.utils.MessagesUtil;

@ManagedBean
@RequestScoped
@Service
@Controller
public class UsuarioService extends basicService<Usuario> implements Serializable {

	private static final long serialVersionUID = 1L;

	static final Logger log = Logger.getLogger(UsuarioService.class);

	@Autowired
	@Qualifier("usuarioDao")
	private UsuarioDao usuarioDao;
	
	@Autowired
	@Qualifier("perfilDao")
	private PerfilDao perfilDao;
	
	@PostConstruct
	public void init() {
		super.setDao(usuarioDao);
		super.setClazz(Usuario.class);
	}

	@PreAuthorize("hasPermission(#user, 'ADMIN')")
	@RequestMapping(value = "/usuarios", method=RequestMethod.GET)
	public String pageUsuario() {
		return "administracao/usuario/usuarios";
	}
	
	@SuppressWarnings("unused")
	private class InformacaoTabela{
		private Integer codigo;
		private String login;
		private String acao;
	}
	
	@RequestMapping(value = "/usuarios/lista", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@Transactional
	public String getLista() {
		List<InformacaoTabela> listaInfo = new ArrayList<InformacaoTabela>();
		InformacaoTabela info = new InformacaoTabela();
		Integer totalRegistro = 0;
		try {
			/*
			List<Usuario> listaTable = new ArrayList<Usuario>();
			for (Pessoa pessoa : pessoaDao.findAll()) {
				if(pessoa.getPessoaTipo().getNome().equals("Funcionário"))
					listaPessoa.add((PessoaFisica) pessoa);
			}
			*/
			boolean status = false;
			totalRegistro = usuarioDao.findAll().size();
			for (Usuario usuario : usuarioDao.findAll()) {
				info = new InformacaoTabela();
				info.codigo = usuario.getIdUsuario();
				info.login = usuario.getLogin();
				status = usuario.isStatus();
				String checked = "";
				if(status)
					checked = "checked";
				String botaoStatus = "<div class=\"ios-switch primary item-status\">"+
						"<div class=\"switch\" id=\"status-"+info.codigo+"\">"+
							"<input type=\"checkbox\" "+checked+" onclick=\"getAcao("+info.codigo+",'status')\" onchange=\"getAcao("+info.codigo+",'status')\" style=\""+status+" teste\" />"+
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
				info.acao = botaoStatus + botaoEditar + botaoExluir;
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
	
	@RequestMapping(value = "/usuarios/acao", method = RequestMethod.POST)
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
	@RequestMapping(value = "/usuarios/deleteall", method = RequestMethod.POST)
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
	            	Usuario usuario = (Usuario) usuarioDao.findByField("id",id);
	            	if(!usuario.getLogin().equals("adm")){
	            		usuarioDao.deleteById(id);
		            	excluidos.append(marks.toString());
		            	excluidos.append(",");
	            	}
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
			//PERFIL
			if( perfil==null ){
				MessagesUtil.error("Selecione pelo menos (1) perfil");
				return;
			}
			String[] perfilArray = perfil.split(",");
			Set<Perfil> setPerfil = new HashSet<Perfil>();
			Perfil perfil = new Perfil();
			for (String string : perfilArray) {
				perfil = (Perfil) perfilDao.findByField("nome", string);
				setPerfil.add(perfil);
			}
			usuario.setPerfis(setPerfil);
			//SAVE OR UPDATE
			if(usuario.getIdUsuario()!=null){
				title = "atualizado";
				update(usuario);
			}else{
				title = "adicionado";
				save(usuario);
			}
			MessagesUtil.sucess("O registro \""+usuario+"\" foi "+title+".");
			usuario = new Usuario();
			perfil = null;
			FacesUtil.fecharModal("modalUsuario");
			FacesUtil.atualizaTable("formDataTable:dataTableBlock");
			RequestContext.getCurrentInstance().update("formUsuario");
		} catch (Exception e) {
			log.debug("ERRO SALVAR Usuario");
			MessagesUtil.error("O registro \""+usuario+"\" NÂO foi "+title+".");
		}
	}

	@PreAuthorize("hasPermission(#user, 'DELETE')")
	public void excluir() {
		Usuario usuario = new Usuario();
		try {
			usuario = (Usuario) usuarioDao.findByField("id",this.id);
			if(usuario==null){
				MessagesUtil.error("Usuário não encontrado.");
			}else if(usuario.getLogin().equals("adm")){
	    		MessagesUtil.error("O usuário \""+usuario+"\" não pode ser excluido.");
	    	}else{
	    		super.delete(usuario);
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
			usuario = (Usuario) usuarioDao.findById(this.id);
			StringBuilder builder = new StringBuilder();
			for (Perfil perfil : usuario.getPerfis()) {
				builder.append(perfil.getNome());
				builder.append(",");
			}
			perfil = builder.toString();
			RequestContext.getCurrentInstance().update("formUsuario");
			FacesUtil.abrirModal("modalUsuario");
		} catch (Exception e) {
			log.debug(e.getCause());
			log.debug(e.getMessage());
		}
		this.id = null;
	}

	@PreAuthorize("hasPermission(#user, 'SAVE')")
	public void add(){
		usuario = new Usuario();
		perfil = null;
		RequestContext.getCurrentInstance().update("formUsuario");
		FacesUtil.abrirModal("modalUsuario");
	}
	
	@Transactional
	@PreAuthorize("hasPermission(#user, 'UPDATE')")
	public void status() {
		String title = null;
		try {
			usuario = (Usuario) usuarioDao.findById(this.id);
			if(usuario.isStatus()){
				title = "desativado";
				usuario.setStatus(false);
			}else{
				title = "ativado";
				usuario.setStatus(true);
			}
			if(!usuario.getLogin().equals("adm")){
				usuarioDao.update(usuario);
				if(title.equals("desativado"))
					MessagesUtil.warn("O usuário \""+usuario+"\" foi "+title+".");
				else
					MessagesUtil.sucess("O usuário \""+usuario+"\" foi "+title+".");
			}else{
				usuario.setStatus(true);
				MessagesUtil.error("O usuário \""+usuario+"\" não pode ser "+title+".");
			}
			FacesUtil.atualizaTable("formDataTable:dataTableBlock");
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

	private Usuario usuario;
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	private String perfil;
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String newPerfil) {
		this.perfil = newPerfil;
	}
	

}
