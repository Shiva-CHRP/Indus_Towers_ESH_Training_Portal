package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;
import indus.annotations.StepName;

public class Department extends AbstractComponent {

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
	
	@FindBy(xpath = "//button[@type='submit' and (normalize-space()='Add Department' or normalize-space()='Save changes')]")
	WebElement submit;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancel;

	@FindBy(xpath = "//input[contains(@placeholder,'Search')]")
	WebElement searchInput;

	@FindBy(xpath = "//table//tbody//tr/td[1]")
	List<WebElement> departmentNames;

	// ── Menu & Navigation ────────────────────────────────────────────────
	public String departmentMenu() {
		waitElementToAppear(department);
		return department.getText();
	}
	
	public void goToDepartment() {
		waitForBackdropToDisappear();
		waitElementToBeClickable(department);
		clickElement(department);
		waitElementToAppear(departmentScreenName);
	}
	
	public String departmentScreenName() {
		return departmentScreenName.getText();
	}
	
	// ── Modal & Fields ───────────────────────────────────────────────────
	public boolean departmentAddButtonEnable() {
		return departmentAdd.isEnabled();
	}
	
	public void clickAddDepartment() {
		waitForBackdropToDisappear();
		waitElementToBeClickable(departmentAdd);
		clickElement(departmentAdd);
		waitElementToAppear(addDepartmentPopup);
	}
	
	public String addDepartmentModel() {
		return addDepartmentPopup.getText();
	}
	
	public boolean departmentTitle() {
		return title.isEnabled();
	}
	
	public void inputDepartmentTitle(String dtitle) {
		waitForVisibility(title);
		title.clear();
		title.sendKeys(dtitle);
	}

	public void clearDepartmentTitle() {
		waitForVisibility(title);
		try {
			title.clear();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].value='';", title);
		}
	}

	public String getDepartmentTitleValue() {
		return title.getAttribute("value");
	}
	
	public void inputDepartmentDescription(String desc) {
		waitForVisibility(description);
		description.sendKeys(desc);
	}
	
	public boolean addDepartmentButtonDisable() {
		return !submit.isEnabled();
	}

	public boolean isSubmitEnabled() {
		return submit.isEnabled();
	}
	
	public void createDeaprtment() {
		if (submit.isEnabled()) {
			clickElement(submit);
		}
	}
	
	public void cancelAddDepartment() {
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
			waitForElementToDisappear(addDepartmentPopup);
		} catch (Exception ignored) {}
		waitForBackdropToDisappear();
	}

	// ── Table & Search ───────────────────────────────────────────────────
	@StepName("Search Department by name")
	public void searchDepartment(String query) {
		try {
			waitForVisibility(searchInput);
			searchInput.clear();
			searchInput.sendKeys(query);
		} catch (Exception ignored) {}
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
	
	public boolean isDepartmentPresent(String columnName, String deptName) {
		int columnIndex = getColumnIndex("Title");
		if (columnIndex == -1) columnIndex = 1;
		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));
		boolean match = rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(deptName));
		if (match) return true;

		// Fallback: search box
		try {
			List<WebElement> searchInputs = driver.findElements(By.xpath("//input[contains(@placeholder,'Search')]"));
			if (!searchInputs.isEmpty() && searchInputs.get(0).isDisplayed()) {
				WebElement search = searchInputs.get(0);
				search.clear();
				search.sendKeys(deptName);
				try { Thread.sleep(500); } catch (Exception ignored) {}
				rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));
				match = rows.stream().map(WebElement::getText).map(String::trim)
						.anyMatch(value -> value.equalsIgnoreCase(deptName));
				search.clear();
			}
		} catch (Exception ignored) {}

		return match;
	}

	// ── Deletion & In-Use Block Actions ──────────────────────────────────
	@StepName("Delete Department from row kebab menu")
	public void deleteDepartment(String deptName, String reason) {
		By kebabBy = By.xpath("//tr[.//td[normalize-space()='" + deptName + "']]//button[contains(@class,'cursor-pointer')]");
		try {
			WebElement kebab = waitUtils.waitForVisibility(kebabBy);
			waitElementToBeClickable(kebab);
			kebab.click();

			By deleteMenuBy = By.xpath("//div[@role='menuitem' and contains(.,'Delete')] | //button[contains(.,'Delete')]");
			WebElement deleteItem = waitUtils.waitForVisibility(deleteMenuBy);
			waitElementToBeClickable(deleteItem);
			deleteItem.click();

			// Handle ConfirmDeleteModal
			By confirmBtnBy = By.xpath("//div[@role='dialog']//button[contains(.,'Delete')]");
			WebElement confirmBtn = waitUtils.waitForVisibility(confirmBtnBy);
			waitElementToBeClickable(confirmBtn);
			confirmBtn.click();
		} catch (Exception ignored) {}
	}
}
