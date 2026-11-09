package indus.pageobjects.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

/**
 * Page object modeling Skill Matrix (/admin/skill-matrix).
 * Features:
 * - Skill Matrix hero with "Coming Soon" badge
 * - 5-level competency scale description (L1-L5)
 * - 3-step operational workflow explanation
 */
public class SkillMatrix extends AbstractComponent {

    public SkillMatrix(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@id='sidebar-group-INSIGHTS']//a[.//span[normalize-space()='Skill Matrix']]")
    private WebElement skillMatrixSidebarLink;

    @FindBy(xpath = "//h1[normalize-space()='Skill Matrix']")
    private WebElement skillMatrixHeading;

    @FindBy(xpath = "//span[normalize-space()='Coming Soon']")
    private WebElement comingSoonBadge;

    public void goToSkillMatrix() {
        String base = driver.getCurrentUrl().split("#")[0].replaceAll("/(admin|super-admin|manager).*", "");
        driver.get(base + "/admin/skill-matrix");
        waitForPageLoaded();
    }

    public void waitForPageLoaded() {
        waitUtils.waitForVisibility(skillMatrixHeading);
    }

    public String getPageHeading() {
        waitUtils.waitForVisibility(skillMatrixHeading);
        return skillMatrixHeading.getText().trim();
    }

    public boolean isComingSoonBadgeDisplayed() {
        try {
            waitUtils.waitForVisibility(comingSoonBadge);
            return comingSoonBadge.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
