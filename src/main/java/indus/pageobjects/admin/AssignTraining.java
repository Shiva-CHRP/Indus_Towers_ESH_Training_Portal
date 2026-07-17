package indus.pageobjects.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class AssignTraining extends AbstractComponent{

	public AssignTraining(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@id='sidebar-group-CONTENT MANAGEMENT']//a[.//span[normalize-space()='Assign Training']]")
	WebElement assignTraining;
	
	@FindBy(xpath = "//h1[normalize-space()='Assign Training']")
	WebElement assignTrainingName;
	
	@FindBy(xpath = "//button[normalize-space()='Assign Training']")
	WebElement addAssignTraining;
	
	@FindBy(xpath = "//input[@type='checkbox' and not(@disabled)]")
	WebElement selectCheckbox;
	
	@FindBy(xpath = "//button[normalize-space()='Next']")
	WebElement nextBtn;
	
	@FindBy(xpath = "//button[contains(normalize-space(),'Assign training')]")
	WebElement assignTrainingBtn;
	
	@FindBy(xpath = "//button[contains(normalize-space(),'Back to Members')]")
	WebElement backToMembersBtn;
	
	@FindBy(xpath = "//button[contains(normalize-space(),'Back to Courses')]")
	WebElement backToCoursesBtn;
	
	@FindBy(xpath = "//a[@href='/admin/assign' and contains(.,'Back to Courses')]")
	WebElement backToAssignTraining;
	
	@FindBy(xpath = "//button[@type='button' and contains(normalize-space(),'View Detail')]")
	WebElement viewDetailBtn;
	
	@FindBy(xpath = "//button[.//span[normalize-space()='Add Individuals']]")
	WebElement addIndividuals;
	
	@FindBy(xpath = "//button[.//span[normalize-space()='Add Group']]")
	WebElement addGroups;
	
	
	public String assignTrainingMenu() {
		waitElementToAppear(assignTraining);
		return assignTraining.getText();
	}

	public void goToAssignTraining() {
		assignTraining.click();
		waitElementToAppear(assignTrainingName);
	}

	public String assignTrainingName() {
		return assignTrainingName.getText();
	}
	
	public boolean addAssignTrainingButtonEnable() {
		boolean isEnabled = addAssignTraining.isEnabled();
		return isEnabled;
	}
	
	public void clickOnAssignTraining() {
		addAssignTraining.click();
		waitElementToAppear(selectCheckbox);
	}
	
	public void selectCheckox() {
		selectCheckbox.click();
	}
	
	public void clickNextButton() {
		nextBtn.click();
	}
	
	public boolean assignTrainingButtonEnable() {
		boolean isEnabled = assignTrainingBtn.isEnabled();
		return isEnabled;
	}
	
	public void clickOnBackToMembers() {
		backToMembersBtn.click();
	}
	public void clickOnBackToCourses() {
		backToCoursesBtn.click();
	}
	public void backToCoursesPage() {
		backToAssignTraining.click();
	}
	
	public void clickOnViewDetailOnCard() {
		viewDetailBtn.click();
	}
	
	public boolean addIndividualsButton() {
		boolean isEnabled = addIndividuals.isEnabled();
		return isEnabled;
	}
	
	public boolean addGroupsButton() {
		boolean isEnabled = addGroups.isEnabled();
		return isEnabled;
	}
}
