# deloitte


Deloitte API
------------

1.  RAML PATH- 

      deloiteapi/deloitte-raml-api/src/main/resources/deloitte-api.raml
      
2. REST Controller PATH - /deloiteapi/deloitte-api- 

      impl/src/main/java/org/com/deloitte/raml/impl/controller/CustomerControllerImpl.java
        
High Level Design
-----------------

 This API has been designed for Internal and External Clients,

   Internal Clients can do below operations-
    
        1. Create Customer
        2. Update Customer
        3. Delete Customer
        4. get Customer List
        
   External API can access  
    
        1. Get Customer List
        
  
  To achieve the user authentication and authorisation, Spring OAUTH 2.0 has been leveraged.
  
       - Two Types of client ids are configured with corresponding access limit. Please refer,
            deloiteapi/deloitte-api-impl/src/main/java/org/com/deloitte/raml/impl/security/OAuth2Config.java
       
                1. deloitte.internal - Read and Write
                2. deloitte.external - Read 


   Use Cases
   ---------

   1. A consumer may periodically (every 5 minutes) consume the API to enable it (the consumer) to maintain a copy of the provider API's customers (the API represents the system of record)
           
      Step 1 : The external customers will be given client id as 
   Use Cases
   ---------

   1. A consumer may periodically (every 5 minutes) consume the API to enable it (the consumer) to maintain a copy of the provider API's customers (the API represents the system of record)


      Step 1 : The external customers will be given client id as "deloitte.external".
      Step 2 : External customers should get the access token prior to get the customer list

               Sample Request for access Token:
                    curl -X POST --user 'deloitte.external:secret' -d         'grant_type=password&username=mobileapp@example.com&password=password' localhost:8080/oauth/token

               Sample Response:
                    {"access_token":"a59025bc-cf24-400c-8db9-a9e3b5dfec5f","token_type":"bearer","refresh_token":"94be160d-1ed1-4d02-9054-b834af64e1cc","expires_in":3599,"scope":"read"}
      
      Step 3: Get the Customer List
      
               Sample Get Customer Request:
                  curl -i -H "Accept: application/json" -H "Authorization: Bearer $TOKEN" -X GET http://localhost:8080/api/customers/
                    
               Sample Get Customer Response:
               
               HTTP/1.1 200 
            X-Content-Type-Options: nosniff
            X-XSS-Protection: 1; mode=block
            Cache-Control: no-cache, no-store, max-age=0, must-revalidate
            Pragma: no-cache
            Expires: 0
            X-Application-Context: application
            Content-Type: application/json;charset=UTF-8
            Transfer-Encoding: chunked
            Date: Sun, 29 Oct 2017 02:19:54 GMT

            [{"customerId":50,"firstName":"Mohan","lastName":"G","address":{"unitNumber":10,"streetName":"AMOS_STREET","suburb":"Parramatta","state":"NSW","postalCode":2145}},{"customerId":51,"firstName":"Mohan","lastName":"G","address":{"unitNumber":10,"streetName":"AMOS_STREET","suburb":"Parramatta","state":"NSW","postalCode":2145}},{"customerId":52,"firstName":"Mohan","lastName":"G","address":{"unitNumber":10,"streetName":"AMOS_STREET","suburb":"Parramatta","state":"NSW","postalCode":2145}},{"customerId":53,"firstName":"Mohan","lastName":"G","address":{"unitNumber":10,"streetName":"AMOS_STREET","suburb":"Parramatta","state":"NSW","postalCode":2145}}]Mohanasundarams-MacBook-Pro:.ssh mohanasundaramgovindarajan$ 

      High Lights
      -----------
            1. Since the external customers are accessing the API perodically, I have configured the expiration time limit to access token. Every 5 


