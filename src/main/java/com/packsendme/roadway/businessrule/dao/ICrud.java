package com.packsendme.roadway.businessrule.dao;

import java.util.List;
import java.util.Optional;

public interface ICrud<T> {

	public T save(T entity);

	public Optional<T> findOneById(String id);
	
	public List<T> findAll();
	
	public Boolean remove(T entity);
	
	public T update(T entity);
	
	public T findEntityByFourParameters(String p1, String p2, String p3, String p4);
	
	public List<T> findEntitesByTwoParameters(String p1,String p2);

}
