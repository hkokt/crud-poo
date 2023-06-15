package edu.curso.view;

import java.sql.SQLException;

import edu.curso.controller.FuncionarioController;
import edu.curso.controller.ProdutoController;
import edu.curso.model.Funcionario;
import edu.curso.model.Produto;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

public class ProdutoBoundary extends Application {

	private TextField txtNome = new TextField();
	private TextField txtPreco = new TextField();
	private TextField txtMarca = new TextField();

	private ProdutoController controlProd;
	private TableView<Produto> tvProd = new TableView<>();

	public void adicionarProd() {
		try {
			controlProd.adicionar();
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Erro ao gravar o livro," + "contate o Administrador", ButtonType.OK);
			a.showAndWait();
			e.printStackTrace();
		}
	}

	public void pesquisarProd() {
		try {
			controlProd.pesquisar();
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Erro ao pesquisar no banco de dados," + "contate o Administrador",
					ButtonType.OK);
			a.showAndWait();
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void ligacoesProdutos() {
		Bindings.bindBidirectional(txtNome.textProperty(), controlProd.nomeProperty());
		Bindings.bindBidirectional(txtPreco.textProperty(), controlProd.precoProperty(),
				(StringConverter) new DoubleStringConverter());
		Bindings.bindBidirectional(txtMarca.textProperty(), controlProd.marcaProperty());
	}

	@SuppressWarnings("unchecked")
	public void abastecerTableViewProdutos() {
		TableColumn<Produto, String> colNome = new TableColumn<>("Nome");
		colNome.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));
		TableColumn<Produto, String> colMarca = new TableColumn<>("Marca");
		colMarca.setCellValueFactory(new PropertyValueFactory<Produto, String>("marca"));
		TableColumn<Produto, Double> colPreco = new TableColumn<>("Preço");
		colPreco.setCellValueFactory(new PropertyValueFactory<Produto, Double>("preco"));

		TableColumn<Produto, Void> colAcoes = new TableColumn<>("Ações");
		Callback<TableColumn<Produto, Void>, TableCell<Produto, Void>> callBack = new Callback<>() {

			@Override
			public TableCell<Produto, Void> call(TableColumn<Produto, Void> col) {
				TableCell<Produto, Void> tCell = new TableCell<>() {

					final Button btnExcluir = new Button("Excluir");
					{
						btnExcluir.setOnAction(e -> {
							Produto prod = tvProd.getItems().get(getIndex());
							try {
								controlProd.excluir(prod);
							} catch (SQLException err) {
								Alert a = new Alert(AlertType.ERROR,
										"Erro ao excluir o registro," + "contate o Administrador", ButtonType.OK);
								a.showAndWait();
								err.printStackTrace();
							}
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btnExcluir);
						}
					}
				};
				return tCell;
			}
		};

		colAcoes.setCellFactory(callBack);

		double quarto = 800.0 / 4.0;
		colMarca.setPrefWidth(quarto);
		colPreco.setPrefWidth(quarto);
		colNome.setPrefWidth(quarto);
		colAcoes.setPrefWidth(quarto);

		tvProd.getColumns().addAll(colNome, colPreco, colMarca, colAcoes);
		tvProd.setItems(controlProd.getObsLProdutos());

		tvProd.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Produto>() {
			@Override
			public void onChanged(Change<? extends Produto> prod) {
				if (!prod.getList().isEmpty()) {
					controlProd.fromEntity(prod.getList().get(0));
				}
			}
		});
	}

	private TextField txtNomeFun = new TextField();
	private TextField txtCargo = new TextField();
	private TextField txtCpf = new TextField();
	private TextField txtEmail = new TextField();
	private TextField txtCelular = new TextField();

	private FuncionarioController controlFun;
	private TableView<Funcionario> tvFun = new TableView<>();

	public void adicionarFun() {
		try {
			controlFun.adicionar();
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Erro ao gravar o livro," + "contate o Administrador", ButtonType.OK);
			a.showAndWait();
			e.printStackTrace();
		}
	}

	public void pesquisarFun() {
		try {
			controlFun.pesquisar();
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Erro ao pesquisar no banco de dados," + "contate o Administrador",
					ButtonType.OK);
			a.showAndWait();
			e.printStackTrace();
		}
	}

	public void ligacoesFuncionarios() {
		Bindings.bindBidirectional(txtNomeFun.textProperty(), controlFun.nomeFunProperty());
		Bindings.bindBidirectional(txtCargo.textProperty(), controlFun.cargoProperty());
		Bindings.bindBidirectional(txtCpf.textProperty(), controlFun.cpfProperty());
		Bindings.bindBidirectional(txtEmail.textProperty(), controlFun.emailProperty());
		Bindings.bindBidirectional(txtCelular.textProperty(), controlFun.celularProperty());

	}

	@SuppressWarnings("unchecked")
	public void abastecerTableViewFuncionarios() {
		TableColumn<Funcionario, String> colNome = new TableColumn<>("Nome");
		colNome.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("nome"));
		TableColumn<Funcionario, String> colCargo = new TableColumn<>("Cargo");
		colCargo.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("cargo"));
		TableColumn<Funcionario, String> colCpf = new TableColumn<>("Cpf");
		colCpf.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("cpf"));
		TableColumn<Funcionario, String> colEmail = new TableColumn<>("Email");
		colEmail.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("email"));
		TableColumn<Funcionario, String> colCelular = new TableColumn<>("Celular");
		colCelular.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("celular"));

		TableColumn<Funcionario, Void> colAcoes = new TableColumn<>("Ações");
		Callback<TableColumn<Funcionario, Void>, TableCell<Funcionario, Void>> callBack = new Callback<>() {

			@Override
			public TableCell<Funcionario, Void> call(TableColumn<Funcionario, Void> col) {
				TableCell<Funcionario, Void> tCell = new TableCell<>() {

					final Button btnExcluir = new Button("Excluir");
					{
						btnExcluir.setOnAction(e -> {
							Funcionario fun = tvFun.getItems().get(getIndex());
							try {
								controlFun.excluir(fun);
							} catch (SQLException err) {
								Alert a = new Alert(AlertType.ERROR,
										"Erro ao excluir o registro," + "contate o Administrador", ButtonType.OK);
								a.showAndWait();
								err.printStackTrace();
							}
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btnExcluir);
						}
					}
				};
				return tCell;
			}
		};

		colAcoes.setCellFactory(callBack);

		double quarto = 800.0 / 6.0;
		colNome.setPrefWidth(quarto);
		colCargo.setPrefWidth(quarto);
		colCpf.setPrefWidth(quarto);
		colEmail.setPrefWidth(quarto);
		colCelular.setPrefWidth(quarto);
		colAcoes.setPrefWidth(quarto);

		tvFun.getColumns().addAll(colNome, colCargo, colCpf, colEmail, colCelular, colAcoes);
		tvFun.setItems(controlFun.getObsFun());

		tvFun.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Funcionario>() {
			@Override
			public void onChanged(Change<? extends Funcionario> fun) {
				if (!fun.getList().isEmpty()) {
					controlFun.fromEntity(fun.getList().get(0));
				}
			}
		});
	}

	@Override
	public void start(Stage stage) throws Exception {

		try {
			controlProd = new ProdutoController();
			controlFun = new FuncionarioController();
		} catch (SQLException | ClassNotFoundException e) {
			Alert a = new Alert(AlertType.ERROR, "Erro ao acessar o banco de dados," + "contate o Administrador",
					ButtonType.OK);
			a.showAndWait();
			e.printStackTrace();
		}

		BorderPane tabFun = new BorderPane();

		GridPane gridFun = new GridPane();
		tabFun.setTop(gridFun);
		tabFun.setCenter(tvFun);
		gridFun.add(new Label("Nome: "), 0, 0);
		gridFun.add(txtNomeFun, 1, 0);
		gridFun.add(new Label("Cargo: "), 0, 1);
		gridFun.add(txtCargo, 1, 1);
		gridFun.add(new Label("Cpf: "), 0, 2);
		gridFun.add(txtCpf, 1, 2);
		gridFun.add(new Label("Email: "), 0, 3);
		gridFun.add(txtEmail, 1, 3);
		gridFun.add(new Label("Celular: "), 0, 4);
		gridFun.add(txtCelular, 1, 4);

		ligacoesFuncionarios();
		abastecerTableViewFuncionarios();

		Button btnNovoFun = new Button("Novo");
		btnNovoFun.setOnAction(e -> controlFun.novo());

		Button btnSalvarFun = new Button("Salvar");
		btnSalvarFun.setOnAction(e -> adicionarFun());

		Button btnPesquisarFun = new Button("Pesquisar");
		btnPesquisarFun.setOnAction(e -> pesquisarFun());

		FlowPane painelBotoesFun = new FlowPane();
		painelBotoesFun.getChildren().addAll(btnSalvarFun, btnPesquisarFun);

		gridFun.add(btnNovoFun, 0, 5);
		gridFun.add(painelBotoesFun, 1, 5);

		BorderPane tabProd = new BorderPane();

		GridPane grid = new GridPane();
		tabProd.setTop(grid);
		tabProd.setCenter(tvProd);
		grid.add(new Label("Nome: "), 0, 0);
		grid.add(txtNome, 1, 0);
		grid.add(new Label("Preço: "), 0, 1);
		grid.add(txtPreco, 1, 1);
		grid.add(new Label("Marca: "), 0, 2);
		grid.add(txtMarca, 1, 2);

		ligacoesProdutos();
		abastecerTableViewProdutos();

		Button btnNovo = new Button("Novo");
		btnNovo.setOnAction(e -> controlProd.novo());

		Button btnSalvar = new Button("Salvar");
		btnSalvar.setOnAction(e -> adicionarProd());

		Button btnPesquisar = new Button("Pesquisar");
		btnPesquisar.setOnAction(e -> pesquisarProd());

		FlowPane painelBotoes = new FlowPane();
		painelBotoes.getChildren().addAll(btnSalvar, btnPesquisar);

		grid.add(btnNovo, 0, 3);
		grid.add(painelBotoes, 1, 3);

		TabPane tabPane = new TabPane();

		Tab tab1 = new Tab();
		tab1.setText("Produtos");
		tab1.setContent(tabProd);

		Tab tab2 = new Tab();
		tab2.setText("Funcionários");
		tab2.setContent(tabFun);

		tabPane.getTabs().addAll(tab1, tab2);

		StackPane root = new StackPane();
		root.getChildren().add(tabPane);
		Scene scene = new Scene(root, 800, 600);

		stage.setScene(scene);
		stage.setTitle("Mercadin Brabo");
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}