package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemafenicia.model.Apartamento;

@Repository
public class ApartamentoDao extends Dao<Apartamento> {

	static final Logger log = Logger.getLogger(ApartamentoDao.class);

	public ApartamentoDao() {
		log.debug("Iniciou - ApartamentoDao");
		setClazz(Apartamento.class);
	}

}
