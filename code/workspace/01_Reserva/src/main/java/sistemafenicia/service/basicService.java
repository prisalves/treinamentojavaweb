package sistemafenicia.service;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;

import sistemafenicia.dao.Dao;
import sistemafenicia.utils.MessagesUtil;

public class basicService<T extends Object> {

	static final Logger log = Logger.getLogger(basicService.class);

	private Dao<T> dao;

	protected Class<T> clazzService;

	public void setClazz(Class<T> clazzToSet){
		this.clazzService = clazzToSet;
		dao.setClazz(clazzService);
	}

	public void setDao(Dao<T> daoSet){
		this.dao = daoSet;
	}
	
	@PreAuthorize("hasPermission(#user, 'SAVE')")
	public void save(T entity) throws SQLException, Exception {
		try {
			dao.save(entity);
		} catch (PersistenceException ex){
			log.debug("ERRO SAVE: "+ex);
        }catch (ConstraintViolationException c) {
			log.debug("ERRO SAVE: "+c);
		} catch (DataIntegrityViolationException d) {
			log.debug("ERRO SAVE: "+d);
			MessagesUtil.error( "O registro \""+entity+"\" não pode ser salvo.");
		} catch (Exception e) {
			log.debug("ERRO SAVE: "+e);
			throw new Exception(e);
		}
	}

	@SuppressWarnings("unchecked")
	@PreAuthorize("hasPermission(#user, 'UPDATE')")
	public T update(T entity) throws SQLException, Exception {
		Object t = null;
		try {
			t = dao.update(entity);
		} catch (PersistenceException ex){
			log.debug("ERRO UPDATE: "+ex);
            //throw new PersistenceException(ex);
        }catch (ConstraintViolationException c) {
			log.debug("ERRO UPDATE: "+c);
			//throw new PersistenceException(ex);
		} catch (DataIntegrityViolationException d) {
			log.debug("ERRO UPDATE: "+d);
			//MessagesUtil.growl("INFO","teste");
			//MessagesUtil.growl("ERROR","teste");
			MessagesUtil.error( "O registro \""+entity+"\" não pode ser salvo.");
			//MessagesUtil.message("O registro \""+entity+"\" n�o pode ser excluido.",1);
			//MessagesUtil.growl("WARN","teste");
			//throw new Exception(d);
		} catch (Exception e) {
			log.debug("ERRO UPDATE: "+e);
			//MessagesUtil.fatal("O registro \""+entity+"\" já existe no sistema.");
			MessagesUtil.warn("O registro \""+entity+"\" já existe no sistema.");
			MessagesUtil.message("O registro \""+entity+"\" já existe no sistema.","error");
			throw new Exception(e);
		}
		return (T) t;
	}

	@PreAuthorize("hasPermission(#user, 'DELETE')")
	public void delete(T entity)  {
		try {
			//entity = dao.findById(entity);
			dao.delete(entity);
			MessagesUtil.del("O registro \""+entity+"\" foi excluido.");
        } catch (PersistenceException ex){
			log.debug("ERRO DELETE: "+ex);
            //throw new PersistenceException(ex);
        }catch (ConstraintViolationException c) {
			log.debug("ERRO DELETE: "+c);
			//throw new PersistenceException(ex);
		} catch (DataIntegrityViolationException d) {
			log.debug("ERRO DELETE: "+d);
			MessagesUtil.error("O registro \""+entity+"\" não pode ser excluido, porque ele possui dependências.");
			//throw new Exception(d);
		} catch (Exception e) {
			log.debug("ERRO DELETE: "+e);
			//throw new Exception(e);
		}
	}

	@PreAuthorize("hasPermission(#user, 'LIST')")
	public List<?> findAll() {
		List<?> lista = null;
		try {
			lista = dao.findAll();
		} catch (Exception e) {
			log.debug("SERVICE ERRO FINDALL: "+e);
		}
		return lista;
	}
	
	@PreAuthorize("hasPermission(#user, 'LIST')")
	public List<?> findAll(String order, String tipo) {
		List<?> lista = null;
		try {
			lista = dao.findAll(order, tipo);
		} catch (Exception e) {
			log.debug("SERVICE ERRO FINDALL: "+e);
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	public T getObject(String key, String value) {
		try {
			return (T) dao.findByField(key, value);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return null;
	}

	public T newObject() throws Exception {
		return clazzService.newInstance();
	}

	@SuppressWarnings("unchecked")
	public T getObject(Integer id) {
		try {
			return (T) dao.findById(id);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return null;
	}


}
