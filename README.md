# Technology assessment - Spring boot
# Discount calculation api

This project defines a single REST endpoint which accepts user with a list of
all the items he bought.
In return it calculates the total discount for all of the items according to
some rules;
1. if user is employee he gets 30% 
2. if user is affiliate he gets 10%
3. if user is a regular customer who has been so for more than two years
he gets 5%
4. only single percentage based discount can be applied
5. if an item is of type grocery no discount gets applied
6. for every $100 on bill user gets $5 discount

## App-flow
```
Controller (which passes user and bought items data wrapped in a BillingPostDto)
-> Service 
-> Calculator(all the calculators get called 
which are split based on the kind of discount calculation they perform)
```  

## Running the project:
1. clone repo
2. in the project folder execute ```./mvnw spring-boot:run```

## Running tests:
- in the project folder execute ```./mvnw clean test```

## Generating coverage report:
- in the project folder execute ```./mvnw clean test jacoco:report```
Report will be generated in ```/target/site/jacoco/index.html```

## API description:
- Swagger page can be found at http://localhost:8080/swagger-ui.html#/

request-response sample:

request
```
POST /api/v1/billing
{
  "boughtItems": [
    {
      "grocery": false,
      "name": "TV",
      "num": 2,
      "price": 1
    }
  ],
  "user": {
    "createdOn": "2019-06-17T10:47:36.662Z",
    "userTypes": [
      "EMPLOYEE"
    ],
    "username": "Alex"
  }
}
```

response
```
{
  "grossAmount": 100,
  "netAmount": 70,
  "totalDiscount": 30
}
```