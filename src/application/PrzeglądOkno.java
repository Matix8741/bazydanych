package application;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Przegl¹dOkno extends AbstractApp {
	
	
	@Override
	public void start(Stage arg0) throws Exception {
		VBox vbox = new VBox();
		super.start(arg0, vbox, 400, 500);
		DateTable table = new DateTable();
		vbox.getChildren().add(table);
		table.setEditable(true);
		SQL sql = new SQL();
		sql.selectForView(table);

	}

	public void setData(String ...strings ){
		int i =0;
		for(String string : strings){
			i++;
		}
		if(i!=13) return;
		
	}
	
}
