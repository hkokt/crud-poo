package edu.curso.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.curso.model.Produto;

public class ProdutoDAOImpl implements ProdutoDAO {

	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mariadb://localhost:3306/mercado";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASS = "123456";
	private Connection con;

	public ProdutoDAOImpl() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
	}

	@Override
	public Produto adicionar(Produto prod) throws SQLException {
		String sql = "INSERT INTO produtos " + "(nome, preco, marca) " + "VALUES (?, ?, ?)";
		PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		System.out.println(st.toString());
		st.setString(1, prod.getNome());
		st.setDouble(2, prod.getPreco());
		st.setString(3, prod.getMarca());
		st.executeUpdate();

		ResultSet rs = st.getGeneratedKeys();

		if (rs.next()) {
			long id = rs.getLong(1);
			prod.setId(id);
		}
		return prod;
	}

	@Override
	public void atualizar(long id, Produto prod) throws SQLException {
		String sql = "UPDATE produtos SET nome = ?, " + "preco = ?, marca = ? WHERE id = ?";
		PreparedStatement st = con.prepareStatement(sql);

		st.setString(1, prod.getNome());
		st.setDouble(2, prod.getPreco());
		st.setString(3, prod.getMarca());
		st.setLong(4, id);

		st.executeUpdate();

	}

	@Override
	public void remover(long id) throws SQLException {
		String sql = "DELETE FROM produtos WHERE id = ?";
		PreparedStatement st = con.prepareStatement(sql);

		st.setLong(1, id);

		st.executeUpdate();
	}

	@Override
	public List<Produto> procurarPorNome(String nome) throws SQLException {
		List<Produto> lista = new ArrayList<>();
		String sql = "SELECT * FROM produtos WHERE nome " + "LIKE ?";
		PreparedStatement st = con.prepareStatement(sql);

		st.setString(1, "%" + nome + "%");

		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			Produto prod = new Produto();

			prod.setId(rs.getLong("id"));
			prod.setNome(rs.getString("nome"));
			prod.setPreco(rs.getDouble("preco"));
			prod.setMarca(rs.getString("marca"));

			lista.add(prod);
		}
		return lista;
	}

	@Override
	public Produto procurarPorId(long id) throws SQLException {
		String sql = "SELECT * FROM produtos WHERE id = ? ";
		PreparedStatement st = con.prepareStatement(sql);

		st.setLong(1, id);

		ResultSet rs = st.executeQuery();

		if (rs.next()) {
			Produto prod = new Produto();

			prod.setId(rs.getLong("id"));
			prod.setNome(rs.getString("nome"));
			prod.setPreco(rs.getDouble("preco"));
			prod.setMarca(rs.getString("marca"));

			return prod;
		}

		return null;
	}

}
