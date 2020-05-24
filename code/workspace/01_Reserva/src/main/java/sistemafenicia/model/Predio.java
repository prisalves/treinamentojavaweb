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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import sistemafenicia.service.UsuarioSistemaService;

@Entity
@Table(name = "predio")
public class Predio implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idPredio;

	////////// RELACIONAMENTOS
	
	//BIDIRECIONAL
	@OneToMany(mappedBy="predio",fetch=FetchType.LAZY)
	private Set<Apartamento> apartamentos = new HashSet<Apartamento>(0);

	///////////////////////////////////////////////////// COLUNAS

	@NotEmpty(message="Nome: obrigatório.")
	@NotBlank(message="Nome: não pode ser vazio.")
	@Column(nullable = false, updatable = false, length = 255)
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
	
	///////////////////////////////////////////////////// GETS E SETS

	public Integer getIdPredio() {
		return idPredio;
	}

	public void setIdPredio(Integer idPredio) {
		this.idPredio = idPredio;
	}

	public Set<Apartamento> getApartamentos() {
		return apartamentos;
	}

	public void setApartamentos(Set<Apartamento> apartamentos) {
		this.apartamentos = apartamentos;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPredio == null) ? 0 : idPredio.hashCode());
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
		Predio other = (Predio) obj;
		if (idPredio == null) {
			if (other.idPredio != null)
				return false;
		} else if (!idPredio.equals(other.idPredio))
			return false;
		return true;
	}
	
	

}
