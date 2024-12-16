package catalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("/satellite_owner/{satelliteOwnerId}")
	public SatelliteOwnerData getSatelliteOwnerById(@PathVariable Long satelliteOwnerId) {
		log.info("Retrieving satellite owner with ID=", satelliteOwnerId);
		
		return satCatService.retrieveSatelliteOwnerById(satelliteOwnerId);
	}
	
	@PostMapping("/satellite_owner/{satelliteOwnerId}/satellite")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SatelliteData insertSatellite(@PathVariable Long satelliteOwnerId, @RequestBody SatelliteData satelliteData) {
		log.info("Creating satellite {} for satellite owner with ID={}", satelliteData, satelliteOwnerId);
		return satCatService.saveSatellite(satelliteData, satelliteOwnerId);
	}
	
	@PostMapping("/mission")
	public MissionData insertMission(@RequestBody MissionData missionData) {
		log.info("Creating mission {}", missionData);
		return satCatService.saveMission(missionData);
	}
}
