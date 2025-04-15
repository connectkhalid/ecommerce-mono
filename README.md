# 🛒 E-Commerce System

This is a Spring Boot-based backend application for a minimal e-commerce system. The project includes features for managing products, orders, and users with role-based access for admins and buyers. It supports product CRUD, order lifecycle handling (with edit history), and secure authentication & authorization using Spring Security.

> 🚧 **Note**: Notification handling on order placement is not implemented in this version.

---

## 👨‍💻 Author Information

- GitHub: [@connectkhalid](https://github.com/connectkhalid)
- LinkedIn: [Mohammad Khalid Hasan](https://www.linkedin.com/in/connectkhalid/)
- Email: connectkhalid404@gmail.com
- Software Engineer (JAVA) | Java, Spring Boot, REST API, Microservices, React.js

---

## 🧰 Technologies Used

- **Backend Framework**: Spring Boot, Spring Framework, Spring Security
- **ORM**: Hibernate, JPA
- **Database**: MySQL
- **Project Management**: Maven
- **Java Version**: JDK 17

---

## 🔐 Authentication & Authorization

- JWT-based security with Spring Security
- Role-based access for `ADMIN` and `BUYER`
- Signup/Login endpoints with token-based access control

---

## ⚙️ Features Overview

### 🔓 Public Features

- User Registration (Buyer)
- User Login
- View all available products
- Search products by name
- Sort products by price (ascending/descending)

---

### 🔒 Admin Features

- ✅ Add/Remove/Update Products
- ✅ View all products
- ✅ Search products by name
- ✅ View all orders
- ✅ Search orders by unique ID
- ✅ Approve/Reject/Update status of orders
- ✅ View order edit history
- ✅ Mark orders as "Delivered"
- ✅ Auto-move delivered orders to `deliveries` table (scheduled at 12:00 AM daily)

---

### 🙋 Buyer Features

- ✅ Sign Up / Log In
- ✅ View Product List
- ✅ Search Product by Name
- ✅ Sort Products by Lowest/Highest Price
- ✅ Place Orders
- ✅ Edit Orders (only if status is `PENDING`)
- ✅ View Order Edit History
- ❌ Cannot edit once the order is approved/rejected
- ❌ Cannot place orders for products with 0 quantity ("Out of stock" message shown)

---

## 📦 Order Management

- Each order records the **price at the time of placement**, unaffected by later product updates.
- Orders include an **edit history** to track all buyer-side modifications until approval.
- Admin marks order as `Delivered` → **product quantity is updated accordingly**
- A scheduled job runs every day at **midnight** to move delivered orders to a `deliveries` table.

---

## 🧪 Global Features

- 📦 RESTful API architecture
- 🧾 Centralized Exception Handling
- 🛡️ JWT Authentication & Authorization
- 🧼 Clean Code with DTOs and Custom Validators
- 📁 Image Upload Support
- 📜 Edit History Tracking

---

## 🚀 How to Run Locally

### ✅ Prerequisites

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/install.html)
- [MySQL](https://dev.mysql.com/downloads/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)

### 🏃 Run Steps

1. Clone the repo:
   ```bash
   git clone https://github.com/connectkhalid/ecommerce-mono.git
   cd ecommerce-mono
   ```
2. Configure MySQL database in `application.yml`
3. Add permission data form assest file
4. Build and run

---

## 📬 API Documentation

👉 Visit: [Postman API Docs](https://documenter.getpostman.com/view/29496949/2sB2cbZe4G)

---

## ⚠️ Limitations

- ❌ Email/Push notification to admin on order placement not implemented yet
- ❌ Frontend/UI not included (backend only)
