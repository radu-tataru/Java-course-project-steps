package com.example.app;

import org.junit.platform.suite.api.*;

/**
 * JUnit 5 Test Runner for Cucumber BDD scenarios
 * Executes all feature files and generates readable test reports
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty,html:target/cucumber-reports")
@ConfigurationParameter(key = "cucumber.glue", value = "com.example.app")
@ConfigurationParameter(key = "cucumber.features", value = "src/test/resources/features")
public class CucumberTestRunner {
    // This class serves as the entry point for running Cucumber tests
    // All configuration is done via annotations above
}