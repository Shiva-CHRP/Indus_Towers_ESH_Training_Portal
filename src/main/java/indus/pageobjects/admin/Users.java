package indus.pageobjects.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class Users extends AbstractComponent{

	public Users(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@id='sidebar-group-MANAGEMENT']//a[.//span[normalize-space()='Users']]")
	WebElement users;
	
	@FindBy(xpath = "//h1[normalize-space()='User Management']")
	WebElement userManagementName;
	
	@FindBy(xpath = "//button[normalize-space()='Add User']")
	WebElement userAdd;
	
	@FindBy(xpath = "//button[normalize-space()='Mass Edit']")
	WebElement massEdit;
	
	@FindBy(xpath = "//button[normalize-space()='All']")
	WebElement allTab;
	
	@FindBy(xpath = "//button[normalize-space()='Learners']")
	WebElement learnersTab;
	
	@FindBy(xpath = "//button[normalize-space()='Managers']")
	WebElement managersTab;
	
	@FindBy(xpath = "//button[normalize-space()='Admins']")
	WebElement adminTab;
	
	@FindBy(xpath = "//button[normalize-space()='Inactive']")
	WebElement inActiveTab;
	
	@FindBy(xpath = "//h2[normalize-space()='Add User']")
	WebElement addUserPopupName;
	
	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Create Account']")
	WebElement createAccout;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancelAccout;
}
