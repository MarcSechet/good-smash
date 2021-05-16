package mic.poulet.goodsmash.smash.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mic.poulet.goodsmash.smash.model.character.Character;

public interface CharacterDao extends JpaRepository<Character, Long> {

    Character findByName(String name);

    List<Character> findAllByOrderByNameAsc();
}
