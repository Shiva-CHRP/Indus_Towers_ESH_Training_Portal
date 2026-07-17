package indus.pageobjects.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class QuestionBank extends AbstractComponent{

	public QuestionBank(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='sidebar-group-ASSESSMENTS']//a[.//span[normalize-space()='Question Bank']]")
	WebElement questionBank;
	
	@FindBy(xpath = "//h1[normalize-space()='Question Bank']")
	WebElement questionBankName;
	
	@FindBy(xpath = "//button[normalize-space()='Create Question']")
	WebElement questionCreate;
	
	@FindBy(xpath = "//h1[normalize-space()='Create Question']")
	WebElement createQuestionName;
	
	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Create Question']")
	WebElement createQuestionBtn;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancelQuestionBtn;
	
	public String questionBankMenu() {
		waitElementToAppear(questionBank);
		return questionBank.getText();
	}

	public void goToQuestionBank() {
		questionBank.click();
		waitElementToAppear(questionBankName);
	}

	public String questionBankScreenName() {
		return questionBankName.getText();
	}
	
	public boolean questionCreateButtonEnable() {
		boolean isEnabled = questionCreate.isEnabled();
		return isEnabled;
	}
	
	public void clickOnCreateQuestion() {
		questionCreate.click();
	}
	
	public String creatQuestionModel() {
		return createQuestionName.getText();
	}
	
	public boolean createQuestionButtonDisable() {
		boolean isDisabled = createQuestionBtn.isEnabled();
		return isDisabled;
	}
	
	public void cancelCreateQuestion() {
		cancelQuestionBtn.click();
	}
}
