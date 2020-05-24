package sistemafenicia.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cidade")
public class Cidade implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idCidade;

	//RELACIONAMENTOS

	//CIDADES(*)=(1)ESTADO
	@ManyToOne
	@JoinColumn(name="estado_id",referencedColumnName="id")
	private Estado estado;

	///////////////////////////////////////////////////// COLUNAS

	@Column(length = 255, nullable = false, updatable = true)
	private String nome;

	//////////////////////////////////////////TOSTRING
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		//builder.append("Permissões [name=");
		builder.append(this.nome+"/"+getEstado());
		//builder.append("]");
		return builder.toString();
	}

	public Cidade(String nome, Estado estado) {
		super();
		this.estado = estado;
		this.nome = nome;
	}

	public Cidade() {}

	public Integer getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(Integer idCidade) {
		this.idCidade = idCidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCidade == null) ? 0 : idCidade.hashCode());
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
		Cidade other = (Cidade) obj;
		if (idCidade == null) {
			if (other.idCidade != null)
				return false;
		} else if (!idCidade.equals(other.idCidade))
			return false;
		return true;
	}



}
