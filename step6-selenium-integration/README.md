# Step 6: Selenium Integration

## Overview
This step replaces the Desktop API approach with Selenium WebDriver, providing better control, cross-platform compatibility, and automated testing capabilities.

## Key Features

### Selenium WebDriver Integration
- **Automatic ChromeDriver Setup**: Uses WebDriverManager for seamless driver management
- **Chrome Options Configuration**: Optimized browser settings for automation
- **Cross-Platform Compatibility**: Works on Windows, Mac, and Linux

### Enhanced Testing Capabilities
- **Page Title Verification**: Compares actual vs expected page titles
- **Screenshot Capture**: Automatically saves screenshots of each opened page
- **Structured Test Data**: Enhanced JSON format with expectedTitle fields

### Professional Architecture
- **LinkData Class**: Structured data container for link information
- **JsonLinkDataReader**: Enhanced JSON parser supporting verification data
- **Dual Testing Approach**: Basic links + enhanced verification tests

## New Classes

### SeleniumLinkOpener
- `openLink(String url)` - Basic link opening
- `openLinkWithVerification(String url, String expectedTitle)` - Enhanced verification
- `takeScreenshot()` - Automated screenshot capture
- `cleanup()` - Proper resource management

### LinkData
- Data container for name, URL, and expectedTitle
- Supports optional title verification

### JsonLinkDataReader
- Enhanced JSON parsing for test data
- Extracts verification information from JSON structure

## Dependencies
- Selenium WebDriver 4.x
- WebDriverManager (automatic driver management)
- Chrome Browser (for ChromeDriver)

## Usage
```bash
mvn exec:java -pl step6-selenium-integration
```

## Output
- **Console**: Real-time execution status
- **Activity Log**: `java_project/data/activity.log`
- **Screenshots**: `java_project/screenshots/`

## Testing Data
Enhanced JSON format with verification fields:
```json
[
  {
    "name": "JUnit 5",
    "url": "https://junit.org",
    "expectedTitle": "JUnit 5"
  }
]
```

## Benefits Over Desktop API
1. **Better Control**: Fine-grained browser automation
2. **Cross-Platform**: No OS-specific limitations
3. **Testing Ready**: Built-in verification capabilities
4. **Screenshot Evidence**: Visual testing documentation
5. **Professional Automation**: Industry-standard web testing approach

This step demonstrates the transition from simple file processing to professional web automation testing framework.