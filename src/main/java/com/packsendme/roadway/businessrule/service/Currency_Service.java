package com.packsendme.roadway.businessrule.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.packsendme.cross.common.constants.generic.HttpExceptionPackSend;
import com.packsendme.cross.common.response.Response;
import com.packsendme.roadway.commons.constants.RoadwayManagerConstants;
import com.packsendme.roadway.commons.dto.CurrencyDto;
import com.packsendme.roadway.commons.entity.Currency;

import com.packsendme.roadway.businessrule.component.VersionManager_Component;
import com.packsendme.roadway.businessrule.dao.CurrencyImpl_Dao;
import com.packsendme.roadway.businessrule.dto.CurrencyListResponse_Dto;

@Service
@ComponentScan({"com.packsendme.roadbrewa.roadway.dao"})
public class Currency_Service {
	
	@Autowired
	private CurrencyImpl_Dao currency_DAO;
	
	@Autowired
	private VersionManager_Component versionManagerObj;

	private CurrencyDto currencyObj = new CurrencyDto();

	public ResponseEntity<?> findAll() {
		Response<CurrencyListResponse_Dto> responseObj = null;
		CurrencyListResponse_Dto currencyListResponse_Dto = new CurrencyListResponse_Dto();
		try {
			currencyListResponse_Dto.currencies = currencyObj.entityTOdto(currency_DAO.findAll());
			responseObj = new Response<CurrencyListResponse_Dto>(0,HttpExceptionPackSend.CREATED_ROADWAYBRE.getAction(), currencyListResponse_Dto);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CurrencyListResponse_Dto>(0,HttpExceptionPackSend.CREATED_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> save(CurrencyDto currencyDto) {
		Response<CurrencyDto> responseObj = null;
		try {
			Currency entity = currencyObj.dtoTOentity(currencyDto, null, RoadwayManagerConstants.ADD_OP_ROADWAY); 
			currency_DAO.save(entity);
			responseObj = new Response<CurrencyDto>(0,HttpExceptionPackSend.CREATE_ROADWAYBRE.getAction(), currencyDto);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CurrencyDto>(0,HttpExceptionPackSend.CREATE_ROADWAYBRE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> delete(String id) {
		Response<String> responseObj = null;
		try {
			Optional<Currency> currencyData = currency_DAO.findOneById(id);
			if(currencyData.isPresent()) {
				Currency entity = currencyData.get();
				if(currency_DAO.remove(entity) == true) {
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
	
	public ResponseEntity<?> update(String id, CurrencyDto currencyDto) {
		Response<String> responseObj = null;
		try {
			Optional<Currency> currencyData = currency_DAO.findOneById(id);
			if(currencyData.isPresent()) {
				Currency entity = currencyData.get(); 
				entity = currencyObj.dtoTOentity(currencyDto, entity, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				entity = currency_DAO.update(entity);
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
	
}
