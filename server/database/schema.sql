DROP DATABASE IF EXISTS BasicT_database;
CREATE DATABASE BasicT_database;
USE BasicT_database;

CREATE TABLE Users(
	id VARCHAR(255) NOT NULL,
	name CHAR(255) NOT NULL,
	cellphone VARCHAR(20) UNIQUE NOT NULL,
	email	VARCHAR(255) UNIQUE NOT NULL,
	cpf VARCHAR(11) UNIQUE NOT NULL,
	rg VARCHAR(20) NOT NULL,
	state_rg VARCHAR(6) NOT NULL,
	birth_date DATE NOT NULL,
	password VARCHAR(255) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE Products(
    id_product VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    prod_quantity INTEGER NOT NULL,
    price NUMERIC(8, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_product)
);

CREATE TABLE Purchases(
	id_purchase	VARCHAR(255) NOT NULL,
	id_user VARCHAR(255) NOT NULL,
	id_prod VARCHAR(255) NOT NULL,
  prod_quantity	INTEGER NOT NULL,
	purchase_date	DATETIME NOT NULL,
	total_amount NUMERIC(8,2) NOT NULL,
	PRIMARY KEY(id_purchase),
  FOREIGN KEY (id_user) REFERENCES Users(id),
  FOREIGN KEY (id_prod) REFERENCES Products(id_product)
);


-- The password is a SHA256 hash for 'admin'
INSERT INTO Users (id, name, cellphone, email, cpf, rg, state_rg, birth_date, password) VALUES
	('1', 'ADMIN', '00000000000', 'admin', '00000000000', '000000000', 'XXX/XX', '0000-00-00', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918');
    
SELECT * 
FROM Users;