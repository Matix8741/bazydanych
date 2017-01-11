package application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class DateTable extends TableView<Data> {
	private List<Node> cells;
	private List<Label> labels;
	private List<TextField> textfields;
	private SQL sql;
	private DateTable getMe(){ return this; }
	public DateTable(SQL sql) {
		super();
		this.sql = sql;
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
		colunmn4.setCellFactory(TextFieldTableCell.forTableColumn());
		colunmn5.setCellFactory(TextFieldTableCell.forTableColumn());
		colunmn6.setCellFactory(TextFieldTableCell.forTableColumn());
		colunmn7.setCellFactory(TextFieldTableCell.forTableColumn());
		colunmn8.setCellFactory(TextFieldTableCell.forTableColumn());
		colunmn9.setCellFactory(TextFieldTableCell.forTableColumn());
		colunmn10.setCellFactory(TextFieldTableCell.forTableColumn());
		colunmn11.setCellFactory(TextFieldTableCell.forTableColumn());
		colunmn12.setCellFactory(TextFieldTableCell.forTableColumn());
		colunmn3.setCellFactory(ChoiceBoxTableCell.forTableColumn("wydatki","dochody"));
		colunmn2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Data,String>>() {
			
			@Override
			public void handle(CellEditEvent<Data, String> arg0) {
				int row = arg0.getTablePosition().getRow();
				Data nowData = colunmn1.getTableView().getItems().get(row);
				boolean is = true;
				try {
					is =sql.execModyfikowanieRachunku(Integer.valueOf(nowData.getMyStrings(0)),
							Double.valueOf(nowData.getMyStrings(10)).intValue(),
							arg0.getNewValue(),
							nowData.getMyStrings(2),
							nowData.getMyStrings(3),
							nowData.getMyStrings(4),
							nowData.getMyStrings(5),
							nowData.getMyStrings(6),
							nowData.getMyStrings(7),
							nowData.getMyStrings(8),
							nowData.getMyStrings(9),
							nowData.getMyStrings(11));
				} catch (NumberFormatException e) {
					is = false;
					e.printStackTrace();
				}
				if(is){
					((Data) arg0.getTableView().getItems().get(
							arg0.getTablePosition().getRow())
							).setMyString(1, arg0.getNewValue());
				}
				getMe().refresh();
				
			}
		});
		colunmn4.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Data,String>>() {
			
			@Override
			public void handle(CellEditEvent<Data, String> arg0) {
				int row = arg0.getTablePosition().getRow();
				Data nowData = colunmn1.getTableView().getItems().get(row);
				boolean is = true;
				try {
					is =sql.execModyfikowanieRachunku(Integer.valueOf(nowData.getMyStrings(0)),
							Double.valueOf(nowData.getMyStrings(10)).intValue(),
							nowData.getMyStrings(1),
							nowData.getMyStrings(2),
							arg0.getNewValue(),
							nowData.getMyStrings(4),
							nowData.getMyStrings(5),
							nowData.getMyStrings(6),
							nowData.getMyStrings(7),
							nowData.getMyStrings(8),
							nowData.getMyStrings(9),
							nowData.getMyStrings(11));
				} catch (NumberFormatException e) {
					is = false;
					e.printStackTrace();
				}
				if(is){
					((Data) arg0.getTableView().getItems().get(
							arg0.getTablePosition().getRow())
							).setMyString(3, arg0.getNewValue());
				}
				getMe().refresh();
				
			}
		});
		colunmn5.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Data,String>>() {
			
			@Override
			public void handle(CellEditEvent<Data, String> arg0) {
				int row = arg0.getTablePosition().getRow();
				Data nowData = colunmn1.getTableView().getItems().get(row);
				boolean is = true;
				try {
					is =sql.execModyfikowanieRachunku(Integer.valueOf(nowData.getMyStrings(0)),
							Double.valueOf(nowData.getMyStrings(10)).intValue(),
							nowData.getMyStrings(1),
							nowData.getMyStrings(2),
							nowData.getMyStrings(3),
							arg0.getNewValue(),
							nowData.getMyStrings(5),
							nowData.getMyStrings(6),
							nowData.getMyStrings(7),
							nowData.getMyStrings(8),
							nowData.getMyStrings(9),
							nowData.getMyStrings(11));
				} catch (NumberFormatException e) {
					is = false;
					e.printStackTrace();
				}
				if(is){
					((Data) arg0.getTableView().getItems().get(
							arg0.getTablePosition().getRow())
							).setMyString(4, arg0.getNewValue());
				}
				getMe().refresh();
				
			}
		});
		colunmn6.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Data,String>>() {
			
			@Override
			public void handle(CellEditEvent<Data, String> arg0) {
				int row = arg0.getTablePosition().getRow();
				Data nowData = colunmn1.getTableView().getItems().get(row);
				boolean is = true;
				try {
					is =sql.execModyfikowanieRachunku(Integer.valueOf(nowData.getMyStrings(0)),
							Double.valueOf(nowData.getMyStrings(10)).intValue(),
							nowData.getMyStrings(1),
							nowData.getMyStrings(2),
							nowData.getMyStrings(3),
							nowData.getMyStrings(4),
							arg0.getNewValue(),
							nowData.getMyStrings(6),
							nowData.getMyStrings(7),
							nowData.getMyStrings(8),
							nowData.getMyStrings(9),
							nowData.getMyStrings(11));
				} catch (NumberFormatException e) {
					is = false;
					e.printStackTrace();
				}
				if(is){
					((Data) arg0.getTableView().getItems().get(
							arg0.getTablePosition().getRow())
							).setMyString(5, arg0.getNewValue());
				}
				getMe().refresh();
				
			}
		});
		colunmn7.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Data,String>>() {
			
			@Override
			public void handle(CellEditEvent<Data, String> arg0) {
				int row = arg0.getTablePosition().getRow();
				Data nowData = colunmn1.getTableView().getItems().get(row);
				boolean is = true;
				try {
					is =sql.execModyfikowanieRachunku(Integer.valueOf(nowData.getMyStrings(0)),
							Double.valueOf(nowData.getMyStrings(10)).intValue(),
							nowData.getMyStrings(1),
							nowData.getMyStrings(2),
							nowData.getMyStrings(3),
							nowData.getMyStrings(4),
							nowData.getMyStrings(5),
							arg0.getNewValue(),
							nowData.getMyStrings(7),
							nowData.getMyStrings(8),
							nowData.getMyStrings(9),
							nowData.getMyStrings(11));
				} catch (NumberFormatException e) {
					is = false;
					e.printStackTrace();
				}
				if(is){
					((Data) arg0.getTableView().getItems().get(
							arg0.getTablePosition().getRow())
							).setMyString(6, arg0.getNewValue());
				}
				getMe().refresh();
				
			}
		});
		colunmn8.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Data,String>>() {
			
			@Override
			public void handle(CellEditEvent<Data, String> arg0) {
				int row = arg0.getTablePosition().getRow();
				Data nowData = colunmn1.getTableView().getItems().get(row);
				boolean is = true;
				try {
					is =sql.execModyfikowanieRachunku(Integer.valueOf(nowData.getMyStrings(0)),
							Double.valueOf(nowData.getMyStrings(10)).intValue(),
							nowData.getMyStrings(1),
							nowData.getMyStrings(2),
							nowData.getMyStrings(3),
							nowData.getMyStrings(4),
							nowData.getMyStrings(5),
							nowData.getMyStrings(6),
							arg0.getNewValue(),
							nowData.getMyStrings(8),
							nowData.getMyStrings(9),
							nowData.getMyStrings(11));
				} catch (NumberFormatException e) {
					is = false;
					e.printStackTrace();
				}
				if(is){
					((Data) arg0.getTableView().getItems().get(
							arg0.getTablePosition().getRow())
							).setMyString(7, arg0.getNewValue());
				}
				getMe().refresh();
				
			}
		});
		colunmn9.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Data,String>>() {
			
			@Override
			public void handle(CellEditEvent<Data, String> arg0) {
				int row = arg0.getTablePosition().getRow();
				Data nowData = colunmn1.getTableView().getItems().get(row);
				boolean is = true;
				try {
					is =sql.execModyfikowanieRachunku(Integer.valueOf(nowData.getMyStrings(0)),
							Double.valueOf(nowData.getMyStrings(10)).intValue(),
							nowData.getMyStrings(1),
							nowData.getMyStrings(2),
							nowData.getMyStrings(3),
							nowData.getMyStrings(4),
							nowData.getMyStrings(5),
							nowData.getMyStrings(6),
							nowData.getMyStrings(7),
							arg0.getNewValue(),
							nowData.getMyStrings(9),
							nowData.getMyStrings(11));
				} catch (NumberFormatException e) {
					is = false;
					e.printStackTrace();
				}
				if(is){
					((Data) arg0.getTableView().getItems().get(
							arg0.getTablePosition().getRow())
							).setMyString(8, arg0.getNewValue());
				}
				getMe().refresh();
				
			}
		});
		colunmn10.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Data,String>>() {
			
			@Override
			public void handle(CellEditEvent<Data, String> arg0) {
				int row = arg0.getTablePosition().getRow();
				Data nowData = colunmn1.getTableView().getItems().get(row);
				boolean is = true;
				try {
					is =sql.execModyfikowanieRachunku(Integer.valueOf(nowData.getMyStrings(0)),
							Double.valueOf(nowData.getMyStrings(10)).intValue(),
							nowData.getMyStrings(1),
							nowData.getMyStrings(2),
							nowData.getMyStrings(3),
							nowData.getMyStrings(4),
							nowData.getMyStrings(5),
							nowData.getMyStrings(6),
							nowData.getMyStrings(7),
							nowData.getMyStrings(8),
							arg0.getNewValue(),
							nowData.getMyStrings(11));
				} catch (NumberFormatException e) {
					is = false;
					e.printStackTrace();
				}
				if(is){
					((Data) arg0.getTableView().getItems().get(
							arg0.getTablePosition().getRow())
							).setMyString(9, arg0.getNewValue());
				}
				getMe().refresh();
				
			}
		});
		colunmn11.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Data,String>>() {
			
			@Override
			public void handle(CellEditEvent<Data, String> arg0) {
				int row = arg0.getTablePosition().getRow();
				Data nowData = colunmn1.getTableView().getItems().get(row);
				boolean is = true;
				try {
					is =sql.execModyfikowanieRachunku(Integer.valueOf(nowData.getMyStrings(0)),
							Double.valueOf(arg0.getNewValue()).intValue(),
							nowData.getMyStrings(1),
							nowData.getMyStrings(2),
							nowData.getMyStrings(3),
							nowData.getMyStrings(4),
							nowData.getMyStrings(5),
							nowData.getMyStrings(6),
							nowData.getMyStrings(7),
							nowData.getMyStrings(8),
							nowData.getMyStrings(9),
							nowData.getMyStrings(11));
				} catch (NumberFormatException e) {
					is = false;
					e.printStackTrace();
				}
				if(is){
					((Data) arg0.getTableView().getItems().get(
							arg0.getTablePosition().getRow())
							).setMyString(10, arg0.getNewValue());
				}
				getMe().refresh();
				
			}
		});
		colunmn12.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Data,String>>() {
			
			@Override
			public void handle(CellEditEvent<Data, String> arg0) {
				int row = arg0.getTablePosition().getRow();
				Data nowData = colunmn1.getTableView().getItems().get(row);
				boolean is = true;
				try {
					is =sql.execModyfikowanieRachunku(Integer.valueOf(nowData.getMyStrings(0)),
							Double.valueOf(nowData.getMyStrings(10)).intValue(),
							nowData.getMyStrings(1),
							nowData.getMyStrings(2),
							nowData.getMyStrings(3),
							nowData.getMyStrings(4),
							nowData.getMyStrings(5),
							nowData.getMyStrings(6),
							nowData.getMyStrings(7),
							nowData.getMyStrings(8),
							nowData.getMyStrings(9),
							arg0.getNewValue());
				} catch (NumberFormatException e) {
					is = false;
					e.printStackTrace();
				}
				if(is){
					((Data) arg0.getTableView().getItems().get(
							arg0.getTablePosition().getRow())
							).setMyString(11, arg0.getNewValue());
				}
				getMe().refresh();
				
			}
		});
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
