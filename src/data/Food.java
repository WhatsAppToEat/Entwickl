package data;

import java.sql.Date;

public class Food {

	String _name;
	double _menge;
	String _masseinheit;
	String _ablaufdatum;
	
	public Food(){
	}
	
	public Food(String name, double menge, String masseinheit, String ablaufdatum) {
		this._name = name;
		this._menge = menge;
		this._masseinheit = masseinheit;
		this._ablaufdatum = ablaufdatum;
	}
	
	public String getName() {
		return this._name;
	}
	
	public void setName(String name) {
		this._name = name;
	}
	
	public double getMenge() {
		return this._menge;
	}
	
	public void setMenge(double menge) {
		this._menge = menge;
	}
	
	public String getMasseinheit() {
		return this._masseinheit;
	}
	
	public void setMasseinheit(String masseinheit) {
		this._masseinheit = masseinheit;
	}
	
	public String getAblaufdatum() {
		return this._ablaufdatum;
	}
	
	public void setAblaufdatum(String ablaufdatum) {
		this._ablaufdatum = ablaufdatum;
	}
	
}
