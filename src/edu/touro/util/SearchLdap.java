package edu.touro.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class SearchLdap {
	 InitialDirContext ctx = null;
	 PropertyFileConfigurer getPropFile = new PropertyFileConfigurer();
	 
	 
	 public static void main(String args[]) {
		 SearchLdap connectToLdap = new SearchLdap();
		 ReadCSV getData = new ReadCSV();
		 List<String> collection = new ArrayList<String>();
		 collection = getData.collectID();
		 connectToLdap.ldapConnect(collection);
	 }
	 
	 public void ldapConnect(List<String> idCollection) {
		 	String fileName = "";
	        String filePath ="";
	        String fullFileName = "";
	        String baseDN = "";
	        String searchFilter = "";
	        Hashtable<String, Object> env = new Hashtable<String, Object>();
	        Properties prop = null;
			try {
				prop = getPropFile.getProperties();
			} catch (IOException e2) {
				System.out.println("Error getting properties from property file: " + e2);
				e2.printStackTrace();
			}

	        if(null != prop) {
			 fileName = prop.getProperty("OUTPUTFILENAME");
	         filePath = prop.getProperty("FILEPATH");
	         fullFileName = filePath+fileName;
	         baseDN = prop.getProperty("LDAP_BASEDN");
	         searchFilter = prop.getProperty("LDAP_SEARCHFILTER");
	        }
	        else {
	        	System.out.println("Cannot find property file!!");
	        	System.exit(0);
	        }
	        FileWriter fileWriter = null;
			try {
				fileWriter = new FileWriter(fullFileName);
			} catch (IOException e1) {
				System.out.println("Doh! Homer can't find your file specified, please check property file and try again");
				e1.printStackTrace();
			}
	        PrintWriter printWriter = new PrintWriter(fileWriter);
	        
	        	       
	        env.put(Context.INITIAL_CONTEXT_FACTORY,prop.getProperty("INITIAL_CONTEXT_FACTORY"));
	        env.put(Context.PROVIDER_URL, prop.getProperty("PROVIDER_URL"));
	        env.put(Context.SECURITY_AUTHENTICATION, prop.getProperty("SECURITY_AUTHENTICATION"));
	        env.put(Context.SECURITY_PRINCIPAL, prop.getProperty("SECURITY_PRINCIPAL"));
	        env.put(Context.SECURITY_CREDENTIALS, prop.getProperty("SECURITY_CREDENTIALS"));
	        DirContext ctx = null;
	        NamingEnumeration<?> results = null;
	        try {
	            ctx = new InitialDirContext(env);
	            SearchControls controls = new SearchControls();
	            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	            
	            for(int i=0;i<idCollection.size();i++) {
	            	results = ctx.search(baseDN, "(" + searchFilter + "=" + idCollection.get(i) + ")", controls);
	            	if(!results.hasMore()) {
	            		System.out.println(idCollection.get(i) + " is NOT found");
	            		printWriter.print(idCollection.get(i) + ", NOT FOUND");
		                printWriter.print("\n");
	            	}
	            	while (results.hasMore()) {
	                SearchResult searchResult = (SearchResult) results.next();
	                Attributes attributes = searchResult.getAttributes();
	                Attribute attr = attributes.get(prop.getProperty("LDAP_RETURNATTRIBUTE"));
	                if(attr.toString().equalsIgnoreCase("") || attr == null) {
	                	System.out.println(idCollection.get(i) + "is NOT found");
	                }
	                String attributeVal = (String) attr.get();
	                printWriter.print(idCollection.get(i) + ", " + attributeVal);
	                printWriter.print("\n");
	                System.out.println(idCollection.get(i) + " returns the value = " + attributeVal);
	            	}
	            }
	            
	            printWriter.close();
	            
	        } catch (NamingException e) {
	            throw new RuntimeException(e);
	        } finally {
	            if (results != null) {
	                try {
	                    results.close();
	                } catch (Exception e) {
	                }
	            }
	            if (ctx != null) {
	                try {
	                    ctx.close();
	                } catch (Exception e) {
	                }
	            }
	        }
	    }
	 
}
