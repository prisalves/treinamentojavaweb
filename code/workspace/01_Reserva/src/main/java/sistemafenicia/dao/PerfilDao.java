package sistemafenicia.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import sistemafenicia.model.Perfil;

@Repository("perfilDao")
public class PerfilDao extends Dao<Perfil> {

	static final Logger log = Logger.getLogger(PerfilDao.class);

	public PerfilDao() {
		log.debug("Iniciou - PerfilDao");
		setClazz(Perfil.class);
	}

}
