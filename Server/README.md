## Migraine Server Web Application  
This application provides the server-side functionality for the migraine research mobile application.  
  
The server includes administration web pages, and a REST API that the application uses to authenticate and authorize users, and store and retrieve data.  
  
### Rest API testing  
The following examples show how to test the various REST API calls outside of the mobile application.  

#### Initialize the database  
     curl -k https://app.clinvest.com/migraine-server/admin
  
#### Login    
    curl -k --header "Content-Type: application/json" --data '{ username: "root", password: "password"}' https://app.clinvest.com/migraine-server/login  
 
#### User Registration
     curl -k --header "Content-Type: application/json" --data '{"firstName":"John", "lastName":"Doe", "email":"john.doe@example.com", "password":"p@$$w0rd", "birthDate":0}' https://app.clinvest.com/migraine-server/register

#### Confirm Registration  
     curl -k https://app.clinvest.com/migraine-server/register?id=55d8cbf5-9d65-453d-8d92-662526adb3e1