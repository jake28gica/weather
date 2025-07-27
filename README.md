# Weather Application

This is a simple Springboot project that returns data about the weather from a particular city.

---

## ✅ Prerequisites
Before you build or run this project, make sure you have:

- **Java 21 or later** installed.  
  Check:
  ```bash
  java -version

- maven 3.8+ if you want to build an executable jar  
  Check:
    ```bash
  mvn -version


## ✅ Build instructions  
In order to build the jar file, we need to use maven. Run the below command:
    ```bash
    ./mvnw clean install    # on Mac/Linux
    mvnw.cmd clean install  # on Windows

It should create a jar file under the target directory. Check if target/weather-0.0.1-SNAPSHOT.jar exists.

## ✅ Run instructions
To start the weather service, Run the below command from the root directory:
    ```bash
    ./mvnw spring-boot:run      # Linux / Mac
    mvnw.cmd spring-boot:run    # Windows


It should show logs that contains
    ```bash
    Completed initialization in 0 ms

If youre getting 'Permission Denied' in an error message, running this command will make it executable:
    ```bash
    chmod +x mvnw

This means the service has started successfully and it will run on default port: 10000.


## ✅ Running/Testing instructions

Go to your browser and put the ff in the address bar:

asdasd
    ```bash
    http://localhost:10000/getWeather/{city}

Replace city with an existing city. Here is a sample:

    ```bash
    http://localhost:10000/getWeather/sydney








    

    


