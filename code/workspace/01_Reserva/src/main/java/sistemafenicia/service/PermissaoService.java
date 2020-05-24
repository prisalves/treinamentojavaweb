package sistemafenicia.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import sistemafenicia.dao.PermissaoDao;
import sistemafenicia.model.Permissao;
import sistemafenicia.utils.FacesUtil;
import sistemafenicia.utils.MessagesUtil;

@ManagedBean
@RequestScoped
@Service
@Controller
public class PermissaoService extends basicService<Permissao> implements Serializable {

	private static final long serialVersionUID = 1L;

	static final Logger log = Logger.getLogger(PermissaoService.class);

	@Autowired
	private PermissaoDao permissaoDao;
	
	@PostConstruct
	public void init() {
		super.setDao(permissaoDao);
		super.setClazz(Permissao.class);
	}

	@RequestMapping(value = "/permissoes", method=RequestMethod.GET)
	public String pagePermissao() {
		return "administracao/usuario/permissoes";
	}
	
	@SuppressWarnings("unused")
	private class InformacaoTabela{
		private Integer codigo;
		private String nome;
		private String acao;
	}
	
	@RequestMapping(value = "/permissoes/lista", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@Transactional
	public String getLista() {
		List<InformacaoTabela> listaInfo = new ArrayList<InformacaoTabela>();
		InformacaoTabela info = new InformacaoTabela();
		Integer totalRegistro = 0;
		try {
			boolean status = false;
			totalRegistro = permissaoDao.findAll().size();
			for (Permissao permissao : permissaoDao.findAll()) {
				info = new InformacaoTabela();
				info.codigo = permissao.getIdPermissao();
				info.nome = permissao.getNome();
				status = permissao.isStatus();
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
				info.acao = botaoStatus + botaoExluir;
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
	
	@RequestMapping(value = "/permissoes/acao", method = RequestMethod.POST)
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
	@RequestMapping(value = "/permissoes/deleteall", method = RequestMethod.POST)
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
            		permissaoDao.deleteById(id);
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
			//SAVE OR UPDATE
			if(permissao.getIdPermissao()!=null){
				title = "atualizado";
				update(permissao);
			}else{
				title = "adicionado";
				save(permissao);
			}
			MessagesUtil.sucess("O registro \""+permissao+"\" foi "+title+".");
			permissao = new Permissao();
			FacesUtil.fecharModal("modalPermissao");
			FacesUtil.atualizaTable("formDataTable:dataTableBlock");
			RequestContext.getCurrentInstance().update("formPermissao");
			if(FacesUtil.findComponent("formPerfil:selectPermissoes"))
				RequestContext.getCurrentInstance().update("formPerfil:selectPermissoes");
		} catch (Exception e) {
			log.debug("ERRO SALVAR PERMISSAO");
			MessagesUtil.error("O registro \""+permissao+"\" NÂO foi "+title+".");
		}
	}

	@PreAuthorize("hasPermission(#user, 'DELETE')")
	public void excluir() {
		Permissao permissao = new Permissao();
		try {
			permissao = (Permissao) permissaoDao.findByField("id",this.id);
			if(permissao==null){
				MessagesUtil.error("registro "+this.id+" não encontrado.");
			}else{
	    		super.delete(permissao);
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
			permissao = (Permissao) permissaoDao.findById(this.id);
			RequestContext.getCurrentInstance().update("formPermissao");
			FacesUtil.abrirModal("modalPermissao");
		} catch (Exception e) {
			log.debug(e.getCause());
			log.debug(e.getMessage());
		}
		this.id = null;
	}

	@PreAuthorize("hasPermission(#user, 'SAVE')")
	public void add(){
		permissao = new Permissao();
		RequestContext.getCurrentInstance().update("formPermissao");
		FacesUtil.abrirModal("modalPermissao");
	}
	
	@Transactional
	@PreAuthorize("hasPermission(#user, 'UPDATE')")
	public void status() {
		String title = null;
		try {
			permissao = (Permissao) permissaoDao.findById(this.id);
			if(permissao.isStatus()){
				title = "desativado";
				permissao.setStatus(false);
			}else{
				title = "ativado";
				permissao.setStatus(true);
			}
			permissaoDao.update(permissao);
			if(title.equals("desativado"))
				MessagesUtil.warn(" \""+permissao+"\" foi "+title+".");
			else
				MessagesUtil.sucess(" \""+permissao+"\" foi "+title+".");
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
	
	private Permissao permissao;
	public Permissao getPermissao() {
		return permissao;
	}
	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	

}
