package com.example.app.stepdefinitions;

import com.example.app.config.WebDriverManager;
import com.example.app.pages.CartPage;
import com.example.app.pages.ProductsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

/**
 * Step definitions for Shopping Cart scenarios.
 */
public class CartSteps {
    private static final Logger logger = LogManager.getLogger(CartSteps.class);
    private WebDriver driver;
    private ProductsPage productsPage;
    private CartPage cartPage;

    @When("I open the cart")
    public void i_open_the_cart() {
        driver = WebDriverManager.getDriver();
        if (productsPage == null) {
            productsPage = new ProductsPage(driver);
        }

        logger.info("Opening shopping cart");
        cartPage = productsPage.openCart();

        Assertions.assertTrue(cartPage.isCartPageDisplayed(), "Cart page should be displayed");
    }

    @When("I remove {string} from cart")
    public void i_remove_from_cart(String productName) {
        logger.info("Removing product from cart: {}", productName);
        cartPage.removeProductFromCart(productName);
    }

    @Then("cart should contain {int} items")
    public void cart_should_contain_items(int expectedCount) {
        int actualCount = cartPage.getCartItemCount();
        Assertions.assertEquals(expectedCount, actualCount,
                String.format("Expected cart to contain %d items, but got %d", expectedCount, actualCount));
        logger.info("Cart item count verified: {} items", actualCount);
    }

    @Then("cart should contain {int} item")
    public void cart_should_contain_item(int expectedCount) {
        cart_should_contain_items(expectedCount);
    }

    @Then("{string} should be in cart")
    public void should_be_in_cart(String productName) {
        boolean inCart = cartPage.isProductInCart(productName);
        Assertions.assertTrue(inCart, String.format("Product '%s' should be in cart", productName));
        logger.info("Verified product in cart: {}", productName);
    }

    @Then("{string} should not be in cart")
    public void should_not_be_in_cart(String productName) {
        boolean inCart = cartPage.isProductInCart(productName);
        Assertions.assertFalse(inCart, String.format("Product '%s' should NOT be in cart", productName));
        logger.info("Verified product NOT in cart: {}", productName);
    }
}
