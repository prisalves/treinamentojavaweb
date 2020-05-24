package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemafenicia.model.Permissao;

@Repository("permissaoDao")
public class PermissaoDao extends Dao<Permissao> {

	static final Logger log = Logger.getLogger(PermissaoDao.class);

	public PermissaoDao() {
		log.debug("Iniciou - PermissaoDao");
		setClazz(Permissao.class);
	}

}
