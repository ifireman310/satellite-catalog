package catalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import catalog.entity.Mission;

public interface MissionDao extends JpaRepository<Mission, Long> {

}
