package mic.poulet.goodsmash.papicolo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionType {
	NORMAL(new int[]{1, 4, 16, 17, 18, 19, 20, 21, 22, 7, 24, 25}), // 7 for hot (=hardcore), 16 to 22 for bar
	VOTE(new int[]{14, 23}), // TODO TODO TODO : test 23 in app to see if it corresctly displayed as EVENT_WITH_CHILD_RIGHT_AFTER
	EVENT(new int[]{}),
	EVENT_WITH_CHILD_RIGHT_AFTER(new int[]{}),
	CUL_SEC(new int[]{5, 6});

	private final int[] questionTypesInCsv;

	public static QuestionType getFromQuestionType(int questionTypeInCsv) {
		for (QuestionType questionType : QuestionType.values()) {
			for (int _questionTypeInCsv : questionType.getQuestionTypesInCsv())
				if (_questionTypeInCsv == questionTypeInCsv) {
					return questionType;
				}
		}
		return NORMAL;
	}
}
