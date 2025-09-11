# QA Java Course Project - Standalone Steps

A progressive Java learning project designed for QA Engineers transitioning to QA Automation. Each step is a **completely independent application** that can be built and run separately.

## Project Structure

```
qa-java-course-project-v2/
├── step1-file-reading/          # Standalone app for basic file I/O
├── step2-desktop-api/           # Standalone app for browser integration
├── step3-oop-architecture/      # Standalone app demonstrating OOP principles
└── step4-design-patterns/       # Standalone app with advanced patterns
```

## How to Build and Run Each Step

Each step is an independent Maven module that can be built and executed separately:

### Build All Steps
```bash
mvn clean compile
```

### Run Individual Steps

**Step 1: File Reading Operations**
```bash
mvn exec:java -pl step1-file-reading
```

**Step 2: Desktop API Integration**
```bash
mvn exec:java -pl step2-desktop-api
```

**Step 3: OOP Architecture**
```bash
mvn exec:java -pl step3-oop-architecture
```

**Step 4: Design Patterns**
```bash
mvn exec:java -pl step4-design-patterns
```

### Build and Run Specific Step
```bash
# Build only Step 3
mvn clean compile -pl step3-oop-architecture

# Run Step 3
mvn exec:java -pl step3-oop-architecture
```

## Learning Progression

### Step 1: Basic File I/O Operations (Beginner)
- Reading TXT, CSV, and JSON files
- Basic exception handling
- Understanding file paths and BufferedReader
- JSON parsing with org.json library

### Step 2: System Integration (Beginner)
- Using Java Desktop API
- Opening URLs in default browser
- Platform compatibility checking
- Combining file operations with system calls

### Step 3: Object-Oriented Programming (Intermediate)
- Interface design and implementation
- Abstract classes and inheritance
- Factory design pattern
- Polymorphism and encapsulation
- Separation of concerns

### Step 4: Advanced Design Patterns (Intermediate)
- Singleton pattern (enum and traditional)
- Enhanced Factory with inner classes
- Configuration management
- Logging frameworks
- Production-ready error handling

## Target Audience

QA Engineers learning Java fundamentals for test automation careers.

## Course Philosophy

Each step builds conceptually on the previous one but remains **completely independent**:
- Students can focus on one concept at a time
- Each step can be analyzed in isolation
- No overwhelming complexity from unrelated features
- Perfect for incremental learning and review

## Next Steps

After completing all steps, students will be ready to tackle enterprise automation frameworks like:
- Selenium WebDriver
- Cucumber BDD
- RestAssured API Testing
- JUnit/TestNG
- Maven/Gradle build systems