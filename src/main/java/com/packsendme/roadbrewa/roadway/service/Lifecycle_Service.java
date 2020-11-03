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
import com.packsendme.roadbrewa.dto.RoadwayDto;
import com.packsendme.roadbrewa.entity.Roadway;
import com.packsendme.roadbrewa.roadway.component.VersionManager_Component;
import com.packsendme.roadbrewa.roadway.dao.Roadway_Dao;

@Service
@ComponentScan({"com.packsendme.roadbrewa.roadway.dao"})
public class Lifecycle_Service {
	
	@Autowired
	private Roadway_Dao roadway_DAO;
	
	private RoadwayDto roadwayObj = new RoadwayDto();
	
	@Autowired
	private VersionManager_Component versionManagerObj;
	
	
	public ResponseEntity<?> published(String id) {
		Response<String> responseObj = null;
		try {
			// (1) Find BRE with Status = Published
			Roadway roadwayStatusEntity = roadway_DAO.findOneByParameters(RoadwayManagerConstants.PUBLISHED_STATUS);
			if(roadwayStatusEntity == null) {
				// (2) Recover Entity Roadway to change fields (Status / Version)
				Optional<Roadway> roadwayData = roadway_DAO.findOneById(id);
				Roadway roadwayEntity =  roadwayData.get();
				roadwayEntity.status = RoadwayManagerConstants.PUBLISHED_STATUS;
				roadwayEntity.version = versionManagerObj.publishedGenerate(roadwayEntity.version);
				
				// (3) Send Entity to Queue Roadway


				roadwayEntity = roadway_DAO.update(roadwayEntity);
				responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), roadwayEntity.id);
				return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
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
