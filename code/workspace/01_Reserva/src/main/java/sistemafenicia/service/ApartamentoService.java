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

import sistemafenicia.dao.ApartamentoDao;
import sistemafenicia.model.Apartamento;
import sistemafenicia.model.ApartamentoDiaria;
import sistemafenicia.model.ApartamentoItem;
import sistemafenicia.utils.FacesUtil;
import sistemafenicia.utils.MessagesUtil;

@ManagedBean
@RequestScoped
@Service
@Controller
public class ApartamentoService extends basicService<Apartamento> implements Serializable {

	private static final long serialVersionUID = 1L;

	static final Logger log = Logger.getLogger(ApartamentoService.class);

	@Autowired
	private ApartamentoDao apartamentoDao;
	
	@PostConstruct
	public void init() {
		super.setDao(apartamentoDao);
		super.setClazz(Apartamento.class);
	}

	@RequestMapping(value = "/apartamentos", method=RequestMethod.GET)
	public String pageApartamento() {
		return "administracao/apartamento/apartamentos";
	}
	
	@SuppressWarnings("unused")
	private class InformacaoTabela{
		private Integer codigo;
		private String predio;
		private String identificador;
		private String proprietario;
		private String acao;
	}
	
	@RequestMapping(value = "/apartamentos/lista", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@Transactional
	public String getLista() {
		List<InformacaoTabela> listaInfo = new ArrayList<InformacaoTabela>();
		InformacaoTabela info = new InformacaoTabela();
		Integer totalRegistro = 0;
		try {
			boolean status = false;
			totalRegistro = apartamentoDao.findAll().size();
			for (Apartamento apartamento : apartamentoDao.findAll()) {
				info = new InformacaoTabela();
				info.codigo = apartamento.getIdApartamento();
				info.identificador = apartamento.getIdentificador();
				info.predio = apartamento.getPredio().getNome();
				info.proprietario = apartamento.getProprietario().toString();
				status = apartamento.isStatus();
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
	
	@RequestMapping(value = "/apartamentos/acao", method = RequestMethod.POST)
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
	@RequestMapping(value = "/apartamentos/deleteall", method = RequestMethod.POST)
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
            		apartamentoDao.deleteById(id);
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
			if(apartamento.getIdApartamento()!=null){
				title = "atualizado";
				update(apartamento);
			}else{
				title = "adicionado";
				save(apartamento);
			}
			MessagesUtil.sucess("O registro \""+apartamento+"\" foi "+title+".");
			apartamento = new Apartamento();
			FacesUtil.fecharModal("modalApartamento");
			FacesUtil.atualizaTable("formDataTable:dataTableBlock");
			RequestContext.getCurrentInstance().update("formApartamento");
			if(FacesUtil.findComponent("formPerfil:selectPermissoes"))
				RequestContext.getCurrentInstance().update("formPerfil:selectPermissoes");
			if(FacesUtil.findComponent("formReserva:selectApartamento"))
				RequestContext.getCurrentInstance().update("formReserva:selectApartamento");
		} catch (Exception e) {
			log.debug("ERRO SALVAR PERMISSAO");
			MessagesUtil.error("O registro \""+apartamento+"\" NÂO foi "+title+".");
		}
	}

	@PreAuthorize("hasPermission(#user, 'DELETE')")
	public void excluir() {
		Apartamento apartamento = new Apartamento();
		try {
			apartamento = (Apartamento) apartamentoDao.findByField("id",this.id);
			if(apartamento==null){
				MessagesUtil.error("registro "+this.id+" não encontrado.");
			}else{
	    		super.delete(apartamento);
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
			apartamento = (Apartamento) apartamentoDao.findById(this.id);
			addDiarias = apartamento.getApartamentoDiarias();
			addItens = apartamento.getApartamentoItens();
			apartamentoDiaria = new ApartamentoDiaria();
			apartamentoItem = new ApartamentoItem();
			RequestContext.getCurrentInstance().update("formApartamento");
			FacesUtil.abrirModal("modalApartamento");
		} catch (Exception e) {
			log.debug(e.getCause());
			log.debug(e.getMessage());
		}
		this.id = null;
	}

	@PreAuthorize("hasPermission(#user, 'SAVE')")
	public void add(){
		apartamento = new Apartamento();
		apartamentoItem = new ApartamentoItem();
		addItens = new HashSet<ApartamentoItem>();
		apartamentoDiaria = new ApartamentoDiaria();
		addDiarias = new HashSet<ApartamentoDiaria>();
		RequestContext.getCurrentInstance().update("formApartamento");
		FacesUtil.abrirModal("modalApartamento");
	}
	
	@Transactional
	@PreAuthorize("hasPermission(#user, 'UPDATE')")
	public void status() {
		String title = null;
		try {
			apartamento = (Apartamento) apartamentoDao.findById(this.id);
			if(apartamento.isStatus()){
				title = "desativado";
				apartamento.setStatus(false);
			}else{
				title = "ativado";
				apartamento.setStatus(true);
			}
			apartamentoDao.update(apartamento);
			if(title.equals("desativado"))
				MessagesUtil.warn(" \""+apartamento+"\" foi "+title+".");
			else
				MessagesUtil.sucess(" \""+apartamento+"\" foi "+title+".");
			FacesUtil.atualizaTable("formDataTable:dataTableBlock");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
	
	/*ITENS APARTAMENTO*/
	public void addItem(){
		if(apartamentoItem.getNome()!=null && apartamentoItem.getQuantidade()!=null){
			if(apartamento.getApartamentoItens().size()==0){
				addItens.add(apartamentoItem);
				apartamento.setApartamentoItens(addItens);
				apartamentoItem = new ApartamentoItem();
				return;
			}
			boolean itemExiste = false;
			for (ApartamentoItem apItem : apartamento.getApartamentoItens()) {
				if(	apItem.getNome().equals(apartamentoItem.getNome()) 
						&& apItem.getQuantidade()==apartamentoItem.getQuantidade() ){
					itemExiste = true;
				}
			}
			if(!itemExiste){
				addItens.add(apartamentoItem);
				apartamento.setApartamentoItens(addItens);
			}
			
			apartamentoItem = new ApartamentoItem();
		}
	}
	public void removeItem(ApartamentoItem entity){
		apartamento.getApartamentoItens().remove(entity);
	}
	
	/*DIARIAS APARTAMENTO*/
	public void addDiaria(){
		if(apartamentoDiaria.getNome()!=null && apartamentoDiaria.getValor()!=null){
			if(apartamento.getApartamentoDiarias().size()==0){
				addDiarias.add(apartamentoDiaria);
				apartamento.setApartamentoDiarias(addDiarias);
				apartamentoDiaria = new ApartamentoDiaria();
				return;
			}
			boolean diariaExiste = false;
			for (ApartamentoDiaria apDiaria : apartamento.getApartamentoDiarias()) {
				if(	apDiaria.getNome().equals(apartamentoDiaria.getNome()) 
						&& apDiaria.getValor()==apartamentoDiaria.getValor() ){
					diariaExiste = true;
				}
			}
			if(!diariaExiste){
				addDiarias.add(apartamentoDiaria);
				apartamento.setApartamentoDiarias(addDiarias);
			}
			
			apartamentoDiaria = new ApartamentoDiaria();
		}
	}
	public void removeDiaria(ApartamentoDiaria entity){
		apartamento.getApartamentoDiarias().remove(entity);
	}
	
	private Integer id = null;
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	
	private Apartamento apartamento;
	public Apartamento getApartamento() {
		return apartamento;
	}
	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}
	
	private ApartamentoItem apartamentoItem;
	public ApartamentoItem getApartamentoItem() {
		return apartamentoItem;
	}
	public void setApartamentoItem(ApartamentoItem apartamentoItem) {
		this.apartamentoItem = apartamentoItem;
	}
	
	private Set<ApartamentoItem> addItens;
	
	private ApartamentoDiaria apartamentoDiaria;
	public ApartamentoDiaria getApartamentoDiaria() {
		return apartamentoDiaria;
	}
	public void setApartamentoDiaria(ApartamentoDiaria apartamentoDiaria) {
		this.apartamentoDiaria = apartamentoDiaria;
	}
	
	private Set<ApartamentoDiaria> addDiarias;
	

	

}
