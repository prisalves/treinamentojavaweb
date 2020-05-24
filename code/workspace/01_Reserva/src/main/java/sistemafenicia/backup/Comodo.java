/*package sistemafenicia.backup;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "comodo")
public class Comodo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idComodo;

	////////// RELACIONAMENTOS
	
	//COMODO(1)=(1)COMODOTIPO
	@OneToOne(orphanRemoval=false,fetch=FetchType.EAGER,optional=false,cascade=CascadeType.ALL)
	@JoinColumn(name="comodotipo_id")
	private ComodoTipo comodoTipo;
	
	//COMODO(*)=(1)COMODOITEM
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="comodoitem_id")
	private ComodoItem comodoItem;


	///////////////////////////////////////////////////// COLUNAS

	@Column(nullable = false, updatable = true, length = 100)
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
	private String log_usuario;
	
	////////////////////////////////////////// TOSTRING
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NOME:"+nome);
		return builder.toString();
	}
	
	///////////////////////////////////////////////////// GETS E SETS

	public Integer getIdComodo() {
		return idComodo;
	}

	public void setIdComodo(Integer idComodo) {
		this.idComodo = idComodo;
	}

	public ComodoTipo getComodoTipo() {
		return comodoTipo;
	}

	public void setComodoTipo(ComodoTipo comodoTipo) {
		this.comodoTipo = comodoTipo;
	}

	public ComodoItem getComodoItem() {
		return comodoItem;
	}

	public void setComodoItem(ComodoItem comodoItem) {
		this.comodoItem = comodoItem;
	}

	public String getNome() {
		return nome;
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
	
	
	




}
*/