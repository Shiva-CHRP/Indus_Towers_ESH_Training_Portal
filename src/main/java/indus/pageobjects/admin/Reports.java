package indus.pageobjects.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

/**
 * Page object modeling Admin Reports (/admin/reports).
 * Features:
 * - Compliance overview summary metrics
 * - Cluster-wise breakdown navigation (/admin/reports/cluster-wise)
 * - Time period / Date range filters
 * - Export menu trigger
 */
public class Reports extends AbstractComponent {

    public Reports(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@id='sidebar-group-INSIGHTS']//a[.//span[normalize-space()='Reports']]")
    private WebElement reportsSidebarLink;

    @FindBy(xpath = "//h1[contains(text(),'Reports') or contains(text(),'Organisation Reports')]")
    private WebElement reportsHeading;

    @FindBy(xpath = "//button[contains(.,'Cluster-Wise') or contains(.,'Cluster-wise')] | //a[contains(@href,'cluster-wise')]")
    private WebElement clusterWiseReportLink;

    @FindBy(xpath = "//button[contains(.,'Export') or contains(.,'Download')]")
    private WebElement exportButton;

    public void goToReports() {
        String base = driver.getCurrentUrl().split("#")[0].replaceAll("/(admin|super-admin|manager).*", "");
        driver.get(base + "/admin/reports");
        waitForPageLoaded();
    }

    public void waitForPageLoaded() {
        waitUtils.waitForVisibility(reportsHeading);
    }

    public String getReportsHeading() {
        waitUtils.waitForVisibility(reportsHeading);
        return reportsHeading.getText().trim();
    }

    public void goToClusterWiseReport() {
        String base = driver.getCurrentUrl().split("#")[0].replaceAll("/(admin|super-admin|manager).*", "");
        driver.get(base + "/admin/reports/cluster-wise");
        waitUtils.waitForUrlContains("/admin/reports/cluster-wise");
    }

    public boolean isExportButtonVisible() {
        try {
            waitUtils.waitForVisibility(exportButton);
            return exportButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
