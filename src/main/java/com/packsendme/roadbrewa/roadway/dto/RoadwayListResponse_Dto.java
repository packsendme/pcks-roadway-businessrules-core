package com.packsendme.roadbrewa.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.roadbrewa.dto.RoadwayDto;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class RoadwayListResponse_Dto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<RoadwayDto> roadways = new ArrayList<RoadwayDto>();

 

	public RoadwayListResponse_Dto(List<RoadwayDto> roadways) {
		super();
		this.roadways = roadways;
	}



	public RoadwayListResponse_Dto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
