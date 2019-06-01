package it.asirchia.utils.properties.getters;

import java.util.Optional;

public class GetterFromEnvironment implements PropertyGetter {

	@Override
	public Optional<String> get(String key) {
		return Optional.ofNullable(System.getenv(key));
	}

}
