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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;

import sistemafenicia.service.UsuarioSistemaService;

@Entity
@Table(name = "apartamento")
public class Apartamento implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idApartamento;

	////////// RELACIONAMENTOS
	
	/*//APARTAMENTO(1)=(*)ITENS
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	@JoinTable(name="apartamento_itens",
		joinColumns={@JoinColumn(name="apartamento_id",referencedColumnName="id")},
		inverseJoinColumns ={@JoinColumn(name="apartamentoitem_id",referencedColumnName="id")})
	private Set<ApartamentoItem> apartamentoItens = new HashSet<ApartamentoItem>(0);*/
	
	//APARTAMENTO(1)=(*)APARTAMENTO ITEM
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name="apartamento_id", referencedColumnName = "id")
	private Set<ApartamentoItem> apartamentoItens = new HashSet<ApartamentoItem>(0);
	
	//APARTAMENTO(1)=(*)APARTAMENTO DIARIA
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name="apartamento_id", referencedColumnName = "id")
	private Set<ApartamentoDiaria> apartamentoDiarias = new HashSet<ApartamentoDiaria>(0);
	
	//APARTAMENTO(*)=(1)PROPRIETARIO
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="proprietario_id", referencedColumnName = "id")
	private Pessoa proprietario;
	
	//APARTAMENTO(*)=(1)PREDIO
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="predio_id", referencedColumnName = "id")
	private Predio predio;
	
	//BIDIRECIONAL
	@OneToMany(mappedBy="apartamento",fetch=FetchType.LAZY,orphanRemoval=false)
	private Set<Reserva> reservas = new HashSet<Reserva>(0);
	
	//BIDIRECIONAL
	@ManyToMany(mappedBy="apartamentos",fetch=FetchType.LAZY)
	private Set<Movimentacao> movimentacoes = new HashSet<Movimentacao>(0);
	
	
	///////////////////////////////////////////////////// COLUNAS

	@NotBlank(message="Identificador: não pode ser vazio.")
	@Column(nullable = false, updatable = true, length = 100)
	private String identificador;
	
	@Column(name = "limite_adulto", nullable = true, updatable = true)
	private Integer limiteAdulto = 2;
	
	@Column(name = "limite_crianca", nullable = true, updatable = true)
	private Integer limiteCrianca = 0;
	
	@Column(name = "metros_quadrados", nullable = true, updatable = true)
	private Integer metrosQuadrados;
	
	@Column(name="aceitaAnimal")
	private boolean aceitaAnimal = false;
	
	@Column(name = "qnt_vagas", nullable = true, updatable = true)
	private Integer qntVagas = 0;
	
	@Column(columnDefinition="Decimal(30,2) default '0.00' ", nullable = true, updatable = true)
	private BigDecimal valorDiaria;

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
		builder.append( this.predio +"-"+ this.identificador );
		return builder.toString();
	}
	
	///////////////////////////////////////////////////// GETS E SETS

	public Set<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}
	
	public void setMovimentacoes(Set<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}
	
	public Set<ApartamentoDiaria> getApartamentoDiarias() {
		return apartamentoDiarias;
	}
	
	public void setApartamentoDiarias(Set<ApartamentoDiaria> apartamentoDiarias) {
		this.apartamentoDiarias = apartamentoDiarias;
	}
	
	public Integer getQntVagas() {
		return qntVagas;
	}
	
	public void setAceitaAnimal(boolean aceitaAnimal) {
		this.aceitaAnimal = aceitaAnimal;
	}
	
	public void setQntVagas(Integer qntVagas) {
		this.qntVagas = qntVagas;
	}
	
	public BigDecimal getValorDiaria() {
		return valorDiaria;
	}
	
	public void setValorDiaria(BigDecimal valorDiaria) {
		this.valorDiaria = valorDiaria;
	}
	
	public Set<Reserva> getReservas() {
		return reservas;
	}
	
	public void setReservas(Set<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	public Predio getPredio() {
		return predio;
	}
	
	public void setPredio(Predio predio) {
		this.predio = predio;
	}
	
	public Pessoa getProprietario() {
		return proprietario;
	}
	
	public void setProprietario(Pessoa proprietario) {
		this.proprietario = proprietario;
	}
	
	public Integer getIdApartamento() {
		return idApartamento;
	}

	public void setIdApartamento(Integer idApartamento) {
		this.idApartamento = idApartamento;
	}
	
	public Set<ApartamentoItem> getApartamentoItens() {
		return apartamentoItens;
	}
	
	public void setApartamentoItens(Set<ApartamentoItem> apartamentoItens) {
		this.apartamentoItens = apartamentoItens;
	}
	
	public Integer getLimiteAdulto() {
		return limiteAdulto;
	}

	public void setLimiteAdulto(Integer limiteAdulto) {
		this.limiteAdulto = limiteAdulto;
	}

	public Integer getLimiteCrianca() {
		return limiteCrianca;
	}

	public void setLimiteCrianca(Integer limiteCrianca) {
		this.limiteCrianca = limiteCrianca;
	}

	public Integer getMetrosQuadrados() {
		return metrosQuadrados;
	}

	public void setMetrosQuadrados(Integer metrosQuadrados) {
		this.metrosQuadrados = metrosQuadrados;
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
	
	public String getIdentificador() {
		return identificador;
	}
	
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	public boolean isAceitaAnimal() {
		return aceitaAnimal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idApartamento == null) ? 0 : idApartamento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Apartamento other = (Apartamento) obj;
		if (idApartamento == null) {
			if (other.idApartamento != null)
				return false;
		} else if (!idApartamento.equals(other.idApartamento))
			return false;
		return true;
	}
	
	

	

}
