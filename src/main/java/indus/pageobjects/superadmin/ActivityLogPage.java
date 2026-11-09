package indus.pageobjects.superadmin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

/**
 * Page object modeling Super Admin Activity Log (/super-admin/activity-log).
 * Features:
 * - Portal-wide read-only audit log feed
 * - Action and category filters (Created, Updated, Deleted, Archived, Permission change)
 * - Search query input
 * - Export audit log button
 */
public class ActivityLogPage extends AbstractComponent {

    public ActivityLogPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@id='sidebar-group-GOVERNANCE' or @id='sidebar-group-SUPER_ADMIN']//a[.//span[normalize-space()='Activity Log']]")
    private WebElement activityLogSidebarLink;

    @FindBy(xpath = "//h1[normalize-space()='Activity Log']")
    private WebElement activityLogHeading;

    @FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'Search audit')]")
    private WebElement searchInput;

    @FindBy(xpath = "//button[contains(.,'Export') or contains(.,'Download')]")
    private WebElement exportButton;

    @FindBy(xpath = "//tr[contains(@class,'hover:bg-')] | //div[contains(@class,'activity-card')]")
    private List<WebElement> logRows;

    public void goToActivityLog() {
        String base = driver.getCurrentUrl().split("#")[0].replaceAll("/(admin|super-admin|manager).*", "");
        driver.get(base + "/super-admin/activity-log");
        waitForPageLoaded();
    }

    public void waitForPageLoaded() {
        waitUtils.waitForVisibility(activityLogHeading);
    }

    public String getPageHeading() {
        waitUtils.waitForVisibility(activityLogHeading);
        return activityLogHeading.getText().trim();
    }

    public void search(String query) {
        waitUtils.waitForVisibility(searchInput);
        searchInput.clear();
        searchInput.sendKeys(query);
    }

    public boolean isExportButtonVisible() {
        try {
            waitUtils.waitForVisibility(exportButton);
            return exportButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getLogCount() {
        try {
            return logRows.size();
        } catch (Exception e) {
            return 0;
        }
    }
}
