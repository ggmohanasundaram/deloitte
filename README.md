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
                
      
