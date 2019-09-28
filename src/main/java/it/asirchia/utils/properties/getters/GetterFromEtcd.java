package it.asirchia.utils.properties.getters;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.kv.GetResponse;

@SuppressWarnings("unchecked")
public class GetterFromEtcd implements RemotePropertyGetter{
	
	private static final Boolean isActive = 
			Optional.ofNullable(System.getenv("etcd.active")).isPresent() ? Boolean.parseBoolean(System.getenv("etcd.active")) : false;
	
	private static String connectionString = 
			isActive ? System.getenv("etcd.host").concat(":").concat(System.getenv("etcd.port")) : null;
			
	private static Optional<KV> client = Optional.empty();

	public static Boolean isActive() {
		return isActive;
	}
	
	@Override
	public KV buildClient() {
		Client client = Client.builder().endpoints("http://".concat(connectionString)).build();
		KV kvClient = client.getKVClient();
		
		return kvClient;
	}
	
	@Override
	public Optional<String> get(String key) {
		ByteSequence bs_key = ByteSequence.from(key.getBytes());

		if(!client.isPresent()) {
			synchronized(KV.class){
				client = Optional.ofNullable(buildClient());
			}
		}
		
		Optional<String> out = Optional.empty();
		
		try {
			CompletableFuture<GetResponse> getFuture = client.get().get(bs_key);
			GetResponse response = getFuture.get();
			List<KeyValue> kvs = response.getKvs();
			if(!kvs.isEmpty()) {
				out = Optional.ofNullable(kvs.get(0).getValue().toString(Charset.defaultCharset()));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return out; 
		
	}
}
