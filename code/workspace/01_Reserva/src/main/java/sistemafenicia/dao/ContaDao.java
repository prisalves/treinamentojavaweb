package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemafenicia.model.Conta;

@Repository
public class ContaDao extends Dao<Conta> {

	static final Logger log = Logger.getLogger(ContaDao.class);

	public ContaDao() {
		log.debug("Iniciou - ContaDao");
		setClazz(Conta.class);
	}

}
