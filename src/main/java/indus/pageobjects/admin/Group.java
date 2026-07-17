package indus.pageobjects.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class Group extends AbstractComponent{

	public Group(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@id='sidebar-group-MANAGEMENT']//a[.//span[normalize-space()='Group']]")
	WebElement group;
	
	@FindBy(xpath = "//h1[normalize-space()='Group Master']")
	WebElement groupMasterName;
	
	@FindBy(xpath = "//button[normalize-space()='Create Group']")
	WebElement groupCreate;
	
	@FindBy(xpath = "//h1[normalize-space()='Create Group']")
	WebElement createGroupName;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Create Group']")
	WebElement createGroupBtn;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancelGroupBtn;
	
	public String groupMenu() {
		waitElementToAppear(group);
		return group.getText();
	}

	public void goToGroupMaster() {
		group.click();
		waitElementToAppear(groupMasterName);
	}

	public String groupMasterScreenName() {
		return groupMasterName.getText();
	}
	
	public boolean groupCreateButtonEnable() {
		boolean isEnabled = groupCreate.isEnabled();
		return isEnabled;
	}
	
	public void clickCreateGroup() {
		groupCreate.click();
	}
	
	public String creatGroupModel() {
		return createGroupName.getText();
	}
	
	public boolean createGroupButtonDisable() {
		boolean isDisabled = createGroupBtn.isEnabled();
		return isDisabled;
	}
	
	public void cancelCreateGroup() {
		cancelGroupBtn.click();
	}

}
