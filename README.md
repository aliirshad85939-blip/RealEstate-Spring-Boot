# 🏡 RealEstate Portal

A web-based real estate application built using Java, Spring Boot, Thymeleaf, and MySQL.

## ✨ Features

- Browse property listings
- Search properties by location
- View detailed property information
- Submit enquiries through the Contact Owner form
- Store enquiries in MySQL
- User registration and login pages
- Favourite properties
- Admin dashboard and property form

## 🛠️ Tech Stack

- **Backend:** Java, Spring Boot
- **Frontend:** HTML, CSS, Thymeleaf
- **Database:** MySQL
- **Data Access:** Spring Data JPA
- **Build Tool:** Maven
- **IDE:** Visual Studio Code

## 📋 Prerequisites

- Java version compatible with the project
- MySQL Server
- MySQL Workbench (optional)
- Git

## 🚀 How to Run

### 1. Clone the repository

```bash
git clone https://github.com/alirshad85939-blip/RealEstate-Spring-Boot.git
```

### 2. Open the project

Open the cloned folder in Visual Studio Code.

### 3. Configure the database

Check `src/main/resources/application.properties` and configure your local MySQL database connection.

Never publish database passwords or API keys.

### 4. Run the application

On Windows, run this command from the project root:

```bash
.\mvnw.cmd spring-boot:run
```

### 5. Open in the browser

If your configured server port is `8081`, visit:

http://localhost:8081

Otherwise, use the port configured in `application.properties`.

## 📁 Project Structure

```text
src/
├── main/
│   ├── java/com/example/realestate/
│   └── resources/
│       └── templates/
└── test/
pom.xml
mvnw
mvnw.cmd
```

## 🎯 Project Objective

To build a real estate portal while gaining practical experience in Java backend development, Spring Boot, database integration, and server-side web development.

## 👨‍💻 Author

**Irshad Alam**

GitHub: [alirshad85939-blip](https://github.com/alirshad85939-blip)
