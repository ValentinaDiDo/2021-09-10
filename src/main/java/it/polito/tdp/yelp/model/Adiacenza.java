package it.polito.tdp.yelp.model;

public class Adiacenza {
	private Business b1;
	private Business b2;
	private double distanzaKM;
	public Adiacenza(Business b1, Business b2, double distanzaKM) {
		super();
		this.b1 = b1;
		this.b2 = b2;
		this.distanzaKM = distanzaKM;
	}
	public Business getB1() {
		return b1;
	}
	public Business getB2() {
		return b2;
	}
	public double getDistanzaKM() {
		return distanzaKM;
	}
	
	
}
