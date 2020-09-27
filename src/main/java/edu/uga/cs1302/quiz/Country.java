package edu.uga.cs1302.quiz;

public class Country {

	private final String country;
	private final String continent;

	//country constructor each country has a continent that corresponds to it
	public Country(String country, String continent) {
		this.country = country;
		this.continent = continent;
	}

	//setter/getter/other methods for other classes to use
	public String getCountry() {
		return country;
	}

	public String getContinent() {
		return continent;
	}


}
