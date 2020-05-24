package sistemafenicia.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import sistemafenicia.service.UsuarioSistemaService;

@Entity
@Table(name="permissao")
public class Permissao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idPermissao;
	
	//RELACIONAMENTOS
	
	//BIDIRECIONAL
	@ManyToMany(mappedBy="permissoes",fetch=FetchType.LAZY)
	private Set<Perfil> perfis = new HashSet<Perfil>(0);
	
	///////////////////////////////////////////////////// COLUNAS

	//@Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "This is not a valid email")
	//@Pattern(regexp="([^\\s$])",message="Nome: remover espa�os.")
	@NotEmpty(message="Nome: obrigatório.")
	@NotBlank(message="Nome: não pode ser vazio.")
	@Size(min=2, max=30, message="Nome: minimo 2 maximo 30 caracter.")
	@Column(name = "nome", updatable= false, nullable = false, length = 200, unique = true)
	private String nome;

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
		builder.append(nome);
		return builder.toString();
	}
	
	//////////////////////////////

	public String getNome() {
		return nome;
	}

	public Integer getIdPermissao() {
		return idPermissao;
	}

	public void setIdPermissao(Integer idPermissao) {
		this.idPermissao = idPermissao;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
	
	public Set<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<Perfil> perfis) {
		this.perfis = perfis;
	}


}
