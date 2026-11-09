package indus.pageobjects.superadmin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;
import indus.annotations.StepName;

public class RoleEditorPage extends AbstractComponent {

	public RoleEditorPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h1[contains(@class,'font-bold')]")
	private WebElement roleHeading;

	@FindBy(xpath = "//span[normalize-space()='Locked']")
	private WebElement lockedBadge;

	@FindBy(xpath = "//button[contains(.,'Save changes')]")
	private WebElement saveChangesButton;

	@FindBy(xpath = "//button[@role='tab' and contains(.,'Permissions')]")
	private WebElement permissionsTab;

	@FindBy(xpath = "//button[@role='tab' and contains(.,'Users')]")
	private WebElement usersTab;

	@FindBy(xpath = "//button[contains(@aria-label,'Back') or .//*[local-name()='svg']]")
	private WebElement backButton;

	@StepName("Verify if Role Editor is loaded")
	public boolean isRoleEditorOpen() {
		try {
			waitElementToAppear(roleHeading);
			return roleHeading.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Get role title from Role Editor header")
	public String getRoleTitle() {
		waitElementToAppear(roleHeading);
		return roleHeading.getText().trim();
	}

	@StepName("Verify if role has locked template badge")
	public boolean isRoleLocked() {
		try {
			return lockedBadge.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Toggle permission checkbox for a feature")
	public void toggleFeatureCheckbox(String featureName) {
		By checkboxBy = By.xpath("//div[.//span[normalize-space()='" + featureName + "'] or .//p[normalize-space()='" + featureName + "']]//button[@role='checkbox']");
		WebElement cb = waitUtils.waitForVisibility(checkboxBy);
		waitElementToBeClickable(cb);
		cb.click();
	}

	@StepName("Check if Save Changes button is enabled")
	public boolean isSaveButtonEnabled() {
		try {
			return saveChangesButton.isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Click Save Changes button")
	public void clickSaveChanges() {
		try {
			if (saveChangesButton.isEnabled()) {
				waitElementToBeClickable(saveChangesButton);
				saveChangesButton.click();
			}
		} catch (Exception ignored) {}
	}

	@StepName("Navigate back to Roles list")
	public void clickBack() {
		try {
			waitElementToBeClickable(backButton);
			backButton.click();
		} catch (Exception ignored) {}
	}
}
