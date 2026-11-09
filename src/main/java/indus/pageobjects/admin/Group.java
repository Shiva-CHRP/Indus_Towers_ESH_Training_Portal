package indus.pageobjects.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

/**
 * Page object modeling Master Groups (/admin/master/groups).
 * Features:
 * - Group listing table with member count and course count
 * - Create Group modal/form navigation
 * - Group search and status filtering
 */
public class Group extends AbstractComponent {

    public Group(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@id='sidebar-group-MANAGEMENT' or @id='sidebar-group-MASTER']//a[.//span[normalize-space()='Group']]")
    private WebElement groupSidebarLink;

    @FindBy(xpath = "//h1[contains(text(),'Group Master') or contains(text(),'Groups')]")
    private WebElement groupMasterHeading;

    @FindBy(xpath = "//button[contains(.,'Create Group')]")
    private WebElement createGroupButton;

    @FindBy(xpath = "//input[@name='name' or @placeholder='Group name']")
    private WebElement groupNameInput;

    @FindBy(xpath = "//textarea[@name='description' or @placeholder='Group description']")
    private WebElement groupDescriptionInput;

    @FindBy(xpath = "//button[@type='submit' and contains(.,'Create Group')] | //button[normalize-space()='Save Group']")
    private WebElement saveGroupBtn;

    @FindBy(xpath = "//input[contains(@placeholder,'Search groups') or contains(@placeholder,'Search')]")
    private WebElement searchInput;

    public void goToGroupMaster() {
        String base = driver.getCurrentUrl().split("#")[0].replaceAll("/(admin|super-admin|manager).*", "");
        driver.get(base + "/admin/master/groups");
        waitForPageLoaded();
    }

    public void waitForPageLoaded() {
        waitUtils.waitForVisibility(groupMasterHeading);
    }

    public String getPageHeading() {
        waitUtils.waitForVisibility(groupMasterHeading);
        return groupMasterHeading.getText().trim();
    }

    public void clickCreateGroup() {
        waitUtils.waitForClickable(createGroupButton);
        createGroupButton.click();
    }

    public void enterGroupName(String name) {
        waitUtils.waitForVisibility(groupNameInput);
        groupNameInput.clear();
        groupNameInput.sendKeys(name);
    }

    public void enterGroupDescription(String desc) {
        waitUtils.waitForVisibility(groupDescriptionInput);
        groupDescriptionInput.clear();
        groupDescriptionInput.sendKeys(desc);
    }

    public void submitCreateGroup() {
        waitUtils.waitForClickable(saveGroupBtn);
        saveGroupBtn.click();
    }

    public void searchGroup(String query) {
        waitUtils.waitForVisibility(searchInput);
        searchInput.clear();
        searchInput.sendKeys(query);
    }

    public boolean isGroupPresent(String groupName) {
        By groupRowBy = By.xpath("//tr[contains(.,'" + groupName + "')] | //div[contains(@class,'card') and contains(.,'" + groupName + "')]");
        try {
            waitUtils.waitForVisibility(groupRowBy);
            return driver.findElement(groupRowBy).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
