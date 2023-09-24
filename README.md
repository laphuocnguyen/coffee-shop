## Local Setup without docker
- Need postgres local setup
- Clone and open the project in your IDE. Adapt data source in `application.properties` to correct your postgres database.
- Run the main class [DemoApplication](src/main/java/com/example/demo/DemoApplication.java) as spring boot appllication.
On startup the migration will be check and apply in folder `main\resources\db\changelog`.

The Product Service is now ready to receive API requests.

## Local Setup with docker
- Need docker setup
- Clone and build application with command `mvn clean install`
- Then run `docker-compose up --build`
- Or combine two commands into one `mvn clean package && docker-compose up --build`
The Product Service is now ready to receive API requests.

## Open swagger ui

In order to explore all api to the following below for swagger ui.
[http://localhost:8080/swagger](http://localhost:8080/swagger)

## REST API Documentation for Products(Coffee-Shop)

**_Coffee Shop Configuration(Not implement yet)_**

Send a `PUT` request to `http://localhost:8080/api/shops/{id}`

Sample Request Body
```json
{
  "openHour": "",
  "closeHour": "",
  "phoneNumber":"",
  "address" : "",
  "location" : {
    "lat" : "",
    "lng" : ""
  },
  "queueSize" : ""
  
}
```

**_Load Shop Information (Not implement yet)_**

Send a `GET` Request to `http://localhost:8080/api/shop/{shop-id}`

Sample Respone Body
```json
{
  "queueSize":"",
  "queues": [
    {
      "queueId":"",
      "totalCurrentCustomer": ""
    }
  ]
}
```

**_Load Queue Detail (Not implement yet)_**
Send a `GET` Request to `http://localhost:8080/api/shop/{shop-id}/queue/{queue-id}`

Sample Response Body
```json
{
  "customers": [
    {
      "customerId":"",
      "customerName":"",
      "orderedNumber":"",
      "orderId":"",
      "waitingTime": ""
    }
  ]
}
```

**_Load Order Detail (Not implement yet)_**
Send a `GET` Request to `http://localhost:8080/api/customers/{customer-id}/orders/{order-id}`

Sample Response Body
```json
{
  "totalPrice": "",
  "orderItems": [
    {
      "menuItemName":"",
      "quantity":""
    }
  ]
}
```

**_Create Customer Account(Not implement yet)_** 

Send a `POST` Request to `http://localhost:8080/api/customer`

Sample Request Body
```json
{
    "lastname": "",
    "firstname": "",
    "username": "",
    "phoneNumber": "",
    "address":""
}
```

**_Find Closed Coffee Shop (Not implement yet)_**

Send a `PUT` request to `http://localhost:8080/api/shop?lat=&lng=`

Sample Response Body
```json
{
    "shopId": "",
    "menuItems":[
      {
        "itemName": "",
        "price": ""
      }
    ]
}
```

**_Create Order_**

Send a `POST` request to `http://localhost:8080/api/customers/shops/1/orders`

Sample Request Body
```json
[
  {
    "menuItemId": 1,
    "quantity": 2
  },
  {
    "menuItemId": 2,
    "quantity": 6
  }
]
```
Response
```json
{
  "id": 1,
  "status": "IN_QUEUE",
  "shopId": 1,
  "customerId": 1,
  "totalAmount": 260000,
  "waitingTimes": 45
}
```

**_Cancel an order (Not implement yet)_**

Send a `DELETE` request to `http://localhost:8080/api/customers/{customer-id}/orders`

If cancel is successful `204 status` is returned in response, if failed then `exception` is returned

## Run SonarQube

Start docker compose with SonarQube service that already setup with address [http://localhost:9000/](http://localhost:9000/).

Build project with `mvn clean package` then run `mvn sonar:sonar` after that can you go to sonar [http://localhost:9000/dashboard?id=com.gfgtech%3Aproduct](http://localhost:9000/dashboard?id=com.gfgtech%3Aproduct)

(*) In case SonarQube require username and password. We can register new account and using this comment: `mvn sonar:sonar -Dsonar.login={username} -Dsonar.password={password}`

## Security

All rest api have protected by jwt token in order to execute it we have to get token first.

Send a `POST` request to `http://localhost:8080/api/auth/login`

We have prepared default user for testing admin/admin
```json
{
    "username": "admin",
    "password": "admin"
}
```
Response
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyODM3NjYwMiwiaWF0IjoxNjI4MzQwNjAyfQ.tLC099ew6Jh5aEgNQ2_4s8-6mbLmWYjqZ379j-rJGqqDwzElM37kp-MgzAk5rE2NMXFiCIEBLopXjtQfazIEfg"
}
```
