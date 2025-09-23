package com.example.app;

import org.junit.platform.suite.api.*;

/**
 * Enhanced JUnit 5 Test Runner for Cucumber BDD scenarios with ExtentReports
 * Executes all feature files and generates beautiful HTML reports
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty,html:target/cucumber-reports,tech.grasshopper.ExtentCucumberAdapter:")
@ConfigurationParameter(key = "cucumber.glue", value = "com.example.app")
@ConfigurationParameter(key = "cucumber.features", value = "src/test/resources/features")
public class CucumberTestRunner {
    // This class serves as the entry point for running Cucumber tests with ExtentReports
    // All configuration is done via annotations above
    // ExtentReports will be automatically generated alongside Cucumber reports
}