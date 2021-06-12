package mic.poulet.goodsmash.papicolo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;

import lombok.AllArgsConstructor;
import mic.poulet.goodsmash.exceptions.ImportException;
import mic.poulet.goodsmash.papicolo.dao.PapicoloDao;
import mic.poulet.goodsmash.papicolo.model.Category;
import mic.poulet.goodsmash.papicolo.model.Question;
import mic.poulet.goodsmash.papicolo.model.QuestionType;

@AllArgsConstructor
@Service
public class PapicoloService {

	private final PapicoloDao papicoloDao;

	public Optional<Question> findById(Long id) {
		return papicoloDao.findById(id);
	}

	public List<Question> getAll() {

		return papicoloDao.findAll().stream()
				.filter(question -> StringUtils.isBlank(question.getParentKey()))
				.collect(Collectors.toList());
	}

	public Question create(Question question) {
		return papicoloDao.save(question);
	}

	public Question update(Question question) {
		// TODO : put pojo here for update
		return papicoloDao.save(question);
	}

	public void delete(Question question) {
		papicoloDao.delete(question);
	}

	public void deleteChildQuestion(Question questionParent, Long childId) {
		questionParent.getChildren().removeIf(question -> question.getId().equals(childId));
		papicoloDao.save(questionParent);
	}

	public void importAllQuestionsFromCsv() {
		try {
			papicoloDao.deleteAll();
			papicoloDao.saveAll(getQuestionsFromCsv(Category.NORMAL, "csv/rules_default-fr.csv"));
			papicoloDao.saveAll(getQuestionsFromCsv(Category.HOT, "csv/rules_hot-fr.csv"));
			papicoloDao.saveAll(getQuestionsFromCsv(Category.SILLY, "csv/rules_silly-fr.csv"));
			papicoloDao.saveAll(getQuestionsFromCsv(Category.BAR, "csv/rules_bar-fr.csv"));
		} catch (URISyntaxException | IOException e) {
			throw new ImportException(e.getMessage(), e.getCause());
		}
	}

	private List<Question> getQuestionsFromCsv(Category category, String path) throws URISyntaxException, IOException {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource("classpath:" + path);

		CSVReader csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()));
		List<String[]> csvLines = csvReader.readAll();

		ArrayList<Question> questions = new ArrayList<>();
		for (int i = 1; i < csvLines.size(); i++) { // 1 to skip the header
			Question question = createQuestionFromCsvLine(Arrays.asList(csvLines.get(i)), category);
			if (StringUtils.containsIgnoreCase(question.getQuestion(), "facebook")) {
				continue;
			} else if (StringUtils.isNotBlank(question.getParentKey())) {
				questions.get(questions.size() - 1).getChildren().add(question); // TODO : fix bug the children are saved as chielren and as question
			} else {
				questions.add(question);
			}
		}
		return questions;
	}

	private Question createQuestionFromCsvLine(List<String> fields, Category category) {
		if (fields.size() > 5)
			fields = parseLine(fields);
		return new Question()
				.setKey(fields.get(0))
				.setParentKey(fields.get(1))
				.setQuestion(fields.get(2))
				.setQuestionType(StringUtils.isAllBlank(fields.get(0), fields.get(1)) ?
						QuestionType.getFromQuestionType(Integer.parseInt(fields.get(3))) :
						Integer.parseInt(fields.get(3)) == 23 ? QuestionType.EVENT_WITH_CHILD_RIGHT_AFTER : QuestionType.EVENT)
				.setNbPlayers(Integer.parseInt(fields.get(4)))
				.setCategory(category)
				.setQuestionFromPicolo(true);
	}

	/**
	 * Used for parsing lines that have more than 5 fields (means that the question field contains commas)
	 *
	 * @param fields fields[0] : key
	 *               fields[1] : parent_key
	 *               fields[2 to fields.size() - 3 included] : text that can be over multiple fields if the question contains commas ","
	 *               fields[fields.size() - 2] : type
	 *               fields[fields.size() - 1] : nb_players
	 * @return a list with 5 fields correctly parsed
	 */
	private List<String> parseLine(List<String> fields) {
		List<String> parsedFields = new ArrayList<>();
		parsedFields.add(fields.get(0));
		parsedFields.add(fields.get(1));
		StringBuilder sb = new StringBuilder();
		for (int i = 2; i < fields.size() - 2; i++) {
			sb.append(fields.get(i));
		}
		parsedFields.add(sb.toString());
		parsedFields.add(fields.get(fields.size() - 2));
		parsedFields.add(fields.get(fields.size() - 1));
		return parsedFields;
	}
}
