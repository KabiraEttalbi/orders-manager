# Orders Manager

This project implements a **microservices architecture** for managing orders, customers, and products. It also includes service discovery, centralized configuration, and administration servers.

## Project Structure

- **admin-server**: Administration server for monitoring microservices.
- **config-server**: Centralized configuration server.
- **eureka-server**: Service registry and discovery using Eureka.
- **customers**: Microservice for managing customer-related operations.
- **orders**: Microservice for managing orders.
- **products**: Microservice for managing products.

## Technologies Used

- Java 17+
- Spring Boot
- Spring Cloud (Eureka, Config Server)
- MySQL (for microservices' databases)
- Maven

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/KabiraEttalbi/orders-manager.git
cd orders-manager
```

### 2. Set up MySQL Databases

Create databases for each service:

```sql
CREATE DATABASE customer;
CREATE DATABASE order;
CREATE DATABASE product;
```

### 3. Build the Project

Build all microservices using Maven:

```bash
mvn clean install
```

Or if you want to build without running tests:

```bash
mvn clean install -DskipTests
```

### 4. Configure Application Properties

- Check the `config-server` for application configuration files.
- Update `application.yml` or `application.properties` if necessary (DB credentials, ports, etc.).

### 5. Run the Services (in this order)

#### Start Config Server
```bash
cd config-server
mvn spring-boot:run
```

#### Start Eureka Server
```bash
cd eureka-server
mvn spring-boot:run
```

#### Start Admin Server
```bash
cd admin-server
mvn spring-boot:run
```

#### Start Microservices

Each service needs to be started separately:

```bash
cd customers
mvn spring-boot:run
```

```bash
cd orders
mvn spring-boot:run
```

```bash
cd products
mvn spring-boot:run
```

Open each service in a separate terminal tab or window.

---

## Services URLs

- **Eureka Server**: `http://localhost:8761`
- **Admin Server**: `http://localhost:8090`
- **Customers API**: `http://localhost:8081`
- **Orders API**: `http://localhost:8083`
- **Products API**: `http://localhost:8082`
- **Config Server**: `http://localhost:8888`

(Ports may vary based on your settings.)

---

## Features

- Microservice architecture
- Centralized configuration management
- Service discovery with Eureka
- Admin dashboard monitoring
- MySQL database integration
---

## Useful Commands

| Action | Command |
|:------|:--------|
| Clean and install all modules | `mvn clean install` |
| Run a microservice | `mvn spring-boot:run` inside the service folder |
| Skip tests during build | `mvn clean install -DskipTests` |
| Package a microservice | `mvn package` |
| Run a built JAR | `java -jar target/your-app-name.jar` |

---

## Contributors

- [Kabira Ettalbi](https://github.com/KabiraEttalbi)
  
---

## 📄 License

This project is licensed under the MIT License.  
Feel free to use and adapt it for your needs!

---
