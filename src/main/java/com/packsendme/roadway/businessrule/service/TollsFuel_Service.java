package com.packsendme.roadway.businessrule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.packsendme.cross.common.constants.generic.HttpExceptionPackSend;
import com.packsendme.cross.common.response.Response;
import com.packsendme.roadway.commons.constants.RoadwayManagerConstants;
import com.packsendme.roadway.commons.dto.TollsFuelDto;
import com.packsendme.roadway.commons.entity.TollsFuel;
import com.packsendme.roadway.businessrule.dao.TollsFuelCacheImpl_Dao;

@Service
@ComponentScan({"com.packsendme.roadbrewa.roadway.dao"})
public class TollsFuel_Service {
	
	@Autowired
	private TollsFuelCacheImpl_Dao tollsFuel_DAO;
	
	private TollsFuelDto tollsfuelObj = new TollsFuelDto();

	public ResponseEntity<?> findOneTollsFuelByActive(String hashKey) {
		Response<TollsFuelDto> responseObj = null;
		try {
			 TollsFuel entity = tollsFuel_DAO.findOne(hashKey);
			 if(entity != null) {
				 TollsFuelDto tollsFuel_dto = tollsfuelObj.entityTOdto(entity); 
				 responseObj = new Response<TollsFuelDto>(0,HttpExceptionPackSend.CREATE_ROADWAYBRE.getAction(), tollsFuel_dto);
				 return new ResponseEntity<>(responseObj, HttpStatus.OK);

			 }
			 else {
				 responseObj = new Response<TollsFuelDto>(0,HttpExceptionPackSend.CREATE_ROADWAYBRE.getAction(), null);
				 return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);

			 }
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<TollsFuelDto>(0,HttpExceptionPackSend.CREATE_ROADWAYBRE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> save(TollsFuelDto tollsfuelDto) {
		Response<TollsFuelDto> responseObj = null;
		try {
			TollsFuel entity = tollsfuelObj.dtoTOentity(tollsfuelDto, null, RoadwayManagerConstants.ADD_OP_ROADWAY); 
			tollsFuel_DAO.add(tollsfuelDto.country, entity);
			responseObj = new Response<TollsFuelDto>(0,HttpExceptionPackSend.CREATE_ROADWAYBRE.getAction(), tollsfuelDto);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<TollsFuelDto>(0,HttpExceptionPackSend.CREATE_ROADWAYBRE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> delete(String key) {
		Response<String> responseObj = null;
		try {
			tollsFuel_DAO.delete(key);
			responseObj = new Response<String>(0,HttpExceptionPackSend.DELETE_VEHICLE.getAction(), key);
			return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<String>(0,HttpExceptionPackSend.DELETE_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> update(String key, TollsFuelDto tollsFuelDto) {
		Response<String> responseObj = null;
		try {
				TollsFuel entity = tollsfuelObj.dtoTOentity(tollsFuelDto, null, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				tollsFuel_DAO.delete(key);
				tollsFuel_DAO.add(tollsFuelDto.country, entity);
				responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), entity.country);
				return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
}
