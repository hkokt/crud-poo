package edu.curso.dao;

import java.sql.SQLException;
import java.util.List;

import edu.curso.model.Funcionario;

public interface FuncionarioDAO {

	Funcionario adicionar(Funcionario fun) throws SQLException;

	void atualizar(long id, Funcionario fun) throws SQLException;

	void remover(long id) throws SQLException;

	List<Funcionario> procurarPorNome(String nome) throws SQLException;

	Funcionario procurarPorId(long id) throws SQLException;
}
