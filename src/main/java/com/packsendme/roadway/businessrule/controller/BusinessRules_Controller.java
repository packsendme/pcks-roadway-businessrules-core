package com.packsendme.roadway.businessrule.controller;

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

import com.packsendme.roadway.commons.dto.CurrencyDto;
import com.packsendme.roadway.commons.dto.RoadwayDto;
import com.packsendme.roadway.commons.dto.TollsFuelDto;
import com.packsendme.roadway.businessrule.service.Currency_Service;
import com.packsendme.roadway.businessrule.service.Lifecycle_Service;
import com.packsendme.roadway.businessrule.service.BusinessRules_Service;
import com.packsendme.roadway.businessrule.service.TollsFuel_Service;

@RestController
@RequestMapping("/roadway/businessrule")
public class BusinessRules_Controller {

	
	@Autowired
	private BusinessRules_Service roadwayService;	
	
	@Autowired
	private Lifecycle_Service lifecycleService;
	
	@Autowired
	private Currency_Service currencyService;

	@Autowired
	private TollsFuel_Service tollsfuelService;
	
	/***************************************
	 ROADWAY <--> GET | POST | DELETE 
	***************************************/

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/")
	public ResponseEntity<?> getRoadway(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp) {	
		return roadwayService.findAll();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/")
	public ResponseEntity<?> postRoadway(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, 
			@Validated  @RequestBody RoadwayDto roadway)
	{	
		return roadwayService.save(roadway);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/")
	public ResponseEntity<?> deleteRoadway(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return roadwayService.delete(id);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/all")
	public ResponseEntity<?> deleteAllRoadway(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return roadwayService.deleteAll(id);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/")
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
	@PutMapping("/life/published")
	public ResponseEntity<?> checkPublished(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, 
			@Validated @RequestParam("id") String id, @Validated @RequestParam("transport") String transport)
	{	
		return lifecycleService.published(id,transport);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/life/blocked")
	public ResponseEntity<?> checkBlocked(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, 
			@Validated @RequestParam("id") String id)
	{	
		return lifecycleService.blocked(id);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/life/unlocked")
	public ResponseEntity<?> checkUnlocked(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, 
			@Validated @RequestParam("id") String id)
	{	
		return lifecycleService.unlocked(id);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/life/canceled")
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
	@GetMapping("/currency")
	public ResponseEntity<?> getCurrency(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp) {	
		return currencyService.findAll();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/currency")
	public ResponseEntity<?> postCurrency(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, 
			@Validated  @RequestBody CurrencyDto currency)
	{	
		return currencyService.save(currency);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/currency")
	public ResponseEntity<?> deleteCurrency(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return currencyService.delete(id);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/currency")
	public ResponseEntity<?> updateCurrency(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id,
			@Validated  @RequestBody CurrencyDto currency)
	{	
		return currencyService.update(id, currency);
	}

	/***************************************
	 TOLLS AND FUEL <--> GET | POST | DELETE 
	***************************************/

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/tollsfuel")
	public ResponseEntity<?> getTollsFuel(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, 
			@RequestParam("country") String country) {	
		return tollsfuelService.findOneTollsFuelByActive(country);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/tollsfuel")
	public ResponseEntity<?> postTollsFuel(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, 
			@Validated  @RequestBody TollsFuelDto tollsFuel)
	{	
		return tollsfuelService.save(tollsFuel);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/tollsfuel")
	public ResponseEntity<?> deleteTollsFuel(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return tollsfuelService.delete(id);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/tollsfuel")
	public ResponseEntity<?> updateTollsFuel(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id,
			@Validated  @RequestBody TollsFuelDto tollsFuel)
	{	
		return tollsfuelService.update(id, tollsFuel);
	}


}
