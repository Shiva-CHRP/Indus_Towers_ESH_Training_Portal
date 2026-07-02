package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class Cluster extends AbstractComponent{

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
	
	@FindBy(xpath = "//div[@data-parent-picker='true']//button")
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
		cluster.click();
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
		clusterAdd.click();
	}

	public String addClusterModel() {
		return addClusterPopup.getText();
	}

	public boolean clusterTitle() {
		boolean isEnabled = title.isEnabled();
		return isEnabled;
	}

	public void inputClusterTitle(String dtitle) {
		title.sendKeys(dtitle);
	}

	public void inputClusterDescription(String desc) {
		description.sendKeys(desc);
	}

	public boolean addClusterButtonDisable() {
		boolean isDisabled = submit.isEnabled();
		return isDisabled;
	}

	public void selectCircle(String circleName) {
		circleDropdown.click();
		By options = By.xpath("//div[@data-parent-picker='true']//button[.//span]");
		waitUtils.waitForVisibility(options);
		List<WebElement> optionList = driver.findElements(options);

		for (int i = 0; i < optionList.size(); i++) {
		    try {
		        WebElement element = driver.findElement(
		                By.xpath("//div[@data-parent-picker='true']//button[.//span[normalize-space()='"
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

		throw new RuntimeException("National not found: " + circleName);
	}
	
	public void createCluster() {
		if (submit.isEnabled()) {
			submit.click();
		}
	}

	public void cancelAddCluster() {
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

	public boolean isClusterPresent(String columnName, String clusterName) {
		int columnIndex = getColumnIndex("Title");

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(clusterName));
	}
}
