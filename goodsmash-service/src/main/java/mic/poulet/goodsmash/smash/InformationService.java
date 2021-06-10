package mic.poulet.goodsmash.smash;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.smash.dao.CharacterDao;
import mic.poulet.goodsmash.smash.dao.InformationDao;
import mic.poulet.goodsmash.smash.model.character.Character;
import mic.poulet.goodsmash.smash.model.information.Information;
import mic.poulet.goodsmash.smash.model.information.InformationType;

@AllArgsConstructor
@Service
public class InformationService {

	private final InformationDao informationDao;
	private final CharacterDao characterDao;

	public Optional<Information> findById(Long informationId) {
		return informationDao.findById(informationId);
	}

	public List<Information> search(Character character, Long targetId, InformationType informationType) {
		Optional.ofNullable(targetId)
				.ifPresent(aLong -> character.getInformations()
						.removeIf(information -> !Objects.equals(information.getTargetId(), targetId)));
		Optional.ofNullable(informationType)
				.ifPresent(aLong -> character.getInformations()
						.removeIf(information -> !Objects.equals(information.getInformationType(), informationType)));
		return character.getInformations();
	}

	public Information save(Information information) {
		return informationDao.save(information);
	}

	public Information update(Information information) {
		// TODO : put pojo here for update
		return informationDao.save(information);
	}

	public void delete(Information information) {
		informationDao.delete(information);
	}
}
