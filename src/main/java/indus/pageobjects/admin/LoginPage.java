package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;
import indus.utils.ConfigReader;
import indus.annotations.StepName;

public class LoginPage extends AbstractComponent {

	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// ── Tabs ─────────────────────────────────────────────────────────────
	@FindBy(xpath = "//div//span[contains(text(),'Email & Password')] | //button[contains(.,'Email & Password')]")
	private WebElement emailLoginTab;

	@FindBy(xpath = "//div//span[contains(text(),'Phone & OTP')] | //button[contains(.,'Phone & OTP')]")
	private WebElement phoneOtpTab;

	// ── Email & Password Form ────────────────────────────────────────────
	@FindBy(xpath = "//input[@type='email']")
	private WebElement emailAddress;

	@FindBy(xpath = "//input[@type='password']")
	private WebElement password;

	@FindBy(xpath = "//button[@type='submit' and (normalize-space()='Sign In' or normalize-space()='Sign in')]")
	private WebElement signinButton;

	// ── 2FA & OTP Form ───────────────────────────────────────────────────
	@FindBy(xpath = "//input[@inputmode='numeric']")
	private List<WebElement> otpInputs;

	@FindBy(xpath = "//button[@type='submit' and contains(.,'Verify & Sign In')] | //button[@type='submit' and contains(.,'Verify')]")
	private WebElement verifyOtpButton;

	@FindBy(xpath = "//button[contains(.,'Resend OTP')]")
	private WebElement resendOtpButton;

	@FindBy(xpath = "//button[contains(.,'Change')]")
	private WebElement changeOtpTargetButton;

	// ── Active Sessions Modal ────────────────────────────────────────────
	@FindBy(xpath = "//h3[normalize-space()='Active Session Limit Reached']")
	private WebElement activeSessionModalTitle;

	@FindBy(xpath = "//button[contains(.,'Force Logout')]")
	private WebElement forceLogoutButton;

	// ── Validation Errors & Alerts ───────────────────────────────────────
	@FindBy(xpath = "//p[contains(@class,'text-status-fail') or contains(@class,'bg-red-50')]")
	private WebElement alertErrorBanner;

	@FindBy(xpath = "//form//p[contains(@class,'text-status-fail') or contains(@class,'text-red')]")
	private List<WebElement> fieldErrors;

	// ── Dashboard & Profile Indicators ───────────────────────────────────
	@FindBy(xpath = "//p[normalize-space()='Overall Compliance']")
	private WebElement overallCompliance;

	@FindBy(xpath = "//h1[contains(.,'Access Control')] | //a[contains(@href,'access-control')]")
	private WebElement superAdminAccessControlIndicator;

	@FindBy(xpath = "//header//button[.//span[contains(@class,'hidden md:inline-flex')] and .//div]")
	private WebElement profile;

	@FindBy(xpath = "//button[contains(normalize-space(.),'Sign out')]")
	private WebElement signOut;

	@FindBy(xpath = "//div[contains(@class,'flex justify-end')]//button[.//text()[contains(.,'Sign out')]]")
	private WebElement confirmSignOut;

	// ─────────────────────────────────────────────────────────────────────
	// Tab Actions
	// ─────────────────────────────────────────────────────────────────────
	@StepName("Click Email & Password tab")
	public void goToLogin() {
		driver.get(ConfigReader.getUrl());
	}

	public void loginByEmail() {
		waitForBackdropToDisappear();
		waitElementToAppear(emailLoginTab);
		waitElementToBeClickable(emailLoginTab);
		clickElement(emailLoginTab);
	}

	@StepName("Click Phone & OTP tab")
	public void loginByPhone() {
		waitForBackdropToDisappear();
		waitElementToAppear(phoneOtpTab);
		waitElementToBeClickable(phoneOtpTab);
		clickElement(phoneOtpTab);
	}

	// ─────────────────────────────────────────────────────────────────────
	// Input Fields
	// ─────────────────────────────────────────────────────────────────────
	@StepName("Enter User Email Address")
	public void enterUserEmail(String username) {
		waitForVisibility(emailAddress);
		waitElementToBeClickable(emailAddress);
		clearAndType(emailAddress, username);
	}

	@StepName("Enter User Password")
	public void enterPassword(String pwd) {
		waitForVisibility(password);
		waitElementToBeClickable(password);
		clearAndType(password, pwd);
	}

	@StepName("Click Sign In Button")
	public void clickLogin() {
		waitElementToBeClickable(signinButton);
		clickElement(signinButton);
	}

	// ─────────────────────────────────────────────────────────────────────
	// 2FA / OTP Handling (Resilient across 2FA prompt or direct login)
	// ─────────────────────────────────────────────────────────────────────
	@StepName("Check if 2FA OTP Screen is displayed")
	public boolean isOtpScreenDisplayed() {
		try {
			By otpLocator = By.xpath("//input[@inputmode='numeric']");
			List<WebElement> inputs = driver.findElements(otpLocator);
			return !inputs.isEmpty() && inputs.get(0).isDisplayed();
		} catch (Exception ignored) {}
		return false;
	}

	@StepName("Wait for 2FA OTP Screen to appear")
	public void waitForOtpScreen() {
		try {
			By otpLocator = By.xpath("//input[@inputmode='numeric']");
			waitUtils.waitUntil(d -> {
				List<WebElement> otps = d.findElements(otpLocator);
				if (!otps.isEmpty() && otps.get(0).isDisplayed()) {
					return true;
				}
				String url = d.getCurrentUrl();
				if (url.contains("/superadmin") || url.contains("/admin") || url.contains("/manager") || url.contains("/learner")) {
					return true;
				}
				List<WebElement> sessionLimits = d.findElements(By.xpath("//h3[normalize-space()='Active Session Limit Reached']"));
				if (!sessionLimits.isEmpty() && sessionLimits.get(0).isDisplayed()) {
					return true;
				}
				return null;
			});
		} catch (Exception ignored) {
		}
	}

	@StepName("Enter 6-digit 2FA OTP")
	public void enterOtp(String otp) {
		try {
			By otpLocator = By.xpath("//input[@inputmode='numeric']");
			List<WebElement> inputs = driver.findElements(otpLocator);
			if (!inputs.isEmpty() && inputs.get(0).isDisplayed()) {
				inputs.get(0).clear();
				for (int i = 0; i < otp.length() && i < inputs.size(); i++) {
					inputs.get(i).sendKeys(String.valueOf(otp.charAt(i)));
				}
			}
		} catch (Exception ignored) {}
	}

	@StepName("Click Verify & Sign In for 2FA")
	public void clickVerifyOtp() {
		try {
			By verifyBtn = By.xpath("//button[@type='submit' and (contains(.,'Verify') or contains(.,'Sign In'))]");
			List<WebElement> buttons = driver.findElements(verifyBtn);
			if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
				clickElement(buttons.get(0));
			}
		} catch (Exception ignored) {}
	}

	@StepName("Handle Active Sessions Limit if modal appears")
	public boolean handleActiveSessionsIfPresent() {
		try {
			By modalBy = By.xpath("//h3[normalize-space()='Active Session Limit Reached']");
			By otpBy = By.xpath("//input[@inputmode='numeric']");
			By errorBannerBy = By.xpath("//p[contains(@class,'text-status-fail') or contains(@class,'bg-red-50')]");
			By forceLogoutBy = By.xpath("//button[contains(.,'Force Logout')]");

			waitUtils.waitUntil(d -> {
				if (!d.getCurrentUrl().contains("/login")) {
					return true;
				}
				List<WebElement> modals = d.findElements(modalBy);
				if (!modals.isEmpty() && modals.get(0).isDisplayed()) {
					return true;
				}
				List<WebElement> otps = d.findElements(otpBy);
				if (!otps.isEmpty() && otps.get(0).isDisplayed()) {
					return true;
				}
				List<WebElement> errs = d.findElements(errorBannerBy);
				if (!errs.isEmpty() && errs.get(0).isDisplayed()) {
					return true;
				}
				return null;
			});

			List<WebElement> titles = driver.findElements(modalBy);
			if (!titles.isEmpty() && titles.get(0).isDisplayed()) {
				waitUtils.waitForClickable(forceLogoutBy);
				List<WebElement> btns = driver.findElements(forceLogoutBy);
				if (!btns.isEmpty()) {
					waitUtils.clickUsingJS(btns.get(0));
				}
				waitForBackdropToDisappear();
				waitUtils.waitUntil(d -> !d.getCurrentUrl().contains("/login")
						|| (!d.findElements(otpBy).isEmpty() && d.findElements(otpBy).get(0).isDisplayed())
						|| (!d.findElements(errorBannerBy).isEmpty() && d.findElements(errorBannerBy).get(0).isDisplayed()));
				return true;
			}
		} catch (Exception ignored) {
			// Modal did not appear or login proceeded normally
		}
		return false;
	}

	// ─────────────────────────────────────────────────────────────────────
	// Validation Checks
	// ─────────────────────────────────────────────────────────────────────
	@StepName("Get Alert Error Banner Text")
	public String getAlertErrorText() {
		try {
			waitElementToAppear(alertErrorBanner);
			return alertErrorBanner.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Check if Alert Error Banner is displayed")
	public boolean isAlertErrorBannerDisplayed() {
		try {
			waitElementToAppear(alertErrorBanner);
			return alertErrorBanner.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Get Field Validation Error Count")
	public int getFieldErrorCount() {
		try {
			return fieldErrors.size();
		} catch (Exception e) {
			return 0;
		}
	}

	@StepName("Check if specific field error text is present")
	public boolean hasFieldError(String expectedText) {
		try {
			return fieldErrors.stream()
					.map(WebElement::getText)
					.map(String::trim)
					.anyMatch(msg -> msg.toLowerCase().contains(expectedText.toLowerCase()));
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Check if Sign In button is enabled")
	public boolean isSignInButtonEnabled() {
		try {
			return signinButton.isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Check if current URL is Login page")
	public boolean isAtLoginPage() {
		try {
			return driver.getCurrentUrl().contains("/login");
		} catch (Exception e) {
			return false;
		}
	}

	// ─────────────────────────────────────────────────────────────────────
	// Post-Login Surface Indicators
	// ─────────────────────────────────────────────────────────────────────
	@StepName("Admin Overview Page Header Verification")
	public String adminOverallCompliance() {
		waitElementToAppear(overallCompliance);
		return overallCompliance.getText();
	}

	@StepName("Verify Super Admin Access Control is displayed")
	public boolean isSuperAdminAccessControlDisplayed() {
		try {
			waitElementToAppear(superAdminAccessControlIndicator);
			return superAdminAccessControlIndicator.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ─────────────────────────────────────────────────────────────────────
	// Sign Out (Synchronization Clean without Thread.sleep)
	// ─────────────────────────────────────────────────────────────────────
	@StepName("Click on the Profile Icon")
	public void clickProfile() {
		dismissAnyOpenModal();
		waitForBackdropToDisappear();
		try {
			waitElementToAppear(profile);
			clickElement(profile);
		} catch (Exception e) {
			try {
				waitUtils.clickUsingJS(profile);
			} catch (Exception ignored) {}
		}
	}

	@StepName("Click Sign out Button")
	public void clickSignOut() {
		try {
			waitElementToBeClickable(signOut);
			clickElement(signOut);
		} catch (Exception e) {
			try {
				waitUtils.clickUsingJS(profile);
				By menuSignOut = By.xpath("//div[contains(@class,'shadow-xl')]//button[contains(.,'Sign out')] | //button[contains(normalize-space(.),'Sign out')]");
				waitUtils.waitForClickable(menuSignOut);
				WebElement btn = driver.findElement(menuSignOut);
				waitUtils.clickUsingJS(btn);
			} catch (Exception ex) {
				try {
					WebElement btn = driver.findElement(By.xpath("//button[contains(.,'Sign out')]"));
					waitUtils.clickUsingJS(btn);
				} catch (Exception ignored) {}
			}
		}
	}

	@StepName("Click Confirm Sign out Button")
	public void clickConfirmSignOut() {
		try {
			waitElementToBeClickable(confirmSignOut);
			clickElement(confirmSignOut);
		} catch (Exception e) {
			try {
				By modalConfirm = By.xpath("//div[@role='dialog']//button[contains(.,'Sign out')] | //div[contains(@class,'flex justify-end')]//button[contains(.,'Sign out')]");
				waitUtils.waitForClickable(modalConfirm);
				WebElement btn = driver.findElement(modalConfirm);
				waitUtils.clickUsingJS(btn);
			} catch (Exception ignored) {}
		}
		waitForBackdropToDisappear();
	}

	@StepName("Perform Complete Sign Out and wait for Login Page")
	public void signOutFromPortal() {
		try {
			clickProfile();
			clickSignOut();
			clickConfirmSignOut();
			waitElementToAppear(emailLoginTab);
		} catch (Exception e) {
			try {
				driver.get(ConfigReader.getUrl());
				waitElementToAppear(emailLoginTab);
			} catch (Exception ignored) {}
		}
	}

	public void clearFields() {
		try {
			emailAddress.clear();
			password.clear();
		} catch (Exception e) {
			clearUsingJS(emailAddress);
			clearUsingJS(password);
		}
	}

	private void clearAndType(WebElement el, String text) {
		try {
			el.clear();
		} catch (Exception e) {
			clearUsingJS(el);
		}
		if (text != null) {
			el.sendKeys(text);
		}
	}

	private void clearUsingJS(WebElement el) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].value='';", el);
		} catch (Exception ignored) {}
	}
}
