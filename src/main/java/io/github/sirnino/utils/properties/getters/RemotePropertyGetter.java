package io.github.sirnino.utils.properties.getters;

public interface RemotePropertyGetter extends PropertyGetter {

	public <T> T buildClient();
	
}
