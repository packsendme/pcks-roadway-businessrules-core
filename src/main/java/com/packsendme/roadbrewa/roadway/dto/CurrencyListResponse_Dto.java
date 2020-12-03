package com.packsendme.roadbrewa.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.roadbrewa.dto.CurrencyDto;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class CurrencyListResponse_Dto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<CurrencyDto> currencies = new ArrayList<CurrencyDto>();

 

	public CurrencyListResponse_Dto(List<CurrencyDto> currencies) {
		super();
		this.currencies = currencies;
	}



	public CurrencyListResponse_Dto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
