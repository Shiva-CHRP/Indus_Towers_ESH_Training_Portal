package indus.pageobjects.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class Profile extends AbstractComponent{

	public Profile(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@id='sidebar-group-ACCOUNT']//a[.//span[normalize-space()='Profile']]")
	WebElement profile;
	
	@FindBy(xpath = "//h3[normalize-space()='Personal Information']")
	WebElement personalInformationName;
}
