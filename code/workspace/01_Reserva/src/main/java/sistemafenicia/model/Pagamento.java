package sistemafenicia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import sistemafenicia.service.UsuarioSistemaService;

@Entity
@Table(name = "pagamento")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Pagamento implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idPagamento;

	////////// RELACIONAMENTOS

	/*//PAGAMENTOS(*)=(1)MOVIMENTACAO
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="movimentacao_id")
	private Movimentacao movimentacao;*/

	///////////////////////////////////////////////////// COLUNAS

	@Column(nullable = false, updatable = false, length = 100)
	private String tipo;
	
	@Column(name = "dt_pagamento", nullable = true, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataPagamento = new Date();
	
	@Column(name="valor_pago",columnDefinition="Decimal(30,2) default '0.00' ")
	private BigDecimal valorPago;
	
	@Column(name="parcela_paga")
	private Integer parcelaPaga;
	
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
	
	///////////////////////////////////////////////////// GETS E SETS
	
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

	public Integer getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(Integer idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public Integer getParcelaPaga() {
		return parcelaPaga;
	}

	public void setParcelaPaga(Integer parcelaPaga) {
		this.parcelaPaga = parcelaPaga;
	}
	
	/*public Movimentacao getMovimentacao() {
		return movimentacao;
	}
	
	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}*/
	

	////////////////////////////////////////// TOSTRING
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.valorPago);
		return builder.toString();
	}

}
