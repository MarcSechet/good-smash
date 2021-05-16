package mic.poulet.goodsmash.smash.model.tierlist;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"name", "character_id"})
})
public class Tierlist {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String description;

	@OneToMany(targetEntity = Tier.class, cascade = CascadeType.ALL,
			orphanRemoval = true)
	@OrderBy("rank ASC")
	private List<Tier> tiers;

	@ElementCollection
	private List<Long> unusedCharacterIds;
}
