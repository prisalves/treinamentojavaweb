package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemafenicia.model.Predio;

@Repository
public class PredioDao extends Dao<Predio> {

	static final Logger log = Logger.getLogger(PredioDao.class);

	public PredioDao() {
		log.debug("Iniciou - PredioDao");
		setClazz(Predio.class);
	}

}
