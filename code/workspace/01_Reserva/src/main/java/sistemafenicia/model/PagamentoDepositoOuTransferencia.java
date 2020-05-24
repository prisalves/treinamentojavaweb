package sistemafenicia.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "pagamento_deposito_ou_transferencia")
@PrimaryKeyJoinColumn(name = "id_pagamento")
public class PagamentoDepositoOuTransferencia extends Pagamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false, updatable = false, length = 100)
	private String identificacao;

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	
	

}
