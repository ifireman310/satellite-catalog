package catalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import catalog.entity.SatelliteOwner;

public interface SatelliteOwnerDao extends JpaRepository<SatelliteOwner, Long> {

}
