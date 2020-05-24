package sistemafenicia.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sistemafenicia.dao.CidadeDao;
import sistemafenicia.dao.EstadoDao;
import sistemafenicia.model.Cidade;
import sistemafenicia.model.Estado;

//@RequestScoped
//@ManagedBean
@Service
public class AddCidades {

	private static final Logger log = Logger.getLogger(AddCidades.class);

	@Autowired
	@Qualifier("estadoDao")
	EstadoDao estadoDao;

	@Autowired
	@Qualifier("cidadeDao")
	CidadeDao cidadeDao;

	private Estado estado = new Estado();

	public void Add() {
		try {
			this.cargaCidadeEstado0();
			this.cargaCidadeEstado1();
			/*
			 * this.cargaCidadeEstado2(); this.cargaCidadeEstado3();
			 * this.cargaCidadeEstado4(); this.cargaCidadeEstado5();
			 * this.cargaCidadeEstado6(); this.cargaCidadeEstado7();
			 * this.cargaCidadeEstado8(); this.cargaCidadeEstado9();
			 * this.cargaCidadeEstado10(); this.cargaCidadeEstado11();
			 * this.cargaCidadeEstado12(); this.cargaCidadeEstado13();
			 * this.cargaCidadeEstado14(); this.cargaCidadeEstado15();
			 * this.cargaCidadeEstado16(); this.cargaCidadeEstado17();
			 * this.cargaCidadeEstado18(); this.cargaCidadeEstado19();
			 * this.cargaCidadeEstado20(); this.cargaCidadeEstado21();
			 * this.cargaCidadeEstado22(); this.cargaCidadeEstado23();
			 * this.cargaCidadeEstado24(); this.cargaCidadeEstado25();
			 * this.cargaCidadeEstado26(); this.cargaCidadeEstado27();
			 */
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

	}
	
	@Transactional
	public void cargaCidadeEstado0() {
		try { /* divisao0 */
			estado = new Estado("Selecione", "XX");
			Estado novoEstado = (Estado) estadoDao.findByField("nome",estado.getNome());
			if(novoEstado==null){
				estadoDao.save(estado);
				cidadeDao.save(new Cidade("Selecione", estado));
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	@Transactional
	public void cargaCidadeEstado1() {
		try { /* divisao1 */
			estado = new Estado("Acre", "AC");
			Estado novoEstado = (Estado) estadoDao.findByField("nome",estado.getNome());
			if(novoEstado==null){
				estadoDao.save(estado);
				cidadeDao.save(new Cidade("Acrelandia", estado));
				cidadeDao.save(new Cidade("Assis Brasil", estado));
				cidadeDao.save(new Cidade("Brasileia", estado));
				cidadeDao.save(new Cidade("Bujari", estado));
				cidadeDao.save(new Cidade("Capixaba", estado));
				cidadeDao.save(new Cidade("Cruzeiro do Sul", estado));
				cidadeDao.save(new Cidade("Epitaciolandia", estado));
				cidadeDao.save(new Cidade("Feijo", estado));
				cidadeDao.save(new Cidade("Jordao", estado));
				cidadeDao.save(new Cidade("Mancio Lima", estado));
				cidadeDao.save(new Cidade("Manoel Urbano", estado));
				cidadeDao.save(new Cidade("Marechal Thaumaturgo", estado));
				cidadeDao.save(new Cidade("Placido de Castro", estado));
				cidadeDao.save(new Cidade("Porto Acre", estado));
				cidadeDao.save(new Cidade("Porto Walter", estado));
				cidadeDao.save(new Cidade("Rio Branco", estado));
				cidadeDao.save(new Cidade("Rodrigues Alves", estado));
				cidadeDao.save(new Cidade("Santa Rosa", estado));
				cidadeDao.save(new Cidade("Sena Madureira", estado));
				cidadeDao.save(new Cidade("Senador Guiomard", estado));
				cidadeDao.save(new Cidade("Tarauaca", estado));
				cidadeDao.save(new Cidade("Xapuri", estado));
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

}
