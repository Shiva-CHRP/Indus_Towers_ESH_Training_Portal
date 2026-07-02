package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class National extends AbstractComponent{

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
		national.click();
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
		nationalAdd.click();
	}
	
	public String addNationalModel() {
		return addNationalPopup.getText();
	}
	
	public boolean nationalTitle() {
		boolean isEnabled = title.isEnabled();
		return isEnabled;
	}
	
	public void inputNationalTitle(String dtitle) {
		title.sendKeys(dtitle);
	}
	
	public void inputNationalDescription(String desc) {
		description.sendKeys(desc);
	}
	
	public boolean addNationalButtonDisable() {
		boolean isDisabled = submit.isEnabled();
		return isDisabled;
	}
	
	public void createNational() {
		if(submit.isEnabled()) {
			submit.click();
		}
	}
	
	public void cancelAddNational() {
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
	
	public boolean isNationalPresent(String columnName,String nationalName) {
		int columnIndex = getColumnIndex("Title");
		
		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(nationalName));
	}
}
