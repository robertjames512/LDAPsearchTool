package edu.touro.main;

import java.util.ArrayList;
import java.util.List;

import edu.touro.util.ReadCSV;
import edu.touro.util.SearchLdap;

public class LaunchLdapSearch {
	
	 public static void main(String args[]) {
		 SearchLdap connectToLdap = new SearchLdap();
		 ReadCSV getData = new ReadCSV();
		 List<String> collection = new ArrayList<String>();
		 collection = getData.collectID();
		 connectToLdap.ldapConnect(collection);
	 }
	 

}
