package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class Department extends AbstractComponent{

	public Department(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='sidebar-group-MASTER']//a[.//span[normalize-space()='Department']]")
	WebElement department;
	
	@FindBy(xpath = "//h1[normalize-space()='Departments']")
	WebElement departmentScreenName;
	
	@FindBy(xpath = "//button[normalize-space()='Add Department']")
	WebElement departmentAdd;
	
	@FindBy(xpath = "//h2[normalize-space()='Add Department']")
	WebElement addDepartmentPopup;
	
	@FindBy(xpath = "//label[contains(normalize-space(),'Title')]/following-sibling::input")
	WebElement title;
	
	@FindBy(xpath = "//label[contains(normalize-space(),'Description')]/following-sibling::textarea")
	WebElement description;
	
	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Add Department']")
	WebElement submit;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancel;
	@FindBy(xpath = "//table//tbody//tr/td[1]")
    List<WebElement> departmentNames;
	
	public String departmentMenu() {
		waitElementToAppear(department);
		return department.getText();
	}
	
	public void goToDepartment() {
		department.click();
		waitElementToAppear(departmentScreenName);
	}
	
	public String departmentScreenName() {
		return departmentScreenName.getText();
	}
	
	public boolean departmentAddButtonEnable() {
		boolean isEnabled = departmentAdd.isEnabled();
		return isEnabled;
	}
	
	public void clickAddDepartment() {
		departmentAdd.click();
	}
	
	public String addDepartmentModel() {
		return addDepartmentPopup.getText();
	}
	
	public boolean departmentTitle() {
		boolean isEnabled = title.isEnabled();
		return isEnabled;
	}
	
	public void inputDepartmentTitle(String dtitle) {
		title.sendKeys(dtitle);
	}
	
	public void inputDepartmentDescription(String desc) {
		description.sendKeys(desc);
	}
	
	public boolean addDepartmentButtonDisable() {
		boolean isDisabled = submit.isEnabled();
		return isDisabled;
	}
	
	public void createDeaprtment() {
		if(submit.isEnabled()) {
			submit.click();
		}
	}
	
	public void cancelAddDepartment() {
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
	
	public boolean isDepartmentPresent(String columnName,String deptName) {
		int columnIndex = getColumnIndex("Title");
		
		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(deptName));
	}
}
