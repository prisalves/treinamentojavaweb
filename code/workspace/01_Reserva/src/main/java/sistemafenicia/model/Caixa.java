package sistemafenicia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import sistemafenicia.utils.FacesUtil;

@Entity
@Table(name = "caixa")
public class Caixa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idCaixa;

	////////// RELACIONAMENTOS

	/*
	//CAIXA(*)=(1)CONTA
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="conta_id")
	private Conta conta;
	*/

	/*//CAIXA(1)=(1)OPERADOR
	@OneToOne(orphanRemoval=false,fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="operador_id")
	private Usuario operador;*/
	
	//CAIXA(*)=(1)OPERADOR
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="operador_id")
	private Usuario operador;
		
	//BIDIRECIONAL
	@OneToMany(mappedBy="caixa",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private Set<Movimentacao> movimentacoes = new HashSet<Movimentacao>(0);

	///////////////////////////////////////////////////// COLUNAS

	@Column(columnDefinition="Decimal(30,2) default '0.00' ",nullable=false,updatable=true)
	private BigDecimal saldo = new BigDecimal(0);
	
	@Column(name="data_caixa",nullable=false,updatable=false)
	@Temporal(TemporalType.DATE)
	private Date dataCaixa;
	
	@Column(name="data_fechamento",nullable=true,updatable=true)
	@Temporal(TemporalType.DATE)
	private Date dataFechamento;

	//STATUS

	@Column(name="status")
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
		//builder.append(this.getOperador()+"/");
		//builder.append(this.getConta()+"/");
		if(this.dataCaixa!=null)
			builder.append( FacesUtil.converteData(this.dataCaixa,null) );
		return builder.toString();
	}
	
	///////////////////////////////////////////////////// GETS E SETS

	public Integer getIdCaixa() {
		return idCaixa;
	}

	public void setIdCaixa(Integer idCaixa) {
		this.idCaixa = idCaixa;
	}
	
	public Date getDataCaixa() {
		return dataCaixa;
	}
	
	public void setDataCaixa(Date dataCaixa) {
		this.dataCaixa = dataCaixa;
	}
	
	public Date getDataFechamento() {
		return dataFechamento;
	}
	
	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	/*public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}*/

	public Usuario getOperador() {
		return operador;
	}

	public void setOperador(Usuario operador) {
		this.operador = operador;
	}

	public Set<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(Set<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
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
