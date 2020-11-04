package com.packsendme.roadbrewa.roadway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.packsendme.roadbrewa.entity.Roadway;

@Repository
public interface IRoadway_Repository extends MongoRepository<Roadway, String>{

	@Query("{'category' :  {$eq: ?0}}")
	Roadway findBusinessRuleByCategory(String category);
	
	@Query("{'transport' : ?0, status : {$lt : ?1}}")
	Roadway findRodwayBreByTransportStatus(String transport, String status);
}
