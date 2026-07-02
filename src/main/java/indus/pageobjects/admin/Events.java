package indus.pageobjects.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class Events extends AbstractComponent{

	public Events(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@id='sidebar-group-EVENTS']//a[.//span[normalize-space()='Events']]")
	WebElement events;
	
	@FindBy(xpath = "//h1[normalize-space()='Events']")
	WebElement eventsName;

	@FindBy(xpath = "//button[normalize-space()='Create Event']")
	WebElement eventCreate;
	
	@FindBy(xpath = "//h2[normalize-space()='Create Event']")
	WebElement createEventName;
	
	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Create Event']")
	WebElement createEventBtn;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancelEventBtn;
}
