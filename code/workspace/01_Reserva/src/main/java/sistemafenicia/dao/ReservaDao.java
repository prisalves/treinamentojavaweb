package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemafenicia.model.Reserva;

@Repository
public class ReservaDao extends Dao<Reserva> {

	static final Logger log = Logger.getLogger(ReservaDao.class);

	public ReservaDao() {
		log.debug("Iniciou - ReservaDao");
		setClazz(Reserva.class);
	}

}
