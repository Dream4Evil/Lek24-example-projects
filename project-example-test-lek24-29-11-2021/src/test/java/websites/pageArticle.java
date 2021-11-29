package websites;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Random;

public class pageArticle {
     WebDriver driver;

     //Przyciski dodania produktów do koszyka
     @FindBy (xpath = "//header//following-sibling::div[@class=\"product-placeholder\"]//following-sibling::div[contains(class, \"\")]//descendant::div[@class=\"addtocart\"]//span[@data-short=\"Kup\"]")
     List<WebElement> buttonAddProductFromArticle;

     //Przycisk kontynuuj zakupy
     @FindBy(xpath="//a[@href=\"javascript: window.top.ligh7box.close();\"]")
     WebElement buttonContinueShopping;

     //Informacja o ilości produktów w koszyku
     @FindBy(className = "cart-count-value")
     WebElement itemsInShoppingCartInfo;

     public pageArticle(WebDriver driver){
         this.driver = driver;
         PageFactory.initElements(driver, this);
     }


     //======PRODUKTY W ARTYKUŁACH
     public int productsFromArticle__count(){
         return buttonAddProductFromArticle.size();
     }
     public void addProductFromArticle(int numberOfProduct){
         buttonAddProductFromArticle.get(numberOfProduct).click();
     }
     public void addRandomProductFromArticle(){
         Random random = new Random();
         //TODO - zamienić poniższy zakres na: productsFromArticle__count() + dodać przesuwanie jeśli produkt jest nie widoczny
         int randomNumber = random.nextInt(3);
         System.out.println(String.format("Wylosowany produkt do dodania nr: " + randomNumber));

         buttonAddProductFromArticle.get(randomNumber).click();
     }

     //TODO - priority very high - czy da się to tutaj zastosować?
     //boolean exists = driver.findElements(By.id("form-control")).size() != 0;
    //


     //TODO - przenieść do głównej? Klasy wspólnej?
     //=====Kontunowanie zakupów
     public void buttonContinueShopping__click(){
            buttonContinueShopping.click();
     }

    public String itemsInShoppingCartInfo__returnText(){
         return itemsInShoppingCartInfo.getText();
    }
}
