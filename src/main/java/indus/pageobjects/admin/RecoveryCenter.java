package indus.pageobjects.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class RecoveryCenter extends AbstractComponent{

	public RecoveryCenter(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@id='sidebar-group-TOOLS & SUPPORT']//a[.//span[normalize-space()='Recovery Center']]")
	WebElement recoveryCenter;
	
	@FindBy(xpath = "//h1[normalize-space()='Recovery Center']")
	WebElement recoveryCenterName;
	
	@FindBy(xpath = "//button[normalize-space()='Deleted Items']")
	WebElement deletedItemsTab;
	
	@FindBy(xpath = "//button[normalize-space()='Restored History']")
	WebElement restoredHistoryTab;
}
