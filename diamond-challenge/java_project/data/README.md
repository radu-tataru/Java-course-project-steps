# Diamond Challenge Test Data

This directory contains test data files used by the Diamond Challenge framework.

## Files

### test-users.json
Contains user credentials and test product information for SauceDemo testing:
- **users**: Different user types (standard, locked_out, problem, performance_glitch)
- **testProducts**: Product catalog with names and prices
- **checkoutData**: Customer information for checkout flow testing

## Usage

The test data can be loaded and used in Cucumber step definitions or test classes to drive data-driven testing scenarios.

### Example Usage

```java
// Read test data from JSON file
String jsonContent = Files.readString(Paths.get("java_project/data/test-users.json"));
JSONObject testData = new JSONObject(jsonContent);

// Get user credentials
JSONArray users = testData.getJSONArray("users");
JSONObject standardUser = users.getJSONObject(0);
String username = standardUser.getString("username");
String password = standardUser.getString("password");

// Use in tests
loginPage.login(username, password);
```

## Data-Driven Testing

This data supports:
- Multiple user type testing (standard, locked, problem users)
- Product selection and cart scenarios
- Checkout flow with various customer information
- Negative testing scenarios

## Extending Test Data

To add new test scenarios:
1. Add new entries to the appropriate array in `test-users.json`
2. Update step definitions to consume the new data
3. Create new Cucumber scenarios using the data
