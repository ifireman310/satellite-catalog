package catalog.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Satellite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long satelliteId;
	private String noradId;
	private String tleLine1;
	private String tleLine2;
	private BigDecimal mass;
	private BigDecimal sphericalRadius;
	private Boolean moveable;
	private Boolean active;
	
	// This implements a many to one relationship between satellites and satelliteOwner and , meaning one owner can have many satellites
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "satellite_owner_id")
	private SatelliteOwner satelliteOwner;
	
	// This implements a many to many relationship between missions and satellites, meaning a satellite can have many missions, and an individual mission can have many satellites
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "satellite_mission",
			joinColumns = @JoinColumn(name = "satellite_id"),
			inverseJoinColumns = @JoinColumn(name = "mission_id"))
	private Set<Mission> missions = new HashSet<>();
	
}
