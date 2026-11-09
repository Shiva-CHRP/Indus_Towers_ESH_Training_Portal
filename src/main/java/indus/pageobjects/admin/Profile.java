package indus.pageobjects.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

/**
 * Page object modeling Admin Profile (/admin/profile).
 * Features:
 * - Admin Personal Information display
 * - Change Password modal trigger & password form submission
 * - 2FA toggle button
 */
public class Profile extends AbstractComponent {

    public Profile(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@id='sidebar-group-ACCOUNT']//a[.//span[normalize-space()='Profile']]")
    private WebElement profileSidebarLink;

    @FindBy(xpath = "//h3[normalize-space()='Personal Information']")
    private WebElement personalInformationHeading;

    @FindBy(xpath = "//button[contains(.,'Change Password')]")
    private WebElement changePasswordBtn;

    @FindBy(xpath = "//h2[normalize-space()='Change Password'] | //h3[normalize-space()='Change Password']")
    private WebElement changePasswordModalTitle;

    @FindBy(xpath = "//input[@name='current' or @placeholder='Enter current password']")
    private WebElement currentPasswordInput;

    @FindBy(xpath = "//input[@name='next' or @placeholder='Enter new password']")
    private WebElement newPasswordInput;

    @FindBy(xpath = "//input[@name='confirm' or @placeholder='Confirm new password']")
    private WebElement confirmPasswordInput;

    @FindBy(xpath = "//button[@type='submit' and contains(.,'Update Password')]")
    private WebElement updatePasswordSubmitBtn;

    @FindBy(xpath = "//button[normalize-space()='Cancel']")
    private WebElement cancelModalBtn;

    public void goToProfile() {
        String base = driver.getCurrentUrl().split("#")[0].replaceAll("/(admin|super-admin|manager).*", "");
        driver.get(base + "/admin/profile");
        waitForPageLoaded();
    }

    public void waitForPageLoaded() {
        waitUtils.waitForVisibility(personalInformationHeading);
    }

    public boolean isPersonalInformationDisplayed() {
        try {
            waitUtils.waitForVisibility(personalInformationHeading);
            return personalInformationHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void openChangePasswordModal() {
        waitUtils.waitForClickable(changePasswordBtn);
        changePasswordBtn.click();
        waitUtils.waitForVisibility(changePasswordModalTitle);
    }

    public boolean isChangePasswordModalOpen() {
        try {
            waitUtils.waitForVisibility(changePasswordModalTitle);
            return changePasswordModalTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterPasswords(String currentPw, String newPw, String confirmPw) {
        waitUtils.waitForVisibility(currentPasswordInput);
        currentPasswordInput.clear();
        currentPasswordInput.sendKeys(currentPw);

        waitUtils.waitForVisibility(newPasswordInput);
        newPasswordInput.clear();
        newPasswordInput.sendKeys(newPw);

        waitUtils.waitForVisibility(confirmPasswordInput);
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(confirmPw);
    }

    public void clickUpdatePassword() {
        waitUtils.waitForClickable(updatePasswordSubmitBtn);
        updatePasswordSubmitBtn.click();
    }

    public void cancelChangePassword() {
        waitUtils.waitForClickable(cancelModalBtn);
        cancelModalBtn.click();
    }
}
