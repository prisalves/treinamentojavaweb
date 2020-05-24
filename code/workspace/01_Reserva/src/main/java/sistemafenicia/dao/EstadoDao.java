package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemafenicia.model.Estado;

@Repository("estadoDao")
public class EstadoDao extends Dao<Estado> {

	static final Logger log = Logger.getLogger(EstadoDao.class);

	public EstadoDao() {
		log.debug("Iniciou - EstadoDao");
		setClazz(Estado.class);
	}

}
