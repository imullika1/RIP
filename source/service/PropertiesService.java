package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesService {

	private String ipOne;
	private String ipTwo;
	private String groupOne;
	private String groupTwo;

	public PropertiesService(){
		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream("conf.properties"));
			ipOne = properties.getProperty("ipOne");
			ipTwo = properties.getProperty("ipTwo");
			groupOne = properties.getProperty("groupOne");
			groupTwo = properties.getProperty("groupTwo");
		} catch (IOException exception) {
			System.err.println("[service-properties]-[error]: " + exception.getMessage());
		}
	}

	public String getIpOne(){
		return ipOne;
	}
	public String getIpTwo(){
		return ipTwo;
	}
	public String getGroupOne() {
		return groupOne;
	}
	public String getGroupTwo() {
		return groupTwo;
	}
}
