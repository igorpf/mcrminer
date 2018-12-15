# MCRMiner

## External Dependencies

- [Lombok](https://projectlombok.org/) plugin for your IDE so it can recognize generated methods. You may also need to 
enable annotation processing on your IDE
- All other dependencies are managed by Gradle

### Building project 

` $ ./gradlew build`

### Running from command line

` $ ./gradlew bootRun`

### Creating new perspectives for CSV exports

- Create a simple POJO
- Remember to generate `boolean` getters with get prefix, otherwise the CSV exporter won't recognize the field

```java
   public class SimplePOJO {
    private boolean flag;
    public getFlag(){return this.flag;}
   }
```
- Implement `PerspectiveType` interface and annotate the implementation with `@Service` so that Spring can autowire it
- Create the respective `PerspectiveService` and all the needed `PerspectiveCreationStrategy`

### Creating a new mining instance
- The framework supports multiple instances on the same application, so the implementation can either be done by creating
a new application or using an existing application
- In the case of a new application
    - Create a new project under this folder (like gerrit-instance) for a new application
    - Add the exact name used for the folder on `settings.gradle`: `include 'folder-name'`
    - Add `compile project(':mcrminer-framework')` on the dependencies declared the application `build.gradle` file
