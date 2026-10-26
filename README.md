# Sales Order System

Small REST API for learning backend fundamentals with Spring Boot and PostgreSQL.

> Personal learning project created as a first backend portfolio project. It intentionally stays small instead of simulating a production-scale commerce platform.

## What it demonstrates

- Layered Spring Boot structure: controller, service, repository and domain
- PostgreSQL persistence with Spring Data JPA
- Request/response DTOs
- Bean Validation
- Transactional order creation
- Automatic stock deduction
- Pessimistic locking when reading products for an order
- Domain-specific exceptions and global error handling
- Unit tests around order/stock business rules

## Domain

The API manages:

- **Clients**
- **Products**
- **Orders**
- **Order items**

When an order is created, the service loads each product with a pessimistic write lock, checks available stock, deducts the requested quantity and stores the unit price in the order item.

## Main endpoints

### Clients
- `POST /api/clientes`
- `GET /api/clientes/{id}`

### Products
- `POST /api/productos`
- `GET /api/productos/{id}`

### Orders
- `POST /api/pedidos`
- `GET /api/pedidos/{id}`

## Run locally

Requirements:

- Java 21+
- PostgreSQL

Create a database named `gestion_pedidos`, then provide the database password as an environment variable.

Linux/macOS:

```bash
export DB_PASSWORD=your-password
./mvnw spring-boot:run
```

PowerShell:

```powershell
$env:DB_PASSWORD="your-password"
./mvnw spring-boot:run
```

Optional variables:

- `DB_URL` (default: `jdbc:postgresql://localhost:5432/gestion_pedidos`)
- `DB_USERNAME` (default: `postgres`)

## Run tests

```bash
./mvnw test
```

## Example: create an order

```json
{
  "clienteId": 1,
  "items": [
    {
      "productoId": 1,
      "cantidad": 2
    }
  ]
}
```

## Why there is no frontend

This repository is intentionally a backend API. A UI would not demonstrate the main learning goals better than clear API examples and tests. The focus is Java, Spring, SQL/JPA, transactions and concurrency.

## Limitations / next steps

This is a first-project scope. Authentication, database migrations, Docker and CI are intentionally not added here because those topics are demonstrated more deeply in the other portfolio projects.
