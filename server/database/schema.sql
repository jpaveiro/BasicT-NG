DROP DATABASE IF EXISTS BasicT_database;
CREATE DATABASE BasicT_database;
USE BasicT_database;

CREATE TABLE Users(
	id 			    	VARCHAR(255),
	name 				CHAR(255),
	cellphone  	    	VARCHAR(20)     UNIQUE,
	email	 			VARCHAR(255)    UNIQUE,
	cpf 				VARCHAR(11) 	UNIQUE,
	rg 			    	VARCHAR(20),
	state_rg			VARCHAR(6),
	birth_date			DATE,
	password 	    	VARCHAR(255),
	PRIMARY KEY (id)
);

CREATE TABLE Products(
	id_product			VARCHAR(255),
	name				VARCHAR(255),
	prod_quantity		INTEGER,
	price				NUMERIC(8, 2),
	PRIMARY KEY (id_product)
);

CREATE TABLE Purchases(
	id_purchase			VARCHAR(255),
	id_user				VARCHAR(255),
	id_prod				VARCHAR(255),
    prod_quantity		INTEGER,
	purchase_date		DATETIME,
	total_amount		NUMERIC(8,2),
	PRIMARY KEY(id_purchase),
    FOREIGN KEY (id_user) REFERENCES Users(id),
    FOREIGN KEY (id_prod) REFERENCES Products(id_product)
);