package com.packsendme.roadway.businessrule.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.packsendme.roadway.commons.entity.Currency;
import com.packsendme.roadway.commons.entity.Roadway;

@Repository
public interface ICurrency_Repository extends MongoRepository<Currency, String>{

	@Query("{'name' :  {$eq: ?0}}")
	Roadway findCurrencyByName(String name);
	
}
