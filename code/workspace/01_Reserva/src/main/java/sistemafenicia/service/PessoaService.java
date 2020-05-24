package sistemafenicia.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ValueChangeEvent;

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

import sistemafenicia.dao.CidadeDao;
import sistemafenicia.dao.ContaDao;
import sistemafenicia.dao.EstadoDao;
import sistemafenicia.dao.PessoaDao;
import sistemafenicia.dao.PessoaFisicaDao;
import sistemafenicia.model.Cidade;
import sistemafenicia.model.Conta;
import sistemafenicia.model.Estado;
import sistemafenicia.model.Perfil;
import sistemafenicia.model.Pessoa;
import sistemafenicia.model.PessoaFisica;
import sistemafenicia.model.PessoaTipo;
import sistemafenicia.model.Usuario;
import sistemafenicia.utils.FacesUtil;
import sistemafenicia.utils.MessagesUtil;

@ManagedBean
@RequestScoped
@Service
@Controller
public class PessoaService extends basicService<Pessoa> {

	static final Logger log = Logger.getLogger(PessoaService.class);

	@Autowired
	PessoaDao pessoaDao;
	@Autowired
	ContaDao contaDao;
	@Autowired
	PessoaFisicaDao pessoaFisicaDao;
	@Autowired
	EstadoDao estadoDao;
	@Autowired
	CidadeDao cidadeDao;
	
	Integer id = null;
	PessoaTipo pessoaTipo;
	Conta conta;
	Pessoa pessoa;
	PessoaFisica pessoaFisica;
	PessoaFisica proprietario;
	PessoaFisica cliente;
	PessoaFisica funcionario;
	Usuario usuario;
	
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}
	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}
	public PessoaFisica getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(PessoaFisica funcionario) {
		this.funcionario = funcionario;
	}
	public PessoaFisica getProprietario() {
		return proprietario;
	}
	public void setProprietario(PessoaFisica proprietario) {
		this.proprietario = proprietario;
	}
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	public PessoaFisica getCliente() {
		return cliente;
	}
	public void setCliente(PessoaFisica cliente) {
		this.cliente = cliente;
	}

	@PostConstruct
	public void init() {
		super.setDao(pessoaDao);
		super.setClazz(Pessoa.class);
	}
	
	@RequestMapping(value = {"agenda_contatos/acao","funcionarios/acao","proprietarios/acao","clientes/acao"}, method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String acao(@RequestBody String value){
		setId(Integer.parseInt(value));
		JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("msg","OK");
        return jsonResponse.toString();
	}

	@RequestMapping(value = "/clientes", method=RequestMethod.GET)
	public String pageClientes() {
		return "administracao/pessoa/clientes";
	}
	
	@RequestMapping(value = "/proprietarios", method=RequestMethod.GET)
	public String pageProprietarios() {
		return "administracao/pessoa/proprietarios";
	}
	
	@RequestMapping(value = "/funcionarios", method=RequestMethod.GET)
	public String pageFuncionarios() {
		return "administracao/pessoa/funcionarios";
	}
	
	@RequestMapping(value = "/agenda_contatos", method=RequestMethod.GET)
	public String pageAgendaContatos() {
		return "administracao/pessoa/agenda_contatos";
	}
	
	@SuppressWarnings("unused")
	public class InformacaoTabela{
		private Integer codigo;
		private String nome;
		private String acao;
	}
	
	@Transactional
	@PreAuthorize("hasPermission(#user, 'UPDATE')")
	public void status() {
		String title = null;
		try {
			pessoa = (Pessoa) pessoaDao.findById(this.id);
			if(pessoa.isStatus()){
				title = "desativado";
				pessoa.setStatus(false);
			}else{
				title = "ativado";
				pessoa.setStatus(true);
			}
			pessoaDao.update(pessoa);
			if(title.equals("desativado"))
				MessagesUtil.warn(" \""+pessoa+"\" foi "+title+".");
			else
				MessagesUtil.sucess(" \""+pessoa+"\" foi "+title+".");
			FacesUtil.atualizaTable("formDataTable:dataTableBlock");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
	
	@PreAuthorize("hasPermission(#user, 'DELETE')")
	public void excluir() {
		Pessoa pessoa = null;
		try {
			pessoa = (Pessoa) pessoaDao.findByField("id",this.id);
			if(pessoa==null){
				MessagesUtil.error("Registro <b>"+this.id+"</b>, código não encontrado.");
			}else if(pessoa.getPessoaTipo().getNome().equals("Cliente")){
				MessagesUtil.error("Clientes devem ser excluídos no menu clientes.");
			}else{
	    		super.delete(pessoa);
				FacesUtil.atualizaTable("formDataTable:dataTableBlock");
	    	}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		this.id = null;
	}
	
	@PreAuthorize("hasPermission(#user, 'DELETE')")
	@Transactional
	@RequestMapping(value = "/funcionarios/deleteall", method = RequestMethod.POST)
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
	            	Pessoa pessoa = (Pessoa) pessoaDao.findByField("id",id);
	            	pessoaDao.deleteById(id);
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
	
	
	@PreAuthorize("hasPermission(#user, 'SAVE') or hasPermission(#user, 'UPDATE')")
	@Transactional
	public void saveOrUpdate(String tipoPessoa) {
		try {
			String title = null;
			Set<Perfil> perfis = new HashSet<Perfil>();
			Perfil perfil = new Perfil();
			if(tipoPessoa.equals("Funcionario")){
				pessoaFisica.setPessoaTipo(new PessoaTipo(3));
				perfil.setIdPerfil(1); // 
				perfis.add(perfil);
				usuario.setPerfis(perfis);
			}else if(tipoPessoa.equals("Proprietario")){
				pessoaFisica.setPessoaTipo(new PessoaTipo(1));
				perfil.setIdPerfil(4);
				perfis.add(perfil);
				usuario.setPerfis(perfis);
				if(conta.getBanco()!=null && conta.getAgencia()!=null && conta.getContaCorrente()!=null){
					/*boolean contaexiste = false;
					for (Conta contabanco : contaDao.findAll()) {
						if(contabanco.getBanco().getIdBanco()==conta.getBanco().getIdBanco() 
								&& contabanco.getAgencia().equals(conta.getAgencia()) 
								&& contabanco.getContaCorrente().equals(conta.getContaCorrente())){
							contaexiste = true;
						}
					}
					if(contaexiste && conta.getIdConta()==null){
						MessagesUtil.error("Esta conta já existe  \""+conta+"\", favor informar outra conta.");
						return;
					}*/
					pessoaFisica.setConta(conta);
				}
			}else if(tipoPessoa.equals("Cliente")){
				pessoaFisica.setPessoaTipo(new PessoaTipo(2));
				perfil.setIdPerfil(2);
				perfis.add(perfil);
				usuario.setPerfis(perfis);
			}else if(tipoPessoa.equals("Contato")){
				pessoaFisica.setPessoaTipo(new PessoaTipo(3));
			}
			if(usuario.getLogin()!=null && usuario.getSenha()!=null)
				pessoaFisica.setUsuario(usuario);
			else
				pessoaFisica.setUsuario(null);
			if(pessoaFisica.getIdPessoa()!=null){
				pessoaFisicaDao.update(pessoaFisica);
				title = "atualizado";
			}else{
				pessoaFisicaDao.save(pessoaFisica);
				title = "adicionado";
			}
			/*if(tipoPessoa.equals("Proprietario") && conta.getBanco()!=null && conta.getAgencia()!=null && conta.getContaCorrente()!=null){
				conta.setTitular(pessoaFisica);
				if(conta.getIdConta()!=null)
					contaDao.update(conta);
				else
					contaDao.save(conta);
			}*/
			MessagesUtil.sucess(" \""+pessoaFisica+"\" foi "+title+" com sucesso.");
			pessoaFisica = new PessoaFisica();
			FacesUtil.fecharModal("modal"+tipoPessoa);
			RequestContext.getCurrentInstance().update("form"+tipoPessoa);
			if(FacesUtil.findComponent("formApartamento:selectProprietario"))
				RequestContext.getCurrentInstance().update("formApartamento:selectProprietario");
			if(FacesUtil.findComponent("formReserva:selectCliente"))
				RequestContext.getCurrentInstance().update("formReserva:selectCliente");
			FacesUtil.atualizaTable("formDataTable:dataTableBlock");
		}catch(Exception e){
			log.debug(e.getMessage());
			log.debug("ERRO SALVAR");
	    }
	}
	
	@Transactional
	@PreAuthorize("hasPermission(#user, 'UPDATE')")
	public void alterar(String tipoPessoa){
		try {
			pessoaFisica = (PessoaFisica) pessoaDao.findById(this.id);
			if(pessoaFisica.getUsuario()!=null)
				usuario = pessoaFisica.getUsuario();
			else
				usuario = new Usuario();
			if( tipoPessoa.equals("Proprietario") && pessoaFisica.getConta()!=null )
				conta = pessoaFisica.getConta();
			else if(tipoPessoa.equals("Proprietario"))
				conta = new Conta();
			else if(tipoPessoa.equals("Cliente")){
				if(pessoaFisica.getCidade()!=null){
					estado = pessoaFisica.getCidade().getEstado();
					listaCidades = cidadeDao.findAllByField("estado", estado);
				}else{
					Cidade cidade = new Cidade();
					cidade.setIdCidade(1);
					pessoaFisica.setCidade(cidade);
					Estado estado = new Estado();
					estado.setIdEstado(1);
					listaCidades = cidadeDao.findAllByField("estado", estado);
				}
			}
			RequestContext.getCurrentInstance().update("form"+tipoPessoa);
			FacesUtil.abrirModal("modal"+tipoPessoa);
		} catch (Exception e) {
			log.debug(e.getCause());
			log.debug(e.getMessage());
		}
		this.id = null;
	}
	
	@PreAuthorize("hasPermission(#user, 'SAVE')")
	public void add(String tipoPessoa){
		try {
			pessoaFisica = new PessoaFisica();
			usuario = new Usuario();
			if(tipoPessoa.equals("Proprietario")){
				conta = new Conta();
			}else if(tipoPessoa.equals("Cliente")){
				Cidade cidade = new Cidade();
				cidade.setIdCidade(1);
				pessoaFisica.setCidade(cidade);
				Estado estado = new Estado();
				estado.setIdEstado(1);
				listaCidades = cidadeDao.findAllByField("estado", estado);
			}
			RequestContext.getCurrentInstance().update("form"+tipoPessoa);
			FacesUtil.abrirModal("modal"+tipoPessoa);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
	
	/* ------------------------------- AGENDA CONTATOS ----------------------*/
	
	@SuppressWarnings("unused")
	public class InformacaoTabelaAgenda{
		private Integer codigo;
		private String nome;
		private String telefones;
		private String servicos;
		private String acao;
	}
	
	@RequestMapping(value = "/agenda_contatos/lista", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@Transactional
	public String getListaAgendaContatos() {
		
		List<InformacaoTabelaAgenda> listaInfo = new ArrayList<InformacaoTabelaAgenda>();
		InformacaoTabelaAgenda info = new InformacaoTabelaAgenda();
		List<PessoaFisica> listaPessoa = new ArrayList<PessoaFisica>();
		String servicos = null;
		StringBuilder telefones = null;
		try {
			for (Pessoa pessoa : pessoaDao.findAll()) {
				if(!pessoa.getPessoaTipo().getNome().equals("Proprietário"))
					listaPessoa.add((PessoaFisica) pessoa);
			}
			boolean status = false;
			for (PessoaFisica pessoa : listaPessoa) {
				info = new InformacaoTabelaAgenda();
				info.codigo = pessoa.getIdPessoa();
				info.nome = pessoa.getNome();
					telefones = new StringBuilder();
					if(pessoa.getTelefone1()!=null)
						telefones.append(pessoa.getTelefone1());
					if(pessoa.getTelefone2()!=null)
						telefones.append("<br/>"+pessoa.getTelefone2());
					if(pessoa.getTelefone3()!=null)
						telefones.append("<br/>"+pessoa.getTelefone3());
				info.telefones = telefones.toString();
					servicos = null;
					if(pessoa.getServicos()!=null)
						servicos = pessoa.getServicos();
					else
						servicos = pessoa.getPessoaTipo().toString(); 
				info.servicos = servicos;
				status = pessoa.isStatus();
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
				info.acao = botaoEditar + botaoExluir;
				listaInfo.add(info);
			}
		} catch (Exception ex) {
			log.debug(ex.getMessage());
		}
		
		Integer sEcho = 2;
		Integer iTotalRecords = 2;
		Integer iTotalDisplayRecords = 2;
		
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
	
	/* ------------------------------- FUNCIONARIO ----------------------*/
	
	@RequestMapping(value = "/funcionarios/lista", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@Transactional
	public String getLista() {
		
		List<InformacaoTabela> listaInfo = new ArrayList<InformacaoTabela>();
		InformacaoTabela info = new InformacaoTabela();
		List<PessoaFisica> listaPessoa = new ArrayList<PessoaFisica>();
		try {
			for (Pessoa pessoa : pessoaDao.findAll()) {
				if(pessoa.getPessoaTipo().getNome().equals("Funcionário"))
					listaPessoa.add((PessoaFisica) pessoa);
			}
			boolean status = false;
			for (PessoaFisica pessoa : listaPessoa) {
				info = new InformacaoTabela();
				info.codigo = pessoa.getIdPessoa();
				info.nome = pessoa.getNome();
				status = pessoa.isStatus();
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
		
		Integer sEcho = 2;
		Integer iTotalRecords = 2;
		Integer iTotalDisplayRecords = 2;
		
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
	
	/* ------------------------------- PROPRIETARIO ----------------------*/
	
	@SuppressWarnings("null")
	public List<PessoaFisica> listaProprietariosSelect(){
		try {
			List<PessoaFisica> lista = new ArrayList<PessoaFisica>();
			for (PessoaFisica pessoaFisica : pessoaFisicaDao.findAll()) {
				if(pessoaFisica.getPessoaTipo().getNome().equals("Proprietário")){
					lista.add(pessoaFisica);
				}
			}
			return lista;
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return null;
	}
	
	@RequestMapping(value = "/proprietarios/lista", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@Transactional
	public String getListaProprietarios() {
		List<InformacaoTabela> listaInfo = new ArrayList<InformacaoTabela>();
		InformacaoTabela info = new InformacaoTabela();
		List<PessoaFisica> listaPessoa = new ArrayList<PessoaFisica>();
		try {
			for (Pessoa pessoa : pessoaDao.findAll()) {
				if(pessoa.getPessoaTipo().getNome().equals("Proprietário"))
					listaPessoa.add((PessoaFisica) pessoa);
			}
			boolean status = false;
			for (PessoaFisica pessoa : listaPessoa) {
				info = new InformacaoTabela();
				info.codigo = pessoa.getIdPessoa();
				info.nome = pessoa.getNome();
				status = pessoa.isStatus();
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
		
		Integer sEcho = 2;
		Integer iTotalRecords = 2;
		Integer iTotalDisplayRecords = 2;
		
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

	/* ------------------------------- CLIENTE ----------------------*/
	
	@SuppressWarnings("null")
	public List<PessoaFisica> listaClientesSelect(){
		try {
			List<PessoaFisica> lista = new ArrayList<PessoaFisica>();
			for (PessoaFisica pessoaFisica : pessoaFisicaDao.findAll("nome","ASC")) {
				if(pessoaFisica.getPessoaTipo().getNome().equals("Cliente")){
					lista.add(pessoaFisica);
				}
			}
			return lista;
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return null;
	}
	
	@RequestMapping(value = "/clientes/lista", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@Transactional
	public String getListaClientes() {
		List<InformacaoTabela> listaInfo = new ArrayList<InformacaoTabela>();
		InformacaoTabela info = new InformacaoTabela();
		List<PessoaFisica> listaPessoa = new ArrayList<PessoaFisica>();
		try {
			for (Pessoa pessoa : pessoaDao.findAll()) {
				if(pessoa.getPessoaTipo().getNome().equals("Cliente"))
					listaPessoa.add((PessoaFisica) pessoa);
			}
			boolean status = false;
			for (PessoaFisica pessoa : listaPessoa) {
				info = new InformacaoTabela();
				info.codigo = pessoa.getIdPessoa();
				info.nome = pessoa.getNome();
				status = pessoa.isStatus();
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
		
		Integer sEcho = 2;
		Integer iTotalRecords = 2;
		Integer iTotalDisplayRecords = 2;
		
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

	/*CIDADES / ESTADOS ----------------------------*/
	
	List<Cidade> listaCidades = new ArrayList<Cidade>();
	List<Estado> listaEstados = new ArrayList<Estado>();
	Estado estado = new Estado();
	
	public void changeEstado(ValueChangeEvent e) {
        estado = (Estado) e.getNewValue();
        try {
			listaCidades = cidadeDao.findAllByField("estado", estado);
		} catch (Exception e1) {
			log.debug(e1.getMessage());
		}
    }
	
	public List<Cidade> getListaCidades() {
		/*try {
			if(listaCidades.size()==0 && cliente!=null){
				estado = cliente.getCidade().getEstado();
				listaCidades = cidadeDao.findAllByField("estado", estado);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}*/
		return listaCidades;
	}
	
	public List<Estado> getListaEstados(){
		try {
			listaEstados = estadoDao.findAll();
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return listaEstados;
	}

}
