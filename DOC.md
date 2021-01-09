## Running the app
Navigate to the repo's directory and start the service using the following command.
    
    docker-compose up 

## Implementation

## Layers
The project is divided into 3 layers, **Controller**, **Service** and **Repository** layers. 

### Controllers
Controllers are responsible for controlling the flow of the application over the HTTP requests. There are 3 types of controllers in this project, **AuthController**, **AlertController** and **CurrencyController**. **AuthController** contains the end points for login and signup functionalites. **CurrencyController** contains the endpoints for adding, removing and getting the currencies. Only an admin user is allowed to add/remove a currency. **AlertController** contains the endpoints for creating/removing/cancelling/acking and deleteing an alert. Controllers recieve the DTO objects and send back Response objects. These are defined in the `dto` package under the `request` and `response` sub packages.

### Services
The main logic is written in the Service layer. The service layer validates the requests, passes them further to the Repository layer, encapsulates the responses from the Repository layer in their respective objects and sends them back. There are 3 kinds of services, **Userservice**, **Alertservice** and **CurrencyService**. **AlertService** and **CurrencyService** interact further with the repository layer depending on the request. **UserService** is used by the authentication manager to check the username and password details when the user tries to login and for getting the user details.

### Repositories
A Repository is an abstraction of the data layer and only it interacts directly with the database. The main benefit of this is to provide an abstraction to the code so that it is not bothered by how the database is implemented. Or if the database was to be changed, the repository layer will keep on working as before and not break. There are 4 types of repositories: **AlertRepository**, **CurrencyRepository**, **UserRepository** and **NotificationRepository**.   

## Entities

### BaseEntity
    `id`, 
    `createdAt`, 
    `updatedAt`

### User extends BaseEntity
    `name`, 
    `email` - is unique, 
    `type(ADMIN, NORMAL)` 

- If the type of user is not provided, it is automatically assumed to be a normal user.


### Currency extends BaseEntity
    `name` - is unique, 
    `symbol` - is unique, 
    `currentPrice` 

- If the symbol of the currency matches any symbol defined in the `UnsupportedCurrencyType`, a `UnsupportedCurrencyCreationException` is thrown.
 
### Alert extends BaseEntity
    `user_id` foreign key references User - is unique, 
    `currency_id` foreign key references Currency - is unique,  
    `targetValue` - is unique 
    `status(NEW, TRIGGERED, ACKED, CANCELLED)` 

- The first three properties of alert are unique meaning a user cannot add the same alert with the same price and on the same currency.

### Notification extends BaseEntity
    `user_id`, 
    `currency_id`, 
    `status(NEW, SENT)` 

All of the status and types are of enum type and can be found in the `enumerations` package.

## Notification system and triggers
I have defined 2 triggers on the currency and the alert tables. **trigger_alert** triggers whenever there is a change in the currency's price and checks if the currency price >= the alert's targetValue. If yes, then it sets the alert status to `TRIGGERED`.

**create_notification_object** triggers whenever there is an update on the trigger table and inserts a new row in the notification table if the alert's status is `TRIGGERED`. The initial status of this notification object is `NEW`. I am using triggers so that any change on the currency and alert is registered instantly and later when the `ScheduledTask` runs, it does not have to go through the alerts table to check if it needs to notify a user for a triggered alert. Rather, it can just get all of the notifications with status `NEW` from the notification table and send those notifications to the users. After sending the notification, its status is set to `SENT` so that the user doesn't recieve multiple of the same notifications. Another benefit of using a seperate notification table is that we can periodically remove the old notification records to save space. This functionality is not implemented in this project though, but can be easily done later once the table grows pretty big. I have aleady added the triggers to the database. If you need to add them again, you can run the `triggers.sql` script under the resourses package in the IntelliJ query console.

## Authentication
The application uses jwt token based authentication. Once the user logins, they are provided with the jwt token in reponse which they need to provide in any subsequent requests. Otherwise they won't be authorized to request something from the application. The only endpoints that are free from the jwt authorization requirement are the login and signup endpoints. The login endpoint, however still authenticates the user using their username and password. All of the authentication and jwt authorization functionality is present in the `config` package.

## Testing
All of the tests are located in the `src/main/test/java/com.bayzat.bayztracker` package. This package is further divided into `controller` and `service` sub packages. Locally, I was able to run both types of tests and get a line coverage of `74%`. However, there is an error while running the `controller` tests in the Docker container. So in that case, the line coverage falls down to `52%`. In order to make sure that none of the tests fail while running the project in Docker, I have made 2 profiles: `dev` and `prod`. The default profile is `prod`. If you would like to run the controller tests as well please start a local db instance on your machine matching the  `spring.datasource` properties in  `application-dev.yaml` and delete the following annotation from the top of each of the test class in the `controller` package:

    @IfProfileValue(name = "spring.profiles.active", values = {"dev"})

