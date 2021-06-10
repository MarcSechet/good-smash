package mic.poulet.goodsmash.smash.model.information;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import mic.poulet.goodsmash.smash.model.character.Character;

@Entity
@Getter
@Setter
public class Information {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private InformationType informationType;

	private Long targetId; // optional, used for advantage & disadvantage

	@Size(max = 1200)
	private String description;

	protected Information() {
	}
}
