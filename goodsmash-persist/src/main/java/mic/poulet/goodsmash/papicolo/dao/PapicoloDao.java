package mic.poulet.goodsmash.papicolo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import mic.poulet.goodsmash.papicolo.model.Question;

public interface PapicoloDao extends JpaRepository<Question, Long> {

}
