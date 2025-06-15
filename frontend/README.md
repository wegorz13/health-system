# Health System Frontend

A modern healthcare appointment booking system built with React, TypeScript, and Material UI.

## Quick Start

```bash
# Install dependencies
npm install

# Run in development mode (uses real API)
npm run dev

# Run with mock data (no backend needed)
npm run dev-mock
```

## Project Structure

```
src/
├── components/         # Reusable UI components
│   ├── appointment/    # Appointment-related components
│   │── find_doctor/    # Components for finding doctor based on search
│   └── ui/             # Generic UI components
├── hooks/              # Custom React hooks
├── pages/              # Application pages/routes
├── services/           # API and external services
├── types/              # TypeScript type definitions
└── theme/              # Theme of app
```

## Data Source Modes

This application supports two data modes:

- **API Mode**: Connects to the backend API (default)
- **Mock Mode**: Uses predefined data for UI development

## Tech Stack

- React 18 with TypeScript
- Material UI for component design
- React Router for navigation
- Vite for fast development and building
- Redux Toolkit for state management
- React Query for data fetching
