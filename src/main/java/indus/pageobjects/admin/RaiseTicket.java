package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

/**
 * Page object modeling Raise Ticket triage & creation (/admin/raise-ticket).
 * Features:
 * - Tickets list with KPI status filters (Open, In Progress, Pending, Resolved)
 * - "New Ticket" modal dialog with Category, Subject, Description fields
 * - Required fields validation handling
 */
public class RaiseTicket extends AbstractComponent {

    public RaiseTicket(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@id='sidebar-group-TOOLS & SUPPORT']//a[.//span[normalize-space()='Raise Ticket']]")
    private WebElement raiseTicketSidebarLink;

    @FindBy(xpath = "//h1[normalize-space()='Raise Ticket']")
    private WebElement raiseTicketHeading;

    @FindBy(xpath = "//button[contains(.,'New Ticket')]")
    private WebElement newTicketButton;

    @FindBy(xpath = "//h2[normalize-space()='New Ticket'] | //h3[normalize-space()='New Ticket']")
    private WebElement newTicketModalTitle;

    @FindBy(xpath = "//input[contains(@placeholder,'Brief summary') or @name='subject']")
    private WebElement subjectInput;

    @FindBy(xpath = "//textarea[contains(@placeholder,'details') or @name='description']")
    private WebElement descriptionTextarea;

    @FindBy(xpath = "//button[@type='submit' and contains(.,'Submit Ticket')]")
    private WebElement submitTicketBtn;

    @FindBy(xpath = "//button[normalize-space()='Cancel']")
    private WebElement cancelTicketBtn;

    @FindBy(xpath = "//input[contains(@placeholder,'Search tickets')]")
    private WebElement searchInput;

    @FindBy(xpath = "//tr[contains(@class,'hover:bg-')] | //div[contains(@class,'ticket-card')]")
    private List<WebElement> ticketRows;

    public void goToRaiseTicket() {
        String base = driver.getCurrentUrl().split("#")[0].replaceAll("/(admin|super-admin|manager).*", "");
        driver.get(base + "/admin/raise-ticket");
        waitForPageLoaded();
    }

    public void waitForPageLoaded() {
        waitUtils.waitForVisibility(raiseTicketHeading);
    }

    public String getPageHeading() {
        waitUtils.waitForVisibility(raiseTicketHeading);
        return raiseTicketHeading.getText().trim();
    }

    public void openNewTicketModal() {
        waitUtils.waitForClickable(newTicketButton);
        newTicketButton.click();
        waitUtils.waitForVisibility(newTicketModalTitle);
    }

    public boolean isNewTicketModalDisplayed() {
        try {
            waitUtils.waitForVisibility(newTicketModalTitle);
            return newTicketModalTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterSubject(String subject) {
        waitUtils.waitForVisibility(subjectInput);
        subjectInput.clear();
        subjectInput.sendKeys(subject);
    }

    public void enterDescription(String desc) {
        waitUtils.waitForVisibility(descriptionTextarea);
        descriptionTextarea.clear();
        descriptionTextarea.sendKeys(desc);
    }

    public void clickSubmitTicket() {
        waitUtils.waitForClickable(submitTicketBtn);
        submitTicketBtn.click();
    }

    public void cancelNewTicket() {
        waitUtils.waitForClickable(cancelTicketBtn);
        cancelTicketBtn.click();
    }

    public void searchTickets(String query) {
        waitUtils.waitForVisibility(searchInput);
        searchInput.clear();
        searchInput.sendKeys(query);
    }

    public int getTicketCount() {
        try {
            return ticketRows.size();
        } catch (Exception e) {
            return 0;
        }
    }
}
