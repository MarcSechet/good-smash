package mic.poulet.goodsmash.smash.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import mic.poulet.goodsmash.smash.model.move.BestMove;

public interface BestMoveDao extends JpaRepository<BestMove, Long> {

}
