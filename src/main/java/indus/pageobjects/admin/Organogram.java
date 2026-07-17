package indus.pageobjects.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class Organogram extends AbstractComponent{

	public Organogram(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@id='sidebar-group-MANAGEMENT']//a[.//span[normalize-space()='Organogram']]")
	WebElement organogram;
	
	@FindBy(xpath = "//h1[normalize-space()='Organogram']")
	WebElement organogramName;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='List']")
	WebElement listTab;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Org Chart']")
	WebElement orgChartTab;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Table']")
	WebElement tableTab;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Canvas']")
	WebElement canvasTab;
	
	public String organogramMenu() {
		waitElementToAppear(organogram);
		return organogram.getText();
	}

	public void goToOrganogram() {
		organogram.click();
		waitElementToAppear(organogramName);
	}

	public String organogramScreenName() {
		return organogramName.getText();
	}
	
	public boolean listButtonEnable() {
		boolean isEnabled = listTab.isEnabled();
		return isEnabled;
	}
	
	public void navigateToListTab() {
		listTab.click();
	}
	
	public boolean orgChartTabButtonEnable() {
		boolean isEnabled = orgChartTab.isEnabled();
		return isEnabled;
	}
	
	public void navigateToOrgChartTab() {
		orgChartTab.click();
	}
	
	public boolean tableTabButtonEnable() {
		boolean isEnabled = tableTab.isEnabled();
		return isEnabled;
	}
	
	public void navigateToTableTab() {
		tableTab.click();
	}
	
	public boolean canvasTabButtonEnable() {
		boolean isEnabled = canvasTab.isEnabled();
		return isEnabled;
	}
	
	public void navigateToCanvasTab() {
		canvasTab.click();
	}
}
