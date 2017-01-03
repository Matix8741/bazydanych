package application;


import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModyfikujWind extends AbstractApp {

	protected ModyfikujWind() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root = new VBox();
		root.setSpacing(10);
		root.setPadding(new Insets(10,30,0,30));
		super.start(primaryStage, root, 400, 360);
		Datum date = new Datum("Data: ", 60);
		ChoiceBox typeBox = new ChoiceBox(
				FXCollections.observableArrayList("wydatki","dochody"));
		Datum type = new Datum("Typ: ", typeBox);
		Datum rodzaj = new Datum("Rodzaj: ", 70);
		Datum artyku³ = new Datum("Artyku³: ", 70);
		Datum podmiot = new Datum("Podmiot: ", 75);
		Datum ulica = new Datum("Ulica: ", 60);
		Datum nrBudynku = new Datum("Nr budynku: ", 90);
		Datum miasto = new Datum("Miasto: ", 70);
		Datum kodPocztowy = new Datum("Kod pocztowy: ", 110);
		Datum kwota = new Datum("Kwota: ", 60);
		Datum uwagi = new Datum("Uwagi: ", 60);
		Button dodaj = new Button("DODAJ");
		dodaj.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				SQL sql = new SQL();
				try {
					sql.connect();
					//System.out.println(getString(typeBox.getSelectionModel().getSelectedIndex()));
					sql.execDodawanieRachunku(Integer.valueOf(kwota.textfield.getText()),
											date.textfield.getText() ,
											getString(typeBox.getSelectionModel().getSelectedIndex()) ,
											rodzaj.textfield.getText() ,
											artyku³.textfield.getText() ,
											podmiot.textfield.getText() ,
											ulica.textfield.getText() ,
											nrBudynku.textfield.getText() ,
											miasto.textfield.getText() ,
											kodPocztowy.textfield.getText() ,
											uwagi.textfield.getText());
					sql.select("Artykul", "NazwaArtykulu");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		addMore(root, date,type,rodzaj,artyku³,podmiot, ulica,nrBudynku, miasto,kodPocztowy,kwota,uwagi,dodaj);
		
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
