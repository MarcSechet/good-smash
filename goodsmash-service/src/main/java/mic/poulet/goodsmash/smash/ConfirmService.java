package mic.poulet.goodsmash.smash;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.smash.dao.ConfirmDao;
import mic.poulet.goodsmash.smash.model.confirm.Confirm;

@AllArgsConstructor
@Service
public class ConfirmService {

	private final ConfirmDao confirmDao;

	public Optional<Confirm> findById(Long confirmId) {
		return confirmDao.findById(confirmId);
	}

	public Confirm save(Confirm confirm) {
		return confirmDao.save(confirm);
	}

	public Confirm update(Confirm confirm) {
		// TODO : put pojo here for update
		return confirmDao.save(confirm);
	}

	public void delete(Confirm confirm) {
		confirmDao.delete(confirm);
	}
}
