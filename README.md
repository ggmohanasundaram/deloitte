# deloitte


Deloitte API
------------

  1.  RAML PATH-

      deloiteapi/deloitte-raml-api/src/main/resources/deloitte-api.raml

  2. REST Controller PATH - /deloiteapi/deloitte-api-

      impl/src/main/java/org/com/deloitte/raml/impl/controller/CustomerControllerImpl.java

High Level Design
-----------------

  1. This API has been designed for Internal and External Clients,

    Internal Clients can do below operations-

        1. Create Customer - HTTP POST
        2. Update Customer - HTTP PUT
        3. get Customer List - HTTP GET
        4. Get Cusotmer by ID - HTTP GET/{id}

    External API can access

        1. Get Customer List

   2. This Deloitte API has been implemented with,

        1. Spring Boot
        2. Maven
        3. Java 8.


   3. To achieve the user authentication and authorisation, Spring OAUTH 2.0 has been leveraged.
      Two Types of client ids are configured with corresponding access limit.
            Please refer,
            deloiteapi/deloitte-api-impl/src/main/java/org/com/deloitte/raml/impl/security/OAuth2Config.java

                1. deloitte.internal - Read and Write
                2. deloitte.external - Read

   4. How To Run:

          mvn clean install
          java -jar deloitte-api-impl/target/deloitte-api-impl.jar



Use Cases
---------

   1. A consumer may periodically (every 5 minutes) consume the API to enable it (the consumer) to maintain a copy of the provider API's customers (the API represents the system of record).

      Step 1 : The external customers will be given client id as "deloitte.external".

      Step 2 : External customers should get the access token prior to get the customer list.

               Sample Request for access Token:
                    curl -X POST --user 'deloitte.external:secret' -d         'grant_type=password&username=mobileapp@example.com&password=password' localhost:8080/oauth/token

               Sample Response:
                    {"access_token":"a59025bc-cf24-400c-8db9-a9e3b5dfec5f","token_type":"bearer","refresh_token":"94be160d-1ed1-4d02-9054-b834af64e1cc","expires_in":3599,"scope":"read"}

      Step 3: Get the Customer List.

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
      1. Since the external customers are accessing the API perodically, I have configured the expiration time limit                  for access token. The Client should regenerate the token for every 5 minutes.
      2. External customers are limited to access only get customer's list using
         @PreAuthorize("#oauth2.hasScope('read')"). To access other methods, the client id "deloitte.external" should have            write premission.
      3. The API will through 403 Forbidden error if client try to access other urls like POST, PUT, DELETE.


                       curl -i -H "Accept: application/json" -H "Authorization: Bearer $TOKEN" -X POST -H 'Content-Type: application/json' -d '{ "firstName": "Mohan", "lastName": "G", "address":{
                              "unitNumber":10,
                              "streetName":"AMOS_STREET",
                              "suburb":"Parramatta",
                              "state":"NSW",
                              "postalCode":2145

                        } }' localhost:8080/api/customers
                        HTTP/1.1 403
                        X-Content-Type-Options: nosniff
                        X-XSS-Protection: 1; mode=block
                        Cache-Control: no-cache, no-store, max-age=0, must-revalidate
                        Pragma: no-cache
                        Expires: 0
                        X-Application-Context: application
                        Content-Type: application/json;charset=UTF-8
                        Transfer-Encoding: chunked
                        Date: Sun, 29 Oct 2017 02:37:12 GMT

                        {"timestamp":1509244632053,"status":403,"error":"Forbidden","exception":"org.springframework.security.access.AccessDeniedException","message":"Access Denied","path":"/api/customers"}


    2.  A mobile application used by customer service representatives that uses the API to retrieve and update the customers details.

       Step 1 : The external customers will be given client id as "deloitte.internal".

       Step 2 : Internal customers should get the access token prior to get the customer list.

                Sample Request:

                curl -X POST --user 'deloitte.internal:secret' -d 'grant_type=password&username=mobileapp@example.com&password=password' localhost:8080/oauth/token

                Sample Response:

                {"access_token":"704bac46-a831-4dfa-9b32-540531c24975","token_type":"bearer","expires_in":3599,"scope":"read write"}

            3 : Internal customers can create and update the customers:

                        Sample Request :

                        curl -i -H "Accept: application/json" -H "Authorization: Bearer $TOKEN" -X POST -H 'Content-Type: application/json' -d '{ "firstName": "Mohan", "lastName": "G", "address":{
                        "unitNumber":10,
                        "streetName":"AMOS_STREET",
                        "suburb":"Parramatta",
                        "state":"NSW",
                        "postalCode":2145

                        } }' localhost:8080/api/customers


                        Sample Response:
                        ----------------
                        
                        HTTP/1.1 201 
                        X-Content-Type-Options: nosniff
                        X-XSS-Protection: 1; mode=block
                        Cache-Control: no-cache, no-store, max-age=0, must-revalidate
                        Pragma: no-cache
                        Expires: 0
                        X-Application-Context: application
                        Location: /api/customers/50
                        Content-Length: 0
                        Date: Sun, 29 Oct 2017 02:53:13 GMT






