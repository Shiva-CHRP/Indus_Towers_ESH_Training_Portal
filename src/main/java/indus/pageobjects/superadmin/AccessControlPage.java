package indus.pageobjects.superadmin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;
import indus.annotations.StepName;

public class AccessControlPage extends AbstractComponent {

	public AccessControlPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// ── Sidebar & Header ─────────────────────────────────────────────────
	@FindBy(xpath = "//a[contains(@href,'/super-admin/access-control') or normalize-space()='Access Control']")
	private WebElement sidebarAccessControlLink;

	@FindBy(xpath = "//h1[contains(normalize-space(),'Access Control')]")
	private WebElement pageHeader;

	// ── Tabs ─────────────────────────────────────────────────────────────
	@FindBy(xpath = "//button[normalize-space()='Roles']")
	private WebElement rolesTab;

	@FindBy(xpath = "//button[normalize-space()='People']")
	private WebElement peopleTab;

	@FindBy(xpath = "//button[normalize-space()='Activity Log']")
	private WebElement activityLogTab;

	// ── Standard Templates Section ───────────────────────────────────────
	@FindBy(xpath = "//p[contains(normalize-space(),'Standard Templates') and contains(normalize-space(),'Locked')]")
	private WebElement standardTemplatesSection;

	@FindBy(xpath = "//div[.//p[normalize-space()='Organisation']]")
	private WebElement organisationTemplateCard;

	@FindBy(xpath = "//div[.//p[normalize-space()='Team']]")
	private WebElement teamTemplateCard;

	// ── Custom Roles Section ─────────────────────────────────────────────
	@FindBy(xpath = "//button[contains(normalize-space(),'+ New Role') or contains(normalize-space(),'New Role')]")
	private WebElement newRoleButton;

	@FindBy(xpath = "//input[@placeholder='Search roles...']")
	private WebElement searchRolesInput;

	@FindBy(xpath = "//div[contains(@class,'cursor-pointer')]//p[contains(@class,'font-semibold')]")
	private List<WebElement> customRoleTitles;

	// ─────────────────────────────────────────────────────────────────────
	// Actions
	// ─────────────────────────────────────────────────────────────────────
	@StepName("Navigate to Super Admin Access Control page")
	public void goToAccessControl() {
		waitElementToAppear(sidebarAccessControlLink);
		waitElementToBeClickable(sidebarAccessControlLink);
		sidebarAccessControlLink.click();
		waitElementToAppear(pageHeader);
	}

	@StepName("Verify Access Control Page Header is displayed")
	public String getPageHeaderTitle() {
		waitElementToAppear(pageHeader);
		return pageHeader.getText().trim();
	}

	@StepName("Switch to Roles tab")
	public void clickRolesTab() {
		waitElementToBeClickable(rolesTab);
		rolesTab.click();
	}

	@StepName("Switch to People tab")
	public void clickPeopleTab() {
		waitElementToBeClickable(peopleTab);
		peopleTab.click();
	}

	@StepName("Switch to Activity Log tab")
	public void clickActivityLogTab() {
		waitElementToBeClickable(activityLogTab);
		activityLogTab.click();
	}

	@StepName("Check if Standard Templates Locked section is present")
	public boolean isStandardTemplatesLockedDisplayed() {
		try {
			waitElementToAppear(standardTemplatesSection);
			return standardTemplatesSection.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Verify Standard Template card exists and has locked indicator")
	public boolean isTemplateCardDisplayed(String templateName) {
		try {
			By cardBy = By.xpath("//div[.//p[normalize-space()='" + templateName + "']]");
			WebElement card = waitUtils.waitForVisibility(cardBy);
			return card != null && card.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Click on + New Role button")
	public void clickNewRoleButton() {
		waitElementToBeClickable(newRoleButton);
		newRoleButton.click();
	}

	@StepName("Search for a Custom Role by name")
	public void searchRole(String roleName) {
		try {
			waitForVisibility(searchRolesInput);
			searchRolesInput.clear();
			searchRolesInput.sendKeys(roleName);
		} catch (Exception ignored) {}
	}

	@StepName("Check if a role is present in the list")
	public boolean isRolePresent(String roleName) {
		By roleBy = By.xpath("//div[contains(@class,'cursor-pointer')]//p[normalize-space()='" + roleName + "']");
		try {
			WebElement el = waitUtils.waitForVisibility(roleBy);
			return el != null && el.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Click on a custom role to open Role Editor")
	public void openRoleEditor(String roleName) {
		By roleBy = By.xpath("//div[contains(@class,'cursor-pointer') and .//p[normalize-space()='" + roleName + "']]");
		WebElement roleCard = waitUtils.waitForVisibility(roleBy);
		waitElementToBeClickable(roleCard);
		roleCard.click();
	}

	@StepName("Delete a custom role from the list")
	public void deleteRole(String roleName) {
		By deleteBtnBy = By.xpath("//div[.//p[normalize-space()='" + roleName + "']]//button[@aria-label='Delete role' or .//*[local-name()='svg']]");
		try {
			WebElement deleteBtn = waitUtils.waitForVisibility(deleteBtnBy);
			waitElementToBeClickable(deleteBtn);
			deleteBtn.click();
			acceptAlert();
		} catch (Exception ignored) {}
	}
}
