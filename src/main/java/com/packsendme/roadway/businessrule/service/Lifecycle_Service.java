package com.packsendme.roadway.businessrule.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.packsendme.cross.common.constants.generic.HttpExceptionPackSend;
import com.packsendme.cross.common.response.Response;
import com.packsendme.roadway.commons.constants.RoadwayManagerConstants;
import com.packsendme.roadway.commons.entity.Roadway;
import com.packsendme.roadway.businessrule.component.VersionManager_Component;
import com.packsendme.roadway.businessrule.dao.BusinessRulesCacheImpl_Dao;
import com.packsendme.roadway.businessrule.dao.BusinessRulesImpl_Dao;

@Service
@ComponentScan({"com.packsendme.roadbrewa.roadway.dao"})
public class Lifecycle_Service {
	
	@Autowired
	private BusinessRulesImpl_Dao roadway_DAO;

	@Autowired
	private BusinessRulesCacheImpl_Dao roadwayCache_DAO;

	@Autowired
	private VersionManager_Component versionManagerObj;
	
	
	public ResponseEntity<?> published(String id, String transportType) {
		Response<String> responseObj = null;
		try {
			// (1) Check in DDBB exist BRE with Status = Published to TransportType
			Roadway roadwayStatusEntity = roadway_DAO.findEntityByFourParameters(transportType,RoadwayManagerConstants.PUBLISHED_STATUS,null,null);
			if(roadwayStatusEntity == null) {
				// (2) Recover Entity Roadway to change fields (Status / Version)
				Optional<Roadway> roadwayData = roadway_DAO.findOneById(id);
				if(roadwayData != null){
					// (3) Check Entity if status = REGISTERED or UNLOCKED
					if((roadwayData.get().status.equals(RoadwayManagerConstants.REGISTERED_STATUS)) ||
							(roadwayData.get().status.equals(RoadwayManagerConstants.UNLOCKED_STATUS))) {
					
						Roadway roadwayEntity =  roadwayData.get();
						roadwayEntity.status = RoadwayManagerConstants.PUBLISHED_STATUS;
						roadwayEntity.version = versionManagerObj.publishedGenerate(roadwayEntity.version);
						
						// (3) Send Entity to Queue Roadway Cache
						try {
							boolean returnCache = roadwayCache_DAO.add(roadwayEntity.transport_name, roadwayEntity);
							if(returnCache == true) {
								roadwayEntity = roadway_DAO.update(roadwayEntity);
							}
							responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), roadwayEntity.id);
							return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
						}
						catch (Exception e) {
							e.printStackTrace();
							responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
							return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
						}
					}
					else {
						responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
						return new ResponseEntity<>(responseObj, HttpStatus.CONFLICT);

					}
				}
				else {
					responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
					return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
				}
			}
			else {
				responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
		
	
	public ResponseEntity<?> blocked (String id) {
		Response<String> responseObj = null;
		try {
			// (1) Recover Entity RoadwayBRE
			Optional<Roadway> roadwayData = roadway_DAO.findOneById(id);
			if(roadwayData != null){
				//(2) Check BRE if status equal Published
				if(roadwayData.get().status.equals(RoadwayManagerConstants.PUBLISHED_STATUS)) {
					// (3) Change RoadwayBRE to status and equal Blocked
					Roadway roadwayEntity =  roadwayData.get();
					roadwayEntity.status = RoadwayManagerConstants.BLOCKED_STATUS;
					roadwayEntity.version = versionManagerObj.blockedGenerate(roadwayEntity.version);
					
					// (4) Send request blocked (delete) to QueueCache the Key (transport)
					try {
						boolean returnCache = roadwayCache_DAO.delete(roadwayEntity.transport_name);
						if(returnCache == true) {
							roadwayEntity = roadway_DAO.update(roadwayEntity);
						}
						responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), roadwayEntity.id);
						return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
					}
					catch (Exception e) {
						e.printStackTrace();
						responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
						return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
					}
				}
				else {
					responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
					return new ResponseEntity<>(responseObj, HttpStatus.CONFLICT);
				}
			}
			else {
				responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}	
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> unlocked (String id) {
		Response<String> responseObj = null;
		Roadway roadwayCloneEntity = null;
		String id_entity = null;
		try {
			// (1) Recover Entity RoadwayBRE
			Optional<Roadway> roadwayData = roadway_DAO.findOneById(id);
			if(roadwayData != null){
				//(2) Check BRE if status equal Blocked
				if(roadwayData.get().status.equals(RoadwayManagerConstants.BLOCKED_STATUS)) {
					// (3) Make a copy entity the Entity Roadway-BRE status = Blocked but change the status to Blocked
					id_entity = roadwayData.get().id;
					
					roadwayCloneEntity = roadwayData.get();
					roadwayCloneEntity.status = RoadwayManagerConstants.UNLOCKED_STATUS;
					roadwayCloneEntity.version = versionManagerObj.unlockedGenerate(roadwayCloneEntity.version);

					System.out.println(" ============================ ");
					System.out.println(" ID ENTITY "+ id_entity);
					System.out.println(" ============================ ");

					roadwayCloneEntity.blocked_id = id_entity;
					roadwayCloneEntity.id = null;
					
					try {
						roadwayCloneEntity = roadway_DAO.save(roadwayCloneEntity);
						responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), roadwayCloneEntity.id);
						return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
					}
					catch (Exception e) {
						e.printStackTrace();
						responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
						return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
					}
				}
				else {
					responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
					return new ResponseEntity<>(responseObj, HttpStatus.CONFLICT);
				}
			}
			else {
				responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}	
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	public ResponseEntity<?> canceled (String id) {
		Response<String> responseObj = null;
		try {
			// (1) Recover Entity RoadwayBRE
			Optional<Roadway> roadwayData = roadway_DAO.findOneById(id);
			if(roadwayData != null){
				//(2) Check BRE if status equal Blocked
				if((roadwayData.get().status.equals(RoadwayManagerConstants.BLOCKED_STATUS)) ||
						(roadwayData.get().status.equals(RoadwayManagerConstants.UNLOCKED_STATUS))) {
					// (3) Change Roadway-BRE entity to status and version Canceled
					Roadway roadwayEntity =  roadwayData.get();
					roadwayEntity.status = RoadwayManagerConstants.CANCELED_STATUS;
					roadwayEntity.version = versionManagerObj.canceledGenerate(roadwayEntity.version);
					try {
						roadway_DAO.update(roadwayEntity);
						responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), roadwayEntity.id);
						return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
					}
					catch (Exception e) {
						e.printStackTrace();
						responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
						return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
					}
				}
				else {
					responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
					return new ResponseEntity<>(responseObj, HttpStatus.CONFLICT);
				}
			}
			else {
				responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}	
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
			
	
}
