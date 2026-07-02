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
	WebElement group;
	
	@FindBy(xpath = "//h1[normalize-space()='Organogram']")
	WebElement groupMasterName;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='List']")
	WebElement listTab;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Org Chart']")
	WebElement orgChartTab;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Table']")
	WebElement tableTab;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Canvas']")
	WebElement canvasTab;
}
