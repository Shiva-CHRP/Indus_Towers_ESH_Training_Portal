package indus.pageobjects.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class SkillMatrix extends AbstractComponent{

	public SkillMatrix(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@id='sidebar-group-INSIGHTS']//a[.//span[normalize-space()='Skill Matrix']]")
	WebElement skillMatrix;
	
	@FindBy(xpath = "//h1[normalize-space()='Skill Matrix']")
	WebElement skillMatrixName;
}
