package com.packsendme.roadbrewa.roadway.dao;

public interface ICrudCache<T> {
	
	public boolean add(String hashKey, T entity);
	public boolean delete(String hashKey);
	public T findOne(String value);

	
}
