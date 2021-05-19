package mic.poulet.goodsmash.smash.model.move;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"name", "character_id"})
})
public class BestMove {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String videoUrl; // can be a youtube link or a gif

	@Size(max = 1200)
	private String description;

	protected BestMove() {
	}
}
