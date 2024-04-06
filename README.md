# Basket Splitter 🛍️

Basket Splitter is a Java library designed to optimize the delivery method for a basket of products based on predefined delivery options. It uses configuration data to determine the best delivery groups for given products.

## Key Features

- **Delivery Grouping**: Splits items in a shopping basket into delivery groups based on predefined delivery methods, optimizing for the smallest number of deliveries.
- **Configurable Delivery Methods**: Uses a JSON configuration file to define possible delivery methods for all offered products.
- **Optimization**: Aims to create the largest possible delivery groups to reduce the likelihood of items being left in the basket.

## Getting Started 🚀

### Prerequisites 🛠️

- Java JDK 17 or later ☕

### Adding BasketSplitter to Your Project

To use BasketSplitter in your project, follow these steps to add the library:

1. Download the BasketSplitter JAR file from the `distributions/ProjectWithFatJarAndSources-1.0` section on GitHub.
2. Add the JAR file to your project's build path.


**For Gradle projects:**

   First, place the JAR in your project's `libs` folder. Then, add the following to your `build.gradle` file:
   ```sh
   dependencies {
       implementation fileTree(dir: 'libs', include: ['*.jar'])
   } 
   ```


**For Maven projects:**

Install the JAR into your local Maven repository by executing:

```sh
mvn install:install-file -Dfile=path/to/the/jar.jar -DgroupId=com.ocado -DartifactId=basket-splitter -Dversion=1.0 -Dpackaging=jar
```

Then, add the following dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>com.ocado</groupId>
    <artifactId>basket-splitter</artifactId>
    <version>1.0</version>
</dependency>
```
## Usage 📝

1. **Prepare Your Input**

   Create a list of product names you want to split into delivery groups.


2. **Create the Basket Splitter**

   Use the `BasketSplitter` class in your Java code - initialize it, providing the path to your configuration file in the constructor. 
   Then call the public `split` method, providing the list of product names (`List<String> products`). 
   The `split` method returns the `Map<String, List<String>>` with the optimized delivery groups and the corresponding lists of products.

   ```java
     String absolutePathToConfigFile = "path/to/config.json";

     BasketSplitter basketSplitter = new BasketSplitter(absolutePathToConfigFile);

     // Example list of product names to split into delivery groups
     List<String> productNames = Arrays.asList("Cookies Oatmeal Raisin", "Cheese Cloth", "English Muffin");

     // Invoke the split method and capture the results
     Map<String, List<String>> deliveryGroups = basketSplitter.split(productNames);

     // Print the delivery groups to the console
     System.out.println("Delivery Groups:");
     deliveryGroups.forEach((deliveryMethod, products) -> System.out.println(deliveryMethod + ": " + products));
     ```
    Json configuration file example:
   ```json
   {
   "Cookies Oatmeal Raisin": ["Pick-up point", "Parcel locker"],
   "Cheese Cloth": ["Courier", "Parcel locker", "Same day delivery", "Next day shipping", "Pick-up point"],
   "English Muffin": ["Mailbox delivery", "Courier", "In-store pick-up", "Parcel locker", "Next day shipping", "Express Collection"]
    }
   ```
3. **Ready to use 🚀**
