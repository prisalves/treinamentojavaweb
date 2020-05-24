package sistemafenicia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "apartamento_diaria")
public class ApartamentoDiaria implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idApartamentoDiaria;

	////////// RELACIONAMENTOS
	
	/*//BIDIRECIONAL
	@ManyToOne()
	private Apartamento apartamento;*/

	///////////////////////////////////////////////////// COLUNAS

	@Column(name = "nome", nullable = true, updatable = false, length = 255)
	private String nome;
	
	@Column(name="data_inicio",nullable=false,updatable=true)
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	@Column(name="data_fim",nullable=false,updatable=true)
	@Temporal(TemporalType.DATE)
	private Date dataFim;
	
	@Column(columnDefinition="Decimal(30,2) default '0.00' ",nullable=false,updatable=true)
	private BigDecimal valor;
	
	////////////////////////////////////////// TOSTRING
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.nome);
		return builder.toString();
	}
	
	///////////////////////////////////////////////////// GETS E SETS

	
	/*public Apartamento getApartamento() {
		return apartamento;
	}
	
	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}*/
	
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public Integer getIdApartamentoDiaria() {
		return idApartamentoDiaria;
	}
	public void setIdApartamentoDiaria(Integer idApartamentoDiaria) {
		this.idApartamentoDiaria = idApartamentoDiaria;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ApartamentoDiaria() {
		super();
	}

	public ApartamentoDiaria(String nome, Date dataInicio, Date dataFim, BigDecimal valor) {
		super();
		this.nome = nome;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.valor = valor;
	}
	
	
	
}
