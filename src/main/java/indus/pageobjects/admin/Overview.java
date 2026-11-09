package indus.pageobjects.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

/**
 * Page object modeling Admin Overview & Dashboard (/admin/dashboard).
 * Features:
 * - Welcome greeting banner with admin name
 * - Overall Compliance hero card with circular SVG ProgressRing
 * - KPI Strip: Users, Courses, Compliance, Overdue
 * - Leaderboard: Top Learners and Needs Attention panels
 * - Refresh button action
 */
public class Overview extends AbstractComponent {

    public Overview(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@id='sidebar-group-MAIN']//a[.//span[normalize-space()='Overview']]")
    private WebElement overviewSidebarLink;

    @FindBy(xpath = "//h1[contains(text(),'Welcome back')]")
    private WebElement welcomeHeading;

    @FindBy(xpath = "//p[normalize-space()='Overall Compliance']")
    private WebElement overallComplianceHeader;

    @FindBy(xpath = "//button[contains(.,'Refresh')]")
    private WebElement refreshButton;

    @FindBy(xpath = "//p[normalize-space()='Overall Compliance']/preceding::span[contains(@class,'font-bold')][1] | //div[contains(@class,'relative shrink-0')]//span[contains(@class,'font-bold')]")
    private WebElement compliancePercentageText;

    @FindBy(xpath = "//p[normalize-space()='Leaderboard'] | //div[contains(@class,'border-l-4 border-indus-green')]")
    private WebElement leaderboardSection;

    public void goToOverview() {
        String base = driver.getCurrentUrl().split("#")[0].replaceAll("/(admin|super-admin|manager).*", "");
        driver.get(base + "/admin/dashboard");
        waitForPageLoaded();
    }

    public void waitForPageLoaded() {
        waitUtils.waitForVisibility(overallComplianceHeader);
    }

    public String getWelcomeText() {
        waitUtils.waitForVisibility(welcomeHeading);
        return welcomeHeading.getText().trim();
    }

    public boolean isOverallComplianceDisplayed() {
        try {
            waitUtils.waitForVisibility(overallComplianceHeader);
            return overallComplianceHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCompliancePercentage() {
        waitUtils.waitForVisibility(compliancePercentageText);
        return compliancePercentageText.getText().trim();
    }

    public String getKpiValue(String label) {
        By kpiLocator = By.xpath("//div[.//span[normalize-space()='" + label + "']]//p[contains(@class,'font-bold')] | //span[normalize-space()='" + label + "']/preceding-sibling::p");
        waitUtils.waitForVisibility(kpiLocator);
        return driver.findElement(kpiLocator).getText().trim();
    }

    public boolean isLeaderboardDisplayed() {
        try {
            waitUtils.waitForVisibility(leaderboardSection);
            return leaderboardSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickRefresh() {
        waitUtils.waitForClickable(refreshButton);
        refreshButton.click();
    }
}
