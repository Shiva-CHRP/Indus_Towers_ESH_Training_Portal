package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;
import indus.annotations.StepName;

public class QuestionBank extends AbstractComponent {

	public QuestionBank(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='sidebar-group-ASSESSMENTS']//a[.//span[normalize-space()='Question Bank']]")
	private WebElement questionBank;
	
	@FindBy(xpath = "//h1[normalize-space()='Question Bank']")
	private WebElement questionBankName;
	
	@FindBy(xpath = "//button[contains(.,'Create Question')]")
	private WebElement questionCreate;
	
	@FindBy(xpath = "//h1[normalize-space()='Create Question' or contains(.,'Question')]")
	private WebElement createQuestionName;

	@FindBy(xpath = "//textarea[contains(@placeholder,'question') or contains(@class,'resize-none')]")
	private WebElement questionTextInput;

	@FindBy(xpath = "//input[contains(@placeholder,'Option')]")
	private List<WebElement> optionInputs;

	@FindBy(xpath = "//button[contains(@class,'bg-red-50') or contains(@class,'bg-amber-50')] | //div[contains(@class,'bg-red-50') or contains(@class,'bg-amber-50')]")
	private WebElement similarityAlertBanner;
	
	@FindBy(xpath = "//button[@type='submit' and (contains(.,'Create Question') or contains(.,'Save question'))]")
	private WebElement createQuestionBtn;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	private WebElement cancelQuestionBtn;

	@FindBy(xpath = "//input[contains(@placeholder,'Search questions')]")
	private WebElement searchQuestionsInput;
	
	// ── Navigation ───────────────────────────────────────────────────────
	public String questionBankMenu() {
		waitElementToAppear(questionBank);
		return questionBank.getText().trim();
	}

	public void goToQuestionBank() {
		waitForBackdropToDisappear();
		waitElementToBeClickable(questionBank);
		clickElement(questionBank);
		waitElementToAppear(questionBankName);
	}

	public String questionBankScreenName() {
		waitElementToAppear(questionBankName);
		return questionBankName.getText().trim();
	}
	
	public boolean questionCreateButtonEnable() {
		return questionCreate.isEnabled();
	}
	
	public void clickOnCreateQuestion() {
		waitForBackdropToDisappear();
		waitElementToBeClickable(questionCreate);
		clickElement(questionCreate);
		waitElementToAppear(createQuestionName);
	}
	
	public String creatQuestionModel() {
		waitElementToAppear(createQuestionName);
		return createQuestionName.getText().trim();
	}

	// ── Form Inputs ──────────────────────────────────────────────────────
	@StepName("Enter question text in form")
	public void enterQuestionText(String text) {
		waitForVisibility(questionTextInput);
		questionTextInput.clear();
		questionTextInput.sendKeys(text);
	}

	@StepName("Fill Option A, B, C, D choices")
	public void fillOptions(String optA, String optB, String optC, String optD) {
		if (optionInputs.size() >= 4) {
			optionInputs.get(0).clear();
			optionInputs.get(0).sendKeys(optA);

			optionInputs.get(1).clear();
			optionInputs.get(1).sendKeys(optB);

			optionInputs.get(2).clear();
			optionInputs.get(2).sendKeys(optC);

			optionInputs.get(3).clear();
			optionInputs.get(3).sendKeys(optD);
		}
	}

	@StepName("Select correct answer choice (A, B, C, or D)")
	public void selectCorrectAnswer(String letter) {
		try {
			By radioBy = By.xpath("//button[normalize-space()='" + letter + "'] | //input[@value='" + letter + "']");
			WebElement radio = waitUtils.waitForVisibility(radioBy);
			waitElementToBeClickable(radio);
			clickElement(radio);
		} catch (Exception ignored) {}
	}

	@StepName("Check if real-time similarity alert banner is displayed")
	public boolean isSimilarityAlertDisplayed() {
		try {
			waitElementToAppear(similarityAlertBanner);
			return similarityAlertBanner.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Get text from similarity alert banner")
	public String getSimilarityAlertText() {
		try {
			waitElementToAppear(similarityAlertBanner);
			return similarityAlertBanner.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Submit Create Question form")
	public void submitQuestion() {
		waitElementToBeClickable(createQuestionBtn);
		clickElement(createQuestionBtn);
		try {
			waitUtils.waitForInvisibility(createQuestionName);
		} catch (Exception e) {
			try {
				if (cancelQuestionBtn.isDisplayed()) {
					waitUtils.clickUsingJS(cancelQuestionBtn);
				}
			} catch (Exception ignored) {}
		}
		waitForBackdropToDisappear();
	}
	
	public boolean createQuestionButtonDisable() {
		return !createQuestionBtn.isEnabled();
	}
	
	public void cancelCreateQuestion() {
		try {
			if (cancelQuestionBtn.isDisplayed()) {
				clickElement(cancelQuestionBtn);
			}
		} catch (Exception e) {
			waitUtils.clickUsingJS(cancelQuestionBtn);
		}
		try {
			waitForElementToDisappear(createQuestionName);
		} catch (Exception ignored) {}
		waitForBackdropToDisappear();
	}

	@StepName("Search question bank by keyword")
	public void searchQuestion(String keyword) {
		try {
			waitForVisibility(searchQuestionsInput);
			searchQuestionsInput.clear();
			searchQuestionsInput.sendKeys(keyword);
		} catch (Exception ignored) {}
	}
}
