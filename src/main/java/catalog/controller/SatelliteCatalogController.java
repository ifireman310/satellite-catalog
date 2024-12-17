package catalog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import catalog.controller.model.SatelliteOwnerData;
import catalog.controller.model.SatelliteOwnerData.MissionData;
import catalog.controller.model.SatelliteOwnerData.SatelliteData;
import catalog.service.SatelliteCatalogService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/satellite_catalog")
public class SatelliteCatalogController {

	@Autowired
	SatelliteCatalogService satCatService;
	
	@GetMapping("/satellite")
	public List<SatelliteData> retrieveAllSatellites() {
		log.info("Retreive all satellites called.");
		
		return satCatService.retrieveAllSatellites();
	}
	
	@GetMapping("/satellite/{satelliteId}")
	public SatelliteData retreiveSatelliteById(@PathVariable Long satelliteId) {
		log.info("Retrieving satellite with ID=",satelliteId);
		
		return satCatService.retrieveSatelliteById(satelliteId);
	}
	
	
	@GetMapping("/satellite_owner")
	public List<SatelliteOwnerData> retrieveAllSatelliteOwners() {
		log.info("Retreive all satellite owners called.");
		
		return satCatService.retrieveAllSatelliteOwners();
	}
	
	@GetMapping("/mission")
	public List<MissionData> retrieveAllMissions() {
		log.info("Retreive all missions called.");
		
		return satCatService.retrieveAllMissions();
	}
	
	@PostMapping("/satellite_owner")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SatelliteOwnerData insertSatelliteOwner(@RequestBody SatelliteOwnerData satelliteOwnerData) {
		log.info("Creating satellite owner {}",satelliteOwnerData);
		return satCatService.saveSatelliteOwner(satelliteOwnerData);
	}
	
	@PutMapping("satellite_owner/{satelliteOwnerId}")
	public SatelliteOwnerData updateSatelliteOwner(@PathVariable Long satelliteOwnerId, @RequestBody SatelliteOwnerData satelliteOwnerData) {
		satelliteOwnerData.setSatelliteOwnerId(satelliteOwnerId);
		log.info("Updating satellite owner {}",satelliteOwnerData);
		return satCatService.saveSatelliteOwner(satelliteOwnerData);
	}
	
	@GetMapping("/satellite_owner/{satelliteOwnerId}")
	public SatelliteOwnerData getSatelliteOwnerById(@PathVariable Long satelliteOwnerId) {
		log.info("Retrieving satellite owner with ID=", satelliteOwnerId);
		
		return satCatService.retrieveSatelliteOwnerById(satelliteOwnerId);
	}
	
	@GetMapping("/satellite_owner/{satelliteOwnerId}/satellite")
	public List<SatelliteData> getAllSatellitesByOwner(@PathVariable Long satelliteOwnerId) {
		log.info("Retrieving all satellites associated with satellite owner with ID=",satelliteOwnerId);
		
		return satCatService.retrieveAllSatellitesFromOwner(satelliteOwnerId);
	}
	
	@PostMapping("/satellite_owner/{satelliteOwnerId}/satellite")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SatelliteData insertSatellite(@PathVariable Long satelliteOwnerId, @RequestBody SatelliteData satelliteData) {
		log.info("Creating satellite {} for satellite owner with ID={}", satelliteData, satelliteOwnerId);
		return satCatService.saveSatellite(satelliteData, satelliteOwnerId);
	}
	
	@PutMapping("/satellite_owner/{satelliteOwnerId}/satellite/{satelliteId}")
	public SatelliteData updateSatellite(@PathVariable Long satelliteId, @PathVariable Long satelliteOwnerId, @RequestBody SatelliteData satelliteData) {
		satelliteData.setSatelliteId(satelliteId);
		log.info("Updating satellite {} for owner ID={}",satelliteData,satelliteOwnerId);
		
		return satCatService.saveSatellite(satelliteData,satelliteOwnerId);
	}
	
	@PostMapping("/mission")
	public MissionData insertMission(@RequestBody MissionData missionData) {
		log.info("Creating mission {}", missionData);
		return satCatService.saveMission(missionData);
	}
	
	@DeleteMapping("/satellite/{satelliteId}")
	public Map<String,String> deleteSatelliteById(@PathVariable Long satelliteId) {
		log.info("Deleting satellite with ID={}",satelliteId);
		
		satCatService.deleteSatelliteById(satelliteId);
		
		return Map.of("message", "Deletion of satellite with ID=" + satelliteId + " was successful.");
	}
	
	@DeleteMapping("/mission/{missionId}")
	public Map<String,String> deleteMissionById(@PathVariable Long missionId) {
		log.info("Deleting satellite with ID={}",missionId);
		
		satCatService.deleteMissionById(missionId);
		
		return Map.of("message", "Deletion of mission with ID=" + missionId + " was successful.");
	}
	
	@DeleteMapping("/satellite_owner/{satelliteOwnerId}")
	public Map<String,String> deleteSatelliteOwnerById(@PathVariable Long satelliteOwnerId) {
		log.info("Deleting satellite owner with ID={}",satelliteOwnerId);
		
		satCatService.deleteSatelliteOwnerById(satelliteOwnerId);
		
		return Map.of("message", "Deletion of satellite owner with ID=" + satelliteOwnerId + " was successful.");
	}
	
	@PutMapping("satellite/{satelliteId}/add_mission/{missionId}")
	public SatelliteData addMissionToSatellite(@PathVariable Long satelliteId, @PathVariable Long missionId) {
		log.info("Adding mission with ID={} to satellite with ID={}",missionId, satelliteId);
		
		return satCatService.addMissionToSatellite(satelliteId, missionId);
	}
	
	@PutMapping("satellite/{satelliteId}/remove_mission/{missionId}")
	public SatelliteData removeMissionFromSatellite(@PathVariable Long satelliteId, @PathVariable Long missionId) {
		log.info("Removing mission with ID={} from satellite with ID={}",missionId, satelliteId);
		
		return satCatService.removeMissionFromSatellite(satelliteId, missionId);
	}
	
	@GetMapping("satellite/mission/{missionId}")
	public List<SatelliteData> getAllSatellitesForSpecificMission(@PathVariable Long missionId) {
		log.info("Retrieving all satellites that are part of the mission with ID={}",missionId);
		
		return satCatService.retrieveSatellitesForMission(missionId);
	}
}
