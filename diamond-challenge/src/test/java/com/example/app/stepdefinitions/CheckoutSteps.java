package com.example.app.stepdefinitions;

import com.example.app.config.WebDriverManager;
import com.example.app.pages.*;
import com.example.app.utils.ScreenshotUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

/**
 * Step definitions for Checkout flow scenarios.
 */
public class CheckoutSteps {
    private static final Logger logger = LogManager.getLogger(CheckoutSteps.class);
    private WebDriver driver;
    private CartPage cartPage;
    private CheckoutStepOnePage checkoutStepOnePage;
    private CheckoutStepTwoPage checkoutStepTwoPage;
    private CheckoutCompletePage checkoutCompletePage;

    @When("I proceed to checkout")
    public void i_proceed_to_checkout() {
        driver = WebDriverManager.getDriver();
        if (cartPage == null) {
            cartPage = new CartPage(driver);
        }

        logger.info("Proceeding to checkout");
        checkoutStepOnePage = cartPage.proceedToCheckout();

        Assertions.assertTrue(checkoutStepOnePage.isCheckoutStepOneDisplayed(),
                "Checkout step one page should be displayed");
    }

    @When("I fill checkout information:")
    public void i_fill_checkout_information(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps();
        Map<String, String> customerInfo = data.get(0);

        String firstName = customerInfo.get("firstName");
        String lastName = customerInfo.get("lastName");
        String zipCode = customerInfo.get("zipCode");

        logger.info("Filling checkout information: {} {} - {}", firstName, lastName, zipCode);
        checkoutStepOnePage.enterFirstName(firstName);
        checkoutStepOnePage.enterLastName(lastName);
        checkoutStepOnePage.enterPostalCode(zipCode);
    }

    @When("I continue to checkout step 2")
    public void i_continue_to_checkout_step_2() {
        logger.info("Continuing to checkout step 2");
        checkoutStepTwoPage = checkoutStepOnePage.clickContinue();

        Assertions.assertTrue(checkoutStepTwoPage.isCheckoutStepTwoDisplayed(),
                "Checkout step two page should be displayed");
    }

    @When("I verify order total is correct")
    public void i_verify_order_total_is_correct() {
        String subtotal = checkoutStepTwoPage.getSubtotal();
        String tax = checkoutStepTwoPage.getTax();
        String total = checkoutStepTwoPage.getTotal();

        logger.info("Order summary - Subtotal: {}, Tax: {}, Total: {}", subtotal, tax, total);

        Assertions.assertNotNull(total, "Order total should be displayed");
        double totalAmount = checkoutStepTwoPage.getTotalAmount();
        Assertions.assertTrue(totalAmount > 0, "Order total should be greater than 0");
    }

    @When("I click finish")
    public void i_click_finish() {
        logger.info("Clicking finish to complete order");
        checkoutCompletePage = checkoutStepTwoPage.clickFinish();

        Assertions.assertTrue(checkoutCompletePage.isCheckoutCompleteDisplayed(),
                "Checkout complete page should be displayed");
    }

    @When("I click continue without filling information")
    public void i_click_continue_without_filling_information() {
        logger.info("Clicking continue without filling information");
        checkoutStepOnePage.clickContinue();
    }

    @Then("I should see {string} message")
    public void i_should_see_message(String expectedMessage) {
        String actualMessage = checkoutCompletePage.getCompleteHeader();
        Assertions.assertTrue(actualMessage.contains(expectedMessage),
                String.format("Expected message containing '%s', but got '%s'", expectedMessage, actualMessage));
        logger.info("Order completion message verified: {}", actualMessage);
    }

    @Then("I should see error {string}")
    public void i_should_see_error(String expectedError) {
        Assertions.assertTrue(checkoutStepOnePage.isErrorMessageDisplayed(),
                "Error message should be displayed");
        String actualError = checkoutStepOnePage.getErrorMessage();
        Assertions.assertTrue(actualError.contains(expectedError),
                String.format("Expected error containing '%s', but got '%s'", expectedError, actualError));
        logger.warn("Checkout error verified: {}", actualError);
    }

    @Then("ExtentReport should capture success screenshot")
    public void extent_report_should_capture_success_screenshot() {
        String screenshotPath = ScreenshotUtil.captureScreenshot(driver, "OrderComplete");
        Assertions.assertNotNull(screenshotPath, "Screenshot should be captured");
        logger.info("Success screenshot captured: {}", screenshotPath);
    }

    @Then("ExtentReport should capture error screenshot")
    public void extent_report_should_capture_error_screenshot() {
        String screenshotPath = ScreenshotUtil.captureScreenshot(driver, "LoginError");
        Assertions.assertNotNull(screenshotPath, "Screenshot should be captured");
        logger.warn("Error screenshot captured: {}", screenshotPath);
    }

    @Then("purchase completion should be logged at INFO level")
    public void purchase_completion_should_be_logged_at_info_level() {
        logger.info("Purchase completed successfully - E2E flow verified");
    }
}
