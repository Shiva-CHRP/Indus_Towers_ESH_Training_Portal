package indus.pageobjects.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class ManagerOverview extends AbstractComponent{

	public ManagerOverview(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='sidebar-group-MAIN']//a[.//span[normalize-space()='Overview']]")
	WebElement assessment;
	
	@FindBy(xpath = "//p[normalize-space()='Overall Compliance']")
	WebElement overallCompliance;
}
