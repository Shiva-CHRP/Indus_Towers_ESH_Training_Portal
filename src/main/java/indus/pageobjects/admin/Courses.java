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
	WebElement createCoursebtn;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancelCoursebtn;
	
	@FindBy(xpath = "//button[.//text()[normalize-space()='Assign Assessment']]")
	WebElement assignAssessmentbtn;
	
	@FindBy(xpath = "//button[normalize-space()='Cancel']")
	WebElement assignAssessmentCancelbtn;
	
	@FindBy(xpath = "//button[normalize-space()='Assign']")
	WebElement assignAssessmentAssignbtn;
	
	@FindBy(xpath = "//button[.//span[contains(normalize-space(),'Manage Content')]]")
	WebElement manageContentbtn;
	
	@FindBy(xpath = "//h1[normalize-space()='Course Content']")
	WebElement courseContentName;
	
	@FindBy(xpath = "//button[normalize-space()='Add Content']")
	WebElement addContent;
	
	@FindBy(xpath = "//button[normalize-space()='Cancel']")
	WebElement addContentCancelbtn;
	
	@FindBy(xpath = "//button[normalize-space()='Upload All']")
	WebElement addContentUploadAllbtn;
	
	@FindBy(xpath = "//a[@href='/admin/courses' and contains(.,'Back to Course Catalog')]")
	WebElement backToCoursebtn;
}
