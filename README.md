# Introduction

**Clear Ledger** is a home accounting software designed to simplify financial management for households. By providing an intuitive platform to record income and expenses, categorise transactions, and generate insightful reports, **Clear Ledger** aims to empower users to make informed financial decisions. Whether it’s tracking daily spending, setting monthly budgets, or planning for future goals, **Clear Ledger** is here to bring clarity and order to your financial life.

# Features

## User Registration and Login

- **User Registration**: New users can create an account by providing essential details such as their name, email address, and password.

- **User Login**: Registered users can securely log in to their accounts using their credentials.

## Ledger Management

- **Create a Ledger**: Users can create a new ledger to track their income and expenses. Each ledger represents a separate financial record, such as a personal account or a shared family budget.
- **Edit a Ledger**: Users can modify the details of an existing ledger, such as its name or description.
- **Delete a Ledger**: If a ledger is no longer needed, users can delete it to keep their account organised.
- **Limitation**: Each user can create or join up to three ledgers to ensure optimal performance and usability.

## Income and Expense Tracking

- **Record Income**: Users can add income entries, specifying the amount, source, and date.
- **Record Expenses**: Users can log their expenses, including details such as the amount, category, and date.
- **Transaction History**: All income and expense records are displayed in a clear and organised transaction history for easy reference.

### Upcoming Features (Under Development)

- **Category Management**: Users will be able to create and manage custom categories (e.g., groceries, transportation, entertainment) for better organisation of their transactions.
- **Data Statistics and Reports**: Comprehensive financial reports and visual statistics (e.g., charts and graphs) will be available to help users analyse their spending patterns and financial health.
- **Budgeting Tool**: A budgeting feature will allow users to set monthly or yearly budgets for specific categories and receive notifications when they are close to exceeding their limits.
- **Data Export (Excel)**: Users will have the option to export their financial data, including income, expenses, and reports, to Excel for offline analysis or record-keeping.

# Quick Start

## Prerequisites

- `Docker`
- `Docker Compose`
- `Node.js`: version 20
- `pnpm`: version 10
- `JDK`: Amazon Corretto 21

## Installation

### Deployment with Docker (Recommended)

Follow these steps to deploy Clear Ledger using Docker:

#### Step 1: Set Up the PostgreSQL Database

1. Create a PostgreSQL database for Clear Ledger:

   - Host/IP: The hostname or IP address of your PostgreSQL server.
   - Port: The port number exposed by PostgreSQL (default is `5432`).
   - Username: The username for accessing the database.
   - Password: The password for the database user.

2. Create a database named `clear_ledger`:

   ```sql
   CREATE DATABASE clear_ledger;
   ```

3. Execute the SQL script to create the database tables:

   - Locate the SQL file `clear_ledger_database/01-structure.sql` in the project directory.

   - Run the script in the `clear_ledger` database:

     ```shell
     psql -h <host> -p <port> -U <username> -d clear_ledger -f clear_ledger_database/01-structure.sql
     ```

#### Step 2: Set Up the Redis Environment

1. Prepare a Redis instance:
   - Host/IP: The hostname or IP address of your Redis server.
   - Port: The port number exposed by Redis (default is `6379`).
   - Password: The password for accessing Redis (if applicable).
2. Choose a database index (0-15) for storing Clear Ledger's cache information.

#### Step 3: Create the `docker-compose.yml` File

Create a `docker-compose.yml` file with the following content (adjust the placeholders as needed):

```yaml
services:
  server:
    image: ghcr.io/onixbyte/clear-ledger/clear-ledger-server:latest
    environment:
      - ACTIVE_PROFILES=<your_customised_profiles_names>
      - TZ=<your_timezone>
    ports:
      - "8080:8080"
    volumes:
      - ./config:/app/config

  web:
    image: caddy:2.9-alpine
    ports:
      - "80:80"
    volumes:
      - ./conf:/etc/caddy
      - ./dist:/usr/share/caddy
```

#### Step 4: Create Required Directories

In the same directory as the `docker-compose.yml` file, create the following folders:

- `config`: For storing backend configuration files.
- `conf`: For storing frontend configuration files (e.g., Caddy configuration).
- `dist`: For storing the compiled frontend static files.

> **Note:** We provided some server configuration examples in `clear-ledger-server/config`, remove the `.default` suffix and fill in the values as required.

#### Step 5: Run the System

Start the Clear Ledger system using Docker Compose:

```shell
docker compose up -d
```

Once the containers are running, you can access the application at `http://localhost`.

# Technology Stack

## Backend

- **Spring Boot**: The core framework for building the backend application, providing a robust and scalable foundation for handling business logic and API endpoints.
- **Spring Security**: Used for authentication and authorisation, ensuring secure access to the application’s resources.
- **JWT (JSON Web Tokens)**: Implements stateless authentication for secure user sessions and API requests.

## Database

- **PostgreSQL 16**: The primary relational database for storing structured data, such as user information, ledgers, and transaction records.
- **Redis**: Used for caching frequently accessed data and managing session states to improve performance and scalability.

## Frontend

- **React**: A powerful JavaScript library for building dynamic and responsive user interfaces.
- **TypeScript**: Enhances code quality and developer productivity by adding static typing to JavaScript.
- **Redux**: Manages the application’s global state, ensuring consistent data flow across components.
- **React Router**: Handles client-side routing, enabling seamless navigation within the application.
- **Ant Design UI**: A comprehensive UI library that provides pre-built, customizable components for a polished and professional user interface.

## Development Tools

- **Gradle (Kotlin DSL)**: The build automation tool used for managing dependencies, compiling code, and packaging the application.
- **pnpm**: A fast and disk-space-efficient package manager for installing and managing frontend dependencies.
- **Vite**: A modern build tool that provides fast development server startup and hot module replacement (HMR) for an efficient development experience.

# Contribution Guidelines

We welcome contributions from the community! If you'd like to contribute to Clear Ledger, please follow these steps:

## How to Contribute

1. **Fork the Repository**: Fork the project repository to your GitHub account.

2. **Clone the Repository**: Clone your forked repository to your local machine.

3. **Create a Branch**: Create a new branch for your feature or bug fix:

   ```
   git checkout -b feature/your-feature-name
   ```

4. **Make Changes**: Implement your changes and ensure they follow the project's coding standards.

5. **Test Your Changes**: Run the existing tests and add new tests if necessary.

6. **Commit Your Changes**: Write clear and concise commit messages:

   ```shell
   git commit -m "<commit type>(scope): your commit message here"
   ```

7. **Push Your Changes**: Push your changes to your forked repository:

   ```shell
   git push origin feature/your-feature-name
   ```

8. **Create a Pull Request**: Open a pull request (PR) against the main repository. Provide a detailed description of your changes.

## Code Style

- Follow the existing code style and naming conventions.
- Ensure your code is well-documented with clear comments where necessary.

## Reporting Issues

If you encounter any issues or have suggestions for improvements, please open an issue on the GitHub repository. Include the following details:

- A clear description of the issue.
- Steps to reproduce the issue.
- Expected and actual behaviour.
- Screenshots or logs (if applicable).

------

# License

Clear Ledger is released under the **Apache License**. This means you are free to use, modify, and distribute the software, provided that the original license terms are included.

------

# Acknowledgements

We would like to express our gratitude to the following individuals, projects, and communities for their contributions and support:

- **Spring Boot**: For providing a robust framework for building the backend.
- **PostgreSQL**: For offering a reliable and scalable database solution.
- **React and Ant Design**: For enabling the creation of a modern and responsive user interface.
- **Open Source Community**: For inspiring us to build and share this project.

Special thanks to all contributors and users who have provided feedback and suggestions to improve Clear Ledger.

------

# Contact Information

If you have any questions, suggestions, or need assistance, please feel free to reach out:

- **Email**: [opensource@onixbyte.com](mailto:opensource@onixbyte.com)
- **GitHub Issues**: [Open an Issue](https://github.com/onixbyte/clear-ledger/issues)
- **Project Repository**: [Clear Ledger on GitHub](https://github.com/onixbyte/clear-ledger)

We appreciate your interest in **Clear Ledger** and look forward to hearing from you!
