package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemafenicia.model.ReservaTipo;

@Repository
public class ReservaTipoDao extends Dao<ReservaTipo> {

	static final Logger log = Logger.getLogger(ReservaTipoDao.class);

	public ReservaTipoDao() {
		log.debug("Iniciou - ReservaTipoDao");
		setClazz(ReservaTipo.class);
	}

}
