package io.github.sirnino.utils.properties.getters;

import java.util.Optional;

public interface PropertyGetter{

	public Optional<String> get(String key);
	
}
