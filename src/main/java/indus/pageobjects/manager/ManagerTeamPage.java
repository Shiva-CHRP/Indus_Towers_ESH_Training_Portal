package indus.pageobjects.manager;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

/**
 * Page object modeling Manager Overview & Team page (/manager/team).
 * Features:
 * - Team Compliance hero banner & circular progress ring
 * - KPI Strip: Total, Compliant, In Progress, Overdue
 * - Filter chips: All, Overdue, In Progress, Compliant, Not Enrolled
 * - Search direct reports by name, designation, site
 * - Member Cards leading to MemberDetail
 */
public class ManagerTeamPage extends AbstractComponent {

    public ManagerTeamPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h1[contains(text(),'Welcome back')]")
    private WebElement welcomeHeading;

    @FindBy(xpath = "//p[normalize-space()='Team Compliance']")
    private WebElement teamComplianceHeader;

    @FindBy(xpath = "//button[contains(.,'Refresh')]")
    private WebElement refreshButton;

    @FindBy(xpath = "//p[normalize-space()='Team Compliance']/preceding::span[contains(@class,'font-bold')][1] | //div[contains(@class,'relative shrink-0')]//span[contains(@class,'font-bold')]")
    private WebElement compliancePercentageText;

    @FindBy(xpath = "//input[@placeholder='Search by name, designation, site...']")
    private WebElement searchInput;

    @FindBy(xpath = "//p[normalize-space()='Leaderboard']")
    private WebElement leaderboardHeader;

    @FindBy(xpath = "//h3[normalize-space()='My Team']")
    private WebElement myTeamHeader;

    @FindBy(xpath = "//button[contains(@class,'group') and .//span[contains(@class,'font-semibold')]]")
    private List<WebElement> memberCards;

    @FindBy(xpath = "//div[contains(@class,'animate-spin')]")
    private WebElement loaderSpinner;

    public void goToTeam() {
        driver.get(driver.getCurrentUrl().split("#")[0].replaceAll("/(admin|super-admin|manager).*", "") + "/manager/team");
        waitForPageLoaded();
    }

    public void waitForPageLoaded() {
        waitUtils.waitForVisibility(teamComplianceHeader);
    }

    public String getWelcomeText() {
        waitUtils.waitForVisibility(welcomeHeading);
        return welcomeHeading.getText().trim();
    }

    public boolean isTeamComplianceDisplayed() {
        try {
            waitUtils.waitForVisibility(teamComplianceHeader);
            return teamComplianceHeader.isDisplayed();
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
        WebElement el = waitUtils.waitForVisibility(kpiLocator);
        return el.getText().trim();
    }

    public void filterByStatus(String statusLabel) {
        By chipLocator = By.xpath("//button[normalize-space()='" + statusLabel + "']");
        waitUtils.waitForClickable(chipLocator);
        driver.findElement(chipLocator).click();
        waitForTeamLoaded();
    }

    public void searchMember(String query) {
        waitUtils.waitForVisibility(searchInput);
        searchInput.clear();
        searchInput.sendKeys(query);
        waitForTeamLoaded();
    }

    public int getMemberCardCount() {
        try {
            return memberCards.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public MemberDetailPage selectMemberByIndex(int index) {
        waitUtils.waitForVisibility(memberCards.get(index));
        memberCards.get(index).click();
        return new MemberDetailPage(driver);
    }

    public MemberDetailPage selectMemberByName(String name) {
        By memberCardBy = By.xpath("//button[contains(@class,'group')][.//span[normalize-space()='" + name + "']]");
        waitUtils.waitForClickable(memberCardBy);
        driver.findElement(memberCardBy).click();
        return new MemberDetailPage(driver);
    }

    public boolean isLeaderboardDisplayed() {
        try {
            waitUtils.waitForVisibility(leaderboardHeader);
            return leaderboardHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForTeamLoaded() {
        try {
            waitUtils.waitForInvisibility(By.xpath("//*[contains(text(),'Loading team...')]"));
        } catch (Exception ignored) {
        }
    }
}
