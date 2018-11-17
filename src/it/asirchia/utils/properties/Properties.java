package it.asirchia.utils.properties;

import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 *  Property Manager - to retrieve application configuration
 *  Copyright (C) 2018  - Antonino Sirchia
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *  @author Antonino Sirchia (asirchia@gmail.com)
 */
public class Properties {
	
	//Zookeeper Stuff
	private static final String pathPattern = "/conf/%s/%s";
	private static final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
	private static String zookeeperConnectionString = System.getenv("zookeeper.host").concat(":").concat(System.getenv("zookeeper.port"));
	private static String envName = System.getenv("env"); 
	private static Optional<CuratorFramework> zooClient = Optional.empty();
	
	//Configuration file stuff
	private static final String BUNDLE_NAME = "configuration";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	/**
	 * Retrieves the Property with the specified keyword.
	 * First it searches the properties in the Environment Variables, 
	 * if no match is found it searches in the Zookeeper Configuration Manager,
	 * if no match is found yet, it takes the default value in the configuration file.
	 *  
	 * @param key - the String that represent the name of the property to retrieve
	 * @return a java.util.Optional that may contain the value of the retrieved property
	 * @see java.util.Optional
	 * 
	 */
	public static Optional<String> get(String key) {
		
		Optional<String> ret = fromEnv(key);
		
		if(!ret.isPresent()) {
			ret = fromZookeper(key);
		}
		
		if(!ret.isPresent()) {
			ret = fromFile(key);
		}
		
		return ret;
	}
		
	private static CuratorFramework getZooClient() {
		if(!zooClient.isPresent()) {
			synchronized(CuratorFramework.class){
				zooClient = Optional.ofNullable(CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy));
				zooClient.get().start();
			}
		}
		return zooClient.get();
	}
	
	protected static Optional<String> fromEnv(String key){
		return Optional.ofNullable(System.getenv(key));
	}
	
	protected static Optional<String> fromZookeper(String key) {
		
		Optional<String> ret = Optional.empty();
		String path = String.format(pathPattern, envName ,key);
		
		try { 
			byte[] data = getZooClient().getData().forPath(path);
			ret = Optional.of(new String(data));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	protected static Optional<String> fromFile(String key) {
		
		Optional<String> ret = Optional.empty();
		try {
			return ret = Optional.ofNullable(RESOURCE_BUNDLE.getString(key));
		} 
		catch (MissingResourceException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
}
