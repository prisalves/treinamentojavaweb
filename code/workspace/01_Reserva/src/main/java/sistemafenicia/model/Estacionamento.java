package sistemafenicia.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "estacionamento")
public class Estacionamento implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idEstacionamento;

	////////// RELACIONAMENTOS
	
	//BIDIRECIONAL
	@ManyToMany(mappedBy="estacionamentos",fetch=FetchType.LAZY)
	private Set<Reserva> reservas = new HashSet<Reserva>(0);

	///////////////////////////////////////////////////// COLUNAS

	@Column(name = "identificador", nullable = true, updatable = false, length = 100)
	private String identificador;
	
	////////////////////////////////////////// TOSTRING
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.identificador);
		return builder.toString();
	}
	
	///////////////////////////////////////////////////// GETS E SETS

	public Integer getIdEstacionamento() {
		return idEstacionamento;
	}
	
	public void setIdEstacionamento(Integer idEstacionamento) {
		this.idEstacionamento = idEstacionamento;
	}
	
	public Set<Reserva> getReservas() {
		return reservas;
	}
	
	public void setReservas(Set<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	public String getIdentificador() {
		return identificador;
	}
	
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	public Estacionamento() {
		super();
	}

	public Estacionamento(String identificador) {
		super();
		this.identificador = identificador;
	}
	
	
}
