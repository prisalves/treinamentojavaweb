package sistemafenicia.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import sistemafenicia.service.UsuarioSistemaService;

@Entity
@Table(name="perfil")
public class Perfil implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idPerfil;

	//RELACIONAMENTOS

	//PERFIS(*)=(*)PERMISSOES 
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="perfis_permissoes",
		joinColumns={@JoinColumn(name="perfil_id",referencedColumnName="id")},
		inverseJoinColumns ={@JoinColumn(name="permissao_id",referencedColumnName="id")})
	private Set<Permissao> permissoes = new HashSet<Permissao>(0);

	//BIDIRECIONAL	
	@ManyToMany(mappedBy="perfis",fetch=FetchType.LAZY)
	private Set<Usuario> usuarios = new HashSet<Usuario>(0);
	
	///////////////////////////////////////////////////// COLUNAS

	@NotEmpty(message="Nome: obrigatório.")
	@NotBlank(message="Nome: não pode ser vazio.")
	@Size(min=2, max=30, message="Nome: minimo 2 maximo 30 caracter.")
	@Column(name = "nome",updatable=false, nullable = false, length = 30, unique = true)
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

	//////////////////////////////

	public Integer getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(Set<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
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

	@Override
	public String getAuthority() {
		return this.nome;
	}
	
	

	//////////////////////////////////////////TOSTRING
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		//builder.append("Perfil [name=");
		builder.append(this.nome);
		//builder.append(", permissões=");
		builder.append("=");
		builder.append(this.permissoes);
		//builder.append("]");
		return builder.toString();
	}

}
