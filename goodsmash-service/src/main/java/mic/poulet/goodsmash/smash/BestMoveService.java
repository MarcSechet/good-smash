package mic.poulet.goodsmash.smash;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.smash.dao.BestMoveDao;
import mic.poulet.goodsmash.smash.model.move.BestMove;

@AllArgsConstructor
@Service
public class BestMoveService {

	private final BestMoveDao bestMoveDao;

	public Optional<BestMove> findById(Long informationId) {
		return bestMoveDao.findById(informationId);
	}

	public BestMove save(BestMove bestMove) {
		return bestMoveDao.save(bestMove);
	}

	public BestMove update(BestMove bestMove) {
		// TODO : put pojo here for update
		return bestMoveDao.save(bestMove);
	}

	public void delete(BestMove bestMove) {
		bestMoveDao.delete(bestMove);
	}
}
