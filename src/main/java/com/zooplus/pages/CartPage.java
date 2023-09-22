package com.zooplus.pages;

import com.zooplus.utils.LocatorReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {
    // Locators
    private LocatorReader locatorReader;
    private By cartHeaderMessage;
    private By startShoppingMessage;
    private By continueShoppingButton;
    private By recommendationDiv;
    private By recommendationItemsList;
    private By recommendationItemTitle;
    private By recommendationItemPrice;
    private By recommendedProductATC;
    private By basketItemTitle;
    private By basketItemPrice;
    private By subtotalPrice;
    private By shippingCountry;
    private By changeCountryDropdpwn;
    private By countryNamesList;
    private By updateCountry;
    private By shippingCountryLoader;
    private By decrementItemButton;
    private By deleteSingleItemMessage;
    private By deleteSingleItemUndo;
    private By incrementItemButton;
    private By itemQuantity;
    private By recommendationDivHeader;
    private By checkoutButton;

    public CartPage() {
        locatorReader = new LocatorReader("CartPageLocators");
        cartHeaderMessage = By.xpath(locatorReader.getLocator("headerMessage.xpath"));
        startShoppingMessage = By.xpath(locatorReader.getLocator("startShoppingMessage.xpath"));
        continueShoppingButton = By.xpath(locatorReader.getLocator("continueShoppingButton.xpath"));
        recommendationDiv = By.xpath(locatorReader.getLocator("recommendationDiv.xpath"));
        recommendationItemsList = By.xpath(locatorReader.getLocator("recommendationItemsList.xpath"));
        recommendationItemTitle = By.xpath(locatorReader.getLocator("recommendedItemTitle.xpath"));
        recommendationItemPrice = By.xpath(locatorReader.getLocator("recommendedItemPrice.xpath"));
        recommendedProductATC = By.xpath(locatorReader.getLocator("recommendedItemsATC.xpath"));
        basketItemTitle = By.xpath(locatorReader.getLocator("basketItemTitle.xpath"));
        basketItemPrice = By.xpath(locatorReader.getLocator("basketItemPrice.xpath"));
        subtotalPrice = By.xpath(locatorReader.getLocator("checkoutSubtotalPrice.xpath"));
        shippingCountry = By.xpath(locatorReader.getLocator("shippingCountry.xpath"));
        shippingCountryLoader = By.xpath(locatorReader.getLocator("shippingCountrySvg.xpath"));
        changeCountryDropdpwn = By.xpath(locatorReader.getLocator("changeCountryDropdown.xpath"));
        countryNamesList = By.xpath(locatorReader.getLocator("countryNamesDropdown.xpath"));
        updateCountry = By.xpath(locatorReader.getLocator("changeCountrySubmit.xpath"));
        decrementItemButton = By.xpath(locatorReader.getLocator("decrementItemButton.xpath"));
        deleteSingleItemMessage = By.xpath(locatorReader.getLocator("deleteSingleItemMessage.xpath"));
        deleteSingleItemUndo = By.xpath(locatorReader.getLocator("deleteSingleItemUndo.xpath"));
        incrementItemButton = By.xpath(locatorReader.getLocator("incrementItemButton.xpath"));
        itemQuantity = By.xpath(locatorReader.getLocator("itemQuantity.xpath"));
        recommendationDivHeader = By.xpath(locatorReader.getLocator("recommendationDivHeader.xpath"));
        checkoutButton = By.xpath(locatorReader.getLocator("checkoutButton.xpath"));
    }

    // Methods
    public void openCartPage() {
        openPage("https://www.zooplus.com/checkout/cart");
    }

    public Boolean cartHeaderMessageIsNotEmpty() {
        return driver.findElement(cartHeaderMessage).getText().length() > 0;
    }

    public Boolean startShoppingMessageIsNotEmpty() {
        return driver.findElement(startShoppingMessage).getText().length() > 0;
    }

    public Boolean continueShoppingButtonExists() {
        return driver.findElement(continueShoppingButton).isDisplayed();
    }

    public Boolean recommendationDivExists() {
        return driver.findElement(recommendationDiv).isDisplayed();
    }

    public Boolean recommendationItemsExists() {
        return driver.findElements(recommendationItemsList).size() > 0;
    }

    public String getRecommendationItemTitle(int index) {
        return driver.findElements(recommendationItemTitle).get(index).getText();
    }

    public String getRecommendationItemPrice(int index) {
        return driver.findElements(recommendationItemPrice).get(index).getText();
    }

    public void addRecommendedProductToCart(int index) {
        waitForElementToBeClickable(recommendedProductATC);
        driver.findElements(recommendedProductATC).get(index).click();
    }


    public String getBasketItemTitle(int index) throws InterruptedException {
        int count = 0;
        // retry
        while(count < 3) {
            Thread.sleep(2000);
            if(driver.findElements(basketItemTitle).size() == index+1)
                break;
            count++;
        }
        waitForElementToBeClickable(basketItemTitle);
        return driver.findElements(basketItemTitle).get(index).getText();
    }

    public String getBasketItemPrice(int index) throws InterruptedException {
        int count = 0;
        // retry
        while(count < 3) {
            Thread.sleep(2000);
            if(driver.findElements(basketItemPrice).size() == index+1)
                break;
            count++;
        }
        waitForElementToBeClickable(basketItemPrice);
        return driver.findElements(basketItemPrice).get(index).getText();
    }

    public String getSubtotalPrice() {
        return driver.findElement(subtotalPrice).getText();
    }

    public WebElement getShippingCountry() {
        waitForElementToBeClickable(shippingCountry);
        return driver.findElement(shippingCountry);
    }

    public void clickShippingCountry() {
        getShippingCountry().click();
    }

    public WebElement getChangeCountryDropdown() {
        return driver.findElement(changeCountryDropdpwn);
    }

    public WebElement changeCountrySubmit() {
        return driver.findElement(updateCountry);
    }

    public List<WebElement> getShippingCountrySvg() {
        return driver.findElements(shippingCountryLoader);
    }

    public void retryTillLoaderDisappear() throws InterruptedException {
        int count = 0;
        while(count < 3) {
            if(getShippingCountrySvg().size() < 2)
                break;
            // wait for loader to disappear
            Thread.sleep(2000);
            count++;
        }
    }

    public String changeShippingCountry() throws InterruptedException {
        getChangeCountryDropdown().click();
        WebElement changedCountryNameElement  = driver.findElements(countryNamesList).get(2);
        changedCountryNameElement.click();
        String updatedCountryName = driver.findElement(changeCountryDropdpwn).getText();
        WebElement submitButton = changeCountrySubmit();
        submitButton.click();
        retryTillLoaderDisappear();
        return updatedCountryName;
    }

    public void decrementItem(int index) {
        waitForElementToBeClickable(decrementItemButton);
        driver.findElements(decrementItemButton).get(index).click();
    }

    public Boolean deleteSingleItemMessageExists() {
        waitForElementToBePresent(deleteSingleItemMessage);
        return driver.findElement(deleteSingleItemMessage).getText().length() > 0;
    }

    public Boolean deleteSingleItemUndoExists() {
        return driver.findElement(deleteSingleItemUndo).isDisplayed();
    }

    public void incrementItemQuantity(int index) throws InterruptedException {
        waitForElementToBeClickable(incrementItemButton);
        driver.findElements(incrementItemButton).get(index).click();
        // no unique locator for this row hence unable to check for loader svg
        Thread.sleep(2000);
    }

    public void sortPricesAndPrint(ArrayList<Float> prices) {
        for(int i=0;i<prices.size();i++) {
            for(int j=0;j<prices.size()-i-1;j++){
                if(prices.get(j) < prices.get(j+1)) {
                    float temp = prices.get(j);
                    prices.set(j, prices.get(j+1));
                    prices.set(j+1, temp);
                }
            }
        }
        System.out.println(prices);
    }

    public Boolean divExistsWithHeader(String header) {
        waitForElementToBeClickable(recommendationDivHeader);
        List<WebElement> headersElement = driver.findElements(recommendationDivHeader);
        List<String> headerStrings = new ArrayList<>();
        for(WebElement elementHeader : headersElement) {
            headerStrings.add(elementHeader.getText().toLowerCase());
        }
        return headerStrings.contains(header);
    }

    public void clickOnCheckoutButton() {
        driver.findElement(checkoutButton).click();
    }
}