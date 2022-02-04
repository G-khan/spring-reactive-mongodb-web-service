
## spring-reactive-mongodb-web-service

User service for Spring Data Reactive MongoDB with Spring Webflux (Functional Endpoints)

![Project Status: Active](https://www.repostatus.org/badges/latest/active.svg)

### Prerequisites
* JDK 11+
* Docker or MongoDB installed
* [You can read my article on Medium](https://medium.com/yemeksepeti-teknoloji/spring-reactive-mongodb-ile-reactive-uygulama-geli%C5%9Ftirme-e046fa49ae58)


Examples

* Mono, Flux structures
* Functional Reactive Endpoints
* WebTestClient
* Spring Data Mongo Reactive

<code>  


     ### 
     GET http://localhost:8083/api/v1/users
      
     ###    
     POST http://localhost:8083/api/v1/users    
     Content-Type: application/json  
      
     {    
     "name": "Zühtü",    
     "score": 52    
     }  
      
     ###    
     PUT http://localhost:8083/api/v1/users/61ec13ca48d46147479f8fcd    
     Content-Type: application/json  
     
     {    
     "name": "Gokhanadev",    
     "score": 52    
     }  

     ###
     GET http://localhost:8083/api/v1/users/61ec13ca48d46147479f8fcd
     
     ###
     DELETE http://localhost:8083/api/v1/users/61ec13ca48d46147479f8fcd  
     
     ###    
     DELETE http://localhost:8083/api/v1/users?name=Zühtü  

</code>
