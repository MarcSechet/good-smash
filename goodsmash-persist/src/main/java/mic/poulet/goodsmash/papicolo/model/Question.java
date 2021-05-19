package mic.poulet.goodsmash.papicolo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"key", "parentKey", "question", "category", "questionType"})
})
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String key;

	private String parentKey;

	@Size(max = 1200)
	private String question;

	private int nbPlayers;

	@Enumerated(EnumType.STRING)
	private Category category;

	@Enumerated(EnumType.STRING)
	private QuestionType questionType;

	@OneToMany(targetEntity = Question.class, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Question> children = new ArrayList<>();

	private boolean questionFromPicolo;

	public Question setId(Long id) {
		this.id = id;
		return this;
	}

	public Question setKey(String key) {
		this.key = key;
		return this;
	}

	public Question setParentKey(String parentKey) {
		this.parentKey = parentKey;
		return this;
	}

	public Question setQuestion(String question) {
		this.question = question;
		return this;
	}

	public Question setNbPlayers(int nbPlayers) {
		this.nbPlayers = nbPlayers;
		return this;
	}

	public Question setCategory(Category category) {
		this.category = category;
		return this;
	}

	public Question setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
		return this;
	}

	public Question setChildren(List<Question> children) {
		this.children = children;
		return this;
	}

	public Question setQuestionFromPicolo(boolean questionFromPicolo) {
		this.questionFromPicolo = questionFromPicolo;
		return this;
	}
}
