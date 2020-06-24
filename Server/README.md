## Migraine Server Web Application  
This application provides the server-side functionality for the migraine research mobile application.  
  
The server includes administration web pages, and a REST API that the application uses to authenticate and authorize users, and store and retrieve data.  
  
### Rest API testing  
The following examples show how to test the various REST API calls outside of the mobile application.  

#### Initialize the database
```  
curl -k https://app.clinvest.com/migraine-server/admin
```
  
#### Login
```    
curl -k --header "Content-Type: application/json" --data '{ username: "root", password: "password"}' https://app.clinvest.com/migraine-server/login  
```
 
#### User Registration
```
curl -k --header "Content-Type: application/json" --data '{"firstName":"John", "lastName":"Doe", "email":"john.doe@example.com", "password":"p@$$w0rd", "birthDate":0}' https://app.clinvest.com/migraine-server/register
```

#### Confirm Registration
```  
curl -k https://app.clinvest.com/migraine-server/register?id=55d8cbf5-9d65-453d-8d92-662526adb3e1
```

#### Save FAMS entry
```
curl -k --header "Content-Type: application/json" --data '{"userId":"1f65c9f4-b610-4e49-9636-71366cf1ea15","Q1":1,"Q2":1,"Q3":1,"Q4":1,"Q5":1,"Q6":1,"Q7":1,"Q8":1,"Q9":1,"Q10":1,"Q11":1,"Q12":1,"Q13":1,"Q14":1,"Q15":1,"Q16":1,"Q17":1,"Q18":1,"Q19":1,"Q20":1,"Q21":1,"Q22":1,"Q23":1,"Q24":1,"Q25":1,"Q26":1,"Q27":1}' https://app.clinvest.com/migraine-server/fams?auth=
```
