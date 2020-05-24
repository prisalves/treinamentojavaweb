package sistemafenicia.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "pagamento_cheque")
@PrimaryKeyJoinColumn(name = "id_pagamento")
public class PagamentoCheque extends Pagamento implements Serializable {

	private static final long serialVersionUID = 1L;

	//PAGAMENTO_CARTAO(1)=(1)EMITENTE
	@OneToOne(orphanRemoval=true,fetch=FetchType.LAZY,optional=false,cascade=CascadeType.ALL)
	@JoinColumn(name="emitente_id")
	@ForeignKey(name="Fk_pagamento_cartao_emitente")
	private Pessoa emitente;

	@Column(nullable = false, updatable = false, length = 100)
	private String numero;

	@Column(name="tipo_recebimento", nullable = false, updatable = false, length = 100)
	private String tipoRecebimento;

	@Column(nullable = false, updatable = false, length = 100)
	private String situacao;

	public Pessoa getEmitente() {
		return emitente;
	}

	public void setEmitente(Pessoa emitente) {
		this.emitente = emitente;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipoRecebimento() {
		return tipoRecebimento;
	}

	public void setTipoRecebimento(String tipoRecebimento) {
		this.tipoRecebimento = tipoRecebimento;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	

}
