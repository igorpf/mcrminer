# MCRMiner

## External ependencies

- [Lombok](https://projectlombok.org/) plugin for your IDE so it can recognize generated methods. You may also need to 
enable annotation processing on your IDE

### Building project 

` $ ./gradlew build`

### Creating new projections for CSV exports

- Create a simple POJO
- Remember to generate `boolean` getters with get prefix:

```java
   public class SimplePOJO {
    private boolean flag;
    public getFlag(){return this.flag;}
   }
```

Otherwise the CSV exporter won't recognize the field

- Use `@Builder` annotation if you need a simple way to build the object using the Builder pattern


### Running from command line

` $ ./gradlew bootRun`