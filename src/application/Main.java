package application;
	
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
		nowyWpis.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		nowyWpis.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						try {
							ModyfikujaceWindow.newWindow();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				});
				
			}
		});
		Button przegl퉐aj = new Button("Przegl퉐aj");
		przegl퉐aj.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		przegl퉐aj.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						Przegl퉐Okno.NewWindow();
						
					}
				});
				
			}
		});
		HBox end = new HBox();
		SQL sql = new SQL();
		Label saldo = new Label("Saldo: UNKNOW");
		try {
			sql.selectForSaldo(saldo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		refresh(new Timer(), sql, saldo);
		saldo.setFont(new Font(20));
		end.getChildren().add(saldo);
		end.setPadding(new Insets(0, 0,0,125)); 
		buttons.getChildren().addAll(nowyWpis, przegl퉐aj);
		root.getChildren().addAll(buttons, end);
		scene.setRoot(root);	
		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
	}
	
	public static void refresh(Timer timer, SQL sql, Label label){
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						try {
							sql.selectForSaldo(label);
						} catch (SQLException e) {
						}
						
					}
				});
				
			}
		},0,1000);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}