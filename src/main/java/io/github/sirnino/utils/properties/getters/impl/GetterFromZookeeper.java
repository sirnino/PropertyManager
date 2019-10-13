package io.github.sirnino.utils.properties.getters.impl;

import java.util.Optional;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import io.github.sirnino.utils.properties.getters.RemotePropertyGetter;

@SuppressWarnings("unchecked")
public class GetterFromZookeeper implements RemotePropertyGetter{
		
	private static final Boolean active = 
			Optional.ofNullable(System.getenv("zookeeper.active")).isPresent() ? Boolean.parseBoolean(System.getenv("zookeeper.active")) : false;
	
	private static String connectionString = 
			active ? System.getenv("zookeeper.host").concat(":").concat(System.getenv("zookeeper.port")) : null;
			
	private Optional<CuratorFramework> client = Optional.empty();
	
	private static final String pathPattern = "/conf/%s";
	
	private static final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

	public static Boolean isActive() {
		return active;
	}
	
	public CuratorFramework buildClient() {
		if(!client.isPresent()) {
			synchronized(CuratorFramework.class){
				client = Optional.ofNullable(CuratorFrameworkFactory.newClient(connectionString, retryPolicy));
				client.get().start();
			}
		}
		return client.get();
	}
	
	public Optional<String> get(String key) {
		
		Optional<String> ret = Optional.empty();
		String path = String.format(pathPattern, key);
		
		try { 
			byte[] data = buildClient().getData().forPath(path);
			ret = Optional.of(new String(data));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
}
