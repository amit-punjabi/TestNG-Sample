package com.zooplus.pages;

import com.zooplus.utils.DriverManager;
import com.zooplus.utils.LocatorReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.UUID;


public class BasePage {
    private LocatorReader locatorReader;
    protected static WebDriver driver;
    private By cookieAccept;

    public BasePage() {
        driver = DriverManager.getDriver();
        locatorReader = new LocatorReader("GenericLocators");
        cookieAccept = By.id(locatorReader.getLocator("cookieAccept.id"));
    }

    public void openPage(String url) {
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState")
                .equals("complete"));
        driver.manage().deleteCookieNamed("sid");
        Cookie ck = new Cookie("sid", "amit-punjabi-test-" + generateString());
        driver.manage().addCookie(ck);
        driver.manage().window().maximize();
        dismissCookiePopup();
    }

    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0,4);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void dismissCookiePopup() {
        waitForElementToBeClickable(cookieAccept);
        driver.findElement(cookieAccept).click();

    }

    public WebElement waitForElementToBePresent(By element) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        return wait.until(ExpectedConditions.presenceOfElementLocated(element));
    }

    public List<WebElement> waitForElementsToBePresent(By element) {
        return driver.findElements(element);
    }

    public Boolean waitForElementToDisappear(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public WebElement waitForElementToBeClickable(By element) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

}