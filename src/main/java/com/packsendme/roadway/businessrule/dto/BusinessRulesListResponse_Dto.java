package com.packsendme.roadway.businessrule.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.roadway.commons.dto.RoadwayDto;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class BusinessRulesListResponse_Dto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<RoadwayDto> roadways = new ArrayList<RoadwayDto>();

 

	public BusinessRulesListResponse_Dto(List<RoadwayDto> roadways) {
		super();
		this.roadways = roadways;
	}



	public BusinessRulesListResponse_Dto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
