package websites;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

//TODO - wrzucić strony z formularza zamówiania do jednej "paczki"
public class pageSummary {

    WebDriver driver;

    @FindBy(className = "button yellow")
    WebElement buttonOrder;

    public pageSummary(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void buttonOrder(){
        buttonOrder.click();
    }
}
