package application;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public abstract class AbstractApp extends Application {

	protected static Scene scene;
	public void start(Stage primaryStage, Parent root, double x, double y) {
		try {		
			scene = new Scene(root,x,y);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Bud¿et prywatny");
			primaryStage.show();
			primaryStage.getIcons().add(new Image(getClass().getResource("icon.png").toExternalForm()));
			primaryStage.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public AbstractApp() {
		// TODO Auto-generated constructor stub
	}

}
