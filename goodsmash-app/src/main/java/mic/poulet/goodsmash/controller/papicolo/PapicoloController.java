package mic.poulet.goodsmash.controller.papicolo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.PapicoloService;
import mic.poulet.goodsmash.papicolo.model.Category;
import mic.poulet.goodsmash.papicolo.model.Question;
import mic.poulet.goodsmash.papicolo.model.QuestionType;
import mic.poulet.goodsmash.spec.api.PapicoloApi;
import mic.poulet.goodsmash.spec.model.QuestionDto;

@AllArgsConstructor
@Controller
@RequestMapping("${api-smash.base-path:}")
public class PapicoloController implements PapicoloApi {

	private final PapicoloService papicoloService;
	private final ModelMapper mapper;

	private QuestionDto toDto(Question question) {
		return mapper.map(question, QuestionDto.class);
	}

	@Override
	public ResponseEntity<List<QuestionDto>> getAll() {
		List<Question> questions = papicoloService.getAll();
		return CollectionUtils.isEmpty(questions) ?
				ResponseEntity.noContent().build() :
				ResponseEntity.ok(questions.stream().map(this::toDto).collect(Collectors.toList()));
	}

	@Override
	public ResponseEntity<QuestionDto> create(QuestionDto questionDto) {
		Question question = papicoloService.create(mapper.map(questionDto, Question.class));
		return ResponseEntity.ok(toDto(question));
	}

	@Override
	public ResponseEntity<QuestionDto> getById(Long id) {
		Question question = papicoloService.findById(id).orElseThrow();
		return ResponseEntity.ok(toDto(question));
	}

	@Override
	public ResponseEntity<QuestionDto> updateById(Long id, QuestionDto questionDto) {
		Question question = papicoloService.findById(id).orElseThrow();

		// TODO : do we need other updates ? Check mobile app for that
		Optional.ofNullable(questionDto.getQuestion())
				.ifPresent(question::setQuestion);
		Optional.ofNullable(questionDto.getCategory())
				.map(categoryDto -> Category.valueOf(categoryDto.name()))
				.ifPresent(question::setCategory);
		Optional.ofNullable(questionDto.getQuestionType())
				.map(questionTypeDto -> QuestionType.valueOf(questionTypeDto.name()))
				.ifPresent(question::setQuestionType);
		Optional.ofNullable(questionDto.getNbPlayers())
				.ifPresent(question::setNbPlayers);
		Optional.ofNullable(questionDto.getQuestionFromPicolo())
				.ifPresent(question::setQuestionFromPicolo);

		question = papicoloService.update(question);
		return ResponseEntity.ok(toDto(question));
	}

	@Override
	public ResponseEntity<Void> deleteById(Long id) {
		Question question = papicoloService.findById(id).orElseThrow();
		papicoloService.delete(question);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> importAllQuestions() {
		papicoloService.importAllQuestionsFromCsv();
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> deleteChildById(Long id, Long childId) {
		Question question = papicoloService.findById(id).orElseThrow();
		papicoloService.deleteChildQuestion(question, childId);
		return ResponseEntity.ok().build();
	}
}
