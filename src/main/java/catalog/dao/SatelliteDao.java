package catalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import catalog.entity.Satellite;

public interface SatelliteDao extends JpaRepository<Satellite, Long> {

}
