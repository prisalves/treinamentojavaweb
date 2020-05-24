package sistemafenicia.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import sistemafenicia.service.UsuarioSistemaService;

@Entity
@Table(name = "reserva")
public class Reserva implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idReserva;

	////////// RELACIONAMENTOS
	
	//BIDIRECIONAL
	@ManyToOne
	@JoinColumn(name="cliente_id", referencedColumnName = "id")
	private Pessoa cliente;
	
	//RESERVAS(*)=(1)APARTAMENTO
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="apartamento_id", referencedColumnName = "id")
	private Apartamento apartamento;
	
	/*//BIDIRECIONAL
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true,mappedBy="reserva",fetch=FetchType.LAZY)
	private Set<ReservaMovimentacao> reservaMovimentacoes = new HashSet<ReservaMovimentacao>(0);*/
	
	//RESERVA(1)=(*)RESERVA MOVIMENTACOES
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER)
	@JoinColumn(name="reserva_id", referencedColumnName = "id")
	private Set<ReservaMovimentacao> reservaMovimentacoes = new HashSet<ReservaMovimentacao>(0);
	
	//RESERVA(*)=(*)ESTACIONAMENTO 
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="reservas_estacionamentos",
		joinColumns={@JoinColumn(name="reserva_id",referencedColumnName="id")},
		inverseJoinColumns ={@JoinColumn(name="estacionamento_id",referencedColumnName="id")})
	private Set<Estacionamento> estacionamentos = new HashSet<Estacionamento>(0);
	
	//RESERVA(*)=(1)RESERVA TIPO
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="reservatipo_id", referencedColumnName = "id")
	private ReservaTipo reservaTipo;

	///////////////////////////////////////////////////// COLUNAS

	@Column(name = "qnt_adulto")
	private Integer quantidadeAdulto = 2;
	
	@Column(name = "qnt_crianca")
	private Integer quantidadeCrianca = 0;
	
	@Column(name="comAnimal")
	private boolean comAnimal = false;
	
	@Column(name = "qnt_vagas", nullable = true, updatable = true)
	private Integer qntVagas = 0;
	
	@Column(name = "dt_pretencao_inicio",nullable=false,updatable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataPretencaoInicio;
	
	@Column(name = "dt_pretencao_fim",nullable=false,updatable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataPretencaoFim;
	
	/*@Column(nullable = false, updatable = true, length = 100)
	private String situacao;*/
	
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
	
	////////////////////////////////////////// TOSTRING
	
	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		StringBuilder builder = new StringBuilder();
		builder.append("<b>"+df.format(this.dataPretencaoInicio)+"</b> a <b>"+ df.format(this.dataPretencaoFim)+"</b>" );
		return builder.toString();
	}
	
	///////////////////////////////////////////////////// GETS E SETS

	public ReservaTipo getReservaTipo() {
		return reservaTipo;
	}
	
	public void setReservaTipo(ReservaTipo reservaTipo) {
		this.reservaTipo = reservaTipo;
	}
	
	public Set<Estacionamento> getEstacionamentos() {
		return estacionamentos;
	}
	
	public void setEstacionamentos(Set<Estacionamento> estacionamentos) {
		this.estacionamentos = estacionamentos;
	}
	
	public Set<ReservaMovimentacao> getReservaMovimentacoes() {
		return reservaMovimentacoes;
	}
	
	public void setReservaMovimentacoes(Set<ReservaMovimentacao> reservaMovimentacoes) {
		this.reservaMovimentacoes = reservaMovimentacoes;
	}
	
	public Reserva() {
		super();
	}
	
	/*public String getSituacao() {
		return situacao;
	}
	
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}*/
	
	public Apartamento getApartamento() {
		return apartamento;
	}
	
	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}

	public Pessoa getCliente() {
		return cliente;
	}
	
	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
	}
	
	public Integer getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Integer idReserva) {
		this.idReserva = idReserva;
	}

	public Integer getQuantidadeAdulto() {
		return quantidadeAdulto;
	}

	public void setQuantidadeAdulto(Integer quantidadeAdulto) {
		this.quantidadeAdulto = quantidadeAdulto;
	}

	public Integer getQuantidadeCrianca() {
		return quantidadeCrianca;
	}

	public void setQuantidadeCrianca(Integer quantidadeCrianca) {
		this.quantidadeCrianca = quantidadeCrianca;
	}

	public boolean isComAnimal() {
		return comAnimal;
	}

	public void setComAnimal(boolean comAnimal) {
		this.comAnimal = comAnimal;
	}

	public Integer getQntVagas() {
		return qntVagas;
	}
	
	public void setQntVagas(Integer qntVagas) {
		this.qntVagas = qntVagas;
	}

	public Date getDataPretencaoInicio() {
		return dataPretencaoInicio;
	}

	public void setDataPretencaoInicio(Date dataPretencaoInicio) {
		this.dataPretencaoInicio = dataPretencaoInicio;
	}

	public Date getDataPretencaoFim() {
		return dataPretencaoFim;
	}

	public void setDataPretencaoFim(Date dataPretencaoFim) {
		this.dataPretencaoFim = dataPretencaoFim;
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
		result = prime * result + ((idReserva == null) ? 0 : idReserva.hashCode());
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
		Reserva other = (Reserva) obj;
		if (idReserva == null) {
			if (other.idReserva != null)
				return false;
		} else if (!idReserva.equals(other.idReserva))
			return false;
		return true;
	}
	
	

	
}
