package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class TowerTypes extends AbstractComponent {

	public TowerTypes(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='sidebar-group-MASTER']//a[.//span[normalize-space()='Tower Type']]")
	WebElement towerType;
	
	@FindBy(xpath = "//h1[normalize-space()='Tower Types']")
	WebElement towerTypeScreenName;
	
	@FindBy(xpath = "//button[normalize-space()='Add Tower Type']")
	WebElement towerTypeAdd;
	
	@FindBy(xpath = "//h2[normalize-space()='Add Tower Type']")
	WebElement addTowerTypePopup;
	
	@FindBy(xpath = "//label[contains(normalize-space(),'Title')]/following-sibling::input")
	WebElement title;
	
	@FindBy(xpath = "//label[contains(normalize-space(),'Description')]/following-sibling::textarea")
	WebElement description;
	
	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Add Tower Type']")
	WebElement submit;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancel;
	@FindBy(xpath = "//table//tbody//tr/td[1]")
    List<WebElement> towerTypeNames;
	
	public String towerTypeMenu() {
		waitElementToAppear(towerType);
		return towerType.getText();
	}
	
	public void goToTowerType() {
		waitForBackdropToDisappear();
		clickElement(towerType);
		waitElementToAppear(towerTypeScreenName);
	}
	
	public String towerTypeScreenName() {
		return towerTypeScreenName.getText();
	}
	
	public boolean towerTypeAddButtonEnable() {
		boolean isEnabled = towerTypeAdd.isEnabled();
		return isEnabled;
	}
	
	public void clickAddTowerType() {
		waitForBackdropToDisappear();
		clickElement(towerTypeAdd);
		waitElementToAppear(addTowerTypePopup);
	}
	
	public String addTowerTypeModel() {
		return addTowerTypePopup.getText();
	}
	
	public boolean towerTypeTitle() {
		boolean isEnabled = title.isEnabled();
		return isEnabled;
	}
	
	public void inputTowerTypeTitle(String dtitle) {
		waitElementToAppear(title);
		title.clear();
		title.sendKeys(dtitle);
	}
	
	public void inputTowerTypeDescription(String desc) {
		description.clear();
		description.sendKeys(desc);
	}
	
	public boolean addTowerTypeButtonDisable() {
		boolean isDisabled = submit.isEnabled();
		return isDisabled;
	}
	
	public void createTowerType() {
		if(submit.isEnabled()) {
			clickElement(submit);
		}
	}
	
	public void cancelAddTowerType() {
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
			waitUtils.waitForInvisibility(addTowerTypePopup);
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
	
	public boolean isTowerTypePresent(String columnName, String towerTypeName) {
		int columnIndex = getColumnIndex("Title");
		if (columnIndex == -1) {
			columnIndex = 1;
		}
		
		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		boolean match = rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(towerTypeName));
		if (match) {
			return true;
		}

		// Fallback: check search box if entity is on another page
		try {
			List<WebElement> searchInputs = driver.findElements(By.xpath("//input[contains(@placeholder,'Search')]"));
			if (!searchInputs.isEmpty() && searchInputs.get(0).isDisplayed()) {
				WebElement search = searchInputs.get(0);
				search.clear();
				search.sendKeys(towerTypeName);
				try { Thread.sleep(500); } catch (Exception ignored) {}
				rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));
				match = rows.stream().map(WebElement::getText).map(String::trim)
						.anyMatch(value -> value.equalsIgnoreCase(towerTypeName));
				search.clear();
			}
		} catch (Exception ignored) {}

		return match;
	}
}
