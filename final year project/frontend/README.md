# FundTracker Frontend

The frontend for the FundTracker accounting management system, built with **React 19** and **TypeScript**, featuring a rich UI powered by **Material UI**.

---

## Tech Stack

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

## Project Structure

```
frontend/
├── public/
│   ├── index.html
│   └── manifest.json
├── src/
│   ├── api/
│   │   └── index.ts              # Axios instance and all API calls
│   ├── components/
│   │   ├── common/
│   │   │   ├── DataTable.tsx     # Reusable data table component
│   │   │   └── PageHeader.tsx    # Reusable page header
│   │   └── layout/
│   │       └── Layout.tsx        # App shell with sidebar navigation
│   ├── context/
│   │   └── AuthContext.tsx       # JWT auth state management
│   ├── pages/
│   │   ├── LoginPage.tsx
│   │   ├── DashboardPage.tsx
│   │   ├── MasterPage.tsx
│   │   ├── PaymentPage.tsx
│   │   ├── ReportPage.tsx
│   │   ├── UsersPage.tsx
│   │   ├── invoices/
│   │   │   ├── ExportInvoicePage.tsx
│   │   │   ├── PurchaseInvoicePage.tsx
│   │   │   ├── RetailInvoicePage.tsx
│   │   │   └── DebitNotePage.tsx
│   │   └── receipts/
│   │       ├── ReceiptPage.tsx
│   │       ├── ExportReceiptPage.tsx
│   │       ├── RetailReceiptPage.tsx
│   │       └── IncentivesPage.tsx
│   ├── types/
│   │   └── index.ts              # TypeScript interfaces and types
│   ├── App.tsx                   # Root component with routing
│   ├── index.tsx                 # App entry point
│   └── index.css
├── .env
├── package.json
└── tsconfig.json
```

---

## Prerequisites

- Node.js 18 or higher
- npm 9+ or yarn

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/your-username/fundtracker.git
cd fundtracker/frontend
```

### 2. Install dependencies

```bash
npm install
```

### 3. Configure environment variables

Create a `.env` file in the `frontend/` root directory:

```env
REACT_APP_API_URL=http://localhost:8080/api
```

> For production, create a `.env.production` file pointing to your deployed backend URL.

### 4. Start the development server

```bash
npm start
```

The app will open at `http://localhost:3000`.

---

## Available Scripts

| Command | Description |
|---|---|
| `npm start` | Start the development server |
| `npm run build` | Build the app for production |
| `npm test` | Run tests in watch mode |
| `npm run eject` | Eject from Create React App (irreversible) |

---

## Features

- **Authentication** — JWT-based login with protected routes and persistent sessions via `AuthContext`
- **Dashboard** — Financial summary with charts (Recharts) showing income, expenses, and balances
- **Master Data** — Manage clients, suppliers, banks, currencies, categories, and more
- **Invoices** — Export invoices, purchase invoices, retail invoices, and debit notes
- **Receipts** — Export receipts, retail receipts, domestic receipts, and incentives
- **Payments** — Track and manage outgoing payments
- **Reports** — Date-range filtered financial reports
- **User Management** — Add and manage system users

---

## Environment Variables

| Variable | Description | Default |
|---|---|---|
| `REACT_APP_API_URL` | Base URL of the backend REST API | `http://localhost:8080/api` |

> All environment variables must be prefixed with `REACT_APP_` to be accessible in the app.

---

## Building for Production

```bash
npm run build
```

The optimized production build will be output to the `build/` folder. Deploy this folder to any static hosting service (Nginx, Apache, Vercel, Netlify, S3, etc.).

### Nginx configuration example

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

```bash
npm test
```

Uses React Testing Library with Jest for component and integration tests.

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
