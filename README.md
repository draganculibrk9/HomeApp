# HomeApp
Household management applicaton written in _Spring Boot_ and _Angular_, using _gRPC_ framework.

# Project setup

Clone the repository:

`git clone https://github.com/draganculibrk9/HomeApp.git`

- **_AuthService_**: Open terminal in _AuthService_ root directory and run `mvnw spring-boot:run`, runs on port 8081 and _gRPC_ server is started on port 9091
- **_HouseholdService_**: Open terminal in _HouseholdService_ root directory and run `mvnw spring-boot:run`, runs on port 8082 and _gRPC_ server is started on port 9092
- **_ServicesService_**: Open terminal in _ServicesService_ root directory and run `mvnw spring-boot:run`, runs on port 8083 and _gRPC_ server is started on port 9093
- **_Client_**: Open terminal in _Client_ root directory and run `npm install` and `npm run start`, it should be available at port 4200
- **_Envoy proxy_**: Start _Docker_ container, open terminal in _Proxy_ directory and run:
```
  docker build -t todo/envoy -f ./envoy.Dockerfile .
  docker run -d -p 8080:8080 -p 8079:8079 -p 8078:8078 todo/envoy
```
