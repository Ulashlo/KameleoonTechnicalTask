CREATE TABLE IF NOT EXISTS users
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(50)  NOT NULL UNIQUE,
    email            VARCHAR(100) NOT NULL UNIQUE,
    password         VARCHAR(100) NOT NULL,
    date_of_creation TIMESTAMP    NOT NULL
);