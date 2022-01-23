
## spring-reactive-mongodb-web-service

User service for Spring Data Reactive MongoDB with Spring Webflux (Functional Endpoints)

![Project Status: WIP – Initial development is in progress, but there has not yet been a stable, usable release suitable for the public.](https://www.repostatus.org/badges/latest/wip.svg)

### Prerequisites
* JDK 11+
* Docker or MongoDB installed
* [You can read Reactive rest service on Medium](https://gokhana.medium.com)


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