package step;

import Base.BaseMethods;
import com.thoughtworks.gauge.Step;
import helper.ElementHelper;
import helper.StoreHelper;
import model.ElementInfo;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;
import java.util.Set;

public class StepImplementation extends BaseMethods {



    @Step("<second> second wait")
    public void waitBySeconds(int seconds){
        waitByMilliSeconds(seconds * 1000);
    }


    @Step("Go to <url>")
    public void goToUrl(String url){

        driver.get(url);
        logger.info(url + " going to.");

    }

    @Step("Wait for <key> and click")
    public void checkElementVisibiltyAndClick(String key){
        isElementVisible(key,5);
        isElementClickable(key,10);
        Assert.assertTrue("Aranan"+key+"elementi sayfa üzerinde bulunamadı",isElementVisible(key,5));
        logger.info(key+"elementine tıklandı");
        clickElement(key);
    }

    @Step("Hover to <key>")
    public void hoverStep(String key){
        isElementVisible(key,5);
        hoverElement(key);
    }
    @Step({"<key> li elementi bul yoksa <message> mesajını hata olarak göster",
            "Find element by <key> if not exist show error message <message>"})
    public void isElementExist(String key, String message) {
        Assert.assertTrue("element bulunamadı. "+message, isElementVisible(key,7));
    }


    @Step("Is <key> element Visible ? <timeout>")
    public boolean isElementVisible(String key, int timeout){
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        try{
            WebDriverWait wait = new WebDriverWait(driver,timeout);
            waitVisibilityOfElementLocatedBy(ElementHelper.getElementInfoToBy(elementInfo));
            return true;
        }catch (Exception e){
            logger.info(key +" not visible");
            return false;
        }
    }
    @Step("Is <key> element Clickable ? <timeout>")
    public boolean isElementClickable(String key, int timeout){
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        try{
            WebDriverWait wait = new WebDriverWait(driver,timeout);
            waitClickableOfElementLocatedBy(ElementHelper.getElementInfoToBy(elementInfo));
            return true;
        }catch (Exception e){
            logger.info(key +" not visible");
            return false;
        }
    }

    @Step("Write <text> to the <key> and clear area")
    public void sendKeys(String text, String key){
        isElementVisible(key,5);
        isElementClickable(key,5);
        clearAndSendKey(text,key);

        logger.info(text+" written to "+key);
    }

    @Step("Go to <index> tab")
    public void goToCategoryTab(String tabKey){

        WebElement webElement = driver.findElement(By.xpath("(//ul[@class='main-nav']/li/a)["+tabKey+"]"));
        webElement.click();


    }

    @Step("Check page title text <tabText>")
    public final void assertPage(String expectedPageTitle){

        String titleText = driver.getTitle();
        System.out.println("Title "+titleText);

        Assert.assertEquals("Beklenen sayfa başlığı sayfadaki ile uyuşmuyor.",expectedPageTitle,titleText);
        logger.info("Sayfa başlığı doğrulandı.");
    }


    @Step("Write Logger -> <text>")
    public void loggerInfo (String text){

        logger.info(text);

    }

    public String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
    @Step("Write randomly email")
    public void mailGenerator()
    {
        String email= getSaltString()+"@gmail.com" ;
        sendKeys(email,"CREAT_ACCOUNT_EMAIL_INPUT_AREA");

    }
    @Step("Selecth month")
    public void selectMonth( ){
        Select s = new Select(new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.id("Ecom_Payment_Card_ExpDate_Month"))));
        s.selectByIndex(1);

    }
    @Step("Selecth year")
    public void selectYear(){
        Select s = new Select(new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.id("Ecom_Payment_Card_ExpDate_Year"))));
        s.selectByIndex(2);

    }


    @Step("Click with javascript <key>")
    public void javaScriptClicker(String key){
        isElementVisible(key,5);
        isElementClickable(key,5);
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        WebElement element = driver.findElement(ElementHelper.getElementInfoToBy(elementInfo));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
    }
    @Step("Delete all cookies")
    public void deleteAllCookies(){

        Set<Cookie> allCookies = driver.manage().getCookies();
        for (Cookie cookie : allCookies) {
            driver.manage().deleteCookieNamed(cookie.getName());
        }
}}
