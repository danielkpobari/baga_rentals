Baga Rentals API
This is a simple REST API for Booking a Bike.  

An Admin can add a bike, a User can book a bike and they can both see all bikes.

Technologies
Java
Maven
Springboot
H2 Database
Requirements
You need the following to build and run the application:

[JDK 20]
[Maven 3.8.1]
[SpringBoot 3.2.2]
HOW TO USE THIS APPLICATION
Register 2. Login 3. Add Bike [Admin] 4. Book Bike [User] 5. view all Bikes 
For ease of use and to test the functionality, I have made documents of the endpoints here
POST
auth-Admin
http://localhost:8080/api/auth/signin
﻿

Body
raw (json)
json
{
    "username": "daniel",
    "password": "B1a2g3#4"
}
POST
addBike
http://localhost:8080/api/bikes/addBike
﻿

Authorization
Bearer Token
Token
<token>
Body
raw (json)
json
{
    "model": "2027",
    "brand" : "Toyota",
    "color" : "Red"
}
POST
auth-User
http://localhost:8080/api/auth/signin
﻿

Body
raw (json)
json
{
    "username": "kpobari",
    "password": "D1a2g3#4"
}
POST
New Request
http://localhost:8080/api/bikes/bookBike
﻿

Authorization
Bearer Token
Token
<token>
Body
raw (json)
json
{
  "username": "kpobari",
  "brand": "Toyota",
  "bikeId": 1,
  "startDate": "2024-02-05T12:30:00",
  "endDate": "2024-02-10T15:45:00"
}
GET
getAllBike
