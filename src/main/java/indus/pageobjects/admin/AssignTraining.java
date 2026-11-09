package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;
import indus.annotations.StepName;

public class AssignTraining extends AbstractComponent {

	public AssignTraining(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@id='sidebar-group-CONTENT MANAGEMENT']//a[.//span[normalize-space()='Assign Training']]")
	private WebElement assignTraining;
	
	@FindBy(xpath = "//h1[normalize-space()='Assign Training']")
	private WebElement assignTrainingName;
	
	@FindBy(xpath = "//button[contains(.,'Assign Training') or contains(.,'Assign Course')]")
	private WebElement addAssignTraining;
	
	@FindBy(xpath = "//input[@type='checkbox' and not(@disabled)]")
	private WebElement selectCheckbox;

	@FindBy(xpath = "//table//input[@type='checkbox']")
	private List<WebElement> allCheckboxes;
	
	@FindBy(xpath = "//button[normalize-space()='Next' or contains(.,'Next')]")
	private WebElement nextBtn;
	
	@FindBy(xpath = "//button[contains(normalize-space(),'Assign training') or contains(normalize-space(),'Assign Training') or contains(normalize-space(),'Confirm')]")
	private WebElement assignTrainingBtn;
	
	@FindBy(xpath = "//button[contains(normalize-space(),'Back to Members')]")
	private WebElement backToMembersBtn;
	
	@FindBy(xpath = "//button[contains(normalize-space(),'Back to Courses')]")
	private WebElement backToCoursesBtn;
	
	@FindBy(xpath = "//a[@href='/admin/assign' and contains(.,'Back to Courses')]")
	private WebElement backToAssignTraining;
	
	@FindBy(xpath = "//button[@type='button' and contains(normalize-space(),'View Detail')]")
	private WebElement viewDetailBtn;
	
	@FindBy(xpath = "//button[.//span[normalize-space()='Add Individuals'] or contains(.,'Add Individuals')]")
	private WebElement addIndividuals;
	
	@FindBy(xpath = "//button[.//span[normalize-space()='Add Group'] or contains(.,'Add Group')]")
	private WebElement addGroups;

	@FindBy(xpath = "//input[@type='date' or contains(@placeholder,'deadline') or contains(@class,'date')]")
	private WebElement deadlineInput;

	// ── Navigation ───────────────────────────────────────────────────────
	public String assignTrainingMenu() {
		waitElementToAppear(assignTraining);
		return assignTraining.getText().trim();
	}

	public void goToAssignTraining() {
		waitElementToBeClickable(assignTraining);
		assignTraining.click();
		waitElementToAppear(assignTrainingName);
	}

	public String assignTrainingName() {
		waitElementToAppear(assignTrainingName);
		return assignTrainingName.getText().trim();
	}
	
	public boolean addAssignTrainingButtonEnable() {
		return addAssignTraining.isEnabled();
	}
	
	public void clickOnAssignTraining() {
		waitElementToBeClickable(addAssignTraining);
		addAssignTraining.click();
		waitElementToAppear(nextBtn);
	}
	
	// ── Wizard Step 1: Courses Selection ─────────────────────────────────
	@StepName("Select first course in Assign Wizard Step 1")
	public void selectFirstCourse() {
		if (selectCheckbox != null) {
			waitElementToBeClickable(selectCheckbox);
			selectCheckbox.click();
		}
	}

	@StepName("Click Next button in wizard")
	public void clickNextButton() {
		waitElementToBeClickable(nextBtn);
		nextBtn.click();
	}

	// ── Wizard Step 2: Audience Selection ────────────────────────────────
	@StepName("Select first audience member / group in Step 2")
	public void selectAudienceMember() {
		try {
			if (!allCheckboxes.isEmpty()) {
				WebElement cb = allCheckboxes.get(0);
				waitElementToBeClickable(cb);
				cb.click();
			}
		} catch (Exception ignored) {}
	}

	// ── Wizard Step 3: Deadline & Confirmation ───────────────────────────
	@StepName("Confirm and submit training assignment in Step 3")
	public void confirmAssignment() {
		waitElementToBeClickable(assignTrainingBtn);
		assignTrainingBtn.click();
	}
	
	public boolean assignTrainingButtonEnable() {
		return assignTrainingBtn.isEnabled();
	}
	
	public void clickOnBackToMembers() {
		waitElementToBeClickable(backToMembersBtn);
		backToMembersBtn.click();
	}

	public void clickOnBackToCourses() {
		waitElementToBeClickable(backToCoursesBtn);
		backToCoursesBtn.click();
	}

	public void backToCoursesPage() {
		waitElementToBeClickable(backToAssignTraining);
		backToAssignTraining.click();
	}
	
	public void clickOnViewDetailOnCard() {
		waitElementToBeClickable(viewDetailBtn);
		viewDetailBtn.click();
	}
	
	public boolean addIndividualsButton() {
		return addIndividuals.isEnabled();
	}
	
	public boolean addGroupsButton() {
		return addGroups.isEnabled();
	}
}
