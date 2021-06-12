package mic.poulet.goodsmash.smash.model.confirm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class Confirm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 1200)
	private String name;

	private String description;

	private String comment;

	@OneToMany(targetEntity = ConfirmDetails.class, cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<ConfirmDetails> confirmDetails = new ArrayList<>();

	protected Confirm() {
	}
}
