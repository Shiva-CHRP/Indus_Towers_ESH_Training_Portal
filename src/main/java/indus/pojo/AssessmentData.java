package indus.pojo;

public class AssessmentData {
	private String assessmentTitle;
	private String description;
	private int minQuestionsThreshold;
	private int invalidQuestionSelectionCount;
	private String expectedMinQuestionsError;

	public AssessmentData() {}

	public String getAssessmentTitle() { return assessmentTitle; }
	public void setAssessmentTitle(String assessmentTitle) { this.assessmentTitle = assessmentTitle; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public int getMinQuestionsThreshold() { return minQuestionsThreshold; }
	public void setMinQuestionsThreshold(int minQuestionsThreshold) { this.minQuestionsThreshold = minQuestionsThreshold; }

	public int getInvalidQuestionSelectionCount() { return invalidQuestionSelectionCount; }
	public void setInvalidQuestionSelectionCount(int invalidQuestionSelectionCount) { this.invalidQuestionSelectionCount = invalidQuestionSelectionCount; }

	public String getExpectedMinQuestionsError() { return expectedMinQuestionsError; }
	public void setExpectedMinQuestionsError(String expectedMinQuestionsError) { this.expectedMinQuestionsError = expectedMinQuestionsError; }

	@Override
	public String toString() {
		return "AssessmentData [assessmentTitle=" + assessmentTitle + ", minQuestionsThreshold=" + minQuestionsThreshold + "]";
	}
}
