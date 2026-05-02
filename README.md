# FundTracker — Accounting Management System

A full-stack financial management application for tracking invoices, receipts, payments, and reports — built with **Spring Boot + MongoDB** on the backend and **React + TypeScript** on the frontend.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Monorepo Structure](#monorepo-structure)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [1. Clone the Repository](#1-clone-the-repository)
  - [2. Backend Setup](#2-backend-setup)
  - [3. Frontend Setup](#3-frontend-setup)
- [Backend Details](#backend-details)
  - [Project Structure](#backend-project-structure)
  - [API Endpoints](#api-endpoints)
  - [Security](#security)
  - [Production Build (Backend)](#production-build-backend)
- [Frontend Details](#frontend-details)
  - [Project Structure](#frontend-project-structure)
  - [Features](#features)
  - [Environment Variables](#environment-variables)
  - [Production Build (Frontend)](#production-build-frontend)
- [Running Tests](#running-tests)
- [Contributing](#contributing)
- [License](#license)

---

## Project Overview

FundTracker is an enterprise-grade fund management system that handles:

- Export, purchase, and retail **invoices**
- Export, retail, and domestic **receipts**
- **Payments** and **debit notes**
- **Incentives** tracking (DBK, MEIS, Salexport)
- **Master data** — clients, suppliers, banks, currencies, categories
- **Dashboard** with financial summaries and charts
- **Reports** with date-range filtering
- JWT-secured **user management**

---

## Monorepo Structure

```
fundtracker/
├── backend/          # Spring Boot REST API
├── frontend/         # React TypeScript SPA
└── README.md
```

---

## Tech Stack

### Backend

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.2.0 |
| Language | Java 17 |
| Database | MongoDB |
| Security | Spring Security + JWT (jjwt 0.11.5) |
| Validation | Spring Boot Starter Validation |
| Build Tool | Maven |
| Boilerplate | Lombok |

### Frontend

| Layer | Technology |
|---|---|
| Framework | React 19 |
| Language | TypeScript 4.9 |
| UI Library | Material UI (MUI) v9 |
| Routing | React Router DOM v7 |
| HTTP Client | Axios |
| Charts | Recharts |
| Date Handling | Day.js + MUI X Date Pickers |
| Notifications | React Hot Toast |
| Build Tool | Create React App (react-scripts 5) |

---

## Prerequisites

| Tool | Minimum Version |
|---|---|
| Java | 17 |
| Maven | 3.8+ |
| MongoDB | 6.0+ |
| Node.js | 18+ |
| npm | 9+ |

---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/fundtracker.git
cd fundtracker
```

---

### 2. Backend Setup

#### Configure application properties

Create `backend/src/main/resources/application.properties`:

```properties
# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/fundtracker

# JWT
app.jwt.secret=YOUR_SECRET_KEY_HERE
app.jwt.expiration=86400000

# CORS (comma-separated allowed origins)
app.cors.allowed-origins=http://localhost:3000

# Server
server.port=8080
```

> ⚠️ Never commit secrets to version control. Use environment variables in production.

#### Run the backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

---

### 3. Frontend Setup

#### Install dependencies

```bash
cd frontend
npm install
```

#### Configure environment variables

Create `frontend/.env`:

```env
REACT_APP_API_URL=http://localhost:8080/api
```

#### Start the development server

```bash
npm start
```

The app will open at `http://localhost:3000`.

---

## Backend Details

### Backend Project Structure

```
backend/src/main/java/com/fundtracker/
├── FundTrackerApplication.java         # Entry point
├── config/
│   └── WebSecurityConfig.java          # CORS + JWT security config
├── controller/
│   ├── AuthController.java             # Login endpoint
│   ├── DashboardController.java        # Dashboard summary
│   ├── MasterController.java           # Master data (clients, banks, etc.)
│   ├── ExportInvoiceController.java
│   ├── PurchaseInvoiceController.java
│   ├── RetailInvoiceController.java
│   ├── DebitNoteController.java
│   ├── ExportReceiptController.java
│   ├── ReceiptController.java
│   ├── RetailReceiptController.java
│   ├── PaymentController.java
│   ├── IncentivesController.java
│   ├── ReportController.java
│   └── UserController.java
├── model/                              # MongoDB document models
├── repository/                         # Spring Data MongoDB repositories
├── service/                            # Business logic layer
├── dto/                                # Request/Response DTOs
├── security/                           # JWT filter, UserDetailsService
└── exception/                          # Global exception handler
```

### API Endpoints

All endpoints (except `/auth/login`) require a valid JWT token:
`Authorization: Bearer <token>`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/auth/login` | Authenticate and receive JWT |
| `GET` | `/api/dashboard` | Dashboard summary |
| `GET/POST/PUT/DELETE` | `/api/master/**` | Master data management |
| `GET/POST/PUT/DELETE` | `/api/export-invoices/**` | Export invoices |
| `GET/POST/PUT/DELETE` | `/api/purchase-invoices/**` | Purchase invoices |
| `GET/POST/PUT/DELETE` | `/api/retail-invoices/**` | Retail invoices |
| `GET/POST/PUT/DELETE` | `/api/debit-notes/**` | Debit notes |
| `GET/POST/PUT/DELETE` | `/api/receipts/**` | Receipts |
| `GET/POST/PUT/DELETE` | `/api/export-receipts/**` | Export receipts |
| `GET/POST/PUT/DELETE` | `/api/retail-receipts/**` | Retail receipts |
| `GET/POST/PUT/DELETE` | `/api/payments/**` | Payments |
| `GET/POST/PUT/DELETE` | `/api/incentives/**` | Incentives |
| `GET` | `/api/reports/**` | Financial reports |
| `GET/POST/PUT/DELETE` | `/api/users/**` | User management |

#### Authentication Example

```bash
# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "password"}'

# Use the returned token
curl http://localhost:8080/api/dashboard \
  -H "Authorization: Bearer <your-jwt-token>"
```

### Security

- Stateless JWT-based authentication (no server sessions)
- BCrypt password hashing
- Role-based access control via Spring Security
- CORS configured via `app.cors.allowed-origins`

### Production Build (Backend)

```bash
cd backend
mvn clean package -DskipTests
java -jar target/fundtracker-backend-1.0.0.jar
```

Set environment variables for production:

```bash
export SPRING_DATA_MONGODB_URI=mongodb+srv://user:pass@cluster.mongodb.net/fundtracker
export APP_JWT_SECRET=your-very-long-secret-key
export APP_CORS_ALLOWED_ORIGINS=https://your-frontend-domain.com
```

---

## Frontend Details

### Frontend Project Structure

```
frontend/src/
├── api/
│   └── index.ts                    # Axios instance and all API calls
├── components/
│   ├── common/
│   │   ├── DataTable.tsx           # Reusable data table
│   │   └── PageHeader.tsx          # Reusable page header
│   └── layout/
│       └── Layout.tsx              # App shell with sidebar navigation
├── context/
│   └── AuthContext.tsx             # JWT auth state management
├── pages/
│   ├── LoginPage.tsx
│   ├── DashboardPage.tsx
│   ├── MasterPage.tsx
│   ├── PaymentPage.tsx
│   ├── ReportPage.tsx
│   ├── UsersPage.tsx
│   ├── invoices/
│   │   ├── ExportInvoicePage.tsx
│   │   ├── PurchaseInvoicePage.tsx
│   │   ├── RetailInvoicePage.tsx
│   │   └── DebitNotePage.tsx
│   └── receipts/
│       ├── ReceiptPage.tsx
│       ├── ExportReceiptPage.tsx
│       ├── RetailReceiptPage.tsx
│       └── IncentivesPage.tsx
├── types/
│   └── index.ts                    # TypeScript interfaces and types
├── App.tsx                         # Root component with routing
└── index.tsx                       # App entry point
```

### Features

- **Authentication** — JWT login with protected routes and persistent sessions
- **Dashboard** — Financial summaries with interactive charts (Recharts)
- **Master Data** — Manage clients, suppliers, banks, currencies, and categories
- **Invoices** — Export, purchase, retail invoices and debit notes
- **Receipts** — Export, retail, and domestic receipts with incentives tracking
- **Payments** — Track and manage outgoing payments
- **Reports** — Date-range filtered financial reports
- **User Management** — Add and manage system users

### Environment Variables

| Variable | Description | Default |
|---|---|---|
| `REACT_APP_API_URL` | Base URL of the backend REST API | `http://localhost:8080/api` |

> All React environment variables must be prefixed with `REACT_APP_`.

### Available Scripts

| Command | Description |
|---|---|
| `npm start` | Start the development server |
| `npm run build` | Build for production |
| `npm test` | Run tests in watch mode |

### Production Build (Frontend)

```bash
cd frontend
npm run build
```

The output will be in the `build/` folder. Deploy to any static host (Nginx, Vercel, Netlify, S3, etc.).

#### Nginx configuration example

```nginx
server {
    listen 80;
    root /var/www/fundtracker;
    index index.html;

    location / {
        try_files $uri /index.html;
    }

    location /api {
        proxy_pass http://localhost:8080;
    }
}
```

---

## Running Tests

**Backend:**

```bash
cd backend
mvn test
```

**Frontend:**

```bash
cd frontend
npm test
```

---

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Pull Request

---

## License

This project is licensed under the MIT License.
