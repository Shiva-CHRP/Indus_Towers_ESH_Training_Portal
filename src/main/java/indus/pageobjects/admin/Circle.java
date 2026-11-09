package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class Circle extends AbstractComponent {

	public Circle(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='sidebar-group-MASTER']//a[.//span[normalize-space()='Circle']]")
	WebElement circle;

	@FindBy(xpath = "//h1[normalize-space()='Circles']")
	WebElement circleScreenName;

	@FindBy(xpath = "//button[normalize-space()='Add Circle']")
	WebElement circleAdd;

	@FindBy(xpath = "//h2[normalize-space()='Add Circle']")
	WebElement addCirclePopup;

	@FindBy(xpath = "//label[contains(normalize-space(),'Title')]/following-sibling::input")
	WebElement title;

	@FindBy(xpath = "//label[contains(normalize-space(),'Description')]/following-sibling::textarea")
	WebElement description;

	@FindBy(xpath = "//div[@data-parent-picker='true']//button | //div[@data-parent-picker]//button")
	private WebElement nationalDropdown;

	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Add Circle']")
	WebElement submit;

	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancel;
	@FindBy(xpath = "//table//tbody//tr/td[1]")
	List<WebElement> circleNames;

	public String circleMenu() {
		waitElementToAppear(circle);
		return circle.getText();
	}

	public void goToCircle() {
		waitForBackdropToDisappear();
		clickElement(circle);
		waitElementToAppear(circleScreenName);
	}

	public String circleScreenName() {
		return circleScreenName.getText();
	}

	public boolean circleAddButtonEnable() {
		boolean isEnabled = circleAdd.isEnabled();
		return isEnabled;
	}

	public void clickAddCircle() {
		waitForBackdropToDisappear();
		clickElement(circleAdd);
		waitElementToAppear(addCirclePopup);
	}

	public String addCircleModel() {
		return addCirclePopup.getText();
	}

	public boolean circleTitle() {
		boolean isEnabled = title.isEnabled();
		return isEnabled;
	}

	public void inputCircleTitle(String dtitle) {
		waitElementToAppear(title);
		title.clear();
		title.sendKeys(dtitle);
	}

	public void inputCircleDescription(String desc) {
		description.clear();
		description.sendKeys(desc);
	}

	public boolean addCircleButtonDisable() {
		boolean isDisabled = submit.isEnabled();
		return isDisabled;
	}
	
	public void selectNational(String nationalName) {
		clickElement(nationalDropdown);
		By targetOption = By.xpath("//div[contains(@data-parent-picker,'')]//button[.//span[normalize-space()='" + nationalName + "']]");
		try {
			WebElement element = waitUtils.waitForVisibility(targetOption);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			return;
		} catch (Exception e) {
			By options = By.xpath("//div[@data-parent-picker='true']//button[.//span] | //div[@data-parent-picker]//button[.//span]");
			List<WebElement> optionList = driver.findElements(options);
			for (WebElement el : optionList) {
				if (el.getText().trim().equalsIgnoreCase(nationalName)) {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
					return;
				}
			}
		}
		throw new RuntimeException("National not found: " + nationalName);
	}

	public void createCircle() {
		if (submit.isEnabled()) {
			clickElement(submit);
		}
	}

	public void cancelAddCircle() {
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
			waitUtils.waitForInvisibility(addCirclePopup);
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

	public boolean isCirclePresent(String columnName, String circleName) {
		int columnIndex = getColumnIndex("Title");
		if (columnIndex == -1) {
			columnIndex = 1;
		}

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		boolean match = rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(circleName));
		if (match) {
			return true;
		}

		// Fallback: check search box if entity is on another page
		try {
			List<WebElement> searchInputs = driver.findElements(By.xpath("//input[contains(@placeholder,'Search')]"));
			if (!searchInputs.isEmpty() && searchInputs.get(0).isDisplayed()) {
				WebElement search = searchInputs.get(0);
				search.clear();
				search.sendKeys(circleName);
				try { Thread.sleep(500); } catch (Exception ignored) {}
				rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));
				match = rows.stream().map(WebElement::getText).map(String::trim)
						.anyMatch(value -> value.equalsIgnoreCase(circleName));
				search.clear();
			}
		} catch (Exception ignored) {}

		return match;
	}
}
