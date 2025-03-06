CREATE DATABASE clear_ledger;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS user_ledgers;
DROP TABLE IF EXISTS ledgers;

CREATE TABLE IF NOT EXISTS users
(
    id         CHAR(10) PRIMARY KEY,
    username   VARCHAR(50) UNIQUE  NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    email      VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ledgers
(
    id          CHAR(10) PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_ledgers
(
    user_id   CHAR(10) REFERENCES users (id),
    ledger_id CHAR(10) REFERENCES ledgers (id),
    role      VARCHAR(50) NOT NULL, -- e.g., 'owner', 'member'
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, ledger_id)
);

CREATE TABLE IF NOT EXISTS transactions
(
    id               CHAR(10) PRIMARY KEY,
    ledger_id        CHAR(10) REFERENCES ledgers (id),
    user_id          CHAR(10) REFERENCES users (id),
    amount           DECIMAL(10, 2) NOT NULL,
    description      TEXT,
    transaction_date TIMESTAMP      NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE VIEW view_transaction AS
SELECT t.id,
       t.ledger_id,
       t.user_id,
       u.username,
       t.amount,
       t.description,
       t.transaction_date,
       t.created_at
FROM transactions t
         LEFT JOIN users u on t.user_id = u.id;