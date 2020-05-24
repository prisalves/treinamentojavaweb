package sistemafenicia.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sistemafenicia.utils.MessagesUtil;

public class Dao<T> {

	static final Logger log = Logger.getLogger(Dao.class);

	@Autowired
	private SessionFactory sessionFactory;

	protected final Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	protected Class<T> clazz;

	public void setClazz(final Class<T> clazzToSet){
		this.clazz = clazzToSet;
	}


	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void save(T entity) throws SQLException, Exception {
		try {
			getSession().persist(entity);
		} catch (HibernateException hi){
			log.debug("ERRO SAVE: "+hi);
			SQLException se = (SQLException) hi.getCause();
			if(se.getErrorCode()==1062)
				MessagesUtil.error("O registro \""+entity+"\" já existe.");
			if(se.getErrorCode()==1048){
				String[] campo = se.getMessage().split("'");
				String mensagem = "O campo "+campo[1]+" é obrigatório.";
				MessagesUtil.warn(mensagem);
				MessagesUtil.messageErro(mensagem);
			}
        } catch (PersistenceException ex){
			log.debug("ERRO SAVE: "+ex);
        }catch (ConstraintViolationException c) {
			log.debug("ERRO SAVE: "+c);
		} catch (DataIntegrityViolationException d) {
			log.debug("ERRO SAVE: "+d);
		} catch (Exception e) {
			log.debug("ERRO SAVE: "+e);
		}
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Object update(T entity) throws SQLException, Exception {
		Object o = null;
		try {
			o = getSession().merge(entity);
			//log.debug("UPDATE: "+entity);
		} catch (HibernateException hi){
			log.debug("ERRO UPDATE: "+hi);
			//System.out.println(hi.getCause());
			//SQLException se = (SQLException) hi.getCause();
			//System.out.println(se.getErrorCode());
			//MessagesUtil.error("O registro \""+perfil+"\" NÂO foi "+title+".");
            throw new HibernateException(hi);
        } catch (PersistenceException ex){
			log.debug("ERRO UPDATE: "+ex);
            //throw new PersistenceException(ex);
        }catch (ConstraintViolationException c) {
			log.debug("ERRO UPDATE: "+c);
		} catch (DataIntegrityViolationException d) {
			log.debug("ERRO UPDATE: "+d);
			//throw new Exception(d);
		} catch (Exception e) {
			log.debug("ERRO UPDATE: "+e);
			//throw new Exception(e);
		}
		return o;
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void delete(T entity) throws Exception {
		try {
			getSession().delete(entity);
			//log.debug("DELETE: "+entity);
		} catch (HibernateException hi){
			log.debug("ERRO DELETE: "+hi);
            //throw new PersistenceException(hi);
        } catch (PersistenceException ex){
			log.debug("ERRO DELETE: "+ex);
            //throw new PersistenceException(ex);
        }catch (ConstraintViolationException c) {
			log.debug("ERRO DELETE: "+c);
		} catch (DataIntegrityViolationException d) {
			log.debug("ERRO DELETE: "+d);
			//throw new Exception(d);
		} catch (Exception e) {
			log.debug("ERRO DELETE: "+e);
			//throw new Exception(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void deleteById(int id) {
        try {
			delete((T) findByField("id",id));
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
    }

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> findAll() throws Exception {
		List<T> lista = null;
		try {
			lista = getSession().createCriteria(clazz)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list();
		} catch (Exception e) {
			log.debug("ERRO FIND ALL: "+e);
			//throw new Exception(e);
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> findAll(String order, String tipo) throws Exception {
		List<T> lista = null;
		try {
			if(tipo!=null && tipo.equals("DESC")){
				lista = getSession().createCriteria(clazz)
						.addOrder(Order.desc(order))
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
						.list();
			}else{
				lista = getSession().createCriteria(clazz)
						.addOrder(Order.asc(order))
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
						.list();
			}
			
		} catch (Exception e) {
			log.debug("ERRO FIND ALL: "+e);
			//throw new Exception(e);
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> findAllByField(String field, Object value) throws Exception {
		List<T> lista = null;
		try {
			lista = getSession().createCriteria(clazz)
					.add(Restrictions.eq(field, value))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list();
		} catch (Exception e) {
			log.error("ERRO FIND ALL BY FIELD: "+ e.getMessage());
			//throw new Exception(e);
		}
		return lista;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Object findById(int id) throws Exception {
		Object o = null;
		try {
			//o = getSession().get(clazz, id);
			o = getSession().createCriteria(clazz)
					.add(Restrictions.eq("id", id))
					.uniqueResult();
		} catch (Exception e) {
			log.error("ERRO FIND ID: "+ e.getMessage());
		}
		return o;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Object findByField(String field, Object value) throws Exception {
		Object o = null;
		try {
			o = getSession().createCriteria(clazz)
					.add(Restrictions.eq(field, value))
					.uniqueResult();
		} catch (Exception e) {
			log.error("ERRO FIND BY FIELD: "+ e.getMessage());
			throw new Exception(e);
		}
		return o;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> findByQuery(String sql) throws Exception {
		List<T> lista = null;
		try {
			lista = getSession().createQuery(sql.toString()).list();
		} catch (Exception e) {
			log.error("ERRO FIND BY QUERY: "+ e.getMessage());
			//throw new Exception(e);
		}
		return lista;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@SuppressWarnings("unchecked")
	public List<T> findCriteria(DetachedCriteria detachedCriteria, int firstResult, int maxResults) throws Exception {
		List<T> lista = null;
		//log.debug("Find range of data using criteria: " + detachedCriteria + ", firstResult:" + firstResult + ", maxResults:" + maxResults);
		try {
			Criteria criteria = detachedCriteria.getExecutableCriteria(getSession()).setFirstResult(firstResult);
			if (maxResults >= 0) {
				criteria = criteria.setMaxResults(maxResults);
			}
			lista = criteria.list();
		} catch (Exception e) {
			log.error("ERRO FIND BY CRITERIA: "+ e.getMessage());
			//throw new Exception(e);
		}
		return lista;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> findCriteria(DetachedCriteria criteria) {
		//log.debug("Find data using criteria: " + criteria);
		try {
			return this.findCriteria(criteria, 0, -1);
		} catch (Exception e) {
			log.error("ERRO FIND BY CRITERIA: "+ e.getMessage());
		}
		return null;
	}

}
