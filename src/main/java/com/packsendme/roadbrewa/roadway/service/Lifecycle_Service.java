package com.packsendme.roadbrewa.roadway.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.packsendme.lib.common.constants.generic.HttpExceptionPackSend;
import com.packsendme.lib.common.response.Response;
import com.packsendme.roadbrewa.component.RoadwayManagerConstants;
import com.packsendme.roadbrewa.entity.Roadway;
import com.packsendme.roadbrewa.roadway.component.VersionManager_Component;
import com.packsendme.roadbrewa.roadway.dao.RoadwayCacheImpl_Dao;
import com.packsendme.roadbrewa.roadway.dao.RoadwayImpl_Dao;

@Service
@ComponentScan({"com.packsendme.roadbrewa.roadway.dao"})
public class Lifecycle_Service {
	
	@Autowired
	private RoadwayImpl_Dao roadway_DAO;

	@Autowired
	private RoadwayCacheImpl_Dao roadwayCache_DAO;

	@Autowired
	private VersionManager_Component versionManagerObj;
	
	
	public ResponseEntity<?> published(String id) {
		Response<String> responseObj = null;
		try {
			// (1) Check in DDBB exist BRE with Status = Published
			Roadway roadwayStatusEntity = roadway_DAO.findOneByParameters(RoadwayManagerConstants.PUBLISHED_STATUS);
			if(roadwayStatusEntity == null) {
				// (2) Recover Entity Roadway to change fields (Status / Version)
				Optional<Roadway> roadwayData = roadway_DAO.findOneById(id);
				if(roadwayData != null){
					// (3) Check BRE if status equal REGISTERED or UNLOCKED
					if((roadwayData.get().status.equals(RoadwayManagerConstants.REGISTERED_STATUS)) ||
							(roadwayData.get().status.equals(RoadwayManagerConstants.UNLOCKED_STATUS))) {
					
						Roadway roadwayEntity =  roadwayData.get();
						roadwayEntity.status = RoadwayManagerConstants.PUBLISHED_STATUS;
						roadwayEntity.version = versionManagerObj.publishedGenerate(roadwayEntity.version);
						
						// (3) Send Entity to Queue Roadway Cache
						try {
							boolean returnCache = roadwayCache_DAO.add(roadwayEntity.transport, roadwayEntity);
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
						return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
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
						boolean returnCache = roadwayCache_DAO.delete(roadwayEntity.transport);
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
					return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
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
	
	public ResponseEntity<?> unblocked (String id) {
		Response<String> responseObj = null;
		Roadway roadwayCloneEntity = null;
		try {
			// (1) Recover Entity RoadwayBRE
			Optional<Roadway> roadwayData = roadway_DAO.findOneById(id);
			if(roadwayData != null){
				//(2) Check BRE if status equal Blocked
				if(roadwayData.get().status.equals(RoadwayManagerConstants.BLOCKED_STATUS)) {
					// (3) Make a copy the Roadway-BRE with status = Blocked
					roadwayCloneEntity = roadwayData.get();
					roadwayCloneEntity.id = null;

					// (4) Chnage Roadway-BRE entity to status and version Unblocked
					Roadway roadwayEntity =  roadwayData.get();
					roadwayEntity.status = RoadwayManagerConstants.UNLOCKED_STATUS;
					roadwayEntity.version = versionManagerObj.unlockedGenerate(roadwayEntity.version);
					try {
						roadway_DAO.save(roadwayCloneEntity);
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
					return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
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
