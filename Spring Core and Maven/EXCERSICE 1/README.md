# Library Management System - Spring Framework Exercise

## Project Overview
This is a basic Spring Framework application demonstrating core concepts including:
- Maven project configuration
- Spring dependency injection
- XML-based application context configuration
- Service and Repository pattern implementation

## Project Structure
```
LibraryManagement/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/library/
        │       ├── App.java                    (Main Application Class)
        │       ├── service/
        │       │   └── BookService.java        (Service Layer)
        │       └── repository/
        │           └── BookRepository.java     (Data Access Layer)
        └── resources/
            └── applicationContext.xml          (Spring Configuration)
```

## Files Description

### 1. **pom.xml**
Maven configuration file with the following dependencies:
- Spring Core (v5.3.20)
- Spring Context (v5.3.20)
- Spring Beans (v5.3.20)
- JUnit 4.13.2 (for testing)

### 2. **applicationContext.xml**
Spring configuration file that defines:
- **bookRepository bean**: Instance of `BookRepository` with `repositoryName` property set to "Central Library Repository"
- **bookService bean**: Instance of `BookService` with dependency injection of `bookRepository` bean

### 3. **BookRepository.java**
Data access layer class providing:
- `addBook(String bookName)` - Add a book to repository
- `getAllBooks()` - Retrieve all books
- `getBookCount()` - Get total number of books
- `searchBook(String bookName)` - Search for a specific book
- `displayInfo()` - Display repository information
- Property: `repositoryName` (injected via setter)

### 4. **BookService.java**
Business logic layer class providing:
- `addBook(String bookName)` - Add a book with validation
- `getAllBooks()` - Get all books from repository
- `getTotalBooks()` - Get total count of books
- `searchBook(String bookName)` - Search for a book
- `displayServiceInfo()` - Display detailed service information
- Dependency: `BookRepository` (injected via setter)

### 5. **App.java**
Main application class that:
- Loads Spring ApplicationContext from `applicationContext.xml`
- Retrieves `BookService` bean from the Spring container
- Tests the service by adding books, displaying information, and performing searches

## How to Run

### Prerequisites
- Java 11 or higher
- Maven 3.6.0 or higher
- Spring Framework 5.3.20

### Step 1: Compile the Project
```bash
cd LibraryManagement
mvn clean compile
```

### Step 2: Run the Application
```bash
mvn exec:java -Dexec.mainClass="com.library.App"
```

Or compile and run directly:
```bash
mvn clean compile exec:java -Dexec.mainClass="com.library.App"
```

### Expected Output
```
========================================
Library Management System - Spring Demo
========================================

Loading Spring ApplicationContext...
Spring context loaded successfully!

Testing BookService and BookRepository beans:

Book added: Java Programming
Book added: Spring in Action
Book added: Effective Java
Book added: Design Patterns

=== Book Service Information ===
Repository: Central Library Repository
Total Books: 4
Available Books:
  - Java Programming
  - Spring in Action
  - Effective Java
  - Design Patterns
================================

Searching for 'Spring in Action': true
Searching for 'Python Basics': false

=== All Available Books ===
  ✓ Java Programming
  ✓ Spring in Action
  ✓ Effective Java
  ✓ Design Patterns
Total books: 4

========================================
Application Test Completed Successfully!
========================================
```

## Key Spring Concepts Demonstrated

### 1. **Dependency Injection**
- `BookService` depends on `BookRepository`
- The dependency is injected through property setter in XML configuration
- Spring manages the lifecycle and initialization of beans

### 2. **ApplicationContext**
- Uses `ClassPathXmlApplicationContext` to load XML configuration from classpath
- Retrieves beans from the container using `getBean()` method

### 3. **Bean Definition**
- Beans are defined in XML with unique `id` attributes
- Constructor and properties are configured in XML

### 4. **Loose Coupling**
- Service layer doesn't create Repository directly
- Repository can be replaced with another implementation without changing Service code

## Building a JAR File

To package the application as a JAR:
```bash
mvn clean package
```

The JAR file will be created in the `target/` directory.

## Notes
- The project uses Java 11 as target version
- Spring handles object creation and dependency management
- XML configuration provides centralized bean configuration
- The Repository pattern separates data access logic from business logic

## Exercise Completion Checklist
✓ Maven project created with name "LibraryManagement"
✓ Spring Core dependencies added to pom.xml
✓ applicationContext.xml created with bean definitions
✓ BookRepository class created in com.library.repository package
✓ BookService class created in com.library.service package
✓ Main application class created to load Spring context
✓ Dependency injection demonstrated through XML configuration
✓ Application can be executed to test bean configuration
