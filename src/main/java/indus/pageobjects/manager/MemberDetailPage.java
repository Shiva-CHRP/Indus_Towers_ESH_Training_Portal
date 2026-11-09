package indus.pageobjects.manager;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

/**
 * Page object modeling Member Detail page (/manager/team/:memberId).
 * Features:
 * - Direct report summary profile & snapshot stats
 * - Assigned courses grid with status pills & progress bars
 * - Remind button trigger for overdue/incomplete courses
 * - Send Reminder modal with In-app & Email channel selection
 */
public class MemberDetailPage extends AbstractComponent {

    public MemberDetailPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h1[contains(@class,'font-bold')] | //h2[contains(@class,'font-bold')] | //div[.//p[contains(normalize-space(),'Team Member Overview')]]//h2")
    private WebElement memberNameHeading;

    @FindBy(xpath = "//nav//a[normalize-space()='Team' or contains(@href,'/manager/team')] | //button[contains(.,'Back')]")
    private WebElement backButton;

    @FindBy(xpath = "//div[contains(@class,'grid')]//div[contains(@class,'rounded-xl') and .//p[contains(@class,'font-semibold')]]")
    private List<WebElement> courseCards;

    // Send Reminder modal
    @FindBy(xpath = "//h2[normalize-space()='Send Reminder'] | //p[normalize-space()='Send Reminder']")
    private WebElement reminderModalTitle;

    @FindBy(xpath = "//button[contains(.,'In-app')]")
    private WebElement inAppChannelButton;

    @FindBy(xpath = "//button[contains(.,'Email')]")
    private WebElement emailChannelButton;

    @FindBy(xpath = "//button[normalize-space()='Send Reminder Now']")
    private WebElement sendReminderSubmitButton;

    public String getMemberName() {
        waitUtils.waitForVisibility(memberNameHeading);
        return memberNameHeading.getText().trim();
    }

    public int getAssignedCoursesCount() {
        try {
            return courseCards.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isCourseListed(String courseTitle) {
        By courseBy = By.xpath("//p[contains(@class,'font-semibold') and normalize-space()='" + courseTitle + "']");
        try {
            return waitUtils.waitForVisibility(courseBy).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRemindButtonPresent(String courseTitle) {
        By remindBtnBy = By.xpath("//div[.//p[normalize-space()='" + courseTitle + "']]//button[contains(.,'Remind')]");
        try {
            return driver.findElement(remindBtnBy).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void openRemindModal(String courseTitle) {
        By remindBtnBy = By.xpath("//div[.//p[normalize-space()='" + courseTitle + "']]//button[contains(.,'Remind')]");
        waitUtils.waitForClickable(remindBtnBy);
        clickElement(remindBtnBy);
        waitUtils.waitForVisibility(reminderModalTitle);
    }

    public void sendReminder(boolean inApp, boolean email) {
        waitUtils.waitForVisibility(reminderModalTitle);
        waitUtils.waitForClickable(sendReminderSubmitButton);
        clickElement(sendReminderSubmitButton);
        waitForBackdropToDisappear();
    }

    public void goBack() {
        waitUtils.waitForClickable(backButton);
        clickElement(backButton);
        waitForBackdropToDisappear();
    }
}
