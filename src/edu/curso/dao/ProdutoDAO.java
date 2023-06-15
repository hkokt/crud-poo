package edu.curso.dao;

import java.sql.SQLException;
import java.util.List;

import edu.curso.model.Produto;

public interface ProdutoDAO {
	
	Produto adicionar(Produto prod) throws SQLException;

	void atualizar(long id, Produto prod) throws SQLException;

	void remover(long id) throws SQLException;

	List<Produto> procurarPorNome(String nome) throws SQLException;

	Produto procurarPorId(long id) throws SQLException;
}
