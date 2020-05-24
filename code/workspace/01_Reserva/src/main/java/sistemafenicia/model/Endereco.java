/*package sistemafenicia.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import sistemafenicia.enuns.TipoEndereco;

@Entity
@Table(name = "endereco")
public class Endereco implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idEndereco;

	//RELACIONAMENTOS

	//ENDERECO(1)=(1)CIDADE
	@OneToOne(orphanRemoval=false,fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="cidade_id",referencedColumnName="id")
	private Cidade cidade;

	//BIDIRECIONAL
	@ManyToMany(mappedBy="enderecos",fetch=FetchType.LAZY)
	private Set<Pessoa> pessoas = new HashSet<Pessoa>(0);
	
	//BIDIRECIONAL
	@OneToOne(mappedBy="endereco")
	//@JoinColumn(name="pessoa_id",referencedColumnName="id")
    private Pessoa pessoa;
	
	//private TipoEndereco tipoEndereco;

	///////////////////////////////////////////////////// COLUNAS

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
	private String log_usuario;

	public Integer getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Integer idEndereco) {
		this.idEndereco = idEndereco;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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

	//////////////////////////////////////////TOSTRING
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		//builder.append("Permissões [name=");
		builder.append("TIPO:"+this.tipoEndereco);
		builder.append("LAGRODOURO:"+this.lagrodouro);
		builder.append("CIDADE:"+getCidade());
		//builder.append("]");
		return builder.toString();
	}



}
*/