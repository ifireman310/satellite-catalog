package catalog.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class SatelliteOwner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long satelliteOwnerId;
	private String company;
	private String address;
	private String email;
	private String phone;
	
	// This implements a one to many relationship between satelliteOwner and satellites, meaning one owner can have many satellites
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "satelliteOwner",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Satellite> satellites = new HashSet<>();
	
}
