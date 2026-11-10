package indus.pageobjects.manager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

/**
 * Page object modeling Manager Assessment View (/manager/assessments/:id).
 * In Manager portal, assessments are strictly read-only:
 * - Edit button is hidden/omitted.
 * - Delete button is omitted.
 * - Back button navigates back to previous screen.
 */
public class ManagerAssessmentViewPage extends AbstractComponent {

    public ManagerAssessmentViewPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h1[contains(@class,'font-bold')]")
    private WebElement assessmentTitle;

    @FindBy(xpath = "//button[contains(.,'Back')]")
    private WebElement backButton;

    public void navigateToAssessment(String assessmentId) {
        String base = driver.getCurrentUrl().split("#")[0].replaceAll("/(admin|super-admin|manager).*", "");
        driver.get(base + "/manager/assessments/" + assessmentId);
        waitUtils.waitUntil(d ->
                !d.findElements(By.xpath("//h1[contains(@class,'font-bold')] | //p[contains(.,'Page not found')] | //button[contains(.,'Go back')]")).isEmpty()
                || !d.getCurrentUrl().contains("/assessments/"));
    }

    public String getAssessmentTitle() {
        waitUtils.waitForVisibility(assessmentTitle);
        return assessmentTitle.getText().trim();
    }

    public boolean isEditButtonPresent() {
        try {
            return driver.findElements(By.xpath("//button[contains(.,'Edit') or contains(.,'edit')]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDeleteButtonPresent() {
        try {
            return driver.findElements(By.xpath("//button[contains(.,'Delete') or contains(.,'delete')]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void goBack() {
        waitUtils.waitForClickable(backButton);
        backButton.click();
    }
}
