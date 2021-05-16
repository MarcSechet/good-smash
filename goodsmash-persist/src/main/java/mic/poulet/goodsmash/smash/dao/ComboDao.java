package mic.poulet.goodsmash.smash.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import mic.poulet.goodsmash.smash.model.combo.Combo;

public interface ComboDao extends JpaRepository<Combo, Long> {

}
