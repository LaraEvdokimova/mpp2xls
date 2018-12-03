package me.colley.mpp2xls.proto;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
 
public class PropertyReader {
	String result = "";
	Map<String, String> map = new HashMap<String, String>();
	InputStream inputStream;

	public Properties getProperties() throws IOException {
		Properties prop = new Properties();
		String propFileName = "config.properties";
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
//			Date time = new Date(System.currentTimeMillis());
//			// get the property value and print it out
//			String user = prop.getProperty("user");
//			String company1 = prop.getProperty("company1");
//			String company2 = prop.getProperty("company2");
//			String company3 = prop.getProperty("company3");
// 
//			result = "Company List = " + company1 + ", " + company2 + ", " + company3;
//			System.out.println(result + "\nProgram Ran on " + time + " by user=" + user);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return prop;
	} 
	
	public String getPropValues() throws IOException {
 
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			Date time = new Date(System.currentTimeMillis());
 
			// get the property value and print it out
			String user = prop.getProperty("user");
			String company1 = prop.getProperty("company1");
			String company2 = prop.getProperty("company2");
			String company3 = prop.getProperty("company3");
 
			result = "Company List = " + company1 + ", " + company2 + ", " + company3;
			System.out.println(result + "\nProgram Ran on " + time + " by user=" + user);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
		//return map;
	}
}