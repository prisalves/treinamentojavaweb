package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemafenicia.model.Cidade;

@Repository("cidadeDao")
public class CidadeDao extends Dao<Cidade> {

	static final Logger log = Logger.getLogger(CidadeDao.class);

	public CidadeDao() {
		log.debug("Iniciou - CidadeDao");
		setClazz(Cidade.class);
	}

}
