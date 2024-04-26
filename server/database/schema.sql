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
CREATE TABLE Product(
	id_prod				VARCHAR(255),
	name				VARCHAR(255),
	product_quantity	INTEGER,
	price				NUMERIC(8,2),
	status				VARCHAR(255),
	PRIMARY KEY (id_prod)
);
CREATE TABLE Order(
	id_order			VARCHAR(255),
	id_user				VARCHAR(255),
	id_prod				VARCHAR(255),	
	date_order			DATETIME,
	status_prod			VARCHAR(255),
	total_amount		NUMERIC(8,2),
	product_quantity	INTEGER,
	PRIMARY KEY(id_order)
);

ALTER TABLE Order
ADD CONSTRAINT fk_id_prod FOREIGN KEY (id_prod)
REFERENCES Product (id_prod);

ALTER TABLE Order
ADD CONSTRAINT fk_id_user FOREIGN KEY (id_user)
REFERENCES Users (id);

