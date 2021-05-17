package mic.poulet.goodsmash.pepites.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Playlist {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String playlistId;

	@Valid
	@OneToMany(targetEntity = Timecode.class, cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<Timecode> timecodes = new ArrayList<>();

	private String uploaderName;

	private Boolean isPublic;
}
