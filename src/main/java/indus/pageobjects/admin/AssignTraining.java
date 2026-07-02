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
	WebElement selectCourses;
	
	@FindBy(xpath = "//button[normalize-space()='Next']")
	WebElement nextBtn;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancelCoursebtn;
	
	@FindBy(xpath = "//button[contains(normalize-space(),'Assign training')]")
	WebElement assignTrainingbtn;
	
	@FindBy(xpath = "//button[contains(normalize-space(),'Back to Members')]")
	WebElement backToMembersbtn;
	
	@FindBy(xpath = "//button[contains(normalize-space(),'Back to Courses')]")
	WebElement backToCoursesbtn;
	
	@FindBy(xpath = "//a[@href='/admin/assign' and contains(.,'Back to Courses')]")
	WebElement backToAssignbtn;
}
