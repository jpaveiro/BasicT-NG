DROP DATABASE IF EXISTS BasicT_database;
CREATE DATABASE BasicT_database;
USE BasicT_database;

CREATE TABLE Users(
	id 			VARCHAR(255), 
	name 			CHAR(255),
	cellphone  	VARCHAR(20),
	email	 		VARCHAR(255),
	cpf 			VARCHAR(11),
	rg 			VARCHAR(20),
	password 	VARCHAR(255),
	PRIMARY KEY (id)
);