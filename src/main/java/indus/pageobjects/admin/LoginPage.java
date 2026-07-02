package indus.pageobjects.admin;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;
import indus.annotations.StepName;

public class LoginPage extends AbstractComponent{

	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div//span[contains(text(),'Email & Password')]")
	WebElement emailLogin;

	@FindBy(xpath = "//input[@type='email']")
	WebElement emailAddress;

	@FindBy(xpath = "//input[@type='password']")
	WebElement password;
	
	@FindBy(xpath = "//button[@type='submit']")
	WebElement signinButton;
	
	@FindBy(xpath = "//p[normalize-space()='Overall Compliance']")
	WebElement overallCompliance;
	
	@FindBy(xpath = "//header//button[.//span[contains(@class,'hidden md:inline-flex')] and .//div]")
	WebElement profile;
	@FindBy(xpath = "//button[contains(normalize-space(.),'Sign out')]")
	WebElement signOut;
	
	@FindBy(xpath = "//div[contains(@class,'flex justify-end')]//button[.//text()[contains(.,'Sign out')]]")
	WebElement confirmSignOut;
	
	@StepName("Click Email & Password for login through email")
	public void loginByEmail() {
		waitElementToAppear(emailLogin);
		emailLogin.click();
	}
	
	@StepName("Enter User Email Address")
	public void enterUserEmail(String username) {
		waitForVisibility(emailAddress);
		waitElementToBeClickable(emailAddress);
		try {
			emailAddress.clear();
			}
		catch(Exception e){
			((JavascriptExecutor) driver).executeScript("arguments[0].value='';", emailAddress);
		}
		emailAddress.clear();
		emailAddress.sendKeys(username);
	}
	@StepName("Enter User Password")
	public void enterPassword(String passwords) {
		waitForVisibility(password);
		waitElementToBeClickable(password);
		try {
			password.clear();
			}
		catch(Exception e){
			((JavascriptExecutor) driver).executeScript("arguments[0].value='';", password);
		}
		password.clear();
		password.sendKeys(passwords);
	}
	
	@StepName("Click Sign In Button to Login in Portal")
	public void clickLogin() {
		signinButton.click();		
    }
	
	@StepName("Admin Overview Page")
	public String adminOverallCompliance() {
		waitElementToAppear(overallCompliance);
		return overallCompliance.getText();
	}
	
	@StepName("Click on the Profile Name for Signout")
	public void clickProfile() {
		waitElementToAppear(profile);
		profile.click();
	}
	@StepName("Click Sign out Button to Logout from Portal")
	public void clickSignOut() {
		waitElementToBeClickable(signOut);
		signOut.click();
	}
	@StepName("Click Confirm Sign out Button to Logout from Portal")
	public void clickConfirmSignOut() {
		waitElementToBeClickable(confirmSignOut);
		confirmSignOut.click();
	}
	
	public void clearFields() {
		emailAddress.clear();
		password.clear();
	}
	
	
}
