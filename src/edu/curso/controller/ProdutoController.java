package edu.curso.controller;

import java.sql.SQLException;
import java.util.List;

import edu.curso.dao.ProdutoDAO;
import edu.curso.dao.ProdutoDAOImpl;
import edu.curso.model.Produto;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProdutoController {

	private LongProperty id = new SimpleLongProperty();
	private StringProperty nome = new SimpleStringProperty();
	private DoubleProperty preco = new SimpleDoubleProperty();
	private StringProperty marca = new SimpleStringProperty();

	private ObservableList<Produto> obsListProd = FXCollections.observableArrayList();
	private ProdutoDAO prodDAO;

	public ProdutoController() throws ClassNotFoundException, SQLException {
		prodDAO = new ProdutoDAOImpl();
	}

	public void novo() {
		id.set(0);
		nome.set("");
		preco.set(0);
		marca.set("");
	}

	public void excluir(Produto prod) throws SQLException {
		obsListProd.remove(prod);
		prodDAO.remover(prod.getId());
	}

	public void fromEntity(Produto prod) {
		id.set(prod.getId());
		nome.set(prod.getNome());
		preco.set(prod.getPreco());
		marca.set(prod.getMarca());
	}

	public void adicionar() throws SQLException {

		if (id.get() == 0) {
			Produto prod = new Produto();

			prod.setId(id.get());
			prod.setNome(nome.get());
			prod.setPreco(preco.get());
			prod.setMarca(marca.get());
			
			prod = prodDAO.adicionar(prod);
			
			obsListProd.add(prod);
		} else {
			Produto prod = new Produto();

			prod.setId(id.get());
			prod.setNome(nome.get());
			prod.setPreco(preco.get());
			prod.setMarca(marca.get());

			for (int i = 0; i < obsListProd.size(); i++) {
				Produto produto = obsListProd.get(i);
				if (produto.getId() == id.get()) {
					obsListProd.set(i, prod);
				}
			}
			prodDAO.atualizar(id.get(), prod);
		}

	}

	public void pesquisar() throws SQLException {
		obsListProd.clear();
		List<Produto> lista = prodDAO.procurarPorNome(nome.get());
		obsListProd.addAll(lista);
	}

	public StringProperty nomeProperty() {
		return nome;
	}

	public DoubleProperty precoProperty() {
		return preco;
	}

	public StringProperty marcaProperty() {
		return marca;
	}

	public ObservableList<Produto> getObsLProdutos() {
		return obsListProd;
	}
}