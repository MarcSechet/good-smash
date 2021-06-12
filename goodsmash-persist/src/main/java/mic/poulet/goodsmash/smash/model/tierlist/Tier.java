package mic.poulet.goodsmash.smash.model.tierlist;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Tier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer rank;

	@Column(unique = true)
	private String name;

	@Column(columnDefinition = "varchar(10) default '#000000'")
	private String color;

	@ElementCollection
	private List<Long> characterIds = new ArrayList<>();
}
