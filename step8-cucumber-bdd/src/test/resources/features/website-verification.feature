Feature: Website Verification
  As a QA engineer
  I want to verify that developer tool websites are accessible and functional
  So that I can ensure reliable access to essential development resources

  Background:
    Given I have a web browser available
    And I can access the internet

  @smoke @critical
  Scenario Outline: Verify developer website accessibility and basic functionality
    Given I navigate to the "<website>" website
    When the page loads completely
    Then the page title should contain "<expected_title>"
    And all critical page elements should be visible
    And I should be able to take a screenshot

    Examples:
      | website           | expected_title |
      | JUnit 5           | JUnit 5        |
      | Maven             | Maven          |
      | GitHub            | GitHub         |
      | Selenium WebDriver| Selenium       |

  @integration
  Scenario: Verify all websites in sequence
    Given I have the list of developer websites to test
    When I test each website for basic functionality
    Then all websites should be accessible
    And all page titles should match expectations
    And screenshots should be captured for evidence