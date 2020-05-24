package sistemafenicia.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import sistemafenicia.service.UsuarioSistemaService;

@Entity
@Table(name = "pessoa")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idPessoa;

	//RELACIONAMENTOS

	//PESSOA(1)=(1)USUARIO
	@OneToOne(orphanRemoval=true,fetch=FetchType.LAZY,optional=true,cascade=CascadeType.ALL)
	@JoinColumn(name="usuario_id", referencedColumnName = "id")
	private Usuario usuario;

	/*//BIDIRECIONAL
	@OneToMany(mappedBy="pessoa",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private Set<Telefone> telefones = new HashSet<Telefone>(0);*/
	
	/*//PESSOA(*)=(*)ENDERECOS
	@ManyToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinTable(name="pessoas_enderecos",
		joinColumns={@JoinColumn(name="pessoa_id",referencedColumnName="id")},
		inverseJoinColumns ={@JoinColumn(name="endereco_id",referencedColumnName="id")})
	private Set<Endereco> enderecos = new HashSet<Endereco>(0);*/
	
	/*//PESSOA(1)=(1)ENDERECO
	@OneToOne(fetch=FetchType.EAGER,optional=true,orphanRemoval=true,cascade=CascadeType.ALL)
	@JoinColumn(name="endereco_id",referencedColumnName="id")
	private Endereco endereco;*/
	
	//BIDIRECIONAL
	@OneToMany(mappedBy="proprietario",fetch=FetchType.LAZY,orphanRemoval=false)
	private Set<Apartamento> apartamentos = new HashSet<Apartamento>(0);
	
	//PESSOA(*)=(1)PESSOA TIPO
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="pessoatipo_id", referencedColumnName = "id")
	private PessoaTipo pessoaTipo;
	
	//PESSOA(*)=(1)CONTA
	@ManyToOne(fetch=FetchType.LAZY,optional=true,cascade=CascadeType.ALL)
	@JoinColumn(name="conta_id", referencedColumnName = "id")
	private Conta conta;
	
	/*//PESSOA(1)=(1)CONTA
	@OneToOne(mappedBy="titular",fetch=FetchType.LAZY,optional=true,orphanRemoval=true)
	private Conta conta;*/
	
	//PESSOA(1)=(*)RESERVAS
	@OneToMany(mappedBy="cliente",fetch=FetchType.LAZY,orphanRemoval=false)
	private Set<Reserva> reservas = new HashSet<Reserva>(0);
	
	//PESSOA(1)=(1)CIDADE
	@OneToOne(orphanRemoval=false,fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="cidade_id",referencedColumnName="id")
	private Cidade cidade;
	

	///////////////////////////////////////////////////// COLUNAS
	
	@Column(columnDefinition="TEXT", nullable = true, updatable = true)
	@Basic (fetch = FetchType.EAGER)
	private String servicos;
	
	@Column(nullable = true, updatable = true, length = 30, unique = false)
	private String telefone1;
	
	@Column(nullable = true, updatable = true, length = 30, unique = false)
	private String telefone2;
	
	@Column(nullable = true, updatable = true, length = 30, unique = false)
	private String telefone3;
	
	@Column(nullable = true, updatable = true, length = 50, unique = false)
	private String email;
	
	@Column(name="lagrodouro", length=200, nullable = true, updatable = true, unique = false)
	private String lagrodouro;

	@Column(name="numero", length=20, nullable = true, updatable = true, unique = false)
	private String numero;

	@Column(name="complemento", length=30, nullable = true, updatable = true, unique = false)
	private String complemento;

	@Column(name="bairro", length=30, nullable = true, updatable = true, unique = false)
	private String bairro;
	
	@Column(name="tipoEndereco")
	private String tipoEndereco;
	
	@Column(length = 10, nullable = true, updatable = true)
	private String cep; 

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
		builder.append(this.pessoaTipo);
		return builder.toString();
	}
	
	///////////////////////////////////////////////////// GETS E SETS
	
	public Pessoa() {
		super();
	}
	
	public String getServicos() {
		return servicos;
	}
	
	public void setServicos(String servicos) {
		this.servicos = servicos;
	}
	
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public String getLagrodouro() {
		return lagrodouro;
	}

	public void setLagrodouro(String lagrodouro) {
		this.lagrodouro = lagrodouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getTipoEndereco() {
		return tipoEndereco;
	}
	
	public void setTipoEndereco(String tipoEndereco) {
		this.tipoEndereco = tipoEndereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefone1() {
		return telefone1;
	}
	
	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}
	
	public String getTelefone2() {
		return telefone2;
	}
	
	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}
	
	public String getTelefone3() {
		return telefone3;
	}
	
	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}
	
	public Set<Reserva> getReservas() {
		return reservas;
	}
	
	public void setReservas(Set<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	public PessoaTipo getPessoaTipo() {
		return pessoaTipo;
	}

	public void setPessoaTipo(PessoaTipo pessoaTipo) {
		this.pessoaTipo = pessoaTipo;
	}
	
	public Conta getConta() {
		return conta;
	}
	
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	public Set<Apartamento> getApartamentos() {
		return apartamentos;
	}
	
	public void setApartamentos(Set<Apartamento> apartamentos) {
		this.apartamentos = apartamentos;
	}

	public Integer getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPessoa == null) ? 0 : idPessoa.hashCode());
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
		Pessoa other = (Pessoa) obj;
		if (idPessoa == null) {
			if (other.idPessoa != null)
				return false;
		} else if (!idPessoa.equals(other.idPessoa))
			return false;
		return true;
	}
	

	

}
