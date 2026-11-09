package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class Site extends AbstractComponent {

	public Site(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@id='sidebar-group-MASTER']//a[.//span[normalize-space()='Site']]")
	WebElement site;
	
	@FindBy(xpath = "//h1[normalize-space()='Sites']")
	WebElement siteScreenName;
	
	@FindBy(xpath = "//button[normalize-space()='Add Site']")
	WebElement siteAdd;
	
	@FindBy(xpath = "//h2[normalize-space()='Add Site']")
	WebElement addSitePopup;
	
	@FindBy(xpath = "//label[contains(normalize-space(),'Title')]/following-sibling::input")
	WebElement title;
	
	@FindBy(xpath = "//label[contains(normalize-space(),'Description')]/following-sibling::textarea")
	WebElement description;
	
	@FindBy(xpath = "//label[contains(normalize-space(),'Cluster')]/following-sibling::div//button")
	WebElement clusterDropdown;
	
	@FindBy(xpath = "//label[contains(normalize-space(),'District')]/following-sibling::div//button")
	WebElement districtDropdown;
	
	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Add Site']")
	WebElement submit;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancel;
	@FindBy(xpath = "//table//tbody//tr/td[1]")
    List<WebElement> districtNames;
	
	public String siteMenu() {
		waitElementToAppear(site);
		return site.getText();
	}

	public void goToSite() {
		waitForBackdropToDisappear();
		clickElement(site);
		waitElementToAppear(siteScreenName);
	}

	public String siteScreenName() {
		return siteScreenName.getText();
	}

	public boolean siteAddButtonEnable() {
		boolean isEnabled = siteAdd.isEnabled();
		return isEnabled;
	}

	public void clickAddSite() {
		waitForBackdropToDisappear();
		clickElement(siteAdd);
		waitElementToAppear(addSitePopup);
	}

	public String addSiteModel() {
		return addSitePopup.getText();
	}

	public boolean siteTitle() {
		boolean isEnabled = title.isEnabled();
		return isEnabled;
	}

	public void inputSiteTitle(String dtitle) {
		waitElementToAppear(title);
		title.clear();
		title.sendKeys(dtitle);
	}

	public void inputSiteDescription(String desc) {
		description.clear();
		description.sendKeys(desc);
	}

	public boolean addSiteButtonDisable() {
		boolean isDisabled = submit.isEnabled();
		return isDisabled;
	}

	public void selectCluster(String clusterName) {
		clickElement(clusterDropdown);
		By options = By.xpath("//div[contains(@data-parent-picker,'')]//button[.//span] | //div[@data-parent-picker]//button[.//span]");
		waitUtils.waitForVisibility(options);
		List<WebElement> optionList = driver.findElements(options);

		for (int i = 0; i < optionList.size(); i++) {
		    try {
		        WebElement element = driver.findElement(
		                By.xpath("//div[contains(@data-parent-picker,'')]//button[.//span[normalize-space()='"
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

		throw new RuntimeException("Cluster not found: " + clusterName);
	}
	
	public void selectDistrict(String districtName) {
		clickElement(districtDropdown);
		By options = By.xpath("//div[contains(@data-parent-picker,'')]//button[.//span] | //div[@data-parent-picker]//button[.//span]");
		waitUtils.waitForVisibility(options);
		List<WebElement> optionList = driver.findElements(options);

		for (int i = 0; i < optionList.size(); i++) {
		    try {
		        WebElement element = driver.findElement(
		                By.xpath("//div[contains(@data-parent-picker,'')]//button[.//span[normalize-space()='"
		                        + districtName + "']]"));

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

		throw new RuntimeException("District not found: " + districtName);
	}
	
	public void createSite() {
		if (submit.isEnabled()) {
			clickElement(submit);
			try {
				waitUtils.waitForInvisibility(addSitePopup);
			} catch (Exception e) {
				try {
					if (cancel.isDisplayed()) {
						waitUtils.clickUsingJS(cancel);
					}
				} catch (Exception ignored) {}
			}
			waitForBackdropToDisappear();
		}
	}

	public void cancelAddSite() {
		try {
			if (cancel.isDisplayed()) {
				clickElement(cancel);
			}
		} catch (Exception e) {
			waitUtils.clickUsingJS(cancel);
		}
		try {
			waitUtils.waitForInvisibility(addSitePopup);
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

	public boolean isSitePresent(String columnName, String siteName) {
		int columnIndex = getColumnIndex("Title");

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr/td[" + columnIndex + "]"));

		return rows.stream().map(WebElement::getText).map(String::trim)
				.anyMatch(value -> value.equalsIgnoreCase(siteName));
	}
}
