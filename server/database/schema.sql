DROP DATABASE IF EXISTS BasicT_database;
CREATE DATABASE BasicT_database;
USE BasicT_database;

CREATE TABLE Users(
	id 			    VARCHAR(255),
	name 			CHAR(255),
	cellphone  	    VARCHAR(20)     UNIQUE,
	email	 		VARCHAR(255)    UNIQUE,
	cpf 			VARCHAR(11) 	UNIQUE,
	rg 			    VARCHAR(20),
	state_rg		VARCHAR(6),
	birth_date		DATE,
	password 	    VARCHAR(255),
	PRIMARY KEY (id)
);
