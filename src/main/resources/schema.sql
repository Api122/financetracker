CREATE TABLE accounts (
                          account_id VARCHAR(255) PRIMARY KEY,
                          currency   VARCHAR(3)   NOT NULL
);

CREATE TABLE transactions (
                              id         BIGINT AUTO_INCREMENT PRIMARY KEY,
                              account_id VARCHAR(255) NOT NULL,
                              type       VARCHAR(20)  NOT NULL,
                              amount     DECIMAL(19,2) NOT NULL,
                              category   VARCHAR(255) NOT NULL,
                              occured_at TIMESTAMP    NOT NULL,
                              currency   VARCHAR(3)   NOT NULL,
                              FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);