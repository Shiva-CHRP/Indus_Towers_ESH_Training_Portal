package indus.pageobjects.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;

public class RaiseTicket extends AbstractComponent{

	public RaiseTicket(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@id='sidebar-group-TOOLS & SUPPORT']//a[.//span[normalize-space()='Raise Ticket']]")
	WebElement raiseItems;
	
	@FindBy(xpath = "//h1[normalize-space()='Raise Ticket']")
	WebElement raiseItemsName;
	
	@FindBy(xpath = "//button[normalize-space()='New Ticket']")
	WebElement addNewTicket;
	
	@FindBy(xpath = "//h2[normalize-space()='New Ticket']")
	WebElement newTicketName;
	
	@FindBy(xpath = "//button[@type='submit' and normalize-space()='Submit Ticket']")
	WebElement createNewTicketBtn;
	
	@FindBy(xpath = "//button[@type='button' and normalize-space()='Cancel']")
	WebElement cancelNewTicketBtn;
}
