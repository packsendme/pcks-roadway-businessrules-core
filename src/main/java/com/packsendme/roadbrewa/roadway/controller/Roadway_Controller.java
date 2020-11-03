package com.packsendme.roadbrewa.roadway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.packsendme.roadbrewa.dto.RoadwayDto;
import com.packsendme.roadbrewa.roadway.service.Lifecycle_Service;
import com.packsendme.roadbrewa.roadway.service.Roadway_Service;

@RestController
@RequestMapping("/roadbrewa")
public class Roadway_Controller {

	
	@Autowired
	private Roadway_Service roadwayService;	
	
	@Autowired
	private Lifecycle_Service lifecycleService;

	
	/***************************************
	 ROADWAY <--> GET | POST | DELETE 
	***************************************/

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/roadway")
	public ResponseEntity<?> getRoadway(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp) {	
		return roadwayService.findAll();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/roadway")
	public ResponseEntity<?> postRoadway(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, 
			@Validated  @RequestBody RoadwayDto roadway)
	{	
		return roadwayService.save(roadway);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/roadway")
	public ResponseEntity<?> deleteRoadway(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return roadwayService.delete(id);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/roadway")
	public ResponseEntity<?> updateRoadway(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id,
			@Validated  @RequestBody RoadwayDto roadway)
	{	
		return roadwayService.update(id, roadway);
	}

	
	
	/***************************************
	 LIFE CYCLE <--> 
	***************************************/
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/roadway/published")
	public ResponseEntity<?> checkPublished(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, 
			@Validated @RequestParam("id") String id)
	{	
		return lifecycleService.published(id);
	}

}