Feature: SauceDemo E-commerce Purchase Flow
  As a customer
  I want to browse products and complete purchases
  So that I can buy items from SauceDemo

  Background:
    Given I am on the SauceDemo login page
    And Log4j logging is initialized

  @e2e @purchase @smoke
  Scenario: Complete purchase flow from start to finish
    Given I login as "standard_user"
    When I add "Sauce Labs Backpack" to cart
    And I add "Sauce Labs Bike Light" to cart
    And I open the cart
    Then cart should contain 2 items
    When I proceed to checkout
    And I fill checkout information:
      | firstName | lastName | zipCode |
      | John      | Doe      | 12345   |
    And I continue to checkout step 2
    And I verify order total is correct
    And I click finish
    Then I should see "Thank you for your order!" message
    And ExtentReport should capture success screenshot
    And purchase completion should be logged at INFO level

  @product @cart
  Scenario: Add multiple products to cart and verify count
    Given I login as "standard_user"
    When I add "Sauce Labs Backpack" to cart
    And I add "Sauce Labs Bolt T-Shirt" to cart
    And I add "Sauce Labs Onesie" to cart
    Then cart badge should show 3 items
    When I open the cart
    Then cart should contain 3 items
    And "Sauce Labs Backpack" should be in cart
    And "Sauce Labs Bolt T-Shirt" should be in cart
    And "Sauce Labs Onesie" should be in cart

  @product @remove
  Scenario: Remove product from cart
    Given I login as "standard_user"
    When I add "Sauce Labs Backpack" to cart
    And I add "Sauce Labs Bike Light" to cart
    And I open the cart
    Then cart should contain 2 items
    When I remove "Sauce Labs Backpack" from cart
    Then cart should contain 1 item
    And "Sauce Labs Bike Light" should be in cart
    And "Sauce Labs Backpack" should not be in cart

  @negative @login
  Scenario: Login with locked out user
    Given I am on the SauceDemo login page
    When I attempt to login with username "locked_out_user" and password "secret_sauce"
    Then I should see error message "Epic sadface: Sorry, this user has been locked out."
    And error should be logged at WARN level
    And ExtentReport should capture error screenshot

  @negative @checkout
  Scenario: Checkout without filling required information
    Given I login as "standard_user"
    When I add "Sauce Labs Backpack" to cart
    And I open the cart
    And I proceed to checkout
    And I click continue without filling information
    Then I should see error "Error: First Name is required"
    And error should be logged at WARN level

  @sorting @products
  Scenario: Sort products by price (low to high)
    Given I login as "standard_user"
    When I sort products by "lohi"
    Then products should be sorted by price ascending
    And sorting action should be logged at INFO level

  @datadriven @multipleusers
  Scenario Outline: Login with different user types
    Given I am on the SauceDemo login page
    When I login with username "<username>" and password "<password>"
    Then I should see "<expectedPage>"
    And login attempt should be logged with username "<username>"

    Examples:
      | username                | password     | expectedPage |
      | standard_user           | secret_sauce | Products     |
      | performance_glitch_user | secret_sauce | Products     |
      | problem_user            | secret_sauce | Products     |
