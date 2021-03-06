package application;

import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrzeglıdOkno extends AbstractApp {
	
	private static PrzeglıdOkno one;
	private static Stage primaryStage;
	
	public static void NewWindow(){
		if(one == null){
			one = new PrzeglıdOkno();
			primaryStage = new Stage();
		}
		if(!(primaryStage.isShowing())){
			try {
				one.start(primaryStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void start(Stage arg0) throws Exception {
		VBox vbox = new VBox();
		
		SQL sql = new SQL();
		DateTable table = new DateTable(sql);
		vbox.getChildren().add(table);
		vbox.setVgrow(table, Priority.ALWAYS);
		table.setEditable(true);
		sql.selectForView(table);
		super.start(arg0, vbox, 400, 500);
		arg0.setMaximized(true);

	}

	public void setData(String ...strings ){
		int i =0;
		for (int j = 0; j < strings.length; j++) {
			i++;
		}
		if(i!=13) return;
		
	}
	
}
