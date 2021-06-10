package mic.poulet.goodsmash.controller.smash;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.exceptions.InvalidDataException;
import mic.poulet.goodsmash.smash.CharacterService;
import mic.poulet.goodsmash.smash.InformationService;
import mic.poulet.goodsmash.smash.model.character.Character;
import mic.poulet.goodsmash.smash.model.information.Information;
import mic.poulet.goodsmash.smash.model.information.InformationType;
import mic.poulet.goodsmash.spec.api.InformationsApi;
import mic.poulet.goodsmash.spec.model.InformationDto;
import mic.poulet.goodsmash.spec.model.InformationTypeDto;

@AllArgsConstructor
@Controller
@RequestMapping("${api-smash.base-path:}")
public class InformationController implements InformationsApi {

	private final InformationService informationService;
	private final CharacterService characterService;
	private final ModelMapper mapper;

	private InformationDto toDto(Information information) {
		return mapper.map(information, InformationDto.class);
	}

	@Override
	public ResponseEntity<List<InformationDto>> searchInformations(Long characterId, Long targetId, InformationTypeDto informationTypeDto) {
		if (targetId == null && informationTypeDto == null) {
			throw new InvalidDataException("You must have at least of these fields not null to use this search : targetId, informationType");
		}

		Character character = characterService.findById(characterId).orElseThrow();
		InformationType informationTypeEntity = Optional.ofNullable(informationTypeDto).map(InformationTypeDto::toString).map(InformationType::valueOf).orElse(null);

		List<InformationDto> informationDtos = informationService.search(character, targetId, informationTypeEntity).stream()
				.map(this::toDto)
				.collect(Collectors.toList());

		return ResponseEntity.ok(informationDtos);
	}

	@Override
	public ResponseEntity<InformationDto> createInformation(Long characterId, InformationDto informationDto) {
		Information information = mapper.map(informationDto, Information.class);
		Character character = characterService.findById(characterId).orElseThrow();

		character.getInformations().add(information);
		characterService.save(character);

		return ResponseEntity.ok(toDto(character.getInformations().get(character.getInformations().size() - 1))); // We get the last elem bevause it is the one saved in the db and thus it has an id
	}

	@Override
	public ResponseEntity<InformationDto> getInformationById(Long informationId) {
		Information information = informationService.findById(informationId).orElseThrow();

		return ResponseEntity.ok(toDto(information));
	}
	@Override
	public ResponseEntity<InformationDto> updateInformationById(Long informationId, InformationDto informationDto) {
		Information information = informationService.findById(informationId).orElseThrow();

		information.setDescription(informationDto.getDescription());
		information.setTargetId(informationDto.getTargetId());
		information.setInformationType(Optional.ofNullable(informationDto.getInformationType()).map(InformationTypeDto::toString).map(InformationType::valueOf).orElse(null));

		return ResponseEntity.ok(toDto(informationService.update(information)));
	}

	@Override
	public ResponseEntity<Void> deleteInformationById(Long informationId) {
		Information information = informationService.findById(informationId).orElseThrow();

		informationService.delete(information);

		return ResponseEntity.ok().build();
	}

}
