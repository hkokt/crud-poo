create database mercado;

use mercado;

CREATE TABLE produtos (
	id int PRIMARY KEY AUTO_INCREMENT,
	nome varchar(100),
	preco float,
	marca varchar(100)
);

