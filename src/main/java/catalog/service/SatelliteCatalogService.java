package catalog.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import catalog.controller.model.SatelliteOwnerData;
import catalog.controller.model.SatelliteOwnerData.MissionData;
import catalog.controller.model.SatelliteOwnerData.SatelliteData;
import catalog.dao.MissionDao;
import catalog.dao.SatelliteDao;
import catalog.dao.SatelliteOwnerDao;
import catalog.entity.Mission;
import catalog.entity.Satellite;
import catalog.entity.SatelliteOwner;


@Service
public class SatelliteCatalogService {

	@Autowired
	SatelliteDao satelliteDao;
	
	@Autowired
	SatelliteOwnerDao satelliteOwnerDao;
	
	@Autowired
	MissionDao missionDao;
	
	
	// GET ALL SATELLITES
	@Transactional(readOnly = true)
	public List<SatelliteData> retrieveAllSatellites() {
		List<SatelliteData> result = new LinkedList<>();
		List<Satellite> satellites = satelliteDao.findAll();
		
		for (Satellite satellite : satellites) {
			SatelliteData sd = new SatelliteData(satellite);
			result.add(sd);
		}
		return result;
	}
	
	// GET ALL SATELLITE OWNERS
	@Transactional(readOnly = true)
	public List<SatelliteOwnerData> retrieveAllSatelliteOwners() {
		List<SatelliteOwnerData> result =  new LinkedList<>();
		List<SatelliteOwner> satelliteOwners = satelliteOwnerDao.findAll();
		
		for (SatelliteOwner satelliteOwner : satelliteOwners) {
			SatelliteOwnerData sod = new SatelliteOwnerData(satelliteOwner);
			result.add(sod);
		}
		return result;
	}
	
	// GET ALL MISSIONS
	@Transactional(readOnly = true)
	public List<MissionData> retrieveAllMissions() {
		List<MissionData> result = new LinkedList<>();
		List<Mission> missions = missionDao.findAll();
		
		for (Mission mission : missions) {
			MissionData md = new MissionData(mission);
			result.add(md);
		}
		return result;
	}
	
	private SatelliteOwner findOrCreateSatelliteOwner(Long satelliteOwnerId) {

		SatelliteOwner satelliteOwner;

		if (Objects.isNull(satelliteOwnerId)) {
			satelliteOwner = new SatelliteOwner();
		} else {
			satelliteOwner = findSatelliteOwnerById(satelliteOwnerId);
		}
		return satelliteOwner;
	}
	
	private Satellite findOrCreateSatellite(Long satelliteId) {

		Satellite satellite;

		if (Objects.isNull(satelliteId)) {
			satellite = new Satellite();
		} else {
			satellite = findSatelliteById(satelliteId);
		}
		return satellite;
	}
	
	private Mission findOrCreateMission(Long missionId) {

		Mission mission;

		if (Objects.isNull(missionId)) {
			mission = new Mission();
		} else {
			mission = findMissionById(missionId);
		}
		return mission;
	}
	
	private SatelliteOwner findSatelliteOwnerById(Long satelliteOwnerId) {
		return satelliteOwnerDao.findById(satelliteOwnerId)
				.orElseThrow(() -> new NoSuchElementException("Satellite Owner with ID=" + satelliteOwnerId + " was not found."));
	}
	
	public SatelliteOwnerData retrieveSatelliteOwnerById(Long satelliteOwnerId) {
		return new SatelliteOwnerData(findSatelliteOwnerById(satelliteOwnerId));
	}
	
	private Satellite findSatelliteById(Long satelliteId) {
		return satelliteDao.findById(satelliteId)
				.orElseThrow(() -> new NoSuchElementException("Satellite with ID=" + satelliteId + " was not found."));
	}
	
	public SatelliteData retrieveSatelliteById(Long satelliteId) {
		return new SatelliteData(findSatelliteById(satelliteId));
	}
	
	private Mission findMissionById(Long missionId) {
		return missionDao.findById(missionId)
				.orElseThrow(() -> new NoSuchElementException("Mission with ID=" + missionId + " was not found."));
	}

	public MissionData retrieveMissionById(Long missionId) {
		return new MissionData(findMissionById(missionId));
	}
	
	// Save a new satellite owner
	@Transactional(readOnly = false)
	public SatelliteOwnerData saveSatelliteOwner(SatelliteOwnerData satelliteOwnerData) {
		
		Long satelliteOwnerId = satelliteOwnerData.getSatelliteOwnerId();
		SatelliteOwner satelliteOwner = findOrCreateSatelliteOwner(satelliteOwnerId);
		
		// If the satellite owner already exists (i.e. we're updating it), only update these fields (this way we don't lose the relationships)
		satelliteOwner.setAddress(satelliteOwnerData.getAddress());
		satelliteOwner.setCompany(satelliteOwnerData.getCompany());
		satelliteOwner.setEmail(satelliteOwnerData.getEmail());
		satelliteOwner.setPhone(satelliteOwnerData.getPhone());
		
		return new SatelliteOwnerData(satelliteOwnerDao.save(satelliteOwner));
	}

	// Save a new satellite
	@Transactional(readOnly = false)
	public SatelliteData saveSatellite(SatelliteData satelliteData, Long satelliteOwnerId) {
		
		SatelliteOwner satelliteOwner = findSatelliteOwnerById(satelliteOwnerId);
		Long satelliteId = satelliteData.getSatelliteId();
		Satellite satellite = findOrCreateSatellite(satelliteId);
		
		// If the satellite owner already exists (i.e. we're updating it), only update these fields (this way we don't lose the relationships)
		satellite.setActive(satelliteData.getActive());
		satellite.setMass(satelliteData.getMass());
		satellite.setMoveable(satelliteData.getMoveable());
		satellite.setNoradId(satelliteData.getNoradId());
		satellite.setSphericalRadius(satelliteData.getSphericalRadius());
		satellite.setTleLine1(satelliteData.getTleLine1());
		satellite.setTleLine2(satelliteData.getTleLine2());
		
		// This is necessary if it is a new satellite, if the satellite already exists (and we're just updating fields) then it is redundant
		satellite.setSatelliteOwner(satelliteOwner);
		satelliteOwner.getSatellites().add(satellite);
		
		return new SatelliteData(satelliteDao.save(satellite));
	}

	// Save a new satellite
	@Transactional(readOnly = false)
	public SatelliteData addMissionToSatellite(Long satelliteId, Long MissionId) {
		
		Satellite satellite = findSatelliteById(satelliteId);
		Mission mission = findMissionById(MissionId);
		satellite.getMissions().add(mission);
		mission.getSatellites().add(satellite);
		
		missionDao.save(mission);
		
		return new SatelliteData(satelliteDao.save(satellite));
	}
	
	public SatelliteData removeMissionFromSatellite(Long satelliteId, Long MissionId) {
		
		Satellite satellite = findSatelliteById(satelliteId);
		Mission mission = findMissionById(MissionId);
		
		satellite.getMissions().remove(mission);
		mission.getSatellites().remove(satellite);
		missionDao.save(mission);
		
		return new SatelliteData(satelliteDao.save(satellite));
	}
	
	// Save a new mission
	public MissionData saveMission(MissionData missionData) {
		Mission mission = findOrCreateMission(missionData.getMissionId());
		
		mission.setName(missionData.getName());
		
		return new MissionData(missionDao.save(mission));
	}
	
	// Add satellite to mission
	public Satellite addSatelliteToMission(Long satelliteId, Long missionId) {
		Mission mission = findMissionById(missionId);
		Satellite satellite = findSatelliteById(satelliteId);
		
		satellite.getMissions().add(mission);
		mission.getSatellites().add(satellite);
		
		return satelliteDao.save(satellite);	
	}

	public List<SatelliteData> retrieveAllSatellitesFromOwner(Long satelliteOwnerId) {
		SatelliteOwnerData satelliteOwnerData = retrieveSatelliteOwnerById(satelliteOwnerId);
		List<SatelliteData> result = new LinkedList<>();
		
		for (SatelliteData satData : satelliteOwnerData.getSatellites()) {
			result.add(satData);
		}
		return result;
	}

	@Transactional(readOnly = false)
	public void deleteSatelliteById(Long satelliteId) {
		Satellite satellite = findSatelliteById(satelliteId);
		satelliteDao.delete(satellite);
	}

	@Transactional(readOnly = false)
	public void deleteMissionById(Long missionId) {
		Mission mission = findMissionById(missionId);
		List<Long> satIds = new LinkedList<>();
		
		for (Satellite satellite : mission.getSatellites()) {
			satIds.add(satellite.getSatelliteId());
		}
		
		for (Long satelliteId : satIds) {
			removeMissionFromSatellite(satelliteId, missionId);
		}
		
		missionDao.delete(mission);
	}

	@Transactional(readOnly = false)
	public void deleteSatelliteOwnerById(Long satelliteOwnerId) {
		SatelliteOwner satelliteOwner = findSatelliteOwnerById(satelliteOwnerId);
		satelliteOwnerDao.delete(satelliteOwner);
	}
	
	public List<SatelliteData> retrieveSatellitesForMission(Long missionId) {
		Mission mission = findMissionById(missionId);
		List<SatelliteData> result = new LinkedList<>();
		
		for (Satellite satellite : mission.getSatellites()) {
			SatelliteData sd = new SatelliteData(satellite);
			result.add(sd);
		}
		return result;
	}
}
