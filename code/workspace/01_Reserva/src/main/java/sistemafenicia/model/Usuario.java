package sistemafenicia.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import sistemafenicia.service.UsuarioSistemaService;

@Entity
@Table(name="usuario")
@ManagedBean
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer idUsuario;
	
	//RELACIONAMENTOS
	
	//BIDIRECIONAL
	@OneToOne(mappedBy="usuario",fetch=FetchType.LAZY,optional=true,orphanRemoval=false)
    private Pessoa pessoa;
	
	//USUARIOS(*)=(*)PERFIS
	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="usuarios_perfis",
    	joinColumns={@JoinColumn(name="usuario_id",referencedColumnName="id")},
    	inverseJoinColumns ={@JoinColumn(name="perfil_id",referencedColumnName="id")})
    private Set<Perfil> perfis = new HashSet<Perfil>(0);
	
	//BIDIRECIONAL
	@OneToMany(mappedBy="operador",fetch=FetchType.LAZY,orphanRemoval=false)
	private Set<Caixa> caixas = new HashSet<Caixa>(0);
	
	///////////////////////////////////////////////////// COLUNAS

	//@NotBlank(message="Login: obrigatório.")
	//@Size(min=3, max=20, message="login de 3 a 20 caracter.")
	@Column(name="login", updatable=true, nullable=false, length=30, unique=true)
	private String login;

	//@NotBlank(message="Senha: obrigatório.")
	//@Size(min=3, max=15, message="Senha de 3 a 15 caracter.")
	@Column(name="senha", updatable=true, nullable=false, length=255, unique=false)
	private String senha;
	
	@Column(length = 255, nullable = true, updatable = true)
	private String novaSenha = null;
	
	@Column(length=255, updatable=true, nullable=true, unique=true)
	private String email;

	/* Spring Security fields*/
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean status = true;
    
    //STATUS
    
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
  	
  	public Usuario() {
		super();
	}
  	
  	public Set<Caixa> getCaixas() {
		return caixas;
	}
  	
  	public void setCaixas(Set<Caixa> caixas) {
		this.caixas = caixas;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		//PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//return passwordEncoder.encode(rawPassword)(this.senha);
		return senha;
	}

	public void setSenha(String senha) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);
		this.senha = passwordEncoder.encode(senha);
		//this.senha = senha;
	}

	public Set<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<Perfil> perfis) {
		this.perfis = perfis;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<Perfil>(this.perfis);
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.status;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	
	
	
	//////////////////////////////////////////TOSTRING
	@Override
	public String toString() {
		return this.login;
	}

	
}
