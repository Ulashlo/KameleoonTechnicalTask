CREATE TABLE IF NOT EXISTS users
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(50)  NOT NULL UNIQUE,
    email            VARCHAR(100) NOT NULL UNIQUE,
    password         VARCHAR(100) NOT NULL,
    date_of_creation TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS quote
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    content             VARCHAR(255) NOT NULL,
    user_who_created_id BIGINT       NOT NULL,
    date_of_creation    TIMESTAMP    NOT NULL,
    date_of_last_update TIMESTAMP,
    score               INT          NOT NULL DEFAULT 0,
    FOREIGN KEY (user_who_created_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS vote
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    type                VARCHAR(20) NOT NULL,
    user_who_created_id BIGINT      NOT NULL,
    quote_id            BIGINT      NOT NULL,
    date_of_voting      TIMESTAMP   NOT NULL,
    FOREIGN KEY (user_who_created_id) REFERENCES users (id),
    FOREIGN KEY (quote_id) REFERENCES quote (id) ON DELETE CASCADE
);