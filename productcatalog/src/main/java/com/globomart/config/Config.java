package com.globomart.config;

import java.io.IOException;
import java.util.Properties;

public class Config {
	
	private Properties configurations = null;
	private Config()
	{
		configurations = new Properties();
		try {
			configurations.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Config getInstance()
	{
		return ConfigInstance.CONFIG_INSTANCE;	
	}
	
	private static class ConfigInstance{
		static final Config CONFIG_INSTANCE = new Config();
	}
	
	public String getValue(String key){
		return configurations.getProperty(key);
	}

}
