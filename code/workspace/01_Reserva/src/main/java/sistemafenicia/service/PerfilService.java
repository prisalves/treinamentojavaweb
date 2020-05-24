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
import sistemafenicia.dao.PermissaoDao;
import sistemafenicia.model.Perfil;
import sistemafenicia.model.Permissao;
import sistemafenicia.utils.FacesUtil;
import sistemafenicia.utils.MessagesUtil;

@ManagedBean
@RequestScoped
@Service
@Controller
public class PerfilService extends basicService<Perfil> implements Serializable {

	private static final long serialVersionUID = 1L;

	static final Logger log = Logger.getLogger(PerfilService.class);

	@Autowired
	private PermissaoDao permissaoDao;
	
	@Autowired
	private PerfilDao perfilDao;
	
	@PostConstruct
	public void init() {
		super.setDao(perfilDao);
		super.setClazz(Perfil.class);
	}

	@RequestMapping(value = "/perfis", method=RequestMethod.GET)
	public String pagePerfil() {
		return "administracao/usuario/perfis";
	}
	
	@SuppressWarnings("unused")
	private class InformacaoTabela{
		private Integer codigo;
		private String nome;
		private String acao;
	}
	
	@RequestMapping(value = "/perfis/lista", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@Transactional
	public String getLista() {
		List<InformacaoTabela> listaInfo = new ArrayList<InformacaoTabela>();
		InformacaoTabela info = new InformacaoTabela();
		Integer totalRegistro = 0;
		try {
			/*
			List<Perfil> listaTable = new ArrayList<Perfil>();
			for (Pessoa pessoa : pessoaDao.findAll()) {
				if(pessoa.getPessoaTipo().getNome().equals("Funcionário"))
					listaPessoa.add((PessoaFisica) pessoa);
			}
			*/
			boolean status = false;
			totalRegistro = perfilDao.findAll().size();
			for (Perfil perfil : perfilDao.findAll()) {
				info = new InformacaoTabela();
				info.codigo = perfil.getIdPerfil();
				info.nome = perfil.getNome();
				status = perfil.isStatus();
				String checked = null;
				if(status)
					checked = "checked";
				String botaoStatus = "<div class=\"ios-switch primary item-status\">"+
						"<div class=\"switch\" id=\"status-"+info.codigo+"\">"+
							"<input type=\"checkbox\" "+checked+" onclick=\"getAcao("+info.codigo+",'status')\" onchange=\"getAcao("+info.codigo+",'status')\" style=\""+status+"\" />"+
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
	
	@RequestMapping(value = "/perfis/acao", method = RequestMethod.POST)
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
	@RequestMapping(value = "/perfis/deleteall", method = RequestMethod.POST)
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
	            	//Perfil perfil = (Perfil) perfilDao.findByField("id",id);
            		perfilDao.deleteById(id);
	            	excluidos.append(marks.toString());
	            	excluidos.append(",");
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
			///PERMISSOES
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
			perfil = null;
			FacesUtil.fecharModal("modalPerfil");
			FacesUtil.atualizaTable("formDataTable:dataTableBlock");
			RequestContext.getCurrentInstance().update("formPerfil");
			if(FacesUtil.findComponent("formUsuario:selectPerfis"))
				RequestContext.getCurrentInstance().update("formUsuario:selectPerfis");
		} catch (Exception e) {
			log.debug("ERRO SALVAR Perfil");
			MessagesUtil.error("O registro \""+perfil+"\" NÂO foi "+title+".");
		}
	}

	@PreAuthorize("hasPermission(#user, 'DELETE')")
	public void excluir() {
		Perfil perfil = new Perfil();
		try {
			perfil = (Perfil) perfilDao.findByField("id",this.id);
			if(perfil==null){
				MessagesUtil.error("registro "+this.id+" não encontrado.");
			}else if(perfil.getUsuarios().size()>0){
				MessagesUtil.error("O registro \""+perfil+"\" não pode ser excluido, porque ele possui dependências.");
				this.id = null;
				return;
			}else{
	    		//perfilDao.deleteById(this.id);
				super.delete(perfil);
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
			perfil = (Perfil) perfilDao.findById(this.id);
			
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
		this.id = null;
	}

	@PreAuthorize("hasPermission(#user, 'SAVE')")
	public void add(){
		perfil = new Perfil();
		permissao = null;
		RequestContext.getCurrentInstance().update("formPerfil");
		FacesUtil.abrirModal("modalPerfil");
	}
	
	@Transactional
	@PreAuthorize("hasPermission(#user, 'UPDATE')")
	public void status() {
		String title = null;
		try {
			perfil = (Perfil) perfilDao.findById(this.id);
			if(perfil.isStatus()){
				title = "desativado";
				perfil.setStatus(false);
			}else{
				title = "ativado";
				perfil.setStatus(true);
			}
			perfilDao.update(perfil);
			if(title.equals("desativado"))
				MessagesUtil.warn(" \""+perfil+"\" foi "+title+".");
			else
				MessagesUtil.sucess(" \""+perfil+"\" foi "+title+".");
			/*if(!perfil.getLogin().equals("adm")){
				perfilDao.update(perfil);
				if(title.equals("desativado"))
					MessagesUtil.warn("O usuário \""+perfil+"\" foi "+title+".");
				else
					MessagesUtil.sucess("O usuário \""+perfil+"\" foi "+title+".");
			}else{
				perfil.setStatus(true);
				MessagesUtil.error("O usuário \""+perfil+"\" não pode ser "+title+".");
			}*/
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

	private Perfil perfil = new Perfil();
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	private String permissao = null;
	public String getPermissao() {
		return permissao;
	}
	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}
	

}
