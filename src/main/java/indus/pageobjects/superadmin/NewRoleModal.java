package indus.pageobjects.superadmin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;
import indus.annotations.StepName;

public class NewRoleModal extends AbstractComponent {

	public NewRoleModal(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@role='dialog']//h2[normalize-space()='New role']")
	private WebElement modalTitle;

	@FindBy(xpath = "//input[@id='role-name']")
	private WebElement roleNameInput;

	@FindBy(xpath = "//button[@type='submit' and contains(normalize-space(),'Create role')]")
	private WebElement createRoleButton;

	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	private WebElement cancelButton;

	@FindBy(xpath = "//p[contains(@class,'text-status-fail')]")
	private WebElement errorMessageBanner;

	@StepName("Check if New Role modal dialog is displayed")
	public boolean isModalOpen() {
		try {
			waitElementToAppear(modalTitle);
			return modalTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Enter Role Name in New Role modal")
	public void enterRoleName(String name) {
		waitForVisibility(roleNameInput);
		waitElementToBeClickable(roleNameInput);
		roleNameInput.clear();
		roleNameInput.sendKeys(name);
	}

	@StepName("Click Create Role button")
	public void clickCreateRole() {
		waitElementToBeClickable(createRoleButton);
		createRoleButton.click();
	}

	@StepName("Click Cancel button on New Role modal")
	public void clickCancel() {
		waitElementToBeClickable(cancelButton);
		cancelButton.click();
		waitForElementToDisappear(modalTitle);
	}

	@StepName("Verify if Create Role submit button is enabled")
	public boolean isCreateRoleButtonEnabled() {
		return createRoleButton.isEnabled();
	}

	@StepName("Get duplicate or validation error message from modal")
	public String getErrorMessage() {
		try {
			waitElementToAppear(errorMessageBanner);
			return errorMessageBanner.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}
}
