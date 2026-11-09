package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class Cluster extends AbstractComponent {

	public Cluster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='sidebar-group-MASTER']//a[.//span[normalize-space()='Cluster']]")
	WebElement cluster;
	
	@FindBy(xpath = "//h1[normalize-space()='Clusters']")
	WebElement clusterScreenName;
	
	@FindBy(xpath = "//button[normalize-space()='Add Cluster']")
	WebElement clusterAdd;
	
	@FindBy(xpath = "//h2[normalize-space()='Add Cluster']")
	WebElement addClusterPopup;
	
	@FindBy(xpath = "//label[contains(normalize-space(),'Title')]/following-sibling::input")
	WebElement title;
	
	@FindBy(xpath = "//label[contains(normalize-space(),'Description')]/following-sibling::textarea")
	WebElement description;
	
	@FindBy(xpath = "//div[@data-parent-picker='true']//button | //div[@data-parent-picker]//button")
	WebElement circleDropdown;
	
	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Add Cluster']")
	WebElement submit;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancel;
	@FindBy(xpath = "//table//tbody//tr/td[1]")
    List<WebElement> clusterNames;
	
	public String clusterMenu() {
		waitElementToAppear(cluster);
		return cluster.getText();
	}

	public void goToCluster() {
		waitForBackdropToDisappear();
		clickElement(cluster);
		waitElementToAppear(clusterScreenName);
	}

	public String clusterScreenName() {
		return clusterScreenName.getText();
	}

	public boolean clusterAddButtonEnable() {
		boolean isEnabled = clusterAdd.isEnabled();
		return isEnabled;
	}

	public void clickAddCluster() {
		waitForBackdropToDisappear();
		clickElement(clusterAdd);
		waitElementToAppear(addClusterPopup);
	}

	public String addClusterModel() {
		return addClusterPopup.getText();
	}

	public boolean clusterTitle() {
		boolean isEnabled = title.isEnabled();
		return isEnabled;
	}

	public void inputClusterTitle(String dtitle) {
		waitElementToAppear(title);
		title.clear();
		title.sendKeys(dtitle);
	}

	public void inputClusterDescription(String desc) {
		description.clear();
		description.sendKeys(desc);
	}

	public boolean addClusterButtonDisable() {
		boolean isDisabled = submit.isEnabled();
		return isDisabled;
	}

	public void selectCircle(String circleName) {
		clickElement(circleDropdown);
		By options = By.xpath("//div[contains(@data-parent-picker,'')]//button[.//span] | //div[@data-parent-picker]//button[.//span]");
		waitUtils.waitForVisibility(options);
		List<WebElement> optionList = driver.findElements(options);

		for (int i = 0; i < optionList.size(); i++) {
		    try {
		        WebElement element = driver.findElement(
		                By.xpath("//div[contains(@data-parent-picker,'')]//button[.//span[normalize-space()='"
		                        + circleName + "']]"));

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

		throw new RuntimeException("Circle not found: " + circleName);
	}
	
	public void createCluster() {
		if (submit.isEnabled()) {
			clickElement(submit);
		}
	}

	public void cancelAddCluster() {
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
			waitUtils.waitForInvisibility(addClusterPopup);
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

	public boolean isClusterPresent(String columnName, String clusterName) {
		int columnIndex = getColumnIndex("Title");
		if (columnIndex == -1) {
			columnIndex = 1;
		}

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		boolean match = rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(clusterName));
		if (match) {
			return true;
		}

		// Fallback: check search box if entity is on another page
		try {
			List<WebElement> searchInputs = driver.findElements(By.xpath("//input[contains(@placeholder,'Search')]"));
			if (!searchInputs.isEmpty() && searchInputs.get(0).isDisplayed()) {
				WebElement search = searchInputs.get(0);
				search.clear();
				search.sendKeys(clusterName);
				try { Thread.sleep(500); } catch (Exception ignored) {}
				rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));
				match = rows.stream().map(WebElement::getText).map(String::trim)
						.anyMatch(value -> value.equalsIgnoreCase(clusterName));
				search.clear();
			}
		} catch (Exception ignored) {}

		return match;
	}
}
