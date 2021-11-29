package websites;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class pageOrderFormYourData {
    WebDriver driver;

//    @FindBy(xpath = "//input[@class='form-control']") - przez to rozwiązanie mogą znikać poprzednie elementy? Przez co size WebElementu się zmniejsza? Przez co odwołujać się do elmenetu odwołujemy się do porpzenich elementów.

    @FindBy(xpath = "//input[contains(@class, 'form-control')]")
    List<WebElement> formEmail;
//    0.E-mail
//    1.Imię
//    2.Nazwisko
//    3.Nazwa firmy
//    4.NIP
//    5.Regon
//    6.PESEL
//    7.Telefon
//    8.Ulica
//    9.Numer domu
//    10.Numer lokalu
//    11.Kod pocztowy
//    12.Miejscowość

    @FindBy(xpath = "//a[@class='button yellow']")
    WebElement buttonPaymentAndDeliveryMethods;


    //TODO - dodać metody związane z innymi metodami rozliczenia
    @FindBy(xpath = "//div[contains(text(),'')]//label//input[@type=\"radio\"]")
    List<WebElement> buttonRadioSettlementMethod;   //3 i 4 są najpierw nie widoczne
    //1.Paragon fiskalny
    //2.Faktura VAT
    //3.Osoba prywatna
    //4.Firma


    //TODO - zmienić nazwę elementu
    //TODO - przerzucić na jedną główną
    //Pierwszy przycisk do logowania w headerze
    @FindBy(xpath = "//aside[@class=\"user-nav\"]")
    WebElement buttonLoginFirst;

    //TODO - zmienić nazwę elementu?
    @FindBy(xpath = "//a[@class=\"user-nav-link user-login\"]")
    WebElement buttonLoginSecond;

    //Pole błędu - "pole wymagane"
    @FindBy(xpath = "//span[contains(@class, \"form-error\")]")
    WebElement requiredError;

    public pageOrderFormYourData(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void formEmail__sendDataToField(String data, int numberOfField){
        formEmail.get(numberOfField).sendKeys(data);
    }

    public String formField__returnData(int number){
        return formEmail.get(number).getText();
    }

    public void buttonPaymentAndDeliveryMethods__click(){
        buttonPaymentAndDeliveryMethods.click();
    }

    public boolean requiredError__isVisible(){
//        try {
//            if(requiredError.isDisplayed()){
//                System.out.println(String.format("======Element jest widoczny!======"));
//                return true;
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            System.out.println(String.format("======Element NIE jest widoczny!======"));
//            return false;
//        }
//        return false;

        if (requiredError.isDisplayed()){
            return true;
        } else {
            return false;
        }
    }

    //Usuwanie danych z pola formularza
    public void clearDataFromField(int number){
        formEmail.get(number).clear();
    }
}
