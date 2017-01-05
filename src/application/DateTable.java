package application;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class DateTable extends TableView<Data> {
	private List<Node> cells;
	private List<Label> labels;
	private List<TextField> textfields;
	
	public DateTable() {
		super();
		TableColumn<Data, String> colunmn1 = new TableColumn<>("ID");
		TableColumn<Data, String> colunmn2 = new TableColumn<>("Data");
		TableColumn<Data, String> colunmn3 = new TableColumn<>("Typ");
		TableColumn<Data, String> colunmn4 = new TableColumn<>("Rodzaj");
		TableColumn<Data, String> colunmn5 = new TableColumn<>("Artyku³");
		TableColumn<Data, String> colunmn6 = new TableColumn<>("Podmiot");
		TableColumn<Data, String> colunmn7 = new TableColumn<>("Ulica");
		TableColumn<Data, String> colunmn8 = new TableColumn<>("Nr Budynku");
		TableColumn<Data, String> colunmn9 = new TableColumn<>("Miasto");
		TableColumn<Data, String> colunmn10 = new TableColumn<>("Kod Pocztowy");
		TableColumn<Data, String> colunmn11 = new TableColumn<>("Kwota");
		TableColumn<Data, String> colunmn12 = new TableColumn<>("Uwagi");
		TableColumn<Data, String> colunmn13 = new TableColumn<>("Saldo");
		colunmn1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Data, String> param) {
				return new SimpleStringProperty(param.getValue().getMyStrings(0));
			}
		});
		getColumns().addAll(colunmn1,colunmn2,colunmn3,colunmn4,colunmn5,colunmn6,colunmn7,colunmn8,
				colunmn9,colunmn10,colunmn11,colunmn12,colunmn13);
		colunmn1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Data, String> param) {
				return new SimpleStringProperty(param.getValue().getMyStrings(0));
			}
		});
		colunmn2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Data, String> param) {
				return new SimpleStringProperty(param.getValue().getMyStrings(1));
			}
		});
		colunmn3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Data, String> param) {
				return new SimpleStringProperty(param.getValue().getMyStrings(2));
			}
		});
		colunmn4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data,String>, ObservableValue<String>>() {
		
			@Override
			public ObservableValue<String> call(CellDataFeatures<Data, String> param) {
				return new SimpleStringProperty(param.getValue().getMyStrings(3));
			}
		});
		colunmn5.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Data, String> param) {
				return new SimpleStringProperty(param.getValue().getMyStrings(4));
			}
		});
		colunmn6.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Data, String> param) {
				return new SimpleStringProperty(param.getValue().getMyStrings(5));
			}
		});
		colunmn7.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Data, String> param) {
				return new SimpleStringProperty(param.getValue().getMyStrings(6));
			}
		});
		colunmn8.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Data, String> param) {
				return new SimpleStringProperty(param.getValue().getMyStrings(7));
			}
		});
		colunmn9.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data,String>, ObservableValue<String>>() {
					
					@Override
					public ObservableValue<String> call(CellDataFeatures<Data, String> param) {
						return new SimpleStringProperty(param.getValue().getMyStrings(8));
					}
				});
		colunmn10.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Data, String> param) {
				return new SimpleStringProperty(param.getValue().getMyStrings(9));
			}
		});
		colunmn11.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Data, String> param) {
				return new SimpleStringProperty(param.getValue().getMyStrings(10));
			}
		});
		colunmn12.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Data, String> param) {
				return new SimpleStringProperty(param.getValue().getMyStrings(11));
			}
		});
		colunmn12.setPrefWidth(50);
		colunmn13.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Data, String> param) {
				return new SimpleStringProperty(param.getValue().getMyStrings(12));
			}
		});
		setEditable(true);
	}
//	public DateForTuple() {
//		cells = new ArrayList<Node>();
//		labels = new ArrayList<Label>();
//		textfields = new ArrayList<TextField>();
//	}
//	public void update(){
//		for(Node child : getChildren()){
//			getChildren().remove(child);
//		}
//		for(Node child : cells){
//			getChildren().add(child);
//		}
//	}
	
}
