package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemafenicia.model.Movimentacao;

@Repository
public class MovimentacaoDao extends Dao<Movimentacao> {

	static final Logger log = Logger.getLogger(MovimentacaoDao.class);

	public MovimentacaoDao() {
		log.debug("Iniciou - MovimentacaoDao");
		setClazz(Movimentacao.class);
	}

}
