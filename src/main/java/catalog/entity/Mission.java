package catalog.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Mission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long missionId;
	private String name;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "missions", cascade = CascadeType.PERSIST)
	private Set<Satellite> satellites = new HashSet<>();
}
