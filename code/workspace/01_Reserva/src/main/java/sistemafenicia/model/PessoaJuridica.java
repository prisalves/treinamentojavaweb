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

@Entity
@Table(name = "pessoa_juridica")
@PrimaryKeyJoinColumn(name = "id_pessoa")
public class PessoaJuridica extends Pessoa implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	//RELACIONAMENTOS
	
	@OneToOne(orphanRemoval=true,fetch=FetchType.LAZY,optional=true,cascade=CascadeType.ALL)
	@JoinColumn(name="contato_id")
	private Pessoa pessoa;
	
	///////////////////////////////////////////////////// COLUNAS
	
	@Column(name = "nome_fantasia", nullable = true, length = 255, updatable = true)
	private String nomeFantasia;
	
	@Column(name = "razao_social", nullable = true, length = 255, updatable = true)
	private String razaoSocial;
	
	@Column(name = "cnpj", nullable = true, length = 20, updatable = true, unique = true)
	private String cnpj;
	
	@Column(name = "insc_estadual", nullable = true, length = 100, updatable = true)
	private String inscEstadual;
	
	@Column(name = "insc_municipal", nullable = true, length = 100, updatable = true)
	private String inscMunicipal;
	
	@Column(name = "area_atuacao", nullable = true, length = 255, updatable = true)
	private String areaAtuacao;
	
	@Column(name = "contato_cargo", nullable = true, length = 255, updatable = true)
	private String contatoCargo;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscEstadual() {
		return inscEstadual;
	}

	public void setInscEstadual(String inscEstadual) {
		this.inscEstadual = inscEstadual;
	}

	public String getInscMunicipal() {
		return inscMunicipal;
	}

	public void setInscMunicipal(String inscMunicipal) {
		this.inscMunicipal = inscMunicipal;
	}

	public String getAreaAtuacao() {
		return areaAtuacao;
	}

	public void setAreaAtuacao(String areaAtuacao) {
		this.areaAtuacao = areaAtuacao;
	}

	public String getContatoCargo() {
		return contatoCargo;
	}

	public void setContatoCargo(String contatoCargo) {
		this.contatoCargo = contatoCargo;
	}
	
	
	


}
