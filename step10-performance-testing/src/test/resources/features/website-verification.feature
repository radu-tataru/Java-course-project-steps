Feature: Website Verification
  As a quality assurance engineer
  I want to verify that all configured websites are accessible and functional
  So that I can ensure a reliable user experience

  Background:
    Given I have access to the test environment
    And all required browser drivers are available

  @smoke @critical
  Scenario Outline: Verify website accessibility and basic functionality
    Given I navigate to the "<website>" website
    When the page loads completely
    Then the page title should contain "<expected_title>"
    And all critical page elements should be visible
    And the page should load within 3 seconds
    
    Examples:
      | website | expected_title |
      | GitHub  | GitHub         |
      | JUnit   | JUnit 5        |
      | Maven   | Maven          |

  @api @integration
  Scenario: Verify API endpoints are responding correctly
    Given I have valid API credentials
    When I call the health check endpoint
    Then the response status should be 200
    And the response time should be less than 500ms
    And the response should contain valid JSON

  @performance @load-testing
  Scenario: Website performance under load
    Given I configure a load test for 50 concurrent users
    When I run the load test for 5 minutes
    Then the average response time should be less than 2 seconds
    And the error rate should be less than 1%
    And the system should maintain 95th percentile response time under 3 seconds