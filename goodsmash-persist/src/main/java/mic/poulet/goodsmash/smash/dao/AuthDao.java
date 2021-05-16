package mic.poulet.goodsmash.smash.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import mic.poulet.goodsmash.smash.model.auth.SmashUser;

public interface AuthDao extends JpaRepository<SmashUser, Long> {
    SmashUser findByEmail(String email);
}
