package mic.poulet.goodsmash.smash;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.smash.dao.CharacterDao;
import mic.poulet.goodsmash.smash.model.character.Character;

@AllArgsConstructor
@Service
public class CharacterService {

	private final CharacterDao characterDao;

	public Optional<Character> findById(Long id) {
		return characterDao.findById(id);
	}

	public Optional<Character> findByName(String name) {
		return characterDao.findByName(name);
	}

	public List<Character> getAll() {
		return characterDao.findAllByOrderByNameAsc();
	}

	public Character save(Character character) {
		return characterDao.save(character);
	}

	public Character update(Character character) {
		// TODO : put pojo here for update
		return characterDao.save(character);
	}

	public void delete(Character character) {
		characterDao.delete(character);
	}
}
