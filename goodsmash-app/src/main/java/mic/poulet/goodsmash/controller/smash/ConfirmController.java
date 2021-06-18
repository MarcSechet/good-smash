package mic.poulet.goodsmash.controller.smash;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.exceptions.InvalidDataException;
import mic.poulet.goodsmash.smash.CharacterService;
import mic.poulet.goodsmash.smash.ConfirmService;
import mic.poulet.goodsmash.smash.model.character.Character;
import mic.poulet.goodsmash.smash.model.confirm.Confirm;
import mic.poulet.goodsmash.smash.model.confirm.ConfirmDetails;
import mic.poulet.goodsmash.spec.api.ConfirmsApi;
import mic.poulet.goodsmash.spec.model.ConfirmDetailsDto;
import mic.poulet.goodsmash.spec.model.ConfirmDto;

@AllArgsConstructor
@RestController
@RequestMapping("${api-smash.base-path:}")
public class ConfirmController implements ConfirmsApi {

	private final ConfirmService confirmService;
	private final CharacterService characterService;
	private final ModelMapper mapper;

	private ConfirmDto toDto(Confirm confirm) {
		return mapper.map(confirm, ConfirmDto.class);
	}

	@Override
	public ResponseEntity<List<ConfirmDto>> getConfirms(Long characterId) {
		Character character = characterService.findById(characterId).orElseThrow();

		List<ConfirmDto> confirmDtos = character.getConfirms().stream()
				.map(this::toDto)
				.collect(Collectors.toList());

		return ResponseEntity.ok(confirmDtos);
	}

	@Override
	public ResponseEntity<ConfirmDto> createConfirm(Long characterId, ConfirmDto confirmDto) {
		Confirm confirm = mapper.map(confirmDto, Confirm.class);
		Character character = characterService.findById(characterId).orElseThrow();

		character.getConfirms().add(confirm);
		characterService.save(character);

		return ResponseEntity.ok(toDto(character.getConfirms().get(character.getConfirms().size() - 1))); // We get the last elem bevause it is the one saved in the db and thus it has an id
	}

	@Override
	public ResponseEntity<ConfirmDto> getConfirmById(Long confirmId) {
		Confirm confirm = confirmService.findById(confirmId).orElseThrow();

		return ResponseEntity.ok(toDto(confirm));
	}

	@Override
	public ResponseEntity<ConfirmDto> updateConfirmById(Long confirmId, ConfirmDto confirmDto) {
		Confirm confirm = confirmService.findById(confirmId).orElseThrow();

		confirm.setDescription(confirmDto.getDescription());
		confirm.setName(confirmDto.getName());
		confirm.setComment(confirmDto.getComment());

		// We need to clear and then add all or else jpa loses track of the children entities
		// jpaSystemException: A collection with cascade=\"all-delete-orphan\" was no longer referenced by the owning entity instance
		/*confirm.getConfirmDetails().clear();
		confirmService.update(confirm);
		confirm.getConfirmDetails().addAll(Optional.ofNullable(confirmDto.getConfirmDetailsDtos()).stream().flatMap(Collection::stream)
				.map(confirmDetailsDto -> mapper.map(confirmDetailsDto, ConfirmDetails.class))
				.collect(Collectors.toList()));*/

		return ResponseEntity.ok(toDto(confirmService.update(confirm)));
	}

	@Override
	public ResponseEntity<Void> deleteConfirmById(Long confirmId) {
		Confirm confirm = confirmService.findById(confirmId).orElseThrow();

		confirmService.delete(confirm);

		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<ConfirmDto> createConfirmDetails(Long confirmId, ConfirmDetailsDto confirmDetailsDto) {
		Confirm confirm = confirmService.findById(confirmId).orElseThrow();
		if (confirm.getConfirmDetails().stream().anyMatch(cd -> cd.getTargetId().equals(confirmDetailsDto.getTargetId())))
			throw new InvalidDataException(String.format("Duplicate target id : %s", confirmDetailsDto.getTargetId()));
		ConfirmDetails confirmDetails = mapper.map(confirmDetailsDto, ConfirmDetails.class);

		confirm.getConfirmDetails().add(confirmDetails);
		confirmService.save(confirm);

		return ResponseEntity.ok(toDto(confirm));
	}

	@Override
	public ResponseEntity<ConfirmDto> updateConfirmDetailsById(Long confirmId, Long confirmDetailsId, ConfirmDetailsDto confirmDetailsDto) {
		Confirm confirm = confirmService.findById(confirmId).orElseThrow();
		ConfirmDetails confirmDetails = confirm.getConfirmDetails().stream().filter(cd -> Objects.equals(cd.getId(), confirmDetailsId)).findAny().orElseThrow();

		confirmDetails.setComment(confirmDetailsDto.getComment());
		confirmDetails.setKillPercentRage0(confirmDetailsDto.getKillPercentRage0());
		confirmDetails.setKillPercentRage50(confirmDetailsDto.getKillPercentRage50());
		confirmDetails.setKillPercentRage100(confirmDetailsDto.getKillPercentRage100());
		confirmDetails.setKillPercentRage150(confirmDetailsDto.getKillPercentRage150());
		//confirmDetails.setTargetId(confirmDetailsDto.getTargetId());
		confirmService.save(confirm);

		return ResponseEntity.ok(toDto(confirm));
	}

	@Override
	public ResponseEntity<Void> deleteConfirmDetailsById(Long confirmId, Long confirmDetailsId) {
		Confirm confirm = confirmService.findById(confirmId).orElseThrow();
		boolean deleted = confirm.getConfirmDetails().removeIf(cd -> Objects.equals(cd.getId(), confirmDetailsId));

		if (!deleted)
			throw new NoSuchElementException("No value present");
		confirmService.save(confirm);

		return ResponseEntity.ok().build();
	}
}
