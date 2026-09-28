# DineHub

DineHub is a food ordering and restaurant management application being developed using **Spring Boot microservices**.

The project is being developed incrementally, with each service maintained independently inside the same repository.

---

# Architecture

The planned DineHub architecture is:

```text
                         Eureka Server
                            :8761
                               |
              +----------------+----------------+
              |                |                |
              v                v                v
        User Service      Food Service      Order Service
           :8080             :8081             :8082
                                                   |
                                                   v
                                            Payment Service
                                               :8083
```

The current repository contains:

- `eureka-server`
- `user-service`
- `food-service`

Order Service and Payment Service are planned but have not yet been implemented.

---

# Technologies

- Java 17
- Spring Boot 4.1.1
- Spring Cloud 2025.1.3
- Spring WebMVC
- Spring Data JPA
- MySQL
- Jakarta Bean Validation
- Spring Security Crypto
- BCrypt
- Lombok
- Netflix Eureka
- Maven

---

# Eureka Server

The Eureka Server acts as the service registry for DineHub.

### Port

```text
8761
```

### Application Name

```text
eureka-server
```

### Configuration

```properties
spring.application.name=eureka-server

eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
server.port=8761
```

The Eureka Server is configured as a standalone Eureka registry and does not register itself as a client.

The application uses:

```java
@EnableEurekaServer
```

to enable the Eureka Server.

---

# User Service

The User Service manages user accounts, authentication, user operations, password hashing, validation, and session-based access control.

### Port

```text
8080
```

### Application Name

```text
user-service
```

## User Entity

```text
User
----------------
id
name
email
password
phoneNumber
role
address
createdAt
updatedAt
```

## Roles

The current `Role` enum contains:

```text
admin
chef
waitress
user
```

---

# User Service Features

## Create User

### Endpoint

```http
POST /api/users/createUser
```

Creates a new user.

The request body is validated using:

```java
@Valid
@RequestBody User user
```

### Current Validation Rules

- Name is required.
- Name must contain between 3 and 40 characters.
- Email is required.
- Email must have a valid format.
- Password is required.
- Password must contain at least 6 characters.
- Phone number is optional.
- If a phone number is provided, it must contain exactly 10 digits.
- Role is required.
- Email must be unique.

---

## Login

### Endpoint

```http
POST /api/users/login
```

Login uses:

- Email
- Password
- BCrypt password verification
- HTTP Session authentication

Request parameter validation is enabled using:

```java
@Validated
```

with:

```java
@NotBlank
@Email
```

After successful login, the user's email is stored in the HTTP session.

---

## Logout

### Endpoint

```http
POST /api/users/logout
```

Invalidates the current HTTP session.

---

## Get Users By Name

### Endpoint

```http
GET /api/users/getUsers?name={name}
```

Returns users matching the provided name.

A valid HTTP session is required.

---

## Get User By Email

### Endpoint

```http
GET /api/users/getUserByEmail/{email}
```

Returns a user using their email address.

A valid HTTP session is required.

---

## Update User

### Endpoint

```http
PUT /api/users/updateUser?email={email}
```

Updates:

- Name
- Phone number
- Address
- Role

The request body is validated using `@Valid`.

A valid HTTP session is required.

---

## Delete User

### Endpoint

```http
DELETE /api/users/deleteUser/{email}
```

Deletes a user using their email address.

A valid HTTP session is required.

The delete operation uses a transaction.

---

# User Authentication

The current User Service uses **HTTP Session-based authentication**.

JWT authentication has not been implemented.

### Login Flow

```text
User
  |
  | Email + Password
  v
User Controller
  |
  v
User Service
  |
  | Find User
  v
BCrypt Password Verification
  |
  | Password Matches
  v
HTTP Session
  |
  | Store user email
  v
Authenticated Session
```

Protected endpoints check the session:

```java
session.getAttribute("user")
```

The current session timeout is:

```properties
server.servlet.session.timeout=2h
```

---

# Password Security

Passwords are not stored as plain text.

The User Service uses Spring Security Crypto with BCrypt:

```java
new BCryptPasswordEncoder()
```

During user creation:

```text
Plain Password
      |
      v
BCrypt
      |
      v
Hashed Password
      |
      v
MySQL
```

During login:

```text
Entered Password
      |
      v
PasswordEncoder.matches()
      |
      v
Stored BCrypt Hash
```

The password is not included in `UserResponse`.

---

# Request Validation

The User Service uses Jakarta Bean Validation.

## Object Validation

Request bodies use:

```java
@Valid
@RequestBody User user
```

This validates the fields inside the `User` object.

## Method Parameter Validation

The controller uses:

```java
@Validated
```

for validation of request parameters and path variables.

Examples:

```java
@NotBlank
@Email
```

### Validation Flow

```text
HTTP Request
      |
      v
Controller Validation
      |
      +------ Invalid ------> 400 Bad Request
      |
      v
Service Layer
      |
      v
Repository
      |
      v
Database
```

Invalid request bodies are rejected before the service method is executed.

---

# Global Exception Handling - User Service

The User Service uses:

```java
@RestControllerAdvice
```

The current global exception handler handles:

- `MethodArgumentNotValidException`
- `UserNotFoundException`
- `UserAlreadyExistsException`
- Generic `Exception`

Validation errors are returned as:

```text
400 Bad Request
```

Example:

```json
{
    "password": "Password must contain at least 6 character",
    "role": "Role is required",
    "phoneNumber": "Phone number must contain exactly 10 digits",
    "name": "Name size should between 3 to 40",
    "email": "Email is not in valid format"
}
```

---

# User Service Database

The User Service uses MySQL.

### Database

```text
dinehub_user
```

The database is configured with:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/dinehub_user?createDatabaseIfNotExist=true
```

JPA/Hibernate is used for persistence.

---

# Eureka Client - User Service

The User Service contains the Eureka Client dependency and is configured to register with the Eureka Server.

```properties
eureka.client.enabled=true
eureka.instance.prefer-ip-address=true
eureka.client.service-url.defaultZone=${EUREKA_SERVER_URL:http://localhost:8761/eureka}
```

The default Eureka Server URL is:

```text
http://localhost:8761/eureka
```

---

# Food Service

The Food Service is currently implemented with basic CRUD functionality and is still under development.

### Port

```text
8081
```

### Application Name

```text
foodService
```

### Database

```text
dinehub_food_db
```

---

## Food Entity

The current `Food` entity contains:

```text
Food
----------------
foodId
name
description
price
quantity
imageUrl
isAvailable
createdAt
updatedAt
```

> `categoryId` is part of the planned data model but is not currently present in the repository's `Food` entity.

### Current Validation Rules

- Food name is required.
- Food name cannot exceed 100 characters.
- Description cannot exceed 1000 characters.
- Price is required.
- Price must be greater than zero.
- Quantity is required.
- Quantity cannot be negative.
- Availability is required.

If availability is not set before creation, the entity lifecycle callback defaults it to:

```text
true
```

---

# Food Service Endpoints

## Create Food

```http
POST /api/foods
```

Creates a food item.

The request body uses:

```java
@Valid
@RequestBody Food food
```

---

## Get Food By ID

```http
GET /api/foods/{foodId}
```

Returns a food item by ID.

---

## Get All Food

```http
GET /api/foods
```

Returns all food items.

---

## Update Food

```http
PUT /api/foods/{foodId}
```

Updates an existing food item.

The current implementation updates:

- Name
- Description
- Price
- Quantity
- Image URL
- Availability

---

## Delete Food

```http
DELETE /api/foods/{foodId}
```

Deletes a food item.

Returns:

```text
204 No Content
```

when the deletion succeeds.

---

# Food Service Exception Handling

The Food Service currently contains:

```text
FoodNotFoundException
GlobalExceptionHandler
```

`FoodNotFoundException` returns:

```text
404 Not Found
```

when a food item cannot be found.

---

# Food Service Timestamps

The Food entity uses JPA lifecycle callbacks.

Before insertion:

```java
@PrePersist
```

sets:

```text
createdAt
updatedAt
```

Before an update:

```java
@PreUpdate
```

updates:

```text
updatedAt
```

---

# Planned Services

The following services are planned but are not currently implemented in the repository.

## Order Service

Planned model:

```text
Order
----------------
orderId          PK
userId
items
totalPrice
orderType
orderStatus
createdAt
updatedAt
```

### Order Item

```text
OrderItem
----------------
foodId
name
quantity
unitPrice
subtotal
```

### Order Type

```text
DINE_IN
DELIVERY
```

### Order Status

```text
PENDING
CONFIRMED
PREPARING
OUT_FOR_DELIVERY
READY
DELIVERED
CANCELLED
```

---

## Payment Service

Planned model:

```text
Payment
----------------
paymentId
orderId
userId
amount
paymentMethod
paymentStatus
transactionId
paidAt
```

---

# Planned Features

The following features are planned for future development:

- [ ] Complete Food Service
- [ ] Add Food Service Eureka Client
- [ ] Order Service
- [ ] Payment Service
- [ ] Inter-service communication
- [ ] API Gateway
- [ ] Service authorization
- [ ] Complete role-based access control
- [ ] Payment processing
- [ ] Dine-in order workflow
- [ ] Delivery order workflow
- [ ] Production deployment

---

# Repository Structure

```text
DineHub
│
├── eureka-server
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com.dinehub.eurekaserver
│   │   │   │       └── EurekaServerApplication.java
│   │   │   └── resources
│   │   │       └── application.properties
│   │   └── test
│   └── pom.xml
│
├── user-service
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com.dinehub.user
│   │   │   │       ├── config
│   │   │   │       ├── controller
│   │   │   │       ├── dto
│   │   │   │       ├── entity
│   │   │   │       ├── exception
│   │   │   │       ├── repository
│   │   │   │       └── service
│   │   │   └── resources
│   │   │       └── application.properties
│   │   └── test
│   └── pom.xml
│
├── food-service
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com.dinehub.foodService
│   │   │   │       ├── controller
│   │   │   │       ├── entity
│   │   │   │       ├── exception
│   │   │   │       ├── repository
│   │   │   │       ├── service
│   │   │   │       └── seviceImpl
│   │   │   └── resources
│   │   │       └── application.properties
│   │   └── test
│   └── pom.xml
│
└── README.md
```

---

# Development Progress

## Eureka Server

- [x] Create Eureka Server project
- [x] Add Eureka Server dependency
- [x] Enable `@EnableEurekaServer`
- [x] Configure standalone Eureka Server
- [x] Configure port `8761`

## User Service

- [x] Create User Service
- [x] Configure MySQL
- [x] Create User entity
- [x] Add user validation
- [x] Create User repository
- [x] Create service layer
- [x] Implement user creation
- [x] Implement user lookup by name
- [x] Implement user lookup by email
- [x] Implement user update
- [x] Implement user deletion
- [x] Implement login
- [x] Implement logout
- [x] Add BCrypt password hashing
- [x] Add HTTP Session authentication
- [x] Add controller parameter validation
- [x] Add global exception handling
- [x] Add validation error handling
- [x] Add Eureka Client dependency
- [x] Enable Eureka Client

## Food Service

- [x] Create Food Service
- [x] Configure MySQL
- [x] Create Food entity
- [x] Create Food repository
- [x] Create Food service interface
- [x] Create Food service implementation
- [x] Create Food controller
- [x] Implement create food
- [x] Implement get food by ID
- [x] Implement get all food
- [x] Implement update food
- [x] Implement delete food
- [x] Add Food validation
- [x] Add Food not found exception
- [x] Add Food global exception handler
- [ ] Add Eureka Client
- [ ] Complete remaining Food Service features

## Order Service

- [ ] Create Order Service
- [ ] Create Order entity
- [ ] Create OrderItem entity
- [ ] Implement order creation
- [ ] Implement order status management
- [ ] Implement Dine-In orders
- [ ] Implement Delivery orders

## Payment Service

- [ ] Create Payment Service
- [ ] Create Payment entity
- [ ] Implement payment processing
- [ ] Implement payment status management
- [ ] Implement transaction handling

---

# Git Workflow

The project uses Git for version control.

The main branch is:

```text
main
```

Service development is performed using separate branches.

Example:

```text
main
  |
  ├── user-service
  |
  ├── food-service
  |
  ├── order-service
  |
  └── payment-service
```

Completed service changes can be merged into `main` through pull requests.

---

# Development Approach

DineHub is being developed incrementally.

The current workflow is:

```text
Build Service
     |
     v
Test Service
     |
     v
Commit Changes
     |
     v
Create Pull Request
     |
     v
Merge into main
     |
     v
Build Next Service
```

The README will be updated as new services and features are implemented.
