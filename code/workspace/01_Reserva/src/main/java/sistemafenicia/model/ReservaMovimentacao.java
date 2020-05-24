package sistemafenicia.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reserva_movimentacao")
public class ReservaMovimentacao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer idReservaMovimentacao;

	////////// RELACIONAMENTOS
	
	//RESERVA MOVIMENTACOES(*)=(1)RESERVA
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="caixa_id", referencedColumnName = "id")
	private Caixa caixa;
	
	/*//RESERVA MOVIMENTACAO(1)=(1)CAIXA
	@OneToOne(orphanRemoval=false,optional=false)
	@JoinColumn(name="caixa_id")
	private Caixa caixa;*/

	///////////////////////////////////////////////////// COLUNAS

	@Column(name = "movimentacao", nullable = true, updatable = true, length = 100)
	private String movimentacao;
	
	@Column(name = "tipoPagamento", nullable = true, updatable = true, length = 100)
	private String tipoPagamento;
	
	@Column(columnDefinition="Decimal(30,2) default '0.00' ", nullable = true, updatable = true)
	private BigDecimal valor;
	
	////////////////////////////////////////// TOSTRING
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.movimentacao);
		return builder.toString();
	}
	
	///////////////////////////////////////////////////// GETS E SETS

	/*public Reserva getReserva() {
		return reserva;
	}
	
	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}*/
	
	public Caixa getCaixa() {
		return caixa;
	}
	
	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}
	
	public Integer getIdReservaMovimentacao() {
		return idReservaMovimentacao;
	}

	public void setIdReservaMovimentacao(Integer idReservaMovimentacao) {
		this.idReservaMovimentacao = idReservaMovimentacao;
	}

	public String getMovimentacao() {
		return movimentacao;
	}

	public void setMovimentacao(String movimentacao) {
		this.movimentacao = movimentacao;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	
	
}
