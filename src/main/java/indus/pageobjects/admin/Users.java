package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;
import indus.annotations.StepName;

public class Users extends AbstractComponent {

	public Users(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// ── Sidebar Link ─────────────────────────────────────────────────────
	@FindBy(xpath = "//div[@id='sidebar-group-MANAGEMENT']//a[.//span[normalize-space()='Users']]")
	private WebElement usersMenu;

	// ── Page Header & Statistics ─────────────────────────────────────────
	@FindBy(xpath = "//h1[normalize-space()='User Management']")
	private WebElement userManagementHeader;

	// ── Filter Tabs ──────────────────────────────────────────────────────
	@FindBy(xpath = "//button[normalize-space()='Learners']")
	private WebElement learnersTab;

	@FindBy(xpath = "//button[normalize-space()='Managers']")
	private WebElement managersTab;

	@FindBy(xpath = "//button[normalize-space()='Admins']")
	private WebElement adminsTab;

	@FindBy(xpath = "//button[normalize-space()='Inactive']")
	private WebElement inactiveTab;

	// ── Search & Actions ─────────────────────────────────────────────────
	@FindBy(xpath = "//input[@placeholder='Search by name, employee ID, or email...']")
	private WebElement searchInput;

	@FindBy(xpath = "//button[normalize-space()='Add User']")
	private WebElement addUserButton;

	@FindBy(xpath = "//button[normalize-space()='Bulk Upload']")
	private WebElement bulkUploadButton;

	// ── Single User Modal Form ───────────────────────────────────────────
	@FindBy(xpath = "//h2[normalize-space()='Add User']")
	private WebElement addUserModalHeader;

	@FindBy(xpath = "//label[contains(.,'Full Name')]/following-sibling::input")
	private WebElement fullNameInput;

	@FindBy(xpath = "//label[contains(.,'Employee ID')]/following-sibling::input")
	private WebElement employeeIdInput;

	@FindBy(xpath = "//label[contains(.,'Email')]/following-sibling::input")
	private WebElement emailInput;

	@FindBy(xpath = "//label[contains(.,'Phone')]/following-sibling::input")
	private WebElement phoneInput;

	@FindBy(xpath = "//label[contains(.,'Password')]/following-sibling::div//input | //input[@type='password' or @placeholder='Enter password']")
	private WebElement passwordInput;

	@FindBy(xpath = "//div[@role='dialog']//button[@type='submit' and normalize-space()='Create Account']")
	private WebElement createAccountButton;

	@FindBy(xpath = "//div[@role='dialog']//button[@type='button' and normalize-space()='Cancel']")
	private WebElement cancelAccountButton;

	// ── Bulk Upload Modal Form ───────────────────────────────────────────
	@FindBy(xpath = "//h2[normalize-space()='Bulk Upload Users']")
	private WebElement bulkUploadModalHeader;

	@FindBy(xpath = "//button[contains(.,'Download template')]")
	private WebElement downloadTemplateButton;

	@FindBy(xpath = "//input[@type='file']")
	private WebElement fileUploadInput;

	@FindBy(xpath = "//div[@role='dialog']//button[contains(.,'Upload') and contains(.,'user')]")
	private WebElement confirmUploadButton;

	@FindBy(xpath = "//div[@role='dialog']//button[normalize-space()='Cancel']")
	private WebElement cancelBulkUploadButton;

	// ─────────────────────────────────────────────────────────────────────
	// Navigation & Tabs
	// ─────────────────────────────────────────────────────────────────────
	@StepName("Verify Users menu label in sidebar")
	public String usersMenu() {
		waitElementToAppear(usersMenu);
		return usersMenu.getText().trim();
	}

	@StepName("Navigate to Users Management page")
	public void goToUsers() {
		waitForBackdropToDisappear();
		waitElementToBeClickable(usersMenu);
		clickElement(usersMenu);
		waitElementToAppear(userManagementHeader);
	}

	@StepName("Get User Management screen header title")
	public String userManagementScreenName() {
		waitElementToAppear(userManagementHeader);
		return userManagementHeader.getText().trim();
	}

	@StepName("Filter users by Learners tab")
	public void navigateToLearnersTab() {
		waitElementToBeClickable(learnersTab);
		clickElement(learnersTab);
	}

	@StepName("Filter users by Managers tab")
	public void navigateToManagersTab() {
		waitElementToBeClickable(managersTab);
		clickElement(managersTab);
	}

	@StepName("Filter users by Admins tab")
	public void navigateToAdminTab() {
		waitElementToBeClickable(adminsTab);
		clickElement(adminsTab);
	}

	@StepName("Filter users by Inactive tab")
	public void navigateToInActiveTab() {
		waitElementToBeClickable(inactiveTab);
		clickElement(inactiveTab);
	}

	@StepName("Search user by name, employee ID, or email")
	public void searchUser(String query) {
		try {
			waitForVisibility(searchInput);
			searchInput.clear();
			searchInput.sendKeys(query);
		} catch (Exception ignored) {}
	}

	// ─────────────────────────────────────────────────────────────────────
	// Add User Modal Actions
	// ─────────────────────────────────────────────────────────────────────
	@StepName("Click Add User button")
	public void clickAddUser() {
		waitForBackdropToDisappear();
		waitElementToBeClickable(addUserButton);
		clickElement(addUserButton);
		waitElementToAppear(addUserModalHeader);
	}

	@StepName("Verify if Add User modal is opened")
	public String addUserModel() {
		waitElementToAppear(addUserModalHeader);
		return addUserModalHeader.getText().trim();
	}

	@StepName("Fill Add User form")
	public void fillUserForm(String fullName, String empId, String email, String phone, String password) {
		waitForVisibility(fullNameInput);
		fullNameInput.clear();
		fullNameInput.sendKeys(fullName);

		employeeIdInput.clear();
		employeeIdInput.sendKeys(empId);

		emailInput.clear();
		emailInput.sendKeys(email);

		if (phone != null && !phone.isEmpty()) {
			phoneInput.clear();
			phoneInput.sendKeys(phone);
		}

		if (password != null && !password.isEmpty()) {
			waitForVisibility(passwordInput);
			passwordInput.clear();
			passwordInput.sendKeys(password);
		}
	}

	@StepName("Submit Add User form")
	public void submitUserForm() {
		waitElementToBeClickable(createAccountButton);
		clickElement(createAccountButton);
		try {
			waitUtils.waitForInvisibility(addUserModalHeader);
		} catch (Exception e) {
			try {
				if (cancelAccountButton.isDisplayed()) {
					waitUtils.clickUsingJS(cancelAccountButton);
				}
			} catch (Exception ignored) {}
		}
		waitForBackdropToDisappear();
	}

	@StepName("Cancel Add User form")
	public void cancelAddUser() {
		try {
			if (cancelAccountButton.isDisplayed()) {
				clickElement(cancelAccountButton);
			}
		} catch (Exception e) {
			waitUtils.clickUsingJS(cancelAccountButton);
		}
		try {
			waitForElementToDisappear(addUserModalHeader);
		} catch (Exception ignored) {}
		waitForBackdropToDisappear();
	}

	@StepName("Check if Create Account submit button is enabled")
	public boolean isCreateAccountEnabled() {
		return createAccountButton.isEnabled();
	}

	// ─────────────────────────────────────────────────────────────────────
	// Bulk Upload Modal Actions
	// ─────────────────────────────────────────────────────────────────────
	@StepName("Click Bulk Upload button")
	public void clickBulkUpload() {
		waitForBackdropToDisappear();
		waitElementToBeClickable(bulkUploadButton);
		clickElement(bulkUploadButton);
		waitElementToAppear(bulkUploadModalHeader);
	}

	@StepName("Verify if Bulk Upload modal is opened")
	public boolean isBulkUploadModalOpen() {
		try {
			waitElementToAppear(bulkUploadModalHeader);
			return bulkUploadModalHeader.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Upload XLSX user file")
	public void uploadUserFile(String filePath) {
		try {
			fileUploadInput.sendKeys(filePath);
		} catch (Exception e) {
			// If file input is hidden, unhide with JS then send keys
			((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", fileUploadInput);
			fileUploadInput.sendKeys(filePath);
		}
	}

	@StepName("Cancel Bulk Upload modal")
	public void cancelBulkUpload() {
		try {
			if (cancelBulkUploadButton.isDisplayed()) {
				clickElement(cancelBulkUploadButton);
			}
		} catch (Exception e) {
			waitUtils.clickUsingJS(cancelBulkUploadButton);
		}
		try {
			waitForElementToDisappear(bulkUploadModalHeader);
		} catch (Exception ignored) {}
		waitForBackdropToDisappear();
	}

	// ─────────────────────────────────────────────────────────────────────
	// User Table Row Actions
	// ─────────────────────────────────────────────────────────────────────
	@StepName("Check if user appears in the table")
	public boolean isUserPresent(String emailOrName) {
		try {
			By rowBy = By.xpath("//table//tbody//tr[contains(.,'" + emailOrName + "')]");
			WebElement row = waitUtils.waitForVisibility(rowBy);
			return row != null && row.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Toggle user status (Deactivate / Reactivate)")
	public void toggleUserStatus(String emailOrName) {
		try {
			By actionBtnBy = By.xpath("//tr[contains(.,'" + emailOrName + "')]//button[@aria-label='User actions' or contains(@class,'kebab')]");
			WebElement actionBtn = waitUtils.waitForVisibility(actionBtnBy);
			waitElementToBeClickable(actionBtn);
			clickElement(actionBtn);

			By toggleMenuBy = By.xpath("//div[@role='menuitem' and (contains(.,'Deactivate') or contains(.,'Reactivate'))]");
			WebElement toggleItem = waitUtils.waitForVisibility(toggleMenuBy);
			waitElementToBeClickable(toggleItem);
			clickElement(toggleItem);

			// Confirm modal button
			By confirmBtnBy = By.xpath("//div[@role='dialog']//button[contains(.,'Deactivate') or contains(.,'Reactivate')]");
			WebElement confirmBtn = waitUtils.waitForVisibility(confirmBtnBy);
			waitElementToBeClickable(confirmBtn);
			clickElement(confirmBtn);
			waitForBackdropToDisappear();
		} catch (Exception ignored) {}
	}
}
