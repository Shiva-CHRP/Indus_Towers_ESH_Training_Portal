package indus.pageobjects.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class Assessments extends AbstractComponent{

	public Assessments(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath = "//div[@id='sidebar-group-ASSESSMENTS']//a[.//span[normalize-space()='Assessments']]")
	WebElement assessment;
	
	@FindBy(xpath = "//h1[normalize-space()='Assessments']")
	WebElement assessmentName;
	
	@FindBy(xpath = "//button[normalize-space()='Create Assessment']")
	WebElement assessmentCreate;
	
	@FindBy(xpath = "//h1[normalize-space()='Create Assessment']")
	WebElement createAssessmentName;
	
	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Create Assessment']")
	WebElement createAssessmentbtn;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancelAssessmentbtn;
}
