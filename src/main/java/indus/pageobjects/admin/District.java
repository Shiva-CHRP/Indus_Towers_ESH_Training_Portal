package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class District extends AbstractComponent {

	public District(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='sidebar-group-MASTER']//a[.//span[normalize-space()='District']]")
	WebElement district;

	@FindBy(xpath = "//h1[normalize-space()='Districts']")
	WebElement districtScreenName;

	@FindBy(xpath = "//button[normalize-space()='Add District']")
	WebElement districtAdd;

	@FindBy(xpath = "//h2[normalize-space()='Add District']")
	WebElement addDistrictPopup;

	@FindBy(xpath = "//label[contains(normalize-space(),'Title')]/following-sibling::input")
	WebElement title;

	@FindBy(xpath = "//label[contains(normalize-space(),'Description')]/following-sibling::textarea")
	WebElement description;

	@FindBy(xpath = "//div[@data-parent-picker='true']//button")
	WebElement clusterDropdown;

	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Add District']")
	WebElement submit;

	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancel;
	@FindBy(xpath = "//table//tbody//tr/td[1]")
	List<WebElement> districtNames;
	
	public String districtMenu() {
		waitElementToAppear(district);
		return district.getText();
	}

	public void goToDistrict() {
		district.click();
		waitElementToAppear(districtScreenName);
	}

	public String districtScreenName() {
		return districtScreenName.getText();
	}

	public boolean districtAddButtonEnable() {
		boolean isEnabled = districtAdd.isEnabled();
		return isEnabled;
	}

	public void clickAddDistrict() {
		districtAdd.click();
	}

	public String addDistrictModel() {
		return addDistrictPopup.getText();
	}

	public boolean districtTitle() {
		boolean isEnabled = title.isEnabled();
		return isEnabled;
	}

	public void inputDistrictTitle(String dtitle) {
		title.sendKeys(dtitle);
	}

	public void inputDistrictDescription(String desc) {
		description.sendKeys(desc);
	}

	public boolean addDistrictButtonDisable() {
		boolean isDisabled = submit.isEnabled();
		return isDisabled;
	}
	
	public void selectCluster(String clusterName) {
		clusterDropdown.click();
		By options = By.xpath("//div[@data-parent-picker='true']//button[.//span]");
		waitUtils.waitForVisibility(options);
		List<WebElement> optionList = driver.findElements(options);

		for (int i = 0; i < optionList.size(); i++) {
		    try {
		        WebElement element = driver.findElement(
		                By.xpath("//div[@data-parent-picker='true']//button[.//span[normalize-space()='"
		                        + clusterName + "']]"));

		        if (element.isDisplayed() && element.isEnabled()) {
		            ((JavascriptExecutor) driver)
		                    .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

		            ((JavascriptExecutor) driver)
		                    .executeScript("arguments[0].click();", element);
		            return;
		        }
		    } catch (Exception ignored) {
		    }
		}

		throw new RuntimeException("National not found: " + clusterName);
	}

	public void createDistrict() {
		if (submit.isEnabled()) {
			submit.click();
		}
	}

	public void cancelAddDistrict() {
		cancel.click();
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

	public boolean isDistrictPresent(String columnName, String districtName) {
		int columnIndex = getColumnIndex("Title");

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(districtName));
	}
}
