package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class Designation extends AbstractComponent {

	public Designation(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='sidebar-group-MASTER']//a[.//span[normalize-space()='Designation']]")
	WebElement designation;
	
	@FindBy(xpath = "//h1[normalize-space()='Designations']")
	WebElement designationScreenName;
	
	@FindBy(xpath = "//button[normalize-space()='Add Designation']")
	WebElement designationAdd;
	
	@FindBy(xpath = "//h2[normalize-space()='Add Designation']")
	WebElement addDesignationPopup;
	
	@FindBy(xpath = "//label[contains(normalize-space(),'Title')]/following-sibling::input")
	WebElement title;
	
	@FindBy(xpath = "//label[contains(normalize-space(),'Description')]/following-sibling::textarea")
	WebElement description;
	
	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Add Designation']")
	WebElement submit;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancel;
	@FindBy(xpath = "//table//tbody//tr/td[1]")
    List<WebElement> designationNames;
	
	public String designationMenu() {
		waitElementToAppear(designation);
		return designation.getText();
	}
	
	public void goToDesignation() {
		waitForBackdropToDisappear();
		clickElement(designation);
		waitElementToAppear(designationScreenName);
	}
	
	public String designationScreenName() {
		return designationScreenName.getText();
	}
	
	public boolean designationAddButtonEnable() {
		boolean isEnabled = designationAdd.isEnabled();
		return isEnabled;
	}
	
	public void clickAddDesignation() {
		waitForBackdropToDisappear();
		clickElement(designationAdd);
		waitElementToAppear(addDesignationPopup);
	}
	
	public String addDesignationModel() {
		return addDesignationPopup.getText();
	}
	
	public boolean designationTitle() {
		boolean isEnabled = title.isEnabled();
		return isEnabled;
	}
	
	public void inputDesignationTitle(String dtitle) {
		waitElementToAppear(title);
		title.clear();
		title.sendKeys(dtitle);
	}
	
	public void inputDesignationDescription(String desc) {
		description.clear();
		description.sendKeys(desc);
	}
	
	public boolean addDesignationButtonDisable() {
		boolean isDisabled = submit.isEnabled();
		return isDisabled;
	}
	
	public void createDesignation() {
		if(submit.isEnabled()) {
			clickElement(submit);
		}
	}
	
	public void cancelAddDesignation() {
		try {
			if (cancel.isDisplayed()) {
				clickElement(cancel);
			}
		} catch (Exception e) {
			try {
				waitUtils.clickUsingJS(cancel);
			} catch (Exception ignored) {}
		}
		try {
			waitUtils.waitForInvisibility(addDesignationPopup);
		} catch (Exception ignored) {}
		waitForBackdropToDisappear();
	}
	
	public int getColumnIndex(String columnName) {

		List<WebElement> headers = driver.findElements(By.xpath("//table//thead//th"));

		for (int i = 0; i < headers.size(); i++) {

			String headerText = headers.get(i).getText().trim();

			if (headerText.equalsIgnoreCase(columnName)) {
				return i + 1;
			}
		}

		return -1;
	}
	
	public boolean isDesignationPresent(String columnName, String designationName) {
		int columnIndex = getColumnIndex("Title");
		if (columnIndex == -1) {
			columnIndex = 1;
		}
		
		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		boolean match = rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(designationName));
		if (match) {
			return true;
		}

		// Fallback: check search box if entity is on another page
		try {
			List<WebElement> searchInputs = driver.findElements(By.xpath("//input[contains(@placeholder,'Search')]"));
			if (!searchInputs.isEmpty() && searchInputs.get(0).isDisplayed()) {
				WebElement search = searchInputs.get(0);
				search.clear();
				search.sendKeys(designationName);
				try { Thread.sleep(500); } catch (Exception ignored) {}
				rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));
				match = rows.stream().map(WebElement::getText).map(String::trim)
						.anyMatch(value -> value.equalsIgnoreCase(designationName));
				search.clear();
			}
		} catch (Exception ignored) {}

		return match;
	}
}
