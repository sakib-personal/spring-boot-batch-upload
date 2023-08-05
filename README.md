# Spring Boot Batch Upload

# Local Environment Setup [Manually]

## Prerequisites
1. Java 17
2. Maven
3. PostgreSql

## Setup Steps:
1. **Clone the application**

   ```bash
    git clone https://github.com/sakib-personal/spring-boot-batch-upload.git
   ```

2. **Move to the root directory the project**
   ```bash
    cd spring-boot-batch-upload
   ```

3. **Configure Environment Variables & Others**
    + Change the datasource variables with your environment in the application.yml.
    + Change the root directory path of temporary file's location in the application.yml.

4. **Build the application using maven**

   ```bash
    mvn clean install
   ```

5. **Run Application**
   <br/>**Using Terminal**
    + Now run the application.
       ```bash
        ./mvnw spring-boot:run
       ```
   **Using IntelIJ IDE**
    + Please go to the edit Run/Debug configuration settings.
    + Press the "+" icon to add new application configuration.
    + Select the Application from the left menu.
    + Set a name for the application at the right top side.
    + Select the Amazon Corretto JDK 17 and main application class.
    + Now press Shift & F10 button at a time.

6. **APIs**
   <br/>**CSV file import API**
    + Use Post method.
    + Add multipart file request with the request parameter "file".
    + Here is the API URL for local environment.
       ```bash
        localhost:8080/api/v1/import-csv-file/customer
       ```
   **Get customer list**
    + Use Get method.
    + Here is the API URL for local environment with request parameters.
       ```bash
        localhost:8080/api/v1/customers?page=1&pageSize=25
       ```