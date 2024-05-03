DROP DATABASE IF EXISTS BasicT_database;
CREATE DATABASE BasicT_database;
USE BasicT_database;

CREATE TABLE users(
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

CREATE TABLE products(
  id_product VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  price NUMERIC(8, 2) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (id_product)
);

CREATE TABLE purchases(
	id_purchase	VARCHAR(255) NOT NULL,
	id_user VARCHAR(255) NOT NULL,
	id_prod VARCHAR(255) NOT NULL,
	prod_quantity	INTEGER NOT NULL,
	purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	purchase_code VARCHAR(255) NOT NULL,
	total_amount NUMERIC(8,2) NOT NULL,
	PRIMARY KEY(id_purchase),
  FOREIGN KEY (id_user) REFERENCES users(id),
  FOREIGN KEY (id_prod) REFERENCES products(id_product)
);

SELECT *
FROM users;

SELECT *
FROM products;

SELECT *
FROM purchases;