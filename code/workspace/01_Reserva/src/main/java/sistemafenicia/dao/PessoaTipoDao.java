package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemafenicia.model.PessoaTipo;

@Repository
public class PessoaTipoDao extends Dao<PessoaTipo> {

	static final Logger log = Logger.getLogger(PessoaTipoDao.class);

	public PessoaTipoDao() {
		log.debug("Iniciou - PessoaTipoDao");
		setClazz(PessoaTipo.class);
	}

}
