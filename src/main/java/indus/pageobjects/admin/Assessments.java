package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;
import indus.annotations.StepName;

public class Assessments extends AbstractComponent {

	public Assessments(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='sidebar-group-ASSESSMENTS']//a[.//span[normalize-space()='Assessments']]")
	private WebElement assessment;
	
	@FindBy(xpath = "//h1[normalize-space()='Assessments']")
	private WebElement assessmentName;
	
	@FindBy(xpath = "//button[contains(.,'Create Assessment')]")
	private WebElement assessmentCreate;
	
	@FindBy(xpath = "//h1[normalize-space()='Create Assessment' or contains(.,'Assessment')]")
	private WebElement createAssessmentName;

	@FindBy(xpath = "//label[contains(.,'Title')]/following-sibling::input | //input[contains(@placeholder,'Assessment title') or @placeholder='e.g. Working at Height Safety Test']")
	private WebElement assessmentTitleInput;

	@FindBy(xpath = "//label[contains(.,'Description')]/following-sibling::textarea")
	private WebElement assessmentDescInput;

	@FindBy(xpath = "//table//input[@type='checkbox']")
	private List<WebElement> questionCheckboxes;

	@FindBy(xpath = "//p[contains(@class,'text-status-fail') or contains(@class,'bg-red-50') or contains(.,'at least 10')]")
	private WebElement minQuestionsValidationError;
	
	@FindBy(xpath = "//button[@type='submit' and contains(normalize-space(),'Create Assessment')]")
	private WebElement createAssessmentBtn;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	private WebElement cancelAssessmentBtn;

	@FindBy(xpath = "//input[contains(@placeholder,'Search assessments')]")
	private WebElement searchAssessmentsInput;
	
	// ── Navigation ───────────────────────────────────────────────────────
	public String assessmentMenu() {
		waitElementToAppear(assessment);
		return assessment.getText().trim();
	}

	public void goToAssessments() {
		waitElementToBeClickable(assessment);
		assessment.click();
		waitElementToAppear(assessmentName);
	}

	public String assessmentScreenName() {
		waitElementToAppear(assessmentName);
		return assessmentName.getText().trim();
	}
	
	public boolean assessmentCreateButtonEnable() {
		return assessmentCreate.isEnabled();
	}
	
	public void clickOnCreateAssessment() {
		waitElementToBeClickable(assessmentCreate);
		assessmentCreate.click();
		waitElementToAppear(createAssessmentName);
	}
	
	public String creatAssessmentModel() {
		waitElementToAppear(createAssessmentName);
		return createAssessmentName.getText().trim();
	}

	// ── Form Details ─────────────────────────────────────────────────────
	@StepName("Fill Assessment title and description")
	public void fillAssessmentDetails(String title, String desc) {
		waitForVisibility(assessmentTitleInput);
		assessmentTitleInput.clear();
		assessmentTitleInput.sendKeys(title);

		if (desc != null && !desc.isEmpty()) {
			assessmentDescInput.clear();
			assessmentDescInput.sendKeys(desc);
		}
	}

	@StepName("Select a specific number of questions from the Question Picker")
	public void selectQuestions(int count) {
		int clicked = 0;
		for (WebElement cb : questionCheckboxes) {
			if (clicked >= count) break;
			try {
				if (!cb.isSelected()) {
					waitElementToBeClickable(cb);
					cb.click();
					clicked++;
				}
			} catch (Exception ignored) {}
		}
	}

	@StepName("Check if Min 10 Questions Validation Error is displayed")
	public boolean isMinQuestionsErrorDisplayed() {
		try {
			waitElementToAppear(minQuestionsValidationError);
			return minQuestionsValidationError.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Get Min 10 Questions error message text")
	public String getMinQuestionsErrorText() {
		try {
			waitElementToAppear(minQuestionsValidationError);
			return minQuestionsValidationError.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Submit Create Assessment form")
	public void submitAssessment() {
		waitElementToBeClickable(createAssessmentBtn);
		createAssessmentBtn.click();
	}
	
	public boolean createAssessmentButtonDisable() {
		return !createAssessmentBtn.isEnabled();
	}
	
	public void cancelCreateAssessment() {
		try {
			waitElementToBeClickable(cancelAssessmentBtn);
			cancelAssessmentBtn.click();
		} catch (Exception ignored) {}
	}

	@StepName("Check if assessment is listed in the assessments table")
	public boolean isAssessmentPresent(String title) {
		try {
			By rowBy = By.xpath("//table//tr[contains(.,'" + title + "')]");
			WebElement row = waitUtils.waitForVisibility(rowBy);
			return row != null && row.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}
