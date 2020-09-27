package edu.uga.cs1302.quiz;

import java.io.IOException;
import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;


public class QuestionCollection {

	private ArrayList<Country> countrys = new ArrayList<Country>();

	//method for reading/parsing the csv file
	public void parseCountrysCSV() {

		//a try/catch that reads and parses through the country_continent csv and puts each country/continent
		//into a country object and into an arraylist containing country objects
		try {
			Reader in = new FileReader("/home/myid/kkochut/cs1302/country_continent.csv");
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
			for (CSVRecord record : records) {

				String country = record.get(0);
				String continent = record.get(1);

				countrys.add(new Country(country, continent));
			}

		}
		catch (IOException e) {
			System.out.println ("Error reading file country_continent.csv");
			System.exit (0);
		}

	}

	//setter/getter/other methods for other classes to use
	public Country countryAtIndex(int index) {
		Country indexCountry = countrys.get(index);
		return indexCountry;
	}

	public Country continentAtIndex(int index) {
		Country indexContinent = countrys.get(index);
		return indexContinent;
	}

}

