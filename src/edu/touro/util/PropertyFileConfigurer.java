package edu.touro.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileConfigurer {
	
	 public Properties getProperties() throws IOException {
		    Properties prop = new Properties();
		    InputStream input = null;
		 
		    try {
		        input = new FileInputStream("C:\\Production_Runs\\LDAPsearch\\ldapsearch.properties");
		         
		        // load the properties file
		        prop.load(input);
		 
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    } finally {
		        if (input != null) {
		            try {
		                input.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		    // get the property value and return it
		    return prop;
		}

}
