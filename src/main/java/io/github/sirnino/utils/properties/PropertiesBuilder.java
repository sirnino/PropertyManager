package io.github.sirnino.utils.properties;

import java.util.Optional;

import io.github.sirnino.utils.properties.getters.PropertyGetter;
import io.github.sirnino.utils.properties.getters.RemotePropertyGetter;
import io.github.sirnino.utils.properties.getters.impl.GetterFromEnvironment;
import io.github.sirnino.utils.properties.getters.impl.GetterFromFile;

public class PropertiesBuilder {

	private static Optional<PropertiesBuilder> builder = Optional.empty();
	
	private PropertyGetter envGetter;
	private PropertyGetter fileGetter;
	private Optional<RemotePropertyGetter> remoteSource;
	
	
	private PropertiesBuilder(PropertyGetter envGetter, PropertyGetter fileGetter, RemotePropertyGetter remoteSource) {
		this.envGetter = envGetter;
		this.fileGetter = fileGetter;
		this.remoteSource = Optional.ofNullable(remoteSource);
	}
	
	public static PropertiesBuilder getInstance() {
		if(!PropertiesBuilder.builder.isPresent()) {
			PropertiesBuilder.builder = Optional.of(new PropertiesBuilder(new GetterFromEnvironment(), new GetterFromFile(), null));
		}
		return PropertiesBuilder.builder.get();
	}
	
	public PropertiesBuilder hasFileGetter(PropertyGetter getter){
		if(getter==null)
			throw new NullPointerException("Null FileGetter provided in initialization");
		
		fileGetter = getter;
		return builder.get();
	}
	
	public PropertiesBuilder hasEnvGetter(PropertyGetter getter){
		if(getter==null)
			throw new NullPointerException("Null EnvGetter provided in initialization");
		
		envGetter = getter;
		return builder.get();
	}
	
	public PropertiesBuilder hasRemoteGetter(RemotePropertyGetter getter){
		remoteSource = Optional.of(getter);
		return builder.get();
	}
	
	public void configure() {
		Properties.configure(fileGetter, envGetter, remoteSource.get());
	}
	

}
