# Sales Order System

REST API for order and sales management built with Spring Boot, JPA and PostgreSQL.

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Data JPA + Hibernate
- PostgreSQL
- Bean Validation (JSR-303)
- Lombok

## Features

- Product and client management
- Order creation with line items
- Automatic stock deduction on order creation
- Pessimistic locking to prevent race conditions on concurrent orders
- Global error handling following RFC 7807 (Problem Details)
- Input validation on all endpoints
