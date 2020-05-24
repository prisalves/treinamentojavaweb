package br.com.emiolo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.emiolo.util.HibernateUtil;

public class Dao<PK, T> {

	protected final Session getSession() {
		if(this.session == null || this.session.isOpen()==false){
			this.session = HibernateUtil.getSessionFactory().openSession();
		}else{
			this.session = HibernateUtil.getSessionFactory().getCurrentSession();
		}
		return this.session;
	}

	protected Class<T> clazz;
	protected Session session;
	private Transaction transaction;

	public void setClazz(final Class<T> clazzToSet){
		this.clazz = clazzToSet;
	}


	public void save(T entity) {
		try {
			this.getSession();
			this.beginTransaction();
			this.session.save(entity);
			this.commit();
		} catch (Exception e) {
			this.rollback();
			System.out.println(e.getMessage());
		} 		
	}
	
	public void delete(int id) {
		try {
			
			Object o = this.getSession().createCriteria(clazz)
			.add(Restrictions.eq("id", id))
			.setMaxResults(1)
			.uniqueResult();
			
			this.getSession();
			this.beginTransaction();
			this.session.delete(o);
			this.commit();
		} catch (Exception e) {
			this.rollback();
			System.out.println(e.getMessage());
		} 
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() throws Exception {
		List<T> lista = null;
		try {
			lista = this.getSession().createCriteria(clazz)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list();
			//this.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}
	
	public void commit() {
		if (!this.transaction.isActive() || this.transaction==null) {
			this.beginTransaction();
		}
		this.transaction.commit();
	}

	public void rollback() {
		this.transaction.rollback();
	}

	public void beginTransaction() {
		if (this.hasTransaction()) {
			this.endTransaction();
		}
		this.transaction = this.session.beginTransaction();
	}

	public void endTransaction() {
		this.transaction = null;
	}

	public void sessionClose() {
		if ( this.session == null || this.session.isOpen() ){
			this.session.close();
		}
	}

	public void sessionClean() {
		this.session.clear();
	}

	public boolean hasTransaction() {
		return this.transaction != null;
	}


}
