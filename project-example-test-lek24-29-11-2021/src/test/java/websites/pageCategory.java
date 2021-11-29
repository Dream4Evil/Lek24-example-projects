package websites;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class pageCategory {
    WebDriver driver;

    //TODO - do ogólnej
    @FindBy(xpath = "//a[@class='button yellow']")
    WebElement buttonGoToShoppingCard;

    //TODO - do ogólnej
    @FindBy(xpath = "//a[@href=\"javascript: window.top.ligh7box.close();\"]")
    WebElement buttonContinueShopping;


    //======BUTTON BUY PRODUCT
    //@FindBy(xpath = "//aside//descendant::span[contains(@class,'button-cart')]") //Metoda z jednym przodkiem
    @FindBy(xpath = "//aside//aside[contains(@class, 'products')]//div[contains(@class, 'products_container')]//article//footer//div//span[contains(@class,'button-cart')]")
    List<WebElement> buttonsBuy;
    //======INCREASE / DECREASE AMOUNT BUTTONS / NUMBER FIELD
    @FindBy(xpath = "//div[@class=\"count-spinner__btn-increase js-increase spinner-event\"]")
    List<WebElement> buttonIncreaseCountOfProducts;
    @FindBy(className="count-spinner__btn-decrease js-decrease spinner-event")
    List<WebElement> buttonDecreaseCountOfProducts;
    @FindBy(xpath ="//input[@class=\"input-count\"]")
    List<WebElement> inputCountNumber;

    //TODO - priority extreme!!! - nie zawsze nie znajduje
    @FindBy(xpath = "//div[@class=\"pagination-page-list\"]//following-sibling::a[@class=\"pagination-nav-controller pagination-nav-controller--next\"][contains(text(),\"Następna\")]")
//    @FindBy(xpath = "//div[@class=\"pagination-nav-wrap\"]//a[2][@class=\"pagination-nav-controller pagination-nav-controller--next\"][@rel=\"next\"]")
    //TODO - poniższe powinno odwoływać się do drugiego elementu listy - poprawić powyższe todo!
    List<WebElement> buttonNextPage;

    @FindBy(className="button")
    WebElement buttonShowAllProducts;

    @FindBy(xpath="//div[@class=\"addtocart\"]//span[@class]//span[contains(text(),\"Powiadom o dostępności\")]")
    List<WebElement> buttonNotifyAboutAvailability;

    @FindBy(xpath = "//div[@class=\"nosearchresults-headings\"]/div[@class=\"heading-main\"]")
    WebElement noSearchResultTextContainer;


    //LEFT COLUMN - FILTERS
    @FindBy(xpath="//input[@name=\"price_to\"]")
    WebElement priceField;
    @FindBy(id = "price_filter_btn")
    WebElement priceSearch;
    //!!! Znaleźć jego brata! i jego dzieci
    @FindBy(xpath = "//span[text()=\"Rejestracja\"]//ancestor::div[contains(@class,\"name dictionary_name btnToggle\")]")
    WebElement registerCheckbox;
    @FindBy(xpath = "//span[text()=\"Rejestracja\"]//ancestor::div[contains(@class,\"name dictionary_name btnToggle\")]//following-sibling::div[contains(@class,\"checkboxes\")]//span//a")
    List<WebElement> registerCheckboxOptions;
    @FindBy(id = "filter_link_19")
    WebElement getRegisterCheckboxSearch;
    @FindBy(xpath = "//span[@class=\"checkbox-item selected\"]")
    WebElement checkedOption;


    //======POP-UP
    @FindBy(xpath = "//aside[@class=\"message warning\"]")
    WebElement textFromPopUp;

    //Kontruktor
    public pageCategory(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //======BUY-BUTTONS-AVALIABLE
    public int buyButtons__count(){
        return buttonsBuy.size();
    }

    public void buyButton__click(int number){
        buttonsBuy.get(number).click();
    }

    public void buttonGoToShoppingCard__click(){
        buttonGoToShoppingCard.click();
    }

    public void buttonContinueShopping__click(){
        buttonContinueShopping.click();
    }

    public void getRegisterCheckboxSearch__click(){
        getRegisterCheckboxSearch.click();
    }

    public String textFromPopUp(){
        return textFromPopUp.getText();
    }

    //LEFT COLUMN - FILTERS
    public void inputPrice__putValue(String number){
        priceField.sendKeys(number);
    }
    public void searchPrice__click(){
        priceSearch.click();
    }
    public void registerCheckbox__click(){
        registerCheckbox.click();
    }
    public void setRegisterCheckboxOptions(String text) throws InterruptedException {
        String textContain;
        boolean foundFlag = false;
        for (int ii = 0; ii < registerCheckboxOptions.size(); ii++) {
            //TODO - Zrobić ucinanie koncówki od "(...
            textContain = registerCheckboxOptions.get(ii).getText();
            System.out.println(String.format("Oryginalny tekst: " + textContain));
            textContain = textContain .split(" \\(")[0];
            System.out.println(String.format("Ucięty tekst: " + textContain));
            if (textContain.equals(text)) {
                registerCheckboxOptions.get(ii).click();
                foundFlag = true;
                System.out.println(String.format("\n===TEKST PASUJE!!!\n"));
                break;
            }
        }
        Assert.assertFalse(foundFlag == false);
        getRegisterCheckboxSearch.click();
    }

    public void checkedOption__click(){
        checkedOption.click();
    }


    //======INCREASE / DECREASE AMOUNT BUTTONS / NUMBER FIELD
    //TODO - zmienić na wpisywanie liczby?
    public void increaseCountOfProducts(int numberOfElement, int countOfClicks) throws InterruptedException {
//        boolean noResults = driver.findElements(By.xpath("//div[@class=\"count-spinner__btn-increase js-increase spinner-event\"]")).size()==0;
//        if (noResults){
//            System.out.println(String.format("\nBrak elementu!"));
//        } else {
//            System.out.println(String.format("\nZnaleziono elementy!"));
//        }
        for (int ii=0; ii<countOfClicks; ii++){
            buttonIncreaseCountOfProducts.get(numberOfElement).click();
//            Thread.sleep(1000);
        }
    }
    public void searchFilters(){

    }


    //======NEXT PAGE
    public void nextPage__click(){
        buttonNextPage.get(1).click();
    }


    public boolean isAvailableResults(){
        boolean noResults = driver.findElements(By.xpath("//div[@class=\"nosearchresults-headings\"]/div[@class=\"heading-main\"]")).size()==0;
        if(noResults==true){
            return true;
        } else {
            return false;
        }
    }

}
