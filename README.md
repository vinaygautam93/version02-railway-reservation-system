# Railway Ticket Reservation System

A **microservices-based Railway Ticket Reservation System** built with **Java Spring Boot**, **Kafka** for asynchronous communication, and **React.js** for the frontend.  
The project also integrates **Stripe Payment Gateway** for secure and real-time payment processing.

---

## **Features**

### **Core Functionalities**
- User registration and authentication.
- Train search and seat availability check.
- Ticket booking, cancellation, and payment using **Stripe Payment Gateway**.
- Email notifications for ticket confirmation and cancellations.
- Event-driven communication using **Kafka** between microservices.

### **Microservices Used**
- **User Service** – Manages user data and authentication.
- **Train Service** – Handles train schedules and seat availability.
- **Booking Service** – Books and cancels tickets.
- **Payment Service** – Secure payment handling using **Stripe**.
- **Notification Service** – Sends confirmation and cancellation emails triggered by Kafka events.

---

## **Tech Stack**

### **Backend**
- **Java 17**, **Spring Boot**
- **Spring Security & JWT**
- **Kafka** (for asynchronous communication)
- **MySQL** (Relational Database)
- **Stripe Payment Gateway**
- **Spring Data JPA**
- **REST APIs**
- **Lombok**

### **Frontend**
- **React.js**, **Tailwind CSS**
- **Axios** (for REST API calls)
- **React Router DOM**
- **Stripe.js** & **React-Stripe-Checkout**

---

## **Architecture**

This project follows **microservices architecture** with:
- **API Gateway** for routing.
- **Eureka Server** for service discovery.
- **Kafka** for message communication.
- **Stripe** for secure online payments.

