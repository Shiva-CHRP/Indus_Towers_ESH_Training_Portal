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

	// ── Sidebar & Header ─────────────────────────────────────────────────
	@FindBy(xpath = "//div[@id='sidebar-group-MANAGEMENT']//a[.//span[normalize-space()='Users']]")
	private WebElement usersMenu;

	@FindBy(xpath = "//h1[normalize-space()='User Management']")
	private WebElement userManagementHeader;

	// ── Header Actions ───────────────────────────────────────────────────
	@FindBy(xpath = "//button[contains(.,'Add User')]")
	private WebElement addUserButton;

	@FindBy(xpath = "//button[contains(.,'Bulk Upload')]")
	private WebElement bulkUploadButton;

	@FindBy(xpath = "//button[contains(.,'Export')]")
	private WebElement exportMenuButton;

	// ── Filter Chips ─────────────────────────────────────────────────────
	@FindBy(xpath = "//button[normalize-space()='All']")
	private WebElement allTab;

	@FindBy(xpath = "//button[normalize-space()='Learners']")
	private WebElement learnersTab;

	@FindBy(xpath = "//button[normalize-space()='Managers']")
	private WebElement managersTab;

	@FindBy(xpath = "//button[normalize-space()='Admins']")
	private WebElement adminsTab;

	@FindBy(xpath = "//button[normalize-space()='Inactive']")
	private WebElement inactiveTab;

	// ── Search Input ─────────────────────────────────────────────────────
	@FindBy(xpath = "//input[contains(@placeholder,'Search by name')]")
	private WebElement searchInput;

	// ── Add User Modal Form ──────────────────────────────────────────────
	@FindBy(xpath = "//h2[normalize-space()='Add User']")
	private WebElement addUserModalHeader;

	@FindBy(xpath = "//label[contains(.,'Full Name')]/following-sibling::input | //input[@placeholder='e.g. Rahul Sharma']")
	private WebElement fullNameInput;

	@FindBy(xpath = "//label[contains(.,'Employee ID')]/following-sibling::input | //input[@placeholder='e.g. IND-00123']")
	private WebElement employeeIdInput;

	@FindBy(xpath = "//label[contains(.,'Email')]/following-sibling::input | //input[@placeholder='rahul.sharma@industowers.com']")
	private WebElement emailInput;

	@FindBy(xpath = "//label[contains(.,'Phone')]/following-sibling::input | //input[@placeholder='+91 98765 43210']")
	private WebElement phoneInput;

	@FindBy(xpath = "//label[contains(.,'Password')]/following-sibling::div//input | //input[@placeholder='Enter password']")
	private WebElement passwordInput;

	@FindBy(xpath = "//button[@type='submit' and contains(.,'Create Account')]")
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
		waitElementToBeClickable(usersMenu);
		usersMenu.click();
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
		learnersTab.click();
	}

	@StepName("Filter users by Managers tab")
	public void navigateToManagersTab() {
		waitElementToBeClickable(managersTab);
		managersTab.click();
	}

	@StepName("Filter users by Admins tab")
	public void navigateToAdminTab() {
		waitElementToBeClickable(adminsTab);
		adminsTab.click();
	}

	@StepName("Filter users by Inactive tab")
	public void navigateToInActiveTab() {
		waitElementToBeClickable(inactiveTab);
		inactiveTab.click();
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
		waitElementToBeClickable(addUserButton);
		addUserButton.click();
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

		passwordInput.clear();
		passwordInput.sendKeys(password);
	}

	@StepName("Submit Add User form")
	public void submitUserForm() {
		waitElementToBeClickable(createAccountButton);
		createAccountButton.click();
	}

	@StepName("Cancel Add User form")
	public void cancelAddUser() {
		waitElementToBeClickable(cancelAccountButton);
		cancelAccountButton.click();
		waitForElementToDisappear(addUserModalHeader);
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
		waitElementToBeClickable(bulkUploadButton);
		bulkUploadButton.click();
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
		waitElementToBeClickable(cancelBulkUploadButton);
		cancelBulkUploadButton.click();
		waitForElementToDisappear(bulkUploadModalHeader);
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
			actionBtn.click();

			By toggleMenuBy = By.xpath("//div[@role='menuitem' and (contains(.,'Deactivate') or contains(.,'Reactivate'))]");
			WebElement toggleItem = waitUtils.waitForVisibility(toggleMenuBy);
			waitElementToBeClickable(toggleItem);
			toggleItem.click();

			// Confirm modal button
			By confirmBtnBy = By.xpath("//div[@role='dialog']//button[contains(.,'Deactivate') or contains(.,'Reactivate')]");
			WebElement confirmBtn = waitUtils.waitForVisibility(confirmBtnBy);
			waitElementToBeClickable(confirmBtn);
			confirmBtn.click();
		} catch (Exception ignored) {}
	}
}
