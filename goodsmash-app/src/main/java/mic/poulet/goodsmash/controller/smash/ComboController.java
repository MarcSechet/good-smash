package mic.poulet.goodsmash.controller.smash;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.smash.CharacterService;
import mic.poulet.goodsmash.smash.ComboService;
import mic.poulet.goodsmash.smash.model.character.Character;
import mic.poulet.goodsmash.smash.model.combo.Combo;
import mic.poulet.goodsmash.smash.model.combo.Move;
import mic.poulet.goodsmash.spec.api.CombosApi;
import mic.poulet.goodsmash.spec.model.ComboDto;

@AllArgsConstructor
@RestController
@RequestMapping("${api-smash.base-path:}")
public class ComboController implements CombosApi {

	private final ComboService comboService;
	private final CharacterService characterService;
	private final ModelMapper mapper;

	private ComboDto toDto(Combo combo) {
		return mapper.map(combo, ComboDto.class);
	}

	@Override
	public ResponseEntity<List<ComboDto>> getCombos(Long characterId) {
		Character character = characterService.findById(characterId).orElseThrow();

		List<ComboDto> comboDtos = character.getCombos().stream()
				.map(this::toDto)
				.collect(Collectors.toList());

		return ResponseEntity.ok(comboDtos);
	}

	@Override
	public ResponseEntity<ComboDto> createCombo(Long characterId, ComboDto comboDto) {
		Combo combo = mapper.map(comboDto, Combo.class);
		Character character = characterService.findById(characterId).orElseThrow();

		character.getCombos().add(combo);
		characterService.save(character);

		return ResponseEntity.ok(toDto(character.getCombos().get(character.getCombos().size() - 1))); // We get the last elem bevause it is the one saved in the db and thus it has an id
	}

	@Override
	public ResponseEntity<ComboDto> getComboById(Long comboId) {
		Combo combo = comboService.findById(comboId).orElseThrow();

		return ResponseEntity.ok(toDto(combo));
	}

	@Override
	public ResponseEntity<ComboDto> updateComboById(Long comboId, ComboDto comboDto) {
		Combo combo = comboService.findById(comboId).orElseThrow();

		combo.setDescription(comboDto.getDescription());
		combo.setName(comboDto.getName());
		combo.setVideoUrl(comboDto.getVideoUrl());
		combo.setMinPercent(comboDto.getMinPercent());
		combo.setMaxPercent(comboDto.getMaxPercent());

		// We need to clear and then add all or else jpa loses track of the children entities
		// jpaSystemException: A collection with cascade=\"all-delete-orphan\" was no longer referenced by the owning entity instance
		combo.getMoves().clear();
		comboService.update(combo);
		combo.getMoves().addAll(Optional.ofNullable(comboDto.getMoves()).stream().flatMap(Collection::stream)
				.map(moveDto -> mapper.map(moveDto, Move.class))
				.collect(Collectors.toList()));
		// Not needed by this list because it's @ElementCollection
		combo.setAdditionalFilters(Optional.ofNullable(comboDto.getAdditionalFilters()).stream().flatMap(Collection::stream).collect(Collectors.toList()));

		return ResponseEntity.ok(toDto(comboService.update(combo)));
	}

	@Override
	public ResponseEntity<Void> deleteComboById(Long comboId) {
		Combo combo = comboService.findById(comboId).orElseThrow();

		comboService.delete(combo);

		return ResponseEntity.ok().build();
	}

}
