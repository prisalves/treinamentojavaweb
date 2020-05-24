package sistemafenicia.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "pessoa_fisica")
@PrimaryKeyJoinColumn(name = "id_pessoa")
public class PessoaFisica extends Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="Nome: obrigatório.")
	@NotBlank(message="Nome: não pode ser vazio.")
	@Column(length = 255, nullable = false, updatable = true, unique = false)
	private String nome;

	@Column(nullable = true, length = 1, updatable = true)
	private String sexo;
	
	@Column(name="dt_nascimento", nullable=true, updatable = true)
    @Temporal(value=TemporalType.DATE)
    private Date dataNascimento;
	
	@Column(name="cod_externo", length=30, nullable = true, updatable = true)
	private String codExterno;
	
	@Column(nullable = true, length = 15, updatable = true, unique = true)
	private String cpf;
	
	@Column(nullable = true, length = 15, updatable = true)
	private String rg;
	
	@Column(nullable = true, length = 20, updatable = true)
	private String orgaoExpedidor;
	
	@Column(nullable = true, length = 100, updatable = true)
	private String carroDescricao;
	
	@Column(nullable = true, length = 10, updatable = true)
	private String carroPlaca;
	
	
	//////////////////////////////////////////TOSTRING
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.nome);
		return builder.toString();
	}
	
	///////////////////////////////////////////////////// GETS E SETS

	public String getOrgaoExpedidor() {
		return orgaoExpedidor;
	}
	
	public void setOrgaoExpedidor(String orgaoExpedidor) {
		this.orgaoExpedidor = orgaoExpedidor;
	}
	
	public String getCarroDescricao() {
		return carroDescricao;
	}
	
	public void setCarroDescricao(String carroDescricao) {
		this.carroDescricao = carroDescricao;
	}
	
	public String getCarroPlaca() {
		return carroPlaca;
	}
	
	public void setCarroPlaca(String carroPlaca) {
		this.carroPlaca = carroPlaca;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCodExterno() {
		return codExterno;
	}

	public void setCodExterno(String codExterno) {
		this.codExterno = codExterno;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public PessoaFisica() {}
	
	public PessoaFisica(String nome, String cpf) {
		super();
		this.nome = nome;
		this.cpf = cpf;
	}
	
	

}
