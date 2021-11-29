package websites;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class pagePaymentsAndDeliveryMethods {

    WebDriver driver;

    //=========RODZAJE PŁATNOŚCI
    //TODO - zrobić ograniczenie tylko do rodzajów płatności
    @FindBy(xpath = "//div[@class=\"box_content \"]//descendant::label[contains(@class, 'delivery-box')]")
    List<WebElement> paymentMethods;    //0.Przelewy24  1.Tradycyjny przelew  2.Płatność przy odbiorze

    //=========RODZAJE PRZESYŁKI
    //TODO - czy elementy te są dostepne JEDYNIE po wybraniu formy płatności?
    @FindBy(xpath = "//header//following-sibling::div[@class=\"box_content \"]//descendant::label[contains(@class, 'delivery-box')]")
    List<WebElement> buttonDeliveryMethods;

    //TODO Lista rodzaj przesyłki
    @FindBy(xpath = "//a[@class=\"button yellow\"]")
    WebElement buttonSummary;

    //TODO - CO TO JEST?
    @FindBy(xpath = "//div[@class=\"form-group field field_despatch_check form-group--check\"]")
    WebElement otherDeliveryAddress;

    @FindBy(xpath = "//input[@name='delivery_adds']")
    WebElement deliveryNotes;



    public pagePaymentsAndDeliveryMethods(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //========= METODY PŁATNOŚCI
    public void buttonPaymentsMethods__click(int numberOfMethod){
        paymentMethods.get(numberOfMethod).click();
    }
    public void buttonPaymentsMethodsChooseRandom__click(){
        //TODO losowanie
        paymentMethods.get(1).click();
    }


    //========= RODZAJ PRZESYŁKI
    public void randomAndChooseDelieveryMethods__click(){
        //TODO random od 2 do 7; 5 - kurier Trójmiasto
        buttonDeliveryMethods.get(3).click();
    }

    public void buttonSummary__click(){
        buttonSummary.click();
    }

    public void otherDeliveryAddress__click(){
        otherDeliveryAddress.click();
    }

    public void deliveryNotes__putText(String sendText){
        deliveryNotes.sendKeys(sendText);
    }
}
