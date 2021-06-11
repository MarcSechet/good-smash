package mic.poulet.goodsmash.smash;

import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.smash.dao.TierlistDao;
import mic.poulet.goodsmash.smash.model.tierlist.Tierlist;

@AllArgsConstructor
@Service
public class TierlistService {

	private final TierlistDao tierlistDao;

	public Optional<Tierlist> findById(Long tierlistId) {
		return tierlistDao.findById(tierlistId);
	}

	public Tierlist save(Tierlist tierlist) {
		return tierlistDao.save(tierlist);
	}

	public Tierlist update(Tierlist tierlist) {
		// TODO : put pojo here for update
		return tierlistDao.save(tierlist);
	}

	public void delete(Tierlist tierlist) {
		tierlistDao.delete(tierlist);
	}


}
