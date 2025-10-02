package com.example.app.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for SauceDemo Products (Inventory) Page.
 * URL: https://www.saucedemo.com/inventory.html
 */
public class ProductsPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(ProductsPage.class);

    // Locators
    private final By pageTitle = By.cssSelector(".title");
    private final By inventoryItems = By.cssSelector(".inventory_item");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartLink = By.cssSelector(".shopping_cart_link");
    private final By productSortDropdown = By.cssSelector("[data-test='product_sort_container']");
    private final By burgerMenuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    // ============= DYNAMIC LOCATORS =============

    private By addToCartButton(String productName) {
        return By.xpath(String.format("//div[@class='inventory_item'][.//div[text()='%s']]//button[contains(@id, 'add-to-cart')]", productName));
    }

    private By removeFromCartButton(String productName) {
        return By.xpath(String.format("//div[@class='inventory_item'][.//div[text()='%s']]//button[contains(@id, 'remove')]", productName));
    }

    private By productPrice(String productName) {
        return By.xpath(String.format("//div[@class='inventory_item'][.//div[text()='%s']]//div[@class='inventory_item_price']", productName));
    }

    // ============= ACTIONS =============

    public void addProductToCart(String productName) {
        logger.info("Adding product to cart: {}", productName);
        click(addToCartButton(productName));
    }

    public void removeProductFromCart(String productName) {
        logger.info("Removing product from cart: {}", productName);
        click(removeFromCartButton(productName));
    }

    public CartPage openCart() {
        logger.info("Opening shopping cart");
        click(cartLink);
        return new CartPage(driver);
    }

    public void sortProducts(String sortOption) {
        logger.info("Sorting products by: {}", sortOption);
        selectByValue(productSortDropdown, sortOption);
    }

    public void logout() {
        logger.info("Logging out");
        click(burgerMenuButton);
        click(logoutLink);
    }

    // ============= VALIDATIONS =============

    public boolean isProductsPageDisplayed() {
        boolean displayed = isDisplayed(pageTitle);
        logger.debug("Products page displayed: {}", displayed);
        return displayed;
    }

    public String getPageTitle() {
        String title = getText(pageTitle);
        logger.debug("Page title: {}", title);
        return title;
    }

    public int getCartItemCount() {
        try {
            String badgeText = getText(cartBadge);
            int count = Integer.parseInt(badgeText);
            logger.info("Cart item count: {}", count);
            return count;
        } catch (Exception e) {
            logger.info("Cart is empty (no badge displayed)");
            return 0;
        }
    }

    public String getProductPrice(String productName) {
        String price = getText(productPrice(productName));
        logger.info("Price of '{}': {}", productName, price);
        return price;
    }

    public int getProductCount() {
        int count = getElementCount(inventoryItems);
        logger.info("Total products displayed: {}", count);
        return count;
    }

    public boolean isAddToCartButtonDisplayed(String productName) {
        return isDisplayed(addToCartButton(productName));
    }

    public boolean isRemoveButtonDisplayed(String productName) {
        return isDisplayed(removeFromCartButton(productName));
    }
}
