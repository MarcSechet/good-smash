package mic.poulet.goodsmash.controller.smash;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.smash.CharacterService;
import mic.poulet.goodsmash.smash.TierlistService;
import mic.poulet.goodsmash.smash.model.character.Character;
import mic.poulet.goodsmash.smash.model.tierlist.Tier;
import mic.poulet.goodsmash.smash.model.tierlist.Tierlist;
import mic.poulet.goodsmash.spec.api.TierlistsApi;
import mic.poulet.goodsmash.spec.model.TierlistDto;

@AllArgsConstructor
@Controller
@RequestMapping("${api-smash.base-path:}")
public class TierlistController implements TierlistsApi {

	private final TierlistService tierlistService;
	private final CharacterService characterService;
	private final ModelMapper mapper;

	private TierlistDto toDto(Tierlist tierlist) {
		return mapper.map(tierlist, TierlistDto.class);
	}

	@Override
	public ResponseEntity<List<TierlistDto>> getTierlists(Long characterId) {
		Character character = characterService.findById(characterId).orElseThrow();

		List<TierlistDto> tierlistDtos = character.getTierlists().stream()
				.map(this::toDto)
				.collect(Collectors.toList());

		return ResponseEntity.ok(tierlistDtos);
	}

	@Override
	public ResponseEntity<TierlistDto> createTierlist(Long characterId, TierlistDto tierlistDto) {
		Tierlist tierlist = mapper.map(tierlistDto, Tierlist.class);
		Character character = characterService.findById(characterId).orElseThrow();

		character.getTierlists().add(tierlist);
		assignTierRanks(tierlist);
		characterService.save(character);

		return ResponseEntity.ok(toDto(character.getTierlists().get(character.getTierlists().size() - 1))); // We get the last elem bevause it is the one saved in the db and thus it has an id
	}

	@Override
	public ResponseEntity<TierlistDto> getTierlistById(Long tierlistId) {
		Tierlist tierlist = tierlistService.findById(tierlistId).orElseThrow();

		return ResponseEntity.ok(toDto(tierlist));
	}

	@Override
	public ResponseEntity<TierlistDto> updateTierlistById(Long tierlistId, TierlistDto tierlistDto) {
		Tierlist tierlist = tierlistService.findById(tierlistId).orElseThrow();

		tierlist.setDescription(tierlistDto.getDescription());
		tierlist.setName(tierlistDto.getName());

		// We need to clear and then add all or else jpa loses track of the children entities
		// jpaSystemException: A collection with cascade=\"all-delete-orphan\" was no longer referenced by the owning entity instance
		tierlist.getTiers().clear();
		tierlistService.update(tierlist);
		tierlist.getTiers().addAll(Optional.ofNullable(tierlistDto.getTiers()).stream().flatMap(Collection::stream)
				.map(tierDto -> mapper.map(tierDto, Tier.class))
				.collect(Collectors.toList()));

		// Not needed by this list because it's @ElementCollection
		tierlist.setUnusedCharacterIds(Optional.ofNullable(tierlistDto.getUnusedCharacterIds()).stream().flatMap(Collection::stream).collect(Collectors.toList()));
		assignTierRanks(tierlist);

		return ResponseEntity.ok(toDto(tierlistService.update(tierlist)));
	}

	@Override
	public ResponseEntity<Void> deleteTierlistById(Long tierlistId) {
		Tierlist tierlist = tierlistService.findById(tierlistId).orElseThrow();

		tierlistService.delete(tierlist);

		return ResponseEntity.ok().build();
	}

	/**
	 * Sets a rank to each tier in a tierlist
	 *
	 * @param tierlist, the tierlist
	 */
	private void assignTierRanks(Tierlist tierlist) {
		IntStream.range(0, tierlist.getTiers().size()).forEach(idx -> tierlist.getTiers().get(idx).setRank(idx));
	}

}
