package websites;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class pageMain {
    WebDriver driver;

    //=========MENU
    @FindBy (className = "nav-main-link")
    List<WebElement> categoryLink;


    //=========POP-UP czy kontynuować czy przejść do koszyka
    //TODO - do ogólnej
    @FindBy(xpath = "//a[@href=\"javascript: window.top.ligh7box.close();\"]")
    WebElement buttonContinueShopping;


    //=========4 sekcje - polecane, nowości, krótka data, wysyłka za zero
    @FindBy(xpath = "//a[@data-tabid]")                           //Karuzela produktów na wersji PC 0.Polecane 1.Nowości 2.Krótka data 3.Wysyłka 0zł
    List<WebElement> carouselSectionsFromMainPage;
    @FindBy(xpath="//div[@data-title=\"Wysyłka za 0zł\"]//descendant::span[@class=\"button special button-cart\"]")
    List<WebElement> buttonCarouselFreeShipmentProducts;          //Lista produktów z darmową dostawą; bez produktów z "Zapytaj o produkt"
    @FindBy (className = "tab_button tab_button_mini")            //Karuzela produktów na wersji mobilnej 0.Polecane 1.Nowości 2.Krótka data 3.Wysyłka za 0zł
    List<WebElement> carouselOfProducts;


    //=========4 karty na stronie
    @FindBy(xpath = "//div[@class=\"cards-section\"]//descendant::span[contains(@class,\"button special button-cart\")]")
    List<WebElement> buttonAllProductsFromCarts;

    @FindBy(xpath="//div[1][@class=\"cards\"]//div[@class=\"card card-left\"]//descendant::span[contains(@class,\"button special button-cart\")]")
    List<WebElement> buttonAllProductsFromFirstCart;
    @FindBy(xpath="//div[1][@class=\"cards\"]//div[@class=\"card card-right\"]//descendant::span[contains(@class,\"button special button-cart\")]")
    List<WebElement> buttonAllProductsFromSecondCart;
    @FindBy(xpath="//div[2][@class=\"cards\"]//div[@class=\"card card-left\"]//descendant::span[contains(@class,\"button special button-cart\")]")
    List<WebElement> buttonAllProductsFromThirdCart;
    @FindBy(xpath="//div[2][@class=\"cards\"]//div[@class=\"card card-right\"]//descendant::span[contains(@class,\"button special button-cart\")]")
    List<WebElement> buttonAllProductsFromFourthCart;

    //Przycisk - następne produkty
    @FindBy(xpath = "//div[contains(@class,\"swiper-button-next\")]")
    List<WebElement> buttonCartsNextProducts;



    @FindBy(className = "button kc-show-all")
    WebElement buttonShowMoreArticles;
    //TODO wrzucić do wspólnej klasy
    @FindBy(className = "cart-group-count")
    WebElement buttonShoppingCart;


    //Wyskakujące okienko z potwierdzeniem
    //TODO - do ogólnej
    @FindBy(xpath = "//a[@href=\"javascript: window.top.ligh7box.close();\"]")
    WebElement buttonContinueShopping1;
    @FindBy(xpath = "//div[@class=\"header\" and text()=\"Dodanie produktu do koszyka\"]")
    WebElement addedProductToCartInfo;


    //Konstruktor
    public pageMain(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //=========MENU
    public int mainCategoryLinks__count(){
        return categoryLink.size();
    }

    public void mainRandomCategoryLink__click(){
        Random random = new Random();
        Integer randomNumber = random.nextInt(mainCategoryLinks__count());
        System.out.println(String.format("Wylosowany number kategorii: " + randomNumber));
        categoryLink.get(randomNumber).click();
    }

    public void buttonShoppingCart__click(){
        buttonShoppingCart.click();
    }

    public void mainCategoryLink__click(int number){
        categoryLink.get(number).click();
    }

    public void carouselOfProducts__click(int number){
        carouselOfProducts.get(number).click();
    }


    //=========ARTYKUŁY Z BLOGA
    @FindBy (xpath = "//h2[contains(text(),\"Porady naszych ekspertów\")]")
    WebElement articlesMainHeader;
    @FindBy (xpath = "//h2[contains(text(),\"Porady naszych ekspertów\")]//following-sibling::aside//descendant::article")
    List<WebElement> articlesMainPage;
    @FindBy (xpath = "//h2//following-sibling::aside//descendant::div[@class=\"owl-nav\"]//button[@class=\"owl-next\"]")
    WebElement buttonArticleNext;
    public void buttonShowMoreArticles__click(){
        buttonShowMoreArticles.click();
    }
    public int articlesMainPage__size(){
        return articlesMainPage.size();
    }
    public void buttonArticlesNext__click(){
        buttonArticleNext.click();
    }


    //======ARTICLES ON MAIN PAGE
    public void buttonRandomArticleMainSite__click() throws InterruptedException {
        Random random = new Random();
        Thread.sleep(1000); //TODO - priority very high! - obejść? powinniśmy dać waita dla wczytanie głownego elementu, a nie funkcji?! Element nadal może być  = 0
        System.out.println(String.format("Ilość artykułów na stronie głównej: " + articlesMainPage__size()));

        Thread.sleep(4000);
        boolean articlesOnMainPage__exist = driver.findElements(By.xpath("//h2[contains(text(),\"Porady naszych ekspertów\")]//following-sibling::aside//descendant::article")).size()==0;

        System.out.println(String.format("Artykuły == 0: " + articlesOnMainPage__exist));
        boolean articlesHeader = articlesMainHeader.isDisplayed();
        System.out.println(String.format("Header artykułów jest wyświetlany: " + articlesHeader));


        Integer randomNumber = random.nextInt(articlesMainPage__size());
        //TODO - priority high - klikanie dalej niż czwarty artykuł
        System.out.println(String.format("Wylosowany INDEX artykuły do kliknięcia: " + randomNumber));
        while (!articlesMainPage.get(randomNumber).isDisplayed()){
            buttonArticlesNext__click();
            Thread.sleep(1000);
        }
        articlesMainPage.get(randomNumber).click();
    }
    public void buttonArticleMainSite__click(int number){
        articlesMainPage.get(number).click();
    }


    //======Produkty z darmową przesyłką
    public void buttonCarouselSection__click(int number){
        carouselSectionsFromMainPage.get(number).click();
    }
    public int productsWithFreeShipment__count(){
        return buttonCarouselFreeShipmentProducts.size();
    }
    //Dodawanie wszystkich produktów z darmowej dostawy
    public void addAllProductsFromCarouselSection(){
        for(int i=0; i<productsWithFreeShipment__count(); i++){
            buttonCarouselFreeShipmentProducts.get(i).click();
            buttonContinueShopping.click();
        }
    }
    //Dodanie pojedyńczego produktu z wszystkich produktów z darmowej dostawy
    public void addProductFromCarouselSection(int number){
        buttonCarouselFreeShipmentProducts.get(number).click();
        buttonContinueShopping.click();
    }

    //======4 CARTS
        public void addProductFromCart(int number){
            buttonAllProductsFromCarts.get(number).click();
        }
        public void addAllProductsFromCarts() throws InterruptedException {
            List<WebElement> actualCart = null;
            JavascriptExecutor jse = (JavascriptExecutor)driver;    //Umożliwia poruszanie
            //TODO - priority high - wykrywanie ile jest calych kart. Czy było to robione?
            for (int jj = 0; jj<4; jj++){
                if (jj == 0){
                    actualCart = buttonAllProductsFromFirstCart;
                }
                else {
                    if(jj == 1){
                        actualCart = buttonAllProductsFromSecondCart;
                    } else {
                        if(jj == 2){
                            actualCart = buttonAllProductsFromThirdCart;
                            //TODO - priority very low - czy nie ma metody automatycznej?
                            jse.executeScript("window.scrollBy(0,-500)");
                        } else {
                            if(jj == 3){
                                actualCart = buttonAllProductsFromFourthCart;
                            } else {
                                System.out.println(String.format("OUT OF BOUND - sprawdzić kod!"));
                            }
                        }
                    }
                }

                for (int ii = 0; ii<actualCart.size(); ii++){   //Przejście po produktach z pojedyńczej karty + klikanie strzałkę "dalej"
                    if(actualCart.get(ii).isDisplayed()) {
                        actualCart.get(ii).click();
                        if (ii == 0){
                            System.out.println(String.format("Pętla nr." + jj + ". Rozmiar aktualnej karty: " + actualCart.size()));
                        }

                        boolean addedProductToCartInfo__exist = driver.findElements(By.xpath("//div[@class=\"header\" and text()=\"Dodanie produktu do koszyka\"]")).size()!=0;
                        if (addedProductToCartInfo__exist){
                            buttonContinueShopping1.click();
//                          Thread.sleep(3000);
                        } else {
                            System.out.println(String.format("Nie znaleziono okienka z komunikatem 'Dodano produkt do koszyka'"));
                            System.out.println(String.format("Wyświetlił się inny komunikat."));
                            System.out.println(String.format("\nNumer produktu w któym wystąpił błąd: " + (ii + 1) + ". Uwaga to NIE INDEX!\n"));
                            Assert.assertFalse(true);
                            System.out.println(String.format("===FAILURE==="));
                            System.out.println(String.format("======END-OF-TEST======\n"));
                        }
                    } else {
                        //TODO - priority low - czy da się to streścić?
                        if ( jj == 0){
                            buttonCartsNextProducts.get(2).click();
                        } else {
                            if (jj == 1){
                                    buttonCartsNextProducts.get(3).click();
                            } else {
                                if (jj == 2){
                                    buttonCartsNextProducts.get(0).click();
                                } else {
                                    if (jj == 3){
                                        buttonCartsNextProducts.get(1).click();
                                    }
                                }
                            }
                        }
                        Thread.sleep(1500);
                        ii--;
                    }
                }

            }
        }
}
