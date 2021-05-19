package mic.poulet.goodsmash.smash.model.combo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"name", "character_id"})
})
public class Combo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String videoUrl;

	private String description;

	private Integer minPercent;

	private Integer maxPercent;

	@OneToMany(targetEntity = Move.class, cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<Move> moves;

	@ElementCollection
	private List<String> additionalFilters; // ex : deep breath for WFT, luma, nana, etc...

	protected Combo() {
	}

}
