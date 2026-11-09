package indus.pageobjects.learner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;
import indus.utils.ToastResponse;

/**
 * Page object modeling Learner Web Access Boundary & APK download portal.
 * In Indus Towers ESH Architecture:
 * - Learners have no desktop web portal.
 * - Login attempts by pure learner accounts are rejected with an explicit error toast
 *   directing them to the mobile app.
 * - The web login screen surfaces a dedicated "Download Android APK" CTA.
 * - Direct navigation to protected routes (/admin/*, /manager/*, /super-admin/*)
 *   is blocked by route guards and redirects to /login.
 */
public class LearnerAccessPage extends AbstractComponent {

    public LearnerAccessPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(.,'Download Android APK') and contains(@href,'.apk')]")
    private WebElement apkDownloadLink;

    @FindBy(xpath = "//p[contains(text(),'Install the mobile companion app')]")
    private WebElement mobileAppBannerText;

    @FindBy(id = "login-input-email")
    private WebElement emailInput;

    @FindBy(id = "login-input-password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    public boolean isApkDownloadLinkVisible() {
        try {
            waitUtils.waitForVisibility(apkDownloadLink);
            return apkDownloadLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getApkDownloadUrl() {
        waitUtils.waitForVisibility(apkDownloadLink);
        return apkDownloadLink.getAttribute("href");
    }

    public boolean isMobileAppBannerVisible() {
        try {
            waitUtils.waitForVisibility(mobileAppBannerText);
            return mobileAppBannerText.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public ToastResponse attemptLearnerLogin(String email, String password) {
        waitUtils.waitForVisibility(emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);
        waitUtils.waitForVisibility(passwordInput);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        waitUtils.waitForClickable(submitButton);
        submitButton.click();
        return toastUtils.captureToast();
    }

    public boolean verifyRouteRedirectsToLogin(String targetPath) {
        String base = driver.getCurrentUrl().split("#")[0].replaceAll("/(login|admin|super-admin|manager).*", "");
        driver.get(base + targetPath);
        waitUtils.waitForUrlContains("/login");
        return driver.getCurrentUrl().contains("/login");
    }
}
