package com.packsendme.roadbrewa.roadway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.roadbrewa.entity.Roadway;
import com.packsendme.roadbrewa.roadway.repository.IRoadway_Repository;

@Component
@ComponentScan({"com.packsendme.roadbrewa.roadway.repository"})
public class RoadwayImpl_Dao implements ICrud<Roadway> {

	@Autowired
	IRoadway_Repository roadwayManager_Rep; 

	
	@Override
	public Roadway save(Roadway entity) {
		try {
			return entity = roadwayManager_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<Roadway> findOneById(String id) {
		try {
			return roadwayManager_Rep.findById(id);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Roadway> findAll() {
		try {
			List<Roadway> entityL = new ArrayList<Roadway>(); 
			entityL = roadwayManager_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(Roadway entity) {
		try {
			roadwayManager_Rep.delete(entity);
			return true; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return false; 
		}		
	}

	@Override
	public Roadway update(Roadway entity) {
		try {
			Roadway entityModel = roadwayManager_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}		
	}

	@Override
	public Roadway findEntityByFourParameters(String p1, String p2, String p3, String p4) {
		try {
			Roadway entityModel = roadwayManager_Rep.findRodwayBreByTransportStatus(p1,p2);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}		
	}

	@Override
	public List<Roadway> findEntitesByTwoParameters(String p1,String p2) {
		try {
			List<Roadway> entityModel = roadwayManager_Rep.findRodwaysBreByBlockId(p1);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}		
	}
}
