package mic.poulet.goodsmash.smash.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import mic.poulet.goodsmash.smash.model.confirm.Confirm;


public interface ConfirmDao extends JpaRepository<Confirm, Long> {

}
