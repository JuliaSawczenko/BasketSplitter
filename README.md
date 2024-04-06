# BasketSplitter Library

The BasketSplitter library is designed to provide an efficient way to split shopping baskets based on various delivery methods, optimizing for factors such as cost, time, and resource availability. This Java library allows developers to easily integrate delivery optimization into their applications.

## Features

- **Configurable Delivery Methods**: Leverage a JSON configuration to define and manage different delivery options within your application.
- **Optimization Algorithm**: Incorporates an algorithm to find the optimal delivery method for each product in a basket, ensuring the best possible outcome.
- **Custom Exceptions**: Comes with custom exceptions for robust error handling related to configuration and product specifications.

## Getting Started

### Prerequisites

- Java JDK 11 or higher

### Adding BasketSplitter to Your Project

To use BasketSplitter in your project, follow these steps to add the library:

1. Download the BasketSplitter JAR file from the Releases section on GitHub.
2. Add the JAR file to your project's build path. Here are instructions for two common scenarios:

   **For Gradle projects:**
   
   First, place the JAR in your project's `libs` folder. Then, add the following to your `build.gradle` file:
   ```
   dependencies {
       implementation fileTree(dir: 'libs', include: ['*.jar'])
   }
   ```
   
   **For Maven projects:**
   
   Install the JAR into your local Maven repository by executing:
   ```
   mvn install:install-file -Dfile=path/to/your/basket-splitter.jar -DgroupId=com.ocado -DartifactId=basket-splitter -Dversion=1.0 -Dpackaging=jar
   ```
   Then, add the following dependency to your `pom.xml`:
   ```xml
   <dependency>
       <groupId>com.ocado</groupId>
       <artifactId>basket-splitter</artifactId>
       <version>1.0</version>
   </dependency>
   ```

### Configuration

After adding the library to your project, modify the delivery method configuration to suit your needs. Ensure you load the appropriate JSON configuration file at runtime.

## Usage

To use the BasketSplitter library in your project, instantiate the `BasketSplitter` class and call its methods with your basket and configuration data.

## Running Tests

To run tests in the BasketSplitter project, execute the following command in the project directory:
```
./gradlew test
```

## Contributing

Contributions are welcome! Please feel free to submit a pull request.

## License

// TODO: Specify the license. If the project is open-sourced, consider using MIT or Apache 2.0.

## Authors

- **[Julia Sawczenko](https://github.com/JuliaSawczenko)** - Initial work

## Acknowledgments

// TODO: Mention any inspirations, code snippets, etc.