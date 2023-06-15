package edu.curso.controller;

import java.sql.SQLException;
import java.util.List;

import edu.curso.dao.FuncionarioDAO;
import edu.curso.dao.FuncionarioDAOimpl;
import edu.curso.model.Funcionario;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FuncionarioController {
	private LongProperty id = new SimpleLongProperty();
	private StringProperty nomeFun = new SimpleStringProperty();
	private StringProperty cargo = new SimpleStringProperty();
	private StringProperty cpf = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	private StringProperty celular = new SimpleStringProperty();

	private ObservableList<Funcionario> obsListFun = FXCollections.observableArrayList();
	private FuncionarioDAO funDAO;

	public FuncionarioController() throws ClassNotFoundException, SQLException {
		funDAO = new FuncionarioDAOimpl();
	}

	public void novo() {
		id.set(0);
		nomeFun.set("");
		cargo.set("");
		cpf.set("");
		email.set("");
		celular.set("");
	}

	public void excluir(Funcionario fun) throws SQLException {
		obsListFun.remove(fun);
		funDAO.remover(fun.getId());
	}

	public void fromEntity(Funcionario fun) {
		id.set(fun.getId());
		nomeFun.set(fun.getNome());
		cargo.set(fun.getCargo());
		cpf.set(fun.getCpf());
		email.set(fun.getEmail());
		celular.set(fun.getCelular());
	}

	public void adicionar() throws SQLException {

		if (id.get() == 0) {
			Funcionario fun = new Funcionario();

			fun.setId(id.get());
			fun.setNome(nomeFun.get());
			fun.setCargo(cargo.get());
			fun.setCpf(cpf.get());
			fun.setEmail(email.get());
			fun.setCelular(celular.get());

			fun = funDAO.adicionar(fun);

			obsListFun.add(fun);
		} else {
			Funcionario fun = new Funcionario();

			fun.setId(id.get());
			fun.setNome(nomeFun.get());
			fun.setCargo(cargo.get());
			fun.setCpf(cpf.get());
			fun.setEmail(email.get());
			fun.setCelular(celular.get());

			for (int i = 0; i < obsListFun.size(); i++) {
				Funcionario funcionario = obsListFun.get(i);
				if (funcionario.getId() == id.get()) {
					obsListFun.set(i, fun);
				}
			}
			System.out.println("teste else " + fun.getEmail());
			funDAO.atualizar(id.get(), fun);
		}

	}

	public void pesquisar() throws SQLException {
		obsListFun.clear();
		List<Funcionario> lista = funDAO.procurarPorNome(nomeFun.get());
		obsListFun.addAll(lista);
	}

	public StringProperty nomeFunProperty() {
		return nomeFun;
	}

	public StringProperty cargoProperty() {
		return cargo;
	}

	public StringProperty cpfProperty() {
		return cpf;
	}

	public StringProperty emailProperty() {
		return email;
	}

	public StringProperty celularProperty() {
		return celular;
	}

	public ObservableList<Funcionario> getObsFun() {
		return obsListFun;
	}

}
