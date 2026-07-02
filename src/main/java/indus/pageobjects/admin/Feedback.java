package indus.pageobjects.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class Feedback extends AbstractComponent{

	public Feedback(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@id='sidebar-group-INSIGHTS']//a[.//span[normalize-space()='Feedback']]")
	WebElement feedback;
	
	@FindBy(xpath = "//h1[normalize-space()='Feedback']")
	WebElement feedbackName;
}
