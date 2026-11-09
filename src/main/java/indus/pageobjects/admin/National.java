package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class National extends AbstractComponent {

	public National(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath = "//div[@id='sidebar-group-MASTER']//a[.//span[normalize-space()='National']]")
	WebElement national;
	
	@FindBy(xpath = "//h1[normalize-space()='Nationals']")
	WebElement nationalScreenName;
	
	@FindBy(xpath = "//button[normalize-space()='Add National']")
	WebElement nationalAdd;
	
	@FindBy(xpath = "//h2[normalize-space()='Add National']")
	WebElement addNationalPopup;
	
	@FindBy(xpath = "//label[contains(normalize-space(),'Title')]/following-sibling::input")
	WebElement title;
	
	@FindBy(xpath = "//label[contains(normalize-space(),'Description')]/following-sibling::textarea")
	WebElement description;
	
	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Add National']")
	WebElement submit;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancel;
	@FindBy(xpath = "//table//tbody//tr/td[1]")
    List<WebElement> nationalNames;
	
	public String nationalMenu() {
		waitElementToAppear(national);
		return national.getText();
	}
	
	public void goToNational() {
		waitForBackdropToDisappear();
		clickElement(national);
		waitElementToAppear(nationalScreenName);
	}
	
	public String nationalScreenName() {
		return nationalScreenName.getText();
	}
	
	public boolean nationalAddButtonEnable() {
		boolean isEnabled = nationalAdd.isEnabled();
		return isEnabled;
	}
	
	public void clickAddNational() {
		waitForBackdropToDisappear();
		clickElement(nationalAdd);
		waitElementToAppear(addNationalPopup);
	}
	
	public String addNationalModel() {
		return addNationalPopup.getText();
	}
	
	public boolean nationalTitle() {
		boolean isEnabled = title.isEnabled();
		return isEnabled;
	}
	
	public void inputNationalTitle(String dtitle) {
		waitElementToAppear(title);
		title.clear();
		title.sendKeys(dtitle);
	}
	
	public void inputNationalDescription(String desc) {
		description.clear();
		description.sendKeys(desc);
	}
	
	public boolean addNationalButtonDisable() {
		boolean isDisabled = submit.isEnabled();
		return isDisabled;
	}
	
	public void createNational() {
		if(submit.isEnabled()) {
			clickElement(submit);
		}
	}
	
	public void cancelAddNational() {
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
			waitUtils.waitForInvisibility(addNationalPopup);
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
	
	public boolean isNationalPresent(String columnName, String nationalName) {
		int columnIndex = getColumnIndex("Title");
		if (columnIndex == -1) {
			columnIndex = 1;
		}
		
		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		boolean match = rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(nationalName));
		if (match) {
			return true;
		}

		// Fallback: check search box if entity is on another page
		try {
			List<WebElement> searchInputs = driver.findElements(By.xpath("//input[contains(@placeholder,'Search')]"));
			if (!searchInputs.isEmpty() && searchInputs.get(0).isDisplayed()) {
				WebElement search = searchInputs.get(0);
				search.clear();
				search.sendKeys(nationalName);
				try { Thread.sleep(500); } catch (Exception ignored) {}
				rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));
				match = rows.stream().map(WebElement::getText).map(String::trim)
						.anyMatch(value -> value.equalsIgnoreCase(nationalName));
				search.clear();
			}
		} catch (Exception ignored) {}

		return match;
	}
}
