package sistemafenicia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import sistemafenicia.service.UsuarioSistemaService;

@Entity
@Table(name = "conta")
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idConta;

	////////// RELACIONAMENTOS
	
	//CONTAS(*)=(1)BANCO
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="banco_id")
	private Banco banco;

	/*//CONTA(1)=(1)ENDERECO
	@OneToOne(orphanRemoval=true,fetch=FetchType.LAZY,optional=true,cascade=CascadeType.ALL)
	@JoinColumn(name="endereco_id")
	@ForeignKey(name="Fk_conta_agencia_endereco")
	private Endereco agenciaEndereco;*/

	/*//CONTA(1)=(1)TITULAR
	@OneToOne(orphanRemoval=true,fetch=FetchType.LAZY,optional=true,cascade=CascadeType.ALL)
	@JoinColumn(name="titular_id")
	@ForeignKey(name="Fk_conta_titular")
	private Pessoa titular;*/

	/*//CONTA(1)=(1)GERENTE
	@OneToOne(orphanRemoval=true,fetch=FetchType.LAZY,optional=true,cascade=CascadeType.ALL)
	@JoinColumn(name="gerente_id")
	@ForeignKey(name="Fk_conta_gerente")
	private Pessoa gerente;*/

	/*
	//BIDIRECIONAL
	@OneToMany(mappedBy="conta",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private Set<Caixa> caixas = new HashSet<Caixa>(0);
	*/
	
	/*//BIDIRECIONAL
	@OneToOne(optional=true,fetch=FetchType.EAGER,orphanRemoval=false)
	@JoinColumn(name="titular_id",referencedColumnName="id")
    private Pessoa titular;*/
	
	//BIDIRECIONAL
	@OneToMany(mappedBy="conta")
	private Set<Pessoa> titulares = new HashSet<Pessoa>(0);

	///////////////////////////////////////////////////// COLUNAS

	@Column(nullable = false, updatable = true, length = 100, unique=false)
	private String agencia;
	
	@Column(nullable = true, updatable = false, length = 100, unique=false)
	private String telefone;

	@Column(name="conta_corrente", nullable = false, updatable = true, length = 100, unique=false)
	private String contaCorrente;

	@Column(columnDefinition="Decimal(30,2) default '0.00' ")
	private BigDecimal saldo;

	//STATUS

	@Column(name="status", length=1)
	private boolean status = true;

	@Column(columnDefinition="TEXT", nullable = true, updatable = true)
	@Basic (fetch = FetchType.EAGER)
	private String obs;

	//LOG

	@Column(name = "log_dt_cadastro", nullable = true, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date logDataCadastro = new Date();

	@Column(name = "log_dt_modificado", updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date logDataModificado = new Date();

	@Column(name = "log_usuario", nullable = true, updatable = true, length = 100)
	private String log_usuario = UsuarioSistemaService.getUsername();
	
	//////////////////////////////////////////TOSTRING
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getBanco()+"/"+this.agencia+"/"+this.contaCorrente);
		return builder.toString();
	}
	
	///////////////////////////////////////////////////// GETS E SETS

	public Integer getIdConta() {
		return idConta;
	}

	public void setIdConta(Integer idConta) {
		this.idConta = idConta;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Set<Pessoa> getTitulares() {
		return titulares;
	}
	
	public void setTitulares(Set<Pessoa> titulares) {
		this.titulares = titulares;
	}

	/*public Set<Caixa> getCaixas() {
		return caixas;
	}

	public void setCaixas(Set<Caixa> caixas) {
		this.caixas = caixas;
	}*/

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(String contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Date getLogDataCadastro() {
		return logDataCadastro;
	}

	public void setLogDataCadastro(Date logDataCadastro) {
		this.logDataCadastro = logDataCadastro;
	}

	public Date getLogDataModificado() {
		return logDataModificado;
	}

	public void setLogDataModificado(Date logDataModificado) {
		this.logDataModificado = logDataModificado;
	}

	public String getLog_usuario() {
		return log_usuario;
	}

	public void setLog_usuario(String log_usuario) {
		this.log_usuario = log_usuario;
	}
	
	

}
