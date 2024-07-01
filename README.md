# CRM System Documentation

### Notes: 
The application is unfinished and besides the rest of the undone features 
I would apply the next obvious code and architecture improvements:

* It would be better to add an extra layer of request/response models generated by openapi-generator-maven-plugin plugin. 


* There is no proper exception handling: so custom Runtime exceptions should be created. It could be done declaratively by utilizing @ControllerAdvice and @ExceptionHandler annotations. 


* I've tried to generify existing JsonConverter class to be able to convert different types in the future. But I've faced with an issue when Jackson could not deserialize cached value to expected model. 


* There are no tests at all. Apparently, it needs to be added by using Mockito + Junit for unit tests. For integration tests Testcontainers could be used.


* For now service layer components inject JPA repositories directly. It means dto->entity conversion happens on the service layer. It would be better to make an additional dao layer which will accept DTO object and convert it on it's side. As result, service layer will accept DTO from controller layer and it will throw the same DTO to the DAO layer.    


* There is no data validation. So, the trivial data validations could be applied in OpenAPI 3.0 spec and in Thymeleaf pages.    
### Launch Instruction
It would be enough to execute two docker-compose commands from project root directory to start the whole application. 

* docker compose build
* docker compose up

### Application Features

1. CRUD operations to manage Client, Contact, Task entities. 
   * Use attached Postman collection to check this out. (CRM-Application.postman_collection.json in root directory)


2. Caching implemented via RedisTemplate to cache List<Client> and make it flexible cause Client could be modified. 
    

3. WebSocket implementation: message exchanging and task due date notification after new task was created for the contact.
   * Go to the localhost:8080 and try to add new user to the chat, then write messages and finally add new task via API.
   * To add new task, the client and contact should be added accordingly. 


4. Few thymeleaf pages for Client and Contact creation. 

