package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class Designation extends AbstractComponent{

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
		designation.click();
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
		designationAdd.click();
	}
	
	public String addDesignationModel() {
		return addDesignationPopup.getText();
	}
	
	public boolean designationTitle() {
		boolean isEnabled = title.isEnabled();
		return isEnabled;
	}
	
	public void inputDesignationTitle(String dtitle) {
		title.sendKeys(dtitle);
	}
	
	public void inputDesignationDescription(String desc) {
		description.sendKeys(desc);
	}
	
	public boolean addDesignationButtonDisable() {
		boolean isDisabled = submit.isEnabled();
		return isDisabled;
	}
	
	public void createDesignation() {
		if(submit.isEnabled()) {
			submit.click();
		}
	}
	
	public void cancelAddDesignation() {
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
	
	public boolean isDesignationPresent(String columnName,String designationName) {
		int columnIndex = getColumnIndex("Title");
		
		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(designationName));
	}
}
