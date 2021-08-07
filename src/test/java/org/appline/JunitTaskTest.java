package org.appline;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class JunitTaskTest {
    private WebDriver driver;
    private WebDriverWait wait;


    @BeforeEach
    public void before() {

        System.setProperty("webdriver.chrome.driver" , "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 10, 1000);

        String baseUrl = "https://www.raiffeisen.ru";
        driver.get(baseUrl);
    }
    @Test
    public void test() {

        WebElement mortgage = driver.findElement(By.xpath("//a[@class = 'main-menu__link' and text()[contains(.,'Ипотека')]]"));
        mortgage.click();

        WebElement refinancing = driver.findElement(By.xpath("//a[@href='/retail/mortgageloans/refinansirovanie-kreditov-inyh-bankov/']"));
        refinancing.click();

        WebElement sendRequest = driver.findElement(By.xpath("//a[@href='https://ipoteka.raiffeisen.ru/promo/refin-form/']"));
        sendRequest.click();

        String fullName = "Баварский Ганс Пивасович";
        String birthDate = "05.05.1994";
        String birthPlace = "Бавария";
        String divCountry = "Германия";
        String series = "1111";
        String number = "1111";
        String issuedBy = "Клубом любителей пива";
        String dateOfIssue = "05.05.2004";
        String phoneNumber = "9999999999";
        String correctNumber = "+7 (999) 999-99-99";
        String address = "г Барнаул, ул Пивоварская, д 11";

        fillInputField(driver.findElement(By.xpath("//input[@data-marker-field='fullName']")) ,  fullName);
        WebElement chooseName = driver.findElement(By.xpath("//text()[contains(.,'" + fullName +"')]/../.."));
        chooseName.click();

        WebElement birthDateField = driver.findElement(By.xpath("//input[@data-marker-field='birthDate']"));
        birthDateField.click();
        birthDateField.sendKeys(birthDate);
        boolean checkFlag = wait.until(ExpectedConditions.attributeContains(birthDateField, "value", birthDate));
        Assertions.assertTrue(checkFlag, "Поле было заполнено некорректно");


        fillInputField(driver.findElement(By.xpath("//input[@data-marker-field='birthPlace']")) ,  birthPlace);

        WebElement gender = driver.findElement(By.xpath("//input[@name = 'gender' and @value = 'M']//.."));
        gender.click();




        WebElement citizenship = driver.findElement(By.xpath(" //span[@data-marker = 'Switcher.Jackdaw']"));
        citizenship.click();


        WebElement countryField = driver.findElement(By.xpath("//span[@data-marker = 'Select.value.Value']"));
        countryField.click();

        WebElement chooseCountry = driver.findElement(By.xpath("//text()[contains(.,'"+divCountry+"')]/../../.."));
        chooseCountry.click();

        fillInputField(driver.findElement(By.xpath("//input[@data-marker-field='foreignSeries']")) ,  series);
        fillInputField(driver.findElement(By.xpath("//input[@data-marker-field='foreignNumber']")) ,  number);
        fillInputField(driver.findElement(By.xpath("//input[@data-marker-field='foreignIssuedBy']")) ,  issuedBy);


        WebElement dateOfIssueField = driver.findElement(By.xpath("//input[@data-marker-field='foreignIssuedDate']"));
        dateOfIssueField.click();
        dateOfIssueField.sendKeys(dateOfIssue);
        boolean checkFlag2 = wait.until(ExpectedConditions.attributeContains(dateOfIssueField, "value", dateOfIssue));
        Assertions.assertTrue(checkFlag2, "Поле было заполнено некорректно");

        fillInputField(driver.findElement(By.xpath("//input[@name='registrationAddress']")) ,  address);
        WebElement chooseAddress = driver.findElement(By.xpath("//text()[contains(.,'"+address+"')]/../."));
        chooseAddress.click();


        WebElement phone = driver.findElement(By.xpath("//input[@data-marker-field='phone']"));
        phone.click();
        phone.sendKeys(phoneNumber);
        System.out.println(phone.getAttribute("value").getClass());
        Assertions.assertEquals(correctNumber, phone.getAttribute("value") , "We open sending user data window");

        WebElement sendButton = driver.findElement(By.xpath("//button[@type = 'submit']"));
        sendButton.click();

        Assertions.assertEquals("Поле обязательно для заполнения", driver.findElement(By.xpath("//input[@placeholder = 'Доход']/../..//div[text()[contains(., 'Поле обязательно')]]")).getText() , "We open sending user data window");
        Assertions.assertEquals("Поле обязательно для заполнения", driver.findElement(By.xpath("//span[@data-marker='Select.Value.Value']/../../..//div[text()[contains(., 'Поле обязательно')]]")).getText() , "We open sending user data window");
        Assertions.assertEquals("Выберите из списка", driver.findElement(By.xpath("//input[@id='pledgeAddress']/../../..//div[text()[contains(.,'Выберите')]]")).getText() , "We open sending user data window");
        Assertions.assertEquals("Поле обязательно для заполнения", driver.findElement(By.xpath("//input[@name='email']/../..//div[text()[contains(.,'Поле')]]")).getText() , "We open sending user data window");
        Assertions.assertEquals("Поле обязательно для заполнения", driver.findElement(By.xpath("//text()[contains(.,'Я соглашаюсь')]/../../../..//div[text()[contains(.,'Поле')]]")).getText() , "We open sending user data window");

        //input[@placeholder = 'Доход']/../..//div[text()[contains(., 'Поле обязательно')]]
        //span[@data-marker='Select.Value.Value']/../../..//div[text()[contains(., 'Поле обязательно')]]
        //input[@id='pledgeAddress']/../../..//div[text()[contains(.,'Выберите')]]
        //input[@name='email']/../..//div[text()[contains(.,'Поле')]]
        //text()[contains(.,'Я соглашаюсь')]/../../../..//div[text()[contains(.,'Поле')]]

        waitTime(15000);
    }

    @AfterEach
    public void after(){
        driver.quit();
    }
    private void waitTime(int timeSec){
        try {
            Thread.sleep(timeSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void fillInputField(WebElement element, String value) {
        element.click();
        element.clear();
        element.sendKeys(value);
        boolean checkFlag = wait.until(ExpectedConditions.attributeContains(element, "value", value));
        Assertions.assertTrue(checkFlag, "Поле было заполнено некорректно");
    }

}
