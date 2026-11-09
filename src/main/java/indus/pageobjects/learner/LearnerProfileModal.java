package indus.pageobjects.learner;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

/**
 * Page object modeling LearnerProfileModal (drill-down modal for learner rows
 * on dashboard leaderboard and team list).
 */
public class LearnerProfileModal extends AbstractComponent {

    public LearnerProfileModal(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h2[contains(text(),'Learner Profile') or contains(text(),'Needing Attention') or contains(text(),'Top Learner')]")
    private WebElement modalTitle;

    @FindBy(xpath = "//button[@aria-label='Close modal' or contains(@class,'close')]")
    private WebElement closeButton;

    @FindBy(xpath = "//div[contains(@class,'modal')]//span[contains(@class,'font-bold')]")
    private WebElement complianceScore;

    @FindBy(xpath = "//div[contains(@class,'modal')]//div[contains(@class,'grid')]//p[contains(@class,'font-medium')]")
    private List<WebElement> courseItems;

    public boolean isModalOpen() {
        try {
            waitUtils.waitForVisibility(modalTitle);
            return modalTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getModalTitle() {
        waitUtils.waitForVisibility(modalTitle);
        return modalTitle.getText().trim();
    }

    public void closeModal() {
        waitUtils.waitForClickable(closeButton);
        closeButton.click();
    }
}
