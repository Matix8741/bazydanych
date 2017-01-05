package application;

import javafx.beans.property.SimpleStringProperty;

public class Data {
	private final SimpleStringProperty[] myStrings;
	
	public Data(String strings[]) {
		myStrings = new SimpleStringProperty[13];
		for(int i=0; i<13;i++){
			myStrings[i] = new SimpleStringProperty(strings[i]);
		}
	}

	public String getMyStrings(int i) {
		return myStrings[i].get();
	}
	public void setMyString(int i, String text){
		myStrings[i].set(text);
	}
}
