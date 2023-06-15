package edu.curso.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.curso.model.Funcionario;

public class FuncionarioDAOimpl implements FuncionarioDAO {
	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mariadb://localhost:3306/mercado";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASS = "123456";
	private Connection con;

	public FuncionarioDAOimpl() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
	}

	@Override
	public Funcionario adicionar(Funcionario fun) throws SQLException {
		String sql = "INSERT INTO funcionarios " + "(nome, cargo, cpf, email, celular) " + "VALUES (?, ?, ?, ?, ?)";
		PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

		st.setString(1, fun.getNome());
		st.setString(2, fun.getCargo());
		st.setString(3, fun.getCpf());
		st.setString(4, fun.getEmail());
		st.setString(5, fun.getCelular());
		
		st.executeUpdate();

		ResultSet rs = st.getGeneratedKeys();

		if (rs.next()) {
			long id = rs.getLong(1);
			fun.setId(id);
		}
		return fun;
	}

	@Override
	public void atualizar(long id, Funcionario fun) throws SQLException {
		String sql = "UPDATE funcionarios SET nome = ?, cargo = ?, cpf= ?, email=?, celular=? WHERE id = ?";
		PreparedStatement st = con.prepareStatement(sql);

		st.setString(1, fun.getNome());
		st.setString(2, fun.getCargo());
		st.setString(3, fun.getCpf());
		st.setString(4, fun.getEmail());
		st.setString(5, fun.getCelular());
		st.setLong(6, id);

		st.executeUpdate();

	}

	@Override
	public void remover(long id) throws SQLException {
		String sql = "DELETE FROM funcionarios WHERE id = ?";
		PreparedStatement st = con.prepareStatement(sql);

		st.setLong(1, id);

		st.executeUpdate();
	}

	@Override
	public List<Funcionario> procurarPorNome(String nome) throws SQLException {
		List<Funcionario> lista = new ArrayList<>();
		String sql = "SELECT * FROM funcionarios WHERE nome " + "LIKE ?";
		PreparedStatement st = con.prepareStatement(sql);

		st.setString(1, "%" + nome + "%");

		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			Funcionario fun = new Funcionario();

			fun.setId(rs.getLong("id"));
			fun.setNome(rs.getString("nome"));
			fun.setCargo(rs.getString("cargo"));
			fun.setCpf(rs.getString("cpf"));
			fun.setEmail(rs.getString("email"));
			fun.setCelular(rs.getString("celular"));

			lista.add(fun);
		}
		return lista;
	}

	@Override
	public Funcionario procurarPorId(long id) throws SQLException {
		String sql = "SELECT * FROM funcionarios WHERE id = ? ";
		PreparedStatement st = con.prepareStatement(sql);

		st.setLong(1, id);

		ResultSet rs = st.executeQuery();

		if (rs.next()) {
			Funcionario fun = new Funcionario();

			fun.setId(rs.getLong("id"));
			fun.setNome(rs.getString("nome"));
			fun.setCargo(rs.getString("cargo"));
			fun.setCpf(rs.getString("cpf"));
			fun.setEmail(rs.getString("email"));
			fun.setCelular(rs.getString("celular"));

			return fun;
		}

		return null;
	}

}
