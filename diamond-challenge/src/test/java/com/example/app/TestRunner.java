package com.example.app;

import org.junit.platform.suite.api.*;

/**
 * JUnit 5 Test Runner for Cucumber BDD tests.
 * Integrates Cucumber with JUnit Platform for test execution.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.plugin",
    value = "pretty, html:reports/cucumber-report.html, json:reports/cucumber.json, "
          + "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:")
@ConfigurationParameter(key = "cucumber.glue", value = "com.example.app.stepdefinitions")
@ConfigurationParameter(key = "cucumber.filter.tags", value = "not @wip")
@ConfigurationParameter(key = "cucumber.execution.parallel.enabled", value = "false")
public class TestRunner {
    // This class is used as a test runner for Cucumber BDD tests
    // All configuration is done via annotations
}
