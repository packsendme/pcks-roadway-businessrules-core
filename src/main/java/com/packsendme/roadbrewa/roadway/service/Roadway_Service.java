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
import com.packsendme.roadbrewa.roadway.dao.RoadwayDAO;
import com.packsendme.roadbrewa.roadway.dto.RoadwayListResponse_Dto;

@Service
@ComponentScan({"com.packsendme.roadbrewa.roadway.dao"})
public class Roadway_Service {
	
	@Autowired
	private RoadwayDAO roadway_DAO;
	
	@Autowired
	private RoadwayDto roadwayObj;
	
	@Autowired
	private VersionManager_Component versionManagerObj;

	public ResponseEntity<?> findAll() {
		Response<RoadwayListResponse_Dto> responseObj = null;
		//RoadwayDto roadwayDto = new RoadwayDto(); 
		RoadwayListResponse_Dto roadwayListResponse_Dto = new RoadwayListResponse_Dto();
		try {
			roadwayListResponse_Dto.roadways = roadwayObj.entityTOdto(roadway_DAO.findAll());
			responseObj = new Response<RoadwayListResponse_Dto>(0,HttpExceptionPackSend.CREATED_ROADWAYBRE.getAction(), roadwayListResponse_Dto);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<RoadwayListResponse_Dto>(0,HttpExceptionPackSend.CREATED_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> save(RoadwayDto roadwayDto) {
		Response<RoadwayDto> responseObj = null;
		try {
			Roadway entity = roadwayObj.dtoTOentity(roadwayDto, null, RoadwayManagerConstants.ADD_OP_ROADWAY);  //parserObj.parserRoadwayBRE_TO_Model(roadwayBRE,null,RoadwayManagerConstants.ADD_OP_ROADWAY);
			entity.version = versionManagerObj.registeredGenerate(RoadwayManagerConstants.VERSION_DEFAULT);
			roadway_DAO.save(entity);
			responseObj = new Response<RoadwayDto>(0,HttpExceptionPackSend.CREATE_ROADWAYBRE.getAction(), roadwayDto);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<RoadwayDto>(0,HttpExceptionPackSend.CREATE_ROADWAYBRE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> delete(String id) {
		Response<String> responseObj = null;
		try {
			Optional<Roadway> roadwayData = roadway_DAO.findOneById(id);
			if(roadwayData.isPresent()) {
				Roadway entity = roadwayData.get(); 
				if(roadway_DAO.remove(entity) == true) {
					responseObj = new Response<String>(0,HttpExceptionPackSend.DELETE_VEHICLE.getAction(), id);
				}
				else {
					responseObj = new Response<String>(0,HttpExceptionPackSend.DELETE_VEHICLE.getAction(), id);
					return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
				}
			}
			else {
				responseObj = new Response<String>(0,HttpExceptionPackSend.DELETE_ROADWAYBRE.getAction(), id);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<String>(0,HttpExceptionPackSend.DELETE_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> update(String id, RoadwayDto roadwayDto) {
		Response<String> responseObj = null;
		try {
			Optional<Roadway> roadwayData = roadway_DAO.findOneById(id);
			if(roadwayData.isPresent()) {
				Roadway entity = roadwayData.get(); 
				entity = roadwayObj.dtoTOentity(roadwayDto, entity, RoadwayManagerConstants.UPDATE_OP_ROADWAY); //parserObj.parserRoadwayBRE_TO_Model(businessRuleBRE,roadwayBRE_Entity,RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				entity = roadway_DAO.update(entity);
				responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), entity.id);
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
	/*
	public ResponseEntity<?> crudTrigger(String operationType, String categoryName_Old, CategoryRuleModel categoryModelNew) {
		RoadwayModel roadwayObj_Model = new RoadwayModel();
		Response<CategoryRuleModel> responseObj = null;
	
		// Find Category relationship with Vehicle will be removed
		try {
			List<RoadwayModel> roadwayL = roadwayBRE_DAO.findAll();
			for(RoadwayModel roadway_entity : roadwayL) {
				
				if(operationType.equals(RoadwayManagerConstants.DELETE_OP_ROADWAY)) {
					if(roadway_entity.categoryRule.categoryType.name_category.equals(categoryModelNew.categoryType.name_category)) {
						roadwayObj_Model = roadway_entity;
						roadwayBRE_DAO.remove(roadwayObj_Model);
						roadwayObj_Model = new RoadwayModel();
					}
				} 
				else if(operationType.equals(RoadwayManagerConstants.UPDATE_OP_ROADWAY)) {
					if(roadway_entity.categoryRule.categoryType.name_category.equals(categoryName_Old)) {
						roadwayObj_Model = roadway_entity;
						roadwayObj_Model.categoryRule = null;
						roadwayObj_Model.categoryRule = categoryModelNew;
						roadwayBRE_DAO.update(roadwayObj_Model);
						roadwayObj_Model = new RoadwayModel();
					}
				}
			}
			responseObj = new Response<CategoryRuleModel>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryRuleModel>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}*/


	
}
