package com.packsendme.roadbrewa.roadway.component;

import org.springframework.stereotype.Component;

import com.packsendme.roadbrewa.component.RoadwayManagerConstants;

@Component
public class VersionManager_Component {

	public int getPositionStatus(String typeVersion) {
		int versionPosition = 0;
		
		switch (typeVersion) {
		case RoadwayManagerConstants.PUBLISHED_STATUS:
			versionPosition = 1;
			break;
		case RoadwayManagerConstants.BLOCKED_STATUS:
			versionPosition = 2;
			break;
		case RoadwayManagerConstants.UNLOCKED_STATUS:
			versionPosition = 3;
			break;
		case RoadwayManagerConstants.REGISTERED_STATUS:
			versionPosition = 4;
			break;
		case RoadwayManagerConstants.CANCELED_STATUS:
			versionPosition = 5;
			break;
		default:
			break;
		}
		return versionPosition;
	}
	
	public String getVersionForStatusType(String typeVersion, String version) {
		int currentPosition = getPositionStatus(typeVersion);
		String resultVersion = "";
		int positionVersion = 0;
		
		for(int i = 0; i < version.length(); i++) {
			char vlrC = version.charAt(i);
 			if(vlrC == '.') {
 				currentPosition++;
				if(currentPosition == positionVersion) {
				 break;
				}
				else {
					resultVersion = "";
					vlrC = '#';
				}
			}
 			if(vlrC != '#') {
 				resultVersion = resultVersion+String.valueOf(vlrC);
 			}
		}
		return resultVersion;
	}
	
	public String putVersion(String typeVersion, String version, String versionChange) {
		boolean vlrAdd = true;
 		int positionVersion = 0;
		String resultVersion = "";
		int currentPosition = getPositionStatus(typeVersion);
		
		for(int i = 0; i < version.length(); i++) {
			char vlrC = version.charAt(i);
			if ((vlrAdd == false) && (vlrC == '.')){
				vlrAdd = true;
			}
			int resultDif = positionVersion - currentPosition; 
			if((resultDif == 0) && (positionVersion == 1)) {
				vlrAdd = false;
				resultVersion = versionChange;
				positionVersion = -100;
			}
			if(vlrC == '.') {
				currentPosition++;
				if((resultDif == 1) && (positionVersion != 5)) {
					vlrAdd = false;
					resultVersion = resultVersion+"."+versionChange;
				}
				else if((resultDif == 1) && (positionVersion == 5)) {
					resultVersion = resultVersion+"."+versionChange;
					break;
				}
			}
			if (vlrAdd == true) {
				resultVersion = resultVersion+String.valueOf(vlrC);
			}
		}
		return resultVersion;
	}

	public String registeredGenerate(String versionCurrent) {
		String versionChange = getVersionForStatusType(RoadwayManagerConstants.REGISTERED_STATUS, versionCurrent);
		System.out.println(" +++++++++++++++++++ ");
		System.out.println(" versionChange "+ versionChange);
		int versionChangeI = Integer.parseInt(versionChange);
		System.out.println(" versionChangeI "+ versionChangeI);
		versionChangeI = versionChangeI+1;
		System.out.println(" versionChangeI "+ versionChangeI);

		versionChange = String.valueOf(versionChangeI); 
		System.out.println(" versionChange "+ versionChange);

		String versionFinal = putVersion(RoadwayManagerConstants.REGISTERED_STATUS, versionCurrent, versionChange);
		System.out.println(" versionFinal "+ versionFinal);

		return versionFinal;
	}
	
	public String publishedGenerate(String versionCurrent) {
		String versionChange = getVersionForStatusType(RoadwayManagerConstants.PUBLISHED_STATUS, versionCurrent);
		int versionChangeI = Integer.parseInt(versionChange);
		versionChangeI = versionChangeI+1;
		versionChange = String.valueOf(versionChangeI); 
		String versionFinal = putVersion(RoadwayManagerConstants.REGISTERED_STATUS, versionCurrent, versionChange);
		return versionFinal;
	}
	
	public String blockedGenerate(String versionCurrent) {
		String versionChange = getVersionForStatusType(RoadwayManagerConstants.BLOCKED_STATUS, versionCurrent);
		int versionChangeI = Integer.parseInt(versionChange);
		versionChangeI = versionChangeI+1;
		versionChange = String.valueOf(versionChangeI); 
		String versionFinal = putVersion(RoadwayManagerConstants.REGISTERED_STATUS, versionCurrent, versionChange);
		return versionFinal;
	}
	
	public String unlockedGenerate(String versionCurrent) {
		String versionChange = getVersionForStatusType(RoadwayManagerConstants.UNLOCKED_STATUS, versionCurrent);
		int versionChangeI = Integer.parseInt(versionChange);
		versionChangeI = versionChangeI+1;
		versionChange = String.valueOf(versionChangeI); 
		String versionFinal = putVersion(RoadwayManagerConstants.REGISTERED_STATUS, versionCurrent, versionChange);
		return versionFinal;
 	}
	
	public String canceledGenerate(String versionCurrent) {
		String versionChange = getVersionForStatusType(RoadwayManagerConstants.CANCELED_STATUS, versionCurrent);
		int versionChangeI = Integer.parseInt(versionChange);
		versionChangeI = versionChangeI+1;
		versionChange = String.valueOf(versionChangeI); 
		String versionFinal = putVersion(RoadwayManagerConstants.REGISTERED_STATUS, versionCurrent, versionChange);
		return versionFinal;
	}


}
