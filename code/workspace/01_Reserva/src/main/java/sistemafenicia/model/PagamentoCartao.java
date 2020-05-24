package sistemafenicia.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "pagamento_cartao")
@PrimaryKeyJoinColumn(name = "id_pagamento")
public class PagamentoCartao extends Pagamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, updatable = false, length = 100)
	private String autenticacao;
	
	@Column(nullable = false, updatable = false, length = 100)
	private String operadora;
	
	@Column(nullable = false, updatable = false, length = 100)
	private String transacao;

	public String getAutenticacao() {
		return autenticacao;
	}

	public void setAutenticacao(String autenticacao) {
		this.autenticacao = autenticacao;
	}

	public String getOperadora() {
		return operadora;
	}

	public void setOperadora(String operadora) {
		this.operadora = operadora;
	}

	public String getTransacao() {
		return transacao;
	}

	public void setTransacao(String transacao) {
		this.transacao = transacao;
	}
	
	
	

}
