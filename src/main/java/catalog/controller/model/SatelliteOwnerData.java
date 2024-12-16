package catalog.controller.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import catalog.entity.Mission;
import catalog.entity.Satellite;
import catalog.entity.SatelliteOwner;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SatelliteOwnerData {

	private long satelliteOwnerId;
	private String company;
	private String address;
	private String email;
	private String phone;
	private Set<SatelliteData> satellites = new HashSet<>();
	
	public SatelliteOwnerData(SatelliteOwner satelliteOwner) {
		satelliteOwnerId = satelliteOwner.getSatelliteOwnerId();
		company = satelliteOwner.getCompany();
		address = satelliteOwner.getAddress();
		email = satelliteOwner.getEmail();
		phone = satelliteOwner.getPhone();
		
		for (Satellite satellite : satelliteOwner.getSatellites()) {
			SatelliteData satelliteData = new SatelliteData(satellite);
			satellites.add(satelliteData);
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class SatelliteData {
		private Long satelliteId;
		private String noradId;
		private String tleLine1;
		private String tleLine2;
		private BigDecimal mass;
		private BigDecimal sphericalRadius;
		private Boolean moveable;
		private Boolean active;
		private Set<MissionData> missions = new HashSet<>();
		
		public SatelliteData(Satellite satellite) {
			satelliteId = satellite.getSatelliteId();
			noradId = satellite.getNoradId();
			tleLine1 = satellite.getTleLine1();
			tleLine2 = satellite.getTleLine2();
			mass = satellite.getMass();
			sphericalRadius = satellite.getSphericalRadius();
			moveable = satellite.getMoveable();
			active = satellite.getActive();
			
			for (Mission mission : satellite.getMissions()) {
				MissionData missionData = new MissionData(mission);
				missions.add(missionData);
			}
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class MissionData {
		private Long missionId;
		private String name;
		
		public MissionData(Mission mission) {
			missionId = mission.getMissionId();
			name = mission.getName();
		}
	}
}
