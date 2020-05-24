package sistemafenicia.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import sistemafenicia.service.UsuarioSistemaService;

@Entity
@Table(name = "movimentacao")
public class Movimentacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idMovimentacao;

	////////// RELACIONAMENTOS

	//MOVIMENTACAO(*)=(1)CAIXA
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="caixa_id")
	private Caixa caixa;
	
	/*//BIDIRECIONAL
	@OneToMany(mappedBy="movimentacao",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private Set<Pagamento> pagamentos = new HashSet<Pagamento>(0);*/
	
	//MOVIMENTACAO(*)=(*)APARTAMENTOS 
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="movimentacoes_apartamentos",
		joinColumns={@JoinColumn(name="movimentacao_id",referencedColumnName="id")},
		inverseJoinColumns ={@JoinColumn(name="apartamento_id",referencedColumnName="id")})
	private Set<Apartamento> apartamentos = new HashSet<Apartamento>(0);
	
	///////////////////////////////////////////////////// COLUNAS

	@Column(name="valor",columnDefinition="Decimal(30,2) default '0.00' ")
	private BigDecimal valor;
	
	@Column(columnDefinition="TEXT",nullable = false, updatable = true)
	@Basic (fetch = FetchType.EAGER)
	private String descricao;

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
	
	///////////////////////////////////////////////////// GETS E SETS

	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Set<Apartamento> getApartamentos() {
		return apartamentos;
	}
	
	public void setApartamentos(Set<Apartamento> apartamentos) {
		this.apartamentos = apartamentos;
	}
	
	public Integer getIdMovimentacao() {
		return idMovimentacao;
	}

	public void setIdMovimentacao(Integer idMovimentacao) {
		this.idMovimentacao = idMovimentacao;
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
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
	
	/*public Set<Pagamento> getPagamentos() {
		return pagamentos;
	}
	
	public void setPagamentos(Set<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}*/


	//////////////////////////////////////////TOSTRING

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		//builder.append(this.getCaixa()+"/");
		builder.append(this.valor);
		return builder.toString();
	}


}
