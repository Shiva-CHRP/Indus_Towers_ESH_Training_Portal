package indus.pageobjects.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class Courses extends AbstractComponent{

	public Courses(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='sidebar-group-CONTENT MANAGEMENT']//a[.//span[normalize-space()='Courses']]")
	WebElement course;
	
	@FindBy(xpath = "//h1[normalize-space()='Course Catalog']")
	WebElement courseCatalogName;
	
	@FindBy(xpath = "//button[normalize-space()='Add Course']")
	WebElement addCourse;
	
	@FindBy(xpath = "//h2[normalize-space()='Add Course']")
	WebElement addCourseName;
	
	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Create Course']")
	WebElement createCourseBtn;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancelCourseBtn;
	
	@FindBy(xpath = "//button[.//text()[normalize-space()='Assign Assessment']]")
	WebElement assignAssessmentBtn;
	
	@FindBy(xpath = "//button[normalize-space()='Cancel']")
	WebElement assignAssessmentCancelBtn;
	
	@FindBy(xpath = "//button[normalize-space()='Assign']")
	WebElement assignAssessmentAssignBtn;
	
	@FindBy(xpath = "//button[.//span[contains(normalize-space(),'Manage Content')]]")
	WebElement manageContentBtn;
	
	@FindBy(xpath = "//h1[normalize-space()='Course Content']")
	WebElement courseContentName;
	
	@FindBy(xpath = "//button[normalize-space()='Add Content']")
	WebElement addContent;
	
	@FindBy(xpath = "//button[normalize-space()='Cancel']")
	WebElement addContentCancelBtn;
	
	@FindBy(xpath = "//button[normalize-space()='Upload All']")
	WebElement addContentUploadAllBtn;
	
	@FindBy(xpath = "//a[@href='/admin/courses' and contains(.,'Back to Course Catalog')]")
	WebElement backToCourseBtn;
	
	
	public String courseMenu() {
		waitElementToAppear(course);
		return course.getText();
	}

	public void goToCourses() {
		course.click();
		waitElementToAppear(courseCatalogName);
	}

	public String courseCatalogScreenName() {
		return courseCatalogName.getText();
	}
	
	public boolean courseCreateButtonEnable() {
		boolean isEnabled = addCourse.isEnabled();
		return isEnabled;
	}
	
	public void clickOnAddCourse() {
		addCourse.click();
	}
	
	public String addCourseModel() {
		return addCourseName.getText();
	}
	
	public boolean createCourseButtonDisable() {
		boolean isDisabled = createCourseBtn.isEnabled();
		return isDisabled;
	}
	
	public void cancelCreateCourse() {
		cancelCourseBtn.click();
	}
	
	
	public boolean assignAssessmentButtonEnable() {
		boolean isEnabled = assignAssessmentBtn.isEnabled();
		return isEnabled;
	}
	
	public void clickOnAssignAssessment() {
		assignAssessmentBtn.click();
	}
	
	public boolean assignButtonDisable() {
		boolean isDisabled = assignAssessmentAssignBtn.isEnabled();
		return isDisabled;
	}
	
	public void cancelAssignAssessment() {
		assignAssessmentCancelBtn.click();
	}
	
	public boolean manageContentButtonEnable() {
		boolean isEnabled = manageContentBtn.isEnabled();
		return isEnabled;
	}
	
	public void clickOnManageContent() {
		manageContentBtn.click();
		waitElementToAppear(courseContentName);
	}
	
	public String courseContentName() {
		return courseContentName.getText();
	}
	
	public void clickOnAddContent() {
		addContent.click();
	}
	
	public boolean uploadAllButtonDisable() {
		boolean isDisabled = addContentUploadAllBtn.isEnabled();
		return isDisabled;
	}
	
	public void cancelAddContent() {
		addContentCancelBtn.click();
		waitElementToAppear(backToCourseBtn);
	}
	
	public void clickOnBackToCourses() {
		backToCourseBtn.click();
	}
	
	
}
