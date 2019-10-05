package io.github.sirnino.utils.properties.getters;

import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

public class GetterFromFile implements PropertyGetter {

	//Configuration file stuff
	private static final String BUNDLE_NAME = "application";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
		
	@Override
	public Optional<String> get(String key) {
		Optional<String> ret = Optional.empty();
		try {
			return ret = Optional.ofNullable(RESOURCE_BUNDLE.getString(key));
		} 
		catch (MissingResourceException e) {
			//No file or key exists
		}
		
		return ret;
	}

}
