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

import com.packsendme.roadbrewa.dto.CurrencyDto;
import com.packsendme.roadbrewa.dto.RoadwayDto;
import com.packsendme.roadbrewa.entity.Currency;
import com.packsendme.roadbrewa.roadway.service.Currency_Service;
import com.packsendme.roadbrewa.roadway.service.Lifecycle_Service;
import com.packsendme.roadbrewa.roadway.service.Roadway_Service;

@RestController
@RequestMapping("/roadbrewa")
public class Roadway_Controller {

	
	@Autowired
	private Roadway_Service roadwayService;	
	
	@Autowired
	private Lifecycle_Service lifecycleService;
	
	@Autowired
	private Currency_Service currencyService;

	
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
	@DeleteMapping("/roadway/all")
	public ResponseEntity<?> deleteAllRoadway(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return roadwayService.deleteAll(id);
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
	@PutMapping("/roadway/published")
	public ResponseEntity<?> checkPublished(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, 
			@Validated @RequestParam("id") String id, @Validated @RequestParam("transport") String transport)
	{	
		return lifecycleService.published(id,transport);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/roadway/blocked")
	public ResponseEntity<?> checkBlocked(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, 
			@Validated @RequestParam("id") String id)
	{	
		return lifecycleService.blocked(id);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/roadway/unlocked")
	public ResponseEntity<?> checkUnlocked(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, 
			@Validated @RequestParam("id") String id)
	{	
		return lifecycleService.unlocked(id);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/roadway/canceled")
	public ResponseEntity<?> checkCanceled(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, 
			@Validated @RequestParam("id") String id)
	{	
		return lifecycleService.canceled(id);
	}

	

	/***************************************
	 CURRENCY <--> GET | POST | DELETE 
	***************************************/

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/roadway/currency")
	public ResponseEntity<?> getCurrency(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp) {	
		return currencyService.findAll();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/roadway/currency")
	public ResponseEntity<?> postCurrency(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, 
			@Validated  @RequestBody CurrencyDto currency)
	{	
		return currencyService.save(currency);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/roadway/currency")
	public ResponseEntity<?> deleteCurrency(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return currencyService.delete(id);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/roadway/currency")
	public ResponseEntity<?> updateCurrency(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id,
			@Validated  @RequestBody CurrencyDto currency)
	{	
		return currencyService.update(id, currency);
	}



}
