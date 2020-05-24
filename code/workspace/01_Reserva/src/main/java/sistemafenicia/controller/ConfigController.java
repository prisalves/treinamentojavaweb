package sistemafenicia.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sistemafenicia.dao.ApartamentoDao;
import sistemafenicia.dao.BancoDao;
import sistemafenicia.dao.CaixaDao;
import sistemafenicia.dao.ContaDao;
import sistemafenicia.dao.EstacionamentoDao;
import sistemafenicia.dao.MovimentacaoDao;
import sistemafenicia.dao.PerfilDao;
import sistemafenicia.dao.PermissaoDao;
import sistemafenicia.dao.PessoaDao;
import sistemafenicia.dao.PessoaFisicaDao;
import sistemafenicia.dao.PessoaTipoDao;
import sistemafenicia.dao.PredioDao;
import sistemafenicia.dao.ReservaDao;
import sistemafenicia.dao.ReservaTipoDao;
import sistemafenicia.dao.UsuarioDao;
import sistemafenicia.model.Apartamento;
import sistemafenicia.model.Banco;
import sistemafenicia.model.Cidade;
import sistemafenicia.model.Conta;
import sistemafenicia.model.Estacionamento;
import sistemafenicia.model.Perfil;
import sistemafenicia.model.Permissao;
import sistemafenicia.model.Pessoa;
import sistemafenicia.model.PessoaFisica;
import sistemafenicia.model.PessoaTipo;
import sistemafenicia.model.Predio;
import sistemafenicia.model.Reserva;
import sistemafenicia.model.ReservaTipo;
import sistemafenicia.model.Usuario;

//@RequestScoped
//@ManagedBean
//@Scope("session")
@Service
//@Component
//@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ConfigController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(ConfigController.class);

	private boolean executado;

	@Autowired
	PermissaoDao permissaoDao;
	@Autowired
	PerfilDao perfilDao;
	@Autowired
	UsuarioDao usuarioDao;
	@Autowired
	AddCidades addCidades;
	@Autowired
	PessoaTipoDao pessoaTipoDao;
	@Autowired
	PessoaDao pessoaDao;
	@Autowired
	PessoaFisicaDao pessoaFisicaDao;
	@Autowired
	PredioDao predioDao;
	@Autowired
	ApartamentoDao apartamentoDao;
	@Autowired
	BancoDao bancoDao;
	@Autowired
	ContaDao contaDao;
	@Autowired
	CaixaDao caixaDao;
	@Autowired
	MovimentacaoDao movimentacaoDao;
	@Autowired
	ReservaTipoDao reservaTipoDao;
	@Autowired
	ReservaDao reservaDao;
	@Autowired
	EstacionamentoDao estacionamentoDao;

	@Transactional(readOnly=true)
	@Autowired
	@PostConstruct
	public void cadastrarUsuarios(){

		try {

			executado = false;
			Usuario testeDados = (Usuario) usuarioDao.findByField("login","adm");
			if(testeDados!=null){
				executado = true;
			}

			if(!executado){

				/*----- PERMISSOES -----*/
				Set<Permissao> permissoes = new HashSet<Permissao>();
				Permissao permissao = new Permissao();

				/*- LIST -*/
				Permissao permissaoList = new Permissao();
				permissaoList.setNome("LIST");
				permissao = (Permissao) permissaoDao.findByField("nome",permissaoList.getNome());
				if(permissao==null){
					permissaoDao.save(permissaoList);
				}else{
					permissaoList = permissao;
				}

				/*- SAVE -*/
				Permissao permissaoSave = new Permissao();
				permissaoSave.setNome("SAVE");
				permissao = (Permissao) permissaoDao.findByField("nome",permissaoSave.getNome());
				if(permissao==null){
					permissaoDao.save(permissaoSave);
				}else{
					permissaoSave = permissao;
				}

				/*- UPDATE -*/
				Permissao permissaoUpdate = new Permissao();
				permissaoUpdate.setNome("UPDATE");
				permissao = (Permissao) permissaoDao.findByField("nome",permissaoUpdate.getNome());
				if(permissao==null){
					permissaoDao.save(permissaoUpdate);
				}else{
					permissaoUpdate = permissao;
				}

				/*- DELETE -*/
				Permissao permissaoDelete = new Permissao();
				permissaoDelete.setNome("DELETE");
				permissao = (Permissao) permissaoDao.findByField("nome",permissaoDelete.getNome());
				if(permissao==null){
					permissaoDao.save(permissaoDelete);
				}else{
					permissaoDelete = permissao;
				}
				
				/*- ADMINISTRADOR -*/
				Permissao permissaoAdministrador = new Permissao();
				permissaoAdministrador.setNome("ADMIN");
				permissao = (Permissao) permissaoDao.findByField("nome",permissaoAdministrador.getNome());
				if(permissao==null){
					permissaoDao.save(permissaoAdministrador);
				}else{
					permissaoAdministrador = permissao;
				}

				/*- PROPRIETARIO -*/
				Permissao permissaoProprietario = new Permissao();
				permissaoProprietario.setNome("PROPRIETARIO");
				permissao = (Permissao) permissaoDao.findByField("nome",permissaoProprietario.getNome());
				if(permissao==null){
					permissaoDao.save(permissaoProprietario);
				}else{
					permissaoProprietario = permissao;
				}

				/*- FUNCIONARIO -*/
				Permissao permissaoFuncionario = new Permissao();
				permissaoFuncionario.setNome("FUNCIONARIO");
				permissao = (Permissao) permissaoDao.findByField("nome",permissaoFuncionario.getNome());
				if(permissao==null){
					permissaoDao.save(permissaoFuncionario);
				}else{
					permissaoFuncionario = permissao;
				}

				/*----- PERFIL -----*/
				Set<Perfil> perfils = new HashSet<Perfil>();
				Perfil perfil = new Perfil();

				/*- ADMIN -*/
				Perfil perfilAdmin = new Perfil();
				perfilAdmin.setNome("ADMIN");
				perfil = (Perfil) perfilDao.findByField("nome", perfilAdmin.getNome());
				if(perfil==null){
					permissoes = new HashSet<Permissao>();
					permissoes.add(permissaoList);
					permissoes.add(permissaoSave);
					permissoes.add(permissaoUpdate);
					permissoes.add(permissaoDelete);
					permissoes.add(permissaoAdministrador);
					permissoes.add(permissaoProprietario);
					permissoes.add(permissaoFuncionario);
					perfilAdmin.setPermissoes(permissoes);
					perfilDao.save(perfilAdmin);
				}else{
					perfilAdmin = perfil;
				}

				/*- CLIENTE -*/
				Perfil perfilCliente = new Perfil();
				perfilCliente.setNome("CLIENTE");
				perfil = (Perfil) perfilDao.findByField("nome", perfilCliente.getNome());
				if(perfil==null){
					permissoes = new HashSet<Permissao>();
					permissoes.add(permissaoList);
					perfilCliente.setPermissoes(permissoes);
					perfilDao.save(perfilCliente);
				}else{
					perfilCliente = perfil;
				}

				/*- FUNCIONARIO -*/
				Perfil perfilFuncionario = new Perfil();
				perfilFuncionario.setNome("FUNCIONARIO");
				perfil = (Perfil) perfilDao.findByField("nome", perfilFuncionario.getNome());
				if(perfil==null){
					permissoes = new HashSet<Permissao>();
					permissoes.add(permissaoFuncionario);
					permissoes.add(permissaoSave);
					permissoes.add(permissaoList);
					permissoes.add(permissaoUpdate);
					perfilFuncionario.setPermissoes(permissoes);
					perfilDao.save(perfilFuncionario);
				}else{
					perfilFuncionario = perfil;
				}

				/*- PROPRIETARIO -*/
				Perfil perfilProprietario = new Perfil();
				perfilProprietario.setNome("PROPRIETARIO");
				perfil = (Perfil) perfilDao.findByField("nome", perfilProprietario.getNome());
				if(perfil==null){
					permissoes = new HashSet<Permissao>();
					permissoes.add(permissaoProprietario);
					permissoes.add(permissaoSave);
					permissoes.add(permissaoList);
					//permissoes.add(permissaoUpdate);
					perfilProprietario.setPermissoes(permissoes);
					perfilDao.save(perfilProprietario);
				}else{
					perfilProprietario = perfil;
				}

				/*----- USUARIO -----*/
				Usuario usuario = new Usuario();

				/*- USUARIO ADM -*/
				Usuario usuarioAdm = new Usuario();
				usuarioAdm.setLogin("adm");
				usuarioAdm.setSenha("admx");
				usuarioAdm.setEmail("adm@boxclick.com.br");

				//PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				//usuarioAdm.setSenha(passwordEncoder.encode(usuarioAdm.getSenha()));

				usuario = (Usuario) usuarioDao.findByField("login",usuarioAdm.getLogin());
				if(usuario==null){
					perfils = new HashSet<Perfil>();
					perfils.add(perfilAdmin);
					usuarioAdm.setPerfis(perfils);
					usuarioDao.save(usuarioAdm);
				}else{
					usuarioAdm = usuario;
				}

				/*- USUARIO FUNCIONARIO 1 -*/
				Usuario usuarioFuncionario1 = new Usuario();
				usuarioFuncionario1.setLogin("funcionario1");
				usuarioFuncionario1.setSenha("funcionario1x");
				usuarioFuncionario1.setEmail("funcionario1@boxclick.com.br");
				usuario = (Usuario) usuarioDao.findByField("login",usuarioFuncionario1.getLogin());
				if(usuario==null){
					perfils = new HashSet<Perfil>();
					perfils.add(perfilFuncionario);
					usuarioFuncionario1.setPerfis(perfils);
					usuarioDao.save(usuarioFuncionario1);
				}else{
					usuarioFuncionario1 = usuario;
				}

				/*- USUARIO FUNCIONARIO 2 -*/
				Usuario usuarioFuncionario2 = new Usuario();
				usuarioFuncionario2.setLogin("funcionario2");
				usuarioFuncionario2.setSenha("funcionario2x");
				usuarioFuncionario2.setEmail("funcionario2@boxclick.com.br");
				usuario = (Usuario) usuarioDao.findByField("login",usuarioFuncionario2.getLogin());
				if(usuario==null){
					perfils = new HashSet<Perfil>();
					perfils.add(perfilFuncionario);
					usuarioFuncionario2.setPerfis(perfils);
					usuarioDao.save(usuarioFuncionario2);
				}else{
					usuarioFuncionario2 = usuario;
				}

				/*
					for (int i = 1; i < 5000; i++) {
						usuarioDemo = new Usuario();
						usuarioDemo.setLogin("demo"+i);
						usuarioDemo.setSenha("demo"+i);
						usuarioDemo.setEmail(i+"demo@boxclick.com.br");
						usuario = (Usuario) usuarioDao.findByField("login",usuarioDemo.getLogin());
						if(usuario==null){
							perfils = new HashSet<Perfil>();
							perfils.add(perfilUsuario);
							usuarioDemo.setPerfis(perfils);
							usuarioDao.save(usuarioDemo);
						}else{
							usuarioDemo = usuario;
						}
					}
				 */

				log.debug("USUARIOS CADASTRADOS");

				addCidades.Add();
				log.debug("CIDADES CADASTRADAS");


				//PESSOA TIPO -----------------------------------------------
				PessoaTipo pessoaTipo = new PessoaTipo();

				//PROPRIETARIO
				PessoaTipo pessoaTipoProprietario =  new PessoaTipo();
				pessoaTipoProprietario.setNome("Proprietário");
				pessoaTipo = (PessoaTipo) pessoaTipoDao.findByField("nome",pessoaTipoProprietario.getNome());
				if(pessoaTipo==null)
					pessoaTipoDao.save(pessoaTipoProprietario);
				else
					pessoaTipoProprietario = pessoaTipo;

				//CLIENTE
				PessoaTipo pessoaTipoCliente =  new PessoaTipo();
				pessoaTipoCliente.setNome("Cliente");
				pessoaTipo = (PessoaTipo) pessoaTipoDao.findByField("nome",pessoaTipoCliente.getNome());
				if(pessoaTipo==null)
					pessoaTipoDao.save(pessoaTipoCliente);
				else
					pessoaTipoCliente = pessoaTipo;

				//CONTATO
				PessoaTipo pessoaTipoContato =  new PessoaTipo();
				pessoaTipoContato.setNome("Contato");
				pessoaTipo = (PessoaTipo) pessoaTipoDao.findByField("nome",pessoaTipoContato.getNome());
				if(pessoaTipo==null)
					pessoaTipoDao.save(pessoaTipoContato);
				else
					pessoaTipoContato = pessoaTipo;

				/*//FUNCIONARIO
					PessoaTipo pessoaTipoFuncionario =  new PessoaTipo();
					pessoaTipoFuncionario.setNome("Funcionário");
					pessoaTipo = (PessoaTipo) pessoaTipoDao.findByField("nome",pessoaTipoFuncionario.getNome());
					if(pessoaTipo==null)
						pessoaTipoDao.save(pessoaTipoFuncionario);
					else
						pessoaTipoFuncionario = pessoaTipo;*/

				log.debug("TIPOS PESSOA CADASTRADAS");

				//PREDIO -----------------------------------------------

				Predio predio = new Predio();

				//GR
				Predio predioGR = new Predio();
				predioGR.setNome("GR");
				predio = (Predio) predioDao.findByField("nome",predioGR.getNome());
				if(predio==null)
					predioDao.save(predioGR);
				else
					predioGR = predio;

				//GV
				Predio predioGV = new Predio();
				predioGV.setNome("GV");
				predio = (Predio) predioDao.findByField("nome",predioGV.getNome());
				if(predio==null)
					predioDao.save(predioGV);
				else
					predioGV = predio;

				//Caparao
				Predio predioCaparao = new Predio();
				predioCaparao.setNome("Caparaó");
				predio = (Predio) predioDao.findByField("nome",predioCaparao.getNome());
				if(predio==null)
					predioDao.save(predioCaparao);
				else
					predioCaparao = predio;

				//MARINETE
				Predio predioMarinete = new Predio();
				predioMarinete.setNome("Marinete");
				predio = (Predio) predioDao.findByField("nome",predioMarinete.getNome());
				if(predio==null)
					predioDao.save(predioMarinete);
				else
					predioMarinete = predio;

				log.debug("PREDIOS CADASTRADAS");


				//BANCO -----------------------------------------------

				Banco banco = new Banco();

				Banco bancoItau = new Banco();
				bancoItau.setNome("ITAU");
				banco = (Banco) bancoDao.findByField("nome",bancoItau.getNome());
				if(banco==null){
					bancoDao.save(bancoItau);
					log.debug("BANCO ITAU CADASTRADO");
				}else
					bancoItau = banco;

				Banco bancoCEF = new Banco();
				bancoCEF.setNome("CEF");
				banco = (Banco) bancoDao.findByField("nome",bancoCEF.getNome());
				if(banco==null){
					bancoDao.save(bancoCEF);
					log.debug("BANCO CEF CADASTRADO");
				}else
					bancoCEF = banco;

				//CONTA -----------------------------------------------

				/*
					Conta conta = new Conta();

					Conta contaProprietario1 = new Conta();
					contaProprietario1.setBanco(bancoItau);
					contaProprietario1.setAgencia("7178");
					contaProprietario1.setContaCorrente("18230-4");
					contaProprietario1.setTitular((Pessoa)pessoaFisicaProprietario1);
					conta = (Conta) contaDao.findByField("contaCorrente",contaProprietario1.getContaCorrente());
					if(conta==null)
						contaDao.save(contaProprietario1);
					else
						contaProprietario1 = conta;*/

				//ESTACIONAMENTO -----------------------------------------------

				Estacionamento estacionamento = new Estacionamento();

				//ESTACIONAMENTO 1
				Estacionamento estacionamento1 = new Estacionamento();
				estacionamento1.setIdentificador("101");
				estacionamento = (Estacionamento) estacionamentoDao.findByField("identificador",estacionamento1.getIdentificador());
				if(estacionamento==null){
					estacionamentoDao.save(estacionamento1);
					log.debug("ESTACIONAMENTO 1 CADASTRADOS");
				}else
					estacionamento1 = estacionamento;


				//APARTAMENTO -----------------------------------------------

				Apartamento apartamentoNew = new Apartamento();
				Apartamento apartamento = new Apartamento();

				/*apartamentoNew.setPredio(predioCaparao);
					apartamentoNew.setIdentificador("123");
					apartamentoNew.setProprietario(pessoaFisicaProprietario1);
					//ITENS
					Set<ApartamentoItem> apartamentoItens = new HashSet<ApartamentoItem>();
					apartamentoItens.add(new ApartamentoItem("Quarto",2));
					apartamentoItens.add(new ApartamentoItem("Garagem",1));
					apartamentoNew.setApartamentoItens(apartamentoItens);

					apartamentoNew.setValorDiaria(new BigDecimal(150));

					apartamento = (Apartamento) apartamentoDao.findByField("identificador", apartamentoNew.getIdentificador());
					if(apartamento==null)
						apartamentoDao.save(apartamentoNew);
					else
						apartamentoNew = apartamento;*/

				//PESSOAS -----------------------------------------------

				PessoaFisica pessoa;

				//PESSOAS PROPRIETARIOS -----------------------------------------------

				//PROPRIETARIOS
				PessoaFisica pessoaFisicaProprietario = new PessoaFisica();

				class Proprietario{
					String nome;
					String usuario;
					Banco banco;
					String agencia;
					String conta;
					List<Apartamento> apartamentos;
					public List<Apartamento> getApartamentos() {
						return apartamentos;
					}
				}
				List<Proprietario> listaProprietarios = new ArrayList<Proprietario>();
				Proprietario proprietario = new Proprietario();
				Usuario usuarioProprietario = new Usuario();
				perfils = new HashSet<Perfil>();
				perfils.add(perfilProprietario);
				Conta contaProprietario = new Conta();
				List<Apartamento> listaApartamentosProprietario = new ArrayList<Apartamento>();

				//Nackle Garios
				proprietario = new Proprietario();
				proprietario.nome = "Nackle Garios";
				proprietario.usuario = "nackle";
				proprietario.banco = bancoCEF;
				proprietario.agencia = "3065";
				proprietario.conta = "30.000-9";
				listaApartamentosProprietario = new ArrayList<Apartamento>();
				apartamento = new Apartamento();
				apartamento.setIdentificador("301");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("302");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("303");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("304");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("404");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				proprietario.apartamentos = listaApartamentosProprietario;
				listaProprietarios.add(proprietario);

				//Locmen Garios Filho
				proprietario = new Proprietario();
				proprietario.nome = "Locmen Garios Filho";
				proprietario.usuario = "locmen";
				proprietario.banco = bancoCEF;
				proprietario.agencia = "";
				proprietario.conta = "";
				listaApartamentosProprietario = new ArrayList<Apartamento>();
				apartamento = new Apartamento();
				apartamento.setIdentificador("601");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("602");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("603");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("604");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("401");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("Caparaó");
				apartamento.setPredio(predioCaparao);
				listaApartamentosProprietario.add(apartamento);
				proprietario.apartamentos = listaApartamentosProprietario;
				listaProprietarios.add(proprietario);

				//Wadih Antonio Garios
				proprietario = new Proprietario();
				proprietario.nome = "Wadih Antonio Garios";
				proprietario.usuario = "wadih";
				proprietario.banco = bancoCEF;
				proprietario.agencia = "";
				proprietario.conta = "";
				listaApartamentosProprietario = new ArrayList<Apartamento>();
				apartamento = new Apartamento();
				apartamento.setIdentificador("201");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("202");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("203");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("204");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("402");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				proprietario.apartamentos = listaApartamentosProprietario;
				listaProprietarios.add(proprietario);

				//Emílio Carlos Garios
				proprietario = new Proprietario();
				proprietario.nome = "Emílio Carlos Garios";
				proprietario.usuario = "emilio";
				proprietario.banco = bancoCEF;
				proprietario.agencia = "";
				proprietario.conta = "";
				listaProprietarios.add(proprietario);

				//Maria Aparecida Lino Garios
				proprietario = new Proprietario();
				proprietario.nome = "Maria Aparecida Lino Garios";
				proprietario.usuario = "maria";
				proprietario.banco = bancoCEF;
				proprietario.agencia = "3065";
				proprietario.conta = "30.000-9";
				listaApartamentosProprietario = new ArrayList<Apartamento>();
				apartamento = new Apartamento();
				apartamento.setIdentificador("301");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("302");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("303");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("304");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("401");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("402");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("403");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("404");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("501");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("502");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("503");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("504");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("602");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("603");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("701");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("702");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("703");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("802");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("901");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("902");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("903");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("403");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("702");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("704");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("701");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("204");
				apartamento.setPredio(predioGV);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("703");
				apartamento.setPredio(predioGR);
				listaApartamentosProprietario.add(apartamento);
				proprietario.apartamentos = listaApartamentosProprietario;
				listaProprietarios.add(proprietario);

				//MARINETE 5° ANDAR
				proprietario = new Proprietario();
				proprietario.nome = "MARINETE 5° ANDAR";
				proprietario.usuario = "marinete";
				proprietario.banco = bancoCEF;
				proprietario.agencia = "";
				proprietario.conta = "";
				listaApartamentosProprietario = new ArrayList<Apartamento>();
				apartamento = new Apartamento();
				apartamento.setIdentificador("Marinete 1");
				apartamento.setPredio(predioMarinete);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("Marinete 2");
				apartamento.setPredio(predioMarinete);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("Marinete 3");
				apartamento.setPredio(predioMarinete);
				listaApartamentosProprietario.add(apartamento);
				apartamento = new Apartamento();
				apartamento.setIdentificador("Marinete 4");
				apartamento.setPredio(predioMarinete);
				listaApartamentosProprietario.add(apartamento);
				proprietario.apartamentos = listaApartamentosProprietario;
				listaProprietarios.add(proprietario);

				for (Proprietario newProprietario : listaProprietarios) {
					pessoaFisicaProprietario = new PessoaFisica();
					pessoaFisicaProprietario.setNome(newProprietario.nome);
					pessoaFisicaProprietario.setPessoaTipo(pessoaTipoProprietario);
					usuarioProprietario = new Usuario();
					usuarioProprietario.setLogin(newProprietario.usuario);
					usuarioProprietario.setSenha(newProprietario.usuario);
					usuarioProprietario.setEmail(newProprietario.usuario+"@email.com.br");
					usuarioProprietario.setPerfis(perfils);
					pessoaFisicaProprietario.setUsuario(usuarioProprietario);
					contaProprietario = new Conta();
					contaProprietario.setBanco(newProprietario.banco);
					contaProprietario.setAgencia(newProprietario.agencia);
					contaProprietario.setContaCorrente(newProprietario.conta);
					pessoaFisicaProprietario.setConta(contaProprietario);
					pessoa = new PessoaFisica();
					pessoa = (PessoaFisica) pessoaFisicaDao.findByField("nome", pessoaFisicaProprietario.getNome());
					if(pessoa==null){
						pessoaFisicaDao.save(pessoaFisicaProprietario);
						log.debug(pessoaFisicaProprietario.getUsuario().getLogin()+" CADASTRADO");
						//contaProprietario.setTitular(pessoaFisicaProprietario);
						//contaDao.save(contaProprietario);
						if(newProprietario.apartamentos!=null && newProprietario.apartamentos.size()>0){
							for (Apartamento apartamentoProprietario : newProprietario.getApartamentos()) {
								apartamentoNew = new Apartamento();
								apartamentoNew.setPredio(apartamentoProprietario.getPredio());
								apartamentoNew.setIdentificador(apartamentoProprietario.getIdentificador());
								apartamentoNew.setProprietario(pessoaFisicaProprietario);
								if(apartamentoNew.getIdentificador().equals("Marinete 1"))
									apartamentoNew.setValorDiaria(new BigDecimal(150));

								DetachedCriteria criteria = DetachedCriteria.forClass(Apartamento.class);
								criteria.add( Restrictions.eq("identificador",apartamentoNew.getIdentificador()));
								criteria.add( Restrictions.eq("predio",apartamentoNew.getPredio()));
								criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
								List<Apartamento> apartamentosResult = apartamentoDao.findCriteria(criteria);
								if(apartamentosResult.size()==0)
									apartamentoDao.save(apartamentoNew);

							}
						}
					}

				}


				//PROPRIETARIO 1
				PessoaFisica pessoaFisicaProprietario1 = new PessoaFisica();
				pessoaFisicaProprietario1.setNome("Proprietário 1");
				pessoaFisicaProprietario1.setPessoaTipo(pessoaTipoProprietario);
				//pessoaFisicaFabio.setConta(contaFabio);
				Usuario usuarioProprietario1 = new Usuario();
				usuarioProprietario1.setLogin("proprietario");
				usuarioProprietario1.setSenha("proprietariox");
				usuarioProprietario1.setEmail("proprietario@boxclick.com.br");
				perfils = new HashSet<Perfil>();
				perfils.add(perfilProprietario);
				usuarioProprietario1.setPerfis(perfils);
				pessoaFisicaProprietario1.setUsuario(usuarioProprietario1);
				pessoa = (PessoaFisica) pessoaFisicaDao.findByField("nome", pessoaFisicaProprietario1.getNome());
				if(pessoa==null){
					pessoaFisicaDao.save(pessoaFisicaProprietario1);
					log.debug("PROPRIETARIO 1 CADASTRADO");
				}else
					pessoaFisicaProprietario1 = pessoa;

				/*//PROPRIETARIO 2
					PessoaFisica pessoaFisicaProprietario2 = new PessoaFisica();
					pessoaFisicaProprietario2.setNome("Proprietario 2");
					pessoaFisicaProprietario2.setPessoaTipo(pessoaTipoProprietario);
					pessoa = (PessoaFisica) pessoaFisicaDao.findByField("nome", pessoaFisicaProprietario2.getNome());
					if(pessoa==null){
						pessoaFisicaDao.save(pessoaFisicaProprietario2);
					}else
						pessoaFisicaProprietario2 = pessoa;*/

				/*//PROPRIETARIO 3
					PessoaFisica pessoaFisicaProprietario3 = new PessoaFisica();
					pessoaFisicaProprietario3.setNome("Proprietario 3");
					pessoaFisicaProprietario3.setPessoaTipo(pessoaTipoProprietario);
					pessoa = (PessoaFisica) pessoaFisicaDao.findByField("nome", pessoaFisicaProprietario3.getNome());
					if(pessoa==null){
						pessoaFisicaDao.save(pessoaFisicaProprietario3);
					}else
						pessoaFisicaProprietario3 = pessoa;*/

				//PESSOAS CLIENTES -----------------------------------------------

				//CLIENTE 1
				PessoaFisica pessoaCliente1 = new PessoaFisica();
				pessoaCliente1.setNome("Cliente 1");
				pessoaCliente1.setCpf("111.111.111-11");
				pessoaCliente1.setRg("55225");
				pessoaCliente1.setOrgaoExpedidor("PMERJ");
				pessoaCliente1.setPessoaTipo(pessoaTipoCliente);

				pessoaCliente1.setCep("28030-335");
				pessoaCliente1.setBairro("pq São Caetano");
				pessoaCliente1.setLagrodouro("Rua anita Pessanha");
				pessoaCliente1.setNumero("156");
				pessoaCliente1.setComplemento("apto 201");
				pessoaCliente1.setTipoEndereco("Residencial");
				Cidade cidade = new Cidade();
				cidade.setIdCidade(2);
				pessoaCliente1.setCidade(cidade);

				pessoaCliente1.setTelefone1("(22) 9812-9297");
				pessoaCliente1.setTelefone2("(22) 9976-3921");

				pessoaCliente1.setCarroDescricao("KIA Sportage (AZUL)");
				pessoaCliente1.setCarroPlaca("MLK-7304");

				Usuario usuarioCliente1 = new Usuario();
				usuarioCliente1.setLogin("cliente");
				usuarioCliente1.setSenha("clientex");
				usuarioCliente1.setEmail("cliente@boxclick.com.br");
				perfils = new HashSet<Perfil>();
				perfils.add(perfilCliente);
				usuarioCliente1.setPerfis(perfils);

				pessoaCliente1.setUsuario(usuarioCliente1);

				pessoa = (PessoaFisica) pessoaFisicaDao.findByField("nome", pessoaCliente1.getNome());
				if(pessoa==null){
					pessoaFisicaDao.save(pessoaCliente1);
					log.debug("CLIENTE 1 CADASTRADO");
				}else
					pessoaCliente1 = pessoa;


				//TIPO RESERVA -----------------------------------------------

				ReservaTipo reservaTipo = new ReservaTipo();

				//PRE RESERVA -----------------
				ReservaTipo tipoPreReserva = new ReservaTipo();
				tipoPreReserva.setNome("Pré-reserva");
				reservaTipo = (ReservaTipo) reservaTipoDao.findByField("nome",tipoPreReserva.getNome());
				if(reservaTipo==null)
					reservaTipoDao.save(tipoPreReserva);
				else
					tipoPreReserva = reservaTipo;

				ReservaTipo tipoReserva = new ReservaTipo();

				//AGUARDANDO PAGAMENTO -----------------
				tipoReserva = new ReservaTipo();
				tipoReserva.setNome("Aguardando pagamento");
				reservaTipo = (ReservaTipo) reservaTipoDao.findByField("nome",tipoReserva.getNome());
				if(reservaTipo==null)
					reservaTipoDao.save(tipoReserva);

				//PAGO PARCIALMENTE -----------------
				tipoReserva = new ReservaTipo();
				tipoReserva.setNome("Pago parcialmente");
				reservaTipo = (ReservaTipo) reservaTipoDao.findByField("nome",tipoReserva.getNome());
				if(reservaTipo==null)
					reservaTipoDao.save(tipoReserva);

				//Cancelado -----------------
				tipoReserva = new ReservaTipo();
				tipoReserva.setNome("Cancelado");
				reservaTipo = (ReservaTipo) reservaTipoDao.findByField("nome",tipoReserva.getNome());
				if(reservaTipo==null)
					reservaTipoDao.save(tipoReserva);

				//CheckIn -----------------
				tipoReserva = new ReservaTipo();
				tipoReserva.setNome("CheckIn");
				reservaTipo = (ReservaTipo) reservaTipoDao.findByField("nome",tipoReserva.getNome());
				if(reservaTipo==null)
					reservaTipoDao.save(tipoReserva);

				//CheckOut -----------------
				tipoReserva = new ReservaTipo();
				tipoReserva.setNome("CheckOut");
				reservaTipo = (ReservaTipo) reservaTipoDao.findByField("nome",tipoReserva.getNome());
				if(reservaTipo==null)
					reservaTipoDao.save(tipoReserva);

				//Estendido -----------------
				tipoReserva = new ReservaTipo();
				tipoReserva.setNome("Estendido");
				reservaTipo = (ReservaTipo) reservaTipoDao.findByField("nome",tipoReserva.getNome());
				if(reservaTipo==null)
					reservaTipoDao.save(tipoReserva);

				log.debug("TIPOS DE RESERVAS CADASTRADAS");

				//RESERVA -----------------------------------------------
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				//Date date = new Date();
				//date = df.parse("20/12/2015");
				//Reserva reserva = new Reserva();

				Reserva reservaCliente1 = new Reserva();
				reservaCliente1.setCliente((Pessoa)pessoaCliente1);
				reservaCliente1.setComAnimal(false);
				reservaCliente1.setQntVagas(1);
				reservaCliente1.setQuantidadeAdulto(2);
				reservaCliente1.setQuantidadeCrianca(1);
				//reservaCliente1.setSituacao("reserva");
				reservaCliente1.setReservaTipo(tipoPreReserva);
				Set<Estacionamento> listaEstacionamentos = new HashSet<Estacionamento>();
				listaEstacionamentos.add(estacionamento1);
				reservaCliente1.setEstacionamentos(listaEstacionamentos);

				reservaCliente1.setDataPretencaoInicio((Date)df.parse("20/01/2016 07:00"));
				reservaCliente1.setDataPretencaoFim((Date)df.parse("25/01/2016 08:00"));

				reservaCliente1.setApartamento(apartamentoNew);


				/*DetachedCriteria criteria = DetachedCriteria.forClass(Reserva.class);
					//criteria.add( Restrictions.ge( "dataPretencaoInicio",reservaCarlos.getDataPretencaoInicio() ) );
					//criteria.add( Restrictions.lt( "dataPretencaoInicio",reservaCarlos.getDataPretencaoInicio() ) );
					criteria.add(Restrictions.eq("comVaga", true));
					List<Reserva> listaReservas = reservaDao.findCriteria(criteria);*/

				List<Reserva> listaReservas = reservaDao.findAllByField("cliente", (Pessoa)pessoaCliente1);

				if(listaReservas.size()==0){
					reservaDao.save(reservaCliente1);
					log.debug("RESERVA CADASTRADO");
				}
			}

		} catch (Exception e) {
			log.debug("ERRO CONFIG");
			//log.debug("ERRO CONFIG "+e.getMessage());
		}

	}



}
