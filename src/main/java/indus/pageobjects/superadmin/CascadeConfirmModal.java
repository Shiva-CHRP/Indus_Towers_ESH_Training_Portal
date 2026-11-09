package indus.pageobjects.superadmin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;
import indus.annotations.StepName;

public class CascadeConfirmModal extends AbstractComponent {

	public CascadeConfirmModal(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h2[@id='cascade-title']")
	private WebElement cascadeTitle;

	@FindBy(xpath = "//div[contains(@class,'border-t')]//button[normalize-space()='Cancel']")
	private WebElement cancelButton;

	@FindBy(xpath = "//div[contains(@class,'border-t')]//button[contains(normalize-space(),'Disable & save') or contains(normalize-space(),'Enable required')]")
	private WebElement confirmButton;

	@StepName("Check if Cascade Dependency Confirmation Modal is displayed")
	public boolean isDisplayed() {
		try {
			waitElementToAppear(cascadeTitle);
			return cascadeTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Get title of Cascade Confirmation Modal")
	public String getTitle() {
		waitElementToAppear(cascadeTitle);
		return cascadeTitle.getText().trim();
	}

	@StepName("Click Cancel button on Cascade Confirmation Modal")
	public void clickCancel() {
		waitElementToBeClickable(cancelButton);
		cancelButton.click();
		waitForElementToDisappear(cascadeTitle);
	}

	@StepName("Click Confirm & Save button on Cascade Confirmation Modal")
	public void clickConfirmCascade() {
		waitElementToBeClickable(confirmButton);
		confirmButton.click();
		waitForElementToDisappear(cascadeTitle);
	}
}
