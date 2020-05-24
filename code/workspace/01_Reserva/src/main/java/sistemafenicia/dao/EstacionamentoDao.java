package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemafenicia.model.Estacionamento;

@Repository
public class EstacionamentoDao extends Dao<Estacionamento> {

	static final Logger log = Logger.getLogger(EstacionamentoDao.class);

	public EstacionamentoDao() {
		log.debug("Iniciou - EstacionamentoDao");
		setClazz(Estacionamento.class);
	}

}
