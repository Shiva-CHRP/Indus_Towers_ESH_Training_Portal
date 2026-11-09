package indus.pageobjects.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

/**
 * Page object modeling Organogram (/admin/organogram & /super-admin/organogram).
 * Features:
 * - View mode switcher: List, Org Chart, Table, Canvas
 * - Node search and Department/Role/Geography filters
 */
public class Organogram extends AbstractComponent {

    public Organogram(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@id='sidebar-group-MANAGEMENT' or @id='sidebar-group-GOVERNANCE' or @id='sidebar-group-TRAINING']//a[.//span[normalize-space()='Organogram']]")
    private WebElement organogramSidebarLink;

    @FindBy(xpath = "//h1[normalize-space()='Organogram']")
    private WebElement organogramHeading;

    @FindBy(xpath = "//button[normalize-space()='List']")
    private WebElement listTab;

    @FindBy(xpath = "//button[normalize-space()='Org Chart']")
    private WebElement orgChartTab;

    @FindBy(xpath = "//button[normalize-space()='Table']")
    private WebElement tableTab;

    @FindBy(xpath = "//button[normalize-space()='Canvas']")
    private WebElement canvasTab;

    @FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'Search name')]")
    private WebElement searchInput;

    public void goToOrganogram() {
        String base = driver.getCurrentUrl().split("#")[0].replaceAll("/(admin|super-admin|manager).*", "");
        driver.get(base + "/admin/organogram");
        waitForPageLoaded();
    }

    public void waitForPageLoaded() {
        waitUtils.waitForVisibility(organogramHeading);
    }

    public String getPageHeading() {
        waitUtils.waitForVisibility(organogramHeading);
        return organogramHeading.getText().trim();
    }

    public boolean isViewTabPresent(String tabName) {
        By tabBy = By.xpath("//button[normalize-space()='" + tabName + "']");
        try {
            waitUtils.waitForVisibility(tabBy);
            return driver.findElement(tabBy).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void switchView(String tabName) {
        By tabBy = By.xpath("//button[normalize-space()='" + tabName + "']");
        waitUtils.waitForClickable(tabBy);
        driver.findElement(tabBy).click();
    }

    public void search(String query) {
        waitUtils.waitForVisibility(searchInput);
        searchInput.clear();
        searchInput.sendKeys(query);
    }
}
