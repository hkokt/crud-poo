package edu.curso.view;

import java.sql.SQLException;

import edu.curso.controller.ProdutoController;
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

	private ProdutoController control;
	private TableView<Produto> tv = new TableView<>();

	public void adicionar() {
		try {
			control.adicionar();
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Erro ao gravar o livro," + "contate o Administrador", ButtonType.OK);
			a.showAndWait();
			e.printStackTrace();
		}
	}

	public void pesquisar() {
		try {
			control.pesquisar();
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Erro ao pesquisar no banco de dados," + "contate o Administrador",
					ButtonType.OK);
			a.showAndWait();
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void ligacoes() {
		Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
		Bindings.bindBidirectional(txtPreco.textProperty(), control.precoProperty(),
				(StringConverter) new DoubleStringConverter());
		Bindings.bindBidirectional(txtMarca.textProperty(), control.marcaProperty());
	}

	@SuppressWarnings("unchecked")
	public void abastecerTableView() {
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
							Produto prod = tv.getItems().get(getIndex());
							try {
								control.excluir(prod);
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

		tv.getColumns().addAll(colNome, colPreco, colMarca, colAcoes);
		tv.setItems(control.getObsLProdutos());

		tv.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Produto>() {

			@Override
			public void onChanged(Change<? extends Produto> prod) {
				if (!prod.getList().isEmpty()) {
					control.fromEntity(prod.getList().get(0));
				}
			}
		});
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			control = new ProdutoController();
		} catch (SQLException | ClassNotFoundException e) {
			Alert a = new Alert(AlertType.ERROR, "Erro ao acessar o banco de dados," + "contate o Administrador",
					ButtonType.OK);
			a.showAndWait();
			e.printStackTrace();
		}

		TabPane tabPane = new TabPane();

		GridPane grid = new GridPane();
		grid.add(new Label("Nome: "), 0, 0);
		grid.add(txtNome, 1, 0);
		grid.add(new Label("Preço: "), 0, 1);
		grid.add(txtPreco, 1, 1);
		grid.add(new Label("Marca: "), 0, 2);
		grid.add(txtMarca, 1, 2);

		ligacoes();
		abastecerTableView();

		Button btnNovo = new Button("Novo");
		btnNovo.setOnAction(e -> control.novo());

		Button btnSalvar = new Button("Salvar");
		btnSalvar.setOnAction(e -> adicionar());

		Button btnPesquisar = new Button("Pesquisar");
		btnPesquisar.setOnAction(e -> pesquisar());

		FlowPane painelBotoes = new FlowPane();
		painelBotoes.getChildren().addAll(btnSalvar, btnPesquisar);

		grid.add(btnNovo, 0, 3);
		grid.add(painelBotoes, 1, 3);

		Tab tab1 = new Tab();
		tab1.setText("produtos");
		tab1.setContent(grid); 

		Tab tab2 = new Tab();
		tab2.setText("funcionarios");
		tab2.setContent(new StackPane()); 

		tabPane.getTabs().addAll(tab1, tab2);

		StackPane root = new StackPane();
		root.getChildren().add(tabPane);

		Scene scn = new Scene(root, 800, 600);
		stage.setScene(scn);
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
