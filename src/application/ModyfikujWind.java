package application;


import java.lang.reflect.Array;
import java.sql.SQLException;
import java.text.Collator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ModyfikujWind extends AbstractApp {

	String address = "";
	protected ModyfikujWind() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root = new VBox();
		root.setSpacing(10);
		root.setPadding(new Insets(10,30,0,30));
		super.start(primaryStage, root, 400, 360);
		DatePicker datepicker =  new DatePicker();
		datepicker.setConverter(new StringConverter<LocalDate>() {
			
			private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			@Override
			public String toString(LocalDate object) {
				if(object == null)
					return "";
				return dateTimeFormat.format(object);
			}
			
			@Override
			public LocalDate fromString(String string) {
				if(string==null || string.trim().isEmpty())
		        {
		            return null;
		        }
		        return LocalDate.parse(string,dateTimeFormat);
			}
		});
		Datum date = new Datum("Data: ",datepicker);
		ChoiceBox<String> typeBox = new ChoiceBox<String>(
				FXCollections.observableArrayList("wydatki","dochody"));
		typeBox.setValue("wydatki");;
		Datum type = new Datum("Typ: ", typeBox);
		List<ComboBox<String>> combobox = new ArrayList<ComboBox<String>>(8);
		List<ObservableList<String>> comboBoxItems = new ArrayList<ObservableList<String>>();
		System.out.println(combobox.size());
		for(int i =0; i<9;i++){
			comboBoxItems.add(FXCollections.observableArrayList());
			combobox.add(new ComboBox<String>());
			combobox.get(i).setPromptText("E");
			comboBoxItems.get(i).sort(new StringComparator("AA"));
			combobox.get(i).setItems(/*new SortedList<String>(*/comboBoxItems.get(i)/*, Collator.getInstance())*/);
			combobox.get(i).setEditable(true);
			addTextComboSortAndAction(combobox.get(i).getEditor(), combobox.get(i));
		}
		comboBoxItems.get(0).sort(new StringComparator("AA"));
		Datum rodzaj = new Datum("Rodzaj: ", combobox.get(0));
		Datum artyku³ = new Datum("Artyku³: ", combobox.get(1));
		Datum podmiot = new Datum("Podmiot: ", combobox.get(2));
		Datum ulica = new Datum("Ulica: ", combobox.get(3));
		Datum nrBudynku = new Datum("Nr budynku: ", combobox.get(4));
		Datum miasto = new Datum("Miasto: ", combobox.get(5));
		Datum kodPocztowy = new Datum("Kod pocztowy: ", combobox.get(6));
		Datum kwota = new Datum("Kwota: ", combobox.get(7));
		addTextLimiterAndAction(kwota.getTextfield(), typeBox);
		SQL sql = new SQL();
		sql.selectForComboboxName(comboBoxItems.get(0));
		sql.selectForComboboxArtykul(comboBoxItems.get(1));
		sql.selectForComboboxPodmiot(comboBoxItems.get(2));
		sql.selectForComboboxUlica(comboBoxItems.get(3));
		sql.selectForComboboxNrBud(comboBoxItems.get(4));
		sql.selectForComboboxMiasto(comboBoxItems.get(5));
		sql.selectForComboboxKodBud(comboBoxItems.get(6));
		sql.selectForComboboxKwot(comboBoxItems.get(7));
		typeBox.getSelectionModel().selectedIndexProperty().addListener( new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(newValue.intValue() == 0){
					int i = 0;
					try{i=Integer.valueOf(kwota.textfield.getText());}
					catch (NumberFormatException e) {
					}
					if(i>0){
						String s = kwota.getTextfield().getText();
						kwota.getTextfield().setText("-"+s);
					}
				}
				
			}
		});
		Datum uwagi = new Datum("Uwagi: ", 60);
		Button dodaj = new Button("DODAJ");
		dodaj.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				SQL sql = new SQL();
				boolean good = true;
				//date.textfield.getText().trim().isEmpty();
				try {
					//System.out.println(getString(typeBox.getSelectionModel().getSelectedIndex()));
					sql.execDodawanieRachunku(Integer.valueOf(kwota.getTextfield().getText()),
											datepicker.getEditor().getText() ,
											getString(typeBox.getSelectionModel().getSelectedIndex()) ,
											rodzaj.getTextfield().getText() ,
											artyku³.getTextfield().getText() ,
											podmiot.getTextfield().getText() ,
											ulica.getTextfield().getText() ,
											nrBudynku.getTextfield().getText() ,
											miasto.getTextfield().getText() ,
											kodPocztowy.getTextfield().getText() ,
											uwagi.getTextfield().getText());
					sql.select("Artykul", "NazwaArtykulu");
				} catch (SQLException | NumberFormatException e) {
					good= false;
					Alert error = new Alert(AlertType.ERROR);
					error.setTitle("B³¹d dodawania");
					error.setHeaderText("Nie uda³o siê dodaæ formularza");
					error.setContentText("B³êdne dane");
					error.showAndWait();
					System.out.println(e.getMessage());
				}finally{
					if(good) primaryStage.close();
				}
				
			}
		});
		addMore(root, date,type,rodzaj,artyku³,podmiot, ulica,nrBudynku, miasto,kodPocztowy,kwota,uwagi,dodaj);
		primaryStage.sizeToScene();
		}
	public static void addTextLimiterAndAction(final TextField tf,final ChoiceBox<String> type) {
		tf.textProperty().addListener((ChangeListener<String>) (ov, oldValue, newValue) -> {
			int works =0;
			boolean catched = false;
			try{ works = Integer.valueOf(tf.getText());}
			catch (IllegalArgumentException e){	
				catched = true;
			}
			String st=getString(type.getSelectionModel().getSelectedIndex());			
			if (catched) {
				tf.setText("");
				}
			else{
				if(st.equals("wydatki")){
					if(works>0) {
						String string = tf.getText();
						tf.setText("-"+string);
					}
				}
				else{
					if(works<0){
						works *=(-1);
						tf.setText(String.valueOf(works));
					}
				}
			}
		});
		}
	
	public static void addTextComboSortAndAction(final TextField tf,final ComboBox<String> type) {
		tf.textProperty().addListener((ChangeListener<String>) (ov, oldValue, newValue) -> {
			type.getItems().sort(new StringComparator(newValue));
		});
		}
	
	private static String getString(int index){
		if(index == 0) return "wydatki";
		else return "dochody";
	}
	private static void addMore(VBox root, Node ... arg){
		boolean meter = false;
		HBox hbox = new HBox();
		hbox.setSpacing(30);
		hbox.setPadding(new Insets(10,0,10,0));
		root.getChildren().add(hbox);
		for(Node node : arg){
			hbox.getChildren().add(node);
			if(meter){
				System.out.println("KKKK");
				hbox = new HBox();
				hbox.setSpacing(30);
				hbox.setPadding(new Insets(10,0,10,0));
				root.getChildren().add(hbox);
			}
			meter = changeBoolean(meter);
			
		}
	}

	private static boolean changeBoolean(boolean state){
		System.out.println(state);
		if(state){
			return false;
		}else{
			return true;
		}
	}
}
