package mic.poulet.goodsmash.smash;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.smash.dao.AuthDao;
import mic.poulet.goodsmash.smash.model.auth.SmashUser;

@AllArgsConstructor
@Service
public class AuthService {

	private final AuthDao authDao;

	public Optional<SmashUser> findByEmail(String email) {
		return authDao.findByEmail(email);
	}
	public SmashUser save(SmashUser user) {
		return authDao.save(user);
	}
}
