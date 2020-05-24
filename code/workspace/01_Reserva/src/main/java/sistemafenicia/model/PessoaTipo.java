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
@Table(name = "pessoa_tipo")
public class PessoaTipo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idPessoaTipo;

	////////// RELACIONAMENTOS
	
	//BIDIRECIONAL
	@OneToMany(mappedBy="pessoaTipo",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private Set<Pessoa> pessoas = new HashSet<Pessoa>(0);

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
	
	public PessoaTipo() {
		super();
	}
	
	public Set<Pessoa> getPessoas() {
		return pessoas;
	}
	
	public void setPessoas(Set<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public Integer getIdPessoaTipo() {
		return idPessoaTipo;
	}
	
	public void setIdPessoaTipo(Integer idPessoaTipo) {
		this.idPessoaTipo = idPessoaTipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public PessoaTipo(Integer idPessoaTipo) {
		super();
		this.idPessoaTipo = idPessoaTipo;
	}
	
	
	

	
}
