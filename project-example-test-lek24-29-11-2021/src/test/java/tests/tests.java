package tests;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.ObjectUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import websites.*;




public class tests {
    WebDriver driver;
    pageMain objPageMain;
    pageCategory objPageCategory;
    pageLogin objPageLogin;
    pageShoppingCart objShoppingCart;
    pageOrderFormYourData objOrderFormYourData;
    pagePaymentsAndDeliveryMethods objPaymentsAndDeliveryMethods;
    pageArticle objArticle;


    //TODO - priority high - dodać godzinę i minutę
//    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd.csv");
//    Date date = new Date();
    //System.out.println(dateFormat.format(date));
    //Plik z wynikami testów
//    String dataFileName = dateFormat.format(date);
    //Zawartość pliku z wynikami
//    String dataFileResults =  null;
//    File output = new File("C:\\Users\\medap\\Desktop\\tests\\results" + dateFormat);

    //TODO - priority very high - zapisywanie do pliku nie działa (bo wykorzystano różne metody?)
    //TODO cd - spróbować wziąć do jednej funkcji i cokolwiek uruchomić
    //FileWriter writer = new FileWriter(output);
    //FileWriter writer;

    int numberOfTest = 1;

    @BeforeTest void setUp() throws IOException {
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ernest\\Desktop\\testy automatyczne\\selenium-java-3.141.59\\chromedriver_v92_107.exe"); //Sćieżka domowa

//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\medap\\Desktop\\tests\\files_for_tests\\chromedriver_win32\\chromedriver.exe");   //Scieżka w pracy
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\medap\\Desktop\\tests\\files_for_tests\\files_from_home\\selenium-java-3.141.59\\chromedriver_v91.0.4472.101.exe");   //Scieżka w pracy, v91 latest
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\medap\\Desktop\\tests\\files_for_tests\\files_from_home\\selenium-java-3.141.59\\chromedriver_v93.0.4577.63.exe");   //Scieżka w pracy, v93 latest
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\medap\\Desktop\\tests\\files_for_tests\\files_from_home\\selenium-java-3.141.59\\chromedriver_v96.exe");   //Scieżka w pracy, v96 latest
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\medap\\Desktop\\tests\\files_for_tests\\files_from_home\\selenium-java-3.141.59\\chromedriver_v95.exe");   //Scieżka w pracy, v95 latest

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.lek24.pl/");
        driver.manage().window().maximize();


        //========= COOKIES - kliknięcie w przycisk zamknięcia; Przy zbyt szybkim przejściu po zamknięciu Cookies trzeba odczekać jakiś czas; w innym wypadku powiadomienie to pojawi się ponownie
        try {
            Thread.sleep(1000);
            WebElement infoBarClose = driver.findElement(By.xpath("//a[@class='close']"));
            infoBarClose.click();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test(priority = 1)
    public void addProductsFromAllCartsFromMainSite() throws InterruptedException {
        System.out.println(String.format("======TEST-addProductsFromAllCartsFromMainSite======"));
        objPageMain = new pageMain(driver);
        objPageMain.addAllProductsFromCarts();
        System.out.println(String.format("===PASS==="));
        System.out.println(String.format("======END-OF-TEST======\n"));
    }


    //TODO - dokończyć testowanie dla: 1)pierwszej strony leków 2)wszystkich leków
    @Test(priority = 4)
    public void checkOrderLimitForMedicines() throws InterruptedException {
        System.out.println(String.format("======TEST-checkOrderLimitForMedicines======"));
        objPageCategory = new pageCategory(driver);
        driver.get("https://www.lek24.pl/kategorie.html");
        JavascriptExecutor jse = (JavascriptExecutor)driver;    //Umożliwia poruszanie

        for (int jj=0; jj<2; jj++){

            if (jj==0){
                objPageCategory.registerCheckbox__click();
                objPageCategory.setRegisterCheckboxOptions("Lek homeopatyczny");
            } else {
                if (jj==1){
//                    System.out.println(String.format("\njj==0"));

                    jse.executeScript("window.scrollBy(0,-600)");
                    Thread.sleep(2000);

                    objPageCategory.checkedOption__click();

                    objPageCategory.getRegisterCheckboxSearch__click();
                    objPageCategory.registerCheckbox__click();
                    objPageCategory.setRegisterCheckboxOptions("Lek");
//                    System.out.println(String.format("jj==1"));
                } else {
                    System.out.println(String.format("Błąd wybierania rejestracji!"));
                }
            }

            if (objPageCategory.isAvailableResults()){
                boolean buttonNextPage__exist = false;
                int kk=0;
                do {
                    kk+=1;
                    for (int ii=0; ii<objPageCategory.buyButtons__count() ; ii++){
                        objPageCategory.increaseCountOfProducts(ii, 20);
                        objPageCategory.buyButton__click(ii);
//                System.out.println(String.format("Produkt nr: " + ii + ".   Powiadomienie Pop-Up: " + objPageCategory.textFromPopUp()));
                        //TODO - priority medium - storzyć wykrywanie rodzaju powiadomienia
                        String str1_number = objPageCategory.textFromPopUp().substring(73);
                        System.out.println(String.format("Produkt index: " + ii + ".   Liczba: " + str1_number));

                        if (Integer.parseInt(str1_number)>20){
                            System.out.println(String.format("===Produkt index: " + ii + ".   JEST WIĘKSZY OD 20!" + ".   Liczba: " + str1_number));
                            Assert.assertFalse(true);
                        }
                        objPageCategory.buttonContinueShopping__click();
                    }

                    buttonNextPage__exist = driver.findElements(By.xpath("//div[@class=\"pagination-page-list\"]//following-sibling::a[@class=\"pagination-nav-controller pagination-nav-controller--next\"][contains(text(),\"Następna\")]")).size() != 0;
                    if (buttonNextPage__exist == true){
                        objPageCategory.nextPage__click();
                        System.out.println(String.format("\n===Strona numer: " + (kk+1) ));
                    }
                }while (buttonNextPage__exist ==  true);
            }
        }

        //            System.out.println(String.format("===PASS==="));
        System.out.println(String.format("======END-OF-TEST======\n"));
//            Assert.assertTrue(true);

//            System.out.println(String.format("===FAILURE==="));
//            System.out.println(String.format("======END-OF-TEST======\n"));
//            Assert.assertFalse(true);

//        objPageCategory.setRegisterCheckboxOptions("Lek homeopatyczny");
//        if (objPageCategory.isAvailableResults()){
//            for (int ii=0; ii<objPageCategory.buyButtons__count() ; ii++){
//                objPageCategory.increaseCountOfProducts(ii, 21);
//                objPageCategory.buyButton__click(ii);
////                System.out.println(String.format("Produkt nr: " + ii + ".   Powiadomienie Pop-Up: " + objPageCategory.textFromPopUp()));
//                //TODO - priority medium - storzyć wykrywanie rodzaju powiadomienia
//                String str1_number = objPageCategory.textFromPopUp().substring(73);
//                System.out.println(String.format("Produkt nr: " + ii + ".   Liczba: " + str1_number));
//
//                if (Integer.parseInt(str1_number)>20){
//                    System.out.println(String.format("===Produkt nr: " + ii + ".   JEST WIĘKSZY OD 20!" + ".   Liczba: " + str1_number));
//                    Assert.assertFalse(true);
//                }
//                objPageCategory.buttonContinueShopping__click();
//            }
//
//
//
//            System.out.println(String.format("===PASS==="));
//            System.out.println(String.format("======END-OF-TEST======\n"));
//            Assert.assertTrue(true);
//        } else {
//            System.out.println(String.format("===FAILURE==="));
//            System.out.println(String.format("======END-OF-TEST======\n"));
//            Assert.assertFalse(true);
//        }
    }





    //TODO - priority medium - nie przechodzi na stronę główną, dlatego jest uruchamiane jako pierwszy.
    @Test(priority = 0)
    public void addExampleProductsFromSingleRandomArticleFromMainPage() throws InterruptedException {
        System.out.println(String.format("======TEST-addExampleProductsFromSingleRandromArticle======"));
        objPageMain = new pageMain(driver);

//        objPageMain.buttonArticleMainSite__click(2);  //Metoda do ręcznego wybierania artykułów;
        objPageMain.buttonRandomArticleMainSite__click();

        objArticle = new pageArticle(driver);

        //TODO - priority high - podłączyć random; jeśli jest nie widoczne to powinnyśmy to przesuwać
        //TODO - priority very high - jeśli produkty nie będą znaleziono powinien być ustaiowny status - PASS?

        boolean buttonsAddProduct__exist = driver.findElements(By.xpath("//header//following-sibling::div[@class=\"product-placeholder\"]//following-sibling::div[contains(class, \"\")]//descendant::div[@class=\"addtocart\"]//span[@data-short=\"Kup\"]")).size() != 0;
        System.out.println(String.format("Artykuł, przyciski zakupu czy istnieją: " + buttonsAddProduct__exist));

        if (buttonsAddProduct__exist){
            //TODO - poniższe losowanie?
//            objArticle.addProductFromArticle(2);  //Ręczne wybieranie
            objArticle.addRandomProductFromArticle();   //Autmatyczne wybieranie
            objArticle.buttonContinueShopping__click();
            if (objArticle.itemsInShoppingCartInfo__returnText().equals("0")){
                System.out.println(String.format("Cart info == 0: " + objArticle.itemsInShoppingCartInfo__returnText().equals("0")));
                //TODO zająć się powiadomieniami?!
                System.out.println(String.format("======END-OF-TEST======FAILURE\n"));
                Assert.assertFalse(true);
            }
        } else {
            System.out.println(String.format("======END-OF-TEST======SKIPPED-NO-PRODUCTS\n"));
        }
    }


    @Test(priority = 1)
    //WORKING - Sprawdzanie czy występują produkty z ceną mniejszą od podanej
    public void checkForProductsWithPriceLessThanValue(){

        System.out.println(String.format("======TEST-checkForProductsWithPriceLessThanValue======"));
//        dataFileResults+="======TEST-checkForProductsWithPriceLessThanValue======";

        driver.get("https://www.lek24.pl/kategorie.html");
        objPageCategory = new pageCategory(driver);
        objPageCategory.inputPrice__putValue("0.1");
        objPageCategory.searchPrice__click();
        //TODO - Asert ustawić / lub poprawić poniższe
        if (objPageCategory.isAvailableResults()){
            System.out.println(String.format("NIE Pojawiła się informacja o braku wyników"));
//            dataFileResults+="NIE Pojawiła się informacja o braku wyników";
//            Assert.assertTrue(true);
        } else {
            System.out.println(String.format("Pojawiła się informacja o braku wyników"));
//            dataFileResults+="Pojawiła się informacja o braku wyników";
//            Assert.assertFalse(true);
        }
        System.out.println(String.format("======END-OF-TEST======\n"));
//        dataFileResults+="======END-OF-TEST======";
    }


    //WORKING - Dodawanie pojedyńczych produktów z każdej kategorii. Produkty są dodawane losowo z pierwszej strony kategorii
    @Test(priority = 1)
    public void addOneProductFromCategoryToShoppingCart(){
        System.out.println(String.format("======TEST-addOneProductFromCategoryToShoppingCart======"));
        objPageMain =  new pageMain(driver);
        int categoryCounts = objPageMain.mainCategoryLinks__count();
        System.out.println(String.format("Ilość kategorii: " + categoryCounts));

        for(int i=0; i<categoryCounts; i++){
            objPageMain.mainCategoryLink__click(i);

            //========= KATEGORIA - dodanie losowego produktu do koszyka
            objPageCategory = new pageCategory(driver);
            int categoryProducts__count = objPageCategory.buyButtons__count();
            System.out.println(String.format("Ilość dostępnych produktów na aktualnej stronie w kategorii: " + categoryProducts__count));
            Random random = new Random();
            int randomNumber = random.nextInt(categoryProducts__count);
            System.out.println(String.format("Numer INDEXU dodanego produktu to: " + randomNumber));
            objPageCategory.buyButton__click(randomNumber);

            //========= WYSKAKUJĄCE POTWIERDZENIE DODANIA - kontynuowanie; pozostanie na aktualnej stronie
            objPageCategory.buttonContinueShopping__click();
        }
        System.out.println(String.format("======END-OF-TEST======\n"));
    }

    //TODO - low priority - zrobienie przypadków testowych dla każdej grupy przykasowej (np. mały koszyk, duży koszyk, produkty mleczne, itd.)
    //WORKING - Dodanie dowolnego 1 produktu z kategori do koszyka i dodanie wszystkich produktów z strefy przykasowej
    @Test(priority = 2)
    public void addProductsFromShoppingCartArea() throws InterruptedException {
        System.out.println(String.format("======TEST-addProductsFromShoppingCartArea======"));
        objPageMain = new pageMain(driver);
        Random random = new Random();

        objPageMain.mainRandomCategoryLink__click();

        objPageCategory = new pageCategory(driver);
        int randomNumberOfProduct = random.nextInt(objPageCategory.buyButtons__count());
        objPageCategory.buyButton__click(randomNumberOfProduct);
        objPageCategory.buttonGoToShoppingCard__click();

        objShoppingCart = new pageShoppingCart(driver);
        objShoppingCart.productFromShoppingCartArea__addAll();
        System.out.println(String.format("======PASS======\n"));
        System.out.println(String.format("======END-OF-TEST======\n"));
    }


    //Testowanie darmowej dostawy dla każdego produktu z sekcji "wysyłka za 0zł"
    @Test(priority = 1)
    public void freeShippingFromMainSite() throws InterruptedException {
        System.out.println(String.format("======TEST-freeShippingFromMainSite======"));
        objPageMain = new pageMain(driver);
        boolean freeShipmentFlagAllProducts = true;

        for (int ii=0; ii<objPageMain.productsWithFreeShipment__count(); ii++){
            Thread.sleep(1000);
            objPageMain.buttonCarouselSection__click(3);
            objPageMain.addProductFromCarouselSection(ii);
            objPageMain.buttonShoppingCart__click();

            objShoppingCart = new pageShoppingCart(driver);
            if(objShoppingCart.minShipingPriceIsZero()){
                System.out.println(String.format("Produkt nr." + (ii+1) + " ma darmową dostawę."));
            } else {
                System.out.println(String.format("Produkt nr." + (ii+1) + " NIE ma darmowej dostawy."));
                freeShipmentFlagAllProducts = false;
            }

            objShoppingCart.removeProduct__byNumber(0);

            driver.switchTo().alert().accept();

            driver.get("https://www.lek24.pl/");
        }
        Assert.assertFalse(!freeShipmentFlagAllProducts);

        System.out.println(String.format("===PASS==="));
        System.out.println(String.format("======END-OF-TEST======\n"));
    }


    //TODO - medium priority - zrobić dla n testów, różnych przesyłek, różnych sposobów płatności, itd.
    //Dodanie produktu do koszyka oraz przejście aż do podsumowania; BEZ KOŃCOWEGO POTWIERDZENIA ZAKUPU!
    @Test(priority = 2)
    public void simpleOrder() throws InterruptedException {
        System.out.println(String.format("======TEST-simpleOrder======"));
        Random random = new Random();
        int categoryProducts__count, randomNumber;


        //========= STRONA GŁÓWNA - przejście do losowej kategorii
        objPageMain = new pageMain(driver);
        objPageMain.mainRandomCategoryLink__click();


        //========= KATEGORIA - dodanie losowego produktu do koszyka
        objPageCategory = new pageCategory(driver);
        categoryProducts__count = objPageCategory.buyButtons__count();
        System.out.println(String.format("Ilość produktów na kategorii: " + categoryProducts__count));
        randomNumber = random.nextInt(categoryProducts__count);
        System.out.println(String.format("Wylosowana liczba to: " + randomNumber));

        objPageCategory.buyButton__click(randomNumber);


        //========= WYSKAKUJĄCE POTWIERDZENIE DODANIA - przejście do koszyka
        objPageCategory.buttonGoToShoppingCard__click();

        objShoppingCart = new pageShoppingCart(driver);
        objShoppingCart.buttonContinueToOrder__click();


        //========= KONTYNUACJA BEZ ZALOGOWANIA - bez rejestracji
        objPageLogin = new pageLogin(driver);
        objPageLogin.buttonContinueWithoutRegister__click();


        //========= TWOJE DANE
        objOrderFormYourData = new pageOrderFormYourData(driver);

//      //TODO - Uzupełnić losowanymi danymi; wczytać fakera; poprawić
//      //Generowanie fakowych danych oraz ich wyświetlanie
        Faker plFaker = new Faker(new Locale("pl-PL"));
//
//      //mail
        String mail = plFaker.internet().emailAddress();
//
//      //Sprawdzanie czy E-mail nie zawiera polskich znaków
        String [] polskieZnaki = { "ą", "ć", "ę", "ł", "ń", "ó", "ś", "ż", "ź" };
        int sizeOfPolskieZnaki = polskieZnaki.length;
        for (int i=0; i<sizeOfPolskieZnaki; i++){
            if (mail.contains(polskieZnaki[i])){
                i=0;
                mail = plFaker.internet().emailAddress();
            }
        }
        System.out.println(String.format("E-mail: " + mail));


        //TEST - pola numer domu i numer lokalu - powinny akceptować znaki specjalne oraz powinny być maxlength=7
        String addressNumber2 = plFaker.address().buildingNumber();
        int addressNumber2_size = addressNumber2.length();

        //Jeśli mniejsze niż 7 znaków to dodajemy dodatkowe znaki aż do otrzymania 8 znaków (sprwadzanie wartości brzegowych)
        if(addressNumber2_size < 8) {
            int number_difference = 8-addressNumber2_size;
            for(int i=0; i<number_difference; i++){
                //TODO - random number
                addressNumber2 += i;
            }
        }
        System.out.println(String.format("Adres domu (8pozycji): " + addressNumber2));
        objOrderFormYourData.formEmail__sendDataToField(addressNumber2, 9);

        //Sprawdzenie czy powiadomienie o błędzie jest wyświetlane, jeśli tak to zakończymy niepowodzeniem
        //TODO - priority medium - trzeba znaleźć metody dla braku wyświetlania elementu; aktualnie zgłasza błąd; aczkolwiek i tak nie przepuści przechodząć dalej
//        if(objOrderFormYourData.requiredError__isVisible()){
//            Assert.assertFalse(true);
//        }

        //TODO - medium priority - Sprawdzić zawartość pola.
        if(objOrderFormYourData.formField__returnData(9).length() > 7){
            System.out.println(String.format("Długość pola z numerem domu > 7"));
            Assert.assertFalse(true);
        }
        //TODO - priority high - błędne zliczanie? "= 0"
        System.out.println(String.format("Długość pola z numerem domu (przy próbie wpisania 8 znaków) = " + objOrderFormYourData.formField__returnData(9).length()));

        //Przypisanie 7 znaków
        if(addressNumber2.length() > 7){
            addressNumber2 = addressNumber2.substring(0,7);
        }
        System.out.println(String.format("Adres domu (7pozycji): " + addressNumber2));
        objOrderFormYourData.formEmail__sendDataToField(addressNumber2, 9);


        String localAddres2 = addressNumber2;
        objOrderFormYourData.formEmail__sendDataToField(localAddres2, 10);
        System.out.println(String.format("Adres mieszkania (7pozycji): " + localAddres2));


        //TODO - priority medium - należy wyjśc z pola aby zaczeły się pojawiać komunkaty o błędzie - poprawić
        //TODO - priority medium - dodać inne przypadki testowe do tablicy
        //TODO - priority medium - przetestować pole nr.9
        //Sprawdzanie znaków specjalnych dla póla nr.10
        String[] tab = {" ", "/"};
        int[] numberOfFormInput = {10};
        for (int ii=0; ii<tab.length; ii++){
            objOrderFormYourData.clearDataFromField(numberOfFormInput[0]);
            objOrderFormYourData.formEmail__sendDataToField(tab[ii], numberOfFormInput[0]);
            //driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
            Thread.sleep(1000);
            boolean exists = driver.findElements(By.id("form-control")).size() != 0;
            //driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            Thread.sleep(1000);
            if (exists) {
                System.out.println(String.format("El. Formularza: " + numberOfFormInput[0] + ". Próba nr." + ii + ". Znaleziono komunikat o błędzie"));
            } else {
                System.out.println(String.format("El. Formularza: " + numberOfFormInput[0] + ". Próba nr." + ii + ". NIE znaleziono komunikatu o błędzie"));
            }
//            objOrderFormYourData.formEmail__sendDataToField(" ", 10);
//            objOrderFormYourData.formEmail__sendDataToField("/", 10);
//            objOrderFormYourData.formEmail__sendDataToField("1 / 2a", 10);
        }


        String name = plFaker.name().firstName();
        String surname = plFaker.name().lastName();
        int phoneNumber = plFaker.number().numberBetween(400000000,999999999);
        String streetName = plFaker.address().streetName();
        String addressNumber = plFaker.address().buildingNumber();
//      String localAddres = plFaker.address().secondaryAddress();//błędne? Zaczyna się częśto od Apt., Suite, itp.
        int localAddres = plFaker.number().numberBetween(1,150);
        String postalCode = plFaker.address().zipCode();
        String city = plFaker.address().city();
        System.out.println(String.format("\nImie: %s\nNazwisko: %s\nNumer telefonu: %s\nUlica: %s\nNumer budynku: %s\nNumer lokalu: %s\nKod pocztowy: %s\nMiasto: %s",
                name,surname, phoneNumber, streetName, addressNumber, localAddres,postalCode, city));
////
////    0.E-mail 1.Imię 2.Nazwisko 3.Nazwa firmy 4.NIP 5.Regon 6.PESEL 7.Telefon 8.Ulica 9.Numer domu 10.Numer lokalu 11.Kod pocztowy12.Miejscowość


        objOrderFormYourData.formEmail__sendDataToField(mail,0);
        objOrderFormYourData.formEmail__sendDataToField(name,1);
        objOrderFormYourData.formEmail__sendDataToField(surname,2);
        objOrderFormYourData.formEmail__sendDataToField(String.valueOf(phoneNumber), 7);
        objOrderFormYourData.formEmail__sendDataToField(streetName,8);
        objOrderFormYourData.formEmail__sendDataToField(addressNumber, 9);
        objOrderFormYourData.formEmail__sendDataToField(Integer.toString(localAddres), 10);
        objOrderFormYourData.formEmail__sendDataToField(postalCode, 11);
        objOrderFormYourData.formEmail__sendDataToField(city, 12);

        objOrderFormYourData.buttonPaymentAndDeliveryMethods__click();

        objPaymentsAndDeliveryMethods = new pagePaymentsAndDeliveryMethods(driver);

        objPaymentsAndDeliveryMethods.buttonPaymentsMethodsChooseRandom__click();

        //Aktualnie domyślnym sposobem płatności jest Poltraf, dlatego można pominąć ten krok.
        //objPaymentsAndDeliveryMethods.randomAndChooseDelieveryMethods__click();

        objPaymentsAndDeliveryMethods.buttonSummary__click();
        System.out.println(String.format("======END-OF-TEST======\n"));
    }


    @AfterTest
    void tearDown(){
        driver.quit();
    }


    @AfterMethod
    void afterTest() throws IOException {
        //======Screenshot======
        Screenshot screenshot;
        screenshot = new AShot().shootingStrategy(ShootingStrategies.scaling(2)).takeScreenshot(driver);

        Date data = new Date();

        //Ścieżka w pracy
        String fileName = "C:\\Users\\medap\\Desktop\\tests\\projects\\project-lek24-v15-8-21\\project-21-8-7-v12-8-21--after-guru-attempt\\screenshots\\screenshot" + numberOfTest + ".jpg";
        numberOfTest++;
        ImageIO.write(screenshot.getImage(), "jpg", new File(fileName));
    }


}
