package edu.touro.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReadCSV {
	
	ResourceBundle prop = ResourceBundle.getBundle("ldapsearch");
	
/*	public static void main(String args[]) {
		ReadCSV getIDinfo = new ReadCSV();
		getIDinfo.collectID();
	}*/
	
	public List<String> collectID() {

			String csvFile = prop.getString("READFILENAME");
	        String line = "";
	        String id = "";
	        List<String> idCollection = new ArrayList<String>();
	        
	     
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) 
	        {

	            while ((line = br.readLine()) != null) 
	            {

	               id = line;
	               idCollection.add(id);                               
	            
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        System.out.println("Array size is: " + idCollection.size());

	    	
		return idCollection;
	}

}
