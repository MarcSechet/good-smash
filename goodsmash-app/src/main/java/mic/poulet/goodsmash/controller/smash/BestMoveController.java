package mic.poulet.goodsmash.controller.smash;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.smash.BestMoveService;
import mic.poulet.goodsmash.smash.CharacterService;
import mic.poulet.goodsmash.smash.model.character.Character;
import mic.poulet.goodsmash.smash.model.move.BestMove;
import mic.poulet.goodsmash.spec.api.BestMovesApi;
import mic.poulet.goodsmash.spec.model.BestMoveDto;

@AllArgsConstructor
@Controller
@RequestMapping("${api-smash.base-path:}")
public class BestMoveController implements BestMovesApi {

	private final BestMoveService bestMoveService;
	private final CharacterService characterService;
	private final ModelMapper mapper;

	private BestMoveDto toDto(BestMove bestMove) {
		return mapper.map(bestMove, BestMoveDto.class);
	}

	@Override
	public ResponseEntity<List<BestMoveDto>> getBestMoves(Long characterId) {
		Character character = characterService.findById(characterId).orElseThrow();

		List<BestMoveDto> bestMoveDtos = character.getBestMoves().stream()
				.map(this::toDto)
				.collect(Collectors.toList());

		return ResponseEntity.ok(bestMoveDtos);
	}

	@Override
	public ResponseEntity<BestMoveDto> createBestMove(Long characterId, BestMoveDto bestMoveDto) {
		BestMove bestMove = mapper.map(bestMoveDto, BestMove.class);
		Character character = characterService.findById(characterId).orElseThrow();

		character.getBestMoves().add(bestMove);
		characterService.save(character);

		return ResponseEntity.ok(toDto(character.getBestMoves().get(character.getBestMoves().size() - 1))); // We get the last elem bevause it is the one saved in the db and thus it has an id
	}

	@Override
	public ResponseEntity<BestMoveDto> getBestMoveById(Long bestMoveId) {
		BestMove bestMove = bestMoveService.findById(bestMoveId).orElseThrow();

		return ResponseEntity.ok(toDto(bestMove));
	}

	@Override
	public ResponseEntity<BestMoveDto> updateBestMoveById(Long bestMoveId, BestMoveDto informationDto) {
		BestMove bestMove = bestMoveService.findById(bestMoveId).orElseThrow();

		bestMove.setDescription(informationDto.getDescription());
		bestMove.setName(informationDto.getName());
		bestMove.setVideoUrl(informationDto.getVideoUrl());

		return ResponseEntity.ok(toDto(bestMoveService.update(bestMove)));
	}

	@Override
	public ResponseEntity<Void> deleteBestMoveById(Long informationId) {
		BestMove bestMove = bestMoveService.findById(informationId).orElseThrow();

		bestMoveService.delete(bestMove);

		return ResponseEntity.ok().build();
	}

}
