package sistemafenicia.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "reserva_tipo")
public class ReservaTipo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idReservaTipo;

	////////// RELACIONAMENTOS
	
	//BIDIRECIONAL
	@OneToMany(mappedBy="reservaTipo",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private Set<Reserva> reservas = new HashSet<Reserva>(0);

	///////////////////////////////////////////////////// COLUNAS

	@Column(nullable = false, updatable = false, length = 255)
	private String nome;
	
	////////////////////////////////////////// TOSTRING
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.nome);
		return builder.toString();
	}
	
	///////////////////////////////////////////////////// GETS E SETS
	
	public ReservaTipo() {
		super();
	}
	
	public Integer getIdReservaTipo() {
		return idReservaTipo;
	}
	
	public void setIdReservaTipo(Integer idReservaTipo) {
		this.idReservaTipo = idReservaTipo;
	}
	
	public Set<Reserva> getReservas() {
		return reservas;
	}
	
	public void setReservas(Set<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
