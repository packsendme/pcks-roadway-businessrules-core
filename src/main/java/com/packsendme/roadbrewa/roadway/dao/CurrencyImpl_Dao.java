package com.packsendme.roadbrewa.roadway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.roadbrewa.entity.Currency;
import com.packsendme.roadbrewa.roadway.repository.ICurrency_Repository;

@Component
@ComponentScan({"com.packsendme.roadbrewa.roadway.repository"})
public class CurrencyImpl_Dao implements ICrud<Currency> {

	@Autowired
	ICurrency_Repository currency_Rep; 

	
	@Override
	public Currency save(Currency entity) {
		try {
			return entity = currency_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<Currency> findOneById(String id) {
		try {
			return currency_Rep.findById(id);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Currency> findAll() {
		try {
			List<Currency> entityL = new ArrayList<Currency>(); 
			entityL = currency_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(Currency entity) {
		try {
			currency_Rep.delete(entity);
			return true; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return false; 
		}		
	}

	@Override
	public Currency update(Currency entity) {
		try {
			Currency entityModel = currency_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}		
	}

	@Override
	public Currency findEntityByFourParameters(String p1, String p2, String p3, String p4) {
		return null; 
	}

	@Override
	public List<Currency> findEntitesByTwoParameters(String p1,String p2) {
		return null; 	
	}
}
