package websites;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class pageShoppingCart {

    WebDriver driver;

    //=========MINIMALNA KWOTA ZA PRZESYŁKĘ
    @FindBy(xpath = "//tr[@class=\"table__row table__row-delivery-cheapest\"]//td[2]")
    WebElement textMinShipingPrice;

    //TODO - przycisk z następnego etapu? Sprawdzić
//    @FindBy(className = "button yellow")
    @FindBy(xpath = "//a[@class = \"button yellow\"]")
    WebElement buttonContinueToOrder;


    //Przycisk - usunięcie wszystkich produktów
    @FindBy(xpath = "//a[@class=\"clear-cart\"]")
    WebElement buttonRemoveAllProducts;
    //Przycisk - usunięcie pojedynczego produktu
    @FindBy(xpath = "//a[@class=\"button button-remove\"]")
    List<WebElement> buttonRemoveProducts;


    //Pole - dodatkowe uwagi do zamówienia np. kolory
    @FindBy(xpath = "//textarea[@name=\"adds\"]")
    WebElement notesToCourier;

    //=========STREFAPRZYKASOWA
    @FindBy(xpath = "//span[@class=\"button special button-cart\"]")
    List<WebElement> buttonProductsFromShoppingArea;
    //===PRZECHODZENIE DO NASTĘPNEGO ETAPU
    @FindBy(xpath = "//button[@class=\"owl-next\"]")
    WebElement carouselNextProducts;


    //TODO - wyrzucić do głównej
    //przycisk kontynuuj zakupy
    @FindBy(xpath="//a[@href=\"javascript: window.top.location.reload();window.top.ligh7box.close();\"]")
    WebElement buttonContinueShopping;

    //Konstruktor
    public pageShoppingCart(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void buttonContinueToOrder__click(){
        buttonContinueToOrder.click();
    }

    public void removeProduct__byNumber(int numberOfProduct){
        buttonRemoveProducts.get(numberOfProduct).click();
    }


    //=========STREFAPRZYKASOWA
    public int productFromShoppingCartArea__count(){
        return buttonProductsFromShoppingArea.size();
    }
    public int productFromShoppingCartArea__countAllVisibleElements(){
        int countOfElements = productFromShoppingCartArea__count();
        int visibleElements = 0;
        for (int ii=0; ii<countOfElements; ii++){
            if (buttonProductsFromShoppingArea.get(ii).isDisplayed()){
                visibleElements++;
            }
        }
        System.out.println(String.format("Ilość widocznych elementów na pierwszej str. karuzeli: " + visibleElements + "\n"));
        return visibleElements;
    }
    public void productFromShoppingCartArea__addAll() throws InterruptedException {
        int visibleElements = productFromShoppingCartArea__countAllVisibleElements();
        for(int ii=0; ii<productFromShoppingCartArea__count(); ii++) {
                int loopNumber = ii+1;
                while ((loopNumber)>visibleElements){
                    Thread.sleep(2000);
                    carouselNextProducts.click();
                    System.out.println(String.format("Przesunięto karuzelę."));
                    loopNumber-=5;
                }
            buttonProductsFromShoppingArea.get(ii).click();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            buttonContinueShopping.click();
            System.out.println(String.format("Numer produktu: " + (ii+1) +  ".Dodano produkt."));
        }
    }


    public boolean minShipingPriceIsZero(){
        String text = textMinShipingPrice.getText();
        String text2 = "0,00 zł";
//        System.out.println(String.format("\nCena minimalna: " + text));

        if(text.equals(text2)){       //W PRZYPADKU STRINGÓW NIE UŻYWAĆ == TYLKO EQUALS!!! STRING!!!
            return true;
        } else {
            return false;
        }
    }
}
