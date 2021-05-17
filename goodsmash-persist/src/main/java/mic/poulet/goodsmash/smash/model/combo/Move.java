package mic.poulet.goodsmash.smash.model.combo;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Move {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ElementCollection
	private List<String> inputs;

	protected Move() {
	}

	public Move(List<String> inputs) {
		this.inputs = inputs;
	}
}
