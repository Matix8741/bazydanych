package application;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class Datum extends HBox {

	TextField textfield;
	private ComboBox combobox;
	
//	public Datum() {
//		// TODO Auto-generated constructor stub
//	}

	public Datum(double spacing) {
		super(spacing);
		// TODO Auto-generated constructor stub
	}

	public Datum(String name, double width){
		super();
		Label label = new Label(name);
		label.setPrefWidth(width);
		textfield = new TextField();
		setSpacing(10);
		textfield.setPrefWidth(190-width);
		setPrefWidth(200);
		getChildren().addAll(label, textfield);
		
	}
	public Datum(String name, ComboBox<String> node){
		super();
		setCombobox(node);
		Label label = new Label(name);
		setPrefWidth(250);
		node.setPrefWidth(150);
		setSpacing(10);
		getChildren().addAll(label, node);
	}
	public Datum(String name, Node node){
		super();
		Label label = new Label(name);
		setPrefWidth(250);
		setSpacing(10);
		getChildren().addAll(label, node);
	}
	
	public Datum(Node... children) {
		super(children);
		// TODO Auto-generated constructor stub
	}

	public Datum(double spacing, Node... children) {
		super(spacing, children);
		// TODO Auto-generated constructor stub
	}

	public TextField getTextfield() {
		if(textfield == null){
			return getCombobox().getEditor();
		}
		return textfield;
	}

	public ComboBox getCombobox() {
		return combobox;
	}

	private void setCombobox(ComboBox combobox) {
		this.combobox = combobox;
	}

	

}
