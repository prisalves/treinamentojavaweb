package br.com.emiolo.service;

import java.util.List;

import br.com.emiolo.dao.Dao;

public class Service<PK, T> {

	private Dao<PK, T> dao;

	protected Class<T> clazzService;

	public void setClazz(Class<T> clazzToSet){
		this.clazzService = clazzToSet;
		dao.setClazz(clazzService);
	}

	public void setDao(Dao<PK, T> daoSet){
		this.dao = daoSet;
	}
	
	public Dao<PK, T> getDao() {
		return dao;
	}
	
	public void save(T entity) {
		try {
			dao.save(entity);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void delete(int id) {
		try {
			dao.delete(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public List<T> findAll() {
		List<T> lista = null;
		try {
			lista = dao.findAll();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}
	

}
