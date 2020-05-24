package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemafenicia.model.Caixa;

@Repository
public class CaixaDao extends Dao<Caixa> {

	static final Logger log = Logger.getLogger(CaixaDao.class);

	public CaixaDao() {
		log.debug("Iniciou - CaixaDao");
		setClazz(Caixa.class);
	}

}
