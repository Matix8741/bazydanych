package application;
	
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends AbstractApp {
	@Override
	public void start(Stage primaryStage) {
		VBox root = new VBox();
		VBox buttons = new VBox();
		super.start(primaryStage,root, 200, 130);
		buttons.setSpacing(10);
		buttons.setPadding(new Insets(20,60,5,60));
		Button nowyWpis = new Button("Nowy Wpis");
		nowyWpis.setOnAction( event -> {
			System.out.println("E");
			javafx.application.Platform.runLater(() -> ModyfikujWind.launch());
			
		});
		Button przegl¹daj = new Button("Przegl¹daj");
		nowyWpis.setOnAction( arg0 -> System.out.println("PRZS"));
		Button modyfikuj = new Button("Modyfikuj");
		nowyWpis.setOnAction( arg0 -> {
			
		});
		HBox end = new HBox();
		Label saldo = new Label("Saldo: UNKNOW");
		saldo.setFont(new Font(10));
		end.getChildren().add(saldo);
		end.setPadding(new Insets(0, 0,0,125)); 
		buttons.getChildren().addAll(nowyWpis, przegl¹daj, modyfikuj);
		root.getChildren().addAll(buttons, end);
		scene.setRoot(root);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}