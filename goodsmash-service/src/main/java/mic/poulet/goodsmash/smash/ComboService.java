package mic.poulet.goodsmash.smash;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.smash.dao.ComboDao;
import mic.poulet.goodsmash.smash.model.combo.Combo;

@AllArgsConstructor
@Service
public class ComboService {

	private final ComboDao comboDao;

	public Optional<Combo> findById(Long comboId) {
		return comboDao.findById(comboId);
	}

	public Combo save(Combo combo) {
		return comboDao.save(combo);
	}

	public Combo update(Combo combo) {
		// TODO : put pojo here for update
		return comboDao.save(combo);
	}

	public void delete(Combo combo) {
		comboDao.delete(combo);
	}
}
