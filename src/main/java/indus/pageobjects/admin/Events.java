package indus.pageobjects.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

/**
 * Page object modeling Admin Events (/admin/events).
 * Features:
 * - Upcoming events calendar & events feed
 * - "Create Event" modal trigger & form inputs
 * - Search and filter by event type (Virtual, In-Person)
 */
public class Events extends AbstractComponent {

    public Events(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@id='sidebar-group-EVENTS']//a[.//span[normalize-space()='Events']]")
    private WebElement eventsSidebarLink;

    @FindBy(xpath = "//h1[normalize-space()='Events']")
    private WebElement eventsHeading;

    @FindBy(xpath = "//button[contains(.,'Create Event')]")
    private WebElement createEventButton;

    @FindBy(xpath = "//h2[normalize-space()='Create Event'] | //h3[normalize-space()='Create Event']")
    private WebElement createEventModalTitle;

    @FindBy(xpath = "//input[@name='title' or contains(@placeholder,'Event title')]")
    private WebElement eventTitleInput;

    @FindBy(xpath = "//button[@type='submit' and contains(.,'Create Event')]")
    private WebElement createEventSubmitBtn;

    @FindBy(xpath = "//button[normalize-space()='Cancel']")
    private WebElement cancelEventBtn;

    @FindBy(xpath = "//input[contains(@placeholder,'Search events')]")
    private WebElement searchInput;

    public void goToEvents() {
        String base = driver.getCurrentUrl().split("#")[0].replaceAll("/(admin|super-admin|manager).*", "");
        driver.get(base + "/admin/events");
        waitForPageLoaded();
    }

    public void waitForPageLoaded() {
        waitUtils.waitForVisibility(eventsHeading);
    }

    public String getPageHeading() {
        waitUtils.waitForVisibility(eventsHeading);
        return eventsHeading.getText().trim();
    }

    public void openCreateEventModal() {
        waitUtils.waitForClickable(createEventButton);
        createEventButton.click();
        waitUtils.waitForVisibility(createEventModalTitle);
    }

    public boolean isCreateEventModalOpen() {
        try {
            waitUtils.waitForVisibility(createEventModalTitle);
            return createEventModalTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterEventTitle(String title) {
        waitUtils.waitForVisibility(eventTitleInput);
        eventTitleInput.clear();
        eventTitleInput.sendKeys(title);
    }

    public void clickSubmitEvent() {
        waitUtils.waitForClickable(createEventSubmitBtn);
        createEventSubmitBtn.click();
    }

    public void cancelCreateEvent() {
        waitUtils.waitForClickable(cancelEventBtn);
        cancelEventBtn.click();
    }

    public void searchEvents(String query) {
        waitUtils.waitForVisibility(searchInput);
        searchInput.clear();
        searchInput.sendKeys(query);
    }
}
