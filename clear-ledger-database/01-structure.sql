create database clear_ledger;

CREATE TABLE users
(
    id         BIGINT PRIMARY KEY,
    username   VARCHAR(50) UNIQUE  NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    email      VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ledgers
(
    id          BIGINT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_ledgers
(
    user_id   BIGINT REFERENCES users (id),
    ledger_id BIGINT REFERENCES ledgers (id),
    role      VARCHAR(50) NOT NULL, -- e.g., 'owner', 'member'
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, ledger_id)
);

CREATE TABLE transactions
(
    id               BIGINT PRIMARY KEY,
    ledger_id        BIGINT REFERENCES ledgers (id),
    user_id          BIGINT REFERENCES users (id),
    amount           DECIMAL(10, 2) NOT NULL,
    description      TEXT,
    transaction_date TIMESTAMP      NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);