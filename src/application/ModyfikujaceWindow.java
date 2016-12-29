package application;

import javafx.stage.Stage;

public class ModyfikujaceWindow extends ModyfikujWind {

	private static ModyfikujWind one;
	private static Stage primaryStage;
	private ModyfikujaceWindow() {
		primaryStage = new Stage();
	}

	private static void start() throws Exception {

		one.start(primaryStage);
	}
	static void newWindow(){
		if(one == null){
			one = new ModyfikujWind();
			primaryStage = new Stage();
		}
		if(!(primaryStage.isShowing())){
			try {
				start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
