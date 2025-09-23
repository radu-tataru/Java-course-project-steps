# Excel Test Data Structure

## File: website-test-data.xlsx

### Sheet Structure:
The Excel file should contain the following columns in the first sheet:

| Column A | Column B | Column C | Column D | Column E | Column F |
|----------|----------|----------|----------|----------|----------|
| TestName | Website | ExpectedTitle | ButtonText | Environment | Priority |

### Sample Data:
```
Row 1 (Header): TestName | Website | ExpectedTitle | ButtonText | Environment | Priority
Row 2: GitHub_Production_Test | GitHub | GitHub | Sign up | prod | high
Row 3: JUnit_Staging_Test | JUnit | JUnit 5 | Guide | staging | medium
Row 4: Maven_Dev_Test | Maven | Maven | Download | dev | high
Row 5: Selenium_Production_Test | Selenium | Selenium | Getting Started | prod | high
```

### Column Descriptions:
- **TestName**: Unique identifier for the test case
- **Website**: Website name (GitHub, JUnit, Maven, Selenium)
- **ExpectedTitle**: Expected page title for verification
- **ButtonText**: Expected button text to verify on the page
- **Environment**: Target environment (dev, staging, prod)
- **Priority**: Test priority level (high, medium, low)

### File Location:
Place the Excel file at: `java_project/data/website-test-data.xlsx`

### Validation:
The ExcelDataProvider class will validate:
1. Header row presence and correct column names
2. Required fields (TestName, Website) are not empty
3. Valid environment values (dev, staging, prod)
4. Valid priority values (high, medium, low)