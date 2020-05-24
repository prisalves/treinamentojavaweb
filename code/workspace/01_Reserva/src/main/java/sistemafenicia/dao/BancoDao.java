package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemafenicia.model.Banco;

@Repository
public class BancoDao extends Dao<Banco> {

	static final Logger log = Logger.getLogger(BancoDao.class);

	public BancoDao() {
		log.debug("Iniciou - BancoDao");
		setClazz(Banco.class);
	}

}
