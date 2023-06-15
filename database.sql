create database mercado;

use mercado;

CREATE TABLE produtos (
	id int PRIMARY KEY AUTO_INCREMENT,
	nome varchar(100),
	preco float,
	marca varchar(100)
);

CREATE TABLE funcionarios(
	id int PRIMARY KEY AUTO_INCREMENT,
	nome varchar(100),
	cargo varchar(40),
	cpf char(11),
	email varchar(100),
	celular char(11)
);

