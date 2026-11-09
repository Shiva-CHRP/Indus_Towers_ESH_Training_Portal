package indus.pageobjects.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;
import indus.annotations.StepName;

public class Courses extends AbstractComponent {

	public Courses(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='sidebar-group-CONTENT MANAGEMENT']//a[.//span[normalize-space()='Courses']]")
	private WebElement course;
	
	@FindBy(xpath = "//h1[normalize-space()='Course Catalog']")
	private WebElement courseCatalogName;
	
	@FindBy(xpath = "//button[normalize-space()='Add Course']")
	private WebElement addCourse;
	
	@FindBy(xpath = "//h2[normalize-space()='Add Course']")
	private WebElement addCourseName;

	@FindBy(xpath = "//label[contains(.,'Title')]/following-sibling::input | //input[@placeholder='e.g. Working at Height Safety']")
	private WebElement courseTitleInput;

	@FindBy(xpath = "//label[contains(.,'Code')]/following-sibling::input | //input[@placeholder='e.g. ESH-WAH-01']")
	private WebElement courseCodeInput;

	@FindBy(xpath = "//label[contains(.,'Description')]/following-sibling::textarea")
	private WebElement courseDescInput;
	
	@FindBy(xpath = "//button[@type='submit' and contains(normalize-space(),'Create Course')]")
	private WebElement createCourseBtn;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	private WebElement cancelCourseBtn;
	
	@FindBy(xpath = "//button[.//text()[normalize-space()='Assign Assessment']]")
	private WebElement assignAssessmentBtn;
	
	@FindBy(xpath = "//button[normalize-space()='Cancel']")
	private WebElement assignAssessmentCancelBtn;
	
	@FindBy(xpath = "//button[normalize-space()='Assign']")
	private WebElement assignAssessmentAssignBtn;
	
	@FindBy(xpath = "//button[.//span[contains(normalize-space(),'Manage Content')] or contains(.,'Manage Content')]")
	private WebElement manageContentBtn;
	
	@FindBy(xpath = "//h1[normalize-space()='Course Content']")
	private WebElement courseContentName;
	
	@FindBy(xpath = "//button[normalize-space()='Add Content']")
	private WebElement addContent;

	@FindBy(xpath = "//input[@type='file']")
	private WebElement contentFileInput;
	
	@FindBy(xpath = "//button[normalize-space()='Cancel']")
	private WebElement addContentCancelBtn;
	
	@FindBy(xpath = "//button[normalize-space()='Upload All' or contains(.,'Upload')]")
	private WebElement addContentUploadAllBtn;
	
	@FindBy(xpath = "//a[@href='/admin/courses' and contains(.,'Back to Course Catalog')]")
	private WebElement backToCourseBtn;
	
	// ── Navigation ───────────────────────────────────────────────────────
	public String courseMenu() {
		waitElementToAppear(course);
		return course.getText().trim();
	}

	public void goToCourses() {
		waitElementToBeClickable(course);
		course.click();
		waitElementToAppear(courseCatalogName);
	}

	public String courseCatalogScreenName() {
		waitElementToAppear(courseCatalogName);
		return courseCatalogName.getText().trim();
	}
	
	// ── Add Course Form ──────────────────────────────────────────────────
	public boolean courseCreateButtonEnable() {
		return addCourse.isEnabled();
	}
	
	public void clickOnAddCourse() {
		waitElementToBeClickable(addCourse);
		addCourse.click();
		waitElementToAppear(addCourseName);
	}
	
	public String addCourseModel() {
		return addCourseName.getText().trim();
	}

	@StepName("Fill Add Course form details")
	public void fillCourseForm(String title, String code, String desc) {
		waitForVisibility(courseTitleInput);
		courseTitleInput.clear();
		courseTitleInput.sendKeys(title);

		if (code != null && !code.isEmpty()) {
			courseCodeInput.clear();
			courseCodeInput.sendKeys(code);
		}

		if (desc != null && !desc.isEmpty()) {
			courseDescInput.clear();
			courseDescInput.sendKeys(desc);
		}
	}

	@StepName("Submit Create Course form")
	public void submitCourse() {
		waitElementToBeClickable(createCourseBtn);
		createCourseBtn.click();
	}
	
	public boolean createCourseButtonDisable() {
		return !createCourseBtn.isEnabled();
	}
	
	public void cancelCreateCourse() {
		waitElementToBeClickable(cancelCourseBtn);
		cancelCourseBtn.click();
		waitForElementToDisappear(addCourseName);
	}
	
	// ── Manage Content & SCORM ───────────────────────────────────────────
	public boolean manageContentButtonEnable() {
		return manageContentBtn.isEnabled();
	}
	
	@StepName("Navigate to Course Content manager")
	public void clickOnManageContent() {
		waitElementToBeClickable(manageContentBtn);
		manageContentBtn.click();
		waitElementToAppear(courseContentName);
	}
	
	public String courseContentName() {
		waitElementToAppear(courseContentName);
		return courseContentName.getText().trim();
	}
	
	public void clickOnAddContent() {
		waitElementToBeClickable(addContent);
		addContent.click();
	}

	@StepName("Upload SCORM or Lesson package file")
	public void uploadPackageFile(String filePath) {
		try {
			contentFileInput.sendKeys(filePath);
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", contentFileInput);
			contentFileInput.sendKeys(filePath);
		}
	}
	
	public boolean uploadAllButtonDisable() {
		return !addContentUploadAllBtn.isEnabled();
	}
	
	public void cancelAddContent() {
		try {
			waitElementToBeClickable(addContentCancelBtn);
			addContentCancelBtn.click();
		} catch (Exception ignored) {}
	}
	
	public void clickOnBackToCourses() {
		waitElementToBeClickable(backToCourseBtn);
		backToCourseBtn.click();
		waitElementToAppear(courseCatalogName);
	}

	@StepName("Check if course card exists in catalog")
	public boolean isCoursePresent(String courseTitle) {
		try {
			By cardBy = By.xpath("//div[contains(@class,'rounded') and .//h3[contains(.,'" + courseTitle + "')]]");
			WebElement card = waitUtils.waitForVisibility(cardBy);
			return card != null && card.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}
