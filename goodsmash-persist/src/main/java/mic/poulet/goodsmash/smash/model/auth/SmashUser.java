package mic.poulet.goodsmash.smash.model.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SmashUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String email;

	private boolean isApproved; // if true then the user can use other webservices

	public SmashUser() {
	}
}
