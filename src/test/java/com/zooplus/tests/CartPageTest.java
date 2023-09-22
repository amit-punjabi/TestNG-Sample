package com.zooplus.tests;

import com.zooplus.pages.CartPage;
import com.zooplus.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class CartPageTest {
    private WebDriver driver;
    private CartPage cartPage;

    @BeforeMethod
    public void setUp() {
        driver = DriverManager.getDriver();
        cartPage = new CartPage();
        cartPage.openCartPage();
    }

    @Test
    public void cartPageOnLoadTest() {
        Assert.assertTrue(cartPage.getCurrentUrl().contains("/cart"), "Incorrect endpoint on cart page");
        Assert.assertTrue(cartPage.cartHeaderMessageIsNotEmpty(), "Header string is empty");
        Assert.assertTrue(cartPage.startShoppingMessageIsNotEmpty(), "Start shopping message string is empty");
        Assert.assertTrue(cartPage.continueShoppingButtonExists(), "Contniue shopping button is not present");
        Assert.assertTrue(cartPage.recommendationDivExists(), "Recommendation div is not present");
        Assert.assertTrue(cartPage.recommendationItemsExists(), "Recommendation items are not present");
    }

    @Test
    public void addSingleRecommendedProductToCartTest() throws InterruptedException {
        String itemTitle = cartPage.getRecommendationItemTitle(0);
        String itemPrice = cartPage.getRecommendationItemPrice(0);
        cartPage.addRecommendedProductToCart(0);
        Assert.assertEquals(itemTitle, cartPage.getBasketItemTitle(0), "Item title doesn't match!");
        Assert.assertEquals(itemPrice, cartPage.getBasketItemPrice(0), "Item price in basket doesn't match!");
        Assert.assertEquals(itemPrice, cartPage.getSubtotalPrice(), "Single product subtotal doesn't match");
    }

    @Test
    public void changeOfCountryOnCheckoutTest() throws InterruptedException {
        cartPage.addRecommendedProductToCart(0);
        cartPage.clickShippingCountry();
        String selectedShippingCountryFromDropdown = cartPage.changeShippingCountry().trim();
        String finalShippingCountry = cartPage.getShippingCountry().getText().trim();
        System.out.println("Final" + finalShippingCountry + "dropdown" + selectedShippingCountryFromDropdown);
        Assert.assertTrue(finalShippingCountry.equals(selectedShippingCountryFromDropdown),
                "Country change not reflected correctly!");
    }

    @Test
    public void removeSingleItemAddedToCartTest() {
        cartPage.addRecommendedProductToCart(0);
        cartPage.decrementItem(0);
        Assert.assertTrue(cartPage.deleteSingleItemMessageExists(), "Delete Sing item message not displayed!");
        Assert.assertTrue(cartPage.deleteSingleItemUndoExists(), "Delete Sing item undo not displayed!");
    }

    @Test
    public void addMultipleRecommendedProductToCartTest() throws InterruptedException {
        String itemTitle1 = cartPage.getRecommendationItemTitle(0);
        cartPage.addRecommendedProductToCart(0);
        Assert.assertEquals(itemTitle1, cartPage.getBasketItemTitle(0), "1st Item title doesn't match!");
        String itemPrice1 = cartPage.getBasketItemPrice(0);
        // add second item
        String itemTitle2 = cartPage.getRecommendationItemTitle(1);
        cartPage.addRecommendedProductToCart(1);
        Assert.assertEquals(itemTitle2, cartPage.getBasketItemTitle(1), "2nd Item title doesn't match!");
        String itemPrice2 = cartPage.getBasketItemPrice(1);
        // add third item
        String itemTitle3 = cartPage.getRecommendationItemTitle(2);
        cartPage.addRecommendedProductToCart(2);
        Assert.assertEquals(itemTitle3, cartPage.getBasketItemTitle(2), "3rd Item title doesn't match!");
        String itemPrice3 = cartPage.getBasketItemPrice(2);
        Float totalPrice = Float.parseFloat(itemPrice1.substring(1))
                + Float.parseFloat(itemPrice2.substring(1))
                + Float.parseFloat(itemPrice3.substring(1));
        Assert.assertEquals(totalPrice.toString(), cartPage.getSubtotalPrice().substring(1),
                "Multiple product subtotal doesn't match");
    }

    @Test
    public void incrementProductQuantityTest() throws InterruptedException {
        cartPage.addRecommendedProductToCart(0);
        Float itemSingleQuantityPrice = Float.parseFloat(cartPage.getBasketItemPrice(0).substring(1));
        cartPage.incrementItemQuantity(0);
        Float expectedPrice = 2 * itemSingleQuantityPrice;
        Assert.assertEquals(expectedPrice.toString(), cartPage.getBasketItemPrice(0).substring(1),
                "Item price on quantity increase doesn't match!");
    }

    @Test
    public void youMightAlsoLikeDivExistsBelowBasketTest() {
        cartPage.addRecommendedProductToCart(0);
        Assert.assertTrue(cartPage.divExistsWithHeader("you might also like"), "You might also like div not present");
    }

    @Test
    public void otherRecommendedProductsDivExistsBelowBasketTest() {
        cartPage.addRecommendedProductToCart(0);
        Assert.assertTrue(cartPage.divExistsWithHeader("other recommended products"), "Recommendation div is not present");
    }

    @Test
    public void proceedToCheckoutTest() throws InterruptedException {
        cartPage.addRecommendedProductToCart(0);
        Assert.assertTrue(!cartPage.getBasketItemTitle(0).isEmpty());
        cartPage.clickOnCheckoutButton();
        Assert.assertTrue(!cartPage.getCurrentUrl().contains("/cart"), "Redirection to sign in didn't happen!");
    }

    @Test
    public void sortAndPrintItemsLowestToHighestInCart() throws InterruptedException {
        ArrayList<Float> prices = new ArrayList<>();
        // add first item
        cartPage.addRecommendedProductToCart(0);
        String itemPrice1 = cartPage.getBasketItemPrice(0);
        // add second item
        cartPage.addRecommendedProductToCart(1);
        String itemPrice2 = cartPage.getBasketItemPrice(1);
        // add third item
        cartPage.addRecommendedProductToCart(2);
        String itemPrice3 = cartPage.getBasketItemPrice(2);
        prices.add(Float.parseFloat(itemPrice1.substring(1)));
        prices.add(Float.parseFloat(itemPrice2.substring(1)));
        prices.add(Float.parseFloat(itemPrice3.substring(1)));
        // call function to sort and print the prices
        cartPage.sortPricesAndPrint(prices);
    }


    // run always to quit the session on any error
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }
}