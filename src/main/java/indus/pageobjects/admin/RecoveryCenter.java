package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

/**
 * Page object modeling Recovery Center (/admin/recovery-center).
 * Features:
 * - Soft-deleted items table and restored history tabs
 * - Type filtering chips (All Types, Users, Courses, Groups, etc.)
 * - Search query filter
 * - Restore & permanent delete triggers
 */
public class RecoveryCenter extends AbstractComponent {

    public RecoveryCenter(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@id='sidebar-group-TOOLS & SUPPORT']//a[.//span[normalize-space()='Recovery Center']]")
    private WebElement recoveryCenterSidebarLink;

    @FindBy(xpath = "//h1[normalize-space()='Recovery Center']")
    private WebElement recoveryCenterHeading;

    @FindBy(xpath = "//button[normalize-space()='Deleted Items']")
    private WebElement deletedItemsTab;

    @FindBy(xpath = "//button[normalize-space()='Restored History']")
    private WebElement restoredHistoryTab;

    @FindBy(xpath = "//input[contains(@placeholder,'Search deleted items') or contains(@placeholder,'Search')]")
    private WebElement searchInput;

    @FindBy(xpath = "//div[contains(@class,'grid')]//div[contains(@class,'rounded-xl border')]")
    private List<WebElement> deletedItemCards;

    public void goToRecoveryCenter() {
        String base = driver.getCurrentUrl().split("#")[0].replaceAll("/(admin|super-admin|manager).*", "");
        driver.get(base + "/admin/recovery-center");
        waitForPageLoaded();
    }

    public void waitForPageLoaded() {
        waitUtils.waitForVisibility(recoveryCenterHeading);
    }

    public String getPageHeading() {
        waitUtils.waitForVisibility(recoveryCenterHeading);
        return recoveryCenterHeading.getText().trim();
    }

    public void selectDeletedItemsTab() {
        waitUtils.waitForClickable(deletedItemsTab);
        deletedItemsTab.click();
    }

    public void selectRestoredHistoryTab() {
        waitUtils.waitForClickable(restoredHistoryTab);
        restoredHistoryTab.click();
    }

    public void filterByType(String typeLabel) {
        By typeChipBy = By.xpath("//button[contains(.,'" + typeLabel + "')]");
        waitUtils.waitForClickable(typeChipBy);
        driver.findElement(typeChipBy).click();
    }

    public void search(String query) {
        waitUtils.waitForVisibility(searchInput);
        searchInput.clear();
        searchInput.sendKeys(query);
    }

    public int getDeletedItemsCount() {
        try {
            return deletedItemCards.size();
        } catch (Exception e) {
            return 0;
        }
    }
}
