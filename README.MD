# Dependency
* Spring Boot
* Java 8
* Redis
* Mybatis
* Jersey
* Mysql 5.6

## Environment Prepare
```
./mysql -u root -p
./mysql create database upgrade
./mysql -u root -p upgrade < upgrade.sql

./redis-server
```

# Test
```
mvn test
```

# Running
 ```
 mvn spring-boot:run
 ```
 
 # Build jar
 ```
  mvn package -Dmaven.test.skip=true
 ```
 
 # Build docker
 ```
 mvn package docker:build -Dmaven.test.skip=true
 docker run -p 8080:8080 -t upgrade/pacific-ocean
 ```
 
 # AWS Demo
 
 ## GET Schedule
 ```
 curl --request GET \
  --url 'http://ec2-18-217-218-155.us-east-2.compute.amazonaws.com:8080/rest/campsite/1?start=20181005&end=20181010'
 ```
 
 ## POST Booking
 ```
curl --request POST \
  --url http://ec2-18-217-218-155.us-east-2.compute.amazonaws.com:8080/rest/booking \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data 'name=Zhen&email=jenkin.du%40gmail.com&start_date=20181005&end_date=20181006'
```

## GET Booking
```
curl --request GET \
  --url http://ec2-18-217-218-155.us-east-2.compute.amazonaws.com:8080/rest/booking/1
```

## PUT Booking
```
curl --request PUT \
  --url http://ec2-18-217-218-155.us-east-2.compute.amazonaws.com:8080/rest/booking/1 \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data 'name=Zhen%20Du&email=jenkin.du%40gmail.com&start_date=20181003&end_date=20181004'
```

## DELETE Booking
```
curl --request DELETE \
  --url http://ec2-18-217-218-155.us-east-2.compute.amazonaws.com:8080/rest/booking/1 \
  --header 'Content-Type: application/x-www-form-urlencoded'
```

[API doc](https://web.postman.co/collections/5494810-f695cab7-6878-eb55-7943-ad88e1ccfd65?workspace=5e7c8d3d-b208-4162-87b5-1abd95cc46e3#e81b9248-3037-98b9-871a-e789f68ae04a)
