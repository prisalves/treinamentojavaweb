package sistemafenicia.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "configuracao")
public class Configuracao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idConfiguracao;

	//COLUNAS

	@Column(nullable = false, updatable = false, unique = true, length = 255)
	//@Type(type="encryptedString")
	private String nome;

	@Column(columnDefinition="TEXT", nullable = false, updatable = true)
	//@Type(type="encryptedString")
	private String value;

	@Column(columnDefinition="TEXT", nullable = true, updatable = true)
	private String obs;

	//STATUS

	/*@Column(name="status", length=1)
		private boolean status = true;

		@Column(columnDefinition="TEXT", nullable = true, updatable = true)
		private String obs;*/

	//LOG

	/*@Column(name = "log_dt_cadastro", nullable = true, updatable = false)
		@Temporal(TemporalType.TIMESTAMP)
		private Date logDataCadastro = new Date();

		@Column(name = "log_dt_modificado", updatable = true)
		@Temporal(TemporalType.TIMESTAMP)
		private Date logDataModificado = new Date();

		@Column(name = "log_usuario", nullable = true, updatable = true, length = 100)
		private String log_usuario;*/

	//////////////////////////////

	public Integer getIdConfiguracao() {
		return idConfiguracao;
	}

	public void setIdConfiguracao(Integer idConfiguracao) {
		this.idConfiguracao = idConfiguracao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}





}
