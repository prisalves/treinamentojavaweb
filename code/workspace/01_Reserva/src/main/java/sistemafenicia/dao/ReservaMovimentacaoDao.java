package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemafenicia.model.ReservaMovimentacao;

@Repository
public class ReservaMovimentacaoDao extends Dao<ReservaMovimentacao> {

	static final Logger log = Logger.getLogger(ReservaMovimentacaoDao.class);

	public ReservaMovimentacaoDao() {
		log.debug("Iniciou - ReservaMovimentacaoDao");
		setClazz(ReservaMovimentacao.class);
	}

}
