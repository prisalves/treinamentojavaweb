/*package sistemafenicia.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "telefone")
public class Telefone implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idTelefone;

	//RELACIONAMENTOS

	//TELEFONE(*)=(1)PESSOA
	@ManyToOne(optional=true,fetch=FetchType.LAZY, targetEntity=Pessoa.class)
	@JoinTable(name="telefones_pessoa",
		joinColumns={@JoinColumn(name="telefone_id",referencedColumnName="id")},
		inverseJoinColumns ={@JoinColumn(name="pessoa_id",referencedColumnName="id")})
	private Pessoa pessoa;

	///////////////////////////////////////////////////// COLUNAS

	@Column(nullable = true, updatable = true, length = 30, unique = false)
	private String telefone;

	
	residencial
	comercial
	celular
	outro
	 
	@Column(nullable = true, updatable = true, length = 30, unique = false)
	private String tipo;

	public Integer getIdTelefone() {
		return idTelefone;
	}

	public void setIdTelefone(Integer idTelefone) {
		this.idTelefone = idTelefone;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	//////////////////////////////////////////TOSTRING
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		//builder.append("Permissões [name=");
		builder.append(telefone);
		//builder.append("]");
		return builder.toString();
	}


}
*/