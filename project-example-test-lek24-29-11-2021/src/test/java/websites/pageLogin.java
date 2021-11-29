package websites;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class pageLogin {
    WebDriver driver;

    @FindBy(xpath = "//div[@class='buttons continue-without-register']//a[contains(@class, 'button')]")
    WebElement buttonContinueWithoutRegister;

    //Konstruktor
    public pageLogin(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void buttonContinueWithoutRegister__click(){
        buttonContinueWithoutRegister.click();
    }
}
