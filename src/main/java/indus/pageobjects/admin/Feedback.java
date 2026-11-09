package indus.pageobjects.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

/**
 * Page object modeling Admin Feedback (/admin/feedback).
 * Features:
 * - Feedback summary cards & ratings distribution
 * - Search query filter
 * - Rating filters (5 Stars, 4 Stars, etc.)
 */
public class Feedback extends AbstractComponent {

    public Feedback(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@id='sidebar-group-INSIGHTS']//a[.//span[normalize-space()='Feedback']]")
    private WebElement feedbackSidebarLink;

    @FindBy(xpath = "//h1[normalize-space()='Feedback']")
    private WebElement feedbackHeading;

    @FindBy(xpath = "//input[contains(@placeholder,'Search feedback')]")
    private WebElement searchInput;

    public void goToFeedback() {
        String base = driver.getCurrentUrl().split("#")[0].replaceAll("/(admin|super-admin|manager).*", "");
        driver.get(base + "/admin/feedback");
        waitForPageLoaded();
    }

    public void waitForPageLoaded() {
        waitUtils.waitForVisibility(feedbackHeading);
    }

    public String getPageHeading() {
        waitUtils.waitForVisibility(feedbackHeading);
        return feedbackHeading.getText().trim();
    }

    public void searchFeedback(String query) {
        waitUtils.waitForVisibility(searchInput);
        searchInput.clear();
        searchInput.sendKeys(query);
    }
}
