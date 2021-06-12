package mic.poulet.goodsmash.controller.smash;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.exceptions.InvalidDataException;
import mic.poulet.goodsmash.smash.CharacterService;
import mic.poulet.goodsmash.smash.dao.ImageDao;
import mic.poulet.goodsmash.smash.model.ImageType;
import mic.poulet.goodsmash.smash.model.MyImage;
import mic.poulet.goodsmash.smash.model.character.Character;
import mic.poulet.goodsmash.smash.model.character.CharacterWeight;
import mic.poulet.goodsmash.spec.api.CharactersApi;
import mic.poulet.goodsmash.spec.model.CharacterDto;

@AllArgsConstructor
@Controller
@RequestMapping("${api-smash.base-path:}")
public class CharacterController implements CharactersApi {

	private final CharacterService characterService;
	private final ImageDao imageDao;
	private final ModelMapper mapper;

	private CharacterDto toDto(Character character) {
		CharacterDto characterDto = mapper.map(character, CharacterDto.class);
		characterDto.setImage(imageDao.findByCharacterIdAndType(character.getId(), ImageType.IMAGE).map(MyImage::getData).orElse(null));
		characterDto.setIcon(imageDao.findByCharacterIdAndType(character.getId(), ImageType.ICON).map(MyImage::getData).orElse(null));
		return characterDto;
	}

	private CharacterDto toDtoWithoutImage(Character character) {
		return mapper.map(character, CharacterDto.class);
	}

	@Override
	public ResponseEntity<List<CharacterDto>> getAllCharacters() {
		List<CharacterDto> characters = characterService.getAll().stream()
				.map(this::toDtoWithoutImage)
				.collect(Collectors.toList());

		return CollectionUtils.isEmpty(characters) ?
				ResponseEntity.noContent().build() :
				ResponseEntity.ok(characters);
	}

	@Override
	public ResponseEntity<CharacterDto> createCharacter(CharacterDto data, MultipartFile image, MultipartFile icon) {
		Character character = mapper.map(data, Character.class);
		character = characterService.save(character);
		try {
			if (image != null) {
				imageDao.save(new MyImage(image.getBytes(), character.getId(), ImageType.IMAGE));
			}
			if (icon != null) {
				imageDao.save(new MyImage(icon.getBytes(), character.getId(), ImageType.ICON));
			}
		} catch (IOException e) {
			throw new InvalidDataException(e.getMessage());
		}
		return ResponseEntity.ok(toDto(character));
	}

	@Override
	public ResponseEntity<CharacterDto> getCharacterByName(String name) {
		Character character = characterService.findByName(name).orElseThrow();
		return ResponseEntity.ok(toDto(character));
	}


	@Override
	public ResponseEntity<CharacterDto> updateByName(String name, CharacterDto data, MultipartFile image, MultipartFile icon) {
		Character character = characterService.findByName(name).orElseThrow();

		try {
			character.setCharacterWeight(Optional.ofNullable(data.getCharacterWeight())
					.map(characterWeightDto -> CharacterWeight.valueOf(characterWeightDto.name()))
					.orElse(null));
			character.setFloaty(Optional.ofNullable(data.getIsFloaty()).orElse(false));
			character.setName(data.getName());
			character.setTier(data.getTier());
			character.setAdditionalFilters(data.getAdditionalFilters());
			character.setSkillRating(data.getSkillRating());
			if (image != null) { // TODO : add some kind of boolean to avoid transmitting the images everytime we do an update
				imageDao.save(new MyImage(image.getBytes(), character.getId(), ImageType.IMAGE));
			}
			if (icon != null) {
				imageDao.save(new MyImage(icon.getBytes(), character.getId(), ImageType.ICON));
			}
		} catch (IOException e) {
			throw new InvalidDataException(e.getMessage());
		}
		character = characterService.update(character);
		return ResponseEntity.ok(toDto(character));
	}

	@Override
	public ResponseEntity<Void> deleteByName(String name) {
		Character character = characterService.findByName(name).orElseThrow();
		characterService.delete(character);
		return ResponseEntity.ok().build();
	}
}
