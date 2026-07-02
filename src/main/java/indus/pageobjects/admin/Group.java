package indus.pageobjects.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class Group extends AbstractComponent{

	public Group(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@id='sidebar-group-MANAGEMENT']//a[.//span[normalize-space()='Group']]")
	WebElement group;
	
	@FindBy(xpath = "//h1[normalize-space()='Group Master']")
	WebElement groupMasterName;
	
	@FindBy(xpath = "//button[normalize-space()='Create Group']")
	WebElement groupCreate;
	
	@FindBy(xpath = "//h1[normalize-space()='Create Group']")
	WebElement createGroupName;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Create Group']")
	WebElement createGroupbtn;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancelGroupbtn;
}
