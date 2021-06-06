package mic.poulet.goodsmash.smash.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mic.poulet.goodsmash.smash.model.auth.SmashUser;

public interface AuthDao extends JpaRepository<SmashUser, Long> {
    Optional<SmashUser> findByEmail(String email);
}
