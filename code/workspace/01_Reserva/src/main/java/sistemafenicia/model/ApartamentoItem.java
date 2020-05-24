package sistemafenicia.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "apartamento_item")
public class ApartamentoItem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idApartamentoItem;

	////////// RELACIONAMENTOS
	
	/*//BIDIRECIONAL
	@ManyToOne
	private Apartamento apartamento;*/

	///////////////////////////////////////////////////// COLUNAS

	@Column(name = "nome", nullable = true, updatable = false, length = 255)
	private String nome;
	
	@Column(name = "quantidade", nullable = true, updatable = true)
	private Integer quantidade;
	
	////////////////////////////////////////// TOSTRING
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.nome);
		return builder.toString();
	}
	
	///////////////////////////////////////////////////// GETS E SETS

	/*
	public Apartamento getApartamento() {
		return apartamento;
	}
	
	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}
	*/
	
	public Integer getIdApartamentoItem() {
		return idApartamentoItem;
	}

	public void setIdApartamentoItem(Integer idApartamentoItem) {
		this.idApartamentoItem = idApartamentoItem;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public ApartamentoItem() {
		super();
	}

	public ApartamentoItem(String nome, Integer quantidade) {
		super();
		this.nome = nome;
		this.quantidade = quantidade;
	}
	
	
}
